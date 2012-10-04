package lector.client.login;


import com.google.gwt.user.client.Window;

import lector.share.model.Book;
import lector.share.model.Catalogo;
import lector.share.model.Language;
import lector.share.model.Professor;
import lector.share.model.ReadingActivity;
import lector.share.model.Student;
import lector.share.model.UserApp;

public class ActualUser {

	private static UserApp User;
	private static ReadingActivity readingactivity;

	public static UserApp getUser() {
		return User;
	}

	public static void setUser(UserApp user) {
		User = user;

	
	}

	public static String getRol() {
		if (User instanceof Student)
		return "Student";
		else if (User instanceof Professor)
			return "Professor";
		else Window.Location.reload();
		return null;
	}

	
	public static Book getBook() {
		return readingactivity.getBook();
	}
	
	public static void setBook(Book book) {
		readingactivity.setBook(book);
	}
	
	public static Catalogo getCatalogo() {
		return readingactivity.getCloseCatalogo();
	}
	
	public static Language getLanguage() {
		return readingactivity.getLanguage();
	}
	
	public static void setCatalogo(Catalogo catalogo) {
		readingactivity.setCloseCatalogo(catalogo);
	}
	
	public static void setLanguage(Language language) {
		readingactivity.setLanguage(language);
	}
	
	public static ReadingActivity getReadingactivity() {
		return readingactivity;
	}
	
	public static void setReadingactivity(ReadingActivity readingactivity) {
		ActualUser.readingactivity = readingactivity;
	}
	
	public static Catalogo getOpenCatalog() {
		return readingactivity.getOpenCatalogo();
	}
	
	public static void setOpenCatalog(Catalogo openCatalog) {
		readingactivity.setOpenCatalogo(openCatalog);
	}
	
}
