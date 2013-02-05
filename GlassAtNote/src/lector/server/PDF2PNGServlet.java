package lector.server;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import lector.client.book.reader.GWTService;
import lector.client.controler.Constants;
import lector.share.model.GeneralException;
import lector.share.model.LocalBook;
import lector.share.model.Professor;
import lector.share.model.ProfessorNotFoundException;
import lector.share.model.UserApp;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.SimpleRenderer;

public class PDF2PNGServlet extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	static final long serialVersionUID = 1L;
	@Resource
	UserTransaction userTransaction;
	private String PERSISTENCE_UNIT_NAME = "System";
	private EntityManagerFactory emf = Persistence
			.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	private static final String DATA_DIRECTORY = "data";
	private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
	private static final int MAX_REQUEST_SIZE = 1024 * 1024;
	private GWTService gwtServiceImpl = new GWTServiceImpl();

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (!isMultipart) {
			return;
		}

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Sets the size threshold beyond which files are written directly to
		// disk.
		factory.setSizeThreshold(MAX_MEMORY_SIZE);

		// Sets the directory used to temporarily store files that are larger
		// than the configured size threshold. We use temporary directory for
		// java
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		// constructs the folder where uploaded file will be stored
		// String uploadFolder = getServletContext().getRealPath("")
		// + File.separator + DATA_DIRECTORY;

		String uploadFolder = getServletContext().getRealPath("").substring(0,
				52)
				+ File.separator + "docroot" + File.separator + DATA_DIRECTORY;

		// String uploadFolderRel = getServletContext().getContextPath()
		// + File.separator + DATA_DIRECTORY;
		String uploadFolderRel = "\\" + DATA_DIRECTORY;
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Set overall request size constraint
		upload.setSizeMax(MAX_REQUEST_SIZE);
		List<String> webLinks = new ArrayList<String>();
		String publishedYear = "0000";
		String title = "";
		String isbn = "0";
		String pagesCount = "0";
		String author = "0";
		Long userAppId = null;
		try {
			// Parse the request
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();

				if (!item.isFormField()) {
					String fileName = new File(item.getName()).getName();
					String filePath = uploadFolder + File.separator + fileName;
					String fileRelPath = uploadFolderRel + File.separator
							+ fileName;
					File uploadedFile = new File(filePath);

					// saves the file to upload directory
					item.write(uploadedFile);
					String finalPath = fileRelPath.replace("//", "/");
					webLinks.add(finalPath);
				} else {
					if (item.getFieldName().equals(
							Constants.BLOB_PUBLISHED_YEAR)) {
						publishedYear = item.getString();
					}

					if (item.getFieldName().equals(Constants.BLOB_TITLE)) {
						title = item.getString();
					}
					if (item.getFieldName().equals(Constants.ISBN)) {
						isbn = item.getString();
					}
					if (item.getFieldName().equals(Constants.PAGES_COUNT)) {
						pagesCount = item.getString();
					}
					if (item.getFieldName().equals(Constants.BLOB_AUTHOR)) {
						author = item.getString();
					}
					if (item.getFieldName().equals(Constants.BLOB_UPLOADER)) {
						userAppId = Long.parseLong(item.getString());
					}

				}
			}

			Professor professor = ((GWTServiceImpl) gwtServiceImpl)
					.findProfessor(userAppId);
			LocalBook localBook = new LocalBook(professor, author, isbn,
					pagesCount, publishedYear, title);
			String absPath = getServletContext().getRealPath("").substring(0,
					52)
					+ File.separator
					+ "docroot"
					+ File.separator;
			List<String> imagesWebLinks = convert(absPath
					+ webLinks.get(0).substring(1));
			localBook.setWebLinks(imagesWebLinks);
			professor.getBooks().add(localBook);
			saveUser((Professor) professor);
			// displays done.jsp page after upload finished
			// getServletContext().getRequestDispatcher("/done.jsp").forward(request,
			// response);

		} catch (FileUploadException ex) {
			throw new ServletException(ex);
		} catch (ProfessorNotFoundException pnfe) {

		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	private List<String> convert(String directory) throws IOException,
			InterruptedException {
		Date date = new Date();
		Long id = (Long) date.getTime();
		String idString = id.toString();
		String cmd = "\"C:\\Program Files\\gs\\gs9.06\\bin\\gswin32c\" -sDEVICE=jpeg -dBATCH -r50 "
				+ "-dNOPAUSE -sOutputFile=\"C:\\glassfish3.2\\glassfish3\\glassfish\\domains\\domain1\\docroot\\data\\"
				+ idString + "%01d.jpg\" " + directory;

		String winDirectory = directory.replace("\\", "/"); // posiblemente se
															// tnenga que
															// remover para Unix
		String cmdPagesCount = "\"C:\\Program Files\\gs\\gs9.06\\bin\\gswin32c\" -q -dNODISPLAY -c (\""
				+ winDirectory
				+ ")"
				+ " (r) file runpdfbegin pdfpagecount = quit\"";
		// System.out.println(cmd);
		Process p = Runtime.getRuntime().exec(cmd);
		new Dumper(p.getInputStream()).start();
		new Dumper(p.getErrorStream()).start();
		int pagesCount = getPagesCount(cmdPagesCount);
		p.waitFor();
		// HACER LA FUNCION QUE GENERA LOS LINKS

		List<String> webLinks = generateLinks(idString, pagesCount);
		return webLinks;
	}

	private static List<String> generateLinks(String pattern, int pagesCount) {
		List<String> webLinks = new ArrayList<String>();
		for (int i = 1; i <= pagesCount; i++) {
			webLinks.add("/data/" + pattern + i + ".jpg");
		}
		return webLinks;
	}

	private int getPagesCount(String cmd) throws IOException {
		Process p2 = Runtime.getRuntime().exec(cmd);
		return Character.getNumericValue((char) p2.getInputStream().read());
	}

	private void saveUser(UserApp user) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			if (user.getId() == null) {
				entityManager.persist(user);
			} else {
				entityManager.merge(user);
			}

			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction); // TODO utilizar
															// m�todo de
															// logger
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

	}
}