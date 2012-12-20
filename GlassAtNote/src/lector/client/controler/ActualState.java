package lector.client.controler;


import java.util.Locale;

import javax.security.auth.callback.LanguageCallback;

import com.google.gwt.user.client.Window;


import lector.client.admin.Administrador;
import lector.share.model.Language;
import lector.share.model.client.BookClient;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.ReadingActivityClient;
import lector.share.model.client.StudentClient;
import lector.share.model.client.UserClient;

public class ActualState {

	private static UserClient User;
	private static ReadingActivityClient readingactivity;
	private static Language ActualLanguage;

	public static UserClient getUser() {
		return User;
	}

	public static void setUser(UserClient user) {
		User = user;

	
	}

//	public static String getRol() {
//		if (User instanceof StudentClient)
//		return "Student";
//		else if (User instanceof ProfessorClient)
//			return "Professor";
//		else Window.Location.reload();
//		return null;
//	}

	
	public static BookClient getReadingActivityBook() {
		if (readingactivity==null) return null;
		return readingactivity.getBook();
	}
	
	public static void setReadingActivityBook(BookClient book) {
		if (readingactivity!=null)
			readingactivity.setBook(book);
	}
	
	public static CatalogoClient getReadingActivityCloseCatalog() {
		if (readingactivity==null) return null;
		return readingactivity.getCloseCatalogo();
	}
	
	public static Language getLanguage() {
		if (readingactivity==null) return null;
		return readingactivity.getLanguage();
	}
	
	public static void setReadingActivityCloseCatalog(CatalogoClient catalogo) {
		if (readingactivity!=null) readingactivity.setCloseCatalogo(catalogo);
	}
	
	public static void setLanguage(Language language) {
		if (readingactivity!=null) readingactivity.setLanguage(language);
	}
	
	public static ReadingActivityClient getReadingactivity() {
		if (readingactivity==null) return null;
		return readingactivity;
	}
	
	public static void setReadingactivity(ReadingActivityClient readingactivity) {
		ActualState.readingactivity = readingactivity;
	}
	
	public static CatalogoClient getReadingActivityOpenCatalog() {
		if (readingactivity==null) return null;
		return readingactivity.getOpenCatalogo();
	}
	
	public static void setReadingActivityOpenCatalog(CatalogoClient openCatalog) {
		if (readingactivity!=null) readingactivity.setOpenCatalogo(openCatalog);
	}
	
	public static Language getActualLanguage() {
		return ActualLanguage;
	}
	
	public static void setActualLanguage(Language actualLanguage) {
		if (ActualLanguage != actualLanguage)
		{
		ActualLanguage = actualLanguage;
		ChangeLanguage();
		}
	}

	private static void ChangeLanguage() {
		//Reaccion en cadena a todos los elementos con Lenguaje editable
		//Administrador.FromFile(Entrada);
	}

	public static boolean isLanguageActive() {
		//return ActualLanguage!=null;
		return true;
	}

	public static void saveLanguageActual(Language languageActual) {
		// TODO salva el Lenguaje
		
	}
	
}
