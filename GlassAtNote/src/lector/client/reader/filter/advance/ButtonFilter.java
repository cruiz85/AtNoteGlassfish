package lector.client.reader.filter.advance;

import lector.client.catalogo.client.Entity;
import com.google.gwt.user.client.ui.Button;

public class ButtonFilter extends Button {
	
	private Entity ID;
	private Tiposids Idtipo;
	
	public ButtonFilter(String HTML,Entity IDin,Tiposids idtipo) {
		super(HTML);
		ID=IDin;
		Idtipo = idtipo;
	}
	
	public void setIdtipo(Tiposids idtipo) {
		Idtipo = idtipo;
	}
	
	public Entity getEntityClient() {
		return ID;
	}
	
	public Tiposids getIdtipo() {
		return Idtipo;
	}
	
	public void setEntityClient(Entity iD) {
		ID = iD;
	}
	
	

}
