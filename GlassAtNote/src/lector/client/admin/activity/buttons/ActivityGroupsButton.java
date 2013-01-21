package lector.client.admin.activity.buttons;

import lector.share.model.GroupApp;
import lector.share.model.client.GroupClient;

import com.google.gwt.user.client.ui.Button;

public class ActivityGroupsButton extends Button {
 
	
	private GroupClient grupo;
	
	public ActivityGroupsButton(GroupClient grupoin) {
		super(grupoin.getName());
		grupo=grupoin;
		
	}
	
	
	public GroupClient getGrupo() {
		return grupo;
	}
	
	public void setGrupo(GroupClient grupo) {
		this.grupo = grupo;
	}
}
