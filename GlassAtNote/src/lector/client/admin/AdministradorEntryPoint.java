package lector.client.admin;

import lector.client.admin.activity.EditorActivityPopupPanel;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.logger.Logger;
import lector.share.model.Language;
import lector.share.model.client.ReadingActivityClient;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.user.client.ui.DockPanel;

public class AdministradorEntryPoint implements EntryPoint {

	private static final String ADMINISTRATORS_NAME = "Administration Menu";
	
	private static final int NCampos=14;
	
	private static String WELLCOME_MENU = "Welcome to the administrator page : ";
	private static String BIENVENIDA = "null";
	private static String CLOSE_SESSION_MENU = "Close Session";
	private static String CATALOG_BUTTON = "Catalogue";
	private static String INTERFACE_LANGUAGE_BUTTON = "Interface Languages";
	private static String EXPORT_TEMPLATES_BUTTON = "Export Templates";
	private static String ACTIVITY_BUTTON="Activity";
	private static String GROUP_BUTTON = "Group";
	private static String USERS_BUTTON = "Users";
	private static String ADMINISTRATORS_BUTTON = "Administrators";
	private static String BOOK_MANAGMENT_BUTTON = "Book Management";
	private static String MY_LIBRARY_BUTTON = "My Library";
	private static String MY_ACTIVITIES_BUTTON = "My Activities";
	private static String MY_PROFILE_BUTTON = "Edit Profile";
	private static String RETURN_TO_ACTIVITY_BUTTON = "Return to the Activity";

	
	private static final String WELLCOME_MENU_RESET = "Welcome to the administrator page : ";
	private static final String CLOSE_SESSION_MENU_RESET = "Close Session";
	private static final String CATALOG_BUTTON_RESET = "Catalogue";
	private static final String INTERFACE_LANGUAGE_BUTTON_RESET = "Interface Languages";
	private static final String EXPORT_TEMPLATES_BUTTON_RESET = "Export Templates";
	private static final String ACTIVITY_BUTTON_RESET="Activity";
	private static final String GROUP_BUTTON_RESET = "Group";
	private static final String USERS_BUTTON_RESET = "Users";
	private static final String ADMINISTRATORS_BUTTON_RESET = "Administrators";
	private static final String BOOK_MANAGMENT_BUTTON_RESET = "Book Management";
	private static final String MY_LIBRARY_BUTTON_RESET = "My Library";
	private static final String MY_ACTIVITIES_BUTTON_RESET = "My Activities";
	private static final String MY_PROFILE_BUTTON_RESET = "Edit Profile";
	private static final String RETURN_TO_ACTIVITY_BUTTON_RESET = "Return to the Activity";
	
	private MenuItem WellcomeButtonMenu;
	private MenuItem CloseSessionMenu;
	private Button CatalogButton;
	private Button InterfaceLanguageButton;
	private Button ExportTemplatesButton;
	private Button ActvityButton;
	private Button GroupButton;
	private Button UsersButton;
	private Button AdministratorsButton;
	private Button BookManagmentButton;
	private Button MyLibraryButton;
	private Button MyActivitiesButton;
	private Button MyProfileButton;
	private Button ReturnToActivityButton;
	
	private TextBox WellcomeMenuTextBox;
	private TextBox CloseSessionMenuTextBox;
	private TextBox CatalogButtonTextBox;
	private TextBox InterfaceLanguageButtonTextBox;
	private TextBox ExportTemplatesButtonTextBox;
	private TextBox ActvityButtonTextBox;
	private TextBox GroupButtonTextBox;
	private TextBox UsersButtonTextBox;
	private TextBox AdministratorsButtonTextBox;
	private TextBox BookManagmentButtonTextBox;
	private TextBox MyLibraryButtonTextBox;
	private TextBox MyActivitiesButtonTextBox;
	private TextBox MyProfileButtonTextBox;
	private TextBox ReturnToActivityButtonTextBox;
	
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);

	private RootPanel rootPanel;
	private DockPanel PanelFondoGeneral;
	private AbsolutePanel PanelEdicion;
	private AdministradorEntryPoint Yo;


	public void onModuleLoad() {
		rootPanel = RootPanel.get();
		rootPanel.setStyleName("Root");
		Yo=this;
//		if ((ActualState.getUser().getFirstName() != null)
//				&& (!ActualState.getUser().getFirstName().isEmpty()))
//			BIENVENIDA = ActualState.getUser().getFirstName();
//		else
//			BIENVENIDA = ActualState.getUser().getEmail();
		BIENVENIDA=ActualState.getUser().getFirstName()+ " " + ActualState.getUser().getLastName().charAt(0)+".";
		PanelFondoGeneral = new DockPanel();
		PanelFondoGeneral.setStyleName("fondoLogo");
		rootPanel.add(PanelFondoGeneral, 0, 0);
		PanelFondoGeneral.setSize("100%", "100%");

		MenuBar menuBar = new MenuBar(false);
		PanelFondoGeneral.add(menuBar, DockPanel.NORTH);
		menuBar.setSize("100%", "24px");

		WellcomeButtonMenu = new MenuItem(AdministradorEntryPoint.WELLCOME_MENU
				+ BIENVENIDA, false, (Command) null);
		WellcomeButtonMenu.setEnabled(false);
		menuBar.addItem(WellcomeButtonMenu);

		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);

		CloseSessionMenu = new MenuItem(AdministradorEntryPoint.CLOSE_SESSION_MENU, false,
				new Command() {
					public void execute() {
						Controlador.change2Welcome();
						Cookies.removeCookie(Constants.COOKIE_NAME);
						Logger.GetLogger()
						.info(Yo.getClass().toString(),
								ActualState.getUser().toString(),
								"Log out");
						ActualState.setUser(null);
						ActualState.setReadingActivityBook(null);
						ActualState.setReadingactivity(null);
					}
				});
		menuBar.addItem(CloseSessionMenu);

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		PanelFondoGeneral.add(horizontalPanel, DockPanel.CENTER);
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

		CatalogButton = new Button(AdministradorEntryPoint.CATALOG_BUTTON );
		verticalPanel_1.add(CatalogButton);
		CatalogButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		CatalogButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		CatalogButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		CatalogButton.setStyleName("gwt-ButtonTOP");
		CatalogButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2CatalogAdmin();
			}
		});
		CatalogButton.setSize("100%", "100%");

		InterfaceLanguageButton = new Button(AdministradorEntryPoint.INTERFACE_LANGUAGE_BUTTON );
		verticalPanel_1.add(InterfaceLanguageButton);
		InterfaceLanguageButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		InterfaceLanguageButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		InterfaceLanguageButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		InterfaceLanguageButton.setStyleName("gwt-ButtonTOP");
		InterfaceLanguageButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2AdminLenguaje();
			}
		});
		InterfaceLanguageButton.setSize("100%", "100%");

		ExportTemplatesButton = new Button(AdministradorEntryPoint.EXPORT_TEMPLATES_BUTTON);
		verticalPanel_1.add(ExportTemplatesButton);
		ExportTemplatesButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		ExportTemplatesButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		ExportTemplatesButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		ExportTemplatesButton.setStyleName("gwt-ButtonTOP");
		ExportTemplatesButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2AdminTemplate();
			}
		});
		ExportTemplatesButton.setSize("100%", "100%");

		ActvityButton = new Button(AdministradorEntryPoint.ACTIVITY_BUTTON);
		verticalPanel_1.add(ActvityButton);
		ActvityButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		ActvityButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		ActvityButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		ActvityButton.setStyleName("gwt-ButtonTOP");
		ActvityButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2ActivityAdmin();
			}
		});
		ActvityButton.setSize("100%", "100%");

		GroupButton = new Button(AdministradorEntryPoint.GROUP_BUTTON);
		verticalPanel_1.add(GroupButton);
		GroupButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		GroupButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		GroupButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		GroupButton.setStyleName("gwt-ButtonTOP");
		GroupButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2GroupAdministration();
			}
		});
		GroupButton.setSize("100%", "100%");

		UsersButton = new Button(AdministradorEntryPoint.USERS_BUTTON);
		verticalPanel_1.add(UsersButton);
		UsersButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		UsersButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		UsersButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		UsersButton.setStyleName("gwt-ButtonTOP");
		UsersButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2UserAdministration();
			}
		});
		UsersButton.setSize("100%", "100%");

		AdministratorsButton = new Button(AdministradorEntryPoint.ADMINISTRATORS_BUTTON );
		verticalPanel_1.add(AdministratorsButton);
		AdministratorsButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		AdministratorsButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		AdministratorsButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		AdministratorsButton.setStyleName("gwt-ButtonTOP");
		AdministratorsButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2AdminAdministration();
			}
		});
		AdministratorsButton.setSize("100%", "100%");

		BookManagmentButton = new Button(AdministradorEntryPoint.BOOK_MANAGMENT_BUTTON);
		verticalPanel_1.add(BookManagmentButton);
		BookManagmentButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		BookManagmentButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		BookManagmentButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		BookManagmentButton.setStyleName("gwt-ButtonTOP");
		BookManagmentButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2BookAdminstrator();
			}
		});
		BookManagmentButton.setSize("100%", "100%");
		

		MyLibraryButton = new Button(AdministradorEntryPoint.MY_LIBRARY_BUTTON);
		verticalPanel_1.add(MyLibraryButton);
		MyLibraryButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		MyLibraryButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		MyLibraryButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		MyLibraryButton.setStyleName("gwt-ButtonTOP");
		MyLibraryButton.setSize("100%", "100%");

		MyActivitiesButton = new Button(AdministradorEntryPoint.MY_ACTIVITIES_BUTTON);
		verticalPanel_1.add(MyActivitiesButton);
		MyActivitiesButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		MyActivitiesButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		MyActivitiesButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		MyActivitiesButton.setStyleName("gwt-ButtonTOP");
		MyActivitiesButton.setSize("100%", "100%");

		MyProfileButton = new Button(AdministradorEntryPoint.MY_PROFILE_BUTTON);
		verticalPanel_1.add(MyProfileButton);
		MyProfileButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2UserEdition();
			}
		});
		MyProfileButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		MyProfileButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		MyProfileButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		MyProfileButton.setStyleName("gwt-ButtonTOP");
		MyProfileButton.setSize("100%", "100%");

		ReturnToActivityButton = new Button(AdministradorEntryPoint.RETURN_TO_ACTIVITY_BUTTON);
		verticalPanel_1.add(ReturnToActivityButton);
		ReturnToActivityButton.setStyleName("gwt-ButtonBotton");
		ReturnToActivityButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonBotton");
			}
		});
		ReturnToActivityButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonBottonOver");
			}
		});
		ReturnToActivityButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonPushBotton");
			}
		});
		ReturnToActivityButton.setSize("100%", "100%");
		ReturnToActivityButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				bookReaderServiceHolder.loadReadingActivityById(ActualState
						.getReadingactivity().getId(),
						new AsyncCallback<ReadingActivityClient>() {

							public void onSuccess(ReadingActivityClient result) {
								ActualState.setReadingactivity(null);
								if (checkComplete(result)) {
									ActualState.setReadingactivity(result);
									Controlador.change2Reader();

								} else {
									ReturnToActivityButton.setEnabled(false);
									ActualState.setReadingactivity(null);
									Window.alert(InformationConstants.ACTIVITY_CHANGES_AND_ARE_NOW_NOT_ACCESIBLE);
								}
							}

							private boolean checkComplete(
									ReadingActivityClient result) {

								return ((result.getBook() != null)
										&& (result.getCloseCatalogo() != null)
										&& (result.getGroup() != null) && (result
										.getLanguage() != null));
							}

							public void onFailure(Throwable caught) {
								ActualState.setReadingactivity(null);
								ReturnToActivityButton.setEnabled(false);
								Window.alert(ErrorConstants.ERROR_ACTIVITY_DONT_EXIST_OR_ARE_UNREACHEABLE);

							}
						});
			}
		});
		MyActivitiesButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2MyActivities();
			}
		});
		MyLibraryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Controlador.change2MyBooks();
			}
		});

		if (ActualState.getReadingactivity() == null) {
			ReturnToActivityButton.setEnabled(false);
			ReturnToActivityButton.setStyleName("gwt-ButtonBottonSelect");
		} else {
			ReturnToActivityButton.setEnabled(true);
			ReturnToActivityButton.setStyleName("gwt-ButtonBotton");
		}
		
		PanelEdicion=new AbsolutePanel();
		if (ActualState.isLanguageActive())
			closeEditPanel();
	}
	
	public void closeEditPanel()
	{
		rootPanel.remove(PanelEdicion);
		rootPanel.add(PanelEdicion, PanelFondoGeneral.getOffsetWidth()-40, 0);
		PanelEdicion.setSize("40px","50px");
		PanelEdicion.clear();
		PanelEdicion.setStyleName("");
		Button Boton=new Button();
		PanelEdicion.add(Boton,0, 0);
		Boton.setHTML(InformationConstants.EDIT_BOTTON);
		Boton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				OpenEditPanel();
				
			}
		});
		
	}
	
	public void OpenEditPanel() {
		rootPanel.remove(PanelEdicion);
		rootPanel.add(PanelEdicion, 0, 0);
		PanelEdicion.setSize(PanelFondoGeneral.getOffsetWidth()+"px",PanelFondoGeneral.getOffsetHeight()+"px");
		PanelEdicion.clear();
		PanelEdicion.setStyleName("BlancoTransparente");
		Button Boton=new Button();
		PanelEdicion.add(Boton,PanelEdicion.getOffsetWidth()-65, 0);
		Boton.setHTML(InformationConstants.END_EDIT_BOTTON);
		Boton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				closeEditPanel();
				
				if (!WellcomeMenuTextBox.getText().isEmpty())
					WELLCOME_MENU=WellcomeMenuTextBox.getText();
				else WELLCOME_MENU=WELLCOME_MENU_RESET;
				
				if (!CloseSessionMenuTextBox.getText().isEmpty())
					CLOSE_SESSION_MENU=CloseSessionMenuTextBox.getText();
				else CLOSE_SESSION_MENU=CLOSE_SESSION_MENU_RESET;
				
				if (!CatalogButtonTextBox.getText().isEmpty())
					CATALOG_BUTTON=CatalogButtonTextBox.getText();
				else CATALOG_BUTTON=CATALOG_BUTTON_RESET;
				
				if (!InterfaceLanguageButtonTextBox.getText().isEmpty())
					INTERFACE_LANGUAGE_BUTTON=InterfaceLanguageButtonTextBox.getText();
				else INTERFACE_LANGUAGE_BUTTON=INTERFACE_LANGUAGE_BUTTON_RESET;
				
				if (!ExportTemplatesButtonTextBox.getText().isEmpty())
					EXPORT_TEMPLATES_BUTTON=ExportTemplatesButtonTextBox.getText();
				else EXPORT_TEMPLATES_BUTTON=EXPORT_TEMPLATES_BUTTON_RESET;
				
				if (!ActvityButtonTextBox.getText().isEmpty())
					ACTIVITY_BUTTON=ActvityButtonTextBox.getText();
				else ACTIVITY_BUTTON=ACTIVITY_BUTTON_RESET;
				
				if (!GroupButtonTextBox.getText().isEmpty())
					GROUP_BUTTON=GroupButtonTextBox.getText();
				else GROUP_BUTTON=GROUP_BUTTON_RESET;
				
				if (!UsersButtonTextBox.getText().isEmpty())
					USERS_BUTTON=UsersButtonTextBox.getText();
				else USERS_BUTTON=USERS_BUTTON_RESET;
				
				if (!AdministratorsButtonTextBox.getText().isEmpty())
					ADMINISTRATORS_BUTTON=AdministratorsButtonTextBox.getText();
				else ADMINISTRATORS_BUTTON=ADMINISTRATORS_BUTTON_RESET;
				
				if (!BookManagmentButtonTextBox.getText().isEmpty())
					BOOK_MANAGMENT_BUTTON=BookManagmentButtonTextBox.getText();
				else BOOK_MANAGMENT_BUTTON=BOOK_MANAGMENT_BUTTON_RESET;
				
				if (!MyLibraryButtonTextBox.getText().isEmpty())
					MY_LIBRARY_BUTTON=MyLibraryButtonTextBox.getText();
				else MY_LIBRARY_BUTTON=MY_LIBRARY_BUTTON_RESET;
				
				if (!MyActivitiesButtonTextBox.getText().isEmpty())
					MY_ACTIVITIES_BUTTON=MyActivitiesButtonTextBox.getText();
				else MY_ACTIVITIES_BUTTON=MY_ACTIVITIES_BUTTON_RESET;
				
				if (!ReturnToActivityButtonTextBox.getText().isEmpty())
					RETURN_TO_ACTIVITY_BUTTON=ReturnToActivityButtonTextBox.getText();
				else RETURN_TO_ACTIVITY_BUTTON=RETURN_TO_ACTIVITY_BUTTON_RESET;
				
				if (!MyProfileButtonTextBox.getText().isEmpty())
					MY_PROFILE_BUTTON=MyProfileButtonTextBox.getText();
				else MY_PROFILE_BUTTON=MY_PROFILE_BUTTON_RESET;
				
				ParsearFieldsAItems();
				SaveChages();
			}
		});
		WellcomeMenuTextBox=new TextBox();
		WellcomeMenuTextBox.setText(WELLCOME_MENU);
		WellcomeMenuTextBox.setSize(WellcomeButtonMenu.getOffsetWidth()+"px", WellcomeButtonMenu.getOffsetHeight()+"px");
		PanelEdicion.add(WellcomeMenuTextBox, WellcomeButtonMenu.getAbsoluteLeft(), WellcomeButtonMenu.getAbsoluteTop());
		
		CloseSessionMenuTextBox=new TextBox();
		CloseSessionMenuTextBox.setText(CLOSE_SESSION_MENU);
		CloseSessionMenuTextBox.setSize(CloseSessionMenu.getOffsetWidth()+"px", CloseSessionMenu.getOffsetHeight()+"px");
		PanelEdicion.add(CloseSessionMenuTextBox, CloseSessionMenu.getAbsoluteLeft(), CloseSessionMenu.getAbsoluteTop());
		
		CatalogButtonTextBox=new TextBox();
		CatalogButtonTextBox.setText(CATALOG_BUTTON);
		CatalogButtonTextBox.setSize(CatalogButton.getOffsetWidth()+"px", CatalogButton.getOffsetHeight()+"px");
		PanelEdicion.add(CatalogButtonTextBox, CatalogButton.getAbsoluteLeft(), CatalogButton.getAbsoluteTop());
		
		InterfaceLanguageButtonTextBox=new TextBox();
		InterfaceLanguageButtonTextBox.setText(INTERFACE_LANGUAGE_BUTTON);
		InterfaceLanguageButtonTextBox.setSize(InterfaceLanguageButton.getOffsetWidth()+"px", InterfaceLanguageButton.getOffsetHeight()+"px");
		PanelEdicion.add(InterfaceLanguageButtonTextBox, InterfaceLanguageButton.getAbsoluteLeft(), InterfaceLanguageButton.getAbsoluteTop());
		
		ExportTemplatesButtonTextBox=new TextBox();
		ExportTemplatesButtonTextBox.setText(EXPORT_TEMPLATES_BUTTON);
		ExportTemplatesButtonTextBox.setSize(ExportTemplatesButton.getOffsetWidth()+"px", ExportTemplatesButton.getOffsetHeight()+"px");
		PanelEdicion.add(ExportTemplatesButtonTextBox, ExportTemplatesButton.getAbsoluteLeft(), ExportTemplatesButton.getAbsoluteTop());
		
		ActvityButtonTextBox=new TextBox();
		ActvityButtonTextBox.setText(ACTIVITY_BUTTON);
		ActvityButtonTextBox.setSize(ActvityButton.getOffsetWidth()+"px", ActvityButton.getOffsetHeight()+"px");
		PanelEdicion.add(ActvityButtonTextBox, ActvityButton.getAbsoluteLeft(), ActvityButton.getAbsoluteTop());
		
		GroupButtonTextBox=new TextBox();
		GroupButtonTextBox.setText(GROUP_BUTTON);
		GroupButtonTextBox.setSize(GroupButton.getOffsetWidth()+"px", GroupButton.getOffsetHeight()+"px");
		PanelEdicion.add(GroupButtonTextBox, GroupButton.getAbsoluteLeft(), GroupButton.getAbsoluteTop());
		
		UsersButtonTextBox=new TextBox();
		UsersButtonTextBox.setText(USERS_BUTTON);
		UsersButtonTextBox.setSize(UsersButton.getOffsetWidth()+"px", UsersButton.getOffsetHeight()+"px");
		PanelEdicion.add(UsersButtonTextBox, UsersButton.getAbsoluteLeft(), UsersButton.getAbsoluteTop());
		
		AdministratorsButtonTextBox=new TextBox();
		AdministratorsButtonTextBox.setText(ADMINISTRATORS_BUTTON);
		AdministratorsButtonTextBox.setSize(AdministratorsButton.getOffsetWidth()+"px", AdministratorsButton.getOffsetHeight()+"px");
		PanelEdicion.add(AdministratorsButtonTextBox, AdministratorsButton.getAbsoluteLeft(), AdministratorsButton.getAbsoluteTop());
		
		BookManagmentButtonTextBox=new TextBox();
		BookManagmentButtonTextBox.setText(BOOK_MANAGMENT_BUTTON);
		BookManagmentButtonTextBox.setSize(BookManagmentButton.getOffsetWidth()+"px", BookManagmentButton.getOffsetHeight()+"px");
		PanelEdicion.add(BookManagmentButtonTextBox, BookManagmentButton.getAbsoluteLeft(), BookManagmentButton.getAbsoluteTop());
		
		MyLibraryButtonTextBox=new TextBox();
		MyLibraryButtonTextBox.setText(MY_LIBRARY_BUTTON);
		MyLibraryButtonTextBox.setSize(MyLibraryButton.getOffsetWidth()+"px", MyLibraryButton.getOffsetHeight()+"px");
		PanelEdicion.add(MyLibraryButtonTextBox, MyLibraryButton.getAbsoluteLeft(), MyLibraryButton.getAbsoluteTop());
		
		MyActivitiesButtonTextBox=new TextBox();
		MyActivitiesButtonTextBox.setText(MY_ACTIVITIES_BUTTON);
		MyActivitiesButtonTextBox.setSize(MyActivitiesButton.getOffsetWidth()+"px", MyActivitiesButton.getOffsetHeight()+"px");
		PanelEdicion.add(MyActivitiesButtonTextBox, MyActivitiesButton.getAbsoluteLeft(), MyActivitiesButton.getAbsoluteTop());
		
		MyProfileButtonTextBox=new TextBox();
		MyProfileButtonTextBox.setText(MY_PROFILE_BUTTON);
		MyProfileButtonTextBox.setSize(MyProfileButton.getOffsetWidth()+"px", MyProfileButton.getOffsetHeight()+"px");
		PanelEdicion.add(MyProfileButtonTextBox, MyProfileButton.getAbsoluteLeft(), MyProfileButton.getAbsoluteTop());
		
		ReturnToActivityButtonTextBox=new TextBox();
		ReturnToActivityButtonTextBox.setText(RETURN_TO_ACTIVITY_BUTTON);
		ReturnToActivityButtonTextBox.setSize(ReturnToActivityButton.getOffsetWidth()+"px", ReturnToActivityButton.getOffsetHeight()+"px");
		PanelEdicion.add(ReturnToActivityButtonTextBox, ReturnToActivityButton.getAbsoluteLeft(), ReturnToActivityButton.getAbsoluteTop());
		
	}
	
	protected void SaveChages() {
		Language LanguageActual = ActualState.getActualLanguage();
			String AdministracionLanguageConfiguration=toFile();
		LanguageActual.setAdministracionLanguageConfiguration(AdministracionLanguageConfiguration);
		ActualState.saveLanguageActual(LanguageActual);
	}

	public void ParsearFieldsAItems()
	{
	WellcomeButtonMenu.setHTML(WELLCOME_MENU + BIENVENIDA);	
	CloseSessionMenu.setHTML(CLOSE_SESSION_MENU);
	CatalogButton.setHTML(CATALOG_BUTTON);
	InterfaceLanguageButton.setHTML(INTERFACE_LANGUAGE_BUTTON);
	ExportTemplatesButton.setHTML(EXPORT_TEMPLATES_BUTTON);
	ActvityButton.setHTML(ACTIVITY_BUTTON);
	GroupButton.setHTML(GROUP_BUTTON);
	UsersButton.setHTML(USERS_BUTTON);
	AdministratorsButton.setHTML(ADMINISTRATORS_BUTTON);
	BookManagmentButton.setHTML(BOOK_MANAGMENT_BUTTON);
	MyLibraryButton.setHTML(MY_LIBRARY_BUTTON);
	MyActivitiesButton.setHTML(MY_ACTIVITIES_BUTTON);
	MyProfileButton.setHTML(MY_PROFILE_BUTTON);
	ReturnToActivityButton.setHTML(RETURN_TO_ACTIVITY_BUTTON);
	
	
	}
	
	public static String toFile() {
		StringBuffer SB=new StringBuffer();
		SB.append(WELLCOME_MENU+'\n');
		SB.append(CLOSE_SESSION_MENU+'\n');
		SB.append(CATALOG_BUTTON+'\n');
		SB.append(INTERFACE_LANGUAGE_BUTTON+'\n');
		SB.append(EXPORT_TEMPLATES_BUTTON+'\n');
		SB.append(ACTIVITY_BUTTON+'\n');
		SB.append(GROUP_BUTTON+'\n');
		SB.append(USERS_BUTTON+'\n');
		SB.append(ADMINISTRATORS_BUTTON+'\n');
		SB.append(BOOK_MANAGMENT_BUTTON+'\n');
		SB.append(MY_LIBRARY_BUTTON+'\n');
		SB.append(MY_ACTIVITIES_BUTTON+'\n');
		SB.append(MY_PROFILE_BUTTON+'\n');
		SB.append(RETURN_TO_ACTIVITY_BUTTON+'\n');
		return SB.toString();
	}
	
	public static void FromFile(String Entrada) {
		String[] Lista = Entrada.split("\n");
		if (Lista.length >= NCampos) {
			if (!Lista[0].isEmpty())
				WELLCOME_MENU = Lista[0];
			else WELLCOME_MENU=WELLCOME_MENU_RESET;
			if (!Lista[1].isEmpty())
				CLOSE_SESSION_MENU = Lista[1];
			else WELLCOME_MENU=WELLCOME_MENU_RESET;
			if (!Lista[2].isEmpty())
				CATALOG_BUTTON = Lista[2];
			else CLOSE_SESSION_MENU=CLOSE_SESSION_MENU_RESET;
			if (!Lista[3].isEmpty())
				INTERFACE_LANGUAGE_BUTTON = Lista[3];
			else INTERFACE_LANGUAGE_BUTTON=INTERFACE_LANGUAGE_BUTTON_RESET;
			if (!Lista[4].isEmpty())
				EXPORT_TEMPLATES_BUTTON = Lista[4];
			else EXPORT_TEMPLATES_BUTTON=EXPORT_TEMPLATES_BUTTON_RESET;
			if (!Lista[5].isEmpty())
				ACTIVITY_BUTTON = Lista[5];
			else ACTIVITY_BUTTON=ACTIVITY_BUTTON_RESET;
			if (!Lista[6].isEmpty())
				GROUP_BUTTON = Lista[6];
			else GROUP_BUTTON=GROUP_BUTTON_RESET;
			if (!Lista[7].isEmpty())
				USERS_BUTTON = Lista[7];
			else USERS_BUTTON=USERS_BUTTON_RESET;
			if (!Lista[8].isEmpty())
				ADMINISTRATORS_BUTTON = Lista[8];
			else ADMINISTRATORS_BUTTON=ADMINISTRATORS_BUTTON_RESET;
			if (!Lista[9].isEmpty())
				BOOK_MANAGMENT_BUTTON = Lista[9];
			else BOOK_MANAGMENT_BUTTON=BOOK_MANAGMENT_BUTTON_RESET;
			if (!Lista[10].isEmpty())
				MY_LIBRARY_BUTTON = Lista[10];
			else MY_LIBRARY_BUTTON=MY_LIBRARY_BUTTON_RESET;
			if (!Lista[11].isEmpty())
				MY_ACTIVITIES_BUTTON = Lista[11];
			else MY_ACTIVITIES_BUTTON=MY_ACTIVITIES_BUTTON_RESET;
			if (!Lista[12].isEmpty())
				MY_PROFILE_BUTTON = Lista[12];
			else MY_PROFILE_BUTTON=MY_PROFILE_BUTTON_RESET;
			if (!Lista[13].isEmpty())
				RETURN_TO_ACTIVITY_BUTTON = Lista[13];
			else RETURN_TO_ACTIVITY_BUTTON=RETURN_TO_ACTIVITY_BUTTON_RESET;
		}
		else 
			Logger.GetLogger().severe(EditorActivityPopupPanel.class.toString(), ActualState.getUser().toString(), ErrorConstants.ERROR_LOADING_LANGUAGE_IN  + ADMINISTRATORS_NAME);
			
			
		
	}
}
