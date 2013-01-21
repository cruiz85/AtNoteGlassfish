package lector.client.admin.generalPanels;

import com.google.gwt.user.client.ui.VerticalPanel;

import lector.client.admin.admins.AdministradorEntidadObjeto;
import lector.client.catalogo.BotonesStackPanelMio;

public class BotonesStackPanelAdminsMio extends BotonesStackPanelMio {


	public BotonesStackPanelAdminsMio(AdministradorEntidadObjeto PC, VerticalPanel verticalPanel) {
		super(PC);
		super.setActual(verticalPanel);
	}

	@Override
	public BotonesStackPanelMio Clone() {
		BotonesStackPanelAdminsMio New=new BotonesStackPanelAdminsMio((AdministradorEntidadObjeto)super.getEntidad(), super.getActual());	
		New.setActual(getActual());
		return New;
	}
	


}
