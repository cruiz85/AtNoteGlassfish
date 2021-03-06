package lector.client.reader.browser;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

import lector.client.admin.generalPanels.BotonesStackPanelAdministracionMio;
import lector.client.controler.EntitdadObject;
import lector.client.controler.catalogo.BotonesStackPanelMio;
import lector.client.controler.catalogo.Finder;
import lector.client.controler.catalogo.client.EntityCatalogElements;
import lector.client.reader.ButtonTipo;
import lector.client.reader.browser.Browser.CatalogTipo;

public class BotonesStackPanelBrowser extends
		BotonesStackPanelAdministracionMio {
	
	private static Browser browser;
	
	public BotonesStackPanelBrowser(String HTML, VerticalPanel Normal,
			VerticalPanel Selected,Finder  F) {
		super(HTML, Normal, Selected,F);

	}
	
	public BotonesStackPanelMio Clone()
	{
		BotonesStackPanelBrowser New=new BotonesStackPanelBrowser(getHTML(), super.getNormal(), super.getSelected(),super.getF());	
	New.setActual(getActual());
	return New;
	}
	
	@Override
	public void Swap() {
//		Actual.remove(this);

//		if (Actual==Normal)
//		{
		Actual=Selected;
		if (!EstainSelectedmio()){
		BotonesStackPanelBrowser BS=(BotonesStackPanelBrowser)this.Clone();	
		BS.setSize("100%", "100%");
		BS.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		BS.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		BS.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		BS.setStyleName("gwt-ButtonTOP");
		BS.setText(this.getText());
		BS.setF(getF());
		if (BS.getF().getCatalogo().getId().equals(browser.getFinderButton().getCatalogo().getId()))
			BS.setHTML(CatalogTipo.Catalog1.getTexto() + this.getHTML());
    	else BS.setHTML(CatalogTipo.Catalog2.getTexto() + this.getHTML());
		
		BS.setEntidad(getEntidad());
		Selected.add(BS);
		BS.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				BotonesStackPanelBrowser BS=(BotonesStackPanelBrowser)event.getSource();	
				Selected.remove(BS);
				Browser.refreshButton();
			}
		});
		}
//		}
//	else 
//		{
//		Actual.remove(this);
//		Actual=Normal;
//		}

	}

	private boolean EstainSelectedmio() {
		for (int i = 0; i < Selected.getWidgetCount(); i++) {
			BotonesStackPanelAdministracionMio BSM= (BotonesStackPanelAdministracionMio)Selected.getWidget(i);
			if (((EntityCatalogElements)BSM.getEntidad()).getEntry().getId().intValue()==((EntityCatalogElements)super.getEntidad()).getEntry().getId().intValue()) return true;
		}
		return false;
	}
	
	public static void setBrowser(Browser browser) {
		BotonesStackPanelBrowser.browser = browser;
	}
	
	public static Browser getBrowser() {
		return browser;
	}

}
