package lector.share.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "student")
@XmlRootElement
public class Student extends UserApp implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManyToMany
	private List<GroupApp> participatingGroups = new ArrayList<GroupApp>();

	public Student() {
	}

	public Student(Long id, String firstName, String lastName, String email,
			String password,Date createdDate) {
		super(id, firstName, lastName, email, password,createdDate);
		// TODO Auto-generated constructor stub
	}

	public Student(String email) {
		super(email);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<GroupApp> getParticipatingGroups() {
		return participatingGroups;
	}

	public void setParticipatingGroups(List<GroupApp> participatingGroups) {
		this.participatingGroups = participatingGroups;
	}

}
