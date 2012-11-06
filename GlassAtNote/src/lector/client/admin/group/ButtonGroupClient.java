package lector.client.admin.group;

import lector.share.model.client.GroupClient;

import com.google.gwt.user.client.ui.Button;

public class ButtonGroupClient extends Button {

	private GroupClient groupClient;
	
	public ButtonGroupClient(GroupClient groupClient) {
		super(groupClient.getName() +" - ID : " + groupClient.getId());
		this.groupClient=groupClient;
	}
	
public GroupClient getGroupClient() {
	return groupClient;
}

public void setGroupClient(GroupClient groupClient) {
	this.groupClient = groupClient;
}


}
