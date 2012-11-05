package lector.client.reader;

import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.EntityCatalogElements;
import lector.share.model.client.EntryClient;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class ButtonTipo extends Button {

	private EntityCatalogElements Entidad;
	private HorizontalPanel pertenezco;
	
	public ButtonTipo(EntityCatalogElements act, HorizontalPanel horizontalPanel) {
		super(act.getName());
		Entidad=act;
		pertenezco=horizontalPanel;
	}
	
	public ButtonTipo(EntityCatalogElements act, String texto, HorizontalPanel horizontalPanel) {
		super(texto+act.getName());
		Entidad=act;
		pertenezco=horizontalPanel;
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
	
	public HorizontalPanel getPertenezco() {
		return pertenezco;
	}

}
