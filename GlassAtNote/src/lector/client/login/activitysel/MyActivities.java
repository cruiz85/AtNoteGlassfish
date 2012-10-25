package lector.client.login.activitysel;

import java.util.ArrayList;

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
import lector.share.model.Language;
import lector.share.model.ReadingActivity;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.StudentClient;
import lector.share.model.client.UserClient;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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

	private ArrayList<ReadingActivity> BooksIDs = new ArrayList<ReadingActivity>();
	private VerticalPanel verticalPanel = new VerticalPanel();
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	static ImageServiceAsync userImageService = GWT.create(ImageService.class);
	private ReadingActivity RA;
	private static int retryCounter=0;
	private static String RAtemp=null;
	private MyActivities Yo;

	public void onModuleLoad() {
		
		BooksIDs = new ArrayList<ReadingActivity>();
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
		verticalPanel = new VerticalPanel();
		verticalPanel.setSpacing(12);
		horizontalPanel.add(verticalPanel);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		HorizontalPanel Glue = new HorizontalPanel();
		verticalPanel.add(Glue);
		Glue.setSize("272px", "20px");
		BooksIDs = new ArrayList<ReadingActivity>();

	}

	private void generaBookIds() {

		UserClient User = ActualUser.getUser();
		if (ActualUser.getUser() instanceof ProfessorClient) {
//			bookReaderServiceHolder.getReadingActivityByProfessorId(
//					User.getId(),
//					new AsyncCallback<ArrayList<ReadingActivity>>()
//
//					{
//
//						public void onSuccess(ArrayList<ReadingActivity> result) {
//							BooksIDs = result;
//							setealibros();
//
//						}
//
//						public void onFailure(Throwable caught) {
//							Window.alert("The Activities could not be retrived, try again later");
//
//						}
//					});

		} else if (User instanceof StudentClient) {

//			bookReaderServiceHolder.getReadingActivityByUserId(User.getId(),
//					new AsyncCallback<ArrayList<ReadingActivity>>()
//
//					{
//
//						public void onSuccess(ArrayList<ReadingActivity> result) {
//							BooksIDs = result;
//							setealibros();
//
//						}
//
//						public void onFailure(Throwable caught) {
//							Window.alert("The Activities could not be retrived, try again later");
//
//						}
//					});

		}
	}

//	private void setealibros() {
//		for (int i = 0; i < BooksIDs.size()-1; i++) {
//
//			ButtonActivityReader button = new ButtonActivityReader(BooksIDs.get(i));
//			button.setSize("100%", "100%");
//			if (!buttonexist(button)) {
//				if (CheckCompleta(button))
//				{
//				verticalPanel.add(button);
//				button.setStyleName("gwt-ButtonTOP");
//				button.addMouseDownHandler(new MouseDownHandler() {
//					public void onMouseDown(MouseDownEvent event) {
//						((Button)event.getSource()).setStyleName("gwt-ButtonPush");
//					}
//				});
//				button.addMouseOutHandler(new MouseOutHandler() {
//					public void onMouseOut(MouseOutEvent event) {
//						((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
//					}
//				});
//				button.addMouseOverHandler(new MouseOverHandler() {
//					public void onMouseOver(MouseOverEvent event) {
//						((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
//					}
//				});
//				}else
//				{
//					if (ActualUser.getUser() instanceof Professor)	
//					{
//						verticalPanel.add(button);
//						button.setEnabled(false);
//						button.setTitle("Inclomplete Activity Data");
//						button.setStyleName("gwt-ButtonGris");
//						button.addMouseDownHandler(new MouseDownHandler() {
//							public void onMouseDown(MouseDownEvent event) {
//								((Button)event.getSource()).setStyleName("gwt-ButtonGrisPush");
//							}
//						});
//						button.addMouseOutHandler(new MouseOutHandler() {
//							public void onMouseOut(MouseOutEvent event) {
//								((Button)event.getSource()).setStyleName("gwt-ButtonGris");
//							}
//						});
//						button.addMouseOverHandler(new MouseOverHandler() {
//							public void onMouseOver(MouseOverEvent event) {
//								((Button)event.getSource()).setStyleName("gwt-ButtonGrisOver");
//							}
//						});
//					}
//				}
//	
//				
//				button.addClickHandler(new ClickHandler() {
//					private AsyncCallback<Book> callback;
//					private AsyncCallback<LocalBook> callback2;
//
//					public void onClick(ClickEvent event) {
//
//						LoadingPanel.getInstance().center();
//						LoadingPanel.getInstance().setLabelTexto("Loading...");
//						callback = new AsyncCallback<Book>() {
//
//							public void onFailure(Throwable caught) {
//								if (retryCounter<3)
//								{
//								retryCounter++;
//								//TODO Error a lenguaje
//								Window.alert("Try Again " + retryCounter);
//								bookReaderServiceHolder.loadFullBookInGoogle(RAtemp,
//										callback);
//								
//								}else
//								{
//								Window.alert("Failed to load the book, this may be unavailable or the connection is too slow.");
//								LoadingPanel.getInstance().hide();
////								throw new UnsupportedOperationException(
////										"Not supported yet.");
//								}
//							}
//
//							public void onSuccess(Book result) {
//								ActualUser.setReadingactivity(RA);
//								ActualUser.setBook(result);
//								ArrayList<Long> L=new ArrayList<Long>();
//								L.add(new Long(Long.MIN_VALUE));
//								MainEntryPoint.setFiltroTypes(L);
//								
//								loadCatalog();
//								LoadingPanel.getInstance().hide();
//
//								// MainEntryPoint.SetBook(result);
//								// labelTester.setText(result.getAuthor());
//								// MainEntryPoint.getTechnicalSpecs().setBook(result);
//
//							}
//
//
//						};
//						
//						
//						callback2=new AsyncCallback<LocalBook>() {
//							
//							public void onSuccess(LocalBook B) {
//								ActualUser.setReadingactivity(RA);
//								ActualUser.setBook(B);
//								ArrayList<Long> L=new ArrayList<Long>();
//								L.add(new Long(Long.MIN_VALUE));
//								MainEntryPoint.setFiltroTypes(L);
//								loadCatalog();
//								LoadingPanel.getInstance().hide();
//								
//							}
//							
//							public void onFailure(Throwable caught) {
//								if (retryCounter<3)
//								{
//								retryCounter++;
//								//TODO Error a lenguaje
//								Window.alert("Try Again " + retryCounter);
//								userImageService.loadBookBlobById(Long.parseLong(RAtemp), callback2);
//								
//								}else
//								{
//								Window.alert("Failed to load the book, this may be unavailable or the connection is too slow.");
//								LoadingPanel.getInstance().hide();
////								throw new UnsupportedOperationException(
////										"Not supported yet.");
//								}
//								
//							}
//						};
//						ButtonActivityReader B = (ButtonActivityReader) event.getSource();
//						String[] SS = B.getRA().getBookId().split(Constants.BREAKER);
//						RA=B.getRA();
//						retryCounter=0;
//						RAtemp=SS[1];
//						 if (!RAtemp.startsWith(" ##"))
//							 bookReaderServiceHolder.loadFullBookInGoogle(SS[1],
//									 callback);
//						 else 
//						 {
//							String[] RAtemp2=RAtemp.split("##");
//							RAtemp=RAtemp2[1];
//							userImageService.loadBookBlobById(Long.parseLong(RAtemp), callback2);
//							
//						 }
//					}
//				});
//			}
//
//		}
//		
//		//Ultimo Botton
//		if (!BooksIDs.isEmpty())
//		{
//			ButtonActivityReader button = new ButtonActivityReader(BooksIDs.get(BooksIDs.size()-1));
//			button.setSize("100%", "100%");
//			if (!buttonexist(button)) {
//				if (CheckCompleta(button))
//				{
//				verticalPanel.add(button);
//				button.setStyleName("gwt-ButtonBotton");
//				button.addMouseOutHandler(new MouseOutHandler() {
//					public void onMouseOut(MouseOutEvent event) {
//						((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
//					}
//				});
//				button.addMouseOverHandler(new MouseOverHandler() {
//					public void onMouseOver(MouseOverEvent event) {
//						((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
//					}
//				});
//				button.addMouseDownHandler(new MouseDownHandler() {
//					public void onMouseDown(MouseDownEvent event) {
//						((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
//					}
//				});
//				}else
//				{
//					if (ActualUser.getUser().getProfile().equals(Constants.PROFESSOR))	
//					{
//						verticalPanel.add(button);
//						button.setEnabled(false);
//						button.setTitle("Inclomplete Activity Data");
//						button.setStyleName("gwt-ButtonGrisDown");
//						button.addMouseOutHandler(new MouseOutHandler() {
//							public void onMouseOut(MouseOutEvent event) {
//								((Button)event.getSource()).setStyleName("gwt-ButtonGrisDown");
//							}
//						});
//						button.addMouseOverHandler(new MouseOverHandler() {
//							public void onMouseOver(MouseOverEvent event) {
//								((Button)event.getSource()).setStyleName("gwt-ButtonGrisDownOver");
//							}
//						});
//						button.addMouseDownHandler(new MouseDownHandler() {
//							public void onMouseDown(MouseDownEvent event) {
//								((Button)event.getSource()).setStyleName("gwt-ButtonGrisDownPush");
//							}
//						});
//					}
//				}
//				
//				
//				button.addClickHandler(new ClickHandler() {
//					private AsyncCallback<Book> callback;
//					private AsyncCallback<LocalBook> callback2;
//
//					public void onClick(ClickEvent event) {
//
//						LoadingPanel.getInstance().center();
//						LoadingPanel.getInstance().setLabelTexto("Loading...");
//						callback = new AsyncCallback<Book>() {
//
//							public void onFailure(Throwable caught) {
//								if (retryCounter<3)
//								{
//								retryCounter++;
//								//TODO Error a lenguaje
//								Window.alert("Try Again " + retryCounter);
//								bookReaderServiceHolder.loadFullBookInGoogle(RAtemp,
//										callback);
//								
//								}else
//								{
//								Window.alert("Failed to load the book, this may be unavailable or the connection is too slow.");
//								LoadingPanel.getInstance().hide();
////								throw new UnsupportedOperationException(
////										"Not supported yet.");
//								}
//							}
//
//							public void onSuccess(Book result) {
//								ActualUser.setReadingactivity(RA);
//								ActualUser.setBook(result);
//								ArrayList<Long> L=new ArrayList<Long>();
//								L.add(new Long(Long.MIN_VALUE));
//								MainEntryPoint.setFiltroTypes(L);
//								loadCatalog();
//								LoadingPanel.getInstance().hide();
//
//								// MainEntryPoint.SetBook(result);
//								// labelTester.setText(result.getAuthor());
//								// MainEntryPoint.getTechnicalSpecs().setBook(result);
//
//							}
//
//
//						};
//						callback2=new AsyncCallback<LocalBook>() {
//							
//							public void onSuccess(LocalBook B) {
//								ActualUser.setReadingactivity(RA);
//								ActualUser.setBook(B);
//								ArrayList<Long> L=new ArrayList<Long>();
//								L.add(new Long(Long.MIN_VALUE));
//								MainEntryPoint.setFiltroTypes(L);
//								loadCatalog();
//								LoadingPanel.getInstance().hide();
//								
//							}
//							
//							public void onFailure(Throwable caught) {
//								if (retryCounter<3)
//								{
//								retryCounter++;
//								//TODO Error a lenguaje
//								Window.alert("Try Again " + retryCounter);
//								userImageService.loadBookBlobById(Long.parseLong(RAtemp), callback2);
//								
//								}else
//								{
//								Window.alert("Failed to load the book, this may be unavailable or the connection is too slow.");
//								LoadingPanel.getInstance().hide();
////								throw new UnsupportedOperationException(
////										"Not supported yet.");
//								}
//								
//							}
//						};
//						ButtonActivityReader B = (ButtonActivityReader) event.getSource();
//						String[] SS = B.getRA().getBookId().split(Constants.BREAKER);
//						RA=B.getRA();
//						retryCounter=0;
//						RAtemp=SS[SS.length-1];
//						 if (!RAtemp.startsWith(" ##"))
//							 bookReaderServiceHolder.loadFullBookInGoogle(SS[1],
//									 callback);
//						 else 
//						 {
//							String[] RAtemp2=RAtemp.split("##");
//							RAtemp=RAtemp2[1];
//							userImageService.loadBookBlobById(Long.parseLong(RAtemp), callback2);
//							
//						 }
//					}
//				});
//			}
//		}
//	}

	private boolean CheckCompleta(ButtonActivityReader button) {
		ReadingActivity RA=button.getRA();
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
				loadLanguage();
				
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Book not supported yet");
				throw new UnsupportedOperationException(
						"Not supported yet.");
				
			}
		});
	}
	
	protected void loadLanguage() {
		bookReaderServiceHolder.loadLanguageById(RA.getLanguage().getId(), new AsyncCallback<Language>() {
			
			public void onSuccess(Language result) {
				ActualUser.setLanguage(result);
				Controlador.change2Reader();
				
			}
			


			public void onFailure(Throwable caught) {
				Window.alert("Book not supported yet");
				throw new UnsupportedOperationException(
						"Not supported yet.");
				
			}
		});
		
	}

	private boolean buttonexist(Button button) {
		//El primero es un Glue
		for (int i = 1; i < verticalPanel.getWidgetCount(); i++) {
			Button B = ((Button) verticalPanel.getWidget(i));
			if (B.getText().equals(button.getText()))
				return true;
		}
		return false;
	}
}
