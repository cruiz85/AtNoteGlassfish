package lector.client.admin;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Controlador;
import lector.client.login.ActualUser;
import lector.share.model.ReadingActivity;
import lector.share.model.client.ReadingActivityClient;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockPanel;

public class Administrador implements EntryPoint {

	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private Button btnNewButton_4;
	private Button MyProfile;
	
	
	public void onModuleLoad() {
		RootPanel RootTXOriginal = RootPanel.get();
	//	RootTXOriginal.setSize("100%", "100%");
			RootTXOriginal.setStyleName("Root");
		
		String Bienvenida;
		if ((ActualUser.getUser().getFirstName()!=null)&&(!ActualUser.getUser().getFirstName().isEmpty()))
		Bienvenida= ActualUser.getUser().getFirstName();
		else 
		Bienvenida=ActualUser.getUser().getEmail();
						
						DockPanel dockPanel = new DockPanel();
						dockPanel.setStyleName("fondoLogo");
						RootTXOriginal.add(dockPanel,0,0);
						dockPanel.setSize("100%", "100%");
						
						MenuBar menuBar = new MenuBar(false);
						dockPanel.add(menuBar, DockPanel.NORTH);
						menuBar.setSize("100%", "24px");
						
						MenuItem menuItem = new MenuItem("Welcome to the administrator page : " + Bienvenida , false, (Command) null);
						menuItem.setEnabled(false);
						menuBar.addItem(menuItem);
						
						MenuItemSeparator separator = new MenuItemSeparator();
						menuBar.addSeparator(separator);
						
						MenuItem mntmNewItem = new MenuItem("Close Session", false, new Command() {
							public void execute() {
								Controlador.change2Welcome();
//				Window.open(ActualUser.getUser().getLogoutUrl(), "_self", "");
								ActualUser.setUser(null);
								ActualUser.setBook(null);
								ActualUser.setReadingactivity(null);
							}
						});
						menuBar.addItem(mntmNewItem);
						
						HorizontalPanel horizontalPanel = new HorizontalPanel();
						dockPanel.add(horizontalPanel, DockPanel.CENTER);
						horizontalPanel.setSpacing(12);
						horizontalPanel.setSize("100%", "100%");
						
						VerticalPanel verticalPanel = new VerticalPanel();
						horizontalPanel.add(verticalPanel);
						verticalPanel.setSpacing(20);
						verticalPanel.setSize("460px", "");
						
							
							HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
							horizontalPanel_2.setStyleName("AzulTransparente");
							horizontalPanel_2.setSpacing(10);
							verticalPanel.add(horizontalPanel_2);
							horizontalPanel_2.setWidth("100%");
							
							HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
							horizontalPanel_1.setStyleName("BlancoTransparente");
							horizontalPanel_1.setSpacing(6);
							horizontalPanel_2.add(horizontalPanel_1);
							horizontalPanel_1.setSize("100%", "100%");
							
							VerticalPanel verticalPanel_1 = new VerticalPanel();
							horizontalPanel_1.add(verticalPanel_1);
							verticalPanel_1.setWidth("100%");
							
							Button btnNewButton = new Button("Catalogue");
							verticalPanel_1.add(btnNewButton);
							btnNewButton.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPush");
								}
							});
							btnNewButton.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
								}
							});
							btnNewButton.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
								}
							});
							btnNewButton.setStyleName("gwt-ButtonTOP");
							btnNewButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									Controlador.change2CatalogAdmin();
								}
							});
							btnNewButton.setSize("100%", "100%");
							
							
							
							Button btnLanguagesAdministration = new Button("Interface Languages");
							verticalPanel_1.add(btnLanguagesAdministration);
							btnLanguagesAdministration.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
								}
							});
							btnLanguagesAdministration.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
								}
							});
							btnLanguagesAdministration.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPush");
								}
							});
							btnLanguagesAdministration.setStyleName("gwt-ButtonTOP");
							btnLanguagesAdministration.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									Controlador.change2AdminLenguaje();
								}
							});
							btnLanguagesAdministration.setSize("100%", "100%");
							
							Button btnTemplatesAdministration = new Button("Export Templates");
							verticalPanel_1.add(btnTemplatesAdministration);
							btnTemplatesAdministration.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
								}
							});
							btnTemplatesAdministration.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
								}
							});
							btnTemplatesAdministration.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPush");
								}
							});
							btnTemplatesAdministration.setStyleName("gwt-ButtonTOP");
							btnTemplatesAdministration.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									Controlador.change2AdminTemplate();
								}
							});
							btnTemplatesAdministration.setSize("100%", "100%");
							
							
							
							Button btnNewButton_9 = new Button("Activity");
							verticalPanel_1.add(btnNewButton_9);
							btnNewButton_9.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
								}
							});
							btnNewButton_9.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
								}
							});
							btnNewButton_9.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPush");
								}
							});
							btnNewButton_9.setStyleName("gwt-ButtonTOP");
							btnNewButton_9.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									Controlador.change2ActivityAdmin();
								}
							});
							btnNewButton_9.setSize("100%", "100%");
							
							
							Button btnNewButton_1 = new Button("Group");
							verticalPanel_1.add(btnNewButton_1);
							btnNewButton_1.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
								}
							});
							btnNewButton_1.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
								}
							});
							btnNewButton_1.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPush");
								}
							});
							btnNewButton_1.setStyleName("gwt-ButtonTOP");
							btnNewButton_1.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									Controlador.change2GroupAdministration();
								}
							});
							btnNewButton_1.setSize("100%", "100%");
							
							
							
							Button btnNewButton_5 = new Button("Users");
							verticalPanel_1.add(btnNewButton_5);
							btnNewButton_5.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
								}
							});
							btnNewButton_5.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
								}
							});
							btnNewButton_5.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPush");
								}
							});
							btnNewButton_5.setStyleName("gwt-ButtonTOP");
							btnNewButton_5.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									Controlador.change2UserAdministration();
								}
							});
							btnNewButton_5.setSize("100%", "100%");
							
							Button btnNewButton_6 = new Button("Administrators");
							verticalPanel_1.add(btnNewButton_6);
							btnNewButton_6.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
								}
							});
							btnNewButton_6.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
								}
							});
							btnNewButton_6.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPush");
								}
							});
							btnNewButton_6.setStyleName("gwt-ButtonTOP");
							btnNewButton_6.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									Controlador.change2AdminAdministration();
								}
							});
							btnNewButton_6.setSize("100%", "100%");
							
							
							Button btnNewButton_2 = new Button("Get a Book");
							verticalPanel_1.add(btnNewButton_2);
							btnNewButton_2.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
								}
							});
							btnNewButton_2.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
								}
							});
							btnNewButton_2.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPush");
								}
							});
							btnNewButton_2.setStyleName("gwt-ButtonTOP");
							btnNewButton_2.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									Controlador.change2BookAdminstrator();
								}
							});
							btnNewButton_2.setSize("100%", "100%");
							//
							Button LoadABook = new Button("Load a Book");
							verticalPanel_1.add(LoadABook);
							LoadABook.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
								}
							});
							LoadABook.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
								}
							});
							LoadABook.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPush");
								}
							});
							LoadABook.setStyleName("gwt-ButtonTOP");
							LoadABook.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									Controlador.change2LoadABook();
								}
							});
							LoadABook.setSize("100%", "100%");
							//
							
							Button btnNewButton_3 = new Button("My Books");
							verticalPanel_1.add(btnNewButton_3);
							btnNewButton_3.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
								}
							});
							btnNewButton_3.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
								}
							});
							btnNewButton_3.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPush");
								}
							});
							btnNewButton_3.setStyleName("gwt-ButtonTOP");
							btnNewButton_3.setSize("100%", "100%");
							
							Button btnNewButton_10 = new Button("My Activities");
							verticalPanel_1.add(btnNewButton_10);
							btnNewButton_10.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
								}
							});
							btnNewButton_10.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
								}
							});
							btnNewButton_10.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPush");
								}
							});
							btnNewButton_10.setStyleName("gwt-ButtonTOP");
							btnNewButton_10.setSize("100%", "100%");
							
							MyProfile = new Button("Edit Profile");
							verticalPanel_1.add(MyProfile);
							MyProfile.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									Controlador.change2UserEdition();
								}
							});
							MyProfile.setText("My Profile");
							MyProfile.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
								}
							});
							MyProfile.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
								}
							});
							MyProfile.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPush");
								}
							});
							MyProfile.setStyleName("gwt-ButtonTOP");
							MyProfile.setSize("100%", "100%");
							
							
							btnNewButton_4 = new Button("Return to the Activity");
							verticalPanel_1.add(btnNewButton_4);
							btnNewButton_4.setStyleName("gwt-ButtonBotton");
							btnNewButton_4.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
								}
							});
							btnNewButton_4.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
								}
							});
							btnNewButton_4.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
								}
							});
							btnNewButton_4.setSize("100%", "100%");
		btnNewButton_4.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				bookReaderServiceHolder.loadReadingActivityById(ActualUser.getReadingactivity().getId(), new AsyncCallback<ReadingActivityClient>() {
					
					public void onSuccess(ReadingActivityClient result) {
						ActualUser.setReadingactivity(null);
						if (checkComplete(result)){
							ActualUser.setReadingactivity(result);
							Controlador.change2Reader();
							
						}
						else {
							btnNewButton_4.setEnabled(false);
							Window.alert("Some atributes was modificated and the Reading Activity are now inaccessible");
						}
					}
					
					private boolean checkComplete(ReadingActivityClient result) {
						
						return (
								(result.getBook()!=null)&&
								(result.getCloseCatalogo()!=null)&&
								(result.getGroup()!=null)&&
								(result.getLanguage()!=null)
								);
					}

					public void onFailure(Throwable caught) {
						ActualUser.setReadingactivity(null);
						btnNewButton_4.setEnabled(false);
						Window.alert("The acivity don't exist because was removed recently");
						
					}
				});
			}
		});
		btnNewButton_10.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2MyActivities();
			}
		});
		btnNewButton_3.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2MyBooks();
			}
		});
		
		if (ActualUser.getReadingactivity()==null) {
			btnNewButton_4.setEnabled(false);
			btnNewButton_4.setStyleName("gwt-ButtonBottonSelect");
		}
		else {
			btnNewButton_4.setEnabled(true);
			btnNewButton_4.setStyleName("gwt-ButtonBotton");
		}
	}
}
