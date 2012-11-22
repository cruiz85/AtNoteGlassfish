package lector.share.model.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GroupClient implements IsSerializable {

	private Long id;
	private String name;

	private ProfessorClient professor;
	private List<UserClient> participatingUsers = new ArrayList<UserClient>();

	private List<UserClient> remainingUsers = new ArrayList<UserClient>();

	public GroupClient() {

	}

	public GroupClient(Long id, String name, ProfessorClient professor,
			List<UserClient> participatingUsers, List<UserClient> remainingUsers) {
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

	public List<UserClient> getParticipatingUsers() {
		return participatingUsers;
	}

	public void setParticipatingUsers(List<UserClient> participatingUsers) {
		this.participatingUsers = participatingUsers;
	}

	public List<UserClient> getRemainingUsers() {
		return remainingUsers;
	}

	public void setRemainingUsers(List<UserClient> remainingUsers) {
		this.remainingUsers = remainingUsers;
	}

}
