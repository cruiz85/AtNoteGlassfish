package lector.client.admin.book.upload;

import java.util.ArrayList;

import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.reader.LoadingPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
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
import lector.share.model.client.UserClient;

public class BookUploadEntryPoint implements EntryPoint {

private static final String BOOKUPLOADENTRYPOINT = "Book upoload entry point";
	
	private static final int NCampos=2;
	
	private static final int DecoradorWidth = 2;
	
	private static String MENUITEMCLOSE = "Close";
	private static String RADIO_BUTTON_SIMPLE_LABEL="Simple";
	private static String RADIO_BUTTON_PDF_LABEL="PDF";
	private static String AUTORLABEL = "Autor:";
	private static String PUBLISHYEARLABEL = "Published Year:";
	private static String TITTLELABEL = "Title:";
	private static String UPLOAD= "Upload";
	
	private static final String MENUITEMCLOSE_RESET = "Close";
	private static final String RADIO_BUTTON_SIMPLE_LABEL_RESET="Simple";
	private static final String RADIO_BUTTON_PDF_LABEL_RESET="PDF";
	private static final String AUTORLABEL_RESET = "Autor:";
	private static final String PUBLISHYEARLABEL_RESET = "Published Year:";
	private static final String TITTLELABEL_RESET = "Title:";
	private static final String UPLOAD_RESET= "Upload";
	
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
	
	
	private static final String AcceptJPG="image/png, image/gif,image/jpeg";
	private static final String AcceptPDF="application/pdf";
	private static final String radioButtonGroup="RBGroup";
	private static final String PLUS = "+";

	

	
	
	/**
	 * Metodo de entrada que pinta la ventana para el entry point
	 */
	public void onModuleLoad() {

		RootPanel RP = RootPanel.get();
		RP.setSize("100%", "100%");

		MenuBar menuBar = new MenuBar(false);
		RP.add(menuBar,0,0);
		menuBar.setWidth("100%");

		CloseMenuItem = new MenuItem(MENUITEMCLOSE, false, new Command() {

			public void execute() {
				Controlador.change2BookAdminstrator();

			}
		});
		menuBar.addItem(CloseMenuItem);
		CloseMenuItem.setHTML(MENUITEMCLOSE);

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setStyleName("fondoLogo");
		RP.add(horizontalPanel,0,25);
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
		
		SimpleRadioButton =new RadioButton(BookUploadEntryPoint.radioButtonGroup, RADIO_BUTTON_SIMPLE_LABEL);
		PDFRadioButton =new RadioButton(BookUploadEntryPoint.radioButtonGroup, RADIO_BUTTON_PDF_LABEL);
		
		ClickHandler event=new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (event.getSource() == PDFRadioButton){
				//Poner Panel Simple
					actualFiles=1;
					PanelUploaders.clear();
					PanelUploaders.add(FU);
					PanelUploaders.remove(FU1);
					PanelUploaders.remove(FU2);
					PanelUploaders.remove(FU3);
					SubmitButton.setEnabled(false);
					startNewBlobstoreSessionPDF();
					PlusButton.setVisible(false);
					Accepnow=AcceptPDF;
					putacceptInFileUploaders();	
				}
				else 
				{
					actualFiles=4;
					PanelUploaders.clear();
					PanelUploaders.add(FU);
					PanelUploaders.add(FU1);
					PanelUploaders.add(FU2);
					PanelUploaders.add(FU3);
					SubmitButton.setEnabled(false);
					startNewBlobstoreSessionSimple();
					PlusButton.setVisible(true);
					Accepnow=AcceptJPG;
					putacceptInFileUploaders();
				}
			}
		};
		
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

		Accepnow=AcceptJPG;
		putacceptInFileUploaders();
		
		
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
				
				//Recorro los uploaders para ver cuales estan llenos
				ArrayList<Widget> Listanueva = new ArrayList<Widget>();
				for (Widget widget : PanelUploaders) {
					FileUpload T = (FileUpload) widget;
					if (!T.getFilename().isEmpty()) {
						// Compativilidad
						Listanueva.add(T);
					}
				}
				
				//Elimino todos
				PanelUploaders.clear();
				
				//Relleno con los que estan llenos y los numero
				int i = 0;
				for (Widget widget : Listanueva) {
					FileUpload T = (FileUpload) widget;
					T.setName(Integer.toString(i));
					PanelUploaders.add(widget);
					i++;
				}
				
				//A subir!!!
				if ((!Listanueva.isEmpty()) && !TitleTextBox.getText().isEmpty())
					{
					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto(InformationConstants.UPLOADING);
					form.submit();
					}
				else
					{
					Window.alert(ErrorConstants.TEXT_NULL_OR_NO_IMAGEN);
					restarPanelOld();
					}
				
			}

			/**
			 * Recupera los paneles al estado antes de la subida
			 */
			private void restarPanelOld() {
				PanelUploaders.clear();
				if (PDFRadioButton.getValue()){
					//Poner Panel Simple
						actualFiles=1;
						PanelUploaders.add(FU);
						PanelUploaders.remove(FU1);
						PanelUploaders.remove(FU2);
						PanelUploaders.remove(FU3);
						SubmitButton.setEnabled(false);
						startNewBlobstoreSessionPDF();
						PlusButton.setVisible(false);
						Accepnow=AcceptPDF;
						putacceptInFileUploaders();
						
						
					}
					else 
					{
						actualFiles=4;
						PanelUploaders.add(FU);
						PanelUploaders.add(FU1);
						PanelUploaders.add(FU2);
						PanelUploaders.add(FU3);
						SubmitButton.setEnabled(false);
						startNewBlobstoreSessionSimple();
						PlusButton.setVisible(true);
						Accepnow=AcceptJPG;
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

		
		SubmitCompleteHandler Simple = new FormPanel.SubmitCompleteHandler() {

		
			public void onSubmitComplete(SubmitCompleteEvent event) {
				
				LoadingPanel.getInstance().hide();
				
				//startNewBlobstoreSession();
				String results=event.getResults();
				results=results.replaceAll("\\<.*?\\>","");
				if ((event.getResults()!=null)&&!(results.isEmpty()))
					Window.alert(event.getResults());
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto(InformationConstants.LOADING);
				bookReaderServiceHolder.loadUserById(ActualState.getUser()
						.getId(), new AsyncCallback<UserClient>() {

					public void onSuccess(UserClient result) {
						ActualState.setUser(result);
						Controlador.change2BookAdminstrator();
						// form.reset();
						LoadingPanel.getInstance().hide();

					}

					public void onFailure(Throwable caught) {
						Window.alert(ErrorConstants.ERROR_LOAD_FORCE_LOGIN);
						Window.Location.reload();
						LoadingPanel.getInstance().hide();
					}
				});

			}
		};
		
		
		
		

		

		actualFiles = 4;
		SimpleRadioButton.setValue(true);
		form.addSubmitCompleteHandler(Simple);
		startNewBlobstoreSessionSimple();
	}

	/**
	 * Pone el tipo de elementos aceptados en el fileupload
	 */
	protected void putacceptInFileUploaders() {
		for (Widget uploader : PanelUploaders) {
			((FileUpload)uploader).getElement().setAttribute("accept", Accepnow);
		}
		
	}

	/**
	 * Inicia el sistema para subir archivos para la carga en JPG
	 */
	private void startNewBlobstoreSessionSimple() {
		String A=GWT.getHostPageBaseURL();
		
		A=A+"upload";
				form.setAction(A);
				SubmitButton.setEnabled(true);

	}
	
	/**
	 * Inicial el sistema para subir archivos en PDF
	 */
	private void startNewBlobstoreSessionPDF() {
		String A=GWT.getHostPageBaseURL();
		
		A=A+"pdf2pngserverlet";
				form.setAction(A);
				SubmitButton.setEnabled(true);

	}
}
