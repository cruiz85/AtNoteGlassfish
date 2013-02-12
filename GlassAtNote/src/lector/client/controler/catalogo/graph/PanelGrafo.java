package lector.client.controler.catalogo.graph;

import java.util.ArrayList;

import lector.client.controler.catalogo.BotonesStackPanelMio;
import lector.share.model.client.CatalogoClient;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
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
	private VerticalPanel Panel_Zoom;
	private Button btnNewButton;
	private Button btnNewButton_1;
	private Button btnNewButton_2;
	private CatalogoClient Catalogo;
	private Arbolgrafo AG;
	private static float multiplicador=1.3f; 
	private boolean storedSizes=false;

	public PanelGrafo() {
		
		HorizontalPanel inicial=new HorizontalPanel();
		inicial.setWidth("100%");
		
		AB=new AbsolutePanel();
		initWidget(inicial);
		
		
		if (multiplicador<=1.0f) 
			multiplicador=1.0f;
		
		simplePanel = new SimplePanel();
		AB.add(simplePanel,0,0);
		
		Panel_Zoom = new VerticalPanel();
//		AB.add(Panel_Zoom,0,0);
		
		
		btnNewButton = new Button("Mas");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!storedSizes)
				{
				AG.storeBGSize();
				storedSizes=true;
				}
				multiplicador=multiplicador+0.1f;
				//Go(Catalogo);
				AG.changeMultiplicador();
				AB.setSize(verticalPanel_1.getOffsetWidth()+"px", verticalPanel_1.getOffsetHeight()+"px");
				Pinta();
			}

		});
		btnNewButton.setText("+");
		btnNewButton.setHTML("<img src=\"ZPlus.gif\">");
		Panel_Zoom.add(btnNewButton);
		Panel_Zoom.setCellVerticalAlignment(btnNewButton,
				HasVerticalAlignment.ALIGN_MIDDLE);
		Panel_Zoom.setCellHorizontalAlignment(btnNewButton,
				HasHorizontalAlignment.ALIGN_CENTER);

		btnNewButton_1 = new Button("Menos");
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!storedSizes)
				{
				AG.storeBGSize();
				storedSizes=true;
				}
				multiplicador=multiplicador-0.1f;
				if (multiplicador<0.3f) multiplicador=0.3f;
//				Go(Catalogo);
				AG.changeMultiplicador();
				AB.setSize(verticalPanel_1.getOffsetWidth()+"px", verticalPanel_1.getOffsetHeight()+"px");
				Pinta();
				
			}
		});
		btnNewButton_1.setText("-");
		btnNewButton_1.setHTML("<img src=\"ZLess.gif\">");
		Panel_Zoom.add(btnNewButton_1);

		Panel_Zoom.setCellVerticalAlignment(btnNewButton_1,
				HasVerticalAlignment.ALIGN_MIDDLE);
		Panel_Zoom.setCellHorizontalAlignment(btnNewButton_1,
				HasHorizontalAlignment.ALIGN_CENTER);

		btnNewButton_2 = new Button("Reset");
		btnNewButton_2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!storedSizes)
					{
					AG.storeBGSize();
					storedSizes=true;
					}
				multiplicador=1.0f;
//				Go(Catalogo);
				AG.changeMultiplicador();
				AB.setSize(verticalPanel_1.getOffsetWidth()+"px", verticalPanel_1.getOffsetHeight()+"px");
				Pinta();
			}
		});
		btnNewButton_2.setText("Reset");
		btnNewButton_2.setHTML("<img src=\"Z.gif\">");
		Panel_Zoom.add(btnNewButton_2);

		Panel_Zoom.setCellVerticalAlignment(btnNewButton_2,
				HasVerticalAlignment.ALIGN_MIDDLE);
		Panel_Zoom.setCellHorizontalAlignment(btnNewButton_2,
				HasHorizontalAlignment.ALIGN_CENTER);
		
		
		inicial.add(Panel_Zoom);
		inicial.add(AB);
		
		verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		AB.add(verticalPanel, 0, 0);
		verticalPanel.setSize("100%", "100%");
		
		verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(verticalPanel_1);
		
		
	}

	public void Go(CatalogoClient Entrada) {
		
		storedSizes=false;
		Catalogo=Entrada;
		verticalPanel_1.clear();
		
		Arbolgrafo.clear();
		AG=new Arbolgrafo();
		AG.StartArbolgrafo(Entrada);
		
		verticalPanel_1.add(AG);
		
		AB.setSize(verticalPanel_1.getOffsetWidth()+"px", verticalPanel_1.getOffsetHeight()+"px");
		
		
	}

	public void Pinta() {
		
		simplePanel.clear();
		canvas = new GWTCanvas(verticalPanel_1.getOffsetWidth(), verticalPanel_1.getOffsetHeight());

		canvas.setLineWidth(2*multiplicador);
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
		return  Math.round(finalX-AB.getAbsoluteLeft());
	}

	private double ProcesY(int finalY) {
		return Math.round(finalY-AB.getAbsoluteTop());
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
		canvas.lineTo(ProcesX(linea.getXCenterOfSimplePanel()), ProcesY( linea.getYBotonOfSimplePanel()));
		
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
	
	
	public static float getMultiplicador() {
		return multiplicador;
	}

	public void refreshsize() {
		if (!storedSizes)
		{
		AG.storeBGSize();
		storedSizes=true;
		}
		AG.changeMultiplicador();
		AB.setSize(verticalPanel_1.getOffsetWidth()+"px", verticalPanel_1.getOffsetHeight()+"px");
		Pinta();
		
	}
	
}
