package lector.client.admin.tagstypes;

import lector.client.admin.BotonesStackPanelAdministracionMio;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.BotonesStackPanelMio;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.EntityCatalogElements;
import lector.client.controler.Constants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ClickHandlerMioSeleccion extends ClickHandlerMio implements
		ClickHandler {
	
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private PopUpFinderSelectorExist PFSE;
	
	
	public ClickHandlerMioSeleccion(PopUpFinderSelectorExist PFSEin) {
		PFSE=PFSEin;
	}
	
	public void onClick(ClickEvent event) {
		EntityCatalogElements E=(EntityCatalogElements)((BotonesStackPanelAdministracionMio)event.getSource()).getEntidad();
		AsyncCallback<Void> LLamada=new AsyncCallback<Void>() {
			
			public void onSuccess(Void result) {
				EditorTagsAndTypes.LoadBasicTypes();
				PFSE.hide();
				
			}
			
			public void onFailure(Throwable caught) {
				EditorTagsAndTypes.LoadBasicTypes();
				Window.alert("Error in copy in folder");
				
				
			}
		};
		
			bookReaderServiceHolder.addChildEntry(E.getEntry().getId(), PFSE.getFather().getId(), LLamada);

	}
	
	public void onClickMan(BotonesStackPanelMio event) {
		EntityCatalogElements BS = (EntityCatalogElements)((BotonesStackPanelAdministracionMio) event).getEntidad();
AsyncCallback<Void> LLamada=new AsyncCallback<Void>() {
			
			public void onSuccess(Void result) {
				EditorTagsAndTypes.LoadBasicTypes();
				PFSE.hide();
				
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Error in copy in folder");
				
			}
		};
		if (PFSE.getFather()==null)
			bookReaderServiceHolder.addChildEntry(BS.getEntry().getId(),  Constants.CATALOGID, LLamada);
		else 
			bookReaderServiceHolder.addChildEntry(BS.getEntry().getId(), PFSE.getFather().getId(), LLamada);

	}

}
