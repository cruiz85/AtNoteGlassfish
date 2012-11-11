package lector.client.catalogo.OwnGraph;

import com.google.gwt.user.client.ui.SimplePanel;

public class Connect2vs3 {

	private LineasT linea;
	private SimplePanel sP;
	
	public Connect2vs3(LineasT linea, SimplePanel sP) {
		super();
		this.linea = linea;
		this.sP = sP;
	}

	public LineasT getLinea() {
		return linea;
	}

	public void setLinea(LineasT linea) {
		this.linea = linea;
	}

	public SimplePanel getsP() {
		return sP;
	}

	public void setsP(SimplePanel sP) {
		this.sP = sP;
	}
	
	
	
}
