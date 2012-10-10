package lector.server;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.EntityTransaction;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import lector.share.model.Annotation;
import lector.share.model.Book;
import lector.share.model.GroupApp;
import lector.share.model.Professor;
import lector.share.model.ReadingActivity;
import lector.share.model.Template;

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
	
	public static void cleanProfessor(Professor professor){
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
