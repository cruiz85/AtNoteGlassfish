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
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import org.mortbay.util.ajax.JSON;

import lector.client.book.reader.GWTService;
import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.client.DecendanceException;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.FileException;
import lector.client.catalogo.client.Folder;
import lector.client.catalogo.client.FolderException;
import lector.client.controler.Constants;
import lector.client.language.LanguageNotFoundException;
import lector.client.login.GroupNotFoundException;
import lector.client.login.UserNotFoundException;
import lector.client.reader.AnnotationNotFoundException;
import lector.client.reader.Book;
import lector.client.reader.BookNotFoundException;
import lector.client.reader.GeneralException;
import lector.client.reader.IlegalFolderFusionException;
import lector.client.reader.NullParameterException;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import lector.client.service.AnnotationSchema;
import lector.share.model.Annotation;
import lector.share.model.AnnotationThread;
import lector.share.model.LocalBook;
import lector.share.model.Catalogo;
import lector.share.model.Entry;
import lector.share.model.Professor;
import lector.share.model.Student;
import lector.share.model.Tag;
import lector.share.model.FolderDB;
import lector.share.model.GroupApp;
import lector.share.model.Language;
import lector.share.model.ReadingActivity;
import lector.share.model.TextSelector;
import lector.share.model.UserApp;

public class GWTServiceImpl extends RemoteServiceServlet implements GWTService {

	private static ArrayList<Long> ids;
	private static ArrayList<Long> annotationThreadIds;
	private static List<Long> sonIds; // used in schema generator
	@PersistenceContext(name = "BookReader11Abr01PU")
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;

	@Override
	public UserApp login(String requestUri) throws UserNotFoundException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		UserApp userApp = new UserApp();
		if (user != null) {

			userApp = loadUserByEmail(user.getEmail());
			if (userApp == null) { // PROBAR AQUI LANZANDO UNA EXCEPCION CON EL
				// URL COMO MENSAJE, PERO NO FUNCIONA, VER
				// QUE PASA
				/*
				 * Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.INFO
				 * , null, user.getEmail());
				 */
				userApp = new UserApp();
				userApp.setLoggedIn(false);
				userApp.setLoginUrl(userService.createLoginURL(requestUri));
				userApp.setLogoutUrl(userService.createLogoutURL(requestUri));
				userApp.setEmail(user.getEmail());
				userApp.setIsAuthenticated(false);
				return userApp;
			}

			makeAUtilArrayListLong(userApp);
			makeAUtilArrayListString(userApp);
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
	public void saveUser(UserApp user) {
		// TODO Auto-generated method stub

	}

	@Override
	public UserApp loadUserById(Long userId) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserApp loadUserByEmail(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> getStudentsByGroupId(Long groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> getStudents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long deleteStudentById(Long studentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Professor> getProfessors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long deleteProfessorById(Long professorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBookFromUser(String bookId, Long userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveGroup(GroupApp groupApp) {
		// TODO Auto-generated method stub

	}

	@Override
	public GroupApp loadGroupById(Long groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GroupApp> getGroupsByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long deleteGroup(Long groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUserAndGroupRelation(Long userId, Long groupId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void validUserAndGroupRelation(Long userId, Long groupId) {
		// TODO Auto-generated method stub

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
	public void saveFolderDB(Folder typeCategory, Long typeCategoryFatherId) {
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
	public AnnotationSchema getSchemaByCatalogId(Long catalogId) {
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
