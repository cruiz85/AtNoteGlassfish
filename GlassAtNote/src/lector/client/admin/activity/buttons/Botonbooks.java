package lector.client.admin.activity.buttons;

import lector.share.model.client.BookClient;

import com.google.gwt.user.client.ui.Button;

public class Botonbooks extends Button {
 
	
	private BookClient book;
	
	public Botonbooks(BookClient bookin) {
		super(bookin.getTitle());
		book=bookin;
		
	}
	
	
	public BookClient getBook() {
		return book;
	}
	
	public void setBook(BookClient book) {
		this.book = book;
	}
	
}
