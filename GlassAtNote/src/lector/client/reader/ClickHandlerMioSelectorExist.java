package lector.client.reader;

import lector.client.admin.generalPanels.BotonesStackPanelAdministracionMio;
import lector.client.admin.tagstypes.ClickHandlerMio;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.catalogo.BotonesStackPanelMio;
import lector.client.controler.catalogo.Finder;
import lector.client.controler.catalogo.client.EntityCatalogElements;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ClickHandlerMioSelectorExist extends ClickHandlerMio implements
		ClickHandler {
	
	private PopUpFinderSelectorExistAnnotation popUpFinderSelectorExistAnnotation;
	private Finder finderrefresh;
	private EntityCatalogElements father;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	
	public ClickHandlerMioSelectorExist(PopUpFinderSelectorExistAnnotation popUpFinderSelectorExistAnnotation, Finder finderrefresh,
			EntityCatalogElements father) {
		 this.popUpFinderSelectorExistAnnotation=popUpFinderSelectorExistAnnotation;
		this.finderrefresh=finderrefresh;
		this.father=father;
	}

	@Override
	public void onClickMan(BotonesStackPanelMio event) {
	
				EntityCatalogElements E=(EntityCatalogElements) ((BotonesStackPanelAdministracionMio)event).getEntidad();
				AsyncCallback<Void> LLamada=new AsyncCallback<Void>() {
					
					public void onSuccess(Void result) {
//						finderrefresh.RefrescaLosDatos();
//						//	scrollPanel.setWidget(finder);
//						finderrefresh.setSize("100%","100%");
						finderrefresh.RefrescaLosDatos();
						SelectorTypePopUpAnnotacion.RestoreFinderButtonActio();
						//	scrollPanel.setWidget(finder);
						//finderrefresh.setSize("100%","100%");
						popUpFinderSelectorExistAnnotation.hide();
						
					}
					
					public void onFailure(Throwable caught) {
						Window.alert(ActualState.getLanguage().getE_Saving());
						
					}
				};
				if (father==null)
				bookReaderServiceHolder.addChildEntry(E.getEntry().getId(), Constants.CATALOGID, LLamada);
				else 
					bookReaderServiceHolder.addChildEntry(E.getEntry().getId(), father.getEntry().getId(), LLamada);
				
			}

}
