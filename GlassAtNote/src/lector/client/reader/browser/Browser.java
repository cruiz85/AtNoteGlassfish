package lector.client.reader.browser;

import java.util.ArrayList;
import java.util.List;

import lector.client.admin.generalPanels.BotonesStackPanelAdministracionMio;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.Finder;
import lector.client.catalogo.FinderKeys;
import lector.client.catalogo.FinderOwnGrafo;
import lector.client.catalogo.OwnGraph.BotonGrafo;
import lector.client.catalogo.OwnGraph.PanelGrafo;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.EntityCatalogElements;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.Folder;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.reader.ButtonTipo;
import lector.client.reader.LoadingPanel;
import lector.share.model.Annotation;
import lector.share.model.Language;
import lector.share.model.client.AnnotationClient;
import lector.share.model.client.EntryClient;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.TypeCategoryClient;
import lector.share.model.client.TypeClient;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.DecoratedTabBar;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;

public class Browser implements EntryPoint {

	private Finder FinderButton;
	private VerticalPanel Selected;
	private Finder FinderButton2;
	private static VerticalPanel SelectedB;
	private Language ActualLang;
	private static Button btnNewButton;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private static ArrayList<TypeClient> filtroResidual;

public enum CatalogTipo {
		
		Catalog1("<img src=\"File.gif\">"), Catalog2("<img src=\"File2.gif\">");
		
		private String Texto;
		
		private CatalogTipo(String A) {
			Texto=A;
		}
		
		public String getTexto() {
			return Texto;
		}
		
	};
	
	public Browser() {
		SelectedB = new VerticalPanel();
	}
	
	public void onModuleLoad() {
		
		ActualLang=ActualState.getLanguage();
		
		RootPanel rootPanel = RootPanel.get();
		RootPanel RootMenu = RootPanel.get("Menu");
//		
		
//		RootPanel rootPanel = RootPanel.get();
		
		SplitLayoutPanel WindowPanel = new SplitLayoutPanel();
		rootPanel.add(WindowPanel,0, 25);
		WindowPanel.setSize("100%", "96%");
		
		SplitLayoutPanel BrowserSelectPanel = new SplitLayoutPanel();
		WindowPanel.add(BrowserSelectPanel);
		
//		SimplePanel simplePanel = new SimplePanel();
//		splitLayoutPanel_1.addNorth(simplePanel, 110.0);
		
		Selected = new VerticalPanel();
		BrowserSelectPanel.addSouth(Selected, 200.0);
		Selected.setWidth("100%");
		Selected.add(SelectedB);
		SelectedB.setWidth("100%");
		
		 if (ActualState.getReadingactivity().getVisualization()==null||ActualState.getReadingactivity().getVisualization().equals(Constants.VISUAL_ARBOL))
	        {
			 
			 FinderButton= new FinderOwnGrafo(ActualState.getOpenCatalog());
			// FinderButton1.RefrescaLosDatos();
	        }
	        else 
	        {
	        	FinderButton= new FinderKeys();
	        	FinderButton.setCatalogo(ActualState.getOpenCatalog());
	        //	FinderButton1.RefrescaLosDatos();
	        }
		
		 if (ActualState.getReadingactivity().getVisualization()==null||ActualState.getReadingactivity().getVisualization().equals(Constants.VISUAL_ARBOL))
	        {
			 FinderButton2= new FinderOwnGrafo(ActualState.getCatalogo());
			// FinderButton1.RefrescaLosDatos();
	        }
	        else 
	        {
	        	FinderButton2= new FinderKeys();
	        	FinderButton2.setCatalogo(ActualState.getCatalogo());
	        //	FinderButton1.RefrescaLosDatos();
	        }
		
		FinderOwnGrafo.setButtonTipoGrafo(new BotonGrafo(
				"prototipo", new VerticalPanel(), SelectedB,FinderButton));
		FinderOwnGrafo.setBotonClickGrafo(new ClickHandler() {

			public void onClick(ClickEvent event) {
				
				BotonGrafo BS=((BotonGrafo) event.getSource());
		        EntityCatalogElements Act=(EntityCatalogElements) BS.getEntidad();
		        
		        if (Act instanceof File || ((Act instanceof Folder)&&(Act.getEntry().getId()!=Constants.CATALOGID)))
		        {
		        	
		        	ButtonTipo nuevo;
		        	if (BS.getF().getCatalogo().getId().equals(FinderButton.getCatalogo().getId()))
		        		nuevo=new ButtonTipo(Act,CatalogTipo.Catalog1.getTexto(),BS.getSelectionPanel());
		        	else nuevo=new ButtonTipo(Act,CatalogTipo.Catalog2.getTexto(),BS.getSelectionPanel());
		        	nuevo.setSize("100%", "100%");
		        	nuevo.addClickHandler(new ClickHandler() {
		        		
						public void onClick(ClickEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
							
						}
					});
				
		        	nuevo.addMouseDownHandler(new MouseDownHandler() {
						public void onMouseDown(MouseDownEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
						}
					});
					

		        	nuevo.addMouseOutHandler(new MouseOutHandler() {
						public void onMouseOut(MouseOutEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
					}
				});
					

		        	nuevo.addMouseOverHandler(new MouseOverHandler() {
						public void onMouseOver(MouseOverEvent event) {
							
							((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
						
					}
				});

		        	nuevo.setStyleName("gwt-ButtonCenter");
		        	nuevo.addClickHandler(new ClickHandler() {
						
						public void onClick(ClickEvent event) {
							ButtonTipo Yo=(ButtonTipo)event.getSource();
							Yo.getPertenezco().remove(Yo);
							if (SelectedB.getWidgetCount()==0)btnNewButton.setVisible(false);
							else btnNewButton.setVisible(true);
							
						}
					});
		        	if (!ExistPreview((Panel) BS.getSelectionPanel(),Act))
		        			BS.getSelectionPanel().add(nuevo);
		        	else Window.alert(ActualState.getLanguage().getE_ExistBefore());
		        }
				
								
				if (SelectedB.getWidgetCount()==0)btnNewButton.setVisible(false);
				else btnNewButton.setVisible(true);
				
			}

			private boolean ExistPreview(Panel labeltypo, EntityCatalogElements act) {
				for (int i = 0; i < ((ComplexPanel) labeltypo).getWidgetCount(); i++) {
					EntityCatalogElements temp = ((ButtonTipo)((ComplexPanel) labeltypo).getWidget(i)).getEntidad();
					if (temp.getEntry().getId()==act.getEntry().getId()) return true;
					
				}
				return false;
			}
		});
		
		BotonesStackPanelBrowser.setBrowser(this);
		FinderKeys.setButtonTipo(new BotonesStackPanelBrowser(
				"prototipo", new VerticalPanel(),SelectedB,FinderButton));
		 FinderKeys.setBotonClick(new ClickHandlerMioFilterBrowser(FinderButton));
		 
		 
	//	FinderButton1.setCatalogo(ActualUser.getOpenCatalog());
		
		 //FinderButton1 = new FinderGrafo(ActualUser.getOpenCatalog());
		
		
	//	FinderButton2 = new FinderGrafo(ActualUser.getCatalogo());
		
		
				
		
				FinderKeys.setButtonTipo(new BotonesStackPanelBrowser(
						"prototipo", new VerticalPanel(), SelectedB,FinderButton2));
				 FinderKeys.setBotonClick(new ClickHandlerMioFilterBrowser(FinderButton2));
				 
				 
				
				 FinderButton2.setSize("100%", "100%");
		//FinderButton = new FinderGrafo(ActualUser.getOpenCatalog());
		
		
		FinderOwnGrafo.setButtonTipoGrafo(new BotonGrafo(
				"prototipo", new VerticalPanel(), SelectedB,FinderButton2));
		
		btnNewButton = new Button(ActualLang.getFilterButtonBrowser());
		Selected.add(btnNewButton);
		btnNewButton.setStyleName("gwt-MenuItemMio");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				refresh(); 
			}
		});
		btnNewButton.setStyleName("gwt-ButtonBotton");
		btnNewButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
			}
		});
		btnNewButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
			}
		});
		btnNewButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
			}
		});
		if (SelectedB.getWidgetCount()==0) btnNewButton.setVisible(false);
		btnNewButton.setSize("100%", "100%");
		
		
//		FinderOwnGrafo.setBotonClickGrafo(new ClickHandler() {
//
//public void onClick(ClickEvent event) {
//				
//				BotonGrafo BS=((BotonGrafo) event.getSource());
//		        EntityCatalogElements Act=(EntityCatalogElements) BS.getEntidad();
//		        
//		        if (Act instanceof File || ((Act instanceof Folder)&&(Act.getEntry().getId()!=Constants.CATALOGID)))
//		        {
//		        	ButtonTipo nuevo=new ButtonTipo(Act,CatalogTipo.Catalog1.getTexto(),BS.getSelectionPanel());
//		        	nuevo.setSize("100%", "100%");
//		        	nuevo.addClickHandler(new ClickHandler() {
//						
//						public void onClick(ClickEvent event) {
//							((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
//							
//						}
//					});
//				
//		        	nuevo.addMouseDownHandler(new MouseDownHandler() {
//						public void onMouseDown(MouseDownEvent event) {
//							((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
//						}
//					});
//					
//
//		        	nuevo.addMouseOutHandler(new MouseOutHandler() {
//						public void onMouseOut(MouseOutEvent event) {
//							((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
//					}
//				});
//					
//
//		        	nuevo.addMouseOverHandler(new MouseOverHandler() {
//						public void onMouseOver(MouseOverEvent event) {
//							
//							((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
//						
//					}
//				});
//
//		        	nuevo.setStyleName("gwt-ButtonCenter");
//		        	nuevo.addClickHandler(new ClickHandler() {
//						
//						public void onClick(ClickEvent event) {
//							ButtonTipo Yo=(ButtonTipo)event.getSource();
//							Yo.getPertenezco().remove(Yo);
//							
//							
//						}
//					});
//		        	if (!ExistPreview(BS.getSelectionPanel(),Act))
//		        			BS.getSelectionPanel().add(nuevo);
//		        	else Window.alert(ActualUser.getLanguage().getE_ExistBefore());
//		        }
//				
//								
//				if (SelectedB.getWidgetCount()==0)btnNewButton.setVisible(false);
//				else btnNewButton.setVisible(true);
//				
//			}
//
//			private boolean ExistPreview(Panel labeltypo, EntityCatalogElements act) {
//				for (int i = 0; i < ((ComplexPanel) labeltypo).getWidgetCount(); i++) {
//					EntityCatalogElements temp = ((ButtonTipo)((ComplexPanel) labeltypo).getWidget(i)).getEntidad();
//					if (temp.getEntry().getId()==act.getEntry().getId()) return true;
//					
//				}
//				return false;
//			}
//		});

		FinderButton.setSize("100%", "100%");
		
		ScrollPanel scrollPanel = new ScrollPanel();
		BrowserSelectPanel.add(scrollPanel);
		scrollPanel.setSize("", "");
		
		DecoratedTabPanel decoratedTabPanel = new DecoratedTabPanel();
		decoratedTabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
			public void onSelection(SelectionEvent<Integer> event) {
				int selectedd=event.getSelectedItem();
				if (selectedd==0)
				{
					
				FinderKeys.setBotonClick(new ClickHandlerMioFilterBrowser(FinderButton2));
				FinderButton2.RefrescaLosDatos();
				}
				else  {
					
					FinderKeys.setBotonClick(new ClickHandlerMioFilterBrowser(FinderButton));
					FinderButton.RefrescaLosDatos();
				}
			}
		});
		scrollPanel.setWidget(decoratedTabPanel);
		decoratedTabPanel.setSize("100%", "100%");
		SimplePanel CatalogoProf= new SimplePanel();
		decoratedTabPanel.add(CatalogoProf, ActualLang.getTeacherTypes(), false);
		CatalogoProf.setSize("100%", "98%");
		CatalogoProf.add(FinderButton2);
		
		decoratedTabPanel.selectTab(0);
		//FinderButton2.setCatalogo(ActualUser.getCatalogo());
		
		//FinderButton.setCatalogo(ActualUser.getOpenCatalog());
		
		SimplePanel CatalogoAbierto= new SimplePanel();
		decoratedTabPanel.add(CatalogoAbierto, ActualLang.getOpenTypes(), false);
		CatalogoAbierto.setSize("100%", "98%");
		CatalogoAbierto.add(FinderButton);;
		
		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);
		menuBar.setWidth("100%");
		
		MenuItem BotonClose = new MenuItem("New item", false, new Command() {
			public void execute() {
				PanelGrafo.setAccionAsociada(null);
				Controlador.change2Reader();
			}
		});


		BotonClose.setHTML(ActualLang.getClose());
		menuBar.addItem(BotonClose);
		BotonClose.setWidth("100%");
		
		
		
		
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto(ActualLang.getLoading());
				LoadingPanel.getInstance().hide();
			
	}

	public void refresh() {
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto(ActualLang.getFiltering());
		ArrayList<EntryClient> Tipos=new ArrayList<EntryClient>();
		for (int i = 0; i < SelectedB.getWidgetCount(); i++) {
			if (SelectedB.getWidget(i) instanceof ButtonTipo)
			{
			ButtonTipo BSM= (ButtonTipo)SelectedB.getWidget(i);
			Tipos.add(((EntityCatalogElements)BSM.getEntidad()).getEntry()) ;
			}
			else {

BotonesStackPanelBrowser BSM= (BotonesStackPanelBrowser)SelectedB.getWidget(i);
Tipos.add(((EntityCatalogElements)BSM.getEntidad()).getEntry()) ;
			}
		}
		
		ArrayList<TypeClient> TCClear=new ArrayList<TypeClient>();
		for (EntryClient typeClient : Tipos) {
			if (typeClient instanceof TypeClient)
				TCClear.add((TypeClient) typeClient);
			else {
				ArrayList<TypeClient> result=procesaTipo((TypeCategoryClient) typeClient);
				if (result!=null&&!result.isEmpty())
					TCClear.addAll(result);
			}
		}
		
			generafiltro(TCClear);
			filterAndAdd(TCClear);
			LoadingPanel.getInstance().hide();
//		bookReaderServiceHolder.getEntriesIdsByIdsRec(Tipos, new AsyncCallback<ArrayList<FileDB>>() {
//			
//			public void onSuccess(ArrayList<FileDB> result) {
//				generafiltro(result);
//				filterAndAdd(result);
//				
//				
//			}
//			
//			private void generafiltro(ArrayList<FileDB> result) {
//				filtroResidual=new ArrayList<Long>();
//				for (FileDB long1 : result) {
//					filtroResidual.add(long1.getId());
//				}
//			}
//
//			public void onFailure(Throwable caught) {
//				Window.alert(ActualLang.getE_filteringmesagetypes());
//				LoadingPanel.getInstance().hide();
//			}
//		});
	}

	private void generafiltro(ArrayList<TypeClient> tCClear) {
		filtroResidual=new ArrayList<TypeClient>();
		for (TypeClient long1 : tCClear) {
			filtroResidual.add(long1);
		}
	}

	private ArrayList<TypeClient> procesaTipo(TypeCategoryClient typeClient) {
		ArrayList<TypeClient> Salida=new ArrayList<TypeClient>();
		for (EntryClient typeClient2 : typeClient.getChildren()) {
			if (typeClient2 instanceof TypeClient)
				Insert(Salida,(TypeClient) typeClient2);
			else {
				ArrayList<TypeClient> result=procesaTipo((TypeCategoryClient) typeClient2);
				if (result!=null&&!result.isEmpty())
					for (TypeClient typeClient3 : result) {
						Insert(Salida,(TypeClient) typeClient3);
					}
			}
		}
		return Salida;
	}

	private void Insert(ArrayList<TypeClient> salida, TypeClient typeClient2) {
		boolean encontrado = false;
		for (TypeClient typeClient : salida) {
			if (typeClient.getId().equals(typeClient2.getId()))
			{
			encontrado=true;
			break;
			}
		}
		
		if (!encontrado) salida.add((TypeClient) typeClient2);
		
	}

	protected void filterAndAdd(ArrayList<TypeClient> result) {
		
		//TODO
		Window.alert(result.toString());
		
		
		ArrayList<Long> ArrayTypes=new ArrayList<Long>();
		for (TypeClient long1 : result) {
			ArrayTypes.add(long1.getId());
		}
		
		AsyncCallback<List<AnnotationClient>> callback=new AsyncCallback<List<AnnotationClient>>() {
			
			public void onSuccess(List
					<AnnotationClient> result) {
				VerticalPanel AnnotationPanel=new VerticalPanel();
				AnotationResultPanel APR=new AnotationResultPanel(AnnotationPanel);
				AnnotationPanel.clear();
				for (AnnotationClient AIndiv : result) {
					AnnotationPanel.add(new CommentPanelBrowser(AIndiv, new Image(ActualState.getBook().getWebLinks().get(AIndiv.getPageNumber())),APR.getHeight()));
				}
				LoadingPanel.getInstance().hide();
				
				APR.center();
			}
			
			public void onFailure(Throwable caught) {
				Window.alert(ActualLang.getE_filteringmesageAnnotations());
				LoadingPanel.getInstance().hide();
				
			}
		};
		if (ActualState.getUser() instanceof ProfessorClient){
		bookReaderServiceHolder.getAnnotationsByTypeClientIdsForProfessor(ArrayTypes, ActualState.getReadingactivity().getId(), ActualState.getUser().getId(), callback);
		}
		else 
			bookReaderServiceHolder.getAnnotationsByTypeClientIdsForStudent(ArrayTypes, ActualState.getReadingactivity().getId(), ActualState.getUser().getId(), callback);
		
	}
	
	public static ArrayList<TypeClient> getFiltroResidual() {
		return filtroResidual;
	}
	
	public static void refreshButton()
	{
		if (SelectedB.getWidgetCount()==0)btnNewButton.setVisible(false);
		else btnNewButton.setVisible(true);
	}
	
	public static VerticalPanel getSelectedB() {
		return SelectedB;
	}
	
	public static Button getBtnNewButton() {
		return btnNewButton;
	}
	
	public Finder getFinderButton() {
		return FinderButton;
	}
	
	public Finder getFinderButton2() {
		return FinderButton2;
	}
}
