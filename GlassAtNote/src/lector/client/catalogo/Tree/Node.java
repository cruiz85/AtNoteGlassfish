package lector.client.catalogo.Tree;

import com.google.gwt.user.client.ui.TreeItem;

import lector.client.catalogo.client.EntityCatalogElements;
import lector.client.controler.EntitdadObject;

public class Node extends TreeItem{

	private EntityCatalogElements Entidad;
	private String Text;
	
	public Node(EntityCatalogElements entitynew) {
		this.Entidad=entitynew;
	}
	
	public EntityCatalogElements getEntidad() {
		return Entidad;
	}
	

	
	public void setEntidad(EntityCatalogElements entidad) {
		Entidad = entidad;
	}
	
	public void setTextNodo(String text) {
		super.setText(text);
	}
	
	
	public void setHTML(String S,String Text) {
		this.Text=Text;
		setHTML("<img src=\""+ S +"\">"+ Text);
	}
	
	@Override
	public String getText() {
		return Text;
	}

}
