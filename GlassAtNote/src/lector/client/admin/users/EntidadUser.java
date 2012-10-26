package lector.client.admin.users;

import lector.client.catalogo.client.Entity;
import lector.share.model.client.StudentClient;

public class EntidadUser extends Entity {

private StudentClient Admin;
	
	public EntidadUser(StudentClient Admin) {
		super(Admin.getFirstName() + " "+ Admin.getLastName()+ " : " +Admin.getEmail());
		this.Admin=Admin;
	}

	public StudentClient getStudent() {
		return Admin;
	}
	
	public void setAdmin(StudentClient admin) {
		Admin = admin;
	}


}
