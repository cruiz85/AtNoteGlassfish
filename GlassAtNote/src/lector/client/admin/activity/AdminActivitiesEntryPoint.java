package lector.client.admin.activity;

import java.util.List;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.controler.ConstantsError;
import lector.client.controler.ConstantsInformation;
import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;
import lector.share.model.Language;
import lector.share.model.client.ReadingActivityClient;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class AdminActivitiesEntryPoint implements EntryPoint {

	private static final String ACTIVITY_ADMINISTRATION_NAME = "Activity Administration Menu";
	
	private static final int NCampos=3;

	private static String ACTIVITY_MENU = "Activity Administration";
	private static String NEW_MENU = "New";
	private static String BACK_MENU = "Back";

	private static final String ACTIVITY_MENU_RESET = "Activity Administration";
	private static final String NEW_MENU_RESET = "New";
	private static final String BACK_MENU_RESET = "Back";
	
	private MenuItem NewActivityMenuItem;
	private MenuItem BackMenuItem;
	private MenuItem ActivityWellcomeMenuItem;
	
	private TextBox NewActivityMenuItemTextBox;
	private TextBox BackMenuItemTextBox;
	private TextBox ActivityWellcomeMenuItemTextBox;
	
	
	private AbsolutePanel PanelEdicion;
	private VerticalPanel PanelBotones;
	
	private VerticalPanel Actual;
	private AdminActivitiesEntryPoint yo;
	private GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private RootPanel rootPanel;

	private DockLayoutPanel PanelFondoGeneral;

	
	public void onModuleLoad() {
		rootPanel = RootPanel.get();
		rootPanel.setSize("100%", "100%");
		rootPanel.setStyleName("Root");

		yo = this;

		PanelFondoGeneral = new DockLayoutPanel(Unit.PX);
		PanelFondoGeneral.setStyleName("fondoLogo");
		rootPanel.add(PanelFondoGeneral, 0, 0);
		PanelFondoGeneral.setSize("100%", "100%");
		
		MenuBar menuBar = new MenuBar(false);
		PanelFondoGeneral.addNorth(menuBar, 25);
		//menuBar.setWidth("100%");

		ActivityWellcomeMenuItem = new MenuItem(ACTIVITY_MENU, false, (Command) null);
		ActivityWellcomeMenuItem.setHTML(ACTIVITY_MENU);
		ActivityWellcomeMenuItem.setEnabled(false);
		menuBar.addItem(ActivityWellcomeMenuItem);

		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);

		NewActivityMenuItem = new MenuItem(NEW_MENU, false, new Command() {
			public void execute() {
				NewActivityPopupPanel NL = new NewActivityPopupPanel(yo);
				NL.center();

			}
		});
		NewActivityMenuItem.setHTML(NEW_MENU);
		menuBar.addItem(NewActivityMenuItem);

		MenuItemSeparator separator_2 = new MenuItemSeparator();
		menuBar.addSeparator(separator_2);

		BackMenuItem = new MenuItem(BACK_MENU, false, new Command() {
			public void execute() {
				Controlador.change2Administrator();
			}
		});
		menuBar.addItem(BackMenuItem);

		PanelBotones = new VerticalPanel();
		PanelBotones.setSpacing(10);
		//PanelBotones.setStyleName("fondoLogo");
//		PanelBotones
//				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	//	rootPanel.add(PanelBotones, 0, 27);
		HorizontalPanel SP= new HorizontalPanel();
		SP.add(PanelBotones);
		SP.setHeight(Constants.P100);
		PanelFondoGeneral.add(SP);
		PanelBotones.setSize("100%", "100%");

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(6);
		PanelBotones.add(horizontalPanel);
		horizontalPanel.setWidth("100%");

		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setSpacing(10);
		horizontalPanel_1.setStyleName("AzulTransparente");
		horizontalPanel.add(horizontalPanel_1);

		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2.setStyleName("BlancoTransparente");
		horizontalPanel_2.setSpacing(6);
		horizontalPanel_1.add(horizontalPanel_2);

		Actual = new VerticalPanel();
		horizontalPanel_2.add(Actual);
		Actual.setWidth("400px");
		refresh();
		PanelEdicion=new AbsolutePanel();
		if (ActualState.isLanguageActive())
			closeEditPanel();

	}

	public void refresh() {
		Actual.clear();
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto(ConstantsInformation.LOADING);
		bookReaderServiceHolder.getReadingActivitiesByProfessorId(ActualState
				.getUser().getId(),
				new AsyncCallback<List<ReadingActivityClient>>() {

					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert(ConstantsError.ERROR_RETRIVING_ACTIVITIES);

					}

					public void onSuccess(List<ReadingActivityClient> result) {
						LoadingPanel.getInstance().hide();
						List<ReadingActivityClient> ActivityMostrar = result;
						for (int i = 0; i < ActivityMostrar.size() - 1; i++) {

							ActivityBotton nue = new ActivityBotton(Actual,
									new VerticalPanel(), ActivityMostrar.get(i));
							nue.setSize("100%", "100%");
							nue.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonPush");
								}
							});
							nue.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonTOP");
								}
							});
							nue.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonTOPOver");
								}
							});
							nue.setStyleName("gwt-ButtonTOP");
							nue.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									SeleccionMenuActivityPopupPanel panel = new SeleccionMenuActivityPopupPanel(
											(ActivityBotton) event.getSource(),
											yo);
									panel.showRelativeTo((ActivityBotton) event
											.getSource());
								}
							});

						}
						if (!ActivityMostrar.isEmpty()) {

							ActivityBotton nue = new ActivityBotton(Actual,
									new VerticalPanel(), ActivityMostrar
											.get(ActivityMostrar.size() - 1));
							nue.setSize("100%", "100%");
							nue.setStyleName("gwt-ButtonBotton");
							nue.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonBotton");
								}
							});
							nue.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonBottonOver");
								}
							});
							nue.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonPushBotton");
								}
							});
							nue.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									SeleccionMenuActivityPopupPanel panel = new SeleccionMenuActivityPopupPanel(
											(ActivityBotton) event.getSource(),
											yo);
									panel.showRelativeTo((ActivityBotton) event
											.getSource());
								}
							});

						}

					}
				});

	}
	
	public void closeEditPanel()
	{
		rootPanel.remove(PanelEdicion);
		rootPanel.add(PanelEdicion, PanelFondoGeneral.getOffsetWidth()-Constants.TAMANOBOTOBEDITOFF, 0);
		PanelEdicion.setSize(Constants.TAMANOBOTOBEDITOFF+Constants.PX,"50px");
		PanelEdicion.clear();
		PanelEdicion.setStyleName("");
		Button Boton=new Button();
		PanelEdicion.add(Boton,0, 0);
		Boton.setHTML(ConstantsInformation.EDIT_BOTTON);
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
		PanelEdicion.add(Boton,PanelEdicion.getOffsetWidth()-Constants.TAMANOBOTOBEDITON, 0);
		Boton.setHTML(ConstantsInformation.END_EDIT_BOTTON);
		Boton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				closeEditPanel();
				
				if (!ActivityWellcomeMenuItemTextBox.getText().isEmpty())
					ACTIVITY_MENU=ActivityWellcomeMenuItemTextBox.getText();
				else ACTIVITY_MENU=ACTIVITY_MENU_RESET;
				
				if (!NewActivityMenuItemTextBox.getText().isEmpty())
					NEW_MENU=NewActivityMenuItemTextBox.getText();
				else NEW_MENU=NEW_MENU_RESET;
				
				if (!BackMenuItemTextBox.getText().isEmpty())
					BACK_MENU=BackMenuItemTextBox.getText();
				else BACK_MENU=BACK_MENU_RESET;
				
			
				ParsearFieldsAItems();
				SaveChages();
			}
		});
		
		ActivityWellcomeMenuItemTextBox=new TextBox();
		ActivityWellcomeMenuItemTextBox.setText(ACTIVITY_MENU);
		ActivityWellcomeMenuItemTextBox.setSize(ActivityWellcomeMenuItem.getOffsetWidth()+"px", ActivityWellcomeMenuItem.getOffsetHeight()+"px");
		PanelEdicion.add(ActivityWellcomeMenuItemTextBox, ActivityWellcomeMenuItem.getAbsoluteLeft(), ActivityWellcomeMenuItem.getAbsoluteTop());
		
		NewActivityMenuItemTextBox=new TextBox();
		NewActivityMenuItemTextBox.setText(NEW_MENU);
		NewActivityMenuItemTextBox.setSize(NewActivityMenuItem.getOffsetWidth()+"px", NewActivityMenuItem.getOffsetHeight()+"px");
		PanelEdicion.add(NewActivityMenuItemTextBox, NewActivityMenuItem.getAbsoluteLeft(), NewActivityMenuItem.getAbsoluteTop());
		
		BackMenuItemTextBox=new TextBox();
		BackMenuItemTextBox.setText(BACK_MENU);
		BackMenuItemTextBox.setSize(BackMenuItem.getOffsetWidth()+"px", BackMenuItem.getOffsetHeight()+"px");
		PanelEdicion.add(BackMenuItemTextBox, BackMenuItem.getAbsoluteLeft(), BackMenuItem.getAbsoluteTop());
		
		
		
		
		
	}
	
	protected void SaveChages() {
		Language LanguageActual = ActualState.getActualLanguage();
			String AdminActivitiesEntryPointLanguageConfiguration=toFile();
		LanguageActual.setAdminActivitiesEntryPointLanguageConfiguration(AdminActivitiesEntryPointLanguageConfiguration);
		ActualState.saveLanguageActual(LanguageActual);
	}

	public void ParsearFieldsAItems()
	{
		ActivityWellcomeMenuItem.setHTML(ACTIVITY_MENU);
	NewActivityMenuItem.setHTML(NEW_MENU);	
	BackMenuItem.setHTML(BACK_MENU);

	
	
	
	}
	
	public static String toFile() {
		StringBuffer SB=new StringBuffer();
		SB.append(ACTIVITY_MENU+"\r\n");
		SB.append(NEW_MENU+"\r\n");
		SB.append(BACK_MENU+"\r\n");
		return SB.toString();
	}
	
	public static void FromFile(String Entrada) {
		if (Entrada.length()==0) 
			ParsearFieldsAItemsRESET();
		else
		{
		String[] Lista = Entrada.split("\r\n");
		if (Lista.length >= NCampos) {
			if (!Lista[0].isEmpty())
				ACTIVITY_MENU = Lista[0];
			else ACTIVITY_MENU=ACTIVITY_MENU_RESET;
			if (!Lista[1].isEmpty())
				NEW_MENU = Lista[1];
			else NEW_MENU=NEW_MENU_RESET;
			if (!Lista[2].isEmpty())
				BACK_MENU = Lista[2];
			else BACK_MENU=BACK_MENU_RESET;
		}
		else 
			Logger.GetLogger().severe(AdminActivitiesEntryPoint.class.toString(), ActualState.getUser().toString(), ConstantsError.ERROR_LOADING_LANGUAGE_IN  + ACTIVITY_ADMINISTRATION_NAME);
		ParsearFieldsAItemsRESET();
			
		}
	}

	private static void ParsearFieldsAItemsRESET() {
		ACTIVITY_MENU=ACTIVITY_MENU_RESET;
		NEW_MENU=NEW_MENU_RESET;
		BACK_MENU=BACK_MENU_RESET;
	}

}
