package lector.client.catalogo.OwnGraph;

import java.util.ArrayList;

import lector.client.catalogo.BotonesStackPanelMio;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.EntryClient;
import lector.share.model.client.TypeCategoryClient;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.SimplePanel;



public class Arbolgrafo extends Composite implements ElementoGrafo{

	private HorizontalPanel PanelNodo;
	private VerticalPanel PanelLineasHorizontales;
	private HorizontalPanel PaneLHijos;
	private static ArrayList<Linea> Lineas;
	private static ArrayList<ElementosAPintar> Pendientes;
	private static ArrayList<ElementoSiguiente> LevelActual;
	private static ArrayList<Arbolgrafo> LevelActualquitados;
	private static int aumentarhermanos=0; 
	private static ArrayList<ElementoSiguiente> LevelSiguiente;
	private SimplePanel Linea;
	private BotonGrafo BG;
	private HorizontalPanel horizontalPanel_2;
	
	private static ClickHandler AccionAsociada;
	private static BotonesStackPanelMio ButonTipo;
	

public Arbolgrafo() {
	ConstruccionEstandar();
}
	
	public void StartArbolgrafo(CatalogoClient entrada) {
		
		if (ButonTipo!=null)
			BG=(BotonGrafo) ButonTipo.Clone();
		else 
			BG=new BotonGrafo("vacio", new VerticalPanel(), new HorizontalPanel(), null);
		if (AccionAsociada!=null)
			BG.addClickHandler(AccionAsociada);
		BG.setCatalogo(entrada);
		BG.setStyleName("gwt-ButtonCenter");
		
		BG.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
				
			}
		});

		BG.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		
		BG.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
		});
		
		BG.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			
		}
		});
		
		//BG=new BotonGrafo(entrada);
		PanelNodo.add(BG);
		LineasT LT=new LineasT(Linea,PaneLHijos);
		Lineas.add(LT);
		
		for (EntryClient hijoEntry : entrada.getEntries()) {
			LevelSiguiente.add(new ElementoSiguiente(hijoEntry,PaneLHijos,LT));
			Pendientes.add(new ElementosAPintar(hijoEntry,true));
			}
		
		nextLevel();
	}

	private void nextLevel() {
		if (LevelActual.isEmpty())
			{
			if (!LevelSiguiente.isEmpty()){
				LevelActualquitados.clear();
				aumentarhermanos=0;
				LevelActual=LevelSiguiente;
				LevelSiguiente=new ArrayList<ElementoSiguiente>();
				nextLevel();
				
			}
			}
		else 
		{
			ElementoSiguiente ES=LevelActual.remove(0);
			StartArbolgrafo(ES);
		}
	}

	private void ConstruccionEstandar() {
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		PanelLineasHorizontales = new VerticalPanel();
		PanelLineasHorizontales.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		PanelLineasHorizontales.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(PanelLineasHorizontales);
		PanelLineasHorizontales.setSize("100%", "");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel_1);
		horizontalPanel_1.setWidth("100%");
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_1.add(verticalPanel_1);
		
		horizontalPanel_2 = new HorizontalPanel();
		verticalPanel_1.add(horizontalPanel_2);
		horizontalPanel_2.setHeight("10px");
		
		PanelNodo = new HorizontalPanel();
		PanelNodo.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_1.add(PanelNodo);
		PanelNodo.setHeight("24px");
		
		Linea = new SimplePanel();
		verticalPanel.add(Linea);
		Linea.setSize("100%", "10px");
		
		PaneLHijos = new HorizontalPanel();
		PaneLHijos.setSpacing(5);
		PaneLHijos.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(PaneLHijos);
		PaneLHijos.setWidth("100%");
		
	}

	public void StartArbolgrafo(ElementoSiguiente entrada) {

		ElementosAPintar futuro=findEntryEnEelementosAPintar(entrada.getHijoEntry());
		if (futuro.addAparicion())
		{

		Pendientes.remove(futuro);	
		Arbolgrafo Next=new Arbolgrafo();
		for (int i = 0; i < aumentarhermanos; i++) {
			VerticalPanel V=Next.getPanelLineasHorizontales();
			SimplePanel SP=new SimplePanel();
			SP.setSize("10px", "10px");
			V.add(SP);
		}
		boolean A=false;
		SimplePanel SP=new SimplePanel();
		for (Connect2vs3 lineasunion : futuro.getLineasAConectar()) {
			VerticalPanel V=Next.getPanelLineasHorizontales();
			SP.setSize("10px", "10px");
			V.add(SP);
			Lineas.add(new LineasUTP(lineasunion, SP));
			A=true;
			
		}
		if (A)
			{
			int modificar=1;
			actualizaHermanos(modificar);
			aumentarhermanos++;
			}
//		else
//		{
//			int modificar=aumentarhermanos-A;
//			for (int i = 0; i < modificar; i++) {
//				VerticalPanel V=Next.getPanelLineasHorizontales();
//				SimplePanel SP=new SimplePanel();
//				SP.setSize("10px", "10px");
//				V.add(SP);
//			}
//		}
		
		entrada.getPaneLHijos().add(Next);
		LevelActualquitados.add(Next);
		Next.evaluaSubArbol(entrada.getHijoEntry());
		Lineas.add(new LineasUTB(entrada.getLineaCuelgue(), Next.getBG()));
		}
		else{
		ArbolgrafoVacio Vacio=new ArbolgrafoVacio();
		futuro.addLineaAConectar(entrada.getLineaCuelgue(),Vacio.getSimplePanel());
			entrada.getPaneLHijos().add(Vacio);
			nextLevel();
		}
		}
	
	private void actualizaHermanos(int modificar) {
		for (Arbolgrafo ArbolHermano : LevelActualquitados) {
			for (int i = 0; i < modificar; i++) {
				VerticalPanel V=ArbolHermano.getPanelLineasHorizontales();
				SimplePanel SP=new SimplePanel();
				SP.setSize("10px", "10px");
				V.add(SP);
				
			}
			
		}
		
	}

	private void evaluaSubArbol(EntryClient entryClient) {
		
		if (ButonTipo!=null)
			BG=(BotonGrafo) ButonTipo.Clone();
		else 
			BG=new BotonGrafo("vacio", new VerticalPanel(), new HorizontalPanel(), null);
		BG.setEntry(entryClient);
		BG.setStyleName("gwt-ButtonCenter");
		PanelNodo.add(BG);
		
BG.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
				
			}
		});

		BG.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		
		BG.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
		});
		
		BG.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			
		}
		});
		
		if (entryClient instanceof TypeCategoryClient){
			if (!((TypeCategoryClient)entryClient).getChildren().isEmpty()){
				LineasT LT=new LineasT(Linea,PaneLHijos);
				Lineas.add(LT);
			
			for (EntryClient hijoEntry : ((TypeCategoryClient)entryClient).getChildren()) {
				LevelSiguiente.add(new ElementoSiguiente(hijoEntry,PaneLHijos,LT));
				if (findEntryEnEelementosAPintar(hijoEntry)==null)
					Pendientes.add(new ElementosAPintar(hijoEntry,false));
			}
			}
		}	
		nextLevel();
			
		
	}

	private ElementosAPintar findEntryEnEelementosAPintar(EntryClient hijoEntry) {
		for (ElementosAPintar EAPEvaluar : Pendientes) {
			if (EAPEvaluar.getElemento().getId().equals(hijoEntry.getId()))
				return EAPEvaluar;
		}
		return null;
	}

	public static void clear() {
		LevelActual=new ArrayList<ElementoSiguiente>();
		LevelSiguiente=new ArrayList<ElementoSiguiente>();
		Lineas=new ArrayList<Linea>();
		Pendientes=new ArrayList<ElementosAPintar>();
		LevelActualquitados=new ArrayList<Arbolgrafo>();
		aumentarhermanos=0;
	}

	
	public static ArrayList<Linea> getLineasT() {
		return Lineas;
	}
	
	public BotonGrafo getBG() {
		return BG;
	}
	
	public VerticalPanel getPanelLineasHorizontales() {
		return PanelLineasHorizontales;
	}

	public static ClickHandler getAccionAsociada() {
		return AccionAsociada;
	}

	public static void setAccionAsociada(ClickHandler accionAsociada) {
		AccionAsociada = accionAsociada;
	}

	public static BotonesStackPanelMio getButonTipo() {
		return ButonTipo;
	}

	public static void setButonTipo(BotonesStackPanelMio butonTipo) {
		ButonTipo = butonTipo;
	}
	
	
}
