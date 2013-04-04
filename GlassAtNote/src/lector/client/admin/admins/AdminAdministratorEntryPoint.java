package lector.client.admin.admins;

import java.util.List;

import lector.client.admin.generalPanels.BotonesStackPanelAdminsMio;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.controler.ConstantsError;
import lector.client.controler.ConstantsInformation;
import lector.client.controler.catalogo.StackPanelMio;
import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;
import lector.share.model.Language;
import lector.share.model.client.ProfessorClient;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Button;
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

public class AdminAdministratorEntryPoint implements EntryPoint {

	private static final String ADINISTRAATOR_ADMINISTRATION_NAME = "Administrator Administration Menu";

	private static final int NCampos = 9;

	private static String ADMINISTRATION_WELLCOME = "Administrators";
	private static String BACK_MENUITEM = "Back";
	private static String ADD_NEW_USER_LABEL = "Add New User";
	private static String FIRSTNAME_LABEL = "First Name";
	private static String LASTNAME_LABEL = "Last Name";
	private static String EMAIL_LABEL = "Email";
	private static String ENTER_PASSWORD_LABEL = "Enter Password";
	private static String REPEAT_PASSWORD_LABEL = "Repeat Password";
	private static String SAVE_NEW_ADMINISTRATOR = "Save New Administrator";
	
	private static final String ADMINISTRATION_WELLCOME_RESET = "Administrators";
	private static final String BACK_MENUITEM_RESET = "Back";
	private static final String ADD_NEW_USER_LABEL_RESET = "Add New User";
	private static final String FIRSTNAME_LABEL_RESET = "First Name";
	private static final String LASTNAME_LABEL_RESET = "Last Name";
	private static final String EMAIL_LABEL_RESET = "Email";
	private static final String ENTER_PASSWORD_LABEL_RESET = "Enter Password";
	private static final String REPEAT_PASSWORD_LABEL_RESET = "Repeat Password";
	private static final String SAVE_NEW_ADMINISTRATOR_RESET = "Save New Administrator";
	

	private MenuItem WellcomeMenuItem;
	private MenuItem BackMenuItem;
	private Label AddNewUserLabel;
	private Label FirstNameLabel;
	private Label LastNameLabel;
	private Label EmailLabel;
	private Label EnterPasswordLabel;
	private Label RepeatPasswordLabel;
	private Button SaveAdminsButton;

	private TextBox WellcomeMenuItemTextBox;
	private TextBox BackMenuItemTextBox;
	private TextBox AddNewUserLabelTextBox;
	private TextBox FirstNameLabelTextBox;
	private TextBox LastNameLabelTextBox;
	private TextBox EmailLabelTextBox;
	private TextBox EnterPasswordLabelTextBox;
	private TextBox RepeatPasswordLabelTextBox;
	private TextBox SaveAdminsButtonTextBox;
	
	
	private static final String FREEGIF = "Free.gif";

	private StackPanelMio stackPanel_1;

	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	AdminAdministratorEntryPoint Yo;
	private TextBox FirstNameTextBox;
	private TextBox LastNameTextBox;
	private TextBox EmailTextBox;
	private PasswordTextBox PaswordATextBox;
	private PasswordTextBox PasswordBTextBox;
	private Image Verificado;
	private DockLayoutPanel PanelFondoGeneral;
	private AbsolutePanel PanelEdicion;
	private RootPanel rootPanel;

	
	public void onModuleLoad() {

		Yo = this;
		rootPanel = RootPanel.get();
		rootPanel.setSize(Constants.P100, Constants.P100);
		rootPanel.setStyleName("Root");

		PanelFondoGeneral = new DockLayoutPanel(Unit.PX);
		rootPanel.add(PanelFondoGeneral, 0, 0);
		
		PanelFondoGeneral.setStyleName("fondoLogo");

		PanelFondoGeneral.setSize(Constants.P100, Constants.P100);
	

		MenuBar menuBar = new MenuBar(false);

		PanelFondoGeneral.addNorth(menuBar, 25);
		SimplePanel PanelCentralBotones = new SimplePanel();
		PanelCentralBotones.setSize(Constants.P100, Constants.P100);
		PanelFondoGeneral.add(PanelCentralBotones);
		menuBar.setWidth("100%");

		WellcomeMenuItem = new MenuItem(ADMINISTRATION_WELLCOME, false,
				(Command) null);
		WellcomeMenuItem.setHTML(ADMINISTRATION_WELLCOME);
		WellcomeMenuItem.setEnabled(false);
		menuBar.addItem(WellcomeMenuItem);

		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);

		BackMenuItem = new MenuItem(BACK_MENUITEM, false, new Command() {
			public void execute() {
				Controlador.change2Administrator();
			}
		});
		menuBar.addItem(BackMenuItem);

		SplitLayoutPanel horizontalSplitPanel = new SplitLayoutPanel();
		// horizontalSplitPanel.setStyleName("fondoLogo");
		PanelCentralBotones.setWidget(horizontalSplitPanel);
		horizontalSplitPanel.setSize(Constants.P100, Constants.P100);

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setSpacing(12);

		verticalPanel.setWidth(Constants.P100);

		VerticalPanel verticalPanel_2 = new VerticalPanel();
		verticalPanel_2.setSpacing(3);
		verticalPanel_2.setStyleName("AzulTransparente");
		verticalPanel.add(verticalPanel_2);
		verticalPanel_2.setWidth(Constants.P100);

		HorizontalPanel horizontalPanel_6 = new HorizontalPanel();
		horizontalPanel_6.setStyleName("BlancoTransparente");
		verticalPanel_2.add(horizontalPanel_6);
		horizontalPanel_6.setWidth(Constants.P100);

		AddNewUserLabel = new Label(ADD_NEW_USER_LABEL);
		AddNewUserLabel.setStyleName("TituloLogin");
		horizontalPanel_6.add(AddNewUserLabel);

		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_1
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_1.setStyleName("BlancoTransparente");
		horizontalPanel_1.setSpacing(10);
		verticalPanel_2.add(horizontalPanel_1);

		FirstNameLabel = new Label(FIRSTNAME_LABEL);
		horizontalPanel_1.add(FirstNameLabel);
		FirstNameLabel.setStyleName("gwt-LabelMargen2px");
		FirstNameLabel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		FirstNameLabel.setWidth("107px");

		FirstNameTextBox = new TextBox();
		horizontalPanel_1.add(FirstNameTextBox);

		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_2.setStyleName("BlancoTransparente");
		horizontalPanel_2.setSpacing(10);
		horizontalPanel_2
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_2.add(horizontalPanel_2);

		LastNameLabel = new Label(LASTNAME_LABEL);
		horizontalPanel_2.add(LastNameLabel);
		LastNameLabel.setStyleName("gwt-LabelMargen2px");
		LastNameLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		LastNameLabel.setWidth("107px");

		LastNameTextBox = new TextBox();
		horizontalPanel_2.add(LastNameTextBox);

		HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
		horizontalPanel_3
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_3.setStyleName("BlancoTransparente");
		horizontalPanel_3.setSpacing(10);
		horizontalPanel_3
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_2.add(horizontalPanel_3);
		horizontalPanel_3.setWidth("");

		EmailLabel = new Label(EMAIL_LABEL);
		horizontalPanel_3.add(EmailLabel);
		EmailLabel.setStyleName("gwt-LabelMargen2px");
		EmailLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		EmailLabel.setWidth("107px");

		EmailTextBox = new TextBox();
		horizontalPanel_3.add(EmailTextBox);

		HorizontalPanel horizontalPanel_4 = new HorizontalPanel();
		horizontalPanel_4
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_4.setStyleName("BlancoTransparente");
		horizontalPanel_4.setSpacing(10);
		horizontalPanel_4
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel_2.add(horizontalPanel_4);

		EnterPasswordLabel = new Label(ENTER_PASSWORD_LABEL);
		horizontalPanel_4.add(EnterPasswordLabel);
		EnterPasswordLabel.setStyleName("gwt-LabelMargen2px");
		EnterPasswordLabel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		EnterPasswordLabel.setWidth("107px");

		PaswordATextBox = new PasswordTextBox();
		horizontalPanel_4.add(PaswordATextBox);
		PaswordATextBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if (PaswordATextBox.getText()
						.equals(PasswordBTextBox.getText()))
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

		RepeatPasswordLabel = new Label(REPEAT_PASSWORD_LABEL);
		horizontalPanel_5.add(RepeatPasswordLabel);
		RepeatPasswordLabel.setStyleName("gwt-LabelMargen2px");
		RepeatPasswordLabel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		RepeatPasswordLabel.setWidth("107px");

		PasswordBTextBox = new PasswordTextBox();
		horizontalPanel_5.add(PasswordBTextBox);

		Verificado = new Image(FREEGIF);
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

		SaveAdminsButton = new Button(SAVE_NEW_ADMINISTRATOR);
		verticalPanel_3.add(SaveAdminsButton);
		SaveAdminsButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});

		SaveAdminsButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});

		SaveAdminsButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");

			}
		});

		SaveAdminsButton.setStyleName("gwt-ButtonCenter");

		VerticalPanel verticalPanel_4 = new VerticalPanel();
		verticalPanel_4.setStyleName("BlancoTransparente");
		verticalPanel_4.setSpacing(6);
		horizontalSplitPanel.addEast(verticalPanel_4, 300);

		horizontalSplitPanel.add(verticalPanel);

		verticalPanel_4.setSize("100%", "100%");

		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setStyleName("BlancoTransparente");
		verticalPanel_4.add(scrollPanel);

		stackPanel_1 = new StackPanelMio();
		scrollPanel.setWidget(stackPanel_1);

		stackPanel_1
				.setBotonTipo(new BotonesStackPanelAdminsMio(
						new AdministradorEntidadObject(new ProfessorClient(
								"prototipo")), new VerticalPanel()));
		stackPanel_1.setBotonClick(new ClickHandler() {

			private ProfessorClient Ident;

			public void onClick(ClickEvent event) {

				Ident = ((AdministradorEntidadObject) ((BotonesStackPanelAdminsMio) event
						.getSource()).getEntidad()).getAdmin();
				if (Window
						.confirm(ConstantsInformation.ARE_YOU_SURE_DELETE_ADMIN
								+ Ident.getFirstName()
								+ " "
								+ Ident.getLastName()
								+ ConstantsInformation.ARE_YOU_SURE_DELETE_ADMIN2)) {

					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto("Loading...");

					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto("Deleting...");

					bookReaderServiceHolder.deleteProfessorById(Ident.getId(),
							new AsyncCallback<Void>() {

								public void onFailure(Throwable caught) {
									Window.alert(ConstantsError.ERROR_USER_CAN_NOT_BE_REMOVED);

								}

								public void onSuccess(Void result) {
									Logger.GetLogger().info(
											Yo.getClass().toString(),
											ActualState.getUser().toString(),
											"Administrator Deleted:  "
													+ Ident.toString());
									if (ActualState.getUser().getId()
											.equals(Ident.getId())) {
										ActualState.setUser(null);
										ActualState
												.setReadingActivityBook(null);
										ActualState.setReadingactivity(null);
										Window.alert(ConstantsInformation.GOODBYE);
										Window.Location.reload();
									}
									refreshPanel();
								}
							});

				}
			}
		});

		stackPanel_1.setSize("100%", "100%");

		SaveAdminsButton.addClickHandler(new ClickHandler() {

			private ProfessorClient newStudent;

			public void onClick(ClickEvent event) {
				SaveAdminsButton.setEnabled(false);
				if (!PaswordATextBox.getText().equals(
						PasswordBTextBox.getText())) {
					PaswordATextBox.setText("");
					PasswordBTextBox.setText("");
					Window.alert(ConstantsError.ERROR_PASSWORDS_NOT_MATCH);
					SaveAdminsButton.setEnabled(true);
				} else if (FirstNameTextBox.getText().isEmpty()) {
					Window.alert(ConstantsError.FIRST_NAME_IS_EMPTY);
					SaveAdminsButton.setEnabled(true);
				} else if (LastNameTextBox.getText().isEmpty()) {
					Window.alert(ConstantsError.LAST_NAME_IS_EMPTY);
					SaveAdminsButton.setEnabled(true);
				} else if (!isValidEmail(EmailTextBox.getText())) {
					Window.alert(ConstantsError.IT_IS_NOT_A_EMAIL);
					SaveAdminsButton.setEnabled(true);
				} else {
					ProfessorClient UC = new ProfessorClient();
					UC.setFirstName(FirstNameTextBox.getText());
					UC.setLastName(LastNameTextBox.getText());
					UC.setEmail(EmailTextBox.getText().toLowerCase());
					UC.setPassword(PaswordATextBox.getText());
					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto(
							ConstantsInformation.SAVING);
					newStudent = UC;
					bookReaderServiceHolder.saveUser(UC,
							new AsyncCallback<Void>() {

								@Override
								public void onSuccess(Void result) {
									LoadingPanel.getInstance().hide();
									Logger.GetLogger().info(
											Yo.getClass().toString(),
											ActualState.getUser().toString(),
											"New Administrator created:  "
													+ newStudent.toString());
									refreshPanel();
									SaveAdminsButton.setEnabled(true);
								}

								@Override
								public void onFailure(Throwable caught) {
									SaveAdminsButton.setEnabled(true);
									LoadingPanel.getInstance().hide();
									Window.alert(ConstantsError.ERROR_IN_REGISTERATION);
									Logger.GetLogger()
											.severe(Yo.getClass().toString(),
													ActualState.getUser()
															.toString(),
													ConstantsError.ERROR_IN_REGISTERATION);
								}
							});
				}
			}

			private native boolean isValidEmail(String email) 
			/*-{ 
			var reg1 = /(@.*@)|(\.\.)|(@\.)|(\.@)|(^\.)/; // not valid 
			var reg2 = /^.+\@(\[?)[a-zA-Z0-9\-\.]+\.([a-zA-Z]{2,3}|[0-9]{1,3})(\]?)$/; // valid 
			return !reg1.test(email) && reg2.test(email); 
			}-*/;

		});

		Verificado.setVisible(false);
		PasswordBTextBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if (PaswordATextBox.getText()
						.equals(PasswordBTextBox.getText()))
					Verificado.setVisible(true);
				else
					Verificado.setVisible(false);
			}
		});
		refreshPanel();
		PanelEdicion = new AbsolutePanel();
		if (ActualState.isLanguageActive())
			closeEditPanel();
		// // Profesores
		// LoadingPanel.getInstance().center();
		// LoadingPanel.getInstance().setLabelTexto(InformationConstants.LOADING);
		// bookReaderServiceHolder
		// .getProfessors(new AsyncCallback<List<ProfessorClient>>() {
		//
		// public void onFailure(Throwable caught) {
		// LoadingPanel.getInstance().hide();
		// }
		//
		// public void onSuccess(List<ProfessorClient> result) {
		// LoadingPanel.getInstance().hide();
		// if (result.size() < 10) {
		// for (ProfessorClient User1 : result) {
		//
		// AdministradorEntidadObjeto E = new AdministradorEntidadObjeto(User1);
		// stackPanel_1.addBotonLessTen(E);
		// }
		//
		// } else {
		// for (ProfessorClient User1 : result) {
		// AdministradorEntidadObjeto E = new AdministradorEntidadObjeto(User1);
		// stackPanel_1.addBoton(E);
		// }
		// }
		// stackPanel_1.setSize("100%", "100%");
		// stackPanel_1.ClearEmpty();
		//
		// }
		// });
	}

	protected void refreshPanel() {
		stackPanel_1.Clear();
		// Profesores
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto(ConstantsInformation.LOADING);
		bookReaderServiceHolder
				.getProfessors(new AsyncCallback<List<ProfessorClient>>() {

					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert(ConstantsError.ERROR_LOADING_USERS);
						Logger.GetLogger().severe(Yo.getClass().toString(),
								ActualState.getUser().toString(),
								ConstantsError.ERROR_LOADING_USERS);
					}

					public void onSuccess(List<ProfessorClient> result) {
						LoadingPanel.getInstance().hide();
						if (result.size() < 10) {
							for (ProfessorClient User1 : result) {

								AdministradorEntidadObject E = new AdministradorEntidadObject(
										User1);
								stackPanel_1.addBotonLessTen(E);
							}

						} else {
							for (ProfessorClient User1 : result) {
								AdministradorEntidadObject E = new AdministradorEntidadObject(
										User1);
								stackPanel_1.addBoton(E);
							}
						}
						stackPanel_1.setSize("100%", "100%");
						stackPanel_1.ClearEmpty();

					}
				});

	}

	public void closeEditPanel() {
		rootPanel.remove(PanelEdicion);
		rootPanel.add(PanelEdicion, PanelFondoGeneral.getOffsetWidth()
				- Constants.TAMANOBOTOBEDITOFF, 0);
		PanelEdicion.setSize(Constants.TAMANOBOTOBEDITOFF+Constants.PX, "50px");
		PanelEdicion.clear();
		PanelEdicion.setStyleName("");
		Button Boton = new Button();
		PanelEdicion.add(Boton, 0, 0);
		Boton.setHTML(ConstantsInformation.EDIT_BOTTON);
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
		Boton.setHTML(ConstantsInformation.END_EDIT_BOTTON);
		Boton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				closeEditPanel();

				 if (!WellcomeMenuItemTextBox.getText().isEmpty())
					 ADMINISTRATION_WELLCOME=WellcomeMenuItemTextBox.getText();
				 else ADMINISTRATION_WELLCOME=ADMINISTRATION_WELLCOME_RESET;
				
				 if (!BackMenuItemTextBox.getText().isEmpty())
					 BACK_MENUITEM=BackMenuItemTextBox.getText();
				 else BACK_MENUITEM=BACK_MENUITEM_RESET;
				
				 if (!AddNewUserLabelTextBox.getText().isEmpty())
					 ADD_NEW_USER_LABEL=AddNewUserLabelTextBox.getText();
				 else ADD_NEW_USER_LABEL=ADD_NEW_USER_LABEL_RESET;

				 if (!FirstNameLabelTextBox.getText().isEmpty())
					 FIRSTNAME_LABEL=FirstNameLabelTextBox.getText();
					 else FIRSTNAME_LABEL=FIRSTNAME_LABEL_RESET;
				 
				 if (!LastNameLabelTextBox.getText().isEmpty())
					 LASTNAME_LABEL=LastNameLabelTextBox.getText();
					 else LASTNAME_LABEL=LASTNAME_LABEL_RESET;
				 
				 if (!EmailLabelTextBox.getText().isEmpty())
					 EMAIL_LABEL=EmailLabelTextBox.getText();
					 else EMAIL_LABEL=EMAIL_LABEL_RESET;
				 
				 if (!EnterPasswordLabelTextBox.getText().isEmpty())
					 ENTER_PASSWORD_LABEL=EnterPasswordLabelTextBox.getText();
					 else ENTER_PASSWORD_LABEL=ENTER_PASSWORD_LABEL_RESET;
				 
				 if (!RepeatPasswordLabelTextBox.getText().isEmpty())
					 REPEAT_PASSWORD_LABEL=RepeatPasswordLabelTextBox.getText();
					 else REPEAT_PASSWORD_LABEL=REPEAT_PASSWORD_LABEL_RESET;
				 
				 if (!SaveAdminsButtonTextBox.getText().isEmpty())
					 SAVE_NEW_ADMINISTRATOR=SaveAdminsButtonTextBox.getText();
					 else SAVE_NEW_ADMINISTRATOR=SAVE_NEW_ADMINISTRATOR_RESET;
				 
				ParsearFieldsAItems();
				SaveChages();
			}
		});

		WellcomeMenuItemTextBox=new TextBox();
		WellcomeMenuItemTextBox.setText(ADMINISTRATION_WELLCOME);
		WellcomeMenuItemTextBox.setSize(WellcomeMenuItem.getOffsetWidth()+"px",
				 WellcomeMenuItem.getOffsetHeight()+"px");
		 PanelEdicion.add(WellcomeMenuItemTextBox,
				 WellcomeMenuItem.getAbsoluteLeft(),
				 WellcomeMenuItem.getAbsoluteTop());
		
		 BackMenuItemTextBox=new TextBox();
		 BackMenuItemTextBox.setText(BACK_MENUITEM);
		 BackMenuItemTextBox.setSize(BackMenuItem.getOffsetWidth()+"px",
				 BackMenuItem.getOffsetHeight()+"px");
		 PanelEdicion.add(BackMenuItemTextBox,
				 BackMenuItem.getAbsoluteLeft(),
		 BackMenuItem.getAbsoluteTop());
		
		 AddNewUserLabelTextBox=new TextBox();
		 AddNewUserLabelTextBox.setText(ADD_NEW_USER_LABEL);
		 AddNewUserLabelTextBox.setSize(AddNewUserLabel.getOffsetWidth()+"px",
				 AddNewUserLabel.getOffsetHeight()+"px");
		 PanelEdicion.add(AddNewUserLabelTextBox, AddNewUserLabel.getAbsoluteLeft(),
				 AddNewUserLabel.getAbsoluteTop());
		 
		 FirstNameLabelTextBox=new TextBox();
		 FirstNameLabelTextBox.setText(FIRSTNAME_LABEL);
		 FirstNameLabelTextBox.setSize(FirstNameLabel.getOffsetWidth()+"px",
				 FirstNameLabel.getOffsetHeight()+"px");
		 PanelEdicion.add(FirstNameLabelTextBox, FirstNameLabel.getAbsoluteLeft(),
				 FirstNameLabel.getAbsoluteTop());
		 
		 LastNameLabelTextBox=new TextBox();
		 LastNameLabelTextBox.setText(LASTNAME_LABEL);
		 LastNameLabelTextBox.setSize(LastNameLabel.getOffsetWidth()+"px",
				 LastNameLabel.getOffsetHeight()+"px");
		 PanelEdicion.add(LastNameLabelTextBox, LastNameLabel.getAbsoluteLeft(),
				 LastNameLabel.getAbsoluteTop());
		 
		 EmailLabelTextBox=new TextBox();
		 EmailLabelTextBox.setText(EMAIL_LABEL);
		 EmailLabelTextBox.setSize(EmailLabel.getOffsetWidth()+"px",
				 EmailLabel.getOffsetHeight()+"px");
		 PanelEdicion.add(EmailLabelTextBox, EmailLabel.getAbsoluteLeft(),
				 EmailLabel.getAbsoluteTop());
		 
		 EnterPasswordLabelTextBox=new TextBox();
		 EnterPasswordLabelTextBox.setText(ENTER_PASSWORD_LABEL);
		 EnterPasswordLabelTextBox.setSize(EnterPasswordLabel.getOffsetWidth()+"px",
				 EnterPasswordLabel.getOffsetHeight()+"px");
		 PanelEdicion.add(EnterPasswordLabelTextBox, EnterPasswordLabel.getAbsoluteLeft(),
				 EnterPasswordLabel.getAbsoluteTop());
		 
		 RepeatPasswordLabelTextBox=new TextBox();
		 RepeatPasswordLabelTextBox.setText(REPEAT_PASSWORD_LABEL);
		 RepeatPasswordLabelTextBox.setSize(RepeatPasswordLabel.getOffsetWidth()+"px",
				 RepeatPasswordLabel.getOffsetHeight()+"px");
		 PanelEdicion.add(RepeatPasswordLabelTextBox, RepeatPasswordLabel.getAbsoluteLeft(),
				 RepeatPasswordLabel.getAbsoluteTop());
		 
		 SaveAdminsButtonTextBox=new TextBox();
		 SaveAdminsButtonTextBox.setText(SAVE_NEW_ADMINISTRATOR);
		 SaveAdminsButtonTextBox.setSize(SaveAdminsButton.getOffsetWidth()+"px",
				 SaveAdminsButton.getOffsetHeight()+"px");
		 PanelEdicion.add(SaveAdminsButtonTextBox, SaveAdminsButton.getAbsoluteLeft(),
				 SaveAdminsButton.getAbsoluteTop());
		

	}

	protected void SaveChages() {
		 Language LanguageActual = ActualState.getActualLanguage();
		 String AdminAdministratorEntryPointLanguageConfiguration=toFile();
		 LanguageActual.setAdminAdministratorEntryPointLanguageConfiguration(AdminAdministratorEntryPointLanguageConfiguration);
		 ActualState.saveLanguageActual(LanguageActual);
	}

	public void ParsearFieldsAItems() {
		WellcomeMenuItem.setHTML(ADMINISTRATION_WELLCOME);
		BackMenuItem.setHTML(BACK_MENUITEM);
		AddNewUserLabel.setText(ADD_NEW_USER_LABEL);
		FirstNameLabel.setText(FIRSTNAME_LABEL);
		LastNameLabel.setText(LASTNAME_LABEL);
		EmailLabel.setText(EMAIL_LABEL);
		EnterPasswordLabel.setText(ENTER_PASSWORD_LABEL);
		RepeatPasswordLabel.setText(REPEAT_PASSWORD_LABEL);
		SaveAdminsButton.setHTML(SAVE_NEW_ADMINISTRATOR);

	}

	public static String toFile() {
		StringBuffer SB = new StringBuffer();
		 SB.append(ADMINISTRATION_WELLCOME+"\r\n");
		 SB.append(BACK_MENUITEM+"\r\n");
		 SB.append(ADD_NEW_USER_LABEL+"\r\n");
		 SB.append(FIRSTNAME_LABEL+"\r\n");
		 SB.append(LASTNAME_LABEL+"\r\n");
		 SB.append(EMAIL_LABEL+"\r\n");
		 SB.append(ENTER_PASSWORD_LABEL+"\r\n");
		 SB.append(REPEAT_PASSWORD_LABEL+"\r\n");
		 SB.append(SAVE_NEW_ADMINISTRATOR+"\r\n");
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
			 ADMINISTRATION_WELLCOME = Lista[0];
		 else ADMINISTRATION_WELLCOME=ADMINISTRATION_WELLCOME_RESET;
		 if (!Lista[1].isEmpty())
			 BACK_MENUITEM = Lista[1];
		 else BACK_MENUITEM=BACK_MENUITEM_RESET;
		 if (!Lista[2].isEmpty())
			 ADD_NEW_USER_LABEL = Lista[2];
		 else ADD_NEW_USER_LABEL=ADD_NEW_USER_LABEL_RESET;
		 if (!Lista[3].isEmpty())
			 FIRSTNAME_LABEL = Lista[3];
			 else FIRSTNAME_LABEL=FIRSTNAME_LABEL_RESET;
		 if (!Lista[4].isEmpty())
			 LASTNAME_LABEL = Lista[4];
			 else LASTNAME_LABEL=LASTNAME_LABEL_RESET;
		 if (!Lista[5].isEmpty())
			 EMAIL_LABEL = Lista[5];
			 else EMAIL_LABEL=EMAIL_LABEL_RESET;
		 if (!Lista[6].isEmpty())
			 ENTER_PASSWORD_LABEL = Lista[6];
			 else ENTER_PASSWORD_LABEL=ENTER_PASSWORD_LABEL_RESET;
		 if (!Lista[7].isEmpty())
			 REPEAT_PASSWORD_LABEL = Lista[7];
			 else REPEAT_PASSWORD_LABEL=REPEAT_PASSWORD_LABEL_RESET;
		 if (!Lista[8].isEmpty())
			 SAVE_NEW_ADMINISTRATOR = Lista[8];
			 else SAVE_NEW_ADMINISTRATOR=SAVE_NEW_ADMINISTRATOR_RESET;
		 }
		 else
		 Logger.GetLogger().severe(AdminAdministratorEntryPoint.class.toString(),
		 ActualState.getUser().toString(),
		 ConstantsError.ERROR_LOADING_LANGUAGE_IN +
		 ADINISTRAATOR_ADMINISTRATION_NAME);
		 ParsearFieldsAItemsRESET();
		}
	}
	
	private static void ParsearFieldsAItemsRESET() {
		ADMINISTRATION_WELLCOME=ADMINISTRATION_WELLCOME_RESET;
		BACK_MENUITEM=BACK_MENUITEM_RESET;
		ADD_NEW_USER_LABEL=ADD_NEW_USER_LABEL_RESET;
		FIRSTNAME_LABEL=FIRSTNAME_LABEL_RESET;
		LASTNAME_LABEL=LASTNAME_LABEL_RESET;
		EMAIL_LABEL=EMAIL_LABEL_RESET;
		ENTER_PASSWORD_LABEL=ENTER_PASSWORD_LABEL_RESET;
		REPEAT_PASSWORD_LABEL=REPEAT_PASSWORD_LABEL_RESET;
		SAVE_NEW_ADMINISTRATOR=SAVE_NEW_ADMINISTRATOR_RESET;
		
	}

	

}
