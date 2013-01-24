package lector.client.admin.book.googleAPI;

import java.util.List;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Controlador;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.reader.LoadingPanel;
import lector.share.model.Book;
import lector.share.model.client.GoogleBookClient;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
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

	

	private static String SEARCH_INICIAL_BUTTON = "Search";
	private static String NEXT_BUTTON = ">";
	private static String PREVIOUS_BUTTON = "<";
	
	private Button searchInicialButton;
	private Button searchNextButton;
	private Button searchPrevious;
	
	
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private VerticalPanel Panel = new VerticalPanel();
	private HorizontalPanel bookSearcherWidget = new HorizontalPanel();
	private TextBox searcherField = new TextBox();
	
	private Hyperlink[] bookLinks = new Hyperlink[8];
	private Label labelTester = new Label("Tester");
	private VerticalPanel hyperlinksPanel = new VerticalPanel();
	private static boolean linkTrigger = false;
	
	private static int pos = 0;
	private final Image image_1 = new Image("Logo.jpg");
	private final Image image_2 = new Image("IconoLogo.JPG");
	private final Label lblNewLabel = new Label("");
	private final SimplePanel simplePanel = new SimplePanel();
	private MenuBar menuBar = new MenuBar(false);
	private MenuItem mntmAddBookAdministration;
	private MenuItemSeparator separator = new MenuItemSeparator();
	private MenuItem mntmNewItem;
	

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
				image_1.setVisible(false);
				image_2.setVisible(true);
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
					image_1.setVisible(false);
					image_2.setVisible(true);
					lblNewLabel.setText("");
					
					AsyncCallback<List<GoogleBookClient>> callback = new AsyncCallback<List<GoogleBookClient>>() {

						public void onFailure(Throwable caught) {
							LoadingPanel.getInstance().hide();
							throw new UnsupportedOperationException(
									"Not supported yet.");
						}

						public void onSuccess(List<GoogleBookClient> result) {
							LoadingPanel.getInstance().hide();
							String bookLinkString = "";
							cleanHyperLinks();
							hyperlinksPanel.clear();
							searchNextButton.setVisible(true);
							searchPrevious.setVisible(false);
							if (result.size() == 0) {
								lblNewLabel.setText("No results found");
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

		RootPanel rootPanel = RootPanel.get("Original");
		rootPanel.setSize("100%", "100%");
		rootPanel.setStyleName("Root");
		RootPanel RootMenu = RootPanel.get("Menu");
		RootMenu.setStyleName("Root");

		Panel.clear();
		for (int i = 0; i < bookLinks.length; i++) {
			bookLinks[i] = new Hyperlink("", "#");
		}
		cleanHyperLinks();
		hyperlinksPanel.clear();
		searchNextButton.setVisible(false);
		searchPrevious.setVisible(false);

		String token = History.getToken();
		if (token.length() == 0) {
			History.newItem("");
		}

		History.addHistoryListener(this);
		History.fireCurrentHistoryState();
		bookSearcherWidget.clear();
		bookSearcherWidget.setStyleName("Root");
		bookSearcherWidget
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		bookSearcherWidget.add(image_2);
		image_2.setSize("25px", "25px");
		image_2.setVisible(false);
		bookSearcherWidget.add(searcherField);
		searcherField.setWidth("617px");
		searcherField.setText("");
		searchInicialButton.setHTML(SEARCH_INICIAL_BUTTON);
		bookSearcherWidget.add(searchInicialButton);
		bookSearcherWidget.add(searchPrevious);
		bookSearcherWidget.add(searchNextButton);
	
		Panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		Panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		Panel.add(image_1);
		image_1.setSize("462px", "258px");
		image_1.setVisible(true);

		menuBar = new MenuBar(false);
		RootMenu.add(menuBar);

		mntmAddBookAdministration = new MenuItem("Add Book Administration",
				false, (Command) null);
		mntmAddBookAdministration.setHTML("Add Book Administration");
		mntmAddBookAdministration.setEnabled(false);
		menuBar.addItem(mntmAddBookAdministration);

		menuBar.addSeparator(separator);

		mntmNewItem = new MenuItem("Back", false, new Command() {
			public void execute() {
				Controlador.change2BookAdminstrator();
			}
		});
		menuBar.addItem(mntmNewItem);
		Panel.add(bookSearcherWidget);
		simplePanel.setStyleName("h5");
		Panel.add(simplePanel);
		lblNewLabel.setStyleName("h5");
		simplePanel.setWidget(lblNewLabel);
		lblNewLabel.setSize("100%", "100%");

		lblNewLabel.setText("");
		hyperlinksPanel.setStyleName("Root");
		hyperlinksPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		Panel.add(hyperlinksPanel);
		rootPanel.add(Panel);
		// add(Panel);

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
		labelTester.setText(historyToken);

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

	public static void setBook(Book book) {
	}

	public static void setCancelTrigger(boolean cancelTrigger) {
	}
}
