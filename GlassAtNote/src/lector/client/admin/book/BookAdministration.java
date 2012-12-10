package lector.client.admin.book;

import java.util.List;
import java.util.Stack;

import lector.client.admin.generalPanels.BotonesStackPanelAdministracionMio;
import lector.client.admin.generalPanels.BotonesStackPanelAdministracionSimple;
import lector.client.admin.generalPanels.PublicPrivatePanel;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.StackPanelMio;
import lector.client.controler.CalendarNow;
import lector.client.controler.Controlador;
import lector.client.controler.ErrorConstants;
import lector.client.logger.Logger;
import lector.client.login.ActualUser;
import lector.share.model.client.BookClient;
import lector.share.model.client.ProfessorClient;

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
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class BookAdministration implements EntryPoint {

	private static final String GET_A_BOOK = "Get a book from Google Library";
	private static final String UPLOAD_A_TEXT = "Upload your own Text";
	private static final String GET_A_BOOK_WELLCOME = "Book Management";
	private PublicPrivatePanel stackPanel_1;
	private VerticalPanel Selected;
	private VerticalPanel simplePanel;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private static Stack<Long> Aborrar;
	private static Long bookToBeRemoved = null;
	private BookAdministration Yo;

	public void onModuleLoad() {
		Yo=this;
		RootPanel RootTXOriginal = RootPanel.get();
		RootPanel RootMenu = RootPanel.get("Menu");
		RootTXOriginal.setSize("100%", "100%");
		RootMenu.setStyleName("Root2");
		RootTXOriginal.setStyleName("Root");

		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);
		menuBar.setSize("100%", "");

		MenuItem mntmNewItem = new MenuItem("New item", false, (Command) null);
		mntmNewItem.setHTML(BookAdministration.GET_A_BOOK_WELLCOME);
		mntmNewItem.setEnabled(false);
		menuBar.addItem(mntmNewItem);

		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		
		MenuItem mntmNewItem2 = new MenuItem("New item", false, new Command(){

			@Override
			public void execute() {
				Controlador.change2LoadABook();
				
			}});
		mntmNewItem2.setHTML(BookAdministration.UPLOAD_A_TEXT);
		menuBar.addItem(mntmNewItem2);


		MenuItem mntmAddbook = new MenuItem(BookAdministration.GET_A_BOOK, false, new Command() {
			public void execute() {
				Controlador.change2Searcher();
			}
		});
		menuBar.addItem(mntmAddbook);

		MenuItem mntmNewItem_1 = new MenuItem("New item", false, new Command() {

			private AsyncCallback<Void> callback;

			public void execute() {
				int SelectedWidgetCount = Selected.getWidgetCount();
				Aborrar = new Stack<Long>();
				for (int i = 0; i < SelectedWidgetCount; i++) {
					BotonesStackPanelAdministracionMio BDPM = (BotonesStackPanelAdministracionMio) Selected
							.getWidget(i);
					Aborrar.add(((EntidadLibro) BDPM.getEntidad()).getBook()
							.getId());
					// ActualUser.getUser().getBookIds().remove(BDPM.getText());
				}

				Selected.clear();

				callback = new AsyncCallback<Void>() {

					public void onSuccess(Void result) {
						if (!Aborrar.isEmpty()) {
							bookToBeRemoved = Aborrar.pop();
							bookReaderServiceHolder.deleteBookById(
									bookToBeRemoved, callback);
						}

						else
							Selected.clear();
					}

					public void onFailure(Throwable caught) {
						Window.alert(ErrorConstants.ERROR_REMOVING_BOOK);
						Logger.GetLogger()
						.severe(Yo.getClass().toString(),
								ErrorConstants.ERROR_REMOVING_BOOK + " at " + CalendarNow.GetDateNow() + 
								" by User " + ActualUser.getUser().getEmail());

					}
				};

				if (!Aborrar.isEmpty()) {
					bookToBeRemoved = Aborrar.pop();
					((ProfessorClient) ActualUser.getUser()).getBooks().remove(
							bookToBeRemoved);
					bookReaderServiceHolder.deleteBookById(bookToBeRemoved,
							callback);
				}

				// bookReaderServiceHolder.saveUser(ActualUser.getUser(),
				// new AsyncCallback<Boolean>() {
				//
				// public void onSuccess(Boolean result) {
				// Selected.clear();
				//
				// }
				//
				// public void onFailure(Throwable caught) {
				// Window.alert("There has been an erron when trying to remove the books of the user");
				// }
				// });
			}
		});
		mntmNewItem_1.setHTML("Remove");
		menuBar.addItem(mntmNewItem_1);

		MenuItemSeparator separator_1 = new MenuItemSeparator();
		menuBar.addSeparator(separator_1);

		MenuItem mntmNewItem_2 = new MenuItem("Back", false, new Command() {
			public void execute() {
				Controlador.change2Administrator();
			}
		});
		menuBar.addItem(mntmNewItem_2);

		SplitLayoutPanel splitLayoutPanel = new SplitLayoutPanel();
		splitLayoutPanel.setStyleName("fondoLogo");
		RootTXOriginal.add(splitLayoutPanel, 0, 25);
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

		stackPanel_1 = new PublicPrivatePanel();
		horizontalPanel_1.add(stackPanel_1);
		
		SimplePanel simplePanel_1 = new SimplePanel();
		splitLayoutPanel.add(simplePanel_1);
		simplePanel_1.setSize("100%", "100%");

		Selected = new VerticalPanel();
		simplePanel_1.setWidget(Selected);
		Selected.setWidth("100%");
		
//		stackPanel_1.setBotonTipo(new BotonesStackPanelAdministracionSimple(
//				"prototipo", new VerticalPanel(), Selected));
//		stackPanel_1.setBotonClick(new ClickHandler() {
//
//			public void onClick(ClickEvent event) {
//				
//				BotonesStackPanelAdministracionMio BSPM = (BotonesStackPanelAdministracionMio) event.getSource();
//				BSPM.Swap();
////				BotonesStackPanelAdministracionSimple BSPMB= new BotonesStackPanelAdministracionSimple(BSPM.getHTML(),BSPM.getNormal(),BSPM.getSelected());
////				BSPM).Swap();
//			}
//		});
		
		
		List<Long> ListaIDsLibros = ((ProfessorClient) ActualUser.getUser())
				.getBooks();
		if (!ListaIDsLibros.isEmpty())
			//TODO CAMBIAR POR UN CARGADOR DE TODOS LOS LIBROS
			bookReaderServiceHolder.getBookClientsByIds(ListaIDsLibros,
					new AsyncCallback<List<BookClient>>() {

						@Override
						public void onSuccess(List<BookClient> result) {
							
							for (BookClient Book : result) {
								EntidadLibro E = new EntidadLibro(Book);
								BotonesStackPanelAdministracionSimple BSPS=new BotonesStackPanelAdministracionSimple(E.getName(), stackPanel_1.getPrivate(), Selected);
								BSPS.setEntidad(E);
								BSPS.addClickHandler(new ClickHandler() {
									
									public void onClick(ClickEvent event) {
										((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
										
									}
								});
								
								BSPS.addMouseDownHandler(new MouseDownHandler() {
										public void onMouseDown(MouseDownEvent event) {
											((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
										}});
								
								BSPS.addMouseOutHandler(new MouseOutHandler() {
									public void onMouseOut(MouseOutEvent event) {
										((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
								}});
								

								BSPS.addMouseOverHandler(new MouseOverHandler() {
									public void onMouseOver(MouseOverEvent event) {
										
										((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
									
								}});
								
								BSPS.setStyleName("gwt-ButtonCenter");
								BSPS.setWidth("100%");
								
								BSPS.addClickHandler(new ClickHandler() {
									
									@Override
									public void onClick(ClickEvent event) {
										BotonesStackPanelAdministracionSimple BSPM = (BotonesStackPanelAdministracionSimple) event.getSource();
										BSPM.setSelected(Selected);
										BSPM.Swap();
										
										
										
										
									}
								});
							}
							
//							if (result.size() < 10) {
//								for (BookClient Book : result) {
//									EntidadLibro E = new EntidadLibro(Book);
//									stackPanel_1.addBotonLessTen(E);
//								}
//
//							} else {
//								for (BookClient Book : result) {
//									EntidadLibro E = new EntidadLibro(Book);
//									stackPanel_1.addBoton(E);
//								}
//							}
							stackPanel_1.ClearEmpty();

						}

						@Override
						public void onFailure(Throwable caught) {
							Window.alert(ErrorConstants.ERROR_RETRIVING_THE_BOOKS);
							Logger.GetLogger()
							.severe(Yo.getClass().toString(),
									ErrorConstants.ERROR_RETRIVING_THE_BOOKS + " at " + CalendarNow.GetDateNow() + 
									" by User " + ActualUser.getUser().getEmail());

						}
					});
		
		stackPanel_1.setSize("100%", "100%");
		stackPanel_1.ClearEmpty();
		
	}
}
