package lector.client.admin.group;

import java.util.ArrayList;
import java.util.List;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Controlador;
import lector.share.model.GroupApp;
import lector.share.model.client.GroupClient;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class Groupadministration implements EntryPoint {

	private SimplePanel Panel_grupo_seleccionado;
	private VerticalPanel Panel_de_grupos;
	private Groupadministration Yo;
	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	private ButtonGroupClient ActualSelect;
	private String OldStyle;
	
	public void onModuleLoad() {
		Yo=this;
		RootPanel RootTXOriginal = RootPanel.get();
		RootPanel RootMenu = RootPanel.get("Menu");
		RootTXOriginal.setSize("100%", "100%");
		RootMenu.setStyleName("Root2");
		RootTXOriginal.setStyleName("Root");
		
		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);
		
		MenuItem mntmNewItem = new MenuItem("Group", false, (Command) null);
		mntmNewItem.setEnabled(false);
		mntmNewItem.setHTML("Group Administration");
		menuBar.addItem(mntmNewItem);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		
		MenuItem mntmNewItem_1 = new MenuItem("New", false, new Command() {
			public void execute() {
				NewGroupPopUpPanel NG=new NewGroupPopUpPanel(Yo);
				NG.center();
				NG.setModal(true);
			}
		});
		mntmNewItem_1.setHTML("New");
		menuBar.addItem(mntmNewItem_1);
		
		MenuItem mntmNewItem_2 = new MenuItem("Back", false, new Command() {
			public void execute() {
				Controlador.change2Administrator();
			}
		});
		mntmNewItem_2.setHTML("Back");
		menuBar.addItem(mntmNewItem_2);
		
		SimplePanel simplePanel = new SimplePanel();
		simplePanel.setStyleName("fondoLogo");
		RootTXOriginal.add(simplePanel,0,25);
		simplePanel.setSize("100%", "100%");
		
		HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
		simplePanel.setWidget(horizontalSplitPanel);
		horizontalSplitPanel.setSize("100%", "100%");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setSpacing(6);
		horizontalSplitPanel.setLeftWidget(verticalPanel);
		verticalPanel.setSize("100%", "");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setStyleName("AzulTransparente");
		horizontalPanel_1.setSpacing(10);
		verticalPanel.add(horizontalPanel_1);
		horizontalPanel_1.setWidth("100%");
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setSpacing(6);
		verticalPanel_1.setStyleName("BlancoTransparente");
		horizontalPanel_1.add(verticalPanel_1);
		verticalPanel_1.setWidth("100%");
		
		Panel_de_grupos = new VerticalPanel();
		verticalPanel_1.add(Panel_de_grupos);
		Panel_de_grupos.setWidth("100%");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setStyleName("BlancoTransparente");
		horizontalSplitPanel.setRightWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
		Panel_grupo_seleccionado = new SimplePanel();
		horizontalPanel.add(Panel_grupo_seleccionado);
		Panel_grupo_seleccionado.setSize("100%", "100%");
		
		bookReaderServiceHolder.getGroupsByUserId(ActualState.getUser().getId(), new AsyncCallback<List<GroupClient>>() {
			
			public void onSuccess(List<GroupClient> result) {
			
					for (int i = 0; i < result.size()-1; i++){
					ButtonGroupClient btnNewButton = new ButtonGroupClient(result.get(i));
					btnNewButton.addClickHandler(new ClickHandler() {


						public void onClick(ClickEvent event) {
							
							
							if (ActualSelect!=null)
								{
								ActualSelect.setStyleName(OldStyle);
								}
							ActualSelect=(ButtonGroupClient)event.getSource();
							ActualSelect.setStyleName("gwt-ButtonTOPSelect");
							OldStyle="gwt-ButtonTOP";
							Panel_grupo_seleccionado.clear();
							Panel_grupo_seleccionado.add(new GroupAndUserPanel(ActualSelect.getGroupClient(),Panel_grupo_seleccionado,Yo));
							
//							bookReaderServiceHolder.loadGroupByName(BotonEvento, new AsyncCallback<GroupApp>() {
//
//								public void onFailure(Throwable caught) {
//									Window.alert("The group could not be loaded");
//									
//								}
//
//								public void onSuccess(GroupApp result) {
//									Panel_grupo_seleccionado.clear();
//									Panel_grupo_seleccionado.add(new GroupAndUserPanel(result,Panel_grupo_seleccionado,Yo));
//								}
//							});
							
							
							
						}
					});
					Panel_de_grupos.add(btnNewButton);
					btnNewButton.setSize("100%", "100%");
					btnNewButton.addMouseOutHandler(new MouseOutHandler() {
						public void onMouseOut(MouseOutEvent event) {
							if (ActualSelect!=(Button)event.getSource()) 
								((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
							else ((Button)event.getSource()).setStyleName("gwt-ButtonTOPSelect");
						}
					});
					btnNewButton.addMouseOverHandler(new MouseOverHandler() {
						public void onMouseOver(MouseOverEvent event) {
							
								((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
							
						}
					});
					btnNewButton.addMouseDownHandler(new MouseDownHandler() {
						public void onMouseDown(MouseDownEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonPush");
						}
					});
					btnNewButton.setStyleName("gwt-ButtonTOP");
				}
				
			if (!result.isEmpty())
			{
				ButtonGroupClient btnNewButton = new ButtonGroupClient(result.get(result.size()-1));
				btnNewButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						
						
						if (ActualSelect!=null)
							{
							ActualSelect.setStyleName(OldStyle);
							}
						ActualSelect=(ButtonGroupClient)event.getSource();
						OldStyle="gwt-ButtonBotton";
						ActualSelect.setStyleName("gwt-ButtonBottonSelect");
						Panel_grupo_seleccionado.clear();
						Panel_grupo_seleccionado.add(new GroupAndUserPanel(ActualSelect.getGroupClient(),Panel_grupo_seleccionado,Yo));
//						bookReaderServiceHolder.loadGroupByName(BotonEvento, new AsyncCallback<GroupApp>() {
//
//							public void onFailure(Throwable caught) {
//								Window.alert("The group could not be loaded");
//								
//							}
//
//							public void onSuccess(GroupApp result) {
//								Panel_grupo_seleccionado.clear();
//								Panel_grupo_seleccionado.add(new GroupAndUserPanel(result,Panel_grupo_seleccionado,Yo));
//							}
//						});
						
						
						
					}
				});
				Panel_de_grupos.add(btnNewButton);
				btnNewButton.setSize("100%", "100%");
				btnNewButton.addMouseOutHandler(new MouseOutHandler() {
					public void onMouseOut(MouseOutEvent event) {
						if (ActualSelect!=(Button)event.getSource()) 
							((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
						else ((Button)event.getSource()).setStyleName("gwt-ButtonBottonSelect");
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
				btnNewButton.setStyleName("gwt-ButtonBotton");
			}
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Groups could not be retrieved, please try again later");
				
			}
		});

		
		
	}

	public void refreshGroups() {
		Panel_de_grupos.clear();
		Panel_grupo_seleccionado.clear();
		
bookReaderServiceHolder.getGroupsByUserId(ActualState.getUser().getId(), new AsyncCallback<List<GroupClient>>() {
	
	public void onSuccess(List<GroupClient> result) {
			
					for (int i = 0; i < result.size()-1; i++){
						ButtonGroupClient btnNewButton = new ButtonGroupClient(result.get(i));
					btnNewButton.addClickHandler(new ClickHandler() {


						public void onClick(ClickEvent event) {
							
							
							if (ActualSelect!=null)
							{
							ActualSelect.setStyleName(OldStyle);
							}
						ActualSelect=(ButtonGroupClient)event.getSource();
						ActualSelect.setStyleName("gwt-ButtonTOPSelect");
						OldStyle="gwt-ButtonTOP";
						Panel_grupo_seleccionado.clear();
						Panel_grupo_seleccionado.add(new GroupAndUserPanel(ActualSelect.getGroupClient(),Panel_grupo_seleccionado,Yo));
						
//						bookReaderServiceHolder.loadGroupByName(BotonEvento, new AsyncCallback<GroupApp>() {
//
//							public void onFailure(Throwable caught) {
//								Window.alert("The group could not be loaded");
//								
//							}
//
//							public void onSuccess(GroupApp result) {
//								Panel_grupo_seleccionado.clear();
//								Panel_grupo_seleccionado.add(new GroupAndUserPanel(result,Panel_grupo_seleccionado,Yo));
//							}
//						});
							
							
							
						}
					});
					Panel_de_grupos.add(btnNewButton);
					btnNewButton.setSize("100%", "100%");
					btnNewButton.addMouseOutHandler(new MouseOutHandler() {
						public void onMouseOut(MouseOutEvent event) {
							if (ActualSelect!=(Button)event.getSource()) 
								((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
							else ((Button)event.getSource()).setStyleName("gwt-ButtonTOPSelect");
						}
					});
					btnNewButton.addMouseOverHandler(new MouseOverHandler() {
						public void onMouseOver(MouseOverEvent event) {
							
								((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
							
						}
					});
					btnNewButton.addMouseDownHandler(new MouseDownHandler() {
						public void onMouseDown(MouseDownEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonPush");
						}
					});
					btnNewButton.setStyleName("gwt-ButtonTOP");
				}
				
			if (!result.isEmpty())
			{
				ButtonGroupClient btnNewButton = new ButtonGroupClient(result.get(result.size()-1));
				btnNewButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						
						
						if (ActualSelect!=null)
						{
						ActualSelect.setStyleName(OldStyle);
						}
					ActualSelect=(ButtonGroupClient)event.getSource();
					OldStyle="gwt-ButtonBotton";
					ActualSelect.setStyleName("gwt-ButtonBottonSelect");
					Panel_grupo_seleccionado.clear();
					Panel_grupo_seleccionado.add(new GroupAndUserPanel(ActualSelect.getGroupClient(),Panel_grupo_seleccionado,Yo));
//					bookReaderServiceHolder.loadGroupByName(BotonEvento, new AsyncCallback<GroupApp>() {
//
//						public void onFailure(Throwable caught) {
//							Window.alert("The group could not be loaded");
//							
//						}
//
//						public void onSuccess(GroupApp result) {
//							Panel_grupo_seleccionado.clear();
//							Panel_grupo_seleccionado.add(new GroupAndUserPanel(result,Panel_grupo_seleccionado,Yo));
//						}
//					});
						
						
						
					}
				});
				Panel_de_grupos.add(btnNewButton);
				btnNewButton.setSize("100%", "100%");
				btnNewButton.addMouseOutHandler(new MouseOutHandler() {
					public void onMouseOut(MouseOutEvent event) {
						if (ActualSelect!=(Button)event.getSource()) 
							((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
						else ((Button)event.getSource()).setStyleName("gwt-ButtonBottonSelect");
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
				btnNewButton.setStyleName("gwt-ButtonBotton");
			}
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Groups could not be retrieved, please try again later");
				
			}
		});
	
	}
	
}
