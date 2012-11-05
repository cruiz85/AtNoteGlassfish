package lector.client.reader.hilocomentarios;

import lector.share.model.client.AnnotationClient;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ParesLlamada {

	private VerticalPanel VP;
	private Long Threads;
	private AnnotationClient IDPadre;
	
	
	public ParesLlamada(VerticalPanel vP, AnnotationClient annotationClient, Long threads) {
		super();
		VP = vP;
		IDPadre = annotationClient;
		Threads=threads;
	}
	
	public AnnotationClient getIDPadre() {
		return IDPadre;
	}
	
	public VerticalPanel getVP() {
		return VP;
	}
	
	public void setIDPadre(AnnotationClient iDPadre) {
		IDPadre = iDPadre;
	}
	
	public void setVP(VerticalPanel vP) {
		VP = vP;
	}
	

	public Long getThread() {
		return Threads;
	}
	
	public void setThread(Long threads) {
		Threads = threads;
	}
	
}
