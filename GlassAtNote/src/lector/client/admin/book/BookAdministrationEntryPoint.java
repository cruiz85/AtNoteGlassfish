package lector.client.admin.book;

import java.util.List;
import java.util.Stack;

import lector.client.admin.generalPanels.BotonesStackPanelAdministracionMio;
import lector.client.admin.generalPanels.BotonesStackPanelAdministracionSimple;
import lector.client.admin.generalPanels.PublicPrivatePanelComposite;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.logger.Logger;
import lector.share.model.Language;
import lector.share.model.client.BookClient;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.UserClient;

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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class BookAdministrationEntryPoint implements EntryPoint {

	private static final String BOOK_ADMINISTRATION_NAME = "BookA dministration Menu";

	private static final int NCampos = 4;

	private static String GET_A_BOOK_WELLCOME = "Book Management";
	private static String UPLOAD_A_TEXT = "Upload your own Text";
	private static String GET_A_BOOK = "Get a book from Google Library";
	private static String DELETE_SELECTED_LABEL = "Delete Selected Books";

	private static String GET_A_BOOK_WELLCOME_RESET = "Book Management";
	private static String UPLOAD_A_TEXT_RESET = "Upload your own Text";
	private static String GET_A_BOOK_RESET = "Get a book from Google Library";
	private static String DELETE_SELECTED_LABEL_RESET = "Delete Selected Books";

	private MenuItem GetABookWellcomeMenuItem;
	private MenuItem UploadATextMenuItem;
	private MenuItem GetABookFromGoogleMenuItem;
	private MenuItem DeleteSelectedMenuItem;

	private TextBox GetABookWellcomeMenuItemTextBox;
	private TextBox UploadATextMenuItemTextBox;
	private TextBox GetABookFromGoogleMenuItemTextBox;
	private TextBox DeleteSelectedMenuItemTextBox;

	public static String CONFIRM_REMOVE_BOOK = "Are you sure to remove the book?, The activities asociates to the book will be removed. Book to remove: ";

	private PublicPrivatePanelComposite PanelPublicPrivatePanel;
	private VerticalPanel Selected;
	private VerticalPanel simplePanel;
	protected static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private static Stack<Long> Aborrar;
	private static Long bookToBeRemoved = null;
	private BookAdministrationEntryPoint Yo;
	private DockLayoutPanel PanelFondoGeneral;
	private AbsolutePanel PanelEdicion;
	private RootPanel rootPanel;

	public void onModuleLoad() {
		Yo = this;
		rootPanel = RootPanel.get();
		rootPanel.setSize("100%", "100%");
		rootPanel.setStyleName("Root");

		PanelFondoGeneral = new DockLayoutPanel(Unit.PX);
		PanelFondoGeneral.setStyleName("fondoLogo");
		rootPanel.add(PanelFondoGeneral, 0, 0);
		PanelFondoGeneral.setSize("100%", "100%");

		MenuBar menuBar = new MenuBar(false);
		PanelFondoGeneral.addNorth(menuBar, 25);
		menuBar.setSize("100%", "100%");

		GetABookWellcomeMenuItem = new MenuItem(GET_A_BOOK_WELLCOME, false,
				(Command) null);
		GetABookWellcomeMenuItem
				.setHTML(BookAdministrationEntryPoint.GET_A_BOOK_WELLCOME);
		GetABookWellcomeMenuItem.setEnabled(false);
		menuBar.addItem(GetABookWellcomeMenuItem);

		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);

		UploadATextMenuItem = new MenuItem(UPLOAD_A_TEXT, false, new Command() {

			@Override
			public void execute() {
				Controlador.change2LoadABook();

			}
		});
		UploadATextMenuItem.setHTML(BookAdministrationEntryPoint.UPLOAD_A_TEXT);
		menuBar.addItem(UploadATextMenuItem);

		GetABookFromGoogleMenuItem = new MenuItem(
				BookAdministrationEntryPoint.GET_A_BOOK, false, new Command() {
					public void execute() {
						Controlador.change2Searcher();
					}
				});
		menuBar.addItem(GetABookFromGoogleMenuItem);

		DeleteSelectedMenuItem = new MenuItem(DELETE_SELECTED_LABEL, false,
				new Command() {

					private AsyncCallback<Void> callback;

					public void execute() {
						int SelectedWidgetCount = Selected.getWidgetCount();
						Aborrar = new Stack<Long>();
						for (int i = 0; i < SelectedWidgetCount; i++) {
							BotonesStackPanelAdministracionMio BDPM = (BotonesStackPanelAdministracionMio) Selected
									.getWidget(i);
							if (Window
									.confirm(BookAdministrationEntryPoint.CONFIRM_REMOVE_BOOK
											+ ((BookEntidadObject) BDPM
													.getEntidad()).getBook()
													.getTitle()))
								Aborrar.add(((BookEntidadObject) BDPM
										.getEntidad()).getBook().getId());
						}

						Selected.clear();

						callback = new AsyncCallback<Void>() {

							public void onSuccess(Void result) {
								if (!Aborrar.isEmpty()) {
									bookToBeRemoved = Aborrar.pop();
									bookReaderServiceHolder.deleteBookById(
											bookToBeRemoved, callback);
								}

								else {
									Selected.clear();
									RefreshUserAndBooks();
								}
							}

							public void onFailure(Throwable caught) {
								Window.alert(ErrorConstants.ERROR_REMOVING_BOOK);
								Logger.GetLogger().severe(
										Yo.getClass().toString(),
										ActualState.getUser().toString(),
										ErrorConstants.ERROR_REMOVING_BOOK);

							}
						};

						if (!Aborrar.isEmpty()) {
							bookToBeRemoved = Aborrar.pop();
							((ProfessorClient) ActualState.getUser())
									.getBooks().remove(bookToBeRemoved);
							bookReaderServiceHolder.deleteBookById(
									bookToBeRemoved, callback);
						} else {
							List<Long> ListaIDsLibros = ((ProfessorClient) ActualState
									.getUser()).getBooks();
							if (!ListaIDsLibros.isEmpty())
								refresh(ListaIDsLibros);
						}
					}
				});
		DeleteSelectedMenuItem.setHTML(DELETE_SELECTED_LABEL);
		menuBar.addItem(DeleteSelectedMenuItem);

		MenuItemSeparator separator_1 = new MenuItemSeparator();
		menuBar.addSeparator(separator_1);

		MenuItem mntmNewItem_2 = new MenuItem("Back", false, new Command() {
			public void execute() {
				Controlador.change2Administrator();
			}
		});
		menuBar.addItem(mntmNewItem_2);

		SplitLayoutPanel splitLayoutPanel = new SplitLayoutPanel();
		PanelFondoGeneral.add(splitLayoutPanel);
		splitLayoutPanel.setSize("100%", "100%");

		simplePanel = new VerticalPanel();
		splitLayoutPanel.addWest(simplePanel, 500.0);
		simplePanel.setWidth("100%");

		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2.setSpacing(5);
		simplePanel.add(horizontalPanel_2);
		horizontalPanel_2.setSize("100%", "100%");

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel_2.add(horizontalPanel);
		horizontalPanel.setStyleName("AzulTransparente");
		horizontalPanel.setSpacing(10);
		horizontalPanel.setSize("100%", "100%");

		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setStyleName("BlancoTransparente");
		horizontalPanel.add(horizontalPanel_1);
		horizontalPanel_1.setSpacing(7);
		horizontalPanel_1.setSize("100%", "100%");

		PanelPublicPrivatePanel = new PublicPrivatePanelComposite();
		horizontalPanel_1.add(PanelPublicPrivatePanel);

		SimplePanel simplePanel_1 = new SimplePanel();
		splitLayoutPanel.add(simplePanel_1);
		simplePanel_1.setSize("100%", "100%");

		Selected = new VerticalPanel();
		simplePanel_1.setWidget(Selected);
		Selected.setWidth("100%");

		List<Long> ListaIDsLibros = ((ProfessorClient) ActualState.getUser())
				.getBooks();
		if (!ListaIDsLibros.isEmpty())
			refresh(ListaIDsLibros);

		PanelPublicPrivatePanel.setSize("100%", "100%");
		PanelPublicPrivatePanel.ClearEmpty();

		PanelEdicion = new AbsolutePanel();
		if (ActualState.isLanguageActive())
			closeEditPanel();

	}

	protected void RefreshUserAndBooks() {
		bookReaderServiceHolder.loadUserById(ActualState.getUser().getId(),
				new AsyncCallback<UserClient>() {

					@Override
					public void onSuccess(UserClient result) {
						ActualState.setUser(result);
						List<Long> ListaIDsLibros = ((ProfessorClient) ActualState
								.getUser()).getBooks();
						if (!ListaIDsLibros.isEmpty())
							refresh(ListaIDsLibros);

					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(ErrorConstants.ERROR_LOADING_USER);

					}
				});
	}

	public void refresh(List<Long> listaIDsLibros) {
		// TODO CAMBIAR POR UN CARGADOR DE TODOS LOS LIBROS
		bookReaderServiceHolder.getBookClientsByIds(listaIDsLibros,
				new AsyncCallback<List<BookClient>>() {

					@Override
					public void onSuccess(List<BookClient> result) {

						for (BookClient Book : result) {
							BookEntidadObject E = new BookEntidadObject(Book);
							BotonesStackPanelAdministracionSimple BSPS = new BotonesStackPanelAdministracionSimple(
									E.getName(), PanelPublicPrivatePanel
											.getPrivate(), Selected);
							BSPS.setEntidad(E);
							BSPS.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonCenter");

								}
							});

							BSPS.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonCenterPush");
								}
							});

							BSPS.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonCenter");
								}
							});

							BSPS.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {

									((Button) event.getSource())
											.setStyleName("gwt-ButtonCenterOver");

								}
							});

							BSPS.setStyleName("gwt-ButtonCenter");
							BSPS.setWidth("100%");

							BSPS.addClickHandler(new ClickHandler() {

								@Override
								public void onClick(ClickEvent event) {
									BotonesStackPanelAdministracionSimple BSPM = (BotonesStackPanelAdministracionSimple) event
											.getSource();
									BSPM.setSelected(Selected);
									BSPM.Swap();

								}
							});
						}

						// if (result.size() < 10) {
						// for (BookClient Book : result) {
						// EntidadLibro E = new EntidadLibro(Book);
						// stackPanel_1.addBotonLessTen(E);
						// }
						//
						// } else {
						// for (BookClient Book : result) {
						// EntidadLibro E = new EntidadLibro(Book);
						// stackPanel_1.addBoton(E);
						// }
						// }
						PanelPublicPrivatePanel.ClearEmpty();

					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(ErrorConstants.ERROR_RETRIVING_THE_BOOKS);
						Logger.GetLogger().severe(Yo.getClass().toString(),
								ActualState.getUser().toString(),
								ErrorConstants.ERROR_RETRIVING_THE_BOOKS);

					}
				});
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

				if (!GetABookWellcomeMenuItemTextBox.getText().isEmpty())
					GET_A_BOOK_WELLCOME = GetABookWellcomeMenuItemTextBox
							.getText();
				else
					GET_A_BOOK_WELLCOME = GET_A_BOOK_WELLCOME_RESET;

				if (!UploadATextMenuItemTextBox.getText().isEmpty())
					UPLOAD_A_TEXT = UploadATextMenuItemTextBox.getText();
				else
					UPLOAD_A_TEXT = UPLOAD_A_TEXT_RESET;

				if (!GetABookFromGoogleMenuItemTextBox.getText().isEmpty())
					GET_A_BOOK = GetABookFromGoogleMenuItemTextBox.getText();
				else
					GET_A_BOOK = GET_A_BOOK_RESET;

				if (!DeleteSelectedMenuItemTextBox.getText().isEmpty())
					DELETE_SELECTED_LABEL = DeleteSelectedMenuItemTextBox
							.getText();
				else
					DELETE_SELECTED_LABEL = DELETE_SELECTED_LABEL_RESET;

				PanelPublicPrivatePanel.OpenEditPanelonClick();

				ParsearFieldsAItems();
				SaveChages();
			}
		});

		GetABookWellcomeMenuItemTextBox = new TextBox();
		GetABookWellcomeMenuItemTextBox.setText(GET_A_BOOK_WELLCOME);
		GetABookWellcomeMenuItemTextBox.setSize(
				GetABookWellcomeMenuItem.getOffsetWidth() + "px",
				GetABookWellcomeMenuItem.getOffsetHeight() + "px");
		PanelEdicion.add(GetABookWellcomeMenuItemTextBox,
				GetABookWellcomeMenuItem.getAbsoluteLeft(),
				GetABookWellcomeMenuItem.getAbsoluteTop());

		UploadATextMenuItemTextBox = new TextBox();
		UploadATextMenuItemTextBox.setText(UPLOAD_A_TEXT);
		UploadATextMenuItemTextBox.setSize(UploadATextMenuItem.getOffsetWidth()
				+ "px", UploadATextMenuItem.getOffsetHeight() + "px");
		PanelEdicion.add(UploadATextMenuItemTextBox,
				UploadATextMenuItem.getAbsoluteLeft(),
				UploadATextMenuItem.getAbsoluteTop());

		GetABookFromGoogleMenuItemTextBox = new TextBox();
		GetABookFromGoogleMenuItemTextBox.setText(GET_A_BOOK);
		GetABookFromGoogleMenuItemTextBox.setSize(
				GetABookFromGoogleMenuItem.getOffsetWidth() + "px",
				GetABookFromGoogleMenuItem.getOffsetHeight() + "px");
		PanelEdicion.add(GetABookFromGoogleMenuItemTextBox,
				GetABookFromGoogleMenuItem.getAbsoluteLeft(),
				GetABookFromGoogleMenuItem.getAbsoluteTop());

		DeleteSelectedMenuItemTextBox = new TextBox();
		DeleteSelectedMenuItemTextBox.setText(DELETE_SELECTED_LABEL);
		DeleteSelectedMenuItemTextBox.setSize(
				DeleteSelectedMenuItem.getOffsetWidth() + "px",
				DeleteSelectedMenuItem.getOffsetHeight() + "px");
		PanelEdicion.add(DeleteSelectedMenuItemTextBox,
				DeleteSelectedMenuItem.getAbsoluteLeft(),
				DeleteSelectedMenuItem.getAbsoluteTop());

		PanelPublicPrivatePanel.OpenEditPanel(PanelEdicion);

	}

	protected void SaveChages() {
		Language LanguageActual = ActualState.getActualLanguage();
		String BookAdministrationEntryPointLanguageConfiguration = toFile();
		LanguageActual
				.setBookAdministrationEntryPointLanguageConfiguration(BookAdministrationEntryPointLanguageConfiguration);
		ActualState.saveLanguageActual(LanguageActual);
		PanelPublicPrivatePanel.SaveChages();
	}

	public void ParsearFieldsAItems() {
		GetABookWellcomeMenuItem.setHTML(GET_A_BOOK_WELLCOME);
		UploadATextMenuItem.setHTML(UPLOAD_A_TEXT);
		GetABookFromGoogleMenuItem.setText(GET_A_BOOK);
		DeleteSelectedMenuItem.setText(DELETE_SELECTED_LABEL);
		PanelPublicPrivatePanel.ParsearFieldsAItems();

	}

	public static String toFile() {
		StringBuffer SB = new StringBuffer();
		SB.append(GET_A_BOOK_WELLCOME + "\r\n");
		SB.append(UPLOAD_A_TEXT + "\r\n");
		SB.append(GET_A_BOOK + "\r\n");
		SB.append(DELETE_SELECTED_LABEL + "\r\n");
		return SB.toString();
	}

	public static void FromFile(String Entrada) {
		String[] Lista = Entrada.split("\r\n");
		if (Lista.length >= NCampos) {
			if (!Lista[0].isEmpty())
				GET_A_BOOK_WELLCOME = Lista[0];
			else
				GET_A_BOOK_WELLCOME = GET_A_BOOK_WELLCOME_RESET;
			if (!Lista[1].isEmpty())
				UPLOAD_A_TEXT = Lista[1];
			else
				UPLOAD_A_TEXT = UPLOAD_A_TEXT_RESET;
			if (!Lista[2].isEmpty())
				GET_A_BOOK = Lista[2];
			else
				GET_A_BOOK = GET_A_BOOK_RESET;
			if (!Lista[3].isEmpty())
				DELETE_SELECTED_LABEL = Lista[3];
			else
				DELETE_SELECTED_LABEL = DELETE_SELECTED_LABEL_RESET;
			// TODO METE EL FRomfile en la actual state DEL PublicPrivatePanel
		} else
			Logger.GetLogger().severe(
					BookAdministrationEntryPoint.class.toString(),
					ActualState.getUser().toString(),
					ErrorConstants.ERROR_LOADING_LANGUAGE_IN
							+ BOOK_ADMINISTRATION_NAME);
		ParsearFieldsAItemsRESET();
	}
	
	private static void ParsearFieldsAItemsRESET() {
		GET_A_BOOK_WELLCOME = GET_A_BOOK_WELLCOME_RESET;
		UPLOAD_A_TEXT = UPLOAD_A_TEXT_RESET;
		GET_A_BOOK = GET_A_BOOK_RESET;
		DELETE_SELECTED_LABEL = DELETE_SELECTED_LABEL_RESET;
		
	}

}
