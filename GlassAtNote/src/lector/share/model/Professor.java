package lector.share.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import lector.share.model.Book;

@Entity
@Table(name = "professor")
public class Professor extends UserApp implements Serializable {
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
	private List<ReadingActivity> readingActivities = new ArrayList<ReadingActivity>();

	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
	private List<Book> books = new ArrayList<Book>();

	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
	private List<Template> templates = new ArrayList<Template>();

	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
	private List<GroupApp> groups = new ArrayList<GroupApp>();

	public Professor() {
	}

	public Professor(Long id, String firstName, String lastName, String email,
			String password,Date createdDate) {
		super(id, firstName, lastName, email, password,createdDate);
		// TODO Auto-generated constructor stub
	}

	public Professor(String email) {
		super(email);
	}

	public List<ReadingActivity> getReadingActivities() {
		return readingActivities;
	}

	public void setReadingActivities(List<ReadingActivity> readingActivities) {
		this.readingActivities = readingActivities;
	}

	public List<Template> getTemplates() {
		return templates;
	}

	public void setTemplates(List<Template> templates) {
		this.templates = templates;
	}

	public List<GroupApp> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupApp> groups) {
		this.groups = groups;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}
