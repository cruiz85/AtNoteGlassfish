package lector.client.admin.generalPanels;

import com.google.gwt.user.client.ui.VerticalPanel;

import lector.client.admin.admins.AdministradorEntidadObject;
import lector.client.controler.catalogo.BotonesStackPanelMio;

public class BotonesStackPanelAdminsMio extends BotonesStackPanelMio {


	public BotonesStackPanelAdminsMio(AdministradorEntidadObject PC, VerticalPanel verticalPanel) {
		super(PC);
		super.setActual(verticalPanel);
	}

	@Override
	public BotonesStackPanelMio Clone() {
		BotonesStackPanelAdminsMio New=new BotonesStackPanelAdminsMio((AdministradorEntidadObject)super.getEntidad(), super.getActual());	
		New.setActual(getActual());
		return New;
	}
	


}
