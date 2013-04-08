/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lector.client.controler;

import com.google.gwt.resources.client.ImageResource;

public class Constants {

	public static Long ALL = Long.MIN_VALUE;
	public static final Long CATALOGID = -1l;
	public static final Long THREADFATHERNULLID = -1l;
	public static final Long TEMPLATEID=-1l;
	public static String LOCAL_BOOK_PATH="LocalBooks.jpg";
	// USERS
	public static String PROFESSOR = "Professor";
	public static String STUDENT = "Student";
	public static String GUEST = "Guest";
	public static String ANNOTATION_PRIVATE = "Private";
	public static String ANNOTATION_PUBLIC = "Public";
	public static String ANNOTATION_RESTRICTED = "Restricted";
	public static String ANNOTATION_UPDATE_PRIVATE = "Private";
	public static String ANNOTATION_UPDATE_PUBLIC = "Public";
	public static String ANNOTATION_UPDATE_RESTRICTED = "Restricted";
	public static String BREAKER = "-";
	public static String COOKIE_NAME="User";
	
	// BOOK_BLOBS PARAMETERS
	public static String BLOB_UPLOADER = "uploader";
	public static String BLOB_AUTHOR = "author";
	public static String BLOB_PUBLISHED_YEAR = "publishedyear";
	public static String BLOB_TITLE = "title";
	public static String ISBN = "ISBN";
	public static String PAGES_COUNT = "pagesCount";
	
	
	// VISUALIZACION
	public static String VISUAL_ARBOL ="Visualization in Tree";
	public static String VISUAL_KEY="Visualization in Keys";
	
	
	
	//TAMANOS
	public static final String TAMANO_PANEL_EDICION="25px";
	public static int TAMANO_PANEL_EDICION_INT=25;
	public static final String PX = "px";
	public static final int TAMANOBOTOBEDITOFF = 33;
	public static final int TAMANOBOTOBEDITON = 65;
	
	
	//IMAGENES
	public static final String FREE_ICON = "Free.gif";
	public static final String P100 = "100%";
	
	//EMAILS
	public static final String MAIL_SENDER = "at.note.mail.service";
	public static final String SENDER_PASS = "1234567ba";
	public static final String MAIL_SENDER_ADDRESS = "at.note.mail.service@gmail.com";
	public static final String FROM_NAME = "CENTRO DE REGISTRO ATNOTE";
	public static final String MAIL_SUBJECT = "Gracias por registrarse en @note...";
	public static final String MAIL_BODY_TEXT = "Gracias por registrarse en @note. <br /> Este correo usted lo ha recibido" +
			" por motivo de una verificación de cuenta, si usted desconoce el emisor de este correo haga caso omiso, sino, por favor pulse el link que se encuentra debajo";
	
}
