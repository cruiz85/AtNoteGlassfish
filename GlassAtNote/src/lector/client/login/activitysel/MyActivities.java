package lector.client.login.activitysel;

import java.util.ArrayList;
import java.util.List;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.book.reader.ImageService;
import lector.client.book.reader.ImageServiceAsync;
import lector.client.controler.CalendarNow;
import lector.client.controler.Controlador;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.logger.Logger;
import lector.client.login.ActualUser;
import lector.client.login.bookselec.ButtonActivityReader;
import lector.client.reader.LoadingPanel;
import lector.client.reader.MainEntryPoint;
import lector.share.model.Language;
import lector.share.model.ReadingActivity;
import lector.share.model.client.BookClient;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.LocalBookClient;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.ReadingActivityClient;
import lector.share.model.client.StudentClient;
import lector.share.model.client.TypeClient;
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
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class MyActivities implements EntryPoint {

	private List<ReadingActivityClient> BooksIDs = new ArrayList<ReadingActivityClient>();
	private VerticalPanel verticalPanel = new VerticalPanel();
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	static ImageServiceAsync userImageService = GWT.create(ImageService.class);
	private ReadingActivityClient RA;
	private static int retryCounter=0;
	private static String RAtemp=null;
	private MyActivities Yo;

	public void onModuleLoad() {
		
		BooksIDs = new ArrayList<ReadingActivityClient>();
		RootPanel RootMenu = RootPanel.get("Menu");
		Yo=this;
		RootPanel RootTXOriginal = RootPanel.get();
		RootTXOriginal.setStyleName("Root2");
		generaBookIds();

		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);

		String Bienvenida;
		if ((ActualUser.getUser().getFirstName()!=null)&&(!ActualUser.getUser().getFirstName().isEmpty()))
		Bienvenida="Welcome " + ActualUser.getUser().getFirstName();
		else 
		Bienvenida="Welcome " + ActualUser.getUser().getEmail();
		MenuItem menuItem = new MenuItem(Bienvenida, false, (Command) null);
		menuItem.setEnabled(false);
		menuBar.addItem(menuItem);
		
		MenuItem mntmNewItem = new MenuItem("Edit Profile", false, new Command() {
			public void execute() {
				Controlador.change2UserEdition();
			}
		});
		mntmNewItem.setHTML("Edit User");
		

		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);

		MenuItem menuItem_1 = new MenuItem("Back", false, new Command() {
			public void execute() {
				Controlador.change2Administrator();
			}
		});

		MenuItem menuItem_2 = new MenuItem("Sign out", false, new Command() {
			public void execute() {
				Controlador.change2Welcome();
			//	 Window.open(ActualUser.getUser().getLogoutUrl(), "_self", "");
				ActualUser.setUser(null);
				ActualUser.setBook(null);
				ActualUser.setReadingactivity(null);
			}
		});

		MenuItem menuItem_3 = new MenuItem("Add to group", false, new Command() {
			public void execute() {
				AddGroupPanel AD=new AddGroupPanel();
				AD.center();
			}
		});
		
		MenuItem menuItem_4 = new MenuItem("Delete My User", false, new Command() {
			public void execute() {
				//TODO Pedir Contrasena
				if (Window.confirm(InformationConstants.DO_YOU_WANT_SURE_DELETE_USER))
				{
					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto(InformationConstants.VALIDATING);
					bookReaderServiceHolder.deleteStudentById(ActualUser.getUser().getId(), new AsyncCallback<Void>() {
						
						@Override
						public void onSuccess(Void result) {
							LoadingPanel.getInstance().hide();
							Window.alert(InformationConstants.GOODBYE);
							Logger.GetLogger().info(Yo.getClass().toString(), "Delete:  "+
							"First Name: " + ActualUser.getUser().getFirstName() + " Last Name: " + ActualUser.getUser().getLastName() +
							" Email: " + ActualUser.getUser().getEmail());
							Window.Location.reload();
						}
						
						@Override
						public void onFailure(Throwable caught) {
							LoadingPanel.getInstance().hide();
							Window.alert(ErrorConstants.ERROR_DELETING_USER);
							Logger.GetLogger().severe(Yo.getClass().toString(), ErrorConstants.ERROR_DELETING_USER + " " + ActualUser.getUser().getEmail() + " at " + CalendarNow.GetDateNow() );
						}
					});
				}
			}
		});
		
		if (ActualUser.getUser() instanceof ProfessorClient)
			menuBar.addItem(menuItem_1);
		else if (ActualUser.getUser() instanceof StudentClient)
			{
			menuBar.addItem(mntmNewItem);
			menuBar.addItem(menuItem_2);
			menuBar.addItem(menuItem_3);
			menuBar.addItem(menuItem_4);
			}

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(10);
		horizontalPanel.setStyleName("fondoLogo");
		RootTXOriginal.add(horizontalPanel, 0, 24);
		horizontalPanel.setSize("100%", "100%");
		
		HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
		horizontalPanel_3.setSpacing(6);
		horizontalPanel.add(horizontalPanel_3);
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_3.add(horizontalPanel_1);
		horizontalPanel_1.setStyleName("AzulTransparente");
		horizontalPanel_1.setSpacing(10);
		
		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2.setStyleName("BlancoTransparente");
		horizontalPanel_1.add(horizontalPanel_2);
		verticalPanel = new VerticalPanel();
		horizontalPanel_2.add(verticalPanel);
		verticalPanel.setWidth("272px");
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		BooksIDs = new ArrayList<ReadingActivityClient>();

	}

	private void generaBookIds() {

		UserClient User = ActualUser.getUser();
		if (ActualUser.getUser() instanceof ProfessorClient) {
			bookReaderServiceHolder.getReadingActivitiesByProfessorId(
					User.getId(),
					new AsyncCallback<List<ReadingActivityClient>>()

					{

						public void onSuccess(List<ReadingActivityClient> result) {
							BooksIDs = result;
							setealibros();

						}

						public void onFailure(Throwable caught) {
							Window.alert("The Activities could not be retrived, try again later");

						}
					});

		} else if (User instanceof StudentClient) {

			bookReaderServiceHolder.getReadingActivitiesByStudentId(User.getId(),
					new AsyncCallback<List<ReadingActivityClient>>()

					{

						public void onSuccess(List<ReadingActivityClient> result) {
							BooksIDs = result;
							setealibros();

						}

						public void onFailure(Throwable caught) {
							Window.alert("The Activities could not be retrived, try again later");

						}
					});

		}
	}

	private void setealibros() {
		for (int i = 0; i < BooksIDs.size()-1; i++) {

			ButtonActivityReader button = new ButtonActivityReader(BooksIDs.get(i));
			button.setSize("100%", "100%");
			if (!buttonexist(button)) {
				if (CheckCompleta(button))
				{
				verticalPanel.add(button);
				button.setStyleName("gwt-ButtonTOP");
				button.addMouseDownHandler(new MouseDownHandler() {
					public void onMouseDown(MouseDownEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonPush");
					}
				});
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
				}else
				{
					if (ActualUser.getUser() instanceof ProfessorClient)	
					{
						verticalPanel.add(button);
						button.setEnabled(false);
						button.setTitle("Inclomplete Activity Data");
						button.setStyleName("gwt-ButtonGris");
						button.addMouseDownHandler(new MouseDownHandler() {
							public void onMouseDown(MouseDownEvent event) {
								((Button)event.getSource()).setStyleName("gwt-ButtonGrisPush");
							}
						});
						button.addMouseOutHandler(new MouseOutHandler() {
							public void onMouseOut(MouseOutEvent event) {
								((Button)event.getSource()).setStyleName("gwt-ButtonGris");
							}
						});
						button.addMouseOverHandler(new MouseOverHandler() {
							public void onMouseOver(MouseOverEvent event) {
								((Button)event.getSource()).setStyleName("gwt-ButtonGrisOver");
							}
						});
					}
				}
	
				
				button.addClickHandler(new ClickHandler() {
					

					public void onClick(ClickEvent event) {

						ButtonActivityReader B = (ButtonActivityReader) event.getSource();
						RA=B.getRA();
						ActualUser.setReadingactivity(RA);
						ArrayList<TypeClient> L=new ArrayList<TypeClient>();
						MainEntryPoint.setFiltroTypes(L);			
						loadCatalog();
						 }
				});
			}
		}
		
		
		//Ultimo Botton
		if (!BooksIDs.isEmpty())
		{
			ButtonActivityReader button = new ButtonActivityReader(BooksIDs.get(BooksIDs.size()-1));
			button.setSize("100%", "100%");
			if (!buttonexist(button)) {
				if (CheckCompleta(button))
				{
				verticalPanel.add(button);
				button.setStyleName("gwt-ButtonBotton");
				button.addMouseOutHandler(new MouseOutHandler() {
					public void onMouseOut(MouseOutEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
					}
				});
				button.addMouseOverHandler(new MouseOverHandler() {
					public void onMouseOver(MouseOverEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
					}
				});
				button.addMouseDownHandler(new MouseDownHandler() {
					public void onMouseDown(MouseDownEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
					}
				});
				}else
				{
					if (ActualUser.getUser() instanceof ProfessorClient)	
					{
						verticalPanel.add(button);
						button.setEnabled(false);
						button.setTitle("Inclomplete Activity Data");
						button.setStyleName("gwt-ButtonGrisDown");
						button.addMouseOutHandler(new MouseOutHandler() {
							public void onMouseOut(MouseOutEvent event) {
								((Button)event.getSource()).setStyleName("gwt-ButtonGrisDown");
							}
						});
						button.addMouseOverHandler(new MouseOverHandler() {
							public void onMouseOver(MouseOverEvent event) {
								((Button)event.getSource()).setStyleName("gwt-ButtonGrisDownOver");
							}
						});
						button.addMouseDownHandler(new MouseDownHandler() {
							public void onMouseDown(MouseDownEvent event) {
								((Button)event.getSource()).setStyleName("gwt-ButtonGrisDownPush");
							}
						});
					}
				}
				
				
				button.addClickHandler(new ClickHandler() {

					public void onClick(ClickEvent event) {

						ButtonActivityReader B = (ButtonActivityReader) event.getSource();
						RA=B.getRA();
						ActualUser.setReadingactivity(RA);
						ArrayList<TypeClient> L=new ArrayList<TypeClient>();
						MainEntryPoint.setFiltroTypes(L);
						
						loadCatalog();
					}
				});
			}
		}
	}

	private boolean CheckCompleta(ButtonActivityReader button) {
		ReadingActivityClient RA=button.getRA();
		if ((RA.getBook()!=null)&(RA.getCloseCatalogo()!=null)&(RA.getGroup()!=null)&(RA.getLanguage()!=null)&(RA.getOpenCatalogo()!=null))
			return true; 
		else return false;
	}

	protected void loadCatalog()
	{
		bookReaderServiceHolder.loadCatalogById(RA.getCloseCatalogo().getId(), new AsyncCallback<CatalogoClient>() {
			
			public void onSuccess(CatalogoClient result) {
				ActualUser.setCatalogo(result);
				loadOpenCatalog();
				
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Book not supported yet");
				throw new UnsupportedOperationException(
						"Not supported yet.");
				
			}
		});
	}
	
	protected void loadOpenCatalog()
	{
		bookReaderServiceHolder.loadCatalogById(RA.getOpenCatalogo().getId(), new AsyncCallback<CatalogoClient>() {
			
			public void onSuccess(CatalogoClient result) {
				ActualUser.setOpenCatalog(result);
				Controlador.change2Reader();
				
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Book not supported yet");
				throw new UnsupportedOperationException(
						"Not supported yet.");
				
			}
		});
	}
	
	

	private boolean buttonexist(ButtonActivityReader button) {
		//El primero es un Glue
		for (int i = 1; i < verticalPanel.getWidgetCount(); i++) {
			ButtonActivityReader B = ((ButtonActivityReader) verticalPanel.getWidget(i));
			if (B.getRA().getId().equals(button.getRA().getId()))
				return true;
		}
		return false;
	}
}
