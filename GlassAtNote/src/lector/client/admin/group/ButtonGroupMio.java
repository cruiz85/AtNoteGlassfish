package lector.client.admin.group;

import lector.share.model.UserApp;
import lector.share.model.client.StudentClient;

import com.google.gwt.user.client.ui.Button;

public class ButtonGroupMio extends Button {

	
	private StudentClient Usuario;
	public ButtonGroupMio(StudentClient userApp) {
		super();
		Usuario=userApp;
	}
	
	public StudentClient getUsuario() {
		return Usuario;
	}
	

}
