package lector.client.catalogo;


import lector.client.catalogo.OwnGraph.PanelGrafo;
import lector.client.catalogo.client.EntityCatalogElements;

import lector.client.reader.LoadingPanel;
import lector.share.model.client.CatalogoClient;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ScrollPanel;

public class FinderOwnGrafo extends Finder {


	
	//el finder del reading activity tiene lenguaje asociado
	private PanelGrafo panelDelGrafo;
	
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
	panelDelGrafo.Go(C);
	panelDelGrafo.Pinta();
}



}
