package lector.client.admin.activity.buttons;

import lector.share.model.client.CatalogoClient;

import com.google.gwt.user.client.ui.Button;

public class Botoncatalogo extends Button {
 
	
	private CatalogoClient catalogo;
	public Botoncatalogo(CatalogoClient catalogin) {
		super(catalogin.getCatalogName());
		if (catalogin.getIsPrivate()) setHTML("<b>*"+catalogin.getCatalogName()+"</b>");
		catalogo=catalogin;
		
	}
	
	
	public CatalogoClient getCatalogo() {
		return catalogo;
	}
	
	public void setCatalogo(CatalogoClient catalogo) {
		this.catalogo = catalogo;
	}
}
