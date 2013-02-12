package lector.client.reader;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import lector.client.admin.generalPanels.BotonesStackPanelAdministracionMio;
import lector.client.controler.catalogo.BotonesStackPanelMio;

public class BotonesStackPanelReaderSelectMio extends BotonesStackPanelAdministracionMio {

	private HorizontalPanel Labeltypo;
	
	public BotonesStackPanelReaderSelectMio(String HTML,VerticalPanel Actual, HorizontalPanel penelBotonesTipo) {
		super(HTML, Actual, Actual, null);
		super.setActual(Actual);
		Labeltypo=penelBotonesTipo;
	}
	
	@Override
	public BotonesStackPanelMio Clone() {
		BotonesStackPanelReaderSelectMio New=new BotonesStackPanelReaderSelectMio(getHTML(), super.getActual(),getLabeltypo());	
		New.setActual(getActual());
		return New;
	}
	
	public HorizontalPanel getLabeltypo() {
		return Labeltypo;
	}
	
	public void setLabeltypo(HorizontalPanel labeltypo) {
		Labeltypo = labeltypo;
	}

}
