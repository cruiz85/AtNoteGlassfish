package lector.client.catalogo.OwnGraph;

import java.util.ArrayList;

import lector.client.catalogo.BotonesStackPanelMio;
import lector.share.model.client.CatalogoClient;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
import com.google.gwt.user.client.ui.Button;

public class PanelGrafo extends Composite {

	private VerticalPanel verticalPanel;
	private VerticalPanel verticalPanel_1;
	private SimplePanel simplePanel;
	private AbsolutePanel AB;
	private GWTCanvas canvas;

	public PanelGrafo() {
		
		AB=new AbsolutePanel();
		initWidget(AB);
		
		verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		AB.add(verticalPanel, 0, 0);
		verticalPanel.setSize("100%", "100%");
		
		verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(verticalPanel_1);
		
		simplePanel = new SimplePanel();
		AB.add(simplePanel,0,0);
		
		
		
		
	}

	public void Go(CatalogoClient Entrada) {
		verticalPanel_1.clear();
		simplePanel.clear();
		Arbolgrafo.clear();
		Arbolgrafo AG=new Arbolgrafo();
		AG.StartArbolgrafo(Entrada);
		
		
		verticalPanel_1.add(AG);
		
		AB.setSize(verticalPanel_1.getOffsetWidth()+"px", verticalPanel_1.getOffsetHeight()+"px");
		
		
	}

	public void Pinta() {
		
		canvas = new GWTCanvas(verticalPanel_1.getOffsetWidth(), verticalPanel_1.getOffsetHeight());

		canvas.setLineWidth(1);
		canvas.setStrokeStyle(new Color(92, 144, 188));
		
		simplePanel.setWidget(canvas);
		
		ArrayList<Linea> LineasPintar=Arbolgrafo.getLineasT();
		
		for (Linea linea : LineasPintar) {
			if (linea instanceof LineasT)
				pintaLineaT((LineasT)linea);
			if (linea instanceof LineasUTB)
				pintaLineaUTB((LineasUTB)linea);
			if (linea instanceof LineasUTP)
				pintaLineaUTP((LineasUTP)linea);
		}
		
	}
	
	private void pintaLineaUTP(LineasUTP linea) {
		canvas.beginPath();
		canvas.moveTo(ProcesX(linea.getFinalX()),ProcesY( linea.getFinalY()));
		canvas.lineTo(ProcesX(linea.getCodoX()),ProcesY(linea.getCodoY()));
		
		canvas.moveTo(ProcesX(linea.getCodoX()),ProcesY(linea.getCodoY()));
		canvas.lineTo(ProcesX(linea.getStartX()),ProcesY(linea.getStartY()));
		
		canvas.closePath();
		canvas.stroke();
		
	}

	private double ProcesX(int finalX) {
		return finalX-AB.getAbsoluteLeft();
	}

	private int ProcesY(int finalY) {
		return finalY-AB.getAbsoluteTop();
	}

	private void pintaLineaUTB(LineasUTB linea) {
		canvas.beginPath();
		canvas.moveTo(ProcesX(linea.getStartX()),ProcesY(linea.getStartY()));
		canvas.lineTo(ProcesX(linea.getFinalX()), ProcesY(linea.getFinalY()));
		
		pintatriangulo(linea);
		
		canvas.closePath();
		canvas.stroke();
		
		
		
	}

	private void pintatriangulo(LineasUTB linea) {
		
		canvas.moveTo(ProcesX(linea.getFinalX()-5), ProcesY(linea.getFinalY()-5));
		canvas.lineTo(ProcesX(linea.getFinalX()), ProcesY(linea.getFinalY()));
		canvas.lineTo(ProcesX(linea.getFinalX()+5),ProcesY(linea.getFinalY()-5));

	}

	private void pintaLineaT(LineasT linea) {
		canvas.beginPath();
		canvas.moveTo(ProcesX(linea.getStartX()),ProcesY( linea.getStartCenterY()));
		canvas.lineTo(ProcesX(linea.getStartX()+linea.getStartW()),ProcesY( linea.getStartCenterY()));
		
		canvas.moveTo(ProcesX(linea.getXCenterOfSimplePanel()), ProcesY( linea.getStartCenterY()));
		canvas.lineTo(ProcesX(linea.getXCenterOfSimplePanel()), ProcesY( linea.getStartY()));
		
		canvas.closePath();
		canvas.stroke();
		
	}

	public void clear()
	{
		verticalPanel_1.clear();
		simplePanel.clear();
	}

	public static void setAccionAsociada(ClickHandler accionAsociada) {
		Arbolgrafo.setAccionAsociada(accionAsociada);
	}

	public static ClickHandler getAccionAsociada() {
		return Arbolgrafo.getAccionAsociada();
	}

	public static void setBotonTipo(BotonesStackPanelMio buttonMio) {
		Arbolgrafo.setButonTipo(buttonMio);

	}
	
	
	
}
