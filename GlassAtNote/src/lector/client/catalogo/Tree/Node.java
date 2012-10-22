package lector.client.catalogo.Tree;

import com.google.gwt.user.client.ui.TreeItem;

import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.EntityCatalogElements;

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
		// TODO Auto-generated method stub
		super.setText(text);
	}
	
	
	public void setHTML(String S,String Text) {
		this.Text=Text;
		setHTML("<img src=\""+ S +"\">"+ Text);
	}
	
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return Text;
	}

}
