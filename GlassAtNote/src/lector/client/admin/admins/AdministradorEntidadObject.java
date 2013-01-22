package lector.client.admin.admins;

import lector.client.controler.EntitdadObject;
import lector.share.model.client.ProfessorClient;

public class AdministradorEntidadObject extends EntitdadObject {

	private ProfessorClient Admin;
	
	public AdministradorEntidadObject(ProfessorClient Admin) {
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
