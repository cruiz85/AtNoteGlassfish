package lector.server;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
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

 
public class UploadServlet extends javax.servlet.http.HttpServlet implements
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
 
        // Sets the size threshold beyond which files are written directly to disk.
        factory.setSizeThreshold(MAX_MEMORY_SIZE);
 
        // Sets the directory used to temporarily store files that are larger
        // than the configured size threshold. We use temporary directory for java
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        
        // constructs the folder where uploaded file will be stored
        String uploadFolder = getServletContext().getRealPath("")
                                    + File.separator + DATA_DIRECTORY;
 
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
 
        // Set overall request size constraint
        upload.setSizeMax(MAX_REQUEST_SIZE);
        List<String> webLinks = new ArrayList<String>();
        try {
            // Parse the request
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
 
                if (!item.isFormField()) {
                    String fileName = new File(item.getName()).getName();
                    String filePath = uploadFolder + File.separator + fileName;
                    File uploadedFile = new File(filePath);
                    
                    // saves the file to upload directory
                    item.write(uploadedFile);
                    webLinks.add(filePath);
                }
            }
            String publishedYear = request.getParameter(Constants.BLOB_PUBLISHED_YEAR);
    		String title = request.getParameter(Constants.BLOB_TITLE);
    		String isbn = request.getParameter(Constants.ISBN);
    		String pagesCount = request.getParameter(Constants.PAGES_COUNT);
    		String author = request.getParameter(Constants.BLOB_AUTHOR);
    		Long userAppId = Long.parseLong(request
    				.getParameter(Constants.BLOB_UPLOADER));
    		Professor professor = ((GWTServiceImpl) gwtServiceImpl).findProfessor(userAppId);
    		LocalBook localBook = new LocalBook(professor, author, isbn, pagesCount, publishedYear, title);
    		professor.getBooks().add(localBook);
    		saveUser((Professor)professor);
            // displays done.jsp page after upload finished
        //    getServletContext().getRequestDispatcher("/done.jsp").forward(request, response);
            
        } catch (FileUploadException ex) {
            throw new ServletException(ex);
        } catch(ProfessorNotFoundException pnfe){
        	
        }catch (Exception ex) {
            throw new ServletException(ex);
        }       
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