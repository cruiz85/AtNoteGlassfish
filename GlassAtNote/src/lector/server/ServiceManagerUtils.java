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
import lector.share.model.Professor;
import lector.share.model.ReadingActivity;
import lector.share.model.Student;
import lector.share.model.Template;
import lector.share.model.TemplateCategory;
import lector.share.model.UserApp;

import org.apache.logging.log4j.Logger;

public class ServiceManagerUtils {
	public static void rollback(EntityTransaction entityTransaction,
			Logger logger) {
		try {
			entityTransaction.rollback();
		} catch (Exception e) {
			logger.warn("Error making a rollback:", e);
		}

	}

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
