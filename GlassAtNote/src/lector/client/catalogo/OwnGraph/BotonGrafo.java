package lector.client.catalogo.OwnGraph;

import lector.client.admin.BotonesStackPanelAdministracionMio;
import lector.client.catalogo.BotonesStackPanelMio;
import lector.client.catalogo.BotonesStackPanelMioGrafo;
import lector.client.catalogo.Finder;
import lector.client.catalogo.client.EntityCatalogElements;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.Folder;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.EntryClient;
import lector.share.model.client.TypeCategoryClient;
import lector.share.model.client.TypeClient;

import com.caucho.amber.entity.Entity;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BotonGrafo extends BotonesStackPanelAdministracionMio {

	private CatalogoClient catalogo;
	private EntryClient entry;
	private boolean isCatalog;
	private HorizontalPanel SelectionPanel;
	
	
	
	public BotonGrafo(String HTML,VerticalPanel Normal, HorizontalPanel horizontalPanel, Finder Fin) {
		super(HTML, Normal, new VerticalPanel(), Fin);
		SelectionPanel= horizontalPanel;
		
	}


	public CatalogoClient getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(CatalogoClient entrada) {
		super.setHTML(entrada.getCatalogName());
		catalogo=entrada;
		entry=null;
		isCatalog=true;
		setEntidad(new Folder(new TypeCategoryClient(entrada.getCatalogName()), entrada, null));
	}

	public EntryClient getEntry() {
		return entry;
	}

	public void setEntry(EntryClient entrada) {
		super.setHTML(entrada.getName());
		entry=entrada;
		catalogo=entrada.getCatalog();
		isCatalog=false;
		EntityCatalogElements E;
		if (entrada instanceof TypeCategoryClient)
			E=new Folder((TypeCategoryClient) entrada, entrada.getCatalog(), null);
		else 
			E=new File((TypeClient) entrada, entrada.getCatalog(), null);
		setEntidad(E);
	}

	public boolean isCatalog() {
		return isCatalog;
	}

	public void setCatalog(boolean isCatalog) {
		this.isCatalog = isCatalog;
	}

	@Override
	public BotonGrafo Clone() {
		BotonGrafo BG=new BotonGrafo("Nuevo",Normal,SelectionPanel,getF());
		return BG;
	}
	

	public void setVerticalPanelActual(VerticalPanel actual) {
		Actual=actual;
	}
	
	public void setVerticalSelected(VerticalPanel actual) {
		Selected=actual;
	}
	
	public void setFinder(Finder actual) {
		setF(actual);
	}
	
	public HorizontalPanel getSelectionPanel() {
		return SelectionPanel;
	}
}
