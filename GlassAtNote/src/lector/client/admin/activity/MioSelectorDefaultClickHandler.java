package lector.client.admin.activity;

import lector.client.admin.tagstypes.ClickHandlerMio;
import lector.client.catalogo.BotonesStackPanelMio;
import lector.client.catalogo.client.EntityCatalogElements;
import lector.client.reader.BotonesStackPanelReaderSelectMio;
import lector.share.model.client.EntryClient;
import lector.share.model.client.TypeClient;

import com.google.gwt.event.dom.client.ClickEvent;

public class MioSelectorDefaultClickHandler extends ClickHandlerMio {

	private EditorActivityPopupPanel eA;
	private FinderDefaultTypePopupPanel Padre;
	
	
	public MioSelectorDefaultClickHandler(EditorActivityPopupPanel eA,
			FinderDefaultTypePopupPanel yo) {
		Padre=yo;
		this.eA=eA;
	}

	@Override
	public void onClick(ClickEvent event) {
		BotonesStackPanelReaderSelectMio B= (BotonesStackPanelReaderSelectMio) event.getSource();
		EntityCatalogElements ECElements=(EntityCatalogElements) B.getEntidad();
		EntryClient EC=ECElements.getEntry();
		if (EC instanceof TypeClient)
			{
			eA.setTypeDefault((TypeClient) EC);
			Padre.hide();
			}
		

	}
	
	public void onClickMan(BotonesStackPanelMio event) {
		BotonesStackPanelReaderSelectMio B= (BotonesStackPanelReaderSelectMio) event;
		EntityCatalogElements ECElements=(EntityCatalogElements) B.getEntidad();
		EntryClient EC=ECElements.getEntry();
		if (EC instanceof TypeClient)
			{
			eA.setTypeDefault((TypeClient) EC);
			Padre.hide();
			}

	}
	

}
