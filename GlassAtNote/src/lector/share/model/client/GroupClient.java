package lector.share.model.client;

import java.util.ArrayList;
import java.util.List;

public class GroupClient {

	private Long id;
	private String name;

	private ProfessorClient professor;
	private List<StudentClient> participatingStudents = new ArrayList<StudentClient>();


	private List<StudentClient> remainingStudents = new ArrayList<StudentClient>();


	public GroupClient() {
	
	}

	public GroupClient(Long id, String name, ProfessorClient professor,
			List<StudentClient> participatingStudents,
			List<StudentClient> remainingStudents) {
		super();
		this.id = id;
		this.name = name;
		this.professor = professor;
		this.participatingStudents = participatingStudents;
		this.remainingStudents = remainingStudents;
	}

	public GroupClient(String name) {
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

	public ProfessorClient getProfessor() {
		return professor;
	}

	public void setProfessor(ProfessorClient professor) {
		this.professor = professor;
	}

	public List<StudentClient> getParticipatingStudents() {
		return participatingStudents;
	}

	public void setParticipatingStudents(List<StudentClient> participatingStudents) {
		this.participatingStudents = participatingStudents;
	}

	public List<StudentClient> getRemainingStudents() {
		return remainingStudents;
	}

	public void setRemainingStudents(List<StudentClient> remainingStudents) {
		this.remainingStudents = remainingStudents;
	}

	
}
