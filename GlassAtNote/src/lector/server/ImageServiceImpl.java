package lector.server;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;
import org.apache.commons.codec.binary.Base64;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.ImageService;
import lector.share.model.Annotation;
import lector.share.model.Book;
import lector.share.model.BookNotFoundException;
import lector.share.model.ExportObject;
import lector.share.model.Tag;
import lector.share.model.TextSelector;
import lector.share.model.client.BookClient;
import lector.share.model.client.TextSelectorClient;
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

	private List<String> getTypesNames(List<Tag> tags) {
		List<String> strings = new ArrayList<String>();
		for (Tag tag : tags) {
			strings.add(tag.getName());
		}
		return strings;
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
//		String urlWithoutHeader = imageURL.substring(41);
//		byte[] oldImageData = getImageDataService(urlWithoutHeader);
//		ImagesService imagesService = ImagesServiceFactory.getImagesService();
//		Image oldImage = ImagesServiceFactory.makeImage(oldImageData);
		
		BufferedImage originalImage = ServiceManagerUtils.readImageFromURL(imageURL);
		int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		BufferedImage newImage = ServiceManagerUtils.resizeImage(originalImage, (int)widthResize, 830, type);
//		Transform resize = ImagesServiceFactory.makeResize((int) widthResize,
//				(int) 830);
		float leftX = anchor.getX();
		float topY = anchor.getY();
		float rightX = (anchor.getX() + anchor.getWidth());
		float bottomY = (anchor.getY() + anchor.getHeight());
		byte[] newImageData = null;
		try {
			BufferedImage processedImage = ServiceManagerUtils.cropMyImage(newImage, Math.round(anchor.getWidth()), Math.round(anchor.getHeight()), Math.round(anchor.getX()), Math.round(anchor.getY()));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(processedImage, "jpg", baos );
			baos.flush();
			newImageData = baos.toByteArray();
			baos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
//		Transform crop = ImagesServiceFactory.makeCrop(leftX, topY, rightX,
//				bottomY);
//		Image newImage = imagesService.applyTransform(resize, oldImage);
	//	newImage = imagesService.applyTransform(crop, newImage);
	//	byte[] newImageData = newImage.getImageData();
		
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

	private byte[] getImageDataService(String id) {
		URL url;
		URLConnection connection;
		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader;
		try {
			url = new URL(
					"http://a-note.appspot.com/rs/AtNote/google/book/image"
							+ id);
			connection = url.openConnection();
			connection.addRequestProperty("Referer",
					"http://kido180020783.appspot.com/");
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
		} catch (MalformedURLException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (IOException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return builder.toString().getBytes();
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
	public String loadHTMLStringForExportUni(ExportObject exportObject) {
		EntityManager entityManager = emf.createEntityManager();
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
		Annotation annotation = entityManager.find(Annotation.class,
				exportObject.getAnnotation().getId());
		List<TextSelector> anchors = annotation.getTextSelectors();
		List<TextSelectorClient> anchorsClient = ServiceManagerUtils
				.produceTextSelectors(anchors);
		int imageWidth = exportObject.getWidth();
		int imageHeight = exportObject.getHeight();
		html.append("<td rowspan=\"4\"><p>"
				+ produceCutImagesList(imageURL, anchorsClient, imageWidth,
						imageHeight, false) + "</p></td><td colspan=\"2\"><p>");
		String clear;
		clear = exportObject.getAnnotation().getComment();
		// try {
		// Clear = new String(exportObject.getAnnotation().getComment()
		// .getValue().getBytes(), "UTF-8");
		// } catch (UnsupportedEncodingException e) {
		// Clear = exportObject.getAnnotation().getComment().getValue();
		// }

		html.append(clear + "</p></td></tr><tr>");
		List<String> fileNames = getTypesNames(annotation.getTags());
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

	private String produceCutImagesList(String imageURL,
			List<TextSelectorClient> anchors, int imageWidth, int imageHeight,
			boolean isRTF) {
		List<String> images = new ArrayList<String>();
		// if (imageURL.startsWith("/serve")) {
		// for (TextSelector anchor : anchors) {
		// images.add(imageFromBlob(imageURL, anchor, imageWidth,
		// imageHeight,isRTF));
		// }
		//
		// } else {
		for (TextSelectorClient anchor : anchors) {
			images.add(imageTransformed(imageURL, anchor, imageWidth,
					imageHeight, isRTF));
		}
		// }

		return produceImagesFormatted(images, isRTF);
	}

	@Override
	public String loadRTFStringForExportUni(ExportObject exportObject) {
		StringBuffer rtf = new StringBuffer();
		String imageURL = exportObject.getImageURL();
		EntityManager entityManager = emf.createEntityManager();
		Annotation annotation = entityManager.find(Annotation.class,
				exportObject.getAnnotation().getId());
		List<TextSelectorClient> anchors = exportObject.getAnnotation().getTextSelectors();
		int imageWidth = exportObject.getWidth();
		int imageHeight = exportObject.getHeight();
		String clear = exportObject.getAnnotation().getComment();
		clear=clear.replace("<div>", "\\par ");
		clear=ParserHTML2RTF.parser(clear);
//		String Links =findLinks(clear);
//		String Image =StractImage(clear);
//		clear=clear.replace("<div>", "\\par ");
//		clear=clear.replaceAll("\\<a.*?/a\\>", "");
//		clear=clear.replaceAll("\\<.*?\\>","");
//		clear=clear+Links+Image;
		rtf.append("\\trowd\\trgaph15\\trleft-15\\trqc\\trbrdrl\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrt\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrr\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrb\\brdrdash\\brdrw15\\brdrcf2 \\trpaddl15\\trpaddr15\\trpaddfl3\\trpaddfr3"
				+ "\\clvmgf\\clvertalc\\clbrdrl\\brdrw15\\brdrs\\brdrcf2\\clbrdrt\\brdrw15\\brdrs\\brdrcf2\\clbrdrr\\brdrw15\\brdrs\\brdrcf2\\clbrdrb\\brdrw15\\brdrs\\brdrcf2 \\cellx2066\\clvertalc\\clbrdrl\\brdrw15\\brdrs\\brdrcf2\\clbrdrt\\brdrw15\\brdrs\\brdrcf2\\clbrdrr\\brdrw15\\brdrs\\brdrcf2\\clbrdrb\\brdrw15\\brdrs\\brdrcf2 \\cellx7151\\pard\\intbl\\nowidctlpar\\sb100\\sa100\\cf1\\fs27"
				+ produceCutImagesList(imageURL, anchors, imageWidth,
						imageHeight,true)
				+ "\\cell "
				+ clear
				+ "\\cell\\row\\trowd\\trgaph15\\trleft-15\\trqc\\trbrdrl\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrt\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrr\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrb\\brdrdash\\brdrw15\\brdrcf2 \\trpaddl15\\trpaddr15\\trpaddfl3\\trpaddfr3"
				+ "\\clvmrg\\clvertalc\\clbrdrl\\brdrw15\\brdrs\\brdrcf2\\clbrdrt\\brdrw15\\brdrs\\brdrcf2\\clbrdrr\\brdrw15\\brdrs\\brdrcf2\\clbrdrb\\brdrw15\\brdrs\\brdrcf2 \\cellx2066\\clvertalc\\clbrdrl\\brdrw15\\brdrs\\brdrcf2\\clbrdrt\\brdrw15\\brdrs\\brdrcf2\\clbrdrr\\brdrw15\\brdrs\\brdrcf2\\clbrdrb\\brdrw15\\brdrs\\brdrcf2 \\cellx7151\\pard\\intbl\\nowidctlpar\\cell\\pard\\intbl\\nowidctlpar\\sb100\\sa100"
				+ getTypesNames(annotation.getTags())
				+ "\\cell\\row\\trowd\\trgaph15\\trleft-15\\trqc\\trbrdrl\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrt\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrr\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrb\\brdrdash\\brdrw15\\brdrcf2 \\trpaddl15\\trpaddr15\\trpaddfl3\\trpaddfr3"
				+ "\\clvmrg\\clvertalc\\clbrdrl\\brdrw15\\brdrs\\brdrcf2\\clbrdrt\\brdrw15\\brdrs\\brdrcf2\\clbrdrr\\brdrw15\\brdrs\\brdrcf2\\clbrdrb\\brdrw15\\brdrs\\brdrcf2 \\cellx2066\\clvertalc\\clbrdrl\\brdrw15\\brdrs\\brdrcf2\\clbrdrt\\brdrw15\\brdrs\\brdrcf2\\clbrdrr\\brdrw15\\brdrs\\brdrcf2\\clbrdrb\\brdrw15\\brdrs\\brdrcf2 \\cellx4722\\clvertalc\\clbrdrl\\brdrw15\\brdrs\\brdrcf2\\clbrdrt\\brdrw15\\brdrs\\brdrcf2\\clbrdrr\\brdrw15\\brdrs\\brdrcf2\\clbrdrb\\brdrw15\\brdrs\\brdrcf2 \\cellx7151\\pard\\intbl\\nowidctlpar\\cell\\pard\\intbl\\nowidctlpar\\sb100\\sa100 " + exportObject.getAuthorName() + " \\cell " +exportObject.getDate() + "\\cell\\row\\trowd\\trgaph15\\trleft-15\\trqc\\trbrdrl\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrt\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrr\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrb\\brdrdash\\brdrw15\\brdrcf2 \\trpaddl15\\trpaddr15\\trpaddfl3\\trpaddfr3"
				+ "\\clvmrg\\clvertalc\\clbrdrl\\brdrw15\\brdrs\\brdrcf2\\clbrdrt\\brdrw15\\brdrs\\brdrcf2\\clbrdrr\\brdrw15\\brdrs\\brdrcf2\\clbrdrb\\brdrw15\\brdrs\\brdrcf2 \\cellx2066\\clvertalc\\clbrdrl\\brdrw15\\brdrs\\brdrcf2\\clbrdrt\\brdrw15\\brdrs\\brdrcf2\\clbrdrr\\brdrw15\\brdrs\\brdrcf2\\clbrdrb\\brdrw15\\brdrs\\brdrcf2 \\cellx4722\\clvertalc\\clbrdrr\\brdrw15\\brdrs\\brdrcf2\\clbrdrb\\brdrw15\\brdrs\\brdrcf2 \\cellx7151\\pard\\intbl\\nowidctlpar\\cell\\cf0\\fs24\\cell\\fs20\\cell\\row\\pard\\nowidctlpar\\fs24\\par");
		
		return rtf.toString();
	}

}
