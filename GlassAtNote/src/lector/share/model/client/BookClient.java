package lector.share.model.client;

import java.util.ArrayList;
import java.util.List;


public class BookClient{
	


	private Long id;
	private String author;
	private String title;
	private String ISBN;
	private String pagesCount;
	private String publishedYear;
	private Long professor;
	private List<String> webLinks = new ArrayList<String>();

	public BookClient() {
	}

	public BookClient(Long professor, String author, String ISBN,
			String pagesCount, String publishedYear, String title, List<String> webLinks) {
		this.professor = professor;
		this.author = author;
		this.ISBN = ISBN;
		this.pagesCount = pagesCount;
		this.publishedYear = publishedYear;
		this.title = title;
		this.webLinks = webLinks;

	}


	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Long getProfessor() {
		return professor;
	}

	public void setProfessor(Long professor) {
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

}