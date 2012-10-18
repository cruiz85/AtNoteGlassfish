package lector.share.model.client;

import java.util.Date;

import javax.persistence.Temporal;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserClient implements IsSerializable {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Date createdDate;
	private boolean isConfirmed = false;

	public UserClient() {
	}

	public UserClient(Long id, String firstName, String lastName, String email,
			String password, Date createdDate,boolean isConfirmed) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.createdDate = createdDate;
		this.isConfirmed = isConfirmed;
	}

	public UserClient(String email) {
		super();
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setId(Long id) {
		this.id = id;
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

	public boolean isConfirmed() {
		return isConfirmed;
	}

	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	
	
}
