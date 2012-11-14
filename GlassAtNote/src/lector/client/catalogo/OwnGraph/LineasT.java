package lector.client.catalogo.OwnGraph;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class LineasT implements Linea{

	private SimplePanel SP;
	private HorizontalPanel paneLHijos;
	private SimplePanel PanelBoton;
		
	public LineasT(SimplePanel sP, HorizontalPanel paneLHijos, SimplePanel panelEnlaceNodo) {
		super();
		SP = sP;
		this.paneLHijos = paneLHijos;
		PanelBoton=panelEnlaceNodo;
	}

	public int getStartX()
	{
	int X=getElementMoreLeft();	
	return X;	
	}
	
	private int getElementMoreLeft() {
		int X=Integer.MAX_VALUE;
		for (Widget Hijo : paneLHijos) {
			ElementoGrafo A=(ElementoGrafo)Hijo;
			int puntoCentroBoton =-1;
			if ((A instanceof Arbolgrafo))
			{
				Arbolgrafo B=(Arbolgrafo)A;	
			puntoCentroBoton=B.getBG().getAbsoluteLeft()+(B.getBG().getOffsetWidth()/2);
			}else
				if ((A instanceof ArbolgrafoVacio)){
				ArbolgrafoVacio B=(ArbolgrafoVacio)A;
				puntoCentroBoton=B.getSimplePanel().getAbsoluteLeft()+(B.getSimplePanel().getOffsetWidth()/2);
			}
			if (puntoCentroBoton<X)
				X=puntoCentroBoton;
		}
		return X;
	}

	public int getStartY()
	{
	return SP.getAbsoluteTop();	
	}
	
	public int getStartW()
	{
		int X=getElementMoreRight()
				-getElementMoreLeft();	
		return X;
	}
	
	private int getElementMoreRight() {
		int X=-1;
		for (Widget Hijo : paneLHijos) {
			ElementoGrafo A=(ElementoGrafo)Hijo;
			int puntoCentroBoton =-1;
			if ((A instanceof Arbolgrafo))
			{
				Arbolgrafo B=(Arbolgrafo)A;	
			puntoCentroBoton=B.getBG().getAbsoluteLeft()+(B.getBG().getOffsetWidth()/2);
			}else
				if ((A instanceof ArbolgrafoVacio)){
				ArbolgrafoVacio B=(ArbolgrafoVacio)A;
				puntoCentroBoton=B.getSimplePanel().getAbsoluteLeft()+(B.getSimplePanel().getOffsetWidth()/2);
			}
			if (puntoCentroBoton>X)
				X=puntoCentroBoton;
		}
		return X;
	}

	public int getStartH()
	{
	return SP.getOffsetHeight();	
	}
	
	public int getStartCenterX()
	{
	return getStartX()+(getStartW()/2);	
	}
	
	public int getStartCenterY()
	{
	return getStartY()+(getStartH()/2);	
	}
	
	public int getXCenterOfSimplePanel()
	{
		return PanelBoton.getAbsoluteLeft()+(PanelBoton.getOffsetWidth()/2);	
	}
	
	public int getYBotonOfSimplePanel()
	{
		return PanelBoton.getAbsoluteTop()+PanelBoton.getOffsetHeight();	
	}
}
