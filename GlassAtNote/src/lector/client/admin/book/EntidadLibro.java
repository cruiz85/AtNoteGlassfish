package lector.client.admin.book;

import lector.client.catalogo.client.Entity;
import lector.share.model.client.BookClient;

public class EntidadLibro extends Entity {

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
