package lector.client.admin.admins;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import lector.client.admin.generalPanels.BotonesStackPanelAdminsMio;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.StackPanelMio;
import lector.client.controler.CalendarNow;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.logger.Logger;
import lector.client.login.ActualUser;
import lector.client.reader.LoadingPanel;
import lector.share.model.UserApp;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.StudentClient;
import lector.share.model.client.UserClient;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

public class NewAdminAdministrator implements EntryPoint {
	private StackPanelMio stackPanel_1;
	private Stack<ProfessorClient> Pilallamada;
	private Button SaveAdmins;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	NewAdminAdministrator Yo;
	private TextBox FirstName;
	private TextBox lastName;
	private TextBox email;
	private PasswordTextBox PaswordA;
	private PasswordTextBox PasswordB;
	private Image Verificado;

	public void onModuleLoad() {

		Yo = this;
		RootPanel RootTXOriginal = RootPanel.get();
		RootPanel RootMenu = RootPanel.get("Menu");
		RootTXOriginal.setSize("100%", "100%");
		RootMenu.setStyleName("Root");
		RootTXOriginal.setStyleName("Root");

		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);
		menuBar.setWidth("100%");

		MenuItem menuItem = new MenuItem(" ", false, (Command) null);
		menuItem.setHTML("Administrators");
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

		SimplePanel simplePanel_1 = new SimplePanel();
		simplePanel_1.setStyleName("fondoLogo");
		RootTXOriginal.add(simplePanel_1, 0, 25);
		simplePanel_1.setSize("100%", "95%");

		HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
		horizontalSplitPanel.setStyleName("fondoLogo");
		simplePanel_1.setWidget(horizontalSplitPanel);
		horizontalSplitPanel.setSize("100%", "100%");

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setSpacing(12);
		horizontalSplitPanel.setLeftWidget(verticalPanel);
		verticalPanel.setSize("100%", "");

		VerticalPanel verticalPanel_2 = new VerticalPanel();
		verticalPanel_2.setSpacing(3);
		verticalPanel_2.setStyleName("AzulTransparente");
		verticalPanel.add(verticalPanel_2);
		verticalPanel_2.setWidth("100%");

		HorizontalPanel horizontalPanel_6 = new HorizontalPanel();
		horizontalPanel_6.setStyleName("BlancoTransparente");
		verticalPanel_2.add(horizontalPanel_6);
		horizontalPanel_6.setWidth("100%");

		Label lblNewLabel = new Label("Add New User");
		lblNewLabel.setStyleName("TituloLogin");
		horizontalPanel_6.add(lblNewLabel);

		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_1
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_1.setStyleName("BlancoTransparente");
		horizontalPanel_1.setSpacing(10);
		verticalPanel_2.add(horizontalPanel_1);
		horizontalPanel_1.setWidth("");

		Label lblFirstname = new Label("First Name");
		horizontalPanel_1.add(lblFirstname);
		lblFirstname.setStyleName("gwt-LabelMargen2px");
		lblFirstname.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblFirstname.setSize("107px", "");

		FirstName = new TextBox();
		horizontalPanel_1.add(FirstName);

		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_2.setStyleName("BlancoTransparente");
		horizontalPanel_2.setSpacing(10);
		horizontalPanel_2
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_2.add(horizontalPanel_2);
		horizontalPanel_2.setWidth("");

		Label lblLastname = new Label("Last Name");
		horizontalPanel_2.add(lblLastname);
		lblLastname.setStyleName("gwt-LabelMargen2px");
		lblLastname.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblLastname.setSize("107px", "");

		lastName = new TextBox();
		horizontalPanel_2.add(lastName);

		HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
		horizontalPanel_3
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_3.setStyleName("BlancoTransparente");
		horizontalPanel_3.setSpacing(10);
		horizontalPanel_3
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_2.add(horizontalPanel_3);
		horizontalPanel_3.setWidth("");

		Label lblEmail = new Label("Email");
		horizontalPanel_3.add(lblEmail);
		lblEmail.setStyleName("gwt-LabelMargen2px");
		lblEmail.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblEmail.setSize("107px", "");

		email = new TextBox();
		horizontalPanel_3.add(email);

		HorizontalPanel horizontalPanel_4 = new HorizontalPanel();
		horizontalPanel_4
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_4.setStyleName("BlancoTransparente");
		horizontalPanel_4.setSpacing(10);
		horizontalPanel_4
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_2.add(horizontalPanel_4);
		horizontalPanel_4.setWidth("");

		Label lblPassword_1 = new Label("Enter Password");
		horizontalPanel_4.add(lblPassword_1);
		lblPassword_1.setStyleName("gwt-LabelMargen2px");
		lblPassword_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblPassword_1.setSize("107px", "");

		PaswordA = new PasswordTextBox();
		horizontalPanel_4.add(PaswordA);
		PaswordA.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if (PaswordA.getText().equals(PasswordB.getText()))
					Verificado.setVisible(true);
				else
					Verificado.setVisible(false);
			}
		});

		HorizontalPanel horizontalPanel_5 = new HorizontalPanel();
		horizontalPanel_5
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_5.setStyleName("BlancoTransparente");
		horizontalPanel_5.setSpacing(10);
		horizontalPanel_5
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_2.add(horizontalPanel_5);
		horizontalPanel_5.setWidth("");

		Label lblRepeatPassword = new Label("Repeat Password");
		horizontalPanel_5.add(lblRepeatPassword);
		lblRepeatPassword.setStyleName("gwt-LabelMargen2px");
		lblRepeatPassword
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblRepeatPassword.setSize("107px", "");

		PasswordB = new PasswordTextBox();
		horizontalPanel_5.add(PasswordB);

		Verificado = new Image("Free.gif");
		horizontalPanel_5.add(Verificado);

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(10);
		horizontalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_2.add(horizontalPanel);
		horizontalPanel.setWidth("100%");

		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setSpacing(5);
		verticalPanel_1.setStyleName("BlancoTransparente");
		verticalPanel_1
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.add(verticalPanel_1);
		verticalPanel_1.setWidth("291px");

		VerticalPanel verticalPanel_3 = new VerticalPanel();
		verticalPanel_1.add(verticalPanel_3);

		SaveAdmins = new Button("Save New Administrator");
		verticalPanel_3.add(SaveAdmins);
		SaveAdmins.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});

		SaveAdmins.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});

		SaveAdmins.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");

			}
		});

		SaveAdmins.setStyleName("gwt-ButtonCenter");

		VerticalPanel verticalPanel_4 = new VerticalPanel();
		verticalPanel_4.setStyleName("BlancoTransparente");
		verticalPanel_4.setSpacing(6);
		horizontalSplitPanel.setRightWidget(verticalPanel_4);
		verticalPanel_4.setSize("100%", "100%");

		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setStyleName("BlancoTransparente");
		verticalPanel_4.add(scrollPanel);

		stackPanel_1 = new StackPanelMio();
		scrollPanel.setWidget(stackPanel_1);
		// simplePanel.add(stackPanel_1);

		stackPanel_1.setBotonTipo(new BotonesStackPanelAdminsMio(
				new EntidadAdmin(new ProfessorClient("prototipo")),
				new VerticalPanel()));
		stackPanel_1.setBotonClick(new ClickHandler() {

			private ProfessorClient Ident;

			public void onClick(ClickEvent event) {

				Ident = ((EntidadAdmin) ((BotonesStackPanelAdminsMio) event
						.getSource()).getEntidad()).getAdmin();
				// if
				// ((ActualUser.getUser().getName()!=null)&&(!ActualUser.getUser().getName().isEmpty()))
				// Name=((BotonesStackPanelAdminsMio)
				// event.getSource()).getEntidad().get;
				// else Name=((BotonesStackPanelAdminsMio)
				// event.getSource()).getEntidad().getName();
				if (Window.confirm(InformationConstants.ARE_YOU_SURE_DELETE_ADMIN
						+ Ident.getFirstName()
						+ " "
						+ Ident.getLastName()
						+ InformationConstants.ARE_YOU_SURE_DELETE_ADMIN2)) {

					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto("Loading...");

					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto("Deleting...");

					bookReaderServiceHolder.deleteProfessorById(Ident.getId(),
							new AsyncCallback<Void>() {

								public void onFailure(Throwable caught) {
									Window.alert(ErrorConstants.ERROR_USER_CAN_NOT_BE_REMOVED);

								}

								public void onSuccess(Void result) {
									// Salir si me borro a mi mismo
									Logger.GetLogger().info(
											Yo.getClass().toString(),
											"New User deleted:  "
													+ "First Name: "
													+ Ident.getFirstName()
													+ " Last Name: "
													+ Ident.getLastName()
													+ " Email: "
													+ Ident.getEmail()
													+ " ID: "
													+ Ident.getId()
													+ " by "
													+ " at " + CalendarNow.GetDateNow()
													+ " Deleted By :" +
													ActualUser.getUser().getFirstName()
													+ " " + ActualUser.getUser().getLastName()
													+ " " + ActualUser.getUser().getEmail());
									if (ActualUser.getUser().getId()
											.equals(Ident.getId())) {
										ActualUser.setUser(null);
										ActualUser.setBook(null);
										ActualUser.setReadingactivity(null);
										Window.alert(InformationConstants.GOODBYE);
										Window.Location.reload();
									}
									refreshPanel();
								}
							});

				}
			}
		});

		stackPanel_1.setSize("100%", "100%");

		SaveAdmins.addClickHandler(new ClickHandler() {

			private ProfessorClient newStudent;

			public void onClick(ClickEvent event) {
				SaveAdmins.setEnabled(false);
				if (!PaswordA.getText().equals(PasswordB.getText())) {
					PaswordA.setText("");
					PasswordB.setText("");
					Window.alert(ErrorConstants.ERROR_PASSWORDS_NOT_MATCH);
				} else if (FirstName.getText().isEmpty()) {
					Window.alert(ErrorConstants.FIRST_NAME_IS_EMPTY);
				} else if (lastName.getText().isEmpty()) {
					Window.alert(ErrorConstants.LAST_NAME_IS_EMPTY);
				} else if (!isValidEmail(email.getText())) {
					Window.alert(ErrorConstants.IT_IS_NOT_A_EMAIL);
				} else {
					ProfessorClient UC = new ProfessorClient();
					UC.setFirstName(FirstName.getText());
					UC.setLastName(lastName.getText());
					UC.setEmail(email.getText().toLowerCase());
					UC.setPassword(PaswordA.getText());
					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto(
							InformationConstants.SAVING);
					newStudent = UC;
					bookReaderServiceHolder.saveUser(UC,
							new AsyncCallback<Void>() {

								@Override
								public void onSuccess(Void result) {
									LoadingPanel.getInstance().hide();
									Logger.GetLogger().info(
											Yo.getClass().toString(),
											"New User created:  "
													+ "First Name: "
													+ newStudent.getFirstName()
													+ " Last Name: "
													+ newStudent.getLastName()
													+ " Email: "
													+ newStudent.getEmail()  + " at " + CalendarNow.GetDateNow()
													+ " Created By :" +
													ActualUser.getUser().getFirstName()
													+ " " + ActualUser.getUser().getLastName()
													+ " " + ActualUser.getUser().getEmail()) ;
									refreshPanel();
									SaveAdmins.setEnabled(true);
								}

								@Override
								public void onFailure(Throwable caught) {
									SaveAdmins.setEnabled(true);
									LoadingPanel.getInstance().hide();
									Window.alert(ErrorConstants.ERROR_IN_REGISTERATION  + " at " + CalendarNow.GetDateNow());
									Logger.GetLogger()
											.severe(Yo.getClass().toString(),
													ErrorConstants.ERROR_IN_REGISTERATION  + " at " + CalendarNow.GetDateNow());
								}
							});
				}
			}

			private native boolean isValidEmail(String email) /*-{ 
																var reg1 = /(@.*@)|(\.\.)|(@\.)|(\.@)|(^\.)/; // not valid 
																var reg2 = /^.+\@(\[?)[a-zA-Z0-9\-\.]+\.([a-zA-Z]{2,3}|[0-9]{1,3})(\]?)$/; // valid 
																return !reg1.test(email) && reg2.test(email); 
																}-*/;

		});

		Verificado.setVisible(false);
		PasswordB.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if (PaswordA.getText().equals(PasswordB.getText()))
					Verificado.setVisible(true);
				else
					Verificado.setVisible(false);
			}
		});

		// Profesores
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto(InformationConstants.LOADING);
		bookReaderServiceHolder
				.getProfessors(new AsyncCallback<List<ProfessorClient>>() {

					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
					}

					public void onSuccess(List<ProfessorClient> result) {
						LoadingPanel.getInstance().hide();
						if (result.size() < 10) {
							for (ProfessorClient User1 : result) {

								EntidadAdmin E = new EntidadAdmin(User1);
								stackPanel_1.addBotonLessTen(E);
							}

						} else {
							for (ProfessorClient User1 : result) {
								EntidadAdmin E = new EntidadAdmin(User1);
								stackPanel_1.addBoton(E);
							}
						}
						stackPanel_1.setSize("100%", "100%");
						stackPanel_1.ClearEmpty();

					}
				});
	}

	protected void refreshPanel() {
		stackPanel_1.Clear();
		// Profesores
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto(InformationConstants.LOADING);
		bookReaderServiceHolder
				.getProfessors(new AsyncCallback<List<ProfessorClient>>() {

					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert(ErrorConstants.ERROR_LOADING_USERS);
						Logger.GetLogger().info(Yo.getClass().toString(),
								ErrorConstants.ERROR_LOADING_USERS
										+ " at " + CalendarNow.GetDateNow());
					}

					public void onSuccess(List<ProfessorClient> result) {
						LoadingPanel.getInstance().hide();
						if (result.size() < 10) {
							for (ProfessorClient User1 : result) {

								EntidadAdmin E = new EntidadAdmin(User1);
								stackPanel_1.addBotonLessTen(E);
							}

						} else {
							for (ProfessorClient User1 : result) {
								EntidadAdmin E = new EntidadAdmin(User1);
								stackPanel_1.addBoton(E);
							}
						}
						stackPanel_1.setSize("100%", "100%");
						stackPanel_1.ClearEmpty();

					}
				});

	}

}
