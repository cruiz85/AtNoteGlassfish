package lector.client.catalogo;

import java.util.ArrayList;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.EntityCatalogElements;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.Folder;
import lector.client.controler.Constants;
import lector.client.controler.ErrorConstants;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.EntryClient;
import lector.share.model.client.TypeCategoryClient;
import lector.share.model.client.TypeClient;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;


public class ElementKey extends Composite{
	
	private EntityCatalogElements Entidad;
	private ButtonKey Mas;
	private ButtonKey Label;
	private VerticalPanelEspacial NextBotones;
	private enum State {Open,Close};
	private State Actual;
	//private Image LargeP;
	private Image Compact;
	private Button NoSOns;
	private VerticalPanel verticalPanel;
	private Label Others;
	private boolean Selected;
	private ButtonKey Actulizador;
	private ArrayList<ElementKey> Otros;
	private String StlieOld;
	private Image LargeN;
	private VerticalPanel verticalPanel_1;
	private HorizontalPanel horizontalPanel_1;
	private Image Large1;
	private Image Large2;
	private Button BotonUp;
	private Button BotonDown;
	private ElementKey Yo;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	public static Finder finderAct;
	private HorizontalPanel horizontalPanel_2;
	
	public ElementKey(EntityCatalogElements ent) {
		
		Entidad=ent;
		Selected=false;
		Yo=this;
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		initWidget(horizontalPanel);
//		horizontalPanel.setSize("", "");
		
		verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.add(verticalPanel);
		
		Otros = new ArrayList<ElementKey>();
		Otros.add(this);
		
		HorizontalPanel BotonT = new HorizontalPanel();
		verticalPanel.add(BotonT);
		BotonT.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		BotonT.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		BotonT.setSize("100%", "100%");
		
		horizontalPanel_2 = new HorizontalPanel();
		BotonT.add(horizontalPanel_2);
		horizontalPanel_2.setHeight("100%");
		
		BotonUp = new Button("<img src=\"BotonesTemplate/Arriba.gif\" alt=\"<-\">");
		horizontalPanel_2.add(BotonUp);
		BotonUp.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				TypeCategoryClient EC=(TypeCategoryClient)Entidad.getFatherIdCreador();
				int i=0;
				int resultado=0;
				for (EntryClient hijos : EC.getChildren()) {
					if (hijos.getId().equals(Entidad.getEntry().getId()))
					{
						resultado=i;
					}
					i++;
				}
				if (resultado==0)
					{
					Window.alert("Top");
					}
				else
				{
					if (EC.getId().equals(Constants.CATALOGID))
					{
						EntryClient Aux	= EC.getCatalog().getEntries().get(resultado-1);
						EC.getCatalog().getEntries().set(resultado-1, EC.getCatalog().getEntries().get(resultado));
						EC.getCatalog().getEntries().set(resultado, Aux);
						saveCatalog(EC.getCatalog());
					}else{
					EntryClient Aux=EC.getChildren().get(resultado-1);
					EC.getChildren().set(resultado-1, EC.getChildren().get(resultado));
					EC.getChildren().set(resultado, Aux);
					saveEntry(EC);
					}
				}
			}

			private void saveEntry(EntryClient entrysave) {
				if (entrysave instanceof TypeCategoryClient)
				bookReaderServiceHolder.updateTypeCategory((TypeCategoryClient)entrysave, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						finderAct.RefrescaLosDatos();
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(ErrorConstants.ERROR_ON_MOVE);
						finderAct.RefrescaLosDatos();
					}
				});
				
			}

			private void saveCatalog(CatalogoClient catalog) {
bookReaderServiceHolder.updateCatalog(catalog, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						finderAct.RefrescaLosDatos();
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(ErrorConstants.ERROR_ON_MOVE);
						finderAct.RefrescaLosDatos();
					}
				});
			}


		});
		BotonUp.setSize("100%", "43px");
		
		BotonUp.setStyleName("gwt-ButtonCenterContinuoIzqEnd");
		BotonUp.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoIzqEnd");
			}
		});
		BotonUp.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoIzqEndOver");
			}
		});
		BotonUp.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoIzqEndPush");
			}
		});
		BotonUp.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoIzqEnd");
			}
		});
		
		BotonDown = new Button("<img src=\"BotonesTemplate/Abajo.gif\" alt=\"<-\">");
		horizontalPanel_2.add(BotonDown);
		BotonDown.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				TypeCategoryClient EC=(TypeCategoryClient)Entidad.getFatherIdCreador();
				int i=0;
				int resultado=0;
				for (EntryClient hijos : EC.getChildren()) {
					if (hijos.getId().equals(Entidad.getEntry().getId()))
					{
						resultado=i;
					}
					i++;
				}
				if (resultado==EC.getChildren().size()-1)
					{
					Window.alert("Botton");
					}
				else
				{
					if (EC.getId().equals(Constants.CATALOGID))
					{
						EntryClient Aux	= EC.getCatalog().getEntries().get(resultado+1);
						EC.getCatalog().getEntries().set(resultado+1, EC.getCatalog().getEntries().get(resultado));
						EC.getCatalog().getEntries().set(resultado, Aux);
						saveCatalog(EC.getCatalog());
					}else{
					EntryClient Aux=EC.getChildren().get(resultado+1);
					EC.getChildren().set(resultado+1, EC.getChildren().get(resultado));
					EC.getChildren().set(resultado, Aux);
					saveEntry(EC);
					}
				}
			}

			private void saveEntry(EntryClient entrysave) {
				if (entrysave instanceof TypeCategoryClient)
				bookReaderServiceHolder.updateTypeCategory((TypeCategoryClient)entrysave, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						finderAct.RefrescaLosDatos();
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(ErrorConstants.ERROR_ON_MOVE);
						finderAct.RefrescaLosDatos();
					}
				});
				
			}

			private void saveCatalog(CatalogoClient catalog) {
bookReaderServiceHolder.updateCatalog(catalog, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						finderAct.RefrescaLosDatos();
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(ErrorConstants.ERROR_ON_MOVE);
						finderAct.RefrescaLosDatos();
					}
				});
			}


		});
		BotonDown.setSize("100%", "43px");
		BotonDown.setStyleName("gwt-ButtonCenterContinuoIzq");
		BotonDown.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoIzq");
			}
		});
		BotonDown.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoIzqOver");
			}
		});
		BotonDown.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoIzqPush");
			}
		});
		BotonDown.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoIzq");
			}
		});
		
		Label = new ButtonKey("New button",this);
		//Label = new ButtonKey("New button",null);
		BotonT.add(Label);
		Label.setSize("100%", "43px");
//		Label.setStyleName("gwt-ButtonIzquierdaMIN");
//		Label.addMouseOutHandler(new MouseOutHandler() {
//			public void onMouseOut(MouseOutEvent event) {
//				if (!Selected)
//				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaMIN");
//			}
//		});
//		Label.addMouseOverHandler(new MouseOverHandler() {
//			public void onMouseOver(MouseOverEvent event) {
//				if (!Selected)
//				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaOverMIN");
//			}
//		});
//		Label.addMouseDownHandler(new MouseDownHandler() {
//			public void onMouseDown(MouseDownEvent event) {
//				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaPushMIN");
//			}
//		});
//		Label.addMouseUpHandler(new MouseUpHandler() {
//			public void onMouseUp(MouseUpEvent event) {
//				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaMIN");
//			}
//		});
		
		Label.setStyleName("gwt-ButtonCenterContinuoDoble");

		Label.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoDoblePush");
			}
		});
		
		Label.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (!Selected)
					((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoDoble");
		}
		});
		
		Label.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				if (!Selected)
					((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoDobleOver");
			
		}
	});
		
		Label.addMouseUpHandler(new MouseUpHandler() {
		public void onMouseUp(MouseUpEvent event) {
			((Button)event.getSource()).setStyleName("gwt-ButtonCenterContinuoDoble");
		}
	});
		
		Mas = new ButtonKey("-",this);
		//Mas = new ButtonKey("+",null);
		BotonT.add(Mas);
		Mas.setSize("48px", "43px");
		Mas.setStyleName("gwt-ButtonDerechaMIN");
		Mas.addClickHandler(new ClickHandler() {
					
					public void onClick(ClickEvent event) {
						if (Actual==State.Close)
						{
					//	LargeP.setVisible(true);
						LargeN.setVisible(true);
						Large1.setVisible(true);
						Large2.setVisible(true);
						NextBotones.setVisible(true);
						Mas.setText("-");
						Actual=State.Open;
						Compact.setVisible(false);			
						}else 
						{
						//	LargeP.setVisible(false);
							LargeN.setVisible(false);
							Large1.setVisible(false);
							Large2.setVisible(false);
							NextBotones.setVisible(false);
							Mas.setText("+");
							Actual=State.Close;
							Compact.setVisible(true);	
						}
						if (Entidad instanceof File)
							isAFile();
						
					}
				});
		
		
		Mas.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaMIN");
			}
		});
		Mas.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
					((Button)event.getSource()).setStyleName("gwt-ButtonDerechaOverMIN");
			}
		});
		Mas.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaPushMIN");
			}
		});
		Mas.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaMIN");
			}
		});
		
		
		NoSOns = new Button(" ");
		BotonT.add(NoSOns);
		NoSOns.setSize("48px", "43px");
		NoSOns.setStyleName("gwt-ButtonDerecha");
		
		NoSOns.setVisible(false);
		
		Others = new Label("Press to show others");
		Others.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (StlieOld!=null&&!StlieOld.isEmpty())
				{
				Label.setStyleName(StlieOld);

				for (ElementKey It : Otros) {
					It.setStyleNameLabelOld();
				}
					}
			}
		});
		Others.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				if (StlieOld!=null&&!StlieOld.isEmpty())
					{
					Label.setStyleName(StlieOld);

					for (ElementKey It : Otros) {
						It.setStyleNameLabelOld();
					}
						}
			}
		});
		Others.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				StlieOld=Label.getStyleName();
			//	Label.setStyleName("gwt-ButtonIzquierdaSelectMIN");
				for (ElementKey It : Otros) {
					It.setStyleNameLabelNew();
				}
			}
		});
		Others.setVisible(false);
		Others.setStyleName("gwt-LabelShowMore");
		
		verticalPanel.add(Others);
		
		Actulizador = new ButtonKey("Actualizacion",this);;

		verticalPanel.add(Actulizador);
		Actulizador.setVisible(false);
		Actual=State.Open;
		
		verticalPanel_1 = new VerticalPanel();
		horizontalPanel.add(verticalPanel_1);
		
		Large1 = new Image("TreeKey/11.jpg");
		verticalPanel_1.add(Large1);
		Large1.setSize("40px", "100%");
		
		horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_1.add(horizontalPanel_1);
		
		LargeN = new Image("TreeKey/13.jpg");
		horizontalPanel_1.add(LargeN);
		horizontalPanel_1.setCellHorizontalAlignment(LargeN, HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_1.setCellVerticalAlignment(LargeN, HasVerticalAlignment.ALIGN_MIDDLE);
		LargeN.setSize("40px", "106%");
		
//		Large = new VerticalPanel();
//		horizontalPanel.add(Large);
//		horizontalPanel.setCellHorizontalAlignment(Large, HasHorizontalAlignment.ALIGN_CENTER);
//		Large.setSize("40px", "100%");
//		
//		LargeP = new Image("ArbolLine.jpg");
//		horizontalPanel.add(LargeP);
//				LargeP.setVisible(true);
//				LargeP.setSize("35px", "100%");
		
		Compact = new Image("ArbolLineChico.jpg");
		horizontalPanel_1.add(Compact);
		Compact.setSize("10px", "55px");
		
		NextBotones = new VerticalPanelEspacial(this);
		horizontalPanel_1.add(NextBotones);
		NextBotones.setVisible(true);
		NextBotones.setSize("100%", "");
		
		Large2 = new Image("TreeKey/15.jpg");
		verticalPanel_1.add(Large2);
		Large2.setSize("40px", "100%");
		Compact.setVisible(false);
		
	}

	protected void setStyleNameLabelOld() {
		Label.setStyleName(StlieOld);
		
	}

	protected void setStyleNameLabelNew() {
		StlieOld=Label.getStyleName();
		Label.setStyleName("gwt-ButtonIzquierdaSelectMIN");
		LLamada_En_Cadena();
		
	}

	private void LLamada_En_Cadena() {
		setOpen();
		Widget VPContenedorW =getParent();
		if (VPContenedorW instanceof VerticalPanelEspacial)
		{
			VerticalPanelEspacial VPContenedor =(VerticalPanelEspacial)VPContenedorW;
			VPContenedor.getEK().setOpen();
			VPContenedor.getEK().LLamada_En_Cadena();
		
		}
		
	}

	private void setOpen() {
		//LargeP.setVisible(true);
		LargeN.setVisible(true);
		Large1.setVisible(true);
		Large2.setVisible(true);
		NextBotones.setVisible(true);
		Mas.setText("-");
		Actual=State.Open;
		Compact.setVisible(false);	
		if (Entidad instanceof File)
			isAFile();
		
		
	}

	public void addClickButtonMas(ClickHandler Clic) {
		Actulizador.addClickHandler(Clic);
		
		
		
	}
	
	public void addClickButton(ClickHandler Clic) {
		Label.addClickHandler(Clic);
		
	}

	public EntityCatalogElements getEntidad() {
		return Entidad;
	}

	public void removeItems() {
		NextBotones.clear();
		
	}
	
	public void setHTML(String S,String Text) {
		Label.setHTML("<img src=\""+ S +"\">"+ Text);
	}

	public void addItem(ElementKey a) {
		NextBotones.add(a);
		
	}

	public void setText(String catalogName) {
		Label.setHTML(catalogName);
		
	}

	public void isAFile() {
		Mas.setVisible(false);
		NoSOns.setVisible(true);
		Compact.setVisible(false);
		//LargeP.setVisible(false);
		LargeN.setVisible(false);
		Large1.setVisible(false);
		Large2.setVisible(false);
		NextBotones.setVisible(false);
		
	}
	
	public ButtonKey getMas() {
		return Mas;
	}
	
	public ButtonKey getLabel() {
		return Label;
	}
	
	public boolean isSelected() {
		return Selected;
	}
	
	public void setSelected(boolean selected) {
		Selected = selected;
	}
	
	public ButtonKey getActulizador() {
		return Actulizador;
	}
	
	public Label getOthers() {
		return Others;
	}

//	public void setlabelStyle(String string) {
//		Label.setStyleName(string);
//		for (ElementKey It : Otros) {
//			It.getLabel().setStyleName(string);
//		}
//		
//	}
	public ArrayList<ElementKey> getOtros() {
		return Otros;
	}
	
	public void setOtros(ArrayList<ElementKey> otros) {
		Otros = otros;
	}
	
	public static void setFinderAct(Finder finderAct) {
		ElementKey.finderAct = finderAct;
	}
	
	public static Finder getFinderAct() {
		return finderAct;
	}
	
	public void setBotonUpState(boolean botonUp) {
		BotonUp.setEnabled(botonUp);
		BotonUp.setVisible(botonUp);
		
	}
	
	public void setBotonDownState(boolean botonDown) {
		BotonDown.setEnabled(botonDown);
		BotonDown.setVisible(botonDown);
	}
}
