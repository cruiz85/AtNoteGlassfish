package lector.client.catalogo;


import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.OwnGraph.PanelGrafo;
import lector.client.catalogo.client.EntityCatalogElements;
import lector.client.controler.ActualState;
import lector.client.controler.ErrorConstants;

import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;
import lector.share.model.client.CatalogoClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ScrollPanel;

public class FinderOwnGrafo extends Finder {


	
	//el finder del reading activity tiene lenguaje asociado
	private PanelGrafo panelDelGrafo;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	
	public FinderOwnGrafo(CatalogoClient C) {
		
		this.C=C;
		simplePanel.clear();
		ScrollPanel SP=new ScrollPanel();
		SP.setSize("100%", "100%");
		panelDelGrafo = new PanelGrafo();
		panelDelGrafo.Go(C);
		panelDelGrafo.Pinta();
		simplePanel.setWidget(SP);
		SP.setWidget(panelDelGrafo);
		
		
		
	}

	
	public boolean isInReadingActivity() {
		return InReadingActivity;
	}
	
	public void setInReadingActivity(boolean inReadingActivity) {
		InReadingActivity = inReadingActivity;
	}
	
	public CatalogoClient getCatalogo() {
		return C;
	}
	
	public void setCatalogo(CatalogoClient c) {
		C = c;
		ActualRama=trtmNewItem;
		cargaLaRama();
	}
	
	public static void setButtonTipoGrafo(BotonesStackPanelMio buttonMio) {
		PanelGrafo.setBotonTipo(buttonMio);

	}

	public static void setBotonClickGrafo(ClickHandler clickHandler) {
		PanelGrafo.setAccionAsociada(clickHandler);

	}

	public EntityCatalogElements getTopPath() {
				return ActualRama.getEntidad();
	}
	
@Override
public void RefrescaLosDatos() {
	LoadingPanel.getInstance().center();
	if (InReadingActivity)  LoadingPanel.getInstance().setLabelTexto(ActualState.getLanguage().getLoading());
	else LoadingPanel.getInstance().setLabelTexto("Loading...");
	bookReaderServiceHolder.loadCatalogById(C.getId(),new AsyncCallback<CatalogoClient>(){

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(ErrorConstants.ERROR_LOADING_CATALOG);
			Logger.GetLogger().severe(Yo.getClass().toString(),ActualState.getUser().toString(), ErrorConstants.ERROR_LOADING_CATALOG);
			LoadingPanel.getInstance().hide();			
		}

		@Override
		public void onSuccess(CatalogoClient result) {	
			C=result;
			panelDelGrafo.Go(C);
			panelDelGrafo.refreshsize();
			LoadingPanel.getInstance().hide();
			
		}});
	
}


}
