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
import java.util.Iterator;

import java.util.Date;

import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import javax.persistence.Persistence;

import javax.transaction.UserTransaction;

import lector.client.book.reader.ExportService;
import lector.client.book.reader.GWTService;

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

import lector.share.model.Catalogo;
import lector.share.model.CatalogoNotFoundException;
import lector.share.model.Entry;
import lector.share.model.FolderDBNotFoundException;
import lector.share.model.NotAuthenticatedException;
import lector.share.model.NullParameterException;
import lector.share.model.Professor;
import lector.share.model.ProfessorNotFoundException;
import lector.share.model.ReadingActivityNotFoundException;
import lector.share.model.Relation;
import lector.share.model.RelationNotFoundException;
import lector.share.model.Student;
import lector.share.model.StudentNotFoundException;
import lector.share.model.Tag;
import lector.share.model.FolderDB;
import lector.share.model.GroupApp;
import lector.share.model.Language;
import lector.share.model.ReadingActivity;
import lector.share.model.TagNotFoundException;

import lector.share.model.UserApp;
import lector.share.model.UserNotFoundException;
import lector.share.model.client.AnnotationClient;
import lector.share.model.client.AnnotationThreadClient;
import lector.share.model.client.BookClient;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.EntryClient;
import lector.share.model.client.GoogleBookClient;
import lector.share.model.client.GroupClient;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.ReadingActivityClient;
import lector.share.model.client.StudentClient;
import lector.share.model.client.TypeCategoryClient;
import lector.share.model.client.TypeClient;
import lector.share.model.client.UserClient;

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

	private ExportService exportService = new ExportServiceImpl();

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
	public UserClient login(String userName, String password)
			throws UserNotFoundException, NotAuthenticatedException,
			GeneralException {

		UserApp user = loadUserByEmail(userName);

		if (!user.getPassword().equals(password)) {
			throw new NotAuthenticatedException(
					"Error in Login: User not Authenticated, please verify your login input");
		}
		return ServiceManagerUtils.produceUserClient(user);
	}

	public UserApp loadUserByName(String name) throws UserNotFoundException,
			GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		List<UserApp> list;
		String sql = "SELECT r FROM UserApp r WHERE r.name='" + name + "'";
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadUserByName: ", e)
			throw new GeneralException("Exception in method loadUserByName:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadUserById: ", e)
			throw new UserNotFoundException(
					"User not found in method loadUserByName");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return list.get(0);
	}

	@Override
	public void saveUser(UserClient userClient) throws GeneralException {
		if (userClient instanceof ProfessorClient) {
			saveProfessor((ProfessorClient) userClient);
		} else {
			saveStudent((StudentClient) userClient);
		}

	}

	private void saveStudent(StudentClient pClient) {
		boolean isNew = false;
		Student oldStudent = null;
		try {
			if (pClient.getId() != null) {
				oldStudent = findStudent(pClient.getId());
			} else {
				Date now = new Date();
				Calendar calendar = Calendar.getInstance();
				now = calendar.getTime();
				saveUser(new Student(pClient.getId(), pClient.getFirstName(),
						pClient.getLastName(), pClient.getEmail(),
						pClient.getPassword(), now));
				isNew = true;
			}

		} catch (StudentNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!isNew) {
			boolean isThereAChange = comparareStudents(oldStudent, pClient);
			if (isThereAChange) {
				try {
					saveUser(oldStudent);
				} catch (GeneralException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void saveProfessor(ProfessorClient pClient) {
		boolean isNew = false;
		Professor oldProfessor = null;
		try {
			if (pClient.getId() != null) {
				oldProfessor = findProfessor(pClient.getId());
			} else {
				Date now = new Date();
				Calendar calendar = Calendar.getInstance();
				now = calendar.getTime();
				saveUser(new Professor(pClient.getId(), pClient.getFirstName(),
						pClient.getLastName(), pClient.getEmail(),
						pClient.getPassword(), now));
				isNew = true;
			}

		} catch (ProfessorNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!isNew) {
			boolean isThereAChange = comparareProfessors(oldProfessor, pClient);
			if (isThereAChange) {
				try {
					saveUser(oldProfessor);
				} catch (GeneralException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
															// método de
															// logger
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

	}

	@Override
	public UserClient loadUserById(Long userId) throws UserNotFoundException,
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

		return ServiceManagerUtils.produceUserClient(list.get(0));
	}

	// @Override
	// public UserClient loadUserByEmail(String email)
	// throws UserNotFoundException, GeneralException {
	// EntityManager entityManager = emf.createEntityManager();
	// List<UserApp> list;
	// String sql = "SELECT r FROM UserApp r WHERE r.email='" + email + "'";
	// try {
	// list = entityManager.createQuery(sql).getResultList();
	// } catch (Exception e) {
	// // logger.error ("Exception in method loadUserByEmail: ", e)
	// throw new GeneralException("Exception in method loadUserByEmail:"
	// + e.getMessage(), e.getStackTrace());
	//
	// }
	// if (list == null || list.isEmpty()) {
	// // logger.error ("Exception in method loadUserById: ", e)
	// throw new UserNotFoundException(
	// "User not found in method loadUserByEmail");
	//
	// }
	// if (entityManager.isOpen()) {
	// entityManager.close();
	// }
	// return ServiceManagerUtils.produceUserClient(list.get(0));
	// }

	private UserApp loadUserByEmail(String email) throws UserNotFoundException,
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
		return list.get(0);
	}

	@Override
	public List<StudentClient> getStudentsByGroupId(Long groupId)
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
		return ServiceManagerUtils.produceStudentClients(list.get(0)
				.getParticipatingStudents());

	}

	@Override
	public List<StudentClient> getStudents() throws GeneralException,
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

		return ServiceManagerUtils.produceStudentClients(list);
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
	public List<ProfessorClient> getProfessors() throws GeneralException,
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

		return ServiceManagerUtils.produceProfessorClients(list);
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
	public void saveGroup(GroupClient groupClient) {
		GroupApp oldGroup = new GroupApp();
		if (groupClient.getId() != null) {
			try {
				oldGroup = findGroup(groupClient.getId());
			} catch (GroupNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		oldGroup.setName(groupClient.getName());
		saveGroup(oldGroup);
	}

	private void saveGroup(GroupApp group) {
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
	public GroupClient loadGroupById(Long groupId) throws GeneralException,
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
		return ServiceManagerUtils.produceGroupClient(list.get(0));
	}

	// TODO Maybe removed.
	@Override
	public List<GroupClient> getGroupsByUserId(Long userId)
			throws GeneralException {
		try {
			UserApp userApp = findUser(userId);
			if (userApp instanceof Professor) {
				return ServiceManagerUtils
						.produceGroupClients(((Professor) userApp).getGroups());
			} else {
				return ServiceManagerUtils
						.produceGroupClients(((Student) userApp)
								.getParticipatingGroups());
			}
		} catch (Exception e) {
			throw new GeneralException("Exception in method getGroupsByUserId");
		}

	}

	@Override
	public List<GroupClient> getGroupsByIds(List<Long> ids)
			throws GroupNotFoundException, GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		List<GroupApp> list;
		String sql = "SELECT r FROM GroupApp r WHERE r.id=" + ids.get(0);
		for (int i = 1; i < ids.size(); i++) {
			sql += "OR r.id=" + ids.get(i);
		}

		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupAppById: ", e)
			throw new GeneralException("Exception in method loadGroupAppById:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadGroupAppById: ", e)
			throw new GroupNotFoundException(
					"GroupApp not found in method loadGroupAppById");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return ServiceManagerUtils.produceGroupClients(list);
	}

	// TODO LANZAR EXCEPCIÓN
	private UserApp findUser(Long id) throws UserNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		UserApp a = entityManager.find(UserApp.class, id);
		if (a == null) {
			throw new UserNotFoundException(
					"User not found in method loadUserById");
		}
		entityManager.close();
		return a;
	}

	public UserApp findByEmail(String email) throws UserNotFoundException,
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
		return list.get(0);
	}

	// TODO LANZAR EXCEPCIÓN
	private GroupApp findGroup(Long id) throws GroupNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		GroupApp a = entityManager.find(GroupApp.class, id);
		if (a == null) {
			throw new GroupNotFoundException(
					"Group not found in method loadGroupById");
		}
		entityManager.close();
		return a;
	}

	// TODO LANZAR EXCEPCIÓN
	private Student findStudent(Long id) throws StudentNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		Student a = entityManager.find(Student.class, id);
		if (a == null) {
			throw new StudentNotFoundException(
					"Student not found in method loadStudentById");
		}
		entityManager.close();
		return a;
	}

	// TODO LANZAR EXCEPCIÓN
	private Annotation findAnnotation(Long id)
			throws AnnotationNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		Annotation a = entityManager.find(Annotation.class, id);
		if (a == null) {
			throw new AnnotationNotFoundException(
					"Annotation not found in method loadAnnotationById");
		}
		entityManager.close();
		return a;
	}

	// TODO LANZAR EXCEPCIÓN
	private AnnotationThread findAnnotationThread(Long id)
			throws AnnotationThreadNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		AnnotationThread a = entityManager.find(AnnotationThread.class, id);
		if (a == null) {
			throw new AnnotationThreadNotFoundException(
					"Annotation not found in method loadAnnotationById");
		}
		entityManager.close();
		return a;
	}

	private Tag findTag(Long id) throws TagNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		Tag a = entityManager.find(Tag.class, id);
		if (a == null) {
			throw new TagNotFoundException("Tag not found in method findTag");
		}
		entityManager.close();
		return a;
	}

	private FolderDB findFolderDB(Long id) throws FolderDBNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		FolderDB a = entityManager.find(FolderDB.class, id);
		if (a == null) {
			throw new FolderDBNotFoundException(
					"FolderDB not found in method loadFolderDBById");
		}
		entityManager.close();
		return a;
	}

	private Relation findRelation(Long fatherId, Long sonId)
			throws RelationNotFoundException, GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		List<Relation> list;
		String sql = "SELECT r FROM Relation r WHERE r.father.id=" + fatherId
				+ " AND r.child.id=" + sonId;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadRelationByName: ", e)
			throw new GeneralException(
					"Exception in method loadRelationByName:" + e.getMessage(),
					e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadRelationById: ", e)
			throw new RelationNotFoundException(
					"Relation not found in method loadRelation");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return list.get(0);
	}

	// TODO LANZAR EXCEPCIÓN
	private Professor findProfessor(Long id) throws ProfessorNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		Professor a = entityManager.find(Professor.class, id);
		if (a == null) {
			throw new ProfessorNotFoundException(
					"Professor not found in method loadProfessorById");
		}
		entityManager.close();
		return a;
	}

	// TODO LANZAR EXCEPCIÓN
	private Catalogo findCatalogo(Long id) throws CatalogoNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		Catalogo a = entityManager.find(Catalogo.class, id);
		if (a == null) {
			throw new CatalogoNotFoundException(
					"Catalogo not found in method loadCatalogoById");
		}
		entityManager.close();
		return a;
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
			GroupApp group = findGroup(groupId);
			Student student = findStudent(userId);
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
	public void removeStudentToBeValidated(Long userId, Long groupId)
			throws GeneralException {
		try {
			GroupApp group = findGroup(groupId);
			Student student = findStudent(userId);
			if (group.getRemainingStudents().contains(student)) {
				group.getRemainingStudents().remove(student);
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
			Student student = findStudent(userId);
			GroupApp group = findGroup(groupId);
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
	public void saveAnnotation(AnnotationClient annotationClient) {
		Annotation oldAnnotation;
		if (annotationClient.getId() != null) {
			try {
				oldAnnotation = findAnnotation(annotationClient.getId());
				seeEditionOnAnnotation(oldAnnotation, annotationClient);
				saveAnnotation(oldAnnotation);
			} catch (AnnotationNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void seeEditionOnAnnotation(Annotation annotation,
			AnnotationClient aClient) {
		boolean isUpdatability = false;
		boolean isVisibility = false;

		if (!annotation.getBookId().equals(aClient.getBookId())) {
			annotation.setBookId(aClient.getBookId());
		}
		if (!annotation.getComment().equals(aClient.getComment())) {
			annotation.setComment(aClient.getComment());
		}
		if (annotation.isEditable() != aClient.isEditable()) {
			annotation.setIsEditable(aClient.isEditable());
		}
		if (!annotation.getPageNumber().equals(aClient.getPageNumber())) {
			annotation.setPageNumber(aClient.getPageNumber());
		}

		if (annotation.getUpdatability() == 1) {
			isUpdatability = true;
		}
		if (annotation.getVisibility() == 1) {
			isVisibility = true;
		}
		if (isUpdatability != aClient.isUpdatability()) {
			short isUpdatable = 0;
			if (aClient.isUpdatability()) {
				isUpdatable = 1;
			}
			annotation.setUpdatability(isUpdatable);
		}
		if (isVisibility != aClient.isVisibility()) {
			short isVisble = 0;
			if (aClient.isVisibility()) {
				isVisble = 1;
			}
			annotation.setVisibility(isVisble);
		}
		List<Long> oldAnnotationTagIds = getTagIdsFromAnnotation(annotation);
		List<Long> aClientTags = getTagIdsFromAnnotationClient(aClient);
		try {
			checkChangesOnTags(annotation, oldAnnotationTagIds, aClientTags);
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		saveAnnotation(annotation);
	}

	private List<Long> getTagIdsFromAnnotation(Annotation a) {
		List<Long> ids = new ArrayList<Long>();
		for (Tag tag : a.getTags()) {
			ids.add(tag.getId());
		}
		return ids;
	}

	private List<Long> getTagIdsFromAnnotationClient(AnnotationClient a) {
		List<Long> ids = new ArrayList<Long>();
		for (TypeClient tag : a.getTags()) {
			ids.add(tag.getId());
		}
		return ids;
	}

	private void checkChangesOnTags(Annotation oldAnnotation, List<Long> aTags,
			List<Long> aClientTags) throws TagNotFoundException {

		for (Long id : aTags) {
			if (!aClientTags.contains(id)) {
				oldAnnotation.getTags().remove(findTag(id));
			}
		}

		for (Long id : aClientTags) {
			if (!aTags.contains(id)) {
				oldAnnotation.getTags().add(findTag(id));
			}
		}
	}

	private void saveAnnotation(Annotation annotation) {
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
	public List<AnnotationClient> getAnnotationsByBookId(Long activityId,
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
		return ServiceManagerUtils.produceAnnotationClients(list); // método
																	// esta
		// retornando null

	}

	@Override
	public List<AnnotationClient> getAnnotationsByPageNumbertAndUserId(
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

		return ServiceManagerUtils.produceAnnotationClients(list); // método
																	// esta
		// retornando null
	}

	@Override
	public List<AnnotationClient> getAnnotationsByPageNumber(
			Integer pageNumber, String bookId, Long readingActivityId)
			throws GeneralException, AnnotationNotFoundException {
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

		return ServiceManagerUtils.produceAnnotationClients(list); // método
																	// esta
		// retornando null
	}

	@Override
	public List<AnnotationClient> getAnnotationsByIds(List<Long> ids) {
		List<Annotation> annotations = new ArrayList<Annotation>();
		for (int i = 0; i < ids.size(); i++) {
			Annotation annotation = quickFind(ids.get(i));
			if (annotation != null) {
				annotations.add(annotation);
			}

		}
		return ServiceManagerUtils.produceAnnotationClients(annotations); // método
																			// esta
		// retornando null
	}

	private Annotation quickFind(Long id) {
		EntityManager entityManager = emf.createEntityManager();
		Annotation a = entityManager.find(Annotation.class, id);
		entityManager.close();
		return a;
	}

	// TODO que hace este método.
	@Override
	public List<AnnotationClient> getAnnotationsByIdsAndAuthorsTeacher(
			List<Long> ids, List<Long> authorIds, Long Activity) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO que hace este método.
	@Override
	public List<AnnotationClient> getAnnotationsByTeacherIds(List<Long> ids,
			Long readingActivityId) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO que hace este método.
	@Override
	public List<AnnotationClient> getAnnotationsByStudentIds(List<Long> ids,
			Long Student, Long readingActivityId) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO que hace este método.
	@Override
	public List<AnnotationClient> getAnnotationsByIdsAndAuthorsStudent(
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
	public void saveAnnotationThread(
			AnnotationThreadClient annotationThreadClient) {
		AnnotationThread oldAnnotationThread = new AnnotationThread();
		if (annotationThreadClient.getId() != null) {
			try {
				oldAnnotationThread = findAnnotationThread(annotationThreadClient
						.getId());

			} catch (AnnotationThreadNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		seeChangesInAnnotationThread(oldAnnotationThread,
				annotationThreadClient);
		saveAnnotationThread(oldAnnotationThread);
	}

	private void seeChangesInAnnotationThread(
			AnnotationThread annotationThread,
			AnnotationThreadClient aThreadClient) {
		if (!annotationThread.getComment().equals(aThreadClient.getComment())) {
			annotationThread.setComment(aThreadClient.getComment());
		}
		if (!annotationThread.getUserName().equals(aThreadClient.getUserName())) {
			annotationThread.setUserName(aThreadClient.getUserName());
		}

	}

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
	public List<AnnotationThreadClient> getAnnotationThreadsByItsFather(
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

		return ServiceManagerUtils.produceAnnotationThreadClients(list.get(0)
				.getSubThreads(), null);
	}

	@Override
	public GoogleBookClient loadFullBookInGoogle(String query) {
		GoogleBook googleBook = getBookInGoogleByISBN(query);
		String cleanGoogleBookId = googleBook.getUrl().substring(33, 45);
		googleBook
				.setWebLinks(getBookImageInGoole(getBookImageStringInGoogle(cleanGoogleBookId)));
		googleBook.setImagesPath(googleBook.getWebLinks().get(0));
		return ServiceManagerUtils.produceGoogleBookClient(googleBook);
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
	public List<GoogleBookClient> getGoogleBookClients(String query) {
		return ServiceManagerUtils
				.produceGoogleBookClients(getGoogleBooks(query));
	}

	@Override
	public void addBookToUser(BookClient bookClient, Long userId) {
		// TODO Auto-generated method stub

	}

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

	public List<GoogleBookClient> getBookClients(String query, int start) {
		return ServiceManagerUtils.produceGoogleBookClients(getBooks(query,
				start));
	}

	private List<GoogleBook> getBooks(String query, int start) {
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
	public void saveCatalog(CatalogoClient catalogClient) {
		// TODO Auto-generated method stub

	}

	@Override
	public CatalogoClient loadCatalogById(Long catalogId) {
		Catalogo catalogo;
		try {
			catalogo = findCatalogo(catalogId);
			CatalogoClient cClient = ServiceManagerUtils
					.produceCatalogoClient(catalogo);
			CatalogoGenerator.Start(cClient, catalogo);
			return cClient;
		} catch (CatalogoNotFoundException e) {

			e.printStackTrace();
		}
		return new CatalogoClient();
	}

	@Override
	public List<CatalogoClient> getCatalogs() {
		return null;

	}

	@Override
	public List<CatalogoClient> getVisbibleCatalogsByProfessorId(
			Long professorId) {

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
	public TypeClient loadTagById(Long tagId) throws TagNotFoundException,
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

		return ServiceManagerUtils.produceTypeClientLazy(list.get(0));
	}

	@Override
	public TypeClient loadTagByNameAndCatalogId(String tagName, Long catalogId)
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

		return ServiceManagerUtils.produceTypeClientLazy(list.get(0));
	}

	private void saveTag(Tag tag) {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			if (tag.getId() == null) {
				entityManager.persist(tag);
			} else {
				entityManager.merge(tag);
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
	public void saveType(TypeClient typesys, Long fatherEntry) {
		FolderDB father = null;
		try {
			Tag tag = new Tag();
			Catalogo catalogo = findCatalogo(typesys.getCatalog().getId());
			father = findFolderDB(fatherEntry);
			tag.setCatalog(catalogo);
			tag.setName(typesys.getName());
			Relation relation = new Relation(father, tag);
			father.getRelations().add(relation);
		} catch (CatalogoNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FolderDBNotFoundException fnfe) {
			fnfe.printStackTrace();
		}

		saveFolderDB(father);

	}

	@Override
	public void deleteTag(Long tagId, Long fatherId) throws GeneralException {
		try {
			FolderDB folderDB = findFolderDB(fatherId);
			Relation relation = findRelation(fatherId, tagId);
			folderDB.getRelations().remove(relation);
			saveFolderDB(folderDB);
		} catch (FolderDBNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (RelationNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		try {
			FolderDB typeCategoryTo = findFolderDB(typeCategoryToId);
			Relation relation = loadRelationByFatherAndSonId(
					typeCategoryFromId, typeId);
			relation.setFather(typeCategoryTo);
			saveRelation(relation);
		} catch (FolderDBNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RelationNotFoundException rnfe) {
			rnfe.printStackTrace();
		}

	}

	private void saveRelation(Relation relation) {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			if (relation.getId() == null) {
				entityManager.persist(relation);
			} else {
				entityManager.merge(relation);
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

	private Relation loadRelationByFatherAndSonId(Long fatherId, Long sonId)
			throws GeneralException, RelationNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<Relation> list;

		String sql = "SELECT r FROM Relation r WHERE r.father.id=" + fatherId
				+ " AND r.child.id=" + sonId;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadRelationById: ", e)
			throw new GeneralException("Exception in method loadRelationById:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadRelationById: ", e)
			throw new RelationNotFoundException(
					"Relation not found in method loadRelationById");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list.get(0);

	}

	@Override
	public List<TypeClient> getTagsByNameAndCatalogId(List<String> tagNames,
			Long catalogId) throws GeneralException {

		EntityManager entityManager = emf.createEntityManager();
		List<Tag> list;
		String sql = "SELECT r FROM Tag r WHERE r.name='" + tagNames.get(0)
				+ "' ";
		for (int i = 1; i < ids.size(); i++) {
			sql += "OR r.name='" + ids.get(i) + "'";
		}
		sql += " AND r.catalogId=" + catalogId;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadTagById: ", e)
			throw new GeneralException("Exception in method loadTagById:"
					+ e.getMessage(), e.getStackTrace());

		}

		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return ServiceManagerUtils.produceTypeClientsLazy(list);

	}

	@Override
	public List<TypeClient> getTagsByIds(List<Long> typeIds)
			throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		List<Tag> list;
		String sql = "SELECT r FROM Tag r WHERE r.id=" + typeIds.get(0);
		for (int i = 1; i < typeIds.size(); i++) {
			sql += "OR r.id=" + typeIds.get(i);
		}

		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadTagById: ", e)
			throw new GeneralException("Exception in method loadTagById:"
					+ e.getMessage(), e.getStackTrace());

		}

		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return ServiceManagerUtils.produceTypeClientsLazy(list);
	}

	@Override
	public List<String> getTagNamesByIds(List<Long> typeIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void renameType(Long typeIds, String newTagName) {
		Tag tag = null;
		try {
			tag = findTag(typeIds);
			tag.setName(newTagName);
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		saveTag(tag);

	}

	@Override
	public void addChildEntry(Long entryId, Long fatherFolderDBId) {
		try {
			FolderDB father = findFolderDB(fatherFolderDBId);
			Tag tag = findTag(entryId);
			Relation relation = new Relation(father, tag);
			saveRelation(relation);

		} catch (FolderDBNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagNotFoundException tnfe) {
			tnfe.printStackTrace();
		}

	}

	@Override
	public TypeCategoryClient loadFolderDBById(Long typeCategoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeCategoryClient loadFolderDBByNameAndCatalogId(
			String FolderDBName, Long catalogId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteTypeCategory(Long typeCategoryId, Long fatherFolderDBId)
			throws GeneralException {
		try {
			FolderDB folderDB = findFolderDB(fatherFolderDBId);
			Relation relation = findRelation(fatherFolderDBId, typeCategoryId);
			folderDB.getRelations().remove(relation);
			saveFolderDB(folderDB);
		} catch (FolderDBNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (RelationNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<EntryClient> getSonsFromFolderDB(Long typeCategoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void renameTypeCategory(Long typeCategoryId, String newFolderDBName) {
		FolderDB folderDB = null;
		try {
			folderDB = findFolderDB(typeCategoryId);
			folderDB.setName(newFolderDBName);
		} catch (FolderDBNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		saveFolderDB(folderDB);

	}

	@Override
	public void saveTypeCategory(TypeCategoryClient typeCategoryClient,
			Long fatherEntry) {
		FolderDB father = null;
		try {
			FolderDB folderDB = new FolderDB();
			Catalogo catalogo = findCatalogo(typeCategoryClient.getCatalog()
					.getId());
			father = findFolderDB(fatherEntry);
			folderDB.setCatalog(catalogo);
			folderDB.setName(typeCategoryClient.getName());
			Relation relation = new Relation(father, folderDB);
			father.getRelations().add(relation);
		} catch (CatalogoNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FolderDBNotFoundException fdne) {
			fdne.printStackTrace();
		}

		saveFolderDB(father);

	}

	private void saveFolderDB(FolderDB folder) {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			if (folder.getId() == null) {
				entityManager.persist(folder);
			} else {
				entityManager.merge(folder);
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
	public void moveFolderDB(Long typeCategoryId, Long typeCategoryFromId,
			Long typeCategoryToId) throws GeneralException, DecendanceException {
		try {
			FolderDB typeCategoryTo = findFolderDB(typeCategoryToId);
			Relation relation = loadRelationByFatherAndSonId(
					typeCategoryFromId, typeCategoryId);
			relation.setFather(typeCategoryTo);
			saveRelation(relation);
		} catch (FolderDBNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RelationNotFoundException rnfe) {
			rnfe.printStackTrace();
		}
	}

	@Override
	public void fusionFolder(Long typeCategoryFromId, Long typeCategoryToId)
			throws IlegalFolderFusionException, GeneralException {

	}

	@Override
	public ReadingActivityClient loadReadingActivityById(Long readingActivityId)
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

		return ServiceManagerUtils.produceReadingActivityClient(list.get(0));
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
	public List<ReadingActivityClient> getReadingActivitiesByStudentId(
			Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReadingActivityClient> getReadingActivitiesByProfessorId(
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

		return ServiceManagerUtils.produceReadingActivityClients(list);
	}

	// TODO HACER ESTE MÉTODO CUANDO SE HAGA EL CATÁLOGO
	@Override
	public void saveReadingActivity(ReadingActivityClient readingActivityClient)
			throws GeneralException {
		// EntityManager entityManager = emf.createEntityManager();
		//
		// try {
		// userTransaction.begin();
		// if (readingActivity.getId() == null) {
		// entityManager.persist(readingActivity);
		// } else {
		// entityManager.merge(readingActivity);
		// }
		//
		// userTransaction.commit();
		// } catch (Exception e) {
		// ServiceManagerUtils.rollback(userTransaction);
		// }
		// if (entityManager.isOpen()) {
		// entityManager.close();
		// }

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
		if (list == null) {
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
		if (list == null) {
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
	public UserClient loginAuxiliar(String userEmail)
			throws UserNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBookById(Long id) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			entityManager.createQuery("DELETE FROM Book s WHERE s.id=" + id);
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException("Exception in method deleteBookById"
					+ e.getMessage(), e.getStackTrace());
		}
	}

	@Override
	public BookClient loadBookClientById(Long id) throws BookNotFoundException,
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

		return ServiceManagerUtils.produceBookClient(list.get(0));
	}

	@Override
	public List<BookClient> getBookClientsByIds(List<Long> ids)
			throws BookNotFoundException, GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		List<Book> list;
		String sql = "SELECT r FROM Book r WHERE r.id=" + ids.get(0);
		for (int i = 1; i < ids.size(); i++) {
			sql += "OR r.id=" + ids.get(i);
		}

		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadBookById: ", e)
			throw new GeneralException("Exception in method loadBookById:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadBookById: ", e)
			throw new BookNotFoundException(
					"Book not found in method loadBookById");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return ServiceManagerUtils.produceBookClients(list);
	}

	private boolean seeEditionOnRemainingStudents(
			List<Long> oldRemainingStudents,
			List<Long> remainingStudentFromClients) {

		boolean isThereAChange = false;
		List<Long> deletedItems = new ArrayList<Long>();
		List<Long> newItems = new ArrayList<Long>();

		for (Long oldStudentId : oldRemainingStudents) { // busca por ids
															// eliminados
			if (!remainingStudentFromClients.contains(oldStudentId)) {
				deletedItems.add(oldStudentId);
				isThereAChange = true;
			}
		}

		return isThereAChange;
	}

	private boolean comparareStudents(Student oldStudent,
			StudentClient studentFromClient) {
		boolean isThereAChange = false;
		if (!oldStudent.getEmail().equals(studentFromClient.getEmail())) {
			isThereAChange = true;
			oldStudent.setEmail(studentFromClient.getEmail());
		} else if ((!oldStudent.getFirstName().equals(
				studentFromClient.getFirstName()))) {
			isThereAChange = true;
			oldStudent.setFirstName(studentFromClient.getFirstName());
		} else if ((!oldStudent.getLastName().equals(
				studentFromClient.getLastName()))) {
			isThereAChange = true;
			oldStudent.setLastName(studentFromClient.getLastName());
		} else if ((!oldStudent.getPassword().equals(
				studentFromClient.getPassword()))) {
			isThereAChange = true;
			oldStudent.setPassword(studentFromClient.getPassword());
		} else if ((oldStudent.getPassword().equals(studentFromClient
				.getPassword()))) {
			isThereAChange = true;
			oldStudent.setPassword(studentFromClient.getPassword());
		}
		return isThereAChange;
	}

	private boolean comparareProfessors(Professor oldProfessor,
			ProfessorClient professorFromClient) {
		boolean isThereAChange = false;
		if (!oldProfessor.getEmail().equals(professorFromClient.getEmail())) {
			isThereAChange = true;
			oldProfessor.setEmail(professorFromClient.getEmail());
		} else if ((!oldProfessor.getFirstName().equals(
				professorFromClient.getFirstName()))) {
			isThereAChange = true;
			oldProfessor.setFirstName(professorFromClient.getFirstName());
		} else if ((!oldProfessor.getLastName().equals(
				professorFromClient.getLastName()))) {
			isThereAChange = true;
			oldProfessor.setLastName(professorFromClient.getLastName());
		} else if ((!oldProfessor.getPassword().equals(
				professorFromClient.getPassword()))) {
			isThereAChange = true;
			oldProfessor.setPassword(professorFromClient.getPassword());
		} else if ((oldProfessor.getPassword().equals(professorFromClient
				.getPassword()))) {
			isThereAChange = true;
			oldProfessor.setPassword(professorFromClient.getPassword());
		}
		return isThereAChange;
	}

	private boolean isDescendant(Entry actDescentSource, FolderDB destiny) {
		if (actDescentSource instanceof Tag)
			return false;
		else {
			FolderDB actDescendantSourceFolder = (FolderDB) actDescentSource;
			List<Relation> relacionesActDescendantSourceFolder = actDescendantSourceFolder
					.getRelations();
			for (int i = 0; i < relacionesActDescendantSourceFolder.size(); i++) {
				if (relacionesActDescendantSourceFolder.get(i).getChild()
						.getId().equals(destiny.getId()))
					return true;
			}
			boolean sons = false;
			for (int i = 0; i < relacionesActDescendantSourceFolder.size(); i++) {
				sons = sons
						|| isDescendant(relacionesActDescendantSourceFolder
								.get(i).getChild(), destiny);

			}
			return sons;
		}

	}

}
