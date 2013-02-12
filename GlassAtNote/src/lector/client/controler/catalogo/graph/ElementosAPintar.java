package lector.client.controler.catalogo.graph;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.SimplePanel;

import lector.share.model.client.EntryClient;

public class ElementosAPintar {

	private EntryClient Elemento;
	private int AparicionesActual;
	private boolean incluirPadreCatalogo;
	private ArrayList<Connect2vs3> LineasAConectar;
	
	public ElementosAPintar(EntryClient elemento, boolean b) {
		AparicionesActual=0;
		Elemento = elemento;
		incluirPadreCatalogo=b;
		LineasAConectar =new ArrayList<Connect2vs3>();
	}
	
	//Retorna si es la ultima
	public boolean addAparicion()
	{
	if (!incluirPadreCatalogo){
		AparicionesActual++;
	}else incluirPadreCatalogo=false;
	return AparicionesActual>=Elemento.getParents().size();
	}
	
	public boolean requierepintado()
	{
		return AparicionesActual>=Elemento.getParents().size();
	}
	
	public EntryClient getElemento() {
		return Elemento;
	}
	
	public int getAparicionesActual() {
		return AparicionesActual;
	}
	
	public ArrayList<Connect2vs3> getLineasAConectar() {
		return LineasAConectar;
	}
	
	public void addLineaAConectar(LineasT Linea,SimplePanel SP)
	{
		LineasAConectar.add(new Connect2vs3(Linea,SP));
	}
}
