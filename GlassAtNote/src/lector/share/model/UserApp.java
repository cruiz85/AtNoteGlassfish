package lector.share.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gwt.user.client.rpc.IsSerializable;

import lector.client.controler.Constants;

@Entity
@Table(name = "user_app")
public class UserApp implements Serializable, IsSerializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	@ManyToMany(mappedBy = "participatingUsers")
	private List<GroupApp> participatingGroups = new ArrayList<GroupApp>();

	@OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
	private List<Annotation> annotations = new ArrayList<Annotation>();
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date createdDate;
	private short isConfirmed = 0;

	public UserApp() {
	}

	public UserApp(Long id, String firstName, String lastName, String email,
			String password,Date createdDate) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		isConfirmed = 0;
		this.createdDate=createdDate;
	
	}

	public List<GroupApp> getParticipatingGroups() {
		return participatingGroups;
	}

	public void setParticipatingGroups(List<GroupApp> participatingGroups) {
		this.participatingGroups = participatingGroups;
	}

	public UserApp(String email) {
		super();
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

	@Override
	public String toString() {
		return "name:" + firstName + " lastName:" + lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public short getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(short isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	
}
