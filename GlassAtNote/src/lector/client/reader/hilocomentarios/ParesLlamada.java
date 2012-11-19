package lector.client.reader.hilocomentarios;

import lector.share.model.client.AnnotationClient;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ParesLlamada {

	private VerticalPanel VP;
	private AnnotationClient IDPadre;
	
	
	public ParesLlamada(VerticalPanel vP, AnnotationClient annotationClient) {
		super();
		VP = vP;
		IDPadre = annotationClient;
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
	


}
