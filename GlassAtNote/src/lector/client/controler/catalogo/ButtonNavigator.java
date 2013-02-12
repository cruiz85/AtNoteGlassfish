package lector.client.controler.catalogo;

import lector.client.controler.EntitdadObject;

import com.google.gwt.user.client.ui.Button;

public class ButtonNavigator extends Button {

	EntitdadObject elemento;
	
	public ButtonNavigator(String name) {
		super(name);
	}

	public void setElemento(EntitdadObject elemento) {
		this.elemento = elemento;
	}
	
	public EntitdadObject getElemento() {
		return elemento;
	}
}
