package lector.client.admin.users;

import com.google.gwt.user.client.ui.VerticalPanel;

import lector.client.catalogo.BotonesStackPanelMio;
import lector.share.model.client.UserClient;

public class BotonesStackPanelUsersMio extends BotonesStackPanelMio {


	private UserClient User;

//	public BotonesStackPanelUsersMio(String HTML, VerticalPanel verticalPanel,UserClient User) {
//		super(HTML);
//		super.setActual(verticalPanel);
//		this.User=User;
//	}
	
	
	public BotonesStackPanelUsersMio(EntidadUser E, VerticalPanel verticalPanel) {
		super(E);
		super.setActual(verticalPanel);
		this.User=E.getStudent();
	}

	@Override
	public BotonesStackPanelMio Clone() {
		BotonesStackPanelUsersMio New=new BotonesStackPanelUsersMio((EntidadUser)super.getEntidad(), super.getActual());	
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
