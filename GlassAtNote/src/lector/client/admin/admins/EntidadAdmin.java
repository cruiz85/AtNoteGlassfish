package lector.client.admin.admins;

import lector.client.catalogo.client.Entity;
import lector.share.model.client.ProfessorClient;

public class EntidadAdmin extends Entity {

	private ProfessorClient Admin;
	
	public EntidadAdmin(ProfessorClient Admin) {
		super(Admin.getFirstName() + " "+ Admin.getLastName()+ " : " +Admin.getEmail());
		this.Admin=Admin;
	}

	public ProfessorClient getAdmin() {
		return Admin;
	}
	
	public void setAdmin(ProfessorClient admin) {
		Admin = admin;
	}
}
