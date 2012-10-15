package lector.client.login;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.controler.ErrorConstants;
import lector.share.model.UserApp;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.StudentClient;
import lector.share.model.client.UserClient;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class Login implements EntryPoint {

	private TextBox User;
	private GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private Button btnNewButton;
	private PasswordTextBox passwordTextBox;
	private Button buttonRegister;

	public void onModuleLoad() {

		RootPanel rootPanel = RootPanel.get();
		rootPanel.setStyleName("Root");

		DockPanel dockPanel = new DockPanel();
		dockPanel.setStyleName("fondoLogo");
		rootPanel.add(dockPanel,0,0);
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
		verticalPanel_5.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_5.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.add(verticalPanel_5);
		verticalPanel_5.setSize("67px", "28px");

		Label lblNewLabel_1 = new Label("User");
		lblNewLabel_1.setStyleName("gwt-LabelMargen2px");
		verticalPanel_5.add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		horizontalPanel.setCellHorizontalAlignment(lblNewLabel_1, HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setCellVerticalAlignment(lblNewLabel_1, HasVerticalAlignment.ALIGN_MIDDLE);
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
		verticalPanel_6.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_2.add(verticalPanel_6);
		verticalPanel_6.setHeight("28px");

		Label lblPassword = new Label("Password");
		lblPassword.setStyleName("gwt-LabelMargen2px");
		verticalPanel_6.add(lblPassword);

		passwordTextBox = new PasswordTextBox();
		horizontalPanel_2.add(passwordTextBox);
				
				HorizontalPanel horizontalPanel_4 = new HorizontalPanel();
				horizontalPanel_4.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
				horizontalPanel_4.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
				verticalPanel.add(horizontalPanel_4);
				horizontalPanel_4.setWidth("100%");
VerticalPanel verticalPanel_4 = new VerticalPanel();
horizontalPanel_4.add(verticalPanel_4);
				
				HorizontalPanel horizontalPanel_5 = new HorizontalPanel();
				horizontalPanel_5.setStyleName("BlancoTransparente");
				horizontalPanel_5.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
				horizontalPanel_5.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
				verticalPanel_4.add(horizontalPanel_5);
				horizontalPanel_5.setWidth("88px");
						
						VerticalPanel verticalPanel_7 = new VerticalPanel();
						horizontalPanel_5.add(verticalPanel_7);
						verticalPanel_7.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
						verticalPanel_7.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
						verticalPanel_7.setStyleName("BlancoTransparente");
						verticalPanel_7.setWidth("");
						
								btnNewButton = new Button("Enter");
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
										((Button) event.getSource())
												.setStyleName("gwt-ButtonCenter");
									}
								});
								
								btnNewButton
										.addMouseOverHandler(new MouseOverHandler() {
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
		verticalPanel_8.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_6.add(verticalPanel_8);
		verticalPanel_8.setHeight("28px");
		
		Label lblFirstname = new Label("FirstName");
		lblFirstname.setStyleName("gwt-LabelMargen2px");
		lblFirstname.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		verticalPanel_8.add(lblFirstname);
		lblFirstname.setSize("", "");
		
		TextBox FirstName = new TextBox();
		horizontalPanel_6.add(FirstName);
		
		HorizontalPanel horizontalPanel_7 = new HorizontalPanel();
		horizontalPanel_7.setSpacing(10);
		verticalPanel_3.add(horizontalPanel_7);
		
		VerticalPanel verticalPanel_9 = new VerticalPanel();
		verticalPanel_9.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_9.setStyleName("BlancoTransparente");
		verticalPanel_9.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_7.add(verticalPanel_9);
		verticalPanel_9.setSize("67px", "28px");
		
		Label lblLastname = new Label("LastName");
		lblLastname.setStyleName("gwt-LabelMargen2px");
		lblLastname.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		verticalPanel_9.add(lblLastname);
		lblLastname.setSize("", "");
		
		TextBox lastName = new TextBox();
		horizontalPanel_7.add(lastName);
		
		HorizontalPanel horizontalPanel_8 = new HorizontalPanel();
		horizontalPanel_8.setSpacing(10);
		verticalPanel_3.add(horizontalPanel_8);
		
		VerticalPanel verticalPanel_10 = new VerticalPanel();
		verticalPanel_10.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_10.setStyleName("BlancoTransparente");
		verticalPanel_10.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_8.add(verticalPanel_10);
		verticalPanel_10.setSize("67px", "28px");
		
		Label lblEmail = new Label("Email");
		lblEmail.setStyleName("gwt-LabelMargen2px");
		lblEmail.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		verticalPanel_10.add(lblEmail);
		lblEmail.setSize("", "");
		
		TextBox email = new TextBox();
		horizontalPanel_8.add(email);
		
		HorizontalPanel horizontalPanel_9 = new HorizontalPanel();
		horizontalPanel_9.setSpacing(10);
		verticalPanel_3.add(horizontalPanel_9);
		
		VerticalPanel verticalPanel_11 = new VerticalPanel();
		verticalPanel_11.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_11.setStyleName("BlancoTransparente");
		verticalPanel_11.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_9.add(verticalPanel_11);
		verticalPanel_11.setSize("67px", "28px");
		
		Label lblPassword_1 = new Label("Password");
		lblPassword_1.setStyleName("gwt-LabelMargen2px");
		lblPassword_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		verticalPanel_11.add(lblPassword_1);
		lblPassword_1.setSize("", "");
		
		PasswordTextBox password = new PasswordTextBox();
		horizontalPanel_9.add(password);
		
		HorizontalPanel horizontalPanel_10 = new HorizontalPanel();
		horizontalPanel_10.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_10.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_2.add(horizontalPanel_10);
		horizontalPanel_10.setWidth("100%");
		
		VerticalPanel verticalPanel_12 = new VerticalPanel();
		horizontalPanel_10.add(verticalPanel_12);
		
		HorizontalPanel horizontalPanel_11 = new HorizontalPanel();
		horizontalPanel_11.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_11.setStyleName("BlancoTransparente");
		horizontalPanel_11.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_12.add(horizontalPanel_11);
		horizontalPanel_11.setWidth("135px");
		
		VerticalPanel verticalPanel_13 = new VerticalPanel();
		verticalPanel_13.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_13.setStyleName("BlancoTransparente");
		verticalPanel_13.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_11.add(verticalPanel_13);
		verticalPanel_13.setWidth("");
		
		buttonRegister = new Button("Enter");
		buttonRegister.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
			}
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
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenter");
			}
		});
		
		buttonRegister
				.addMouseOverHandler(new MouseOverHandler() {
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
		String contrasena = passwordTextBox.getText();
		if (nombre.isEmpty()||contrasena.isEmpty()){
			Window.alert(ErrorConstants.ERROR_PASS_OR_USER_EMPTY);
			btnNewButton.setEnabled(true);
		}		
		else{
		
		bookReaderServiceHolder.login(nombre,contrasena,
				new AsyncCallback<UserClient>() {

					public void onFailure(Throwable caught) {
						Window.alert("You are not authorized to view this application");
						btnNewButton.setEnabled(true);
					}

					public void onSuccess(UserClient result) {
						ActualUser.setUser(result);

						if (result == null) {
							Window.alert("You are not authorized to view this application");
							btnNewButton.setEnabled(true);
						} else if (result instanceof StudentClient)
							Controlador.change2MyActivities();
						else if (result instanceof ProfessorClient)
							Controlador.change2Administrator();

					}

				});
		}
	}
}
