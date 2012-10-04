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
		super();
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
