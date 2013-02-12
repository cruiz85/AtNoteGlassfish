package lector.client.admin.generalPanels;

import com.google.gwt.user.client.ui.VerticalPanel;

import lector.client.controler.catalogo.BotonesStackPanelMio;
import lector.client.controler.catalogo.Finder;

public class BotonesStackPanelAdministracionSimple extends BotonesStackPanelAdministracionMio {


	public BotonesStackPanelAdministracionSimple(String HTML,VerticalPanel Normal, VerticalPanel Selected) {
		super(HTML,Normal,Selected,null);

	}
	
	
	@Override
	public void Swap() {
		Actual.remove(this);
		if (Actual==Normal)
			{
			Actual=Selected;
			}
		else 
			{
			Actual=Normal;
			}
		if((this.getEntidad()!=null)&&!EstainSelected())

				
					Actual.add(this);

	}
	
	


}
