package lector.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

import org.apache.commons.codec.binary.Base64;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.ImageService;
import lector.share.model.Book;
import lector.share.model.BookNotFoundException;
import lector.share.model.ExportObject;
import lector.share.model.GeneralException;
import lector.share.model.Tag;
import lector.share.model.TagNotFoundException;
import lector.share.model.TextSelector;
import lector.share.model.client.BookClient;
import lector.share.model.client.TextSelectorClient;
import lector.share.model.client.TypeClient;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ImageServiceImpl extends RemoteServiceServlet implements
		ImageService {
	GWTService generalAppService = new GWTServiceImpl();
	private String PERSISTENCE_UNIT_NAME = "System";
	private EntityManagerFactory emf = Persistence
			.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	@Resource
	UserTransaction userTransaction;

	@Override
	public BookClient loadBookById(Long id) throws BookNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		Book a = entityManager.find(Book.class, id);
		if (a == null) {
			throw new BookNotFoundException(
					"Book not found in method loadBookById");
		}

		entityManager.close();
		return ServiceManagerUtils.produceBookClient(a);
	}

	@Override
	public List<BookClient> getBookByUserId(Long userAppId) {
		EntityManager entityManager = EMF.get().createEntityManager();
		List<Book> list;
		String sql = "SELECT r FROM Book r WHERE r.userApp=" + userAppId;
		list = entityManager.createQuery(sql).getResultList();

		if (list.isEmpty()) {
			return null;
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return ServiceManagerUtils.produceBookClients(list);
	}

	public String loadHTMLStringForExport(List<ExportObject> exportObjects) {
		StringBuffer html = new StringBuffer(
				"<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head>");
		html.append("<title>Export:");
		html.append(System.nanoTime());
		html.append("</title><body><table width=\"100%\"><tr><td><h1>Export:");
		html.append(System.nanoTime());
		html.append("</h1></td><td align=\"right\">" // TODO IMAGEPATH
				+ "</td></tr></table>");
		for (ExportObject exportObject : exportObjects) {
			html.append("<tr><hr><table align=\"center\" width=\"80%\" border=\"1\" bordercolor=\"blue\">");
			String imageURL = exportObject.getImageURL();
			List<TextSelector> anchors = exportObject.getAnnotation().getTextSelectors();
			List<TextSelectorClient> anchorsClient = ServiceManagerUtils.produceTextSelectors(anchors);
			int imageWidth = exportObject.getWidth();
			int imageHeight = exportObject.getHeight();
			html.append("<td rowspan=\"3\"><p>"
					+ produceCutImagesList(imageURL, anchorsClient, imageWidth,
							imageHeight, false)
					+ "</p></td><td colspan=\"2\"><p>");
			String Clear;
			try {
				Clear = new String(exportObject.getAnnotation().getComment()
						.getBytes(), "UTF-8");

				html.append(Clear + "</p></td></tr><tr>");
				List<String> fileNames = getTypesNames(exportObject.getAnnotation().getTags());

				html.append("<td colspan=\"2\"><p>");
				for (String fileName : fileNames) {
					html.append(fileName + ", ");
				}
				html.append("</p></td>");
				html.append("</tr><tr><td><p>" + exportObject.getAuthorName()
						+ "</p></td><td><p>" + exportObject.getDate()
						+ "</p></td></tr>");
				html.append("<tr><td colspan=\"2\"></td></tr>");
				html.append("</table>");
			} catch (UnsupportedEncodingException e) {
				Clear = exportObject.getAnnotation().getComment();
			} 
		}
		html.append("</body></html>");

		try {
			String htmlUTF = new String(html.toString().getBytes(), "UTF-8");
			return htmlUTF;
		} catch (UnsupportedEncodingException e) {

			return html.toString();
		}

	}

	private List<String> getTypesNames(List<Tag> tags) {
		List<String> strings = new ArrayList<String>();
		for (Tag tag : tags) {
			strings.add(tag.getName());
		}
		return strings;
	}

	private List<Long> getTypeClientIds(List<TypeClient> typeClients) {
		List<Long> ids = new ArrayList<Long>();
		for (TypeClient typeClient : typeClients) {
			ids.add(typeClient.getId());
		}
		return ids;
	}

	private String produceImagesFormatted(List<String> images, boolean isRTF) {
		String format = "";
		for (String image : images) {
			if (isRTF)
				format += image + "\\par";
			else
				format += image + "<br />";
		}

		return format;
	}

	public String imageTransformed(String imageURL, TextSelectorClient anchor,
			int imageWidth, int imageHeight, boolean isRTF) {
		float height = imageHeight;
		float width = imageWidth;
		float prop = height / 830;
		float widthResize = (width / prop);
		String contentType = getImageContentType(imageURL);
		byte[] oldImageData = getImageData(imageURL);
		ImagesService imagesService = ImagesServiceFactory.getImagesService();

		Image oldImage = ImagesServiceFactory.makeImage(oldImageData);
		Transform resize = ImagesServiceFactory.makeResize((int) widthResize,
				(int) 830);
		float leftX = anchor.getX() / widthResize;
		float topY = anchor.getY() / 830f;
		float rightX = (anchor.getX() + anchor.getWidth()) / widthResize;
		float bottomY = (anchor.getY() + anchor.getHeight()) / 830f;

		Transform crop = ImagesServiceFactory.makeCrop(leftX, topY, rightX,
				bottomY);
		Image newImage = imagesService.applyTransform(resize, oldImage);
		newImage = imagesService.applyTransform(crop, newImage);
		byte[] newImageData = newImage.getImageData();
		if (isRTF) {

			StringBuffer sb = new StringBuffer();

			sb.append("\n{\\*\\shppict{\\pict").append("\\picw")
					.append(newImage.getWidth()).append("\\pich")
					.append(newImage.getHeight()).append("\\")
					.append(contentType.replaceAll("image/", ""))
					.append("blip\n"); // for PNG images, use \pngblip

			String A = getHex(newImageData);

			// int mod= A.length()/64;

			sb.append(A);

			sb.append("\n}}");
			return sb.toString();
		}
		Base64 base64codec = new Base64();
		base64codec.encode(newImageData);
		String encodedText = new String(Base64.encodeBase64(newImageData));
		encodedText = "data:" + contentType + ";base64," + encodedText;
		String htmlReturn = "<img src=\"" + encodedText + "\">";
		return htmlReturn;

	}

	private String getImageContentType(String urlImage) {

		String contentType = null;
		try {
			URL url = new URL(urlImage);
			URLConnection uc = null;
			uc = url.openConnection();
			contentType = uc.getContentType();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contentType;

	}

	private byte[] getImageData(String urlImage) {
		byte[] data = null;
		try {
			URL url = new URL(urlImage);
			InputStream inputStream = url.openStream();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];

			int n = 0;
			while (-1 != (n = inputStream.read(buffer))) {
				output.write(buffer, 0, n);
			}
			inputStream.close();

			// Here's the content of the image...
			data = output.toByteArray();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;

	}

	static final String HEXES = "0123456789ABCDEF";

	public static String getHex(byte[] raw) {
		if (raw == null) {
			return null;
		}
		final StringBuilder hex = new StringBuilder(2 * raw.length);
		for (final byte b : raw) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(
					HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}

	@Override
	public String loadHTMLStringForExport(
			ArrayList<lector.share.model.ExportObject> exportObjects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loadHTMLStringForExportUni(
			lector.share.model.ExportObject exportObject) {
		StringBuffer html = new StringBuffer();
		/*
		 * StringBuffer html = new StringBuffer(
		 * "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head>"
		 * ); html.append("<title>Export:"); html.append(System.nanoTime());
		 * html
		 * .append("</title><body><table width=\"100%\"><tr><td><h1>Export:");
		 * html.append(System.nanoTime());
		 * html.append("</h1></td><td align=\"right\">"
		 * +logoImage()+"</td></tr></table>"); for (ExportObject exportObject :
		 * exportObjects) {
		 */
		html.append("<tr><hr><table align=\"center\" width=\"80%\" border=\"1\" bordercolor=\"blue\">");
		String imageURL = exportObject.getImageURL();
		List<TextSelector> anchors = exportObject.getAnnotation().getTextSelectors();
		List<TextSelectorClient> anchorsClient = ServiceManagerUtils.produceTextSelectors(anchors);
		int imageWidth = exportObject.getWidth();
		int imageHeight = exportObject.getHeight();
		html.append("<td rowspan=\"4\"><p>"
				+ produceCutImagesList(imageURL, anchorsClient, imageWidth,
						imageHeight,false) + "</p></td><td colspan=\"2\"><p>");
		String clear;
		clear = exportObject.getAnnotation().getComment();
		// try {
		// Clear = new String(exportObject.getAnnotation().getComment()
		// .getValue().getBytes(), "UTF-8");
		// } catch (UnsupportedEncodingException e) {
		// Clear = exportObject.getAnnotation().getComment().getValue();
		// }

		html.append(clear + "</p></td></tr><tr>");
		List<String> fileNames = getTypesNames(exportObject.getAnnotation().getTags());
		html.append("<td colspan=\"2\"><p>");
		for (String fileName : fileNames) {
			html.append(fileName + ", ");
		}
		html.append("</p></td>");
		html.append("</tr><tr><td><p>" + exportObject.getAuthorName()
				+ "</p></td><td><p>" + exportObject.getDate()
				+ "</p></td></tr>");
		html.append("<tr><td colspan=\"2\"></td></tr>");
		html.append("</table>");
		/* } */

		// try {
		// String htmlUTF = new String(html.toString().getBytes(), "UTF-8");
		// return htmlUTF;
		// } catch (UnsupportedEncodingException e) {
		//
		// return html.toString();
		// }
		return html.toString();
	}

	private String produceCutImagesList(String imageURL,List<TextSelectorClient> anchors, int imageWidth, int imageHeight, boolean isRTF) {
		List<String> images = new ArrayList<String>();
//		if (imageURL.startsWith("/serve")) {
//			for (TextSelector anchor : anchors) {
//				images.add(imageFromBlob(imageURL, anchor, imageWidth,
//						imageHeight,isRTF));
//			}
//
//		} else {
			for (TextSelectorClient anchor : anchors) {
				images.add(imageTransformed(imageURL, anchor, imageWidth,
						imageHeight, isRTF));
			}
//		}

		return produceImagesFormatted(images,isRTF);
	}
	
	@Override
	public String loadRTFStringForExportUni(
			lector.share.model.ExportObject exportObject) {
		// TODO Auto-generated method stub
		return null;
	}

}
