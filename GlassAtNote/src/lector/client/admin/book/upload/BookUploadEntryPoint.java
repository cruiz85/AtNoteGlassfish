package lector.client.admin.book.upload;

import java.util.ArrayList;

import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.controler.ConstantsError;
import lector.client.controler.ConstantsInformation;
import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.SimplePanel;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.book.reader.ImageServiceAsync;
import lector.client.book.reader.ImageService;
import lector.share.model.Language;
import lector.share.model.client.UserClient;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;

public class BookUploadEntryPoint implements EntryPoint {

	private static final String BOOKUPLOADENTRYPOINT = "Book upoload entry point";

	private static final int NCampos = 7;

	private static String MENUITEMCLOSE = "Close";
	private static String RADIO_BUTTON_SIMPLE_LABEL = "Simple";
	private static String RADIO_BUTTON_PDF_LABEL = "PDF";
	private static String AUTORLABEL = "Autor:";
	private static String PUBLISHYEARLABEL = "Published Year:";
	private static String TITTLELABEL = "Title:";
	private static String UPLOAD = "Upload";

	private static final String MENUITEMCLOSE_RESET = "Close";
	private static final String RADIO_BUTTON_SIMPLE_LABEL_RESET = "Simple";
	private static final String RADIO_BUTTON_PDF_LABEL_RESET = "PDF";
	private static final String AUTORLABEL_RESET = "Autor:";
	private static final String PUBLISHYEARLABEL_RESET = "Published Year:";
	private static final String TITTLELABEL_RESET = "Title:";
	private static final String UPLOAD_RESET = "Upload";
	
	private MenuItem CloseMenuItem;
	private RadioButton SimpleRadioButton;
	private RadioButton PDFRadioButton;
	private Label AuthotLabel;
	private Label PublishYearLabel;
	private Label TittleLabel;
	private Button SubmitButton;

	private TextBox CloseMenuItemTextBox;
	private TextBox SimpleRadioButtonTextBox;
	private TextBox PDFRadioButtonTextBox;
	private TextBox AuthotLabelTextBox;
	private TextBox PublishYearLabelTextBox;
	private TextBox TittleLabelTextBox;
	private TextBox SubmitButtonTextBox;
	

	static ImageServiceAsync userImageService = GWT.create(ImageService.class);
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private TextBox AuthorTextBox;
	private TextBox PublishedYearTextBox;
	private TextBox TitleTextBox;
	private TextBox userApp;
	private int actualFiles;
	private VerticalPanel PanelUploaders;
	private FormPanel form;
	private FileUpload FU;
	private FileUpload FU1;
	private FileUpload FU2;
	private FileUpload FU3;
	private Button PlusButton;
	private String Accepnow;

	private static final String AcceptJPG = "image/png, image/gif,image/jpeg";
	private static final String AcceptPDF = "application/pdf";
	private static final String radioButtonGroup = "RBGroup";
	private static final String PLUS = "+";
	private DockLayoutPanel PanelFondoGeneral;

	private AbsolutePanel PanelEdicion;
	private RootPanel rootPanel;

	/**
	 * Metodo de entrada que pinta la ventana para el entry point
	 */
	public void onModuleLoad() {

		rootPanel = RootPanel.get();
		rootPanel.setSize("100%", "100%");

		ClickHandler event = new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (event.getSource() == PDFRadioButton) {
					// Poner Panel Simple
					actualFiles = 1;
					PanelUploaders.clear();
					PanelUploaders.add(FU);
					PanelUploaders.remove(FU1);
					PanelUploaders.remove(FU2);
					PanelUploaders.remove(FU3);
					SubmitButton.setEnabled(false);
					startNewBlobstoreSessionPDF();
					PlusButton.setVisible(false);
					Accepnow = AcceptPDF;
					putacceptInFileUploaders();
				} else {
					actualFiles = 4;
					PanelUploaders.clear();
					PanelUploaders.add(FU);
					PanelUploaders.add(FU1);
					PanelUploaders.add(FU2);
					PanelUploaders.add(FU3);
					SubmitButton.setEnabled(false);
					startNewBlobstoreSessionSimple();
					PlusButton.setVisible(true);
					Accepnow = AcceptJPG;
					putacceptInFileUploaders();
				}
			}
		};

		

		SubmitCompleteHandler Simple = new FormPanel.SubmitCompleteHandler() {

			public void onSubmitComplete(SubmitCompleteEvent event) {

				LoadingPanel.getInstance().hide();

				// startNewBlobstoreSession();
				String results = event.getResults();
				results = results.replaceAll("\\<.*?\\>", "");
				if ((event.getResults() != null) && !(results.isEmpty()))
					Window.alert(event.getResults());
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto(
						ConstantsInformation.LOADING);
				bookReaderServiceHolder.loadUserById(ActualState.getUser()
						.getId(), new AsyncCallback<UserClient>() {

					public void onSuccess(UserClient result) {
						ActualState.setUser(result);
						Controlador.change2BookAdminstrator();
						// form.reset();
						LoadingPanel.getInstance().hide();

					}

					public void onFailure(Throwable caught) {
						Window.alert(ConstantsError.ERROR_LOAD_FORCE_LOGIN);
						Window.Location.reload();
						LoadingPanel.getInstance().hide();
					}
				});

			}
		};

		actualFiles = 4;

		PanelFondoGeneral = new DockLayoutPanel(Unit.PX);
		rootPanel.add(PanelFondoGeneral,0,0);
		PanelFondoGeneral.setSize("100%", "100%");

		MenuBar menuBar = new MenuBar(false);
		PanelFondoGeneral.addNorth(menuBar, 25.0);
		menuBar.setWidth("100%");

		CloseMenuItem = new MenuItem(MENUITEMCLOSE, false, new Command() {

			public void execute() {
				Controlador.change2BookAdminstrator();

			}
		});
		menuBar.addItem(CloseMenuItem);
		CloseMenuItem.setHTML(MENUITEMCLOSE);

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		PanelFondoGeneral.add(horizontalPanel);
		horizontalPanel.setStyleName("fondoLogo");
		horizontalPanel.setSize("100%", "100%");

		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setSpacing(13);
		horizontalPanel.add(horizontalPanel_1);

		form = new FormPanel();
		horizontalPanel_1.add(form);
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		form.setSize("100%", "100%");

		HorizontalPanel horizontalPanel_4 = new HorizontalPanel();
		form.setWidget(horizontalPanel_4);
		horizontalPanel_4.setSize("100%", "100%");

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setStyleName("AzulTransparente");
		horizontalPanel_4.add(verticalPanel);
		verticalPanel.setSpacing(10);
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		VerticalPanel verticalPanel_2 = new VerticalPanel();
		verticalPanel_2.setStyleName("BlancoTransparente");
		verticalPanel_2.setSpacing(6);
		verticalPanel.add(verticalPanel_2);
		verticalPanel_2.setWidth("200px");

		HorizontalPanel horizontalPanelRadioButton = new HorizontalPanel();
		verticalPanel_2.add(horizontalPanelRadioButton);
		horizontalPanelRadioButton.setWidth("100%");

		SimpleRadioButton = new RadioButton(
				BookUploadEntryPoint.radioButtonGroup,
				RADIO_BUTTON_SIMPLE_LABEL);
		PDFRadioButton = new RadioButton(BookUploadEntryPoint.radioButtonGroup,
				RADIO_BUTTON_PDF_LABEL);

		SimpleRadioButton.addClickHandler(event);
		PDFRadioButton.addClickHandler(event);

		horizontalPanelRadioButton.add(SimpleRadioButton);
		horizontalPanelRadioButton.add(PDFRadioButton);
		SimpleRadioButton.setValue(true);

		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		verticalPanel_2.add(horizontalPanel_2);
		horizontalPanel_2.setWidth("100%");

		AuthotLabel = new Label(AUTORLABEL);
		AuthotLabel.setStyleName("gwt-LabelLoad");
		horizontalPanel_2.add(AuthotLabel);

		HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
		horizontalPanel_3.setSpacing(1);
		horizontalPanel_3
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		verticalPanel_2.add(horizontalPanel_3);
		horizontalPanel_3.setWidth("100%");

		AuthorTextBox = new TextBox();
		horizontalPanel_3.add(AuthorTextBox);
		AuthorTextBox.setWidth("393px");
		AuthorTextBox.setName(Constants.BLOB_AUTHOR);

		HorizontalPanel horizontalPanel_5 = new HorizontalPanel();
		verticalPanel_2.add(horizontalPanel_5);

		PublishYearLabel = new Label(PUBLISHYEARLABEL);
		PublishYearLabel.setStyleName("gwt-LabelLoad");
		horizontalPanel_5.add(PublishYearLabel);

		HorizontalPanel horizontalPanel_6 = new HorizontalPanel();
		horizontalPanel_6
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		verticalPanel_2.add(horizontalPanel_6);
		horizontalPanel_6.setWidth("100%");
		PublishedYearTextBox = new TextBox();
		horizontalPanel_6.add(PublishedYearTextBox);
		PublishedYearTextBox.setWidth("393px");
		PublishedYearTextBox.setName(Constants.BLOB_PUBLISHED_YEAR);

		HorizontalPanel horizontalPanel_8 = new HorizontalPanel();
		verticalPanel_2.add(horizontalPanel_8);

		TittleLabel = new Label(TITTLELABEL);
		TittleLabel.setStyleName("gwt-LabelLoad");
		horizontalPanel_8.add(TittleLabel);

		HorizontalPanel horizontalPanel_7 = new HorizontalPanel();
		horizontalPanel_7
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		verticalPanel_2.add(horizontalPanel_7);
		horizontalPanel_7.setWidth("100%");
		TitleTextBox = new TextBox();
		horizontalPanel_7.add(TitleTextBox);
		TitleTextBox.setWidth("393px");
		TitleTextBox.setName(Constants.BLOB_TITLE);

		userApp = new TextBox();
		verticalPanel_2.add(userApp);
		userApp.setWidth("393px");
		userApp.setText(ActualState.getUser().getId().toString());
		userApp.setReadOnly(true);
		userApp.setVisible(false);
		userApp.setName(Constants.BLOB_UPLOADER);

		VerticalPanel verticalPanel_3 = new VerticalPanel();
		verticalPanel_2.add(verticalPanel_3);
		verticalPanel_3.setSize("100%", "100%");

		PanelUploaders = new VerticalPanel();
		verticalPanel_3.add(PanelUploaders);
		PanelUploaders.setWidth("100%");

		FU = new FileUpload();
		FU.setName("1");
		PanelUploaders.add(FU);
		FU.setWidth("100%");

		FU1 = new FileUpload();
		FU1.setName("2");
		PanelUploaders.add(FU1);
		FU1.setWidth("100%");

		FU2 = new FileUpload();
		FU2.setName("3");
		PanelUploaders.add(FU2);
		FU2.setWidth("100%");

		FU3 = new FileUpload();
		FU3.setName("4");
		PanelUploaders.add(FU3);
		FU3.setSize("100%", "100%");

		SimplePanel simplePanel = new SimplePanel();
		verticalPanel_3.add(simplePanel);
		simplePanel.setSize("100%", "100%");

		PlusButton = new Button(PLUS);
		PlusButton.setHTML(PLUS);
		simplePanel.setWidget(PlusButton);
		PlusButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				FileUpload fileUploadMove = new FileUpload();
				actualFiles++;
				fileUploadMove.setName(Integer.toString(actualFiles));
				PanelUploaders.add(fileUploadMove);
				fileUploadMove.setSize("100%", "100%");
				fileUploadMove.getElement().setAttribute("accept", Accepnow);

			}
		});
		PlusButton.setSize("100%", "100%");
		PlusButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");

			}
		});

		PlusButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});

		PlusButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});

		PlusButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");

			}
		});

		PlusButton.setStyleName("gwt-ButtonCenter");

		SubmitButton = new Button(UPLOAD);
		SubmitButton.setHTML(UPLOAD);
		SubmitButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				// Recorro los uploaders para ver cuales estan llenos
				ArrayList<Widget> Listanueva = new ArrayList<Widget>();
				for (Widget widget : PanelUploaders) {
					FileUpload T = (FileUpload) widget;
					if (!T.getFilename().isEmpty()) {
						// Compativilidad
						Listanueva.add(T);
					}
				}

				// Elimino todos
				PanelUploaders.clear();

				// Relleno con los que estan llenos y los numero
				int i = 0;
				for (Widget widget : Listanueva) {
					FileUpload T = (FileUpload) widget;
					T.setName(Integer.toString(i));
					PanelUploaders.add(widget);
					i++;
				}

				// A subir!!!
				if ((!Listanueva.isEmpty())
						&& !TitleTextBox.getText().isEmpty()) {
					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto(
							ConstantsInformation.UPLOADING);
					form.submit();
				} else {
					Window.alert(ConstantsError.TEXT_NULL_OR_NO_IMAGEN);
					restarPanelOld();
				}

			}

			/**
			 * Recupera los paneles al estado antes de la subida
			 */
			private void restarPanelOld() {
				PanelUploaders.clear();
				if (PDFRadioButton.getValue()) {
					// Poner Panel Simple
					actualFiles = 1;
					PanelUploaders.add(FU);
					PanelUploaders.remove(FU1);
					PanelUploaders.remove(FU2);
					PanelUploaders.remove(FU3);
					SubmitButton.setEnabled(false);
					startNewBlobstoreSessionPDF();
					PlusButton.setVisible(false);
					Accepnow = AcceptPDF;
					putacceptInFileUploaders();

				} else {
					actualFiles = 4;
					PanelUploaders.add(FU);
					PanelUploaders.add(FU1);
					PanelUploaders.add(FU2);
					PanelUploaders.add(FU3);
					SubmitButton.setEnabled(false);
					startNewBlobstoreSessionSimple();
					PlusButton.setVisible(true);
					Accepnow = AcceptJPG;
					putacceptInFileUploaders();

				}

			}
		});
		verticalPanel_2.add(SubmitButton);
		SubmitButton.setWidth("100%");
		SubmitButton.setEnabled(false);
		SubmitButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");

			}
		});

		SubmitButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});

		SubmitButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});

		SubmitButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");

			}
		});

		SubmitButton.setStyleName("gwt-ButtonCenter");
		SimpleRadioButton.setValue(true);
		form.addSubmitCompleteHandler(Simple);
		startNewBlobstoreSessionSimple();
		
		Accepnow = AcceptJPG;
		putacceptInFileUploaders();
		
		PanelEdicion = new AbsolutePanel();
		if (ActualState.isLanguageActive())
			closeEditPanel();
	}

	/**
	 * Pone el tipo de elementos aceptados en el fileupload
	 */
	protected void putacceptInFileUploaders() {
		for (Widget uploader : PanelUploaders) {
			((FileUpload) uploader).getElement().setAttribute("accept",
					Accepnow);
		}

	}

	/**
	 * Inicia el sistema para subir archivos para la carga en JPG
	 */
	private void startNewBlobstoreSessionSimple() {
		String A = GWT.getHostPageBaseURL();

		A = A + "upload";
		form.setAction(A);
		SubmitButton.setEnabled(true);

	}

	/**
	 * Inicial el sistema para subir archivos en PDF
	 */
	private void startNewBlobstoreSessionPDF() {
		String A = GWT.getHostPageBaseURL();

		A = A + "pdf2pngserverlet";
		form.setAction(A);
		SubmitButton.setEnabled(true);

	}
	
	/**
	 * Funcion de cerrado del panel de edicion
	 */
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

	/**
	 * Funcion de apertura del panel de edicion
	 */
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

				if (!CloseMenuItemTextBox.getText().isEmpty())
					MENUITEMCLOSE = CloseMenuItemTextBox
							.getText();
				else
					MENUITEMCLOSE = MENUITEMCLOSE_RESET;

				if (!SimpleRadioButtonTextBox.getText().isEmpty())
					RADIO_BUTTON_SIMPLE_LABEL = SimpleRadioButtonTextBox.getText();
				else
					RADIO_BUTTON_SIMPLE_LABEL = RADIO_BUTTON_SIMPLE_LABEL_RESET;

				if (!PDFRadioButtonTextBox.getText().isEmpty())
					RADIO_BUTTON_PDF_LABEL = PDFRadioButtonTextBox.getText();
				else
					RADIO_BUTTON_PDF_LABEL = RADIO_BUTTON_PDF_LABEL_RESET;

				if (!AuthotLabelTextBox.getText().isEmpty())
					AUTORLABEL = AuthotLabelTextBox
							.getText();
				else
					AUTORLABEL = AUTORLABEL_RESET;
				
				if (!PublishYearLabelTextBox.getText().isEmpty())
					PUBLISHYEARLABEL = PublishYearLabelTextBox
							.getText();
				else
					PUBLISHYEARLABEL = PUBLISHYEARLABEL_RESET;

				if (!TittleLabelTextBox.getText().isEmpty())
					TITTLELABEL = TittleLabelTextBox
							.getText();
				else
					TITTLELABEL = TITTLELABEL_RESET;

				if (!SubmitButtonTextBox.getText().isEmpty())
					UPLOAD = SubmitButtonTextBox
							.getText();
				else
					UPLOAD = UPLOAD_RESET;


				ParsearFieldsAItems();
				SaveChages();
			}
		});

		CloseMenuItemTextBox = new TextBox();
		CloseMenuItemTextBox.setText(MENUITEMCLOSE);
		CloseMenuItemTextBox.setSize(
				CloseMenuItem.getOffsetWidth() + "px",
				CloseMenuItem.getOffsetHeight() + "px");
		PanelEdicion.add(CloseMenuItemTextBox,
				CloseMenuItem.getAbsoluteLeft(),
				CloseMenuItem.getAbsoluteTop());

		SimpleRadioButtonTextBox = new TextBox();
		SimpleRadioButtonTextBox.setText(RADIO_BUTTON_SIMPLE_LABEL);
		SimpleRadioButtonTextBox.setSize(SimpleRadioButton.getOffsetWidth()
				+ "px", SimpleRadioButton.getOffsetHeight() + "px");
		PanelEdicion.add(SimpleRadioButtonTextBox,
				SimpleRadioButton.getAbsoluteLeft(),
				SimpleRadioButton.getAbsoluteTop());

		PDFRadioButtonTextBox = new TextBox();
		PDFRadioButtonTextBox.setText(RADIO_BUTTON_PDF_LABEL);
		PDFRadioButtonTextBox.setSize(
				PDFRadioButton.getOffsetWidth() + "px",
				PDFRadioButton.getOffsetHeight() + "px");
		PanelEdicion.add(PDFRadioButtonTextBox,
				PDFRadioButton.getAbsoluteLeft(),
				PDFRadioButton.getAbsoluteTop());

		AuthotLabelTextBox = new TextBox();
		AuthotLabelTextBox.setText(AUTORLABEL);
		AuthotLabelTextBox.setSize(
				AuthotLabel.getOffsetWidth() + "px",
				AuthotLabel.getOffsetHeight() + "px");
		PanelEdicion.add(AuthotLabelTextBox,
				AuthotLabel.getAbsoluteLeft(),
				AuthotLabel.getAbsoluteTop());
		
		PublishYearLabelTextBox = new TextBox();
		PublishYearLabelTextBox.setText(PUBLISHYEARLABEL);
		PublishYearLabelTextBox.setSize(
				PublishYearLabel.getOffsetWidth() + "px",
				PublishYearLabel.getOffsetHeight() + "px");
		PanelEdicion.add(		PublishYearLabelTextBox,
				PublishYearLabel.getAbsoluteLeft(),
				PublishYearLabel.getAbsoluteTop());
		
		TittleLabelTextBox = new TextBox();
		TittleLabelTextBox.setText(TITTLELABEL);
		TittleLabelTextBox.setSize(
				TittleLabel.getOffsetWidth() + "px",
				TittleLabel.getOffsetHeight() + "px");
		PanelEdicion.add(TittleLabelTextBox,
				TittleLabel.getAbsoluteLeft(),
				TittleLabel.getAbsoluteTop());
		
		SubmitButtonTextBox = new TextBox();
		SubmitButtonTextBox.setText(UPLOAD);
		SubmitButtonTextBox.setSize(
				SubmitButton.getOffsetWidth() + "px",
				SubmitButton.getOffsetHeight() + "px");
		PanelEdicion.add(SubmitButtonTextBox,
				SubmitButton.getAbsoluteLeft(),
				SubmitButton.getAbsoluteTop());


	}

	/**
	 * Guarda los cambios en el lenguaje actual
	 */
	protected void SaveChages() {
		Language LanguageActual = ActualState.getActualLanguage();
		String BookUploadEntryPointLanguageConfiguration = toFile();
		LanguageActual
				.setBookUploadEntryPointLanguageConfiguration(BookUploadEntryPointLanguageConfiguration);
		ActualState.saveLanguageActual(LanguageActual);
	}

	/**
	 * Parsea los elementos con el texto actual
	 */
	public void ParsearFieldsAItems() {
		CloseMenuItem.setHTML(MENUITEMCLOSE);
		SimpleRadioButton.setHTML(RADIO_BUTTON_SIMPLE_LABEL);
		PDFRadioButton.setText(RADIO_BUTTON_PDF_LABEL);
		AuthotLabel.setText(AUTORLABEL);
		PublishYearLabel.setText(PUBLISHYEARLABEL);
		TittleLabel.setText(TITTLELABEL);
		SubmitButton.setText(UPLOAD);

	}

	/**
	 * Obtiene el texto a salvar de las etiquetras en el proceso de salvado en el lenguaje
	 * @return Texto con las etiquetas a salvar
	 */
	public static String toFile() {
		StringBuffer SB = new StringBuffer();
		SB.append(MENUITEMCLOSE + "\r\n");
		SB.append(RADIO_BUTTON_SIMPLE_LABEL + "\r\n");
		SB.append(RADIO_BUTTON_PDF_LABEL + "\r\n");
		SB.append(AUTORLABEL + "\r\n");
		SB.append(PUBLISHYEARLABEL + "\r\n");
		SB.append(TITTLELABEL + "\r\n");
		SB.append(UPLOAD + "\r\n");
		return SB.toString();
	}

	/**
	 * Carga el texto entrada en las etiquetas del sistema
	 * @param Entrada Texto que entrara en las etiquetas
	 */
	public static void FromFile(String Entrada) {
		if (Entrada.length()==0) 
			ParsearFieldsAItemsRESET();
		else
		{
		String[] Lista = Entrada.split("\r\n");
		if (Lista.length >= NCampos) {
			if (!Lista[0].isEmpty())
				MENUITEMCLOSE = Lista[0];
			else
				MENUITEMCLOSE = MENUITEMCLOSE_RESET;
			if (!Lista[1].isEmpty())
				RADIO_BUTTON_SIMPLE_LABEL = Lista[1];
			else
				RADIO_BUTTON_SIMPLE_LABEL = RADIO_BUTTON_SIMPLE_LABEL_RESET;
			if (!Lista[2].isEmpty())
				RADIO_BUTTON_PDF_LABEL = Lista[2];
			else
				RADIO_BUTTON_PDF_LABEL = RADIO_BUTTON_PDF_LABEL_RESET;
			if (!Lista[3].isEmpty())
				AUTORLABEL = Lista[3];
			else
				AUTORLABEL = AUTORLABEL_RESET;
			
			if (!Lista[4].isEmpty())
				PUBLISHYEARLABEL = Lista[4];
			else
				PUBLISHYEARLABEL = PUBLISHYEARLABEL_RESET;
			
			if (!Lista[5].isEmpty())
				TITTLELABEL = Lista[5];
			else
				TITTLELABEL = TITTLELABEL_RESET;
			
			if (!Lista[6].isEmpty())
				UPLOAD = Lista[6];
			else
				UPLOAD = UPLOAD_RESET;
			
		} else
			Logger.GetLogger().severe(
					BookUploadEntryPoint.class.toString(),
					ActualState.getUser().toString(),
					ConstantsError.ERROR_LOADING_LANGUAGE_IN
							+ BOOKUPLOADENTRYPOINT);
		ParsearFieldsAItemsRESET();
		}
	}
	
	/**
	 * Parsea los elementos a default en caso de error.
	 */
	private static void ParsearFieldsAItemsRESET() {
		MENUITEMCLOSE = MENUITEMCLOSE_RESET;
		RADIO_BUTTON_SIMPLE_LABEL = RADIO_BUTTON_SIMPLE_LABEL_RESET;
		RADIO_BUTTON_PDF_LABEL = RADIO_BUTTON_PDF_LABEL_RESET;
		AUTORLABEL = AUTORLABEL_RESET;
		PUBLISHYEARLABEL = PUBLISHYEARLABEL_RESET;
		TITTLELABEL = TITTLELABEL_RESET;
		UPLOAD = UPLOAD_RESET;
		
	}
}
