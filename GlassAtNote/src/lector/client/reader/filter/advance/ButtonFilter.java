package lector.client.reader.filter.advance;

import lector.client.controler.EntitdadObject;

import com.google.gwt.user.client.ui.Button;

public class ButtonFilter extends Button {
	
	private EntitdadObject ID;
	private Tiposids Idtipo;
	
	public ButtonFilter(String HTML,EntitdadObject IDin,Tiposids idtipo) {
		super(HTML);
		ID=IDin;
		Idtipo = idtipo;
	}
	
	public void setIdtipo(Tiposids idtipo) {
		Idtipo = idtipo;
	}
	
	public EntitdadObject getEntityClient() {
		return ID;
	}
	
	public Tiposids getIdtipo() {
		return Idtipo;
	}
	
	public void setEntityClient(EntitdadObject iD) {
		ID = iD;
	}
	
	

}
