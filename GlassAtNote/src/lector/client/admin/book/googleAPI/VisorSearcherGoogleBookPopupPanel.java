package lector.client.admin.book.googleAPI;


import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;
import lector.share.model.Language;
import lector.share.model.client.GoogleBookClient;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.UserClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.TextBox;

public class VisorSearcherGoogleBookPopupPanel extends PopupPanel {

	private static final String VISORGOOGLESEARCH = "Visor Google Search Popup";
	
	private static final int NCampos=2;
	
	private static final int DecoradorWidth = 2;
	
	private AbsolutePanel GeneralPanel;

	private SimplePanel EditorZone;
	
	private static String CLOSE_MENU_ITEM="Close";
	private static String ADD_TO_MY_BOOKS_MENUITEM= "Add to My Books";
	
	private static final String CLOSE_MENU_ITEM_RESET="Close";
	private static final String ADD_TO_MY_BOOKS_MENUITEM_RESET= "Add to My Books";
	
	private MenuItem AddToMyBooksMenuItem;
	private MenuItem CloseMenuItem;
	
	private TextBox AddToMyBooksMenuItemTextBox;
	private TextBox CloseMenuItemTextBox;
	
	public String BookId="";
	public VisorSearcherGoogleBookPopupPanel Yo;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	public GoogleBookClient Entrada;

	private AbsolutePanel PanelEdicion;

	private SimplePanel flowPanel;
	
	
	
	
	private static final String SPLIT="&";

	private static final String WINDOW_WIDTH = "846px";

	private static final String WINDOW_HEIGHT = "608px";
	
	public VisorSearcherGoogleBookPopupPanel(GoogleBookClient entrada) {
		super(false);
		setAnimationEnabled(true);
		GeneralPanel=new AbsolutePanel();
		GeneralPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setWidget(GeneralPanel);
		BookId=entrada.getWebLinks().get(0);
		entrada.setImagesPath(BookId);
		Yo=this;
		Entrada=entrada;
		String[] Booksplit=BookId.split(SPLIT);
		BookId=Booksplit[0];
		setGlassEnabled(true);
		flowPanel = new SimplePanel();
		//setWidget(simplePanel);
		GeneralPanel.add(flowPanel,0,0);
		//simplePanel.setSize("100%", "100%");
		flowPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		FlowPanel verticalPanel = new FlowPanel();
		flowPanel.setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		EditorZone=new SimplePanel();
		EditorZone.setHeight(Constants.TAMANO_PANEL_EDICION);
		verticalPanel.add(EditorZone);
		
		
		MenuBar menuBar = new MenuBar(false);
		verticalPanel.add(menuBar);
		
		CloseMenuItem  = new MenuItem(CLOSE_MENU_ITEM, false, new Command() {
			public void execute() {
			Yo.hide();
			}
		});
		menuBar.addItem(CloseMenuItem);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		
		AddToMyBooksMenuItem = new MenuItem(ADD_TO_MY_BOOKS_MENUITEM, false, new Command() {
			public void execute() {
				
				//Window.alert("Works");
				ProfessorClient PC=(ProfessorClient) ActualState.getUser();
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto(InformationConstants.SAVING);
				bookReaderServiceHolder.addBookToUser(Entrada,PC.getId(), new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						realoadUser();
						
						Yo.hide();
						Logger.GetLogger()
						.info(Yo.getClass().toString(),
								ActualState.getUser().toString(),
								InformationConstants.ADDED_BOOK1+Entrada.getTitle()+InformationConstants.ADDED_BOOK1);
						
					}
					
					private void realoadUser() {
						
						bookReaderServiceHolder.loadUserById(ActualState.getUser().getId(), new AsyncCallback<UserClient>() {
							
							@Override
							public void onSuccess(UserClient result) {
								LoadingPanel.getInstance().hide();
								ActualState.setUser(result);
								Controlador.change2BookAdminstrator();

								
							}
							
							@Override
							public void onFailure(Throwable caught) {
								LoadingPanel.getInstance().hide();
								Window.alert(ErrorConstants.ERROR_RELOADING_USER_REFRESH);
								Logger.GetLogger()
								.severe(Yo.getClass().toString(),
										ActualState.getUser().toString(),
										ErrorConstants.ERROR_RELOADING_USER_REFRESH);
								Window.Location.reload();
								
							}
						});
						
					}

					@Override
					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert(ErrorConstants.ERROR_SAVING_BOOK1+Entrada.getTitle()+ErrorConstants.ERROR_SAVING_BOOK2);
						Logger.GetLogger()
						.severe(Yo.getClass().toString(),
								ActualState.getUser().toString(),
								ErrorConstants.ERROR_SAVING_BOOK1+Entrada.getTitle()+ErrorConstants.ERROR_SAVING_BOOK2);
					}
				});
			}
		});
		menuBar.addItem(AddToMyBooksMenuItem);
		
		String Direccion=BookId +"&printsec=frontcover&output=embed";
		
		TextBox textBox = new TextBox();
		textBox.setVisibleLength(180);
		textBox.setReadOnly(true);
		textBox.setText(Direccion);
		verticalPanel.add(textBox);
		textBox.setWidth("839px");
		Frame frame = new Frame(Direccion);
		verticalPanel.add(frame);
		frame.setSize("842px", "556px");
		PanelEdicion=new AbsolutePanel();
		
	}

	@Override
	public void center() {
		super.center();
		EditorZone.setVisible(false);
		if (ActualState.isLanguageActive())
			{
			EditorZone.setVisible(true);
			closeEditPanel();
			}
	}

	private void closeEditPanel() {
		GeneralPanel.remove(PanelEdicion);
		GeneralPanel.add(PanelEdicion,flowPanel.getOffsetWidth()-Constants.TAMANOBOTOBEDITOFF, 0);
		PanelEdicion.setSize(Constants.TAMANOBOTOBEDITOFF+Constants.PX,"50px");
		PanelEdicion.clear();
		PanelEdicion.setStyleName("");
		Button Boton=new Button();
		PanelEdicion.add(Boton,0,0);
		Boton.setHTML(InformationConstants.EDIT_BOTTON);
		Boton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				OpenEditPanel();
				
			}
		});
		
	}
	
	public void OpenEditPanel() {
		GeneralPanel.remove(PanelEdicion);
		GeneralPanel.add(PanelEdicion,0,0);
		PanelEdicion.setSize(flowPanel.getOffsetWidth()+"px",flowPanel.getOffsetHeight()+"px");
		PanelEdicion.clear();
		PanelEdicion.setStyleName("BlancoTransparente");
		Button Boton=new Button();
		Boton.setHTML(InformationConstants.END_EDIT_BOTTON);
		Boton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				closeEditPanel();
				
				if (!AddToMyBooksMenuItemTextBox.getText().isEmpty())
					CLOSE_MENU_ITEM=AddToMyBooksMenuItemTextBox.getText();
				else CLOSE_MENU_ITEM=CLOSE_MENU_ITEM_RESET;

				if (!CloseMenuItemTextBox.getText().isEmpty())
					ADD_TO_MY_BOOKS_MENUITEM=CloseMenuItemTextBox.getText();
			else ADD_TO_MY_BOOKS_MENUITEM=ADD_TO_MY_BOOKS_MENUITEM_RESET;
						
								
				ParsearFieldsAItems();
				SaveChages();
			}
		});
		AddToMyBooksMenuItemTextBox=new TextBox();
		AddToMyBooksMenuItemTextBox.setText(CLOSE_MENU_ITEM);
		AddToMyBooksMenuItemTextBox.setSize(AddToMyBooksMenuItem.getOffsetWidth()+"px", AddToMyBooksMenuItem.getOffsetHeight()+"px");
		PanelEdicion.add(AddToMyBooksMenuItemTextBox, AddToMyBooksMenuItem.getAbsoluteLeft()-flowPanel.getAbsoluteLeft()-DecoradorWidth, AddToMyBooksMenuItem.getAbsoluteTop()-flowPanel.getAbsoluteTop()-DecoradorWidth);
		
		CloseMenuItemTextBox=new TextBox();
		CloseMenuItemTextBox.setText(ADD_TO_MY_BOOKS_MENUITEM);
		CloseMenuItemTextBox.setSize(CloseMenuItem.getOffsetWidth()+"px", CloseMenuItem.getOffsetHeight()+"px");
		PanelEdicion.add(CloseMenuItemTextBox, CloseMenuItem.getAbsoluteLeft()-flowPanel.getAbsoluteLeft()-DecoradorWidth, CloseMenuItem.getAbsoluteTop()-flowPanel.getAbsoluteTop()-DecoradorWidth);
		
		PanelEdicion.add(Boton,PanelEdicion.getOffsetWidth()-Constants.TAMANOBOTOBEDITON, 0);
		
		
		
	}

	protected void SaveChages() {
//		TODO InsertIn language
		Window.alert("now under development");
		Language LanguageActual = ActualState.getActualLanguage();
//		String VisorSearcherGoogleBookPopupPanelLanguageConfiguration=toFile();
//		LanguageActual.setVisorSearcherGoogleBookPopupPanelLanguageConfiguration(VisorSearcherGoogleBookPopupPanelLanguageConfiguration);
		ActualState.saveLanguageActual(LanguageActual);
		
	}

	public String toFile() {
		StringBuffer SB=new StringBuffer();
		SB.append(CLOSE_MENU_ITEM + "\r\n");
		SB.append( ADD_TO_MY_BOOKS_MENUITEM + "\r\n" );
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
				CLOSE_MENU_ITEM = Lista[0];
			else CLOSE_MENU_ITEM=CLOSE_MENU_ITEM_RESET;
			if (!Lista[1].isEmpty())
				ADD_TO_MY_BOOKS_MENUITEM = Lista[1];
			else ADD_TO_MY_BOOKS_MENUITEM=ADD_TO_MY_BOOKS_MENUITEM_RESET;

		}
		else 
			Logger.GetLogger().severe(VisorSearcherGoogleBookPopupPanel.class.toString(), ActualState.getUser().toString(), ErrorConstants.ERROR_LOADING_LANGUAGE_IN  + VISORGOOGLESEARCH);	
		ParsearFieldsAItemsRESET();
		}
	}
	
	private static void ParsearFieldsAItemsRESET() {
		CLOSE_MENU_ITEM=CLOSE_MENU_ITEM_RESET;
		ADD_TO_MY_BOOKS_MENUITEM=ADD_TO_MY_BOOKS_MENUITEM_RESET;
		
	}

	protected void ParsearFieldsAItems() {

		AddToMyBooksMenuItem.setHTML(CLOSE_MENU_ITEM);	
		CloseMenuItem.setHTML(ADD_TO_MY_BOOKS_MENUITEM);
			
	}
}
