package lector.share.model.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GroupClient implements IsSerializable {

	private Long id;
	private String name;

	private ProfessorClient professor;
	private List<StudentClient> participatingUsers = new ArrayList<StudentClient>();

	private List<StudentClient> remainingUsers = new ArrayList<StudentClient>();

	public GroupClient() {

	}

	public GroupClient(Long id, String name, ProfessorClient professor,
			List<StudentClient> participatingUsers,
			List<StudentClient> remainingUsers) {
		super();
		this.id = id;
		this.name = name;
		this.professor = professor;
		this.participatingUsers = participatingUsers;
		this.remainingUsers = participatingUsers;
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

	public List<StudentClient> getParticipatingUsers() {
		return participatingUsers;
	}

	public void setParticipatingUsers(List<StudentClient> participatingUsers) {
		this.participatingUsers = participatingUsers;
	}

	public List<StudentClient> getRemainingUsers() {
		return remainingUsers;
	}

	public void setRemainingUsers(List<StudentClient> remainingUsers) {
		this.remainingUsers = remainingUsers;
	}

}
