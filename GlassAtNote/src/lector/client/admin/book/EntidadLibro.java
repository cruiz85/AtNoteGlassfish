package lector.client.admin.book;

import lector.client.controler.EntitdadObject;
import lector.share.model.client.BookClient;

public class EntidadLibro extends EntitdadObject {

	private BookClient book;
	
	public EntidadLibro(BookClient bookin) {
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
