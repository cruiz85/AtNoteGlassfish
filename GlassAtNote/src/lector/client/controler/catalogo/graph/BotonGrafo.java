package lector.client.controler.catalogo.graph;

import lector.client.admin.generalPanels.BotonesStackPanelAdministracionMio;
import lector.client.controler.catalogo.Finder;
import lector.client.controler.catalogo.client.EntityCatalogElements;
import lector.client.controler.catalogo.client.File;
import lector.client.controler.catalogo.client.Folder;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.EntryClient;
import lector.share.model.client.TypeCategoryClient;
import lector.share.model.client.TypeClient;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BotonGrafo extends BotonesStackPanelAdministracionMio {

	private CatalogoClient catalogo;
	private EntryClient entry;
	private boolean isCatalog;
	private Panel SelectionPanel;
	
	
	
	public BotonGrafo(String HTML,VerticalPanel Normal, Panel Panel, Finder Fin) {
		super(HTML, Normal, new VerticalPanel(), Fin);
		SelectionPanel= Panel;
		
	}


	public CatalogoClient getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(CatalogoClient entrada) {
		super.setHTML(entrada.getCatalogName());
		catalogo=entrada;
		entry=new TypeCategoryClient(entrada.getCatalogName());
		isCatalog=true;
		setEntidad(new Folder((TypeCategoryClient) entry, entrada, null));
		
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
	
	public Panel getSelectionPanel() {
		return SelectionPanel;
	}
	
	@Override
	public void setIcon(String S, String Text) {
		super.setHTML(Text);
	}
}
