package lector.client.catalogo;


import lector.client.catalogo.client.EntityCatalogElements;
import lector.client.catalogo.grafo.PanelGrafo;
import lector.client.reader.LoadingPanel;
import lector.share.model.client.CatalogoClient;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ScrollPanel;

public class FinderGrafo extends Finder {


	
	//el finder del reading activity tiene lenguaje asociado
	private PanelGrafo panelDelGrafo;
	
	public FinderGrafo(CatalogoClient C) {
		
		this.C=C;
		simplePanel.clear();
		ScrollPanel SP=new ScrollPanel();
		SP.setSize("100%", "100%");
		panelDelGrafo = new PanelGrafo(C);
		simplePanel.setWidget(SP);
		SP.setWidget(panelDelGrafo);
		
		
		
	}

	protected void SeleccionaLaRama() {
		
		panelDelGrafo.refresca(C.getId());

		LoadingPanel.getInstance().hide();
		
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
	SeleccionaLaRama();
}



}
