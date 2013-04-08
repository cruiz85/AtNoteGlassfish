package lector.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import lector.client.book.reader.ExportService;
import lector.client.book.reader.GWTService;
import lector.client.controler.Constants;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import lector.share.model.Annotation;
import lector.share.model.AnnotationNotFoundException;
import lector.share.model.AnnotationThread;
import lector.share.model.AnnotationThreadNotFoundException;
import lector.share.model.Book;
import lector.share.model.BookNotFoundException;
import lector.share.model.DecendanceException;
import lector.share.model.EntryNotFoundException;
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
import lector.share.model.Template;
import lector.share.model.TemplateNotFoundException;
import lector.share.model.TextSelector;
import lector.share.model.TwinBrotherException;
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
import lector.share.model.client.RemoteBookClient;
import lector.share.model.client.StudentClient;
import lector.share.model.client.TextSelectorClient;
import lector.share.model.client.TypeCategoryClient;
import lector.share.model.client.TypeClient;
import lector.share.model.client.UserClient;

public class GWTServiceImpl extends RemoteServiceServlet implements GWTService {

	private String PERSISTENCE_UNIT_NAME = "System";
	private EntityManagerFactory emf = Persistence
			.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	@Resource
	UserTransaction userTransaction;

	private ExportService exportService = new ExportServiceImpl();

	public GWTServiceImpl() {
		super();
	}

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

		if (user == null) {
			throw new UserNotFoundException(
					"The email address provided does not correspond to any of our records, please verify it");
		}

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
	public void saveUser(UserClient userClient) throws GeneralException,
			UserNotFoundException {

		if (userClient instanceof ProfessorClient) {
			saveProfessor((ProfessorClient) userClient);
		} else {
			saveStudent((StudentClient) userClient);
		}

	}

	@Override
	public boolean updateUser(Long userId, String password)
			throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		UserApp user = entityManager.find(UserApp.class, userId);
		boolean isTheRealUser = true;
		if (!user.getPassword().equals(password)) {
			isTheRealUser = false;
		} else {
			saveUser(user);
		}
		return isTheRealUser;
	}

	@Override
	public boolean deleteUserWithPassword(Long userId, String password)
			throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		UserApp user = entityManager.find(UserApp.class, userId);
		boolean isTheRealUser = true;
		if (!user.getPassword().equals(password)) {
			isTheRealUser = false;
		} else {
			deleteUser(user);
		}
		return isTheRealUser;
	}

	private void deleteUser(UserApp user) throws GeneralException {
		if (user instanceof Professor) {
			deleteProfessorById(user.getId());
		} else {
			deleteStudentById(user.getId());
		}
	}

	private void saveStudent(StudentClient pClient)
			throws UserNotFoundException, GeneralException {
		boolean isNew = false;
		Student oldStudent = null;
		UserApp user = null;
		try {
			if (pClient.getId() != null) {
				oldStudent = findStudent(pClient.getId());
			} else {

				try {
					user = loadUserByEmail(pClient.getEmail());
				} catch (UserNotFoundException e) {
					user = null;
				}

				if (user != null) {
					throw new GeneralException(
							"Email already registered in the application");
				}
				Date now = new Date();
				Calendar calendar = Calendar.getInstance();
				now = calendar.getTime();
				String confirmationCode = generateCode();
				saveUser(new Student(pClient.getId(), pClient.getFirstName(),
						pClient.getLastName(), pClient.getEmail(),
						pClient.getPassword(), now, confirmationCode));
				isNew = true;
				sendEmail(pClient.getEmail(), confirmationCode);
			}

		} catch (StudentNotFoundException e) {
			e.printStackTrace();
			throw new GeneralException(
					"error when saving user on private method saveStudent");

		}
		if (!isNew) {
			boolean isThereAChange = comparareStudents(oldStudent, pClient);
			if (isThereAChange) {
				try {
					saveUser(oldStudent);
				} catch (GeneralException e) {

					e.printStackTrace();
				}
			}
		}
	}

	private void saveProfessor(ProfessorClient pClient)
			throws UserNotFoundException {
		boolean isNew = false;
		Professor oldProfessor = null;
		UserApp user = null;
		try {
			if (pClient.getId() != null) {
				oldProfessor = findProfessor(pClient.getId());
			} else {
				try {
					user = loadUserByEmail(pClient.getEmail());
				} catch (UserNotFoundException e) {
					user = null;
				}
				if (user != null) {
					throw new GeneralException(
							"Email already registered in the application");
				}
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
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException(
					"Error in saving user, the transaction will be rolledBacked    ");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

	}

	public void saveBook(Book book) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			if (book.getId() == null) {
				entityManager.persist(book);
			} else {
				entityManager.merge(book);
			}
			entityManager.merge(book.getProfessor());
			entityManager.flush();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException(
					"Error in saving book, the transaction will be rolledBacked");

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
	public List<StudentClient> getUsersByGroupId(Long groupId)
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
		if (list == null) {
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
		if (list == null) {
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
			Student student = entityManager.find(Student.class, studentId);
			userTransaction.begin();

			entityManager.remove(student);
			student.getParticipatingGroups().size();
			for (GroupApp group : student.getParticipatingGroups()) {
				group.getParticipatingStudents().remove(student);
				entityManager.merge(group);
			}
			student.setParticipatingGroups(null);
			entityManager.flush();
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
		if (list == null) {
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
	public List<ProfessorClient> getProfessorsAnnotatorsByActivityId(
			Long activityId) throws GeneralException, UserNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<UserApp> list;
		List<Professor> professorList = new ArrayList<Professor>();
		String sql = "SELECT a.creator FROM Annotation a WHERE a.activity.id="
				+ activityId;
		try {
			list = entityManager.createQuery(sql).getResultList();
			if (list == null) {
				// logger.error ("Exception in method loadGroupById: ", e)
				throw new UserNotFoundException(
						"Professor not found in method getProfessors");

			}
			for (UserApp user : list) {
				if (user instanceof Professor)
					professorList.add((Professor) user);
			}
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupByEmail: ", e)
			throw new GeneralException("Exception in method getProfessors:"
					+ e.getMessage(), e.getStackTrace());

		}

		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return ServiceManagerUtils.produceProfessorClients(professorList);
	}

	// TODO QUEDE AQUI
	@Override
	public void deleteProfessorById(Long professorId) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			Professor professor = entityManager.find(Professor.class,
					professorId);
			userTransaction.begin();
			professor.setAnnotations(null);
			professor.setBooks(null);
			professor.setGroups(null);
			professor.setReadingActivities(null);
			professor.setTemplates(null);

			entityManager.remove(professor);
			entityManager.flush();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException(
					"Exception in method deleteProfessorById" + e.getMessage(),
					e.getStackTrace());
		}
	}

	@Override
	public void saveGroup(GroupClient groupClient) throws GeneralException {
		GroupApp oldGroup;

		try {
			if (groupClient.getId() != null) {
				oldGroup = findGroup(groupClient.getId());
				oldGroup.setName(groupClient.getName());

			} else {

				Professor professor = findProfessor(groupClient.getProfessor()
						.getId());
				oldGroup = new GroupApp(groupClient.getName(), professor);
				professor.getGroups().add(oldGroup);
			}
		} catch (GroupNotFoundException e) {

			throw new GeneralException(
					"Group not found, no entity will not be persisted");
		} catch (ProfessorNotFoundException e) {
			throw new GeneralException(
					"Professor not found, the entity will not be persisted");
		}
		saveGroup(oldGroup);
	}

	private void saveGroup(GroupApp group) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		try {
			userTransaction.begin();
			if (group.getId() == null) {
				entityManager.persist(group);
				entityManager.merge(group.getProfessor());
			} else {
				entityManager.merge(group);
				for (Student student : group.getParticipatingStudents()) {
					entityManager.merge(student);
				}
			}

			entityManager.flush();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException(
					"Error when saving the Group, the transaction will be rolled back and no entity will    be persisted");
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
			sql += " OR r.id=" + ids.get(i);
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

	// TODO LANZAR EXCEPCI�N
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

	private Language findLanguage(Long id) throws LanguageNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		Language a = entityManager.find(Language.class, id);
		if (a == null) {
			throw new LanguageNotFoundException(
					"Language not found in method loadLanguageById");
		}
		entityManager.close();
		return a;
	}

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

	// TODO LANZAR EXCEPCI�N
	public Annotation findAnnotation(Long id)
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

	// TODO LANZAR EXCEPCI�N
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

	private Book findBook(Long id) throws BookNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		Book a = entityManager.find(Book.class, id);
		if (a == null) {
			throw new BookNotFoundException(
					"Book not found in method loadBookById");
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

	private Relation findRelation(Long id) throws RelationNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		Relation a = entityManager.find(Relation.class, id);
		if (a == null) {
			throw new RelationNotFoundException(
					"Relation not found in method loadRelationoById");
		}
		entityManager.close();
		return a;
	}

	// TODO LANZAR EXCEPCI�N
	public Professor findProfessor(Long id) throws ProfessorNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		Professor a = entityManager.find(Professor.class, id);
		if (a == null) {
			throw new ProfessorNotFoundException(
					"Professor not found in method loadProfessorById");
		}
		entityManager.close();
		return a;
	}

	// TODO LANZAR EXCEPCI�N
	private Catalogo findCatalogo(Long id) throws CatalogoNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getEntityManagerFactory().getCache().evictAll();
		Catalogo a = entityManager.find(Catalogo.class, id);
		if (a == null) {
			throw new CatalogoNotFoundException(
					"Catalogo not found in method findCatalogo");
		}
		entityManager.close();
		return a;
	}

	@Override
	public void deleteGroup(Long groupId) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			GroupApp group = entityManager.find(GroupApp.class, groupId);
			userTransaction.begin();
			group.getProfessor().getGroups().size();
			group.getProfessor().getGroups().remove(group);
			entityManager.merge(group.getProfessor());
			group.setProfessor(null);

			group.getParticipatingStudents().size();
			for (Student student : group.getParticipatingStudents()) {
				student.getParticipatingGroups().remove(group);
			}
			group.setParticipatingStudents(null);
			entityManager.flush();
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
			group.getRemainingStudents().size();
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
	public void removeStudentsToBeValidated(List<Long> ids, Long groupId)
			throws GeneralException {
		try {
			GroupApp group = findGroup(groupId);
			for (int i = 0; i < group.getRemainingStudents().size(); i++) {
				Student remaining = group.getRemainingStudents().get(i);
				if (ids.contains(remaining.getId())) {
					group.getRemainingStudents().remove(remaining);
				}
			}

			saveGroup(group);
		} catch (Exception e) {
			throw new GeneralException(
					"Error in method addStudentToBeValidated");
		}

	}

	@Override
	public void removeUserParticipatingInGroup(Long userId, Long groupId)
			throws GeneralException {
		try {
			GroupApp group = findGroup(groupId);
			UserApp user = findStudent(userId);
			group.getParticipatingStudents().size();
			if (group.getParticipatingStudents().contains(user)) {
				group.getParticipatingStudents().remove(user);
			}
			saveGroup(group);
		} catch (Exception e) {
			throw new GeneralException(
					"Error in method addStudentToBeValidated");
		}

	}

	@Override
	public void validateStudentsToBeInGroup(List<Long> userIds, Long groupId)
			throws GeneralException {
		try {
			GroupApp group = findGroup(groupId);

			for (int i = 0; i < group.getRemainingStudents().size(); i++) {
				Student remaining = group.getRemainingStudents().get(i);

				if (userIds.contains(remaining.getId())) {
					group.getRemainingStudents().remove(remaining);
					group.getParticipatingStudents().add(remaining);
					remaining.getParticipatingGroups().add(group);
				}

				saveGroup(group);
			}

		} catch (GroupNotFoundException e) {
			throw new GeneralException(
					"Group not found, no entity will be persisted",
					e.getStackTrace());
		}

	}

	@Override
	public void validStudentToBeInGroup(Long userId, Long groupId)
			throws GeneralException {
		try {
			Student student = findStudent(userId);
			GroupApp group = findGroup(groupId);
			group.getRemainingStudents().size();
			if (group.getRemainingStudents().contains(student)) {
				group.getRemainingStudents().remove(student);
			} else {
				throw new GeneralException(
						"Hey!!! this user was not on the list to be validated from: the remainingList");
			}
			group.getParticipatingStudents().size();
			if (!group.getParticipatingStudents().contains(student)) {
				group.getParticipatingStudents().add(student);
				student.getParticipatingGroups().add(group);
			}
			saveGroup(group);

		} catch (Exception e) {
			throw new GeneralException(
					"Error in method validStudentToBeInGroup");
		}

	}

	@Override
	public void saveAnnotation(AnnotationClient annotationClient)
			throws GeneralException {
		Annotation oldAnnotation;
		if (annotationClient.getId() != null) {
			try {
				oldAnnotation = findAnnotation(annotationClient.getId());
				seeEditionOnAnnotation(oldAnnotation, annotationClient);
				saveAnnotation(oldAnnotation);
			} catch (AnnotationNotFoundException e) {
				throw new GeneralException(
						"Annotation not found, no entity will be persisted",
						e.getStackTrace());
			}

		} else {

			try {
				UserApp creator = findUser(annotationClient.getCreator()
						.getId());
				ReadingActivity activity = findReadingActivity(annotationClient
						.getActivity());
				List<TextSelector> textSelectors = reproduceTextSelectorsFromClient(annotationClient
						.getTextSelectors());
				short visibility = (annotationClient.isVisibility() ? (short) 1
						: 0);
				short updatability = (annotationClient.isUpdatability() ? (short) 1
						: 0);
				List<Tag> tags = getTagsByTypes(annotationClient.getTags());

				oldAnnotation = new Annotation(creator, activity,
						textSelectors, annotationClient.getComment(),
						findBook(annotationClient.getBookId()), visibility,
						updatability, annotationClient.getPageNumber(), tags,
						annotationClient.isEditable());
				creator.getAnnotations().add(oldAnnotation);
				activity.getAnnotations().add(oldAnnotation);
				for (Tag tag : tags) {
					tag.getAnnotations().add(oldAnnotation);
				}

				saveAnnotation(oldAnnotation);
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ReadingActivityNotFoundException e) {
				e.printStackTrace();
			} catch (BookNotFoundException e) {
				e.printStackTrace();
			} catch (GeneralException e) {
				e.printStackTrace();
			}

		}
	}

	private List<Tag> getTagsByTypes(List<TypeClient> typeClients)
			throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		List<Tag> list;
		String sql = "SELECT r FROM Tag r WHERE r.id="
				+ typeClients.get(0).getId();
		for (int i = 1; i < typeClients.size(); i++) {
			sql += " OR r.id=" + typeClients.get(i).getId();
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
		return list;
	}

	private List<TextSelector> reproduceTextSelectorsFromClient(
			List<TextSelectorClient> t) {
		List<TextSelector> textSelectors = new ArrayList<TextSelector>();
		for (TextSelectorClient textSelectorClient : t) {
			textSelectors.add(new TextSelector(textSelectorClient.getX(),
					textSelectorClient.getY(), textSelectorClient.getWidth(),
					textSelectorClient.getHeight()));
		}

		return textSelectors;
	}

	private List<Long> getSonsCatalog(Catalogo cata) {
		List<Long> children = new ArrayList<Long>();
		for (Entry relation : cata.getEntries()) {
			children.add(relation.getId());
		}
		return children;
	}

	private List<Long> getSons(FolderDB folder) {
		List<Long> children = new ArrayList<Long>();
		for (Relation relation : folder.getRelations()) {
			children.add(relation.getChild().getId());
		}
		return children;
	}

	private List<Tag> getTagIdsByCatalogId(Long catalogId)
			throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		List<Tag> list;
		String sql = "SELECT r FROM Tag r WHERE r.catalog.id=" + catalogId;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadUserByEmail: ", e)
			throw new GeneralException("Exception in method loadUserByEmail:"
					+ e.getMessage(), e.getStackTrace());

		}

		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list;
	}

	// private List<Long> getTagIds(List<Tag> tags) {
	// List<Long> ids = new ArrayList<Long>();
	// for (Entry folder : tags) {
	// ids.add(folder.getId());
	// }
	// return ids;
	// }
	//
	//
	// private List<Long> getFolderIds(List<FolderDB> folders) {
	// List<Long> ids = new ArrayList<Long>();
	// for (Entry folder : folders) {
	// ids.add(folder.getId());
	// }
	// return ids;
	// }
	//
	// private List<FolderDB> getFolderIdsByCatalogId(Long catalogId)
	// throws GeneralException {
	// EntityManager entityManager = emf.createEntityManager();
	// List<FolderDB> list;
	// String sql = "SELECT r FROM FolderDB r WHERE r.catalog.id=" + catalogId;
	// try {
	// list = entityManager.createQuery(sql).getResultList();
	// } catch (Exception e) {
	// // logger.error ("Exception in method loadUserByEmail: ", e)
	// throw new GeneralException("Exception in method loadUserByEmail:"
	// + e.getMessage(), e.getStackTrace());
	//
	// }
	//
	// if (entityManager.isOpen()) {
	// entityManager.close();
	// }
	//
	// return list;
	// }

	private void seeEditionOnAnnotation(Annotation annotation,
			AnnotationClient aClient) throws GeneralException {
		boolean isUpdatability = false;
		boolean isVisibility = false;

		// NO SE COMPRUEBA EL LIBRO PORQUE EN UNA EDICION ES JAM�S CAMBIAR� EL
		// LIBRO
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
			throw new GeneralException(
					"no Tag found the annotation will not be persisted",
					e.getStackTrace());
		}
		// saveAnnotation(annotation);
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

	// CODIGO NUEVO
	private void checkChangesOnTags(Annotation oldAnnotation, List<Long> aTags,
			List<Long> aClientTags) throws TagNotFoundException {

		for (Long id : aTags) {
			if (!aClientTags.contains(id)) {
				Tag tag = findTag(id);
				oldAnnotation.getTags().size();
				oldAnnotation.getTags().remove(tag);
				tag.getAnnotations().remove(oldAnnotation);

			}
		}

		for (Long id : aClientTags) {
			if (!aTags.contains(id)) {
				Tag tag = findTag(id);
				oldAnnotation.getTags().size();
				oldAnnotation.getTags().add(tag);
				tag.getAnnotations().add(oldAnnotation);
			}
		}
	}

	private void saveAnnotation(Annotation annotation) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		now = calendar.getTime();
		annotation.setCreatedDate(now);
		try {
			userTransaction.begin();
			if (annotation.getId() == null) {
				entityManager.persist(annotation);
				entityManager.merge(annotation.getActivity());
				entityManager.merge(annotation.getCreator());
			} else {
				entityManager.merge(annotation);
			}
			for (Tag tag : annotation.getTags()) {
				entityManager.merge(tag);
			}
			entityManager.flush();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException(
					"Erron when saving the annotation no entity will be persisted",
					e.getStackTrace());
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

		String sql = "SELECT r FROM Annotation r WHERE r.activity.id="
				+ activityId + " AND r.activity.book.ISBN='" + bookId + "'";
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
		return ServiceManagerUtils.produceAnnotationClients(list);

	}

	@Override
	public List<AnnotationClient> getAnnotationsByPageNumbertAndUserId(
			Integer pageNumber, String bookId, Long studentId,
			Long readingActivityId) throws GeneralException,
			AnnotationNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<Annotation> list;

		String sql = "SELECT r FROM Annotation r WHERE r.activity.id="
				+ readingActivityId + " AND r.activity.book.ISBN='" + bookId
				+ "' AND r.pageNumber=" + pageNumber + " AND (r.creator.id="
				+ studentId + " OR r.visibility=1)";
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupByEmail: ", e)
			throw new GeneralException("Exception in method loadGroupByEmail:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new AnnotationNotFoundException(
					"Group not found in method loadGroupByEmail");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return ServiceManagerUtils.produceAnnotationClients(list); // m�todo
																	// esta
		// retornando null
	}

	@Override
	public List<AnnotationClient> getAnnotationsByPageNumber(
			Integer pageNumber, String bookId, Long readingActivityId)
			throws GeneralException, AnnotationNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<Annotation> list;

		String sql = "SELECT r FROM Annotation r WHERE r.activity.id="
				+ readingActivityId + " AND r.activity.book.ISBN='" + bookId
				+ "' AND r.pageNumber=" + pageNumber;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupByEmail: ", e)
			throw new GeneralException("Exception in method loadGroupByEmail:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new AnnotationNotFoundException(
					"Group not found in method loadGroupByEmail");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return ServiceManagerUtils.produceAnnotationClients(list);
	}

	@Override
	public List<AnnotationClient> getAnnotationsByIds(List<Long> ids)
			throws GeneralException, GroupNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<Annotation> list;
		String sql = "SELECT r FROM Annotation r WHERE r.id=" + ids.get(0);
		for (int i = 1; i < ids.size(); i++) {
			sql += " OR r.id=" + ids.get(i);
		}

		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadAnnotationById: ", e)
			throw new GeneralException(
					"Exception in method loadAnnotationById:" + e.getMessage(),
					e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadAnnotationById: ", e)
			throw new GroupNotFoundException(
					"Annotation not found in method loadAnnotationById");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return ServiceManagerUtils.produceAnnotationClients(list);
	}

	// TODO que hace este m�todo.
	@Override
	public List<AnnotationClient> getAnnotationsByIdsAndAuthorsTeacher(
			List<Long> ids, List<Long> authorIds, Long Activity) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO que hace este m�todo.
	@Override
	public List<AnnotationClient> getAnnotationsByTeacherIds(List<Long> ids,
			Long readingActivityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AnnotationClient> getAnnotationsForStudentId(List<Long> ids,
			Long studentId) throws GeneralException,
			AnnotationNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<Annotation> list;
		List<Annotation> listForStudent;
		String sql = "SELECT r FROM Annotation r WHERE r.id=" + ids.get(0);
		for (int i = 1; i < ids.size(); i++) {
			sql += " OR r.id=" + ids.get(i);
		}

		try {
			list = entityManager.createQuery(sql).getResultList();
			listForStudent = new ArrayList<Annotation>();
			for (Annotation annotation : list) {
				if (annotation.getVisibility() == 1) {
					listForStudent.add(annotation);
				} else {
					if (annotation.getCreator().getId().equals(studentId)) {
						listForStudent.add(annotation);
					}
				}

			}
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupByEmail: ", e)
			throw new GeneralException("Exception in method loadGroupByEmail:"
					+ e.getMessage(), e.getStackTrace());

		}

		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return ServiceManagerUtils.produceAnnotationClients(listForStudent);
	}

	@Override
	public List<AnnotationClient> getAnnotationsByTypeClientIdsForProfessor(
			List<Long> ids, Long readingActivityId, Long userId)
			throws GeneralException, TagNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<Tag> list;
		List<Annotation> annotationList = new ArrayList<Annotation>();
		List<Annotation> annotationActivityFilter = new ArrayList<Annotation>();
		String sql = "SELECT t FROM Tag t WHERE t.id=" + ids.get(0);
		for (int i = 1; i < ids.size(); i++) {
			sql += " OR t.id=" + ids.get(i);
		}
		try {
			list = entityManager.createQuery(sql).getResultList();
			if (list == null) {
				// logger.error ("Exception in method loadUserById: ", e)
				throw new TagNotFoundException(
						"User not found in method loadUserByName");

			}
			for (Tag tag : list) {
				annotationList.addAll(tag.getAnnotations());
			}

			for (Annotation annotation : annotationList) {
				if (annotation.getActivity().getId().equals(readingActivityId)) {
					annotationActivityFilter.add(annotation);
				}
			}

		} catch (Exception e) {

			throw new GeneralException("Exception in method loadUserByName:"
					+ e.getMessage(), e.getStackTrace());

		}

		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return ServiceManagerUtils
				.produceAnnotationClients(annotationActivityFilter);
	}

	@Override
	public List<AnnotationClient> getAnnotationsByTypeClientIdsForStudent(
			List<Long> ids, Long readingActivityId, Long studentId)
			throws GeneralException, TagNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<Tag> list;
		List<Annotation> annotationList = new ArrayList<Annotation>();
		List<Annotation> annotationStudentFilter = new ArrayList<Annotation>();
		String sql = "SELECT t FROM Tag t WHERE t.id=" + ids.get(0);
		for (int i = 1; i < ids.size(); i++) {
			sql += " OR t.id=" + ids.get(i);
		}
		try {
			list = entityManager.createQuery(sql).getResultList();
			if (list == null) {
				// logger.error ("Exception in method loadUserById: ", e)
				throw new TagNotFoundException(
						"User not found in method loadUserByName");

			}
			for (Tag tag : list) {
				annotationList.addAll(tag.getAnnotations());
			}
			List<Annotation> annotationActivityFilter = new ArrayList<Annotation>();
			for (Annotation annotation : annotationList) {
				if (annotation.getActivity().getId().equals(readingActivityId)) {
					annotationActivityFilter.add(annotation);
				}
			}

			for (Annotation annotation : annotationActivityFilter) {
				if (annotation.getCreator().getId().equals(studentId)
						|| annotation.getVisibility() == 1) {
					annotationStudentFilter.add(annotation);
				}
			}

		} catch (Exception e) {

			throw new GeneralException("Exception in method loadUserByName:"
					+ e.getMessage(), e.getStackTrace());

		}

		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return ServiceManagerUtils
				.produceAnnotationClients(annotationStudentFilter);
	}

	@Override
	public void deleteAnnotation(Long annotationId) throws GeneralException,
			AnnotationNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.clear();
		try {
			Annotation annotation = entityManager.find(Annotation.class,
					annotationId);
			// ReadingActivity activity = entityManager.find(
			// ReadingActivity.class, annotation.getActivity().getId());
			userTransaction.begin();

			annotation.getActivity().getAnnotations().size();
			annotation.getActivity().getAnnotations().remove(annotation);
			entityManager.merge(annotation.getActivity());
			annotation.setActivity(null);

			annotation.getCreator().getAnnotations().size();
			annotation.getCreator().getAnnotations().remove(annotation);
			entityManager.merge(annotation.getCreator());
			annotation.setCreator(null);

			annotation.getTags().size();
			for (Tag tag : annotation.getTags()) {
				tag.getAnnotations().remove(annotation);
			}

			annotation.setTags(null);

			entityManager.remove(annotation);
			entityManager.flush();
			// /entityManager.merge(annotation.getActivity());
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
			AnnotationThreadClient annotationThreadClient)
			throws GeneralException {
		AnnotationThread oldAnnotationThread;
		if (annotationThreadClient.getId() != null) {
			try {
				oldAnnotationThread = findAnnotationThread(annotationThreadClient
						.getId());
				seeChangesInAnnotationThread(oldAnnotationThread,
						annotationThreadClient);

			} catch (AnnotationThreadNotFoundException e) {
				throw new GeneralException(
						"AnnotationThread not found, the entity will not be persisted",
						e.getStackTrace());
			}

		} else {
			try {
				AnnotationThread threadFather = null;
				Date now = new Date();
				Calendar calendar = Calendar.getInstance();
				now = calendar.getTime();
				Annotation annotation = findAnnotation(annotationThreadClient
						.getAnnotation().getId());
				if (annotationThreadClient.getFather() != null) {
					threadFather = findAnnotationThread(annotationThreadClient
							.getFather().getId());
				}

				oldAnnotationThread = new AnnotationThread(threadFather,
						annotation, annotationThreadClient.getComment(),
						annotationThreadClient.getUserId(),
						annotationThreadClient.getUserName(), now);
				if (threadFather == null) {
					annotation.getThreads().add(oldAnnotationThread);
				} else {
					threadFather.getSubThreads().add(oldAnnotationThread);
				}

			} catch (AnnotationNotFoundException e) {
				throw new GeneralException(
						"Annotation not found, the entity will not be persisted",
						e.getStackTrace());
			} catch (AnnotationThreadNotFoundException e) {
				throw new GeneralException(
						"AnnotationThread not found, the entity will not be persisted",
						e.getStackTrace());
			}

		}
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

	public void saveAnnotationThread(AnnotationThread annotationThread)
			throws GeneralException {
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
			entityManager.merge(annotationThread.getAnnotation());
			if (annotationThread.getFather() != null) {
				entityManager.merge(annotationThread.getFather());
			}

			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException(
					"Error when saving the annotationThread, the transaction will be rolled back",
					e.getStackTrace());
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
			AnnotationThread annotationThread = entityManager.find(
					AnnotationThread.class, annotationThreadId);
			userTransaction.begin();
			if (annotationThread.getFather() != null) {
				annotationThread.getFather().getSubThreads().size();
				annotationThread.getFather().getSubThreads()
						.remove(annotationThread);
				entityManager.merge(annotationThread.getFather());
				annotationThread.setFather(null);
			}
			annotationThread.getAnnotation().getThreads().size();
			annotationThread.getAnnotation().getThreads()
					.remove(annotationThread);
			entityManager.merge(annotationThread.getAnnotation());
			annotationThread.setAnnotation(null);

			entityManager.remove(annotationThread);
			entityManager.flush();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException(
					"Exception in method deleteAnnotationThreadById"
							+ e.getMessage(), e.getStackTrace());
		}
	}

	@Override
	public List<AnnotationThreadClient> getAnnotationThreadsByItsAnnotation(
			Long annotationId) throws GeneralException,
			AnnotationThreadNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<AnnotationThread> list;

		String sql = "SELECT r FROM AnnotationThread r WHERE r.annotation.id="
				+ annotationId + " AND r.father IS NULL";
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadUserById: ", e)
			throw new GeneralException(
					"Exception in method getAnnotationThreadsByItsFather:"
							+ e.getMessage(), e.getStackTrace());

		}
		if (list == null) {
			// logger.error ("Exception in method loadUserById: ", e)
			throw new AnnotationThreadNotFoundException(
					"AnnotationThread not found in method getAnnotationThreadsByItsFather");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return ServiceManagerUtils.produceAnnotationThreadClients(list, null);
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
			connection.addRequestProperty("GET", url.toString());
			connection
					.addRequestProperty(
							"User-Agent",
							"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.70 Safari/533.4");
			connection
					.addRequestProperty(
							"Accept",
							"application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
			connection.addRequestProperty("Accept-Charset",
					"ISO-8859-1,utf-8;q=0.7,*;q=0.7");
			connection.addRequestProperty("Accept-Language",
					"es-es,es;q=0.8,en-us;q=0.5,en;q=0.3");
			// connection.addRequestProperty("Cookie","PREF=ID=893be4845a4c5aec:TM=1271533549:LM=12715335 49:S=KCvKQcyJS2SjNm-5");
			connection
					.addRequestProperty("Cookie",
							"PREF=ID=28face613556316e:TM=1184620070:LM=1184620070:S=DkEaab7_F7PtM3ZX");
			connection.addRequestProperty("Host", "books.google.com");
			connection.addRequestProperty("Connection", "keep-alive");
			// connection.setConnectTimeout(3000);

			// connection.addRequestProperty("Referer",
			// "http://kido180020783.appspot.com/");
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
	public void addBookToUser(BookClient bookClient, Long userId)
			throws GeneralException {
		Book book = reproduceBookFromClient(bookClient, userId);
		book.getProfessor().getBooks().add(book);
		try {
			saveBook(book);
		} catch (GeneralException e) {
			throw new GeneralException("Error in method addBookToUser",
					e.getStackTrace());

		}

	}

	private Book reproduceBookFromClient(BookClient bookClient, Long userId)
			throws GeneralException {
		Professor professor = null;
		try {
			professor = findProfessor(userId);
		} catch (ProfessorNotFoundException e) {
			throw new GeneralException(
					"Professor not found in method reproduceBookFromClient",
					e.getStackTrace());
		}
		if (bookClient instanceof RemoteBookClient)
			return ProcessRemotebook(bookClient, professor); // TODO: el cliente
																// siempre esta
																// enviando los
																// libros como
																// instancias de
																// RemoteBook
																// aun cuando
																// son locales
		else
			return ProcessLocalbook(bookClient, professor);

	}

	private Book ProcessRemotebook(BookClient bookClient, Professor professor) {
		if (bookClient instanceof GoogleBookClient)
			return processGoogleBook(bookClient, professor);

		return null;
	}

	private Book processGoogleBook(BookClient bookClient, Professor professor) {
		GoogleBookClient entrada = (GoogleBookClient) bookClient;

		GoogleBook book = new GoogleBook(entrada.getAuthor(),
				entrada.getISBN(), Integer.toString(entrada.getWebLinks()
						.size()), entrada.getPublishedYear(),
				entrada.getTitle(), entrada.getTbURL(), entrada.getUrl());
		book.setProfessor(professor);
		book.setWebLinks(entrada.getWebLinks());
		return book;
	}

	private Book ProcessLocalbook(BookClient bookClient, Professor professor) {
		// TODO Realizar cuando este el sistema de carga
		return null;
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
	public void saveCatalog(CatalogoClient catalogClient)
			throws GeneralException {
		short isPrivate = (catalogClient.getIsPrivate() ? (short) 1 : 0);
		Catalogo catalogo = new Catalogo(isPrivate,
				catalogClient.getProfessorId(), catalogClient.getCatalogName());
		try {
			saveCatalog(catalogo);
		} catch (GeneralException e) {
			throw new GeneralException(
					"Error when saving the catalog, the transaction will be rolled back",
					e.getStackTrace());
		}
	}

	public void updateCatalog(CatalogoClient catalogClient)
			throws CatalogoNotFoundException, GeneralException {
		Catalogo catalog = findCatalogo(catalogClient.getId());
		catalog.setOrders(reorderrelationsCatalog(catalogClient.getEntries(),
				catalog.getOrders()));
		saveCatalog(catalog);
	}

	private List<Long> reorderrelationsCatalog(List<EntryClient> entriesClient,
			List<Long> orders) {
		List<Long> orderList = new ArrayList<Long>();

		for (EntryClient entryClient : entriesClient) {
			orderList.add(entryClient.getId());
		}
		return orderList;
	}

	private void saveCatalog(Catalogo catalog) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		try {
			userTransaction.begin();
			if (catalog.getId() == null) {
				entityManager.persist(catalog);
			} else {
				entityManager.merge(catalog);
			}

			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException(
					"The catalog could not be saved, the transaction will be rolled back",
					e.getStackTrace());
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

	@Override
	public void addChildToCatalog(EntryClient entryClient, Long catalogId)
			throws TwinBrotherException, GeneralException {
		try {
			Entry entry = null;
			Catalogo catalogo = findCatalogo(catalogId);
			if (entryClient instanceof TypeClient) {
				entry = reproduceTagFromClient((TypeClient) entryClient);
			} else {
				entry = reproduceFolderFromClient((TypeCategoryClient) entryClient);
			}
			if (catalogo.getEntries().contains(entry)) {
				throw new TwinBrotherException(
						"This element has a twin brother in DB");
			}
			catalogo.getEntries().add(entry);
			saveCatalog(catalogo);
		} catch (CatalogoNotFoundException e) {
			e.printStackTrace();
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FolderDBNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Tag reproduceTagFromClient(TypeClient typeClient)
			throws TagNotFoundException {
		if (typeClient.getId() == null) {
			return new Tag(typeClient.getName());
		} else {
			return findTag(typeClient.getId());
		}

	}

	private FolderDB reproduceFolderFromClient(
			TypeCategoryClient typeCategoryClient)
			throws FolderDBNotFoundException {
		if (typeCategoryClient.getId() == null) {
			return new FolderDB(typeCategoryClient.getName());
		} else {
			return findFolderDB(typeCategoryClient.getId());
		}

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
	public List<CatalogoClient> getCatalogs() throws GeneralException,
			CatalogoNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<Catalogo> list;

		String sql = "SELECT r FROM Catalogo r ";
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupByEmail: ", e)
			throw new GeneralException("Exception in method getCatalogos()"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new CatalogoNotFoundException(
					"Catalogo not found in method loadGroupByEmail");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return ServiceManagerUtils.produceCatalogoClients(list);

	}

	@Override
	public List<CatalogoClient> getVisbibleCatalogsByProfessorId(
			Long professorId) throws GeneralException,
			CatalogoNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<Catalogo> list;

		String sql = "SELECT r FROM Catalogo r WHERE r.professorId= "
				+ professorId + " OR r.isPrivate=0";
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupByEmail: ", e)
			throw new GeneralException(
					"Exception in method getVisibleCatalogos()"
							+ e.getMessage(), e.getStackTrace());

		}
		if (list == null) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new CatalogoNotFoundException(
					"Catalogo not found in method getVisibleCatslogs");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return ServiceManagerUtils.produceCatalogoClients(list);

	}

	@Override
	public void deleteCatalog(Long catalogId) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			Catalogo catalogo = entityManager.find(Catalogo.class, catalogId);
			userTransaction.begin();
			List<Entry> listEntry = new CopyOnWriteArrayList<Entry>(
					catalogo.getEntries());
			for (Entry entry : listEntry) {
				Entry entryAux = entityManager.find(Entry.class, entry.getId());

				if (entryAux instanceof FolderDB) {
					deletePlainFolder(entityManager, entry.getId(),
							Constants.CATALOGID);

				} else {
					deleteTag(entityManager, entry.getId(), Constants.CATALOGID);
				}
			}
			entityManager.remove(catalogo);
			entityManager.flush();
			userTransaction.commit();
			entityManager.clear();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException("Exception in method deleteFolderDBById"
					+ e.getMessage(), e.getStackTrace());
		}

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
	public TypeClient loadTypeById(Long tagId) throws TagNotFoundException,
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
	public TypeClient loadTypeByNameAndCatalogId(String tagName, Long catalogId)
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
															// m�todo de
															// logger
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

	@Override
	public void saveType(TypeClient typesys, Long fatherEntry)
			throws GeneralException {
		Tag tag = new Tag();
		try {
			Catalogo catalogo = findCatalogo(typesys.getCatalog().getId());
			tag.setCatalog(catalogo);
			tag.setName(typesys.getName());
			if (fatherEntry != -1l) {
				FolderDB father = findFolderDB(fatherEntry);
				Relation relation = new Relation(father, tag);
				father.getRelations().add(relation);
				saveFolderDB(father);
			} else {
				catalogo.getEntries().add(tag);
				saveCatalog(catalogo);
			}

		} catch (CatalogoNotFoundException e) {
			throw new GeneralException(
					"Catalog not found, no entity will be rolled back",
					e.getStackTrace());
		} catch (FolderDBNotFoundException fnfe) {
			fnfe.printStackTrace();
		}

	}

	public void updateType(TypeClient typesys) throws TagNotFoundException {
		Tag Type = findTag(typesys.getId());
		// TODO Actualizar

	}

	@Override
	public void deleteTag(Long tagId, Long fatherId) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			deleteTag(entityManager, tagId, fatherId);
			entityManager.flush();
			userTransaction.commit();
			entityManager.clear();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException("Exception in method deleteFolderDBById"
					+ e.getMessage(), e.getStackTrace());
		}

	}

	private ReadingActivity loadActivityByDefaultTypeId(Long id)
			throws GeneralException, ReadingActivityNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<ReadingActivity> list;

		String sql = "SELECT r FROM ReadingActivity r WHERE r.defultTag.id="
				+ id;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new GeneralException(
					"Exception in method loadReadingActivityById:"
							+ e.getMessage(), e.getStackTrace());

		}
		if (list == null) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new ReadingActivityNotFoundException(
					"ReadingActivity not found in method loadReadingActivityById");

		}
		if (list.isEmpty()) {
			return null;
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return list.get(0);
	}

	@Override
	public Integer getAnnotationsNumberByTagName(String annotationTagName)
			// puede haber dos tagNames iuales en actividades diferentes.
			throws GeneralException, NullParameterException,
			AnnotationNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fusionTypes(Long tagFromId, Long tagToId)
			throws GeneralException, NullParameterException {
		if (tagFromId == null || tagToId == null) {
			throw new NullParameterException(
					"Parameter cant be null in method deleteDnServices");
		}
		EntityManager entityManager = emf.createEntityManager();
		try {
			userTransaction.begin();
			Tag tagTo = entityManager.find(Tag.class, tagToId);
			Tag tagFrom = entityManager.find(Tag.class, tagFromId);

			ReadingActivity activity = loadActivityByDefaultTypeId(tagFromId);
			if (activity != null) {
				activity.setDefultTag(tagTo);
				entityManager.merge(activity);

			}
			for (Annotation annotation : tagFrom.getAnnotations()) {
				entityManager.merge(annotation);
				annotation.getTags().remove(tagFrom);
				if (!annotation.getTags().contains(tagTo)) {
					annotation.getTags().add(tagTo);
					tagTo.getAnnotations().add(annotation);
				}
			}
			tagFrom.setAnnotations(null);
			changeParentsRelations(entityManager, tagFromId, tagTo.getId(),
					tagFrom.getCatalog().getId());
			entityManager.remove(tagFrom);
			entityManager.flush();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);

		}
	}

	private void changeParentsRelations(EntityManager entityManager,
			Long entryFromId, Long entryToId, Long catalogId)
			throws GeneralException, AnnotationNotFoundException,
			RelationNotFoundException {
		Entry entryTo = entityManager.find(Entry.class, entryToId);
		Entry entryFrom = entityManager.find(Entry.class, entryFromId);
		List<Relation> relations = getRelationsByChildId(entryFromId);
		if (relations == null || relations.isEmpty()) { // cuelga del catalogo
			Catalogo catalogo = entityManager.find(Catalogo.class, catalogId);
			if (!catalogo.getEntries().contains(entryTo)) {
				catalogo.getEntries().add(entryTo);
			}
			catalogo.getEntries().remove(entryFrom);
			entryFrom.setCatalog(null);
		} else {

			List<Relation> list = new CopyOnWriteArrayList<Relation>(relations);
			for (Relation relationFrom : list) {
				relationFrom = entityManager.find(Relation.class,
						relationFrom.getId());
				FolderDB folderFrom = entityManager.find(FolderDB.class,
						relationFrom.getFather().getId());
				List<Relation> listAux = new CopyOnWriteArrayList<Relation>(
						folderFrom.getRelations());
				for (Relation relationTo : listAux) {
					if (!relationTo.getChild().getId().equals(entryTo.getId())) {
						Relation relationAux = new Relation(folderFrom, entryTo);
						folderFrom.getRelations().add(relationAux);
						entityManager.merge(folderFrom);
					}
					relationFrom.setChild(null);
					entityManager.remove(relationFrom);
				}
			}
		}

	}

	// private void callForMultiplePersist(List<Relation> relationsTo,
	// Tag tagFrom, Catalogo catalogo, List<Relation> relationFathers) {
	// EntityManager entityManager = emf.createEntityManager();
	// try {
	// userTransaction.begin();
	// for (Relation relation : relationsTo) {
	// if (relation.getId() != null) {
	// entityManager.merge(relation);
	// } else {
	// entityManager.persist(relation);
	// }
	// }
	// for (Relation relationRem : relationFathers) {
	//
	// FolderDB folderFrom = (FolderDB) relationRem.getFather();
	// for (Relation relation : folderFrom.getRelations()) {
	// if (relation.getId().equals(relationRem.getId())) {
	// folderFrom.getRelations().remove(relation);
	// break;
	// }
	//
	// }
	// entityManager.merge(folderFrom);
	// entityManager.merge(relationRem);
	// }
	// // entityManager.merge(tagFrom);
	// // entityManager.remove(tagFrom);
	// entityManager.merge(catalogo);
	// userTransaction.commit();
	// } catch (Exception e) {
	// ServiceManagerUtils.rollback(userTransaction);
	// }
	// if (entityManager.isOpen()) {
	// entityManager.close();
	// }
	//
	// }

	private List<Relation> getRelationsByChildId(Long id)
			throws GeneralException, RelationNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<Relation> list;

		String sql = "SELECT r FROM Relation r WHERE r.child.id=" + id;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupByEmail: ", e)
			throw new GeneralException("Exception in method getRelations:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new RelationNotFoundException(
					"Relation not found in method getRelations");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list;
	}

	@Override
	public void moveType(Long typeCategoryFromId, Long typeId,
			Long typeCategoryToId) throws GeneralException {
		try {

			Tag tag = findTag(typeId);
			if (!typeCategoryFromId.equals(Constants.CATALOGID)) {
				Relation relation = findRelation((loadRelationByFatherAndSonId(
						typeCategoryFromId, typeId)).getId());
				FolderDB folderFrom = findFolderDB(typeCategoryFromId);
				for (Relation relationToRemove : folderFrom.getRelations()) {
					if (relationToRemove.getId().equals(relation.getId())) {
						folderFrom.getRelations().remove(relationToRemove);
						break;
					}
				}
				if (!typeCategoryToId.equals(Constants.CATALOGID)) {
					FolderDB typeCategoryTo = findFolderDB(typeCategoryToId);
					relation.setFather(typeCategoryTo);
					typeCategoryTo.getRelations().add(relation);
					saveFolderDB(typeCategoryTo);
				} else {
					Catalogo catalogo = tag.getCatalog();
					catalogo.getEntries().add(tag);
					callForMultiplePersist(folderFrom, catalogo);
				}

			} else {
				Catalogo catalogo = tag.getCatalog();
				catalogo.getEntries().remove(tag);
				if (!typeCategoryToId.equals(Constants.CATALOGID)) {
					FolderDB typeCategoryTo = findFolderDB(typeCategoryToId);
					Relation relation = new Relation(typeCategoryTo, tag);
					typeCategoryTo.getRelations().add(relation);
					callForMultiplePersist(typeCategoryTo, catalogo);
				}

			}

		} catch (FolderDBNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RelationNotFoundException rnfe) {
			rnfe.printStackTrace();
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void callForMultiplePersist(FolderDB folderFrom, Catalogo catalogo) {

		EntityManager entityManager = emf.createEntityManager();
		try {
			userTransaction.begin();
			entityManager.merge(folderFrom);
			entityManager.merge(catalogo);
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
		}
		if (entityManager.isOpen()) {
			entityManager.close();
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
															// m�todo de
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
	public List<TypeClient> getTypesByNameAndCatalogId(List<String> tagNames,
			Long catalogId) throws GeneralException {

		EntityManager entityManager = emf.createEntityManager();
		List<Tag> list;
		String sql = "SELECT r FROM Tag r WHERE r.name='" + tagNames.get(0)
				+ "' ";
		for (int i = 1; i < tagNames.size(); i++) {
			sql += " OR r.name='" + tagNames.get(i) + "'";
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
	public List<TypeClient> getTypesByIds(List<Long> typeIds)
			throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		List<Tag> list;
		String sql = "SELECT r FROM Tag r WHERE r.id=" + typeIds.get(0);
		for (int i = 1; i < typeIds.size(); i++) {
			sql += " OR r.id=" + typeIds.get(i);
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

	// @Override
	// public List<String> getTypesNamesByIds(List<Long> typeIds)
	// throws TagNotFoundException, GeneralException {
	// EntityManager entityManager = emf.createEntityManager();
	// List<Tag> list;
	// String sql = "SELECT r FROM Tag r WHERE r.id=" + typeIds.get(0);
	// for (int i = 1; i < typeIds.size(); i++) {
	// sql += " OR r.id=" + typeIds.get(i);
	// }
	//
	// try {
	// list = entityManager.createQuery(sql).getResultList();
	// } catch (Exception e) {
	// // logger.error ("Exception in method loadTagById: ", e)
	// throw new GeneralException("Exception in method loadTagById:"
	// + e.getMessage(), e.getStackTrace());
	//
	// }
	// if (list == null || list.isEmpty()) {
	// // logger.error ("Exception in method loadTagById: ", e)
	// throw new TagNotFoundException(
	// "Tag not found in method loadTagById");
	//
	// }
	// if (entityManager.isOpen()) {
	// entityManager.close();
	// }
	// return getStringFromTags(list);
	// }

	private List<String> getStringFromTags(List<Tag> tags) {
		List<String> names = new ArrayList<String>();
		for (Tag tag : tags) {
			names.add(tag.getName());

		}
		return names;
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
	public void addChildEntry(Long entryId, Long fatherFolderDBId)
			throws TwinBrotherException, GeneralException {
		try {
			FolderDB father = findFolderDB(fatherFolderDBId);
			Entry entry = findEntry(entryId);
			if (father != null) {
				if (isThereTwinBrother(father, entry)) {
					throw new TwinBrotherException(
							"This element has a twin brother in DB");
				}
				Relation relation = new Relation(father, entry);
				father.getRelations().add(relation);
				saveFolderDB(father);
			} else {

				Catalogo catalogo = findCatalogo(fatherFolderDBId);
				catalogo.getEntries().add(entry);
				saveCatalog(catalogo);
			}

		} catch (FolderDBNotFoundException e) {

			e.printStackTrace();
		} catch (EntryNotFoundException e) {

			e.printStackTrace();
		} catch (CatalogoNotFoundException cne) {
			cne.printStackTrace();
		}

	}

	private boolean isThereTwinBrother(FolderDB father, Entry entry) {
		for (Relation relation : father.getRelations()) {
			if (relation.getChild().getId().equals(entry.getId())) {
				return true;
			}

		}
		return false;
	}

	private Entry findEntry(Long entryId) throws EntryNotFoundException {
		try {
			FolderDB folderDB = findFolderDB(entryId);
			return folderDB;

		} catch (FolderDBNotFoundException e) {

		}
		try {
			Tag tag = findTag(entryId);
			return tag;
		} catch (TagNotFoundException e) {
			throw new EntryNotFoundException("Entry not found");
		}

	}

	@Override
	public TypeCategoryClient loadTypeCategoryById(Long typeCategoryId)
			throws GeneralException, FolderDBNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<FolderDB> list;

		String sql = "SELECT r FROM FolderDB r WHERE r.id=" + typeCategoryId;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadFolderDBById: ", e)
			throw new GeneralException("Exception in method loadFolderDBById:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadFolderDBById: ", e)
			throw new FolderDBNotFoundException(
					"FolderDB not found in method loadFolderDBById");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		CatalogoClient catalogoClient = ServiceManagerUtils
				.produceCatalogoClient(list.get(0).getCatalog());
		return ServiceManagerUtils.produceTypeCategoryClient(list.get(0),
				catalogoClient);
	}

	@Override
	public TypeCategoryClient loadTypeCategoryByNameAndCatalogId(
			String folderDBName, Long catalogId) throws GeneralException,
			FolderDBNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<FolderDB> list;

		String sql = "SELECT r FROM FolderDB r WHERE r.name='" + folderDBName
				+ "' AND r.catalog.id=" + catalogId;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadFolderDBById: ", e)
			throw new GeneralException("Exception in method loadFolderDBById:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadFolderDBById: ", e)
			throw new FolderDBNotFoundException(
					"FolderDB not found in method loadFolderDBById");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		CatalogoClient catalogoClient = ServiceManagerUtils
				.produceCatalogoClient(list.get(0).getCatalog());
		return ServiceManagerUtils.produceTypeCategoryClient(list.get(0),
				catalogoClient);
	}

	@Override
	public void deleteTypeCategory(Long id, Long fatherId)
			throws GeneralException {

		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			deletePlainFolder(entityManager, id, fatherId);
			entityManager.flush();
			userTransaction.commit();
			entityManager.clear();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException("Exception in method deleteFolderDBBYid"
					+ e.getMessage(), e.getStackTrace());
		}
	}

	private void deletePlainFolder(EntityManager entityManager, Long id,
			Long fatherId) throws GeneralException,
			AnnotationNotFoundException, RelationNotFoundException {
		FolderDB folderDB = entityManager.find(FolderDB.class, id);
		if (fatherId.equals(Constants.CATALOGID)) { // cuelga del catalogo
			folderDB.getCatalog().getEntries().size();
			folderDB.getCatalog().getEntries().remove(folderDB);
			entityManager.merge(folderDB.getCatalog());
			folderDB.setCatalog(null);
			// entityManager.remove(folderDB);
			List<Relation> list = new CopyOnWriteArrayList<Relation>(
					folderDB.getRelations());
			for (Relation relation : list) {
				Long childId = relation.getChild().getId();
				Entry entryToRemove = entityManager.find(Entry.class, childId);
				if (entryToRemove instanceof FolderDB) {
					FolderDB folderToRemove = (FolderDB) entryToRemove;
					deleteDeepFolderById(entityManager, folderToRemove.getId(),
							folderDB.getId());

				} else {
					deleteTag(entityManager, relation.getChild().getId(),
							folderDB.getId());
				}

			}
			entityManager.remove(folderDB);

		} else {
			List<Relation> relations = getRelationsByChildId(id);
			FolderDB folderFather = entityManager
					.find(FolderDB.class, fatherId);
			Relation relation = loadRelationByFatherAndSonId(fatherId, id);
			relation = entityManager.find(Relation.class, relation.getId());
			folderFather.getRelations().size();
			folderFather.getRelations().remove(relation);
			relation.setFather(null);
			relation.setChild(null);
			// entityManager.merge(relation);
			// entityManager.merge(folderFather);
			if (folderDB != null) {
				if (relations.size() > 1) {
					entityManager.merge(folderDB);
				} else {
					List<Relation> list = new CopyOnWriteArrayList<Relation>(
							folderDB.getRelations());
					for (Relation relationAux : list) {
						deleteDeepFolderById(entityManager, relationAux
								.getChild().getId(), folderDB.getId());
					}
					entityManager.remove(folderDB);
				}
			} else {
				deleteTag(entityManager, id, fatherId);
			}

		}

	}

	private void deleteDeepFolderById(EntityManager entityManager, Long id,
			Long fatherId) throws GeneralException {
		try {

			FolderDB folderDB = entityManager.find(FolderDB.class, id);
			if (folderDB != null) {
				List<Relation> relations = getRelationsByChildId(id);
				FolderDB folderFather = entityManager.find(FolderDB.class,
						fatherId);
				Relation relation = loadRelationByFatherAndSonId(fatherId, id);
				relation = entityManager.find(Relation.class, relation.getId());
				folderFather.getRelations().size();
				folderFather.getRelations().remove(relation);
				relation.setFather(null);
				relation.setChild(null);
				if (folderDB.getCatalog().getEntries().contains(folderDB)) {
					folderDB.getCatalog().getEntries().remove(folderDB);
					entityManager.merge(folderDB.getCatalog());
					folderDB.setCatalog(null);
				}

				// entityManager.merge(relation);
				// entityManager.merge(folderFather);

				if (relations.size() > 1) {
					entityManager.merge(folderDB);
				} else {
					List<Relation> list = new CopyOnWriteArrayList<Relation>(
							folderDB.getRelations());
					for (Relation relationAux : list) {
						deleteDeepFolderById(entityManager, relationAux
								.getChild().getId(), folderDB.getId());
					}
					entityManager.remove(folderDB);
				}
			} else {
				deleteTag(entityManager, id, fatherId);
			}

		} catch (Exception e) {

			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException("Exception in method deleteFolderDBById"
					+ e.getMessage(), e.getStackTrace());
		}

	}

	private Relation loadRelationById(EntityManager entityManager, Long id)
			throws RelationNotFoundException, GeneralException {

		List<Relation> list;
		String sql = "SELECT r FROM Relation r WHERE r.id=" + id;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadRelationByEmail: ", e)
			throw new GeneralException(
					"Exception in method loadRelationByEmail:" + e.getMessage(),
					e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadRelationById: ", e)
			throw new RelationNotFoundException(
					"Relation not found in method loadRelationByEmail");

		}

		return list.get(0);
	}

	private FolderDB loadFolderById(EntityManager entityManager, Long id)
			throws FolderDBNotFoundException, GeneralException {

		List<FolderDB> list;
		String sql = "SELECT r FROM FolderDB r WHERE r.id=" + id;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadFolderByEmail: ", e)
			throw new GeneralException("Exception in method loadFolderByEmail:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadFolderById: ", e)
			throw new FolderDBNotFoundException(
					"Folder not found in method loadFolderByEmail");

		}

		return list.get(0);
	}

	private void deleteTag(EntityManager entityManager, Long id, Long fatherId)
			throws GeneralException, AnnotationNotFoundException,
			RelationNotFoundException {
		Tag tag = entityManager.find(Tag.class, id);
		List<Relation> relations = getRelationsByChildId(id);
		try {
			if (fatherId.equals(Constants.CATALOGID)) {
				tag.getCatalog().getEntries().size();
				tag.getCatalog().getEntries().remove(tag);
				entityManager.merge(tag.getCatalog());
				tag.setCatalog(null);
				ReadingActivity activity = loadActivityByDefaultTypeId(tag
						.getId());
				if (activity != null) {
					activity.setDefultTag(null);
					entityManager.merge(activity);

				}
				for (Annotation annotation : tag.getAnnotations()) {
					annotation.getTags().remove(tag);
					entityManager.merge(annotation);
				}
				entityManager.remove(tag);

			} else {
				FolderDB folderFather = findFolderDB(fatherId);
				Relation relation = loadRelationByFatherAndSonId(fatherId, id);
				for (Relation relationAux : folderFather.getRelations()) {
					if (relationAux.getId().equals(relation.getId())) {
						folderFather.getRelations().remove(relationAux);
						break;
					}
				}

				entityManager.merge(relation.getFather());
				relation.setFather(null);
				relation.setChild(null);
				Relation toBeRemove = entityManager.merge(relation);
				entityManager.remove(toBeRemove);
				if (relations.size() < 2) {
					ReadingActivity activity = loadActivityByDefaultTypeId(tag
							.getId());
					if (activity != null) {
						activity.setDefultTag(null);
						entityManager.merge(activity);

					}

					Tag tagtoBeRemove = entityManager.merge(tag);
					for (Annotation annotation : tagtoBeRemove.getAnnotations()) {
						annotation.getTags().remove(tagtoBeRemove);
						entityManager.merge(annotation);
					}
					entityManager.remove(tagtoBeRemove);

				}
			}

		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException("Exception in method deleteFolderDBById"
					+ e.getMessage(), e.getStackTrace());
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
			Long fatherEntry) throws GeneralException {

		try {

			Catalogo catalogo = findCatalogo(typeCategoryClient.getCatalog()
					.getId());
			FolderDB folderDB = new FolderDB(typeCategoryClient.getName(),
					catalogo);

			if (fatherEntry != -1) {
				FolderDB father = findFolderDB(fatherEntry);
				Relation relation = new Relation(father, folderDB);
				father.getRelations().add(relation);
				saveFolderDB(father);

			} else {
				catalogo.getEntries().add(folderDB);
				// saveCatalog(catalogo);
				saveFolderDB(folderDB);
			}

		} catch (CatalogoNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FolderDBNotFoundException fdne) {
			fdne.printStackTrace();
		}

	}

	public void updateTypeCategory(TypeCategoryClient typeCategoryClient)
			throws FolderDBNotFoundException {
		FolderDB folder = findFolderDB(typeCategoryClient.getId());
		folder.setOrders(reorderrelations(typeCategoryClient.getChildren(),
				folder.getRelations()));
		saveFolderDB(folder);
	}

	private List<Long> reorderrelations(List<EntryClient> children,
			List<Relation> relations) {
		List<Long> relationsOrdered = new ArrayList<Long>();
		for (EntryClient entryRel : children) {
			Long r = findrelation(entryRel, relations);
			relationsOrdered.add(r);
		}
		return relationsOrdered;
	}

	private Long findrelation(EntryClient entryRel, List<Relation> relations) {
		for (Relation relation : relations) {
			if (relation.getChild().getId().equals(entryRel.getId()))
				return relation.getId();
		}
		return null;
	}

	// private List<Relation> reorderrelations(List<EntryClient> children,
	// List<Relation> relations, List<Long> orders) {
	// List<Relation> relationsOrdered = new ArrayList<Relation>();
	// for (EntryClient entryRel : children) {
	// Relation r = findrelation(entryRel,relations, orders);
	// relationsOrdered.add(r);
	// }
	// return relationsOrdered;
	// }
	//
	//
	//
	//
	// private Relation findrelation(EntryClient entryRel, List<Relation>
	// relations, List<Long> orders) {
	// for (Long id : orders) {
	// if
	// (getRelationById(id,relations).getChild().getId().equals(entryRel.getId()))
	// return getRelationById(id,relations);
	// }
	// return null;
	// }

	private Long saveFolderDB(FolderDB folder) {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			if (folder.getId() == null) {
				entityManager.persist(folder);
				entityManager.merge(folder.getCatalog());

			} else {
				entityManager.merge(folder);
				for (Relation relation : folder.getRelations()) {
					entityManager.merge(relation);
				}
			}
			entityManager.flush();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return folder.getId();

	}

	@Override
	public void moveFolderDB(Long typeCategoryFromId, Long typeCategoryId,
			Long typeCategoryToId) throws GeneralException, DecendanceException {
		try {

			FolderDB folderDB = findFolderDB(typeCategoryId);
			if (!typeCategoryFromId.equals(Constants.CATALOGID)) {
				Relation relation = loadRelationByFatherAndSonId(
						typeCategoryFromId, typeCategoryId);

				FolderDB folderFrom = findFolderDB(typeCategoryFromId);
				Relation tmp = findRelation(relation.getId());
				for (Relation relationToRemove : folderFrom.getRelations()) {
					if (relationToRemove.getId().equals(tmp.getId())) {
						folderFrom.getRelations().remove(relationToRemove);
						break;
					}
				}

				if (!typeCategoryToId.equals(Constants.CATALOGID)) {
					FolderDB typeCategoryTo = findFolderDB(typeCategoryToId);
					relation.setFather(typeCategoryTo);
					typeCategoryTo.getRelations().add(relation);
					saveFolderDB(typeCategoryTo);
				} else {

					Catalogo catalogo = folderDB.getCatalog();
					catalogo.getEntries().add(folderDB);
					callForMultiplePersist(folderFrom, catalogo);

				}

			} else {
				if (!typeCategoryToId.equals(Constants.CATALOGID)) {
					Catalogo catalogo = folderDB.getCatalog();
					catalogo.getEntries().remove(folderDB);
					FolderDB typeCategoryTo = findFolderDB(typeCategoryToId);
					Relation relation = new Relation(typeCategoryTo, folderDB);
					typeCategoryTo.getRelations().add(relation);
					callForMultiplePersist(typeCategoryTo, catalogo);
				}

			}

		} catch (FolderDBNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RelationNotFoundException rnfe) {
			rnfe.printStackTrace();
		}

	}

	@Override
	public void fusionFolder(Long folderFromId, Long folderToId)
			throws IlegalFolderFusionException, GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		try {
			userTransaction.begin();
			FolderDB folderTo = entityManager.find(FolderDB.class, folderToId);
			FolderDB folderFrom = entityManager.find(FolderDB.class,
					folderFromId);
			changeParentsRelations(entityManager, folderFromId, folderTo,
					folderFrom.getCatalog().getId());

			giveChildrenInAdoption(entityManager, folderFrom, folderTo);
			entityManager.remove(folderFrom);
			entityManager.flush();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);

		}

	}

	private void giveChildrenInAdoption(EntityManager entityManager,
			FolderDB folderFrom, FolderDB folderTo) throws GeneralException,
			AnnotationNotFoundException, RelationNotFoundException {
		for (Relation relation : folderFrom.getRelations()) {
			boolean flag = false;
			relation = entityManager.find(Relation.class, relation.getId());
			List<Relation> relationsOfChild = getRelationsByChildId(relation
					.getChild().getId());
			for (Relation relationAux : relationsOfChild) {
				if (relationAux.getFather().getId().equals(folderTo.getId())) {
					flag = true;
				}
			}

			if (!flag) {
				Relation newRelation = new Relation(folderTo,
						relation.getChild());
				folderTo.getRelations().add(newRelation);
				entityManager.merge(folderTo);
			}
			relation.setFather(null);
			relation.setChild(null);
		}
	}

	private void changeParentsRelations(EntityManager entityManager,
			Long entryFromId, Entry tagTo, Long catalogId)
			throws GeneralException, AnnotationNotFoundException,
			RelationNotFoundException {

		List<Relation> relations = getRelationsByChildId(entryFromId);
		if (relations == null || relations.isEmpty()) { // cuelga del catalogo
			Catalogo catalogo = entityManager.find(Catalogo.class, catalogId);
			if (!catalogo.getEntries().contains(tagTo)) {
				catalogo.getEntries().add(tagTo);
			}
			catalogo.getEntries().remove(
					entityManager.find(Entry.class, entryFromId));
		} else {

			List<Relation> list = new CopyOnWriteArrayList<Relation>(relations);
			for (Relation relationFrom : list) {
				relationFrom = entityManager.find(Relation.class,
						relationFrom.getId());
				FolderDB folderFrom = entityManager.find(FolderDB.class,
						relationFrom.getFather().getId());
				List<Relation> listAux = new CopyOnWriteArrayList<Relation>(
						folderFrom.getRelations());
				for (Relation relationTo : listAux) {
					if (!relationTo.getChild().getId().equals(tagTo.getId())) {
						Relation relationAux = new Relation(folderFrom, tagTo);
						folderFrom.getRelations().add(relationAux);
						entityManager.merge(folderFrom);
					}
					relationFrom.setChild(null);
				}
			}
		}

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
			ReadingActivity activity = entityManager.find(
					ReadingActivity.class, readingActivityId);
			userTransaction.begin();
			activity.getProfessor().getReadingActivities().size();
			activity.getProfessor().getReadingActivities().remove(activity);
			entityManager.merge(activity.getProfessor());
			activity.setProfessor(null);
			entityManager.remove(activity);
			entityManager.flush();
			// /entityManager.merge(annotation.getActivity());
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException(
					"Exception in method deleteReadingActivityById"
							+ e.getMessage(), e.getStackTrace());
		}
	}

	@Override
	public List<ReadingActivityClient> getReadingActivitiesByStudentId(
			Long userId) throws GeneralException, UserNotFoundException,
			GroupNotFoundException {
		List<Long> groups = getGroupIds(getGroupsOfStudent(userId));

		return ServiceManagerUtils
				.produceReadingActivityClients(getReadingActivitiesByGroupIds(groups));
	}

	private List<ReadingActivity> getReadingActivitiesByGroupIds(List<Long> ids)
			throws GroupNotFoundException, GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		List<ReadingActivity> list;
		String sql = "SELECT r FROM ReadingActivity r WHERE r.group.id="
				+ ids.get(0);
		for (int i = 1; i < ids.size(); i++) {
			sql += " OR r.group.id=" + ids.get(i);
		}

		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadReadingActivityById: ", e)
			throw new GeneralException(
					"Exception in method loadReadingActivityById:"
							+ e.getMessage(), e.getStackTrace());

		}
		if (list == null) {
			// logger.error ("Exception in method loadReadingActivityById: ", e)
			throw new GroupNotFoundException(
					"ReadingActivity not found in method loadReadingActivityById");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return list;
	}

	private List<GroupApp> getGroupsOfStudent(Long userId)
			throws GeneralException, UserNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<GroupApp> list;
		try {
			Student student = findStudent(userId);
			String sql = "SELECT r FROM GroupApp AS r WHERE :student MEMBER OF r.participatingStudents";
			Query query = entityManager.createQuery(sql);
			query.setParameter("student", student);
			list = query.getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadUserByName: ", e)
			throw new GeneralException("Exception in method loadUserByName:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null) {
			// logger.error ("Exception in method loadUserById: ", e)
			throw new UserNotFoundException(
					"User not found in method loadUserByName");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return list;

	}

	private List<Long> getGroupIds(List<GroupApp> groups) {

		List<Long> ids = new ArrayList<Long>();
		for (GroupApp group : groups) {
			ids.add(group.getId());
		}
		return ids;
	}

	// private List<Long> getParticipatingGroupsOfStudent(Long userId)
	// throws StudentNotFoundException {
	// Student student = findStudent(userId);
	// if (student == null) {
	// throw new StudentNotFoundException("The student does not exist");
	// }
	// List<Long> userIds = new ArrayList<Long>();
	// for (GroupApp group : student.getParticipatingGroups()) {
	// userIds.add(group.getId());
	// }
	// return userIds;
	// }

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
		if (list == null) {
			// logger.error ("Exception in method loadUserById: ", e)
			throw new ReadingActivityNotFoundException(
					"ReadingActivity not found in method getReadingActivitysByItsFather");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return ServiceManagerUtils.produceReadingActivityClients(list);
	}

	private List<ReadingActivity> getReadingActivitiesByLanguageId(
			Long languageId) throws GeneralException,
			ReadingActivityNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<ReadingActivity> list;

		String sql = "SELECT a FROM ReadingActivity a WHERE a.language.id="
				+ languageId;
		try {
			list = entityManager.createQuery(sql).getResultList();
			if (list == null) {
				// logger.error ("Exception in method loadGroupById: ", e)
				throw new ReadingActivityNotFoundException(
						"Professor not found in method getProfessors");

			}

		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupByEmail: ", e)
			throw new GeneralException("Exception in method getProfessors:"
					+ e.getMessage(), e.getStackTrace());

		}

		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return list;
	}

	private List<ReadingActivity> getReadingActivitiesByBookId(Long bookId)
			throws GeneralException, ReadingActivityNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<ReadingActivity> list;

		String sql = "SELECT a FROM ReadingActivity a WHERE a.book.id="
				+ bookId;
		try {
			list = entityManager.createQuery(sql).getResultList();
			if (list == null) {
				// logger.error ("Exception in method loadGroupById: ", e)
				throw new ReadingActivityNotFoundException(
						"Professor not found in method getProfessors");

			}

		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupByEmail: ", e)
			throw new GeneralException("Exception in method getProfessors:"
					+ e.getMessage(), e.getStackTrace());

		}

		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return list;
	}

	@Override
	public void saveReadingActivity(ReadingActivityClient readingActivityClient)
			throws GeneralException {

		ReadingActivity activity = null;
		try {
			if (readingActivityClient.getId() != null) {
				activity = findReadingActivity(readingActivityClient.getId());
				activity = UpdateReadingActivity(readingActivityClient,
						activity);

			} else {
				Tag defaultTag = null;
				Template defaultTemplate = null;
				Professor owner = findProfessor(readingActivityClient
						.getProfessor().getId());

				activity = new ReadingActivity(readingActivityClient.getName(),
						owner, null, null, null, null, null,
						Constants.VISUAL_KEY, defaultTemplate, (short) 1,
						(short) 1, defaultTag);

				owner.getReadingActivities().add(activity);

			}
		} catch (ReadingActivityNotFoundException e) {
			e.printStackTrace();
		} catch (BookNotFoundException e) {
			e.printStackTrace();
		} catch (CatalogoNotFoundException e) {
			e.printStackTrace();
		} catch (GroupNotFoundException e) {
			e.printStackTrace();
		} catch (TemplateNotFoundException e) {
			e.printStackTrace();
		} catch (ProfessorNotFoundException e) {
			e.printStackTrace();
		} catch (TagNotFoundException e) {
			e.printStackTrace();
		}
		saveReadingActivity(activity);
	}

	private void saveReadingActivity(ReadingActivity readingActivity) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			userTransaction.begin();
			if (readingActivity.getId() == null) {
				entityManager.persist(readingActivity);
			} else {
				entityManager.merge(readingActivity);
				for (Annotation annotation : readingActivity.getAnnotations()) {
					// for (Tag tag : annotation.getTags()) {
					// entityManager.merge(tag);
					// }
					entityManager.merge(annotation);
				}
			}
			entityManager.merge(readingActivity.getProfessor());
			entityManager.flush();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

	private ReadingActivity UpdateReadingActivity(
			ReadingActivityClient readingActivityClientEntrada,
			ReadingActivity readingActivitySalida)
			throws BookNotFoundException, CatalogoNotFoundException,
			GroupNotFoundException, TemplateNotFoundException,
			TagNotFoundException {

		// privacy
		short privacy = (readingActivityClientEntrada.isPrivacy() ? (short) 1
				: 0);
		readingActivitySalida.setPrivacy(privacy);
		// Lenguaje
		readingActivitySalida.setLanguage(readingActivityClientEntrada
				.getLanguage());

		// Capacidad de template vacio
		if (readingActivityClientEntrada.getIsFreeTemplateAllowed())
			readingActivitySalida.setIsFreeTemplateAllowed((short) 1);
		else
			readingActivitySalida.setIsFreeTemplateAllowed((short) 0);

		// Libro = Si Cambia Borrar las anotaciones asociadas a la actividad.
		if ((readingActivityClientEntrada.getBook() != null)
				&& ((readingActivitySalida.getBook() == null) || (!readingActivityClientEntrada
						.getBook().getId()
						.equals(readingActivitySalida.getBook().getId())))) {
			if (readingActivitySalida.getAnnotations() != null) {
				for (Annotation annotation : readingActivitySalida
						.getAnnotations()) {
					annotation.setActivity(null);
				}
				readingActivitySalida.getAnnotations().clear();
				// readingActivitySalida.setAnnotations(null);
			}

			readingActivitySalida.setBook(findBook(readingActivityClientEntrada
					.getBook().getId()));
		}

		// Catalogo Cerrado = Si Cambia Borrar las anotaciones asociadas a la
		// actividad.
		if ((readingActivityClientEntrada.getCloseCatalogo() != null)
				&& ((readingActivitySalida.getCloseCatalogo() == null) || (!readingActivityClientEntrada
						.getCloseCatalogo()
						.getId()
						.equals(readingActivitySalida.getCloseCatalogo()
								.getId())))) {

			if (readingActivitySalida.getAnnotations() != null) {
				for (Annotation annotation : readingActivitySalida
						.getAnnotations()) {
					for (Tag tag : annotation.getTags()) {
						tag.getAnnotations().remove(annotation);
					}
					annotation.getTags().clear();
				}
				// readingActivitySalida.getAnnotations().clear();
				// readingActivitySalida.setAnnotations(null);
			}

			readingActivitySalida
					.setCloseCatalogo(findCatalogo(readingActivityClientEntrada
							.getCloseCatalogo().getId()));
		}

		// Group= si cambia el grupo las anotaciones pasan a ser del usuario
		// nulo, o del profesor, hay que mirarlo,
		// lo que es seguro es que se borran las privadas.
		if ((readingActivityClientEntrada.getGroup() != null)
				&& ((readingActivitySalida.getGroup() == null) || (!readingActivityClientEntrada
						.getGroup().getId()
						.equals(readingActivitySalida.getGroup().getId())))) {
			if (readingActivitySalida.getAnnotations() != null) {
				for (Annotation annotation : readingActivitySalida
						.getAnnotations()) {
					annotation.setActivity(null);
				}
				readingActivitySalida.getAnnotations().clear();
				// readingActivitySalida.setAnnotations(null);
			}

			readingActivitySalida
					.setGroup(findGroup(readingActivityClientEntrada.getGroup()
							.getId()));
		}

		// Nombre
		readingActivitySalida.setName(readingActivityClientEntrada.getName());

		// Catalogo Abierto = Si Cambia Borrar las anotaciones asociadas a la
		// actividad.
		if ((readingActivityClientEntrada.getOpenCatalogo() != null)
				&& ((readingActivitySalida.getOpenCatalogo() == null) || (!readingActivityClientEntrada
						.getOpenCatalogo()
						.getId()
						.equals(readingActivitySalida.getOpenCatalogo().getId())))) {
			if (readingActivitySalida.getAnnotations() != null) {
				for (Annotation annotation : readingActivitySalida
						.getAnnotations()) {
					for (Tag tag : annotation.getTags()) {
						tag.getAnnotations().remove(annotation);
					}
					annotation.getTags().clear();
				}
				// readingActivitySalida.getAnnotations().clear();
				// readingActivitySalida.setAnnotations(null);
			}
			readingActivitySalida
					.setOpenCatalogo(findCatalogo(readingActivityClientEntrada
							.getOpenCatalogo().getId()));
		}

		// Template
		if ((readingActivityClientEntrada.getTemplate() != null)
				&& ((readingActivitySalida.getTemplate() == null) || (readingActivityClientEntrada
						.getTemplate().getId().equals(readingActivitySalida
						.getTemplate().getId()))))
			readingActivitySalida
					.setTemplate(findTemplate(readingActivityClientEntrada
							.getTemplate().getId()));

		// Visualizacion
		readingActivitySalida.setVisualization(readingActivityClientEntrada
				.getVisualization());

		// Default Type
		if (readingActivityClientEntrada.getDefaultType() != null
				&& ((readingActivitySalida.getDefultTag() == null) || !readingActivityClientEntrada
						.getDefaultType().equals(
								readingActivitySalida.getDefultTag().getId()))) {
			Tag tag = findTag(readingActivityClientEntrada.getDefaultType());
			readingActivitySalida.setDefultTag(tag);
		}

		return readingActivitySalida;
	}

	private ReadingActivity findReadingActivity(Long id)
			throws ReadingActivityNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		ReadingActivity a = entityManager.find(ReadingActivity.class, id);
		if (a == null) {
			throw new ReadingActivityNotFoundException(
					"Student not found in method loadStudentById");
		}
		entityManager.close();
		return a;
	}

	private Template findTemplate(Long id) throws TemplateNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		Template a = entityManager.find(Template.class, id);
		if (a == null) {
			throw new TemplateNotFoundException(
					"Template not found in method loadTemplateById");
		}
		entityManager.close();
		return a;
	}

	// private void saveStudent(StudentClient pClient)
	// throws UserNotFoundException {
	// boolean isNew = false;
	// Student oldStudent = null;
	// UserApp user = null;
	// try {
	// if (pClient.getId() != null) {
	// oldStudent = findStudent(pClient.getId());
	// } else {
	//
	// try {
	// user = loadUserByEmail(pClient.getEmail());
	// } catch (UserNotFoundException e) {
	// user = null;
	// }
	//
	// if (user != null) {
	// throw new GeneralException(
	// "Email already registered in the application");
	// }
	// Date now = new Date();
	// Calendar calendar = Calendar.getInstance();
	// now = calendar.getTime();
	// saveUser(new Student(pClient.getId(), pClient.getFirstName(),
	// pClient.getLastName(), pClient.getEmail(),
	// pClient.getPassword(), now));
	// isNew = true;
	// }
	//
	// } catch (StudentNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (GeneralException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// if (!isNew) {
	// boolean isThereAChange = comparareStudents(oldStudent, pClient);
	// if (isThereAChange) {
	// try {
	// saveUser(oldStudent);
	// } catch (GeneralException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	// }

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
															// m�todo de
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
			Language language = entityManager.find(Language.class, languageId);
			userTransaction.begin();
			List<ReadingActivity> activities = getReadingActivitiesByLanguageId(languageId);
			for (ReadingActivity readingActivity : activities) {
				readingActivity.setLanguage(null);
				entityManager.merge(readingActivity);
			}

			entityManager.remove(language);
			entityManager.flush();
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
	public String getJSONServiceTODrawGraph(String query, String data) {
		URL url;
		URLConnection connection;
		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader;
		try {
			url = new URL(query);
			connection = url.openConnection();
			connection.addRequestProperty("Referer",
					"http://a-note.appspot.com/");
			connection.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(
					connection.getOutputStream());
			wr.write(data);
			wr.flush();
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			wr.close();
			reader.close();
		} catch (MalformedURLException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (IOException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return builder.toString();
	}

	@Override
	public void deleteBookById(Long id) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			Book book = entityManager.find(Book.class, id);
			userTransaction.begin();
			book.getProfessor().getBooks().size();
			book.getProfessor().getBooks().remove(book);
			entityManager.merge(book.getProfessor());
			book.setProfessor(null);

			List<ReadingActivity> activities = getReadingActivitiesByBookId(id);
			for (ReadingActivity readingActivity : activities) {
				readingActivity.setBook(null);
				for (Annotation annotation : readingActivity.getAnnotations()) {
					Annotation annotationToRemove = entityManager.find(
							Annotation.class, annotation.getId());
					annotationToRemove.getActivity().getAnnotations()
							.remove(annotationToRemove);
					entityManager.merge(annotationToRemove.getActivity());
					annotationToRemove.setActivity(null);
					annotationToRemove.getCreator().getAnnotations().size();
					annotationToRemove.getCreator().getAnnotations()
							.remove(annotationToRemove);
					entityManager.merge(annotationToRemove.getCreator());
					annotationToRemove.setCreator(null);

					annotationToRemove.getTags().size();
					for (Tag tag : annotationToRemove.getTags()) {
						tag.getAnnotations().remove(annotationToRemove);
					}

					annotationToRemove.setTags(null);
					entityManager.remove(annotationToRemove);

				}
				readingActivity.setAnnotations(null);
				entityManager.merge(readingActivity);
			}

			entityManager.remove(book);
			entityManager.flush();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException(
					"Exception in sss method vvvdeleteBookById      "
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
		if (list == null) {
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
		List<Book> list = new ArrayList<Book>();
		if (ids == null || ids.isEmpty()) {
			return new ArrayList<BookClient>();
		}

		String sql = "SELECT r FROM Book r WHERE r.id=" + ids.get(0);
		for (int i = 1; i < ids.size(); i++) {
			sql += " OR r.id=" + ids.get(i);
		}

		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadBookById: ", e)
			throw new GeneralException("Exception in method loadBookById:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null) {
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

	private void sendEmail(String email, String code) {

		boolean debug = true;
		// Setup mail server
		try {

			Properties props = System.getProperties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.starttls.enable", "true"); // added this line
			props.put("mail.smtp.port", "587"); // smtp port
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.auth", "true");

			Authenticator auth = new javax.mail.Authenticator() {

				@Override
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(Constants.MAIL_SENDER,
							Constants.SENDER_PASS);
				}
			};

			// Get a mail session
			// Session session = Session.getDefaultInstance(props, auth);
			Session session = Session.getInstance(props, auth);
			session.setDebug(debug);
			// Define a new mail message
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(Constants.MAIL_SENDER_ADDRESS,
					"AtNote"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					email));
			message.setSubject(Constants.MAIL_SUBJECT);

			// Create a message part to represent the body text
			// BodyPart messageBodyPart = new MimeBodyPart();
			// messageBodyPart.setText(messageBody);

			// String mail = "<strong>Nombre:</strong>" + name + "<br/>"
			//
			// + "<strong>e-mail:</strong>" + email + "<br/><br/><br/>";
			// String messageBody = "Mensaje cuerpo de la historia";
			// String mail = "hola   ";
			String mail = "<strong>"
					+ Constants.MAIL_BODY_TEXT
					+ "</strong><br/><br/>Click <a href=\"http://a-note.fdi.ucm.es/GlassAtNote/Validator.php?confirmationCode="
					+ code + "\">here</a> to confirm access to Atnote";
			message.setContent(mail, "text/html");
			// Send the message

			Transport.send(message);
		} catch (MessagingException ex) {
			System.out.println(ex);
		} catch (UnsupportedEncodingException uee) {
			System.out.println(uee);
		}
	}

	private String generateCode() {
		long offset = Timestamp.valueOf("2012-01-01 00:00:00").getTime();
		long end = Timestamp.valueOf("2013-01-01 00:00:00").getTime();
		long diff = end - offset + 1;
		Timestamp rand = new Timestamp(offset + (long) (Math.random() * diff));
		return rand.toString();
	}

	long offset = Timestamp.valueOf("2012-01-01 00:00:00").getTime();
	long end = Timestamp.valueOf("2013-01-01 00:00:00").getTime();
	long diff = end - offset + 1;
	Timestamp rand = new Timestamp(offset + (long) (Math.random() * diff));

}
