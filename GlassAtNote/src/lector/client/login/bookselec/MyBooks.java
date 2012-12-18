package lector.client.login.bookselec;

import java.util.ArrayList;
import java.util.List;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.CalendarNow;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;
import lector.share.model.client.BookClient;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.UserClient;

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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class MyBooks implements EntryPoint {
	
	/// IDIOMA
	public static String MENU_WELLCOME_LABEL="'s Personal Library ";
	
	
	private List<Long> BooksIDs = new ArrayList<Long>();
	private VerticalPanel verticalPanel;
	private VerticalPanel PanelBotones;
	private MyBooks Yo;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	

	public void onModuleLoad() {
		Yo=this;
		BooksIDs = new ArrayList<Long>();
		RootPanel RootTXOriginal = RootPanel.get();
		

		MenuBar menuBar = new MenuBar(false);
		RootTXOriginal.add(menuBar,0,0);

			
		MenuItem TextoBienvenida = new MenuItem(
				ActualState.getUser().getLastName() + MyBooks.MENU_WELLCOME_LABEL, false,
				(Command) null);
		TextoBienvenida.setEnabled(false);
		menuBar.addItem(TextoBienvenida);
		menuBar.setWidth("100%");

		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);

		MenuItem BotonAtras = new MenuItem("Back", false, new Command() {
			public void execute() {
				Controlador.change2Administrator();
			}
		});
		
		
		menuBar.addItem(BotonAtras);


		HorizontalPanel PanelGeneral = new HorizontalPanel();
		PanelGeneral.setSpacing(13);
		PanelGeneral.setStyleName("fondoLogo");
		
		RootTXOriginal.add(PanelGeneral,0,25);
		PanelGeneral.setSize("100%", "97%");
		
		VerticalPanel PanelLibrosGeneral = new VerticalPanel();
		PanelLibrosGeneral.setStyleName("AzulTransparente");
		PanelLibrosGeneral.setSpacing(10);
		PanelGeneral.add(PanelLibrosGeneral);
		PanelBotones = new VerticalPanel();
		verticalPanel = new VerticalPanel();
		verticalPanel.setStyleName("BlancoTransparente");
		verticalPanel.setSpacing(6);
		PanelLibrosGeneral.add(verticalPanel);
		verticalPanel.add(PanelBotones);
		PanelBotones.setSize("500px", "");
		BooksIDs = new ArrayList<Long>();
		
		generaBookIds();

	}

	private void generaBookIds() {
		
		UserClient User = ActualState.getUser();
		if (User instanceof ProfessorClient){
			BooksIDs = ((ProfessorClient) User).getBooks();
			LoadingPanel.getInstance().center();
			LoadingPanel.getInstance().setLabelTexto(
					InformationConstants.LOADING);
			bookReaderServiceHolder.getBookClientsByIds(BooksIDs, new AsyncCallback<List<BookClient>>() {
				
				@Override
				public void onSuccess(List<BookClient> result) {
					LoadingPanel.getInstance().hide();
					setealibros(result);
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					LoadingPanel.getInstance().hide();
					Window.alert(ErrorConstants.ERROR_LOADING_BOOKS + ActualState.getUser().getEmail());
					Logger.GetLogger().severe(Yo.getClass().toString(), ErrorConstants.ERROR_LOADING_BOOKS + ActualState.getUser().getEmail() + " at " + CalendarNow.GetDateNow() );
					
				}
			});
			
		}
		
	}

	private void setealibros(List<BookClient> result) {
		for (int i = 0; i < result.size(); i++) {

			ButtonBook button = new ButtonBook(result.get(i));
			button.setSize("100%", "100%");
			if (!buttonexist(button)){
				PanelBotones.add(button);
			button.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {

//					LoadingPanel.getInstance().center();
//					LoadingPanel.getInstance().setLabelTexto("Loading...");
//					AsyncCallback<Book> callback = new AsyncCallback<Book>() {
//
//						public void onFailure(Throwable caught) {
//							Window.alert("Book not supported yet");
////							throw new UnsupportedOperationException(
////									"Not supported yet.");
//							LoadingPanel.getInstance().hide();
//
//						}
//
//						public void onSuccess(Book result) {
//							Visor Visor=new Visor(result.getImagesPath());
//							Visor.center();
//							Controlador.change2VisorEntry(result.getImagesPath());
//							LoadingPanel.getInstance().hide();
//						}
//					};
							
					ButtonBook Book = (ButtonBook) event.getSource();
//					String[] SS=B.getHTML().split(Constants.BREAKER);
//					
//					if (!SS[SS.length-1].startsWith(" ##"))
//						bookReaderServiceHolder.loadFullBookInGoogle(SS[1],
//							callback);
//					else {
//						Window.alert(InformationConstants.THIS_IS_A_LOCAL_BOOK);
//						LoadingPanel.getInstance().hide();
//					}
					
					Visor Visor=new Visor(Book.getLibro());
					Visor.center();
					
				}
			});			
			button.setStyleName("gwt-ButtonTOP");
			button.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
				}
			});
			button.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
				}
			});
			button.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonPush");
				}
			});
		}
		
	}
		if (PanelBotones.getWidgetCount()>0)
		{
		Widget W=PanelBotones.getWidget(PanelBotones.getWidgetCount()-1);
		Button B=(Button)W;
		B.setSize("100%", "100%");
		B.setStyleName("gwt-ButtonBotton");
		B.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
				}
			});
		B.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
				}
			});
		B.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
				}
			});
	}
	}



	private boolean buttonexist(Button button) {
		for (int i = 0; i < PanelBotones.getWidgetCount(); i++) {
			Button B=((Button)PanelBotones.getWidget(i));
			if (B.getText().equals(button.getText())) return true;
		}
		return false;
	}

}
