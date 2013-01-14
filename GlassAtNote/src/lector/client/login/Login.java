package lector.client.login;

import java.util.Date;

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
import lector.share.model.NotAuthenticatedException;
import lector.share.model.UserNotFoundException;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.StudentClient;
import lector.share.model.client.UserClient;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.CheckBox;

public class Login implements EntryPoint {

	private TextBox User;
	private GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private Button btnNewButton;
	private PasswordTextBox passwordLogin;
	private Button buttonRegister;
	private PasswordTextBox PasswordB;
	private PasswordTextBox PaswordA;
	private Image Verificado;
	private TextBox lastName;
	private TextBox email;
	private TextBox FirstName;
	private Login Yo;
	private UserClient newStudent;
	private CheckBox KeepConected;
	private final long SemillaPrimo=999983;
//	private AbsolutePanel PanelEdicion;
	private DockPanel dockPanel;
	private RootPanel rootPanel;

	public void onModuleLoad() {

		String UserCookie=Cookies.getCookie(Constants.COOKIE_NAME);
		if (UserCookie!=null)
			{
			Long L=(Long.parseLong(UserCookie)/(CalendarNow.GetDateNowInt()*SemillaPrimo));
			LoadingPanel.getInstance().setLabelTexto(
					InformationConstants.LOADING);
			LoadingPanel.getInstance().center();
			bookReaderServiceHolder.loadUserById(L, new AsyncCallback<UserClient>() {
				
				@Override
				public void onSuccess(UserClient result) {
					LoadingPanel.getInstance().hide();
					ActualState.setUser(result);
					Logger.GetLogger().info(
							this.getClass().getName(),
							ActualState.getUser().toString(),
							"Log In");
					if (result instanceof StudentClient)
						Controlador.change2MyActivities();
					else if (result instanceof ProfessorClient)
						Controlador.change2Administrator();
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					LoadingPanel.getInstance().hide();
					}
			});
			}
		
		rootPanel = RootPanel.get();
		rootPanel.setStyleName("Root");
		Yo = this;

		dockPanel = new DockPanel();
		dockPanel.setStyleName("fondoLogo");
		rootPanel.add(dockPanel, 0, 0);
		dockPanel.setSize("100%", "100%");

		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		dockPanel.add(horizontalPanel_1, DockPanel.CENTER);
		horizontalPanel_1.setSize("100%", "100%");

		HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
		horizontalPanel_1.add(horizontalPanel_3);
		horizontalPanel_3.setSize("100%", "100%");

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setStyleName("AzulTransparente");
		horizontalPanel_3.add(verticalPanel);
		verticalPanel.setSize("100%", "");

		SimplePanel simplePanel = new SimplePanel();
		simplePanel.setStyleName("BlancoTransparente");
		verticalPanel.add(simplePanel);

		Label lblNewLabel = new Label("Log In");
		simplePanel.setWidget(lblNewLabel);
		lblNewLabel.setStyleName("TituloLogin");

		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel.add(verticalPanel_1);

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel_1.add(horizontalPanel);
		horizontalPanel.setSpacing(10);

		VerticalPanel verticalPanel_5 = new VerticalPanel();
		verticalPanel_5.setStyleName("BlancoTransparente");
		verticalPanel_5
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_5.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.add(verticalPanel_5);
		verticalPanel_5.setSize("67px", "28px");

		Label lblNewLabel_1 = new Label("User");
		lblNewLabel_1.setStyleName("gwt-LabelMargen2px");
		verticalPanel_5.add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		horizontalPanel.setCellHorizontalAlignment(lblNewLabel_1,
				HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setCellVerticalAlignment(lblNewLabel_1,
				HasVerticalAlignment.ALIGN_MIDDLE);
		lblNewLabel_1.setSize("", "");

		User = new TextBox();
		horizontalPanel.add(User);
		User.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode()) {
					btnNewButton.setEnabled(false);
					revisarTextboxAndEnter();
				}
			}

		});

		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2.setSpacing(10);
		verticalPanel_1.add(horizontalPanel_2);

		VerticalPanel verticalPanel_6 = new VerticalPanel();
		verticalPanel_6.setStyleName("BlancoTransparente");
		verticalPanel_6.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_6
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_2.add(verticalPanel_6);
		verticalPanel_6.setHeight("28px");

		Label lblPassword = new Label("Password");
		lblPassword.setStyleName("gwt-LabelMargen2px");
		verticalPanel_6.add(lblPassword);

		passwordLogin = new PasswordTextBox();
		horizontalPanel_2.add(passwordLogin);
		
		HorizontalPanel horizontalPanel_13 = new HorizontalPanel();
		horizontalPanel_13.setSpacing(10);
		verticalPanel_1.add(horizontalPanel_13);
		
		HorizontalPanel horizontalPanel_14 = new HorizontalPanel();
		horizontalPanel_14.setStyleName("BlancoTransparente");
		horizontalPanel_13.add(horizontalPanel_14);
		
		KeepConected = new CheckBox("Stay signed in");
		horizontalPanel_14.add(KeepConected);

		HorizontalPanel horizontalPanel_4 = new HorizontalPanel();
		horizontalPanel_4
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_4
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.add(horizontalPanel_4);
		horizontalPanel_4.setWidth("100%");
		VerticalPanel verticalPanel_4 = new VerticalPanel();
		horizontalPanel_4.add(verticalPanel_4);

		HorizontalPanel horizontalPanel_5 = new HorizontalPanel();
		horizontalPanel_5.setSpacing(6);
		horizontalPanel_5.setStyleName("BlancoTransparente");
		horizontalPanel_5
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_5
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_4.add(horizontalPanel_5);
		horizontalPanel_5.setWidth("");

		VerticalPanel verticalPanel_7 = new VerticalPanel();
		horizontalPanel_5.add(verticalPanel_7);
		verticalPanel_7.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_7
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_7.setStyleName("BlancoTransparente");
		verticalPanel_7.setWidth("");

		btnNewButton = new Button("Sign in");
		verticalPanel_7.add(btnNewButton);
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				btnNewButton.setEnabled(false);

				revisarTextboxAndEnter();
			}
		});
		btnNewButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});

		btnNewButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});

		btnNewButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");

			}
		});

		btnNewButton.setStyleName("gwt-ButtonCenter");

		VerticalPanel verticalPanel_2 = new VerticalPanel();
		verticalPanel_2.setStyleName("AzulTransparente");
		horizontalPanel_3.add(verticalPanel_2);
		verticalPanel_2.setSize("100%", "");

		SimplePanel simplePanel_1 = new SimplePanel();
		simplePanel_1.setStyleName("BlancoTransparente");
		verticalPanel_2.add(simplePanel_1);
		simplePanel_1.setSize("100%", "100%");

		Label lblNewLabel_2 = new Label("Registration");
		simplePanel_1.setWidget(lblNewLabel_2);
		lblNewLabel_2.setSize("100%", "100%");
		lblNewLabel_2.setStyleName("TituloLogin");

		VerticalPanel verticalPanel_3 = new VerticalPanel();
		verticalPanel_2.add(verticalPanel_3);
		verticalPanel_3.setWidth("100%");

		HorizontalPanel horizontalPanel_6 = new HorizontalPanel();
		horizontalPanel_6.setSpacing(10);
		verticalPanel_3.add(horizontalPanel_6);

		VerticalPanel verticalPanel_8 = new VerticalPanel();
		verticalPanel_8.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_8.setStyleName("BlancoTransparente");
		verticalPanel_8
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_6.add(verticalPanel_8);
		verticalPanel_8.setSize("76px", "28px");

		Label lblFirstname = new Label("First Name");
		lblFirstname.setStyleName("gwt-LabelMargen2px");
		lblFirstname.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		verticalPanel_8.add(lblFirstname);
		lblFirstname.setSize("", "");

		FirstName = new TextBox();
		horizontalPanel_6.add(FirstName);

		HorizontalPanel horizontalPanel_7 = new HorizontalPanel();
		horizontalPanel_7.setSpacing(10);
		verticalPanel_3.add(horizontalPanel_7);

		VerticalPanel verticalPanel_9 = new VerticalPanel();
		verticalPanel_9.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_9.setStyleName("BlancoTransparente");
		verticalPanel_9
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_7.add(verticalPanel_9);
		verticalPanel_9.setSize("76px", "28px");

		Label lblLastname = new Label("Last Name");
		lblLastname.setStyleName("gwt-LabelMargen2px");
		lblLastname.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		verticalPanel_9.add(lblLastname);
		lblLastname.setSize("", "");

		lastName = new TextBox();
		horizontalPanel_7.add(lastName);

		HorizontalPanel horizontalPanel_8 = new HorizontalPanel();
		horizontalPanel_8.setSpacing(10);
		verticalPanel_3.add(horizontalPanel_8);

		VerticalPanel verticalPanel_10 = new VerticalPanel();
		verticalPanel_10
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_10.setStyleName("BlancoTransparente");
		verticalPanel_10
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_8.add(verticalPanel_10);
		verticalPanel_10.setSize("76px", "28px");

		Label lblEmail = new Label("Email");
		lblEmail.setStyleName("gwt-LabelMargen2px");
		lblEmail.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		verticalPanel_10.add(lblEmail);
		lblEmail.setSize("", "");

		email = new TextBox();
		horizontalPanel_8.add(email);

		HorizontalPanel horizontalPanel_9 = new HorizontalPanel();
		horizontalPanel_9.setSpacing(10);
		verticalPanel_3.add(horizontalPanel_9);

		VerticalPanel verticalPanel_11 = new VerticalPanel();
		verticalPanel_11
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_11.setStyleName("BlancoTransparente");
		verticalPanel_11
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_9.add(verticalPanel_11);
		verticalPanel_11.setSize("117px", "28px");

		Label lblPassword_1 = new Label("Enter Password");
		lblPassword_1.setStyleName("gwt-LabelMargen2px");
		lblPassword_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		verticalPanel_11.add(lblPassword_1);
		lblPassword_1.setSize("", "");

		PaswordA = new PasswordTextBox();
		PaswordA.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if (PaswordA.getText().equals(PasswordB.getText()))
					Verificado.setVisible(true);
				else
					Verificado.setVisible(false);
			}
		});
		horizontalPanel_9.add(PaswordA);

		HorizontalPanel horizontalPanel_12 = new HorizontalPanel();
		horizontalPanel_12.setSpacing(10);
		verticalPanel_3.add(horizontalPanel_12);

		VerticalPanel verticalPanel_14 = new VerticalPanel();
		verticalPanel_14
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_14.setStyleName("BlancoTransparente");
		verticalPanel_14
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_12.add(verticalPanel_14);
		verticalPanel_14.setSize("117px", "28px");

		Label lblRepeatPassword = new Label("Repeat Password");
		lblRepeatPassword.setStyleName("gwt-LabelMargen2px");
		lblRepeatPassword
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		verticalPanel_14.add(lblRepeatPassword);
		lblRepeatPassword.setSize("", "");

		PasswordB = new PasswordTextBox();
		PasswordB.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if (PaswordA.getText().equals(PasswordB.getText()))
					Verificado.setVisible(true);
				else
					Verificado.setVisible(false);
			}
		});
		horizontalPanel_12.add(PasswordB);

		Verificado = new Image("Free.gif");
		horizontalPanel_12.add(Verificado);
		Verificado.setVisible(false);

		HorizontalPanel horizontalPanel_10 = new HorizontalPanel();
		horizontalPanel_10
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_10
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_2.add(horizontalPanel_10);
		horizontalPanel_10.setWidth("100%");

		VerticalPanel verticalPanel_12 = new VerticalPanel();
		horizontalPanel_10.add(verticalPanel_12);

		HorizontalPanel horizontalPanel_11 = new HorizontalPanel();
		horizontalPanel_11.setSpacing(6);
		horizontalPanel_11
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_11.setStyleName("BlancoTransparente");
		horizontalPanel_11
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_12.add(horizontalPanel_11);
		horizontalPanel_11.setWidth("");

		VerticalPanel verticalPanel_13 = new VerticalPanel();
		verticalPanel_13
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_13.setStyleName("BlancoTransparente");
		verticalPanel_13
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_11.add(verticalPanel_13);
		verticalPanel_13.setWidth("");

		buttonRegister = new Button("Enter");
		buttonRegister.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
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
					//TODO
				//	ProfessorClient UC = new ProfessorClient();
					StudentClient UC = new StudentClient();
					UC.setFirstName(FirstName.getText());
					UC.setLastName(lastName.getText());
					UC.setEmail(email.getText().toLowerCase());
					UC.setPassword(PaswordA.getText());
					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto(
							InformationConstants.SAVING);
					newStudent=UC;
					bookReaderServiceHolder.saveUser(UC,
							new AsyncCallback<Void>() {

								@Override
								public void onSuccess(Void result) {
									LoadingPanel.getInstance().hide();
									Window.alert(InformationConstants.A_EMAIL_BE_SEND_TO_YOUR_EMAIL_FOR_CONFIRM_THE_REGISTRATION);
									Logger.GetLogger().info(
											Yo.getClass().toString(),
											null,
											"New User created:  "
													+ newStudent.toString(),new AsyncCallback<Void>() {
														
														@Override
														public void onSuccess(Void result) {
															Window.Location.reload();
															
														}
														
														@Override
														public void onFailure(Throwable caught) {
															Logger.callbackfailture();
															
														}
													});
//									Window.Location.reload();
								}

								@Override
								public void onFailure(Throwable caught) {
									LoadingPanel.getInstance().hide();
									Window.alert(ErrorConstants.ERROR_IN_REGISTERATION);
									Logger.GetLogger()
											.severe(Yo.getClass().toString(),
													null,
													ErrorConstants.ERROR_IN_REGISTERATION);
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
		buttonRegister.setText("Register");
		buttonRegister.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});

		buttonRegister.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});

		buttonRegister.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");

			}
		});

		buttonRegister.setStyleName("gwt-ButtonCenter");
		verticalPanel_13.add(buttonRegister);

		MenuBar menuBar = new MenuBar(false);
		dockPanel.add(menuBar, DockPanel.NORTH);
		menuBar.setHeight("24px");
		
		
		
	}

	

	protected void revisarTextboxAndEnter() {
		String nombre = User.getText();
		String contrasena = passwordLogin.getText();
		if (nombre.isEmpty() || contrasena.isEmpty()) {
			Window.alert(ErrorConstants.ERROR_PASS_OR_USER_EMPTY);
			btnNewButton.setEnabled(true);
		} else {
			LoadingPanel.getInstance().center();
			LoadingPanel.getInstance().setLabelTexto(
					InformationConstants.LOGGING);
			bookReaderServiceHolder.login(nombre, contrasena,
					new AsyncCallback<UserClient>() {

						public void onFailure(Throwable caught) {
							LoadingPanel.getInstance().hide();
							if (caught instanceof UserNotFoundException)
								{
								Window.alert(ErrorConstants.YOU_USER_NOT_EXIST);
								Logger.GetLogger().severe(Yo.getClass().toString(),
										null,
										ErrorConstants.YOU_USER_NOT_EXIST);	
								}
							else if (caught instanceof NotAuthenticatedException)
								{
								Window.alert(ErrorConstants.YOU_ARE_NO_AUTORIZED);
								Logger.GetLogger().severe(Yo.getClass().toString(),
										null,
										ErrorConstants.YOU_ARE_NO_AUTORIZED);	
								}
							else {
								Window.alert(ErrorConstants.GENERAL_ERROR_REFRESH);
								Logger.GetLogger().severe(Yo.getClass().toString(),
										null,
										ErrorConstants.GENERAL_ERROR_REFRESH);	
								}
							
							btnNewButton.setEnabled(true);
						}

						public void onSuccess(UserClient result) {
							LoadingPanel.getInstance().hide();
							if (result == null) {
								Window.alert(ErrorConstants.YOU_ARE_NO_AUTORIZED);
								Logger.GetLogger().severe(Yo.getClass().toString(),
										null,
										ErrorConstants.YOU_ARE_NO_AUTORIZED);
								btnNewButton.setEnabled(true);
							} else {
								ActualState.setUser(result);
								if (KeepConected.getValue())
									{
									 int caduca = 1000*60*60*24;
									 Date expira = new Date(new Date().getTime() + caduca);
//									 Cookies.setCookie(Constants.COOKIE_NAME, Long.toString(result.getId()), expira);
									 Cookies.setCookie(Constants.COOKIE_NAME, Long.toString(result.getId()*CalendarNow.GetDateNowInt()*SemillaPrimo), expira);
									}
								Logger.GetLogger().info(
										this.getClass().getName(),
										ActualState.getUser().toString(),
										"Log in");
								if (result instanceof StudentClient)
									Controlador.change2MyActivities();
								else if (result instanceof ProfessorClient)
									Controlador.change2Administrator();
							}
						}

					});
		}
	}
}
