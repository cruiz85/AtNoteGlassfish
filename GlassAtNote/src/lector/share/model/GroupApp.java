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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "group_app")
public class GroupApp implements Serializable, IsSerializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@ManyToOne
	private Professor professor;
	@ManyToMany(cascade = CascadeType.ALL)
	//@JoinTable(name = "group_user", joinColumns = { @JoinColumn(name = "groupId", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "userId", referencedColumnName = "id") })
	private List<Student> participatingStudents = new ArrayList<Student>();

	@ManyToMany(cascade = CascadeType.ALL)
	private List<Student> remainingStudents = new ArrayList<Student>();


	public GroupApp() {
	
	}

	public GroupApp(String name) {
		this();
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public List<Student> getParticipatingStudents() {
		return participatingStudents;
	}

	public void setParticipatingStudents(List<Student> participatingStudents) {
		this.participatingStudents = participatingStudents;
	}

	public List<Student> getRemainingStudents() {
		return remainingStudents;
	}

	public void setRemainingStudents(List<Student> remainingStudents) {
		this.remainingStudents = remainingStudents;
	}

	
}
