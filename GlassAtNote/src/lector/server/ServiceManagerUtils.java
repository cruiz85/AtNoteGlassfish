package lector.server;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.EntityTransaction;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import lector.share.model.Annotation;
import lector.share.model.Book;
import lector.share.model.GoogleBook;
import lector.share.model.GroupApp;
import lector.share.model.LocalBook;
import lector.share.model.Professor;
import lector.share.model.ReadingActivity;
import lector.share.model.Student;
import lector.share.model.Template;
import lector.share.model.TemplateCategory;
import lector.share.model.TextSelector;
import lector.share.model.UserApp;
import lector.share.model.client.AnnotationClient;
import lector.share.model.client.BookClient;
import lector.share.model.client.GoogleBookClient;
import lector.share.model.client.GroupClient;
import lector.share.model.client.LocalBookClient;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.StudentClient;
import lector.share.model.client.TextSelectorClient;
import lector.share.model.client.TypeClient;
import lector.share.model.client.UserClient;


public class ServiceManagerUtils {
//	public static void rollback(EntityTransaction entityTransaction,
//			Logger logger) {
//		try {
//			entityTransaction.rollback();
//		} catch (Exception e) {
//			logger.warn("Error making a rollback:", e);
//		}
//
//	}

	public static void rollback(UserTransaction userTransaction) {
		try {
			userTransaction.rollback();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String removeSpaces(String s) {
		StringTokenizer st = new StringTokenizer(s, " ", false);
		String t = "";
		while (st.hasMoreElements()) {
			t += st.nextElement();
		}
		return t;
	}

	//TODO hacer cuando se tenga lo que pasará con los tgas
//	public static AnnotationClient produceAnnotationClient(Annotation a) {
//		boolean visibility = false;
//		boolean updatability = false;
//		if (a.getVisibility() == 1) {
//			visibility = true;
//		}
//		if (a.getUpdatability() == 1) {
//			updatability = true;
//		}
//		AnnotationClient annotationClient = new AnnotationClient(
//				produceUserClient(a.getCreator()), a.getActivity().getId(),
//				produceTextSelectors(a.getTextSelectors()), a.getComment(),
//				a.getBookId(), visibility, updatability, a.getPageNumber(),
//				produceTypeClients(a.getTags()), a.isEditable());
//	}
//
//	public static List<TypeClient> produceTypeClients(List<Tag> tags) {
//		List<TypeClient> typeClients = new ArrayList<TypeClient>();
//		for (Tag tag : tags) {
//			typeClients.add(produceTypeClient);
//		}
//	}
//
//	private static TypeClient produceTypeClient(Tag t){
//		return new T
//	}
	
	public static LocalBookClient produceLocalBookClient(LocalBook lb){
		return new LocalBookClient(lb.getProfessor().getId(), lb.getAuthor(), lb.getISBN(), lb.getPagesCount(), lb.getPublishedYear(), lb.getTitle(), produceListString(lb.getWebLinks()));
	}
	
	public static GroupClient produceGroupClient(GroupApp g){
		return new GroupClient(g.getId(), g.getName(), produceProfessorClient(g.getProfessor()),produceStudentClients(g.getParticipatingStudents()),produceStudentClients(g.getRemainingStudents()));
	}

	public static ProfessorClient produceProfessorClient(Professor p) {

		return new ProfessorClient(p.getId(), p.getFirstName(),
				p.getLastName(), p.getEmail(), p.isLoggedIn(), p.getLoginUrl(),
				p.getLogoutUrl(), p.isAuthenticated(),
				getReadingActivityIds(p.getReadingActivities()), getBooksIds(p.getBooks()),getTemplatesIds(p.getTemplates()),
				getGroupsIds(p.getGroups()));
	}

	private static List<Long> getGroupsIds(List<GroupApp> g){
		List<Long> ids = new ArrayList<Long>();
		for (GroupApp group : g) {
			ids.add(group.getId());
		}
		return ids;
	}
	private static List<Long> getReadingActivityIds(List<ReadingActivity> g){
		List<Long> ids = new ArrayList<Long>();
		for (ReadingActivity readingActivity : g) {
			ids.add(readingActivity.getId());
		}
		return ids;
	}
	private static List<Long> getBooksIds(List<Book> g){
		List<Long> ids = new ArrayList<Long>();
		for (Book book : g) {
			ids.add(book.getId());
		}
		return ids;
	}
	
	private static List<Long> getTemplatesIds(List<Template> g){
		List<Long> ids = new ArrayList<Long>();
		for (Template template : g) {
			ids.add(template.getId());
		}
		return ids;
	}
	
	private static List<StudentClient> produceStudentClients(List<Student> s) {
		List<StudentClient> studentClients = new ArrayList<StudentClient>();
		for (Student student : s) {
			studentClients.add(produceStudentClient(student));
		}
		return studentClients;
	}

	private static StudentClient produceStudentClient(Student student) {
		return new StudentClient(student.getId(), student.getFirstName(),
				student.getLastName(), student.getEmail(),
				student.isLoggedIn(), student.getLoginUrl(),
				student.getLogoutUrl(), student.isAuthenticated());
	}

	public static GoogleBookClient produceGoogleBookClient(GoogleBook gb) {

		return new GoogleBookClient(gb.getAuthor(), gb.getISBN(),
				gb.getPagesCount(), gb.getPublishedYear(), gb.getTitle(),
				gb.getTbURL(), gb.getUrl(), gb.getWebLinks());
	}

	public static BookClient produceBookClient(Book b) {
		return new BookClient(b.getProfessor().getId(), b.getAuthor(),
				b.getISBN(), b.getPagesCount(), b.getPublishedYear(),
				b.getTitle(), produceListString(b.getWebLinks()));
	}

	private static List<String> produceListString(List<String> strings) {
		List<String> cleanStrings = new ArrayList<String>();
		for (String string : strings) {
			cleanStrings.add(string);
		}
		return cleanStrings;
	}

	public static List<TextSelectorClient> produceTextSelectors(
			List<TextSelector> ts) {
		List<TextSelectorClient> textSelectorClients = new ArrayList<TextSelectorClient>();
		for (TextSelector textSelector : ts) {
			textSelectorClients.add(produceTextSelector(textSelector));
		}
		return textSelectorClients;
	}

	private static TextSelectorClient produceTextSelector(TextSelector tx) {
		return new TextSelectorClient(tx.getX(), tx.getY(), tx.getWidth(),
				tx.getHeight());
	}

	public static UserClient produceUserClient(UserApp u) {
		return new UserClient(u.getId(), u.getFirstName(), u.getLastName(),
				u.getEmail(), u.isLoggedIn(), u.getLoginUrl(),
				u.getLogoutUrl(), u.isAuthenticated());
	}

	public static void cleanTemplate(Template template) {
		List<TemplateCategory> templateCategories = new ArrayList<TemplateCategory>();
		for (TemplateCategory templateCategory : template.getCategories()) {
			templateCategories.add(templateCategory);
		}
		template.setCategories(templateCategories);
	}

	public static void cleanBook(Book book) {
		if (book instanceof GoogleBook) {
			cleanGoogleBook((GoogleBook) book);
		}

	}

	private static void cleanGoogleBook(GoogleBook googleBook) {
		List<Annotation> annotations = new ArrayList<Annotation>();
		for (Annotation annotation : googleBook.getAnnotations()) {
			annotations.add(annotation);
		}
		googleBook.setAnnotations(annotations);
		List<String> webLinks = new ArrayList<String>();
		for (String googleBookApp : googleBook.getWebLinks()) {
			webLinks.add(googleBookApp);
		}
		googleBook.setWebLinks(webLinks);
	}

	public static void cleanGroup(GroupApp group) {

		List<Student> participatingStudents = new ArrayList<Student>();
		for (Student groupApp : group.getParticipatingStudents()) {
			participatingStudents.add(groupApp);
		}
		group.setParticipatingStudents(participatingStudents);
		List<Student> remainingStudents = new ArrayList<Student>();
		for (Student groupApp : group.getParticipatingStudents()) {
			remainingStudents.add(groupApp);
		}
		group.setParticipatingStudents(remainingStudents);
	}

	public static void cleanUser(UserApp user) {
		if (user instanceof Professor) {
			cleanProfessor((Professor) user);
		} else {
			cleanStudent((Student) user);
		}
	}

	private static void cleanStudent(Student student) {
		List<GroupApp> groupApps = new ArrayList<GroupApp>();
		for (GroupApp groupApp : student.getParticipatingGroups()) {
			groupApps.add(groupApp);
		}
		student.setParticipatingGroups(groupApps);
	}

	private static void cleanProfessor(Professor professor) {
		List<Annotation> annotations = new ArrayList<Annotation>();
		for (Annotation annotation : professor.getAnnotations()) {
			annotations.add(annotation);
		}
		professor.setAnnotations(annotations);
		List<Book> books = new ArrayList<Book>();
		for (Book book : professor.getBooks()) {
			books.add(book);
		}
		professor.setBooks(books);
		List<ReadingActivity> readingActivitys = new ArrayList<ReadingActivity>();
		for (ReadingActivity readingActivity : professor.getReadingActivities()) {
			readingActivitys.add(readingActivity);
		}
		professor.setReadingActivities(readingActivitys);
		List<GroupApp> groupApps = new ArrayList<GroupApp>();
		for (GroupApp groupApp : professor.getGroups()) {
			groupApps.add(groupApp);
		}
		professor.setGroups(groupApps);
		List<Template> templates = new ArrayList<Template>();
		for (Template template : professor.getTemplates()) {
			templates.add(template);
		}
		professor.setTemplates(templates);
	}

}
