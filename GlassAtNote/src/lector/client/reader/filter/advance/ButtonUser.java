package lector.client.reader.filter.advance;

import lector.share.model.client.UserClient;

import com.google.gwt.user.client.ui.Button;

public class ButtonUser extends Button {

	private UserClient ID;
	
	public ButtonUser(UserClient iD) {
		super(iD.getFirstName());
		ID=iD;
	}

	public UserClient getID() {
		return ID;
	}
	
	public void setID(UserClient iD) {
		ID = iD;
	}
}

