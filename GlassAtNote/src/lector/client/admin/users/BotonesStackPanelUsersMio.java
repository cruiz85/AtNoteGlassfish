package lector.client.admin.users;

import com.google.gwt.user.client.ui.VerticalPanel;

import lector.client.catalogo.BotonesStackPanelMio;
import lector.share.model.client.UserClient;

public class BotonesStackPanelUsersMio extends BotonesStackPanelMio {


	private UserClient User;

	public BotonesStackPanelUsersMio(String HTML, VerticalPanel verticalPanel,UserClient User) {
		super(HTML);
		super.setActual(verticalPanel);
		this.User=User;
	}

	@Override
	public BotonesStackPanelMio Clone() {
		BotonesStackPanelUsersMio New=new BotonesStackPanelUsersMio(getHTML(), super.getActual(),User);	
		New.setActual(getActual());
		return New;
	}
	
	public UserClient getUser() {
		return User;
	}
	
	public void setUser(UserClient user) {
		User = user;
	}

}
