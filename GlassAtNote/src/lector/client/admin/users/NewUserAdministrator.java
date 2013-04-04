package lector.client.admin.users;

import java.util.List;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.CalendarNow;
import lector.client.controler.Controlador;
import lector.client.controler.ConstantsError;
import lector.client.controler.ConstantsInformation;
import lector.client.controler.catalogo.StackPanelMio;
import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;
import lector.share.model.client.StudentClient;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class NewUserAdministrator implements EntryPoint {
	private StackPanelMio stackPanel_1;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private NewUserAdministrator Yo;

	public void onModuleLoad() {
		RootPanel RootTXOriginal = RootPanel.get();
		RootPanel RootMenu = RootPanel.get("Menu");
		RootTXOriginal.setSize("100%", "100%");
		RootMenu.setStyleName("Root");
		RootTXOriginal.setStyleName("Root");

		Yo = this;
		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);
		menuBar.setWidth("100%");

		MenuItem menuItem = new MenuItem("GroupAdministration", false,
				(Command) null);
		menuItem.setHTML("Users Administrator");
		menuItem.setEnabled(false);
		menuBar.addItem(menuItem);

		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);

		MenuItem mntmBack = new MenuItem("Back", false, new Command() {
			public void execute() {
				Controlador.change2Administrator();
			}
		});
		menuBar.addItem(mntmBack);

		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto("Loading...");
		bookReaderServiceHolder
				.getStudents(new AsyncCallback<List<StudentClient>>() {

					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert(ConstantsError.ERROR_RETRIVING_USERS);
						Logger.GetLogger().severe(
								Yo.getClass().toString(),
								ActualState.getUser().toString(),
								ConstantsError.ERROR_RETRIVING_USERS);

					}

					public void onSuccess(List<StudentClient> result) {
						LoadingPanel.getInstance().hide();
						if (result.size() < 10) {
							for (StudentClient User1 : result) {
								EntidadUser E = new EntidadUser(User1);
								stackPanel_1.addBotonLessTen(E);
							}

						} else {
							for (StudentClient User1 : result) {
								EntidadUser E = new EntidadUser(User1);
								stackPanel_1.addBoton(E);
							}
						}
						stackPanel_1.setSize("100%", "100%");
						stackPanel_1.ClearEmpty();

					}
				});

		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setStyleName("fondoLogo");
		RootTXOriginal.add(horizontalPanel_1, 0, 25);
		horizontalPanel_1.setSize("100%", "97%");

		HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
		horizontalPanel_1.add(horizontalSplitPanel);
		horizontalSplitPanel.setSplitPosition("500px");
		horizontalSplitPanel.setSize("100%", "100%");

		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2.setStyleName("BlancoTransparente");
		horizontalSplitPanel.setLeftWidget(horizontalPanel_2);
		horizontalPanel_2.setSize("100%", "100%");

		ScrollPanel scrollPanel = new ScrollPanel();
		horizontalPanel_2.add(scrollPanel);

		stackPanel_1 = new StackPanelMio();
		scrollPanel.setWidget(stackPanel_1);
		// simplePanel.add(stackPanel_1);

		stackPanel_1.setBotonTipo(new BotonesStackPanelUsersMio(
				new EntidadUser(new StudentClient("prototipo")),
				new VerticalPanel()));
		stackPanel_1.setBotonClick(new ClickHandler() {

			private StudentClient Ident;

			public void onClick(ClickEvent event) {
				Ident = ((EntidadUser) ((BotonesStackPanelUsersMio) event
						.getSource()).getEntidad()).getStudent();
				// if
				// ((ActualUser.getUser().getName()!=null)&&(!ActualUser.getUser().getName().isEmpty()))
				// Name=((BotonesStackPanelAdminsMio)
				// event.getSource()).getEntidad().get;
				// else Name=((BotonesStackPanelAdminsMio)
				// event.getSource()).getEntidad().getName();
				if (Window
						.confirm(ConstantsInformation.ARE_YOU_SURE_DELETE_USER
								+ Ident.getFirstName()
								+ " "
								+ Ident.getLastName()
								+ ConstantsInformation.ARE_YOU_SURE_DELETE_USER2)) {
					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto("Loading...");

					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto("Deleting...");

					bookReaderServiceHolder.deleteStudentById(Ident.getId(),
							new AsyncCallback<Void>() {

								public void onFailure(Throwable caught) {
									Window.alert(ConstantsError.ERROR_USER_CAN_NOT_BE_REMOVED);
									Logger.GetLogger().severe(
											Yo.getClass().toString(),
											ActualState.getUser().toString(),
											ConstantsError.ERROR_USER_CAN_NOT_BE_REMOVED);

								}

								public void onSuccess(Void result) {
									Logger.GetLogger().info(
											Yo.getClass().toString(),
											ActualState.getUser().toString(),
											"User deleted:  "
													+ Ident.toString());

									refreshPanel();
								}
							});

				}

			}
		});

		stackPanel_1.setSize("100%", "100%");
	}

	protected void refreshPanel() {
		stackPanel_1.Clear();
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto("Loading...");
		bookReaderServiceHolder
				.getStudents(new AsyncCallback<List<StudentClient>>() {

					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();

					}

					public void onSuccess(List<StudentClient> result) {
						LoadingPanel.getInstance().hide();
						if (result.size() < 10) {
							for (StudentClient User1 : result) {
								EntidadUser E = new EntidadUser(User1);
								stackPanel_1.addBotonLessTen(E);
							}

						} else {
							for (StudentClient User1 : result) {
								EntidadUser E = new EntidadUser(User1);
								stackPanel_1.addBoton(E);
							}
						}
						stackPanel_1.setSize("100%", "100%");
						stackPanel_1.ClearEmpty();

					}
				});

		stackPanel_1.setSize("100%", "100%");

	}

	


}
