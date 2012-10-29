package lector.client.admin.admins;

import com.google.gwt.user.client.ui.VerticalPanel;

import lector.client.catalogo.BotonesStackPanelMio;

public class BotonesStackPanelAdminsMio extends BotonesStackPanelMio {


	public BotonesStackPanelAdminsMio(EntidadAdmin PC, VerticalPanel verticalPanel) {
		super(PC);
		super.setActual(verticalPanel);
	}

	@Override
	public BotonesStackPanelMio Clone() {
		BotonesStackPanelAdminsMio New=new BotonesStackPanelAdminsMio((EntidadAdmin)super.getEntidad(), super.getActual());	
		New.setActual(getActual());
		return New;
	}
	


}