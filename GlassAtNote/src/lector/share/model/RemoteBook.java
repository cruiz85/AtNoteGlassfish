package lector.share.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import lector.share.model.Book;

@Entity
@Table(name = "remoteBook")
public class RemoteBook extends Book implements Serializable {
	private static final long serialVersionUID = 1L;

	private String libraryName;

	public RemoteBook() {
		super();
	}

	public String getLibraryName() {
		return libraryName;
	}

	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}

}
