package lector.client.reader.filter.advance;

import lector.client.catalogo.client.Entity;
import lector.client.controler.ActualState;
import lector.share.model.client.UserClient;

public class EntityUser extends Entity {

	private UserClient AU;
	
	public EntityUser(UserClient AUin) {
		super(AUin.getFirstName());
		AU=AUin;
	}

	
	public UserClient getAU() {
		return AU;
	}
	
	public void setAU(UserClient aU) {
		AU = aU;
	}
}
