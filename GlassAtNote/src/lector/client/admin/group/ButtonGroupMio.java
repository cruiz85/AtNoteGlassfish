package lector.client.admin.group;

import lector.share.model.UserApp;
import lector.share.model.client.StudentClient;
import lector.share.model.client.UserClient;

import com.google.gwt.user.client.ui.Button;

public class ButtonGroupMio extends Button {

	
	private UserClient Usuario;
	
	public ButtonGroupMio(UserClient userClient) {
		super();
		Usuario=userClient;
	}
	
	public UserClient getUsuario() {
		return Usuario;
	}
	

}
