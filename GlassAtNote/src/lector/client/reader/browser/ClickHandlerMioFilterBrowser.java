package lector.client.reader.browser;

import lector.client.admin.tagstypes.ClickHandlerMio;
import lector.client.controler.EntitdadObject;
import lector.client.controler.catalogo.BotonesStackPanelMio;
import lector.client.controler.catalogo.Finder;
import lector.client.controler.catalogo.client.EntityCatalogElements;
import lector.share.model.client.EntryClient;

import com.google.gwt.event.dom.client.ClickHandler;

public class ClickHandlerMioFilterBrowser extends ClickHandlerMio implements
		ClickHandler {

	
	
	private Finder Finderparent;

	public ClickHandlerMioFilterBrowser(Finder Finder) {
		this.Finderparent=Finder;
	}
	
	
	public void onClickMan(BotonesStackPanelMio event) {

			BotonesStackPanelBrowser BS = (BotonesStackPanelBrowser) event;
			if (!EqualsFinderButton(BS))
				{
				BS.Swap();
				}
			
			if (Browser.getSelectedB().getWidgetCount()==0)Browser.getBtnNewButton().setVisible(false);
			else Browser.getBtnNewButton().setVisible(true);

	}
	
	private boolean EqualsFinderButton(BotonesStackPanelBrowser bS) {
		for (EntryClient entity : ((EntityCatalogElements)bS.getEntidad()).getEntry().getParents()) {
			if (Finderparent.getTopPath().getEntry().getId().equals(entity.getId())) return true;
		}		
		return false;
	}
}
