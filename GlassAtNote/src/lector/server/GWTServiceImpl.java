package lector.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

import java.util.Date;

import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.persistence.Basic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import lector.client.book.reader.GWTService;

import lector.client.controler.Constants;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import lector.share.model.Annotation;
import lector.share.model.AnnotationNotFoundException;
import lector.share.model.AnnotationThread;
import lector.share.model.AnnotationThreadNotFoundException;
import lector.share.model.Book;
import lector.share.model.BookNotFoundException;
import lector.share.model.DecendanceException;
import lector.share.model.GeneralException;
import lector.share.model.GoogleBook;
import lector.share.model.GroupNotFoundException;
import lector.share.model.IlegalFolderFusionException;
import lector.share.model.LanguageNotFoundException;
import lector.share.model.LocalBook;
import lector.share.model.Catalogo;
import lector.share.model.Entry;
import lector.share.model.NullParameterException;
import lector.share.model.Professor;
import lector.share.model.ProfessorNotFoundException;
import lector.share.model.ReadingActivityNotFoundException;
import lector.share.model.Student;
import lector.share.model.StudentNotFoundException;
import lector.share.model.Tag;
import lector.share.model.FolderDB;
import lector.share.model.GroupApp;
import lector.share.model.Language;
import lector.share.model.ReadingActivity;
import lector.share.model.TagNotFoundException;
import lector.share.model.Template;
import lector.share.model.TextSelector;
import lector.share.model.UserApp;
import lector.share.model.UserNotFoundException;

public class GWTServiceImpl extends RemoteServiceServlet implements GWTService {

	private static ArrayList<Long> ids;
	private static ArrayList<Long> annotationThreadIds;
	private static List<Long> sonIds; // used in schema generator

	private EntityManager em;
	private String PERSISTENCE_UNIT_NAME = "System";
	private EntityManagerFactory emf = Persistence
			.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	@Resource
	UserTransaction userTransaction;

	// @Override
	// public UserApp login(String requestUri) throws UserNotFoundException {
	// UserService userService = UserServiceFactory.getUserService();
	// User user = userService.getCurrentUser();
	// UserApp userApp = new UserApp();
	// if (user != null) {
	// try {
	// userApp = loadUserByEmail(user.getEmail());
	// } catch (GeneralException ge) {
	// userApp = new UserApp();
	// userApp.setLoggedIn(false);
	// userApp.setLoginUrl(userService.createLoginURL(requestUri));
	// userApp.setLogoutUrl(userService.createLogoutURL(requestUri));
	// userApp.setEmail(user.getEmail());
	// userApp.setIsAuthenticated(false);
	// return userApp;
	// }
	// userApp.setLoggedIn(true);
	// userApp.setLogoutUrl(userService.createLogoutURL(requestUri));
	// userApp.setLoginUrl(userService.createLoginURL(requestUri));
	// } else {
	//
	// userApp.setLoggedIn(false);
	// userApp.setLoginUrl(userService.createLoginURL(requestUri));
	// userApp.setLogoutUrl(userService.createLogoutURL(requestUri));
	// }
	// return userApp;
	// }

	@Override
	public UserApp login(String requestUri) throws UserNotFoundException,
			GeneralException {
		boolean flag = true;
		UserApp userApp = new UserApp();

		try {

			userApp = loadUserByEmail("root@root");

		} catch (GeneralException ge) {
			saveUser(userApp);
			flag = false;
		} catch (UserNotFoundException une) {
			userApp = new Professor("root@root");
			userApp.setLastName("Joaquin");
			userApp.setAuthenticated(true);
			userApp.setLoggedIn(true);
			saveUser(userApp);
			flag = false;
		}
		if (!flag) {
			return loadUserByEmail("root@root");
		}
		ServiceManagerUtils.cleanProfessor((Professor) userApp);

		return userApp;
	}

	@Override
	public void saveUser(UserApp user) throws GeneralException {
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
															// método de
															// logger
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

	}

	@Override
	public UserApp loadUserById(Long userId) throws UserNotFoundException,
			GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		List<UserApp> list;

		String sql = "SELECT r FROM UserApp r WHERE r.id=" + userId;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadUserById: ", e)
			throw new GeneralException("Exception in method loadUserById:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadUserById: ", e)
			throw new UserNotFoundException(
					"User not found in method loadUserById");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list.get(0);
	}

	@Override
	public UserApp loadUserByEmail(String email) throws UserNotFoundException,
			GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		List<UserApp> list;

		String sql = "SELECT r FROM UserApp r WHERE r.email='" + email + "'";
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadUserByEmail: ", e)
			throw new GeneralException("Exception in method loadUserByEmail:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadUserById: ", e)
			throw new UserNotFoundException(
					"User not found in method loadUserByEmail");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	//	ServiceManagerUtils.cleanProfessor((Professor) list.get(0));
		return list.get(0);
	}

	@Override
	public List<Student> getStudentsByGroupId(Long groupId)
			throws GroupNotFoundException, GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		List<GroupApp> list;

		String sql = "SELECT r FROM GroupApp r WHERE r.id=" + groupId;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupByEmail: ", e)
			throw new GeneralException("Exception in method loadGroupByEmail:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new GroupNotFoundException(
					"Group not found in method loadGroupByEmail");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list.get(0).getParticipatingStudents();
	}

	@Override
	public List<Student> getStudents() throws GeneralException,
			StudentNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<Student> list;

		String sql = "SELECT r FROM Student r";
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupByEmail: ", e)
			throw new GeneralException("Exception in method getStudents:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new StudentNotFoundException(
					"Student not found in method getStudents");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list;
	}

	@Override
	public void deleteStudentById(Long studentId) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			entityManager.createQuery(
					"DELETE FROM Student s WHERE s.id=" + studentId)
					.executeUpdate();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException("Exception in method deleteStudentById"
					+ e.getMessage(), e.getStackTrace());
		}
	}

	@Override
	public List<Professor> getProfessors() throws GeneralException,
			ProfessorNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<Professor> list;

		String sql = "SELECT r FROM Professor r";
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupByEmail: ", e)
			throw new GeneralException("Exception in method getProfessors:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new ProfessorNotFoundException(
					"Professor not found in method getProfessors");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list;
	}

	@Override
	public void deleteProfessorById(Long professorId) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			entityManager.createQuery(
					"DELETE FROM Professor s WHERE s.id=" + professorId)
					.executeUpdate();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException(
					"Exception in method deleteProfessorById" + e.getMessage(),
					e.getStackTrace());
		}
	}

	@Override
	public void saveGroup(GroupApp group) {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			if (group.getId() == null) {
				entityManager.persist(group);
			} else {
				entityManager.merge(group);
			}

			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction); // TODO utilizar
															// método de
															// logger
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

	}

	@Override
	public GroupApp loadGroupById(Long groupId) throws GeneralException,
			GroupNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<GroupApp> list;

		String sql = "SELECT r FROM GroupApp r WHERE r.id=" + groupId;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new GeneralException("Exception in method loadGroupById:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new GroupNotFoundException(
					"Group not found in method loadGroupById");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list.get(0);
	}

	@Override
	public List<GroupApp> getGroupsByUserId(Long userId)
			throws GeneralException {
		try {
			UserApp userApp = loadUserById(userId);
			if (userApp instanceof Professor) {
				return ((Professor) userApp).getGroups();
			} else {
				return ((Student) userApp).getParticipatingGroups();
			}
		} catch (Exception e) {
			throw new GeneralException("Exception in method getGroupsByUserId");
		}

	}

	@Override
	public void deleteGroup(Long groupId) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			entityManager.createQuery(
					"DELETE FROM GroupApp s WHERE s.id=" + groupId)
					.executeUpdate();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException("Exception in method deleteGroupAppById"
					+ e.getMessage(), e.getStackTrace());
		}
	}

	@Override
	public void addStudentToBeValidated(Long userId, Long groupId)
			throws GeneralException {
		try {
			GroupApp group = loadGroupById(groupId);
			Student student = (Student) loadUserById(userId);
			if (!group.getRemainingStudents().contains(student)) {
				group.getRemainingStudents().add(student);
			}
			saveGroup(group);
		} catch (Exception e) {
			throw new GeneralException(
					"Error in method addStudentToBeValidated");
		}

	}

	@Override
	public void validStudentToBeInGroup(Long userId, Long groupId)
			throws GeneralException {
		try {
			Student student = (Student) loadUserById(userId);
			GroupApp group = loadGroupById(groupId);
			if (group.getRemainingStudents().contains(student)) {
				group.getRemainingStudents().remove(student);
			} else {
				throw new GeneralException(
						"Hey!!! this user was not on the list to be validated from: the remainingList");
			}
			if (!group.getParticipatingStudents().contains(student)) {
				group.getParticipatingStudents().add(student);
			}
			saveGroup(group);

		} catch (Exception e) {
			throw new GeneralException(
					"Error in method validStudentToBeInGroup");
		}

	}

	@Override
	public void saveAnnotation(Annotation annotation) {
		EntityManager entityManager = emf.createEntityManager();

		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		now = calendar.getTime();
		annotation.setCreatedDate(now);
		try {
			userTransaction.begin();
			if (annotation.getId() == null) {
				entityManager.persist(annotation);
			} else {
				entityManager.merge(annotation);
			}

			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction); // TODO utilizar
															// método de
															// logger
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

	}

	@Override
	public List<Annotation> getAnnotationsByBookId(Long activityId,
			String bookId) throws GeneralException, AnnotationNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<Annotation> list;

		String sql = "SELECT r FROM Annotation r WHERE r.r.readinActivity.id="
				+ activityId + "r.readinActivity.book.ISBN'=" + bookId + "'";
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupByEmail: ", e)
			throw new GeneralException("Exception in method loadGroupByEmail:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new AnnotationNotFoundException(
					"Group not found in method loadGroupByEmail");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list;
	}

	@Override
	public List<Annotation> getAnnotationsByPageNumbertAndUserId(
			Integer pageNumber, String bookId, Long studentId,
			Long readingActivityId) throws GeneralException,
			AnnotationNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<Annotation> list;

		String sql = "SELECT r FROM Annotation r WHERE r.readinActivity.id="
				+ readingActivityId + "r.readinActivity.book.ISBN'=" + bookId
				+ "' AND r.pageNumber=" + pageNumber + "AND r.creator.id="
				+ studentId;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupByEmail: ", e)
			throw new GeneralException("Exception in method loadGroupByEmail:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new AnnotationNotFoundException(
					"Group not found in method loadGroupByEmail");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list;
	}

	@Override
	public List<Annotation> getAnnotationsByPageNumber(Integer pageNumber,
			String bookId, Long readingActivityId) throws GeneralException,
			AnnotationNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<Annotation> list;

		String sql = "SELECT r FROM Annotation r WHERE r.readinActivity.id="
				+ readingActivityId + "r.readinActivity.book.ISBN'=" + bookId
				+ "' AND r.pageNumber=" + pageNumber;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupByEmail: ", e)
			throw new GeneralException("Exception in method loadGroupByEmail:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new AnnotationNotFoundException(
					"Group not found in method loadGroupByEmail");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list;
	}

	@Override
	public List<Annotation> getAnnotationsByIds(List<Long> ids) {
		List<Annotation> annotations = new ArrayList<Annotation>();
		for (int i = 0; i < ids.size(); i++) {
			Annotation annotation = quickFind(ids.get(i));
			if (annotation != null) {
				annotations.add(annotation);
			}

		}
		return annotations;
	}

	private Annotation quickFind(Long id) {
		EntityManager entityManager = emf.createEntityManager();
		Annotation a = entityManager.find(Annotation.class, id);
		return a;
	}

	// TODO que hace este método.
	@Override
	public List<Annotation> getAnnotationsByIdsAndAuthorsTeacher(
			List<Long> ids, List<Long> authorIds, Long Activity) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO que hace este método.
	@Override
	public List<Annotation> getAnnotationsByTeacherIds(List<Long> ids,
			Long readingActivityId) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO que hace este método.
	@Override
	public List<Annotation> getAnnotationsByStudentIds(List<Long> ids,
			Long Student, Long readingActivityId) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO que hace este método.
	@Override
	public List<Annotation> getAnnotationsByIdsAndAuthorsStudent(
			List<Long> ids, List<Long> authorIds, Long Activity, Long Student) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAnnotation(Long annotationId) throws GeneralException,
			AnnotationNotFoundException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			entityManager.createQuery(
					"DELETE FROM Annotation s WHERE s.id=" + annotationId)
					.executeUpdate();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException(
					"Exception in method deleteAnnotationById" + e.getMessage(),
					e.getStackTrace());
		}
	}

	@Override
	public void saveAnnotationThread(AnnotationThread annotationThread) {
		EntityManager entityManager = emf.createEntityManager();

		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		now = calendar.getTime();
		annotationThread.setCreatedDate(now);
		try {
			userTransaction.begin();
			if (annotationThread.getId() == null) {
				entityManager.persist(annotationThread);
			} else {
				entityManager.merge(annotationThread);
			}

			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction); // TODO utilizar
															// método de
															// logger
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

	}

	@Override
	public void deleteAnnotationThread(Long annotationThreadId)
			throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			entityManager.createQuery(
					"DELETE FROM AnnotationThread s WHERE s.id="
							+ annotationThreadId).executeUpdate();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException(
					"Exception in method deleteAnnotationThreadById"
							+ e.getMessage(), e.getStackTrace());
		}
	}

	@Override
	public List<AnnotationThread> getAnnotationThreadsByItsFather(
			Long threadFatherId) throws GeneralException,
			AnnotationThreadNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<AnnotationThread> list;

		String sql = "SELECT r FROM AnnotationThread r WHERE r.father.id="
				+ threadFatherId;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadUserById: ", e)
			throw new GeneralException(
					"Exception in method getAnnotationThreadsByItsFather:"
							+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadUserById: ", e)
			throw new AnnotationThreadNotFoundException(
					"AnnotationThread not found in method getAnnotationThreadsByItsFather");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list.get(0).getSubThreads();
	}

	@Override
	public Book loadBookById(Long id) throws BookNotFoundException,
			GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		List<Book> list;

		String sql = "SELECT r FROM Book r WHERE r.id=" + id;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new GeneralException("Exception in method loadBookById:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new BookNotFoundException(
					"Book not found in method loadBookById");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list.get(0);
	}

	@Override
	public GoogleBook loadFullBookInGoogle(String query) {
		GoogleBook googleBook = getBookInGoogleByISBN(query);
		String cleanGoogleBookId = googleBook.getUrl().substring(33, 45);
		googleBook
				.setWebLinks(getBookImageInGoole(getBookImageStringInGoogle(cleanGoogleBookId)));
		googleBook.setImagesPath(googleBook.getWebLinks().get(0));
		return googleBook;
	}

	private ArrayList<String> getBookImageInGoole(String imagesWithinHTML) {
		ArrayList<String> list = new ArrayList<String>();
		// System.out.println(imagesWithinHTML);
		// Portadas
		String[] PP0 = imagesWithinHTML.split("\"pid\":\"PP0\",\"src\":\"");
		if (PP0.length == 2) {
			String[] PP = PP0[1].split("\"");

			list.add(PP[0].replaceAll("\\\\u0026", "&"));
		}
		String[] PP1 = imagesWithinHTML.split("\"pid\":\"PP1\",\"src\":\"");
		if (PP1.length == 2) {
			String[] PP = PP1[1].split("\"");

			list.add(PP[0].replaceAll("\\\\u0026", "&"));
		}
		// Hojas
		String[] PAI = imagesWithinHTML.split("\"pid\":\"PA1\",\"src\":\"");
		String[] PP = PAI[1].split("\"");

		list.add(PP[0].replaceAll("\\\\u0026", "&"));
		int cont = 2;
		while (PAI.length != 1) {
			PAI = imagesWithinHTML.split("\"pid\":\"PA" + cont
					+ "\",\"src\":\"");
			if (PAI.length != 1) {
				PP = PAI[1].split("\"");
				list.add(PP[0].replaceAll("\\\\u0026", "&"));
			}
			cont++;
		}
		return list;
	}

	private String getBookImageStringInGoogle(String id) {
		URL url;
		URLConnection connection;
		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader;
		try {
			url = new URL(
					"http://books.google.com/ebooks/reader?id="
							+ id
							+ "&pg=PP0&key=ABQIAAAAgGfd0Syld4wI6M_8-PchExQ_l6-Ytnm_KJl3gFahMrxfvqMmehRrB92flZ-iJptRd3l62UsasikVhg");
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
		return builder.toString();
	}

	private GoogleBook getBookInGoogleByISBN(String query) {
		return getGoogleBooks(query).get(0);
	}

	@Override
	public List<GoogleBook> getGoogleBooks(String query) {
		String cleanQuery = ServiceManagerUtils.removeSpaces(query);
		URL url;
		URLConnection connection;
		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader;
		List<GoogleBook> googleBooks = new ArrayList<GoogleBook>();

		try {
			url = new URL(
					"https://ajax.googleapis.com/ajax/services/search/books?"
							+ "as_brr=1&v=1.0&q="
							+ cleanQuery
							+ "&rsz=8&start=3&key=ABQIAAAAgGfd0Syld4wI6M_8-PchExQ_l6-Ytnm_KJl3gFahMrxfvqMmehRrB92flZ-iJptRd3l62UsasikVhg");
			connection = url.openConnection();
			connection.addRequestProperty("Referer",
					"http://kido180020783.appspot.com/");
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			JSONObject json = new JSONObject(builder.toString());
			JSONObject responseObject = json.getJSONObject("responseData");
			JSONArray results = responseObject.getJSONArray("results");
			for (int i = 0; i < results.length(); i++) {
				JSONObject jsonBook = results.getJSONObject(i);
				googleBooks.add(new GoogleBook(jsonBook.getString("authors"),
						jsonBook.getString("bookId"), jsonBook
								.getString("pageCount"), jsonBook
								.getString("publishedYear"), jsonBook
								.getString("title"), jsonBook
								.getString("tbUrl"), jsonBook
								.getString("unescapedUrl")));
			}

		} catch (MalformedURLException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (IOException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (JSONException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return googleBooks;
	}

	@Override
	public List<GoogleBook> getBooks(String query, int start) {
		String cleanQuery = ServiceManagerUtils.removeSpaces(query);
		URL url;
		URLConnection connection;
		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader;
		List<GoogleBook> googleBooks = new ArrayList<GoogleBook>();

		try {
			url = new URL(
					"https://ajax.googleapis.com/ajax/services/search/books?"
							+ "v=1.0&as_brr=1&q="
							+ cleanQuery
							+ "&rsz=8&start="
							+ start
							+ "&rsz=8&key=ABQIAAAAgGfd0Syld4wI6M_8-PchExQ_l6-Ytnm_KJl3gFahMrxfvqMmehRrB92flZ-iJptRd3l62UsasikVhg");
			connection = url.openConnection();
			connection.addRequestProperty("Referer",
					"http://kido180020783.appspot.com/");
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			JSONObject json = new JSONObject(builder.toString());
			JSONObject responseObject = json.getJSONObject("responseData");
			JSONArray results = responseObject.getJSONArray("results");
			for (int i = 0; i < results.length(); i++) {
				JSONObject jsonBook = results.getJSONObject(i);
				googleBooks.add(new GoogleBook(jsonBook.getString("authors"),
						jsonBook.getString("bookId"), jsonBook
								.getString("pageCount"), jsonBook
								.getString("publishedYear"), jsonBook
								.getString("title"), jsonBook
								.getString("tbUrl"), jsonBook
								.getString("unescapedUrl")));
			}

		} catch (MalformedURLException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (IOException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (JSONException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return googleBooks;
	}

	@Override
	public void saveCatalog(Catalogo catalog) {
		// TODO Auto-generated method stub

	}

	@Override
	public Catalogo loadCatalogById(Long catalogId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getCatalogs() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Catalogo> getVisbibleCatalogsByProfessorId(Long professorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCatalog(Long catalogId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Long> getEntriesIdsByNames(ArrayList<String> names,
			Long catalogTeacher, Long catalogOpen) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Entry> getEntriesByIdsRecursiveIfFolder(ArrayList<Long> Ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tag loadTagById(Long tagId) throws TagNotFoundException,
			GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		List<Tag> list;

		String sql = "SELECT r FROM Tag r WHERE r.id=" + tagId;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadTagById: ", e)
			throw new GeneralException("Exception in method loadTagById:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadTagById: ", e)
			throw new TagNotFoundException(
					"Tag not found in method loadTagById");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list.get(0);
	}

	@Override
	public Tag loadTagByNameAndCatalogId(String tagName, Long catalogId)
			throws TagNotFoundException, GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		List<Tag> list;

		String sql = "SELECT r FROM Tag r WHERE r.name='" + tagName
				+ "' AND r.catalog.id=" + catalogId;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadTagById: ", e)
			throw new GeneralException("Exception in method loadTagById:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadTagById: ", e)
			throw new TagNotFoundException(
					"Tag not found in method loadTagById");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list.get(0);
	}

	@Override
	public void saveTag(Tag typesys, Long typeCategoryId) {

	}

	@Override
	public Long deleteTag(Long typeId, Long typeCategoryId)
			throws GeneralException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getAnnotationsNumberByTagName(String annotationTagName)
			throws GeneralException, NullParameterException,
			AnnotationNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long fusionTags(Long typeFromId, Long typeToId)
			throws GeneralException, NullParameterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void moveTag(Long typeCategoryFromId, Long typeId,
			Long typeCategoryToId) throws GeneralException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Tag> getTagsByNameAndCatalogId(ArrayList<String> typeNames,
			Long catalogId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tag> getTagsByIds(ArrayList<Long> typeIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getTagNamesByIds(ArrayList<Long> typeIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void renameTag(Long typeIds, String newTagName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTagToFolderDB(Long typeIds, Long fatherFolderDBId) {
		// TODO Auto-generated method stub

	}

	@Override
	public FolderDB loadFolderDBById(Long typeCategoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FolderDB loadFolderDBByNameAndCatalogId(String FolderDBName,
			Long catalogId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteFolderDB(Long typeCategoryId, Long fatherFolderDBId)
			throws GeneralException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Entry> getSonsFromFolderDB(Long typeCategoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void renameFolderDB(Long typeCategoryId, String newFolderDBName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveFolderDB(FolderDB typeCategory, Long typeCategoryFatherId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveFolderDB(Long typeCategoryId, Long typeCategoryFromId,
			Long typeCategoryToId) throws GeneralException, DecendanceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void fusionFolder(Long typeCategoryFromId, Long typeCategoryToId)
			throws IlegalFolderFusionException, GeneralException {
		// TODO Auto-generated method stub

	}

	@Override
	public ReadingActivity loadReadingActivityById(Long readingActivityId)
			throws ReadingActivityNotFoundException, GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		List<ReadingActivity> list;

		String sql = "SELECT r FROM ReadingActivity r WHERE r.id="
				+ readingActivityId;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadReadingActivityById: ", e)
			throw new GeneralException(
					"Exception in method loadReadingActivityById:"
							+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadReadingActivityById: ", e)
			throw new ReadingActivityNotFoundException(
					"ReadingActivity not found in method loadReadingActivityById");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list.get(0);
	}

	@Override
	public void deleteReadingActivity(Long readingActivityId)
			throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			entityManager.createQuery(
					"DELETE FROM ReadingActivity s WHERE s.id="
							+ readingActivityId).executeUpdate();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException(
					"Exception in method deleteReadingActivityById"
							+ e.getMessage(), e.getStackTrace());
		}
	}

	@Override
	public Integer removeReadingActivityAnnotations(Long readingActivity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReadingActivity> getReadingActivitiesByStudentId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReadingActivity> getReadingActivitiesByProfessorId(
			Long professorId) throws GeneralException,
			ReadingActivityNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<ReadingActivity> list;

		String sql = "SELECT r FROM ReadingActivity r WHERE r.professor.id="
				+ professorId;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadUserById: ", e)
			throw new GeneralException(
					"Exception in method getReadingActivitysByItsFather:"
							+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadUserById: ", e)
			throw new ReadingActivityNotFoundException(
					"ReadingActivity not found in method getReadingActivitysByItsFather");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list;
	}

	@Override
	public void saveReadingActivity(ReadingActivity readingActivity)
			throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			if (readingActivity.getId() == null) {
				entityManager.persist(readingActivity);
			} else {
				entityManager.merge(readingActivity);
			}

			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction); // TODO utilizar
															// método de
															// logger
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

	}

	@Override
	public void updateReadingActivities() { // TODO que hace esto?.
		// TODO Auto-generated method stub

	}

	@Override
	public void saveLanguage(Language language) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			if (language.getId() == null) {
				entityManager.persist(language);
			} else {
				entityManager.merge(language);
			}

			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction); // TODO utilizar
															// método de
															// logger
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

	}

	@Override
	public void deleteLanguage(Long languageId) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			entityManager.createQuery(
					"DELETE FROM Language s WHERE s.id=" + languageId)
					.executeUpdate();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException("Exception in method deleteLanguageById"
					+ e.getMessage(), e.getStackTrace());
		}
	}

	@Override
	public List<String> getLanguagesNames() throws GeneralException,
			LanguageNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<String> list;

		String sql = "SELECT r.name FROM Language r";
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupByEmail: ", e)
			throw new GeneralException("Exception in method getLanguages:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new LanguageNotFoundException(
					"Language not found in method getLanguages");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list;
	}

	@Override
	public List<Language> getLanguages() throws GeneralException,
			LanguageNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<Language> list;

		String sql = "SELECT r FROM Language r";
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupByEmail: ", e)
			throw new GeneralException("Exception in method getLanguages:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new LanguageNotFoundException(
					"Language not found in method getLanguages");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list;
	}

	@Override
	public Language loadLanguageById(Long languageId)
			throws LanguageNotFoundException, GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		List<Language> list;

		String sql = "SELECT r FROM Language r WHERE r.id=" + languageId;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadLanguageById: ", e)
			throw new GeneralException("Exception in method loadLanguageById:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadLanguageById: ", e)
			throw new LanguageNotFoundException(
					"Language not found in method loadLanguageById");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list.get(0);
	}

	@Override
	public String getJSONServiceTODrawGraph(String url, String body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserApp loginAuxiliar(String userEmail) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBook(String bookId, Long userId) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			entityManager.createQuery(
					"DELETE FROM Book s WHERE s.ISBN='" + bookId
							+ "' AND s.professor.id=" + userId).executeUpdate();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException("Exception in method deleteBookById"
					+ e.getMessage(), e.getStackTrace());
		}
	}

}
