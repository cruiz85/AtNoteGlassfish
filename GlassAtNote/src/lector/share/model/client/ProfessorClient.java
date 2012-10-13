package lector.share.model.client;


import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ProfessorClient extends UserClient implements IsSerializable{
	private static final long serialVersionUID = 1L;

	private List<Long> readingActivities = new ArrayList<Long>();

	private List<Long> books = new ArrayList<Long>();

	private List<Long> templates = new ArrayList<Long>();

	private List<Long> groups = new ArrayList<Long>();

	public ProfessorClient() {
	}

	
	public ProfessorClient(Long id, String firstName, String lastName,
			String email, boolean loggedIn, String loginUrl, String logoutUrl,
			boolean isAuthenticated, List<Long> readingActivities, List<Long> books, List<Long> templates, List<Long> groups) {
		super(id, firstName, lastName, email, loggedIn, loginUrl, logoutUrl,
				isAuthenticated);
		this.readingActivities = readingActivities;
		this.books = books;
		this.templates = templates;
		this.groups = groups;
	}


	public ProfessorClient(String email) {
		super(email);
	}

	public List<Long> getReadingActivities() {
		return readingActivities;
	}

	public void setReadingActivities(List<Long> readingActivities) {
		this.readingActivities = readingActivities;
	}

	public List<Long> getTemplates() {
		return templates;
	}

	public void setTemplates(List<Long> templates) {
		this.templates = templates;
	}

	public List<Long> getGroups() {
		return groups;
	}

	public void setGroups(List<Long> groups) {
		this.groups = groups;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Long> getBooks() {
		return books;
	}

	public void setBooks(List<Long> books) {
		this.books = books;
	}

}
