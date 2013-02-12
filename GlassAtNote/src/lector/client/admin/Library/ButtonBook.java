package lector.client.admin.Library;

import com.google.gwt.user.client.ui.Button;

import lector.share.model.client.BookClient;

public class ButtonBook extends Button{

	BookClient Libro;
	
	public ButtonBook(BookClient bookClient) {
		super(bookClient.getTitle());
		Libro=bookClient;
	}
	
	public BookClient getLibro() {
		return Libro;
	}
	
	public void setLibro(BookClient libro) {
		Libro = libro;
	}
	

}
