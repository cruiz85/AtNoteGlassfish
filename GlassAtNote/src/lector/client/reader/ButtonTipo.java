package lector.client.reader;

import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.EntityCatalogElements;
import lector.share.model.client.EntryClient;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;

public class ButtonTipo extends Button {

	private EntityCatalogElements Entidad;
	private Panel pertenezco;
	
	public ButtonTipo(EntityCatalogElements act, Panel Panel) {
		super(act.getName());
		Entidad=act;
		pertenezco=Panel;
	}
	
	public ButtonTipo(EntityCatalogElements act, String texto, Panel Panel) {
		super(texto+act.getName());
		Entidad=act;
		pertenezco=Panel;
	}

	public void setEntidad(EntityCatalogElements a) {
		Entidad = a;
	}
	
	public EntityCatalogElements getEntidad() {
		return Entidad;
	}
	
	public void setPertenezco(HorizontalPanel pertenezco) {
		this.pertenezco = pertenezco;
	}
	
	public Panel getPertenezco() {
		return pertenezco;
	}

}
