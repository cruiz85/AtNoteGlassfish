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

import javax.persistence.Basic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import lector.client.book.reader.GWTService;

import lector.client.controler.Constants;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import lector.share.model.Annotation;
import lector.share.model.AnnotationNotFoundException;
import lector.share.model.AnnotationThread;
import lector.share.model.Book;
import lector.share.model.BookNotFoundException;
import lector.share.model.DecendanceException;
import lector.share.model.GeneralException;
import lector.share.model.GroupNotFoundException;
import lector.share.model.IlegalFolderFusionException;
import lector.share.model.LanguageNotFoundException;
import lector.share.model.LocalBook;
import lector.share.model.Catalogo;
import lector.share.model.Entry;
import lector.share.model.NullParameterException;
import lector.share.model.Professor;
import lector.share.model.ProfessorNotFoundException;
import lector.share.model.Student;
import lector.share.model.StudentNotFoundException;
import lector.share.model.Tag;
import lector.share.model.FolderDB;
import lector.share.model.GroupApp;
import lector.share.model.Language;
import lector.share.model.ReadingActivity;
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

	@Override
	public UserApp login(String requestUri) throws UserNotFoundException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		UserApp userApp = new UserApp();
		if (user != null) {
			try {
				userApp = loadUserByEmail(user.getEmail());
			} catch (GeneralException ge) {
				userApp = new UserApp();
				userApp.setLoggedIn(false);
				userApp.setLoginUrl(userService.createLoginURL(requestUri));
				userApp.setLogoutUrl(userService.createLogoutURL(requestUri));
				userApp.setEmail(user.getEmail());
				userApp.setIsAuthenticated(false);
				return userApp;
			}
			userApp.setLoggedIn(true);
			userApp.setLogoutUrl(userService.createLogoutURL(requestUri));
			userApp.setLoginUrl(userService.createLoginURL(requestUri));
		} else {

			userApp.setLoggedIn(false);
			userApp.setLoginUrl(userService.createLoginURL(requestUri));
			userApp.setLogoutUrl(userService.createLogoutURL(requestUri));
		}
		return userApp;
	}

	@Override
	public void saveUser(UserApp user) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			if (user.getId() == null) {
				entityManager.persist(user);
			} else {
				entityManager.merge(user);
			}

			entityTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(entityTransaction); // TODO utilizar
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

		String sql = "SELECT r FROM UserApp r WHERE r.email'=" + email + "'";
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
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			entityManager.createQuery(
					"DELETE FROM Student s WHERE s.id=" + studentId)
					.executeUpdate();
			entityTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(entityTransaction);
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
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			entityManager.createQuery(
					"DELETE FROM Professor s WHERE s.id=" + professorId)
					.executeUpdate();
			entityTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(entityTransaction);
			throw new GeneralException(
					"Exception in method deleteProfessorById" + e.getMessage(),
					e.getStackTrace());
		}
	}

	@Override
	public void saveGroup(GroupApp group) {
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			if (group.getId() == null) {
				entityManager.persist(group);
			} else {
				entityManager.merge(group);
			}

			entityTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(entityTransaction); // TODO utilizar
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
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try {
			entityTransaction.begin();
			entityManager.createQuery(
					"DELETE FROM GroupApp s WHERE s.id=" + groupId)
					.executeUpdate();
			entityTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(entityTransaction);
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
		// TODO Auto-generated method stub

	}

	@Override
	public List<Annotation> getAnnotationsByBookId(String bookId)
			throws GeneralException, AnnotationNotFoundException,
			NullParameterException, BookNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Annotation> getAnnotationsByPageNumbertAndUserId(
			Integer pageNumber, String bookId, Long studentId,
			Long readingActivityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Annotation> getAnnotationsByPageNumber(Integer pageNumber,
			String bookId, Long readingActivityId) throws GeneralException,
			AnnotationNotFoundException, NullParameterException,
			BookNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Annotation> getAnnotationsByIds(List<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Annotation> getAnnotationsByIdsAndAuthorsTeacher(
			List<Long> ids, List<Long> authorIds, Long Activity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Annotation> getAnnotationsByTeacherIds(List<Long> ids,
			Long readingActivityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Annotation> getAnnotationsByStudentIds(List<Long> ids,
			Long Student, Long readingActivityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Annotation> getAnnotationsByIdsAndAuthorsStudent(
			List<Long> ids, List<Long> authorIds, Long Activity, Long Student) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long deleteAnnotation(Annotation annotation)
			throws GeneralException, NullParameterException,
			AnnotationNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveAnnotationThread(AnnotationThread annotationThread) {
		// TODO Auto-generated method stub

	}

	@Override
	public Long deleteAnnotationThread(AnnotationThread annotationThread)
			throws GeneralException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AnnotationThread> getAnnotationThreadsByItsFather(
			Long annotationId, Long threadFatherId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book loadBookById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book loadFullBookInGoogle(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBooks(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBooks(String query, int start) {
		// TODO Auto-generated method stub
		return null;
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
	public Tag loadTagById(Long typeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tag loadTagByNameAndCatalogId(String typeName, Long catalogId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveTag(Tag typesys, Long typeCategoryId) {
		// TODO Auto-generated method stub

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
	public ReadingActivity loadReadingActivityById(Long readingActivityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteReadingActivity(Long readingActivityId)
			throws GeneralException, NullParameterException,
			AnnotationNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer removeReadingActivityAnnotations(Long readingActivity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReadingActivity getReadingActivityByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReadingActivity> loadReadingActivityByProfessorId(
			Long professorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveReadingActivity(ReadingActivity readingActivity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateReadingActivities() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveLanguage(Language language) {
		// TODO Auto-generated method stub

	}

	@Override
	public Long deleteLanguage(Long languageId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLanguagesNames() throws GeneralException,
			LanguageNotFoundException, NullParameterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Language> getLanguages() throws GeneralException,
			LanguageNotFoundException, NullParameterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Language loadLanguageById(Long languageId) {
		// TODO Auto-generated method stub
		return null;
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
	public void deleteBook(String bookId, Long userId) {
		// TODO Auto-generated method stub

	}

}
