package lector.client.admin.book.googleAPI;

import java.util.List;

import lector.client.admin.book.BookAdministrationEntryPoint;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;
import lector.share.model.Book;
import lector.share.model.Language;
import lector.share.model.client.GoogleBookClient;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItemSeparator;

public class SearcherGoogleEntryPoint implements EntryPoint, HistoryListener {

	private static final String GOOGLE_SEARCH_NAME = "Uploader Google Search Menu";

	private static final int NCampos = 5;
	
	private static String GOOGLE_LOADER_SYSTEM_MENUITEM = "Google Book loader system";
	private static String BACK_MENUITEM="back";
	private static String SEARCH_INICIAL_BUTTON = "Search";
	private static String NEXT_BUTTON = ">";
	private static String PREVIOUS_BUTTON = "<";
	
	private static String GOOGLE_LOADER_SYSTEM_MENUITEM_RESET = "Google Book loader system";
	private static String BACK_MENUITEM_RESET="back";
	private static String SEARCH_INICIAL_BUTTON_RESET = "Search";
	private static String NEXT_BUTTON_RESET = ">";
	private static String PREVIOUS_BUTTON_RESET = "<";
	
	
	private MenuItem GoogleLoaderWellcomeMenuItem;
	private MenuItem BackMenuItem;
	private Button searchInicialButton;
	private Button searchNextButton;
	private Button searchPrevious;
	
	private TextBox GoogleLoaderWellcomeMenuItemTextBox;
	private TextBox BackMenuItemTextBox;
	private TextBox searchInicialButtonTextBox;
	private TextBox searchNextButtonTextBox;
	private TextBox searchPreviousTextBox;
	
	
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private VerticalPanel Panel = new VerticalPanel();
	private HorizontalPanel bookSearcherWidget = new HorizontalPanel();
	private TextBox searcherField = new TextBox();
	
	private Hyperlink[] bookLinks = new Hyperlink[8];
	//private Label labelTester = new Label("Tester");
	private VerticalPanel hyperlinksPanel = new VerticalPanel();
	private static boolean linkTrigger = false;
	
	private static int pos = 0;
	private final Image image_2 = new Image("IconoLogo.JPG");
	private final Label lblNewLabel = new Label("");
	private final SimplePanel simplePanel = new SimplePanel();
	private MenuBar menuBar = new MenuBar(false);
	private MenuItemSeparator separator = new MenuItemSeparator();	
	private RootPanel rootPanel;
	private DockLayoutPanel PanelFondoGeneral;
	private final HorizontalPanel horizontalPanel1 = new HorizontalPanel();
	private final HorizontalPanel horizontalPanel2 = new HorizontalPanel();

	private AbsolutePanel PanelEdicion;
	
	

	public SearcherGoogleEntryPoint() {
		
		searchInicialButton = new Button(SEARCH_INICIAL_BUTTON);
		searchNextButton = new Button(NEXT_BUTTON);
		searchPrevious = new Button(PREVIOUS_BUTTON );
		
		searchNextButton.setSize("100%", "100%");
		searchNextButton.setStyleName("gwt-ButtonCenter");
		searchNextButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});
		searchNextButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			}
		});
		searchNextButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		searchNextButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (pos == 0) {
					searchPrevious.setVisible(true);
				}
				pos++;
				AsyncCallback<List<GoogleBookClient>> callback = new AsyncCallback<List<GoogleBookClient>>() {

					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						throw new UnsupportedOperationException(
								ErrorConstants.ERROR_RETRIVING_NEW_BOOKS);
					}

					public void onSuccess(List<GoogleBookClient> result) {
						LoadingPanel.getInstance().hide();
						String bookLinkString = "";
						cleanHyperLinks();
						if (result.size() == 0) {
							lblNewLabel.setText(InformationConstants.NO_MORE_RESULTS);
							searchNextButton.setVisible(false);
						}
						for (int i = 0; i < result.size(); i++) {
							bookLinks[i] = new Hyperlink("", "#");
							bookLinkString += result.get(i).getTitle();
							bookLinks[i].setHTML(bookLinkString);
							bookLinks[i].setTargetHistoryToken(result.get(i)
									.getISBN().toString());
							Image image = new Image(result.get(i).getTbURL());
//							bookLinks[i].getElement().appendChild(
//									image.getElement());
							bookLinks[i].getElement().insertFirst(
									image.getElement());
							hyperlinksPanel.add(bookLinks[i]);
							bookLinkString = "";
							bookLinks[i].addClickListener(new ClickListener() {

								public void onClick(Widget sender) {
									linkTrigger = true;
								}
							});
							if (linkTrigger) {
//								MainEntryPoint.getTechnicalSpecs().setBook(
//										result.get(i));
							}

						}
					}
				};
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto(
						InformationConstants.LOADING);
				bookReaderServiceHolder.getBookClients(searcherField.getText(), pos,
						callback);
			}
		});
		searchPrevious.setSize("100%", "100%");
		searchPrevious.setStyleName("gwt-ButtonCenter");
		searchPrevious.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});
		searchPrevious.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			}
		});
		searchPrevious.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		searchPrevious.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				pos--;
				if (pos == 0) {
					searchPrevious.setVisible(false);
				}
				if (!searchNextButton.isVisible())
					searchNextButton.setVisible(true);
				AsyncCallback<List<GoogleBookClient>> callback = new AsyncCallback<List<GoogleBookClient>>() {

					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						throw new UnsupportedOperationException(
								ErrorConstants.ERROR_RETRIVING_OLD_BOOKS);
					}

					public void onSuccess(List<GoogleBookClient> result) {
						LoadingPanel.getInstance().hide();
						String bookLinkString = "";
						cleanHyperLinks();
						lblNewLabel.setText("");
						for (int i = 0; i < result.size(); i++) {
							bookLinks[i] = new Hyperlink("", "#");
							bookLinkString += result.get(i).getTitle();
							bookLinks[i].setHTML(bookLinkString);
							bookLinks[i].setTargetHistoryToken(result.get(i)
									.getISBN().toString());
							Image image = new Image(result.get(i).getTbURL());
//							bookLinks[i].getElement().appendChild(
//							image.getElement());
					bookLinks[i].getElement().insertFirst(
							image.getElement());
							hyperlinksPanel.add(bookLinks[i]);
							bookLinkString = "";
							bookLinks[i].addClickListener(new ClickListener() {

								public void onClick(Widget sender) {
									linkTrigger = true;
								}
							});
							if (linkTrigger) {
//								MainEntryPoint.getTechnicalSpecs().setBook(
//										result.get(i));
							}

						}

					}
				};
				LoadingPanel.getInstance().setLabelTexto(
						InformationConstants.LOADING);
				bookReaderServiceHolder.getBookClients(searcherField.getText(), pos,
						callback);
			}
		});
		searchInicialButton.setSize("100%", "100%");
		searchInicialButton.setStyleName("gwt-ButtonCenter");
		searchInicialButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});
		searchInicialButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			}
		});
		searchInicialButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		searchInicialButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				lblNewLabel.setText("");

				AsyncCallback<List<GoogleBookClient>> callback = new AsyncCallback<List<GoogleBookClient>>() {

					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						throw new UnsupportedOperationException(
								ErrorConstants.ERROR_SEARCHING_BOOKS);
					}

					public void onSuccess(List<GoogleBookClient> result) {
						LoadingPanel.getInstance().hide();
						String bookLinkString = "";
						cleanHyperLinks();
						hyperlinksPanel.clear();
						searchNextButton.setVisible(true);
						searchPrevious.setVisible(false);
						if (result.size() == 0) {
							lblNewLabel.setText(InformationConstants.NO_RESULTS);
							searchNextButton.setVisible(false);
						}
						for (int i = 0; i < result.size(); i++) {
							bookLinks[i] = new Hyperlink("", "#");
							bookLinkString += result.get(i).getTitle();
							bookLinks[i].setHTML(bookLinkString);
							bookLinks[i].setTargetHistoryToken(result.get(i)
									.getISBN().toString());
							Image image = new Image(result.get(i).getTbURL());
//							bookLinks[i].getElement().appendChild(
//							image.getElement());
					bookLinks[i].getElement().insertFirst(
							image.getElement());
							hyperlinksPanel.add(bookLinks[i]);
							bookLinkString = "";
							bookLinks[i].addClickListener(new ClickListener() {

								public void onClick(Widget sender) {
									linkTrigger = true;
								}
							});
							if (linkTrigger) {
//								MainEntryPoint.getTechnicalSpecs().setBook(
//										result.get(i));
							}

						}
					}
				};
				LoadingPanel.getInstance().setLabelTexto(
						InformationConstants.LOADING);
				bookReaderServiceHolder.getGoogleBookClients(searcherField.getText(),
						callback);
			}
		});

		searcherField.addKeyDownHandler(new KeyDownHandler() {

			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					lblNewLabel.setText("");
					
					AsyncCallback<List<GoogleBookClient>> callback = new AsyncCallback<List<GoogleBookClient>>() {

						public void onFailure(Throwable caught) {
							LoadingPanel.getInstance().hide();
							throw new UnsupportedOperationException(
									ErrorConstants.ERROR_SEARCHING_BOOKS);
						}

						public void onSuccess(List<GoogleBookClient> result) {
							LoadingPanel.getInstance().hide();
							String bookLinkString = "";
							cleanHyperLinks();
							hyperlinksPanel.clear();
							searchNextButton.setVisible(true);
							searchPrevious.setVisible(false);
							if (result.size() == 0) {
								lblNewLabel.setText(InformationConstants.NO_RESULTS);
								searchNextButton.setVisible(false);
							}
							for (int i = 0; i < result.size(); i++) {
								bookLinks[i] = new Hyperlink("", "#");
								bookLinkString += result.get(i).getTitle();
								bookLinks[i].setHTML(bookLinkString);
								bookLinks[i].setTargetHistoryToken(result
										.get(i).getISBN());
								Image image = new Image(result.get(i)
										.getTbURL());
//								bookLinks[i].getElement().appendChild(
//								image.getElement());
						bookLinks[i].getElement().insertFirst(
								image.getElement());
								hyperlinksPanel.add(bookLinks[i]);
								bookLinkString = "";
								bookLinks[i]
										.addClickListener(new ClickListener() {

											public void onClick(Widget sender) {
												linkTrigger = true;
											}
										});
								if (linkTrigger) {
//									MainEntryPoint.getTechnicalSpecs().setBook(
//											result.get(i));
								}
							}
						}
					};
					LoadingPanel.getInstance().setLabelTexto(
							InformationConstants.LOADING);
					bookReaderServiceHolder.getGoogleBookClients(searcherField.getText(),
							callback);
				}
			}
		});

	}

	public void onModuleLoad() {

		rootPanel = RootPanel.get();
		rootPanel.setSize("100%", "100%");
		rootPanel.setStyleName("Root");
//		RootPanel RootMenu = RootPanel.get("Menu");
//		RootMenu.setStyleName("Root");

		PanelFondoGeneral = new DockLayoutPanel(Unit.PX);
		PanelFondoGeneral.setStyleName("fondoLogo");
		rootPanel.add(PanelFondoGeneral, 0, 0);
		PanelFondoGeneral.setSize("100%", "100%");
		
		Panel.clear();
		for (int i = 0; i < bookLinks.length; i++) {
			bookLinks[i] = new Hyperlink("", "#");
		}
		cleanHyperLinks();
		hyperlinksPanel.clear();

		String token = History.getToken();
		if (token.length() == 0) {
			History.newItem("");
		}

		History.addHistoryListener(this);
		History.fireCurrentHistoryState();
	
		Panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		Panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		menuBar = new MenuBar(false);
		PanelFondoGeneral.addNorth(menuBar,25);

		GoogleLoaderWellcomeMenuItem = new MenuItem(GOOGLE_LOADER_SYSTEM_MENUITEM,
				false, (Command) null);
		GoogleLoaderWellcomeMenuItem.setHTML("Add Book Administration");
		GoogleLoaderWellcomeMenuItem.setEnabled(false);
		menuBar.addItem(GoogleLoaderWellcomeMenuItem);

		menuBar.addSeparator(separator);

		BackMenuItem = new MenuItem(BACK_MENUITEM, false, new Command() {
			public void execute() {
				Controlador.change2BookAdminstrator();
			}
		});
		menuBar.addItem(BackMenuItem);
		horizontalPanel1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel1.setSpacing(10);
		horizontalPanel1.setStyleName("AzulTransparente");
		
		Panel.add(horizontalPanel1);
		horizontalPanel2.setSpacing(6);
		horizontalPanel2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel2.setStyleName("BlancoTransparente");
		
		horizontalPanel1.add(horizontalPanel2);
		
		searchInicialButton.setHTML(SEARCH_INICIAL_BUTTON);
		searchNextButton.setHTML(NEXT_BUTTON);
		searchPrevious.setHTML(PREVIOUS_BUTTON );
		searchNextButton.setVisible(false);
		searchPrevious.setVisible(false);
		bookSearcherWidget.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel2.add(bookSearcherWidget);
		bookSearcherWidget.clear();
		bookSearcherWidget
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		bookSearcherWidget.add(image_2);
		image_2.setSize("25px", "25px");
		bookSearcherWidget.add(searcherField);
		searcherField.setWidth("617px");
		searcherField.setText("");
		searchInicialButton.setHTML(SEARCH_INICIAL_BUTTON);
		bookSearcherWidget.add(searchInicialButton);
		bookSearcherWidget.add(searchPrevious);
		bookSearcherWidget.add(searchNextButton);
		simplePanel.setStyleName("h5");
		Panel.add(simplePanel);
		lblNewLabel.setStyleName("h5");
		simplePanel.setWidget(lblNewLabel);
		lblNewLabel.setSize("100%", "100%");

		lblNewLabel.setText("");
		hyperlinksPanel.setStyleName("Root");
		hyperlinksPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		Panel.add(hyperlinksPanel);
		PanelFondoGeneral.add(Panel);
		Panel.setSize("100%", "100%");
		// add(Panel);
		
		PanelEdicion = new AbsolutePanel();
		if (ActualState.isLanguageActive())
			closeEditPanel();
		

	}

	private void cleanHyperLinks() {
		for (int i = 0; i < bookLinks.length; i++) {
			if (bookLinks[i].getElement().hasChildNodes()) {
				while (bookLinks[i].getElement().getChildNodes().getLength() >= 1) {
					bookLinks[i].getElement().removeChild(
							bookLinks[i].getElement().getFirstChild());
				}
			}
		}
	}

	public void onHistoryChanged(String historyToken) {
//		labelTester.setText(historyToken);

		 AsyncCallback<GoogleBookClient> callback = new AsyncCallback<GoogleBookClient>() {
		
		 public void onFailure(Throwable caught) {
			 LoadingPanel.getInstance().hide();
		 Window.alert(ErrorConstants.NOT_SUPPORTED);
		 //throw new UnsupportedOperationException("Not supported yet.");
		
		 }
		
		 public void onSuccess(GoogleBookClient result) {
			 LoadingPanel.getInstance().hide();
		/* ActualUser.setBookActual(result);
		 Controlador.change2Reader();
		 // MainEntryPoint.SetBook(result);
		 labelTester.setText(result.getAuthor());
		 // MainEntryPoint.getTechnicalSpecs().setBook(result);
*/		 

			 VisorSearcherGoogleBookPopupPanel VS = new VisorSearcherGoogleBookPopupPanel(result);
			 VS.center();
			
		
		 }
		 };
		 
		if (linkTrigger) {
			// bookReaderServiceHolder.getBookImageInHathiByISBN(historyToken,
			// callback);
			LoadingPanel.getInstance().setLabelTexto(
					InformationConstants.LOADING);
			LoadingPanel.getInstance().center();
			 bookReaderServiceHolder.loadFullBookInGoogle(historyToken,
			 callback);
			// MainEntryPoint.setCurrentPageNumber(0);


		}
		linkTrigger = false;
	}

	
	public void closeEditPanel() {
		rootPanel.remove(PanelEdicion);
		rootPanel.add(PanelEdicion, PanelFondoGeneral.getOffsetWidth()
				- Constants.TAMANOBOTOBEDITOFF, 0);
		PanelEdicion.setSize("40px", "50px");
		PanelEdicion.clear();
		PanelEdicion.setStyleName("");
		Button Boton = new Button();
		PanelEdicion.add(Boton, 0, 0);
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
		PanelEdicion.setSize(PanelFondoGeneral.getOffsetWidth() + "px",
				PanelFondoGeneral.getOffsetHeight() + "px");
		PanelEdicion.clear();
		PanelEdicion.setStyleName("BlancoTransparente");
		Button Boton = new Button();
		PanelEdicion.add(Boton, PanelEdicion.getOffsetWidth()
				- Constants.TAMANOBOTOBEDITON, 0);
		Boton.setHTML(InformationConstants.END_EDIT_BOTTON);
		Boton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				closeEditPanel();

				if (!GoogleLoaderWellcomeMenuItemTextBox.getText().isEmpty())
					GOOGLE_LOADER_SYSTEM_MENUITEM = GoogleLoaderWellcomeMenuItemTextBox
							.getText();
				else
					GOOGLE_LOADER_SYSTEM_MENUITEM = GOOGLE_LOADER_SYSTEM_MENUITEM_RESET;

				if (!BackMenuItemTextBox.getText().isEmpty())
					BACK_MENUITEM = BackMenuItemTextBox.getText();
				else
					BACK_MENUITEM = BACK_MENUITEM_RESET;

				if (!searchInicialButtonTextBox.getText().isEmpty())
					SEARCH_INICIAL_BUTTON = searchInicialButtonTextBox.getText();
				else
					SEARCH_INICIAL_BUTTON = SEARCH_INICIAL_BUTTON_RESET;

				if (!searchNextButtonTextBox.getText().isEmpty())
					NEXT_BUTTON = searchNextButtonTextBox
							.getText();
				else
					NEXT_BUTTON = NEXT_BUTTON_RESET;
				
				if (!searchPreviousTextBox.getText().isEmpty())
					PREVIOUS_BUTTON = searchPreviousTextBox
							.getText();
				else
					PREVIOUS_BUTTON = PREVIOUS_BUTTON_RESET;


				ParsearFieldsAItems();
				SaveChages();
			}
		});

		GoogleLoaderWellcomeMenuItemTextBox = new TextBox();
		GoogleLoaderWellcomeMenuItemTextBox.setText(GOOGLE_LOADER_SYSTEM_MENUITEM);
		GoogleLoaderWellcomeMenuItemTextBox.setSize(
				GoogleLoaderWellcomeMenuItem.getOffsetWidth() + "px",
				GoogleLoaderWellcomeMenuItem.getOffsetHeight() + "px");
		PanelEdicion.add(GoogleLoaderWellcomeMenuItemTextBox,
				GoogleLoaderWellcomeMenuItem.getAbsoluteLeft(),
				GoogleLoaderWellcomeMenuItem.getAbsoluteTop());

		BackMenuItemTextBox = new TextBox();
		BackMenuItemTextBox.setText(BACK_MENUITEM);
		BackMenuItemTextBox.setSize(BackMenuItem.getOffsetWidth()
				+ "px", BackMenuItem.getOffsetHeight() + "px");
		PanelEdicion.add(BackMenuItemTextBox,
				BackMenuItem.getAbsoluteLeft(),
				BackMenuItem.getAbsoluteTop());

		searchInicialButtonTextBox = new TextBox();
		searchInicialButtonTextBox.setText(SEARCH_INICIAL_BUTTON);
		searchInicialButtonTextBox.setSize(
				searchInicialButton.getOffsetWidth() + "px",
				searchInicialButton.getOffsetHeight() + "px");
		PanelEdicion.add(searchInicialButtonTextBox,
				searchInicialButton.getAbsoluteLeft(),
				searchInicialButton.getAbsoluteTop());

		searchNextButtonTextBox = new TextBox();
		searchNextButtonTextBox.setText(NEXT_BUTTON);
		searchNextButtonTextBox.setSize(
				searchNextButton.getOffsetWidth() + "px",
				searchNextButton.getOffsetHeight() + "px");
		PanelEdicion.add(searchNextButtonTextBox,
				searchNextButton.getAbsoluteLeft(),
				searchNextButton.getAbsoluteTop());
		
		searchPreviousTextBox = new TextBox();
		searchPreviousTextBox.setText(PREVIOUS_BUTTON);
		searchPreviousTextBox.setSize(
				searchPrevious.getOffsetWidth() + "px",
				searchPrevious.getOffsetHeight() + "px");
		PanelEdicion.add(searchPreviousTextBox,
				searchPrevious.getAbsoluteLeft(),
				searchPrevious.getAbsoluteTop());


	}

	protected void SaveChages() {
		Language LanguageActual = ActualState.getActualLanguage();
		String SearcherGoogleEntryPointLanguageConfiguration = toFile();
		LanguageActual
				.setSearcherGoogleEntryPointLanguageConfiguration(SearcherGoogleEntryPointLanguageConfiguration);
		ActualState.saveLanguageActual(LanguageActual);
	}

	public void ParsearFieldsAItems() {
		GoogleLoaderWellcomeMenuItem.setHTML(GOOGLE_LOADER_SYSTEM_MENUITEM);
		BackMenuItem.setHTML(BACK_MENUITEM);
		searchInicialButton.setText(SEARCH_INICIAL_BUTTON);
		searchNextButton.setText(NEXT_BUTTON);
		searchPrevious.setText(PREVIOUS_BUTTON);

	}

	public static String toFile() {
		StringBuffer SB = new StringBuffer();
		SB.append(GOOGLE_LOADER_SYSTEM_MENUITEM + "\r\n");
		SB.append(BACK_MENUITEM + "\r\n");
		SB.append(SEARCH_INICIAL_BUTTON + "\r\n");
		SB.append(NEXT_BUTTON + "\r\n");
		SB.append(PREVIOUS_BUTTON + "\r\n");
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
				GOOGLE_LOADER_SYSTEM_MENUITEM = Lista[0];
			else
				GOOGLE_LOADER_SYSTEM_MENUITEM = GOOGLE_LOADER_SYSTEM_MENUITEM_RESET;
			if (!Lista[1].isEmpty())
				BACK_MENUITEM = Lista[1];
			else
				BACK_MENUITEM = BACK_MENUITEM_RESET;
			if (!Lista[2].isEmpty())
				SEARCH_INICIAL_BUTTON = Lista[2];
			else
				SEARCH_INICIAL_BUTTON = SEARCH_INICIAL_BUTTON_RESET;
			if (!Lista[3].isEmpty())
				NEXT_BUTTON = Lista[3];
			else
				NEXT_BUTTON = NEXT_BUTTON_RESET;
			
			if (!Lista[4].isEmpty())
				PREVIOUS_BUTTON = Lista[4];
			else
				PREVIOUS_BUTTON = PREVIOUS_BUTTON_RESET;
		} else
			Logger.GetLogger().severe(
					SearcherGoogleEntryPoint.class.toString(),
					ActualState.getUser().toString(),
					ErrorConstants.ERROR_LOADING_LANGUAGE_IN
							+ GOOGLE_SEARCH_NAME);
		ParsearFieldsAItemsRESET();
		}
	}
	
	private static void ParsearFieldsAItemsRESET() {
		GOOGLE_LOADER_SYSTEM_MENUITEM = GOOGLE_LOADER_SYSTEM_MENUITEM_RESET;
		BACK_MENUITEM = BACK_MENUITEM_RESET;
		SEARCH_INICIAL_BUTTON = SEARCH_INICIAL_BUTTON_RESET;
		NEXT_BUTTON = NEXT_BUTTON_RESET;
		PREVIOUS_BUTTON = PREVIOUS_BUTTON_RESET;
		
	}
}
