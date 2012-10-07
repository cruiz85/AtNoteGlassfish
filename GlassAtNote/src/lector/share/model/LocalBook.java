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

}
