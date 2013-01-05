package lector.share.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import lector.share.model.Annotation;
import lector.share.model.Professor;

@Entity
@Table(name = "book")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String author;
	private String title;
	private String ISBN;
	private String pagesCount;
	private String publishedYear;
	@ManyToOne
	private Professor professor;
	// private String tbURL; de Google
	// private String imagesPath;
	// private String url;
	private int annotationsCount = 0;
	private List<String> webLinks = new ArrayList<String>();
	
	public Book() {
	}

	public Book(Professor professor, String author, String ISBN,
			String pagesCount, String publishedYear, String title) {
		this.professor = professor;
		this.author = author;
		this.ISBN = ISBN;
		this.pagesCount = pagesCount;
		this.publishedYear = publishedYear;
		this.title = title;

	}

	public int getAnnotationsCount() {
		return annotationsCount;
	}

	public void setAnnotationsCount(int annotationsCount) {
		this.annotationsCount = annotationsCount;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getPagesCount() {
		return pagesCount;
	}

	public void setPagesCount(String pagesCount) {
		this.pagesCount = pagesCount;
	}

	public String getPublishedYear() {
		return publishedYear;
	}

	public void setPublishedYear(String publishedYear) {
		this.publishedYear = publishedYear;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getWebLinks() {
		return webLinks;
	}

	public void setWebLinks(List<String> webLinks) {
		this.webLinks = webLinks;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}