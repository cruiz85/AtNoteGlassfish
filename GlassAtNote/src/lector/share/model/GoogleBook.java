package lector.share.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lector.share.model.Annotation;
import lector.share.model.Professor;

@Entity
@Table(name = "google_book")
public class GoogleBook extends RemoteBook implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tbURL;
	private String imagesPath;
	private String url;

	public GoogleBook() {
		super("GOOGLE LIBRARY");
	}

	public GoogleBook(String author, String ISBN, String pagesCount,
			String publishedYear, String title, String tbURL, String unscapedURL) {
		this();
		super.setAuthor(author);
		super.setISBN(ISBN);
		super.setPagesCount(pagesCount);
		super.setPublishedYear(publishedYear);
		super.setTitle(title);
		this.tbURL = tbURL;
		this.url = unscapedURL;
	}

	public String getImagesPath() {
		return imagesPath;
	}

	public void setImagesPath(String imagesPath) {
		this.imagesPath = imagesPath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTbURL() {
		return tbURL;
	}

	public void setTbURL(String tbURL) {
		this.tbURL = tbURL;
	}

}
