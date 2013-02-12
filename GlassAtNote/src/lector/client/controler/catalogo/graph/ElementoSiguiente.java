package lector.client.controler.catalogo.graph;

import lector.share.model.client.EntryClient;

import com.google.gwt.user.client.ui.HorizontalPanel;

public class ElementoSiguiente {

	private EntryClient hijoEntry;
	private HorizontalPanel paneLHijos;
	private LineasT LineaCuelgue;
	


	public ElementoSiguiente(EntryClient hijoEntry, HorizontalPanel paneLHijos,
			LineasT lineaCuelgue) {
		this.hijoEntry = hijoEntry;
		this.paneLHijos = paneLHijos;
		LineaCuelgue = lineaCuelgue;
	}

	public EntryClient getHijoEntry() {
		return hijoEntry;
	}

	public void setHijoEntry(EntryClient hijoEntry) {
		this.hijoEntry = hijoEntry;
	}

	public HorizontalPanel getPaneLHijos() {
		return paneLHijos;
	}

	public void setPaneLHijos(HorizontalPanel paneLHijos) {
		this.paneLHijos = paneLHijos;
	}
	
	public LineasT getLineaCuelgue() {
		return LineaCuelgue;
	}
	
	public void setLineaCuelgue(LineasT lineaCuelgue) {
		LineaCuelgue = lineaCuelgue;
	}

}
