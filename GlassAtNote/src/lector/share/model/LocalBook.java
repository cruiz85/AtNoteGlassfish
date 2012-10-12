package lector.share.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import lector.share.model.Book;

@Entity
@Table(name = "local_book")
public class LocalBook extends Book implements Serializable {

	public LocalBook() {
		super();
	}

	public LocalBook(Professor professor, String author, String ISBN,
			String pagesCount, String publishedYear, String title) {
		super(professor, author, ISBN, pagesCount, publishedYear, title);
		// TODO Auto-generated constructor stub
	}

}
