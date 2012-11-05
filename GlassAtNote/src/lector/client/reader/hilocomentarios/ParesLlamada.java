package lector.client.reader.hilocomentarios;

import java.util.ArrayList;
import java.util.List;

import lector.share.model.TextSelector;
import lector.share.model.client.TextSelectorClient;

import com.google.gwt.user.client.ui.VerticalPanel;

public class ParesLlamada {

	private VerticalPanel VP;
	private Long IDPadre;
	private Long IDThread;
	private List<TextSelectorClient> Selectores;
	
	
	public ParesLlamada(VerticalPanel vP, Long iDPadre,Long IDThreadin,List<TextSelectorClient> Selectoresin) {
		super();
		VP = vP;
		IDPadre = iDPadre;
		IDThread = IDThreadin;
		Selectores=Selectoresin;
	}
	
	public Long getIDPadre() {
		return IDPadre;
	}
	
	public VerticalPanel getVP() {
		return VP;
	}
	
	public void setIDPadre(Long iDPadre) {
		IDPadre = iDPadre;
	}
	
	public void setVP(VerticalPanel vP) {
		VP = vP;
	}
	
	public Long getIDThread() {
		return IDThread;
	}
	
	public void setIDThread(Long iDThread) {
		IDThread = iDThread;
	}
	
	public List<TextSelectorClient> getSelectores() {
		return Selectores;
	}
	
	public void setSelectores(ArrayList<TextSelectorClient> selectores) {
		Selectores = selectores;
	}
}
