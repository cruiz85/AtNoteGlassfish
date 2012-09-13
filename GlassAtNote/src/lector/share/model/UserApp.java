package lector.share.model;


import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import lector.client.controler.Constants;


@Entity
@Table(name = "user_app")
@XmlRootElement
public class UserApp implements Serializable, IsSerializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String lastName;
	private String DNI;
	private String email;
	private String profile;
	@ManyToMany(mappedBy = "users")
	private List<GroupApp> groups = new ArrayList<GroupApp>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Annotation> annotations = new ArrayList<Annotation>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<ReadingActivity> readingActivities = new ArrayList<ReadingActivity>();
	private ArrayList<String> bookIds; // SOLO PARA PROFESORES. 
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Template> templates = new ArrayList<Template>();
	// DATOS DE GOOGLE.
	@Transient
	private boolean loggedIn = false;
	@Transient
	private String loginUrl;
	@Transient
	private String logoutUrl;
	@Transient
	private boolean isAuthenticated = true;

	public UserApp() {
	}

	public UserApp(String email, String profile) {
		this();
		this.email = email;
		this.profile = profile;
		if (profile.equals(Constants.PROFESSOR)) {
			this.bookIds = new ArrayList<String>();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public ArrayList<String> getBookIds() {
		return bookIds;
	}

	public void setBookIds(ArrayList<String> bookIds) {
		this.bookIds = bookIds;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String DNI) {
		this.DNI = DNI;
	}

	public boolean isIsAuthenticated() {
		return isAuthenticated;
	}

	public void setIsAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	public List<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

	@Override
	public String toString() {
		return "name:" + name + " lastName:" + lastName;
	}

}
