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

	
	private String RADIO_BUTTON_SIMPLE_LABEL="Simple";
	private String RADIO_BUTTON_PDF_LABEL="PDF";
	
	static ImageServiceAsync userImageService = GWT.create(ImageService.class);
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private TextBox author;
	private TextBox publishedYear;
	private TextBox title;
	private TextBox userApp;
	private int actualFiles;
	private VerticalPanel PanelUploaders;
	private FormPanel form;
	private FileUpload FU;
	private Button submitButton;
	private FileUpload FU1;
	private FileUpload FU2;
	private FileUpload FU3;
	private RadioButton SimpleRadioButton;
	private RadioButton PDFRadioButton;
	private Button PlusButton;
	private String UPLOAD= "Upload";
	

	private static final String radioButtonGroup="RBGroup";
	
	public void onModuleLoad() {

		RootPanel RP = RootPanel.get();
		RP.setSize("100%", "100%");

		MenuBar menuBar = new MenuBar(false);
		RP.add(menuBar,0,0);
		menuBar.setWidth("100%");

		MenuItem mntmNewItem = new MenuItem("Close", false, new Command() {

			public void execute() {
				Controlador.change2BookAdminstrator();

			}
		});
		menuBar.addItem(mntmNewItem);

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
					submitButton.setEnabled(false);
					startNewBlobstoreSessionPDF();
					PlusButton.setVisible(false);
					
				}
				else 
				{
					actualFiles=4;
					PanelUploaders.clear();
					PanelUploaders.add(FU);
					PanelUploaders.add(FU1);
					PanelUploaders.add(FU2);
					PanelUploaders.add(FU3);
					submitButton.setEnabled(false);
					startNewBlobstoreSessionSimple();
					PlusButton.setVisible(true);
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
		
		Label lblAutor = new Label("Autor:");
		lblAutor.setStyleName("gwt-LabelLoad");
		horizontalPanel_2.add(lblAutor);

		HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
		horizontalPanel_3.setSpacing(1);
		horizontalPanel_3
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		verticalPanel_2.add(horizontalPanel_3);
		horizontalPanel_3.setWidth("100%");

		author = new TextBox();
		horizontalPanel_3.add(author);
		author.setWidth("393px");
		author.setName(Constants.BLOB_AUTHOR);

		HorizontalPanel horizontalPanel_5 = new HorizontalPanel();
		verticalPanel_2.add(horizontalPanel_5);

		Label lblNewLabel = new Label("Published Year:");
		lblNewLabel.setStyleName("gwt-LabelLoad");
		horizontalPanel_5.add(lblNewLabel);

		HorizontalPanel horizontalPanel_6 = new HorizontalPanel();
		horizontalPanel_6
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		verticalPanel_2.add(horizontalPanel_6);
		horizontalPanel_6.setWidth("100%");
		publishedYear = new TextBox();
		horizontalPanel_6.add(publishedYear);
		publishedYear.setWidth("393px");
		publishedYear.setName(Constants.BLOB_PUBLISHED_YEAR);

		HorizontalPanel horizontalPanel_8 = new HorizontalPanel();
		verticalPanel_2.add(horizontalPanel_8);

		Label lblNewLabel_1 = new Label("Title:");
		lblNewLabel_1.setStyleName("gwt-LabelLoad");
		horizontalPanel_8.add(lblNewLabel_1);

		HorizontalPanel horizontalPanel_7 = new HorizontalPanel();
		horizontalPanel_7
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		verticalPanel_2.add(horizontalPanel_7);
		horizontalPanel_7.setWidth("100%");
		title = new TextBox();
		horizontalPanel_7.add(title);
		title.setWidth("393px");
		title.setName(Constants.BLOB_TITLE);

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

		PlusButton = new Button("+");
		simplePanel.setWidget(PlusButton);
		PlusButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				FileUpload fileUploadMove = new FileUpload();
				actualFiles++;
				fileUploadMove.setName(Integer.toString(actualFiles));
				PanelUploaders.add(fileUploadMove);
				fileUploadMove.setSize("100%", "100%");

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

		submitButton = new Button(UPLOAD);
		submitButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ArrayList<Widget> Listanueva = new ArrayList<Widget>();
				for (Widget widget : PanelUploaders) {
					FileUpload T = (FileUpload) widget;
					if (!T.getFilename().isEmpty()) {
						// Compativilidad
						Listanueva.add(T);
					}
				}
				PanelUploaders.clear();
				int i = 0;
				for (Widget widget : Listanueva) {
					FileUpload T = (FileUpload) widget;
					T.setName(Integer.toString(i));
					PanelUploaders.add(widget);
					// Window.alert(T.getFilename() + " : " + i);
					i++;
				}
				if ((!Listanueva.isEmpty()) && !title.getText().isEmpty())
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

			private void restarPanelOld() {
				PanelUploaders.clear();
				if (PDFRadioButton.getValue()){
					//Poner Panel Simple
						actualFiles=1;
						PanelUploaders.add(FU);
						PanelUploaders.remove(FU1);
						PanelUploaders.remove(FU2);
						PanelUploaders.remove(FU3);
						submitButton.setEnabled(false);
						startNewBlobstoreSessionPDF();
						PlusButton.setVisible(false);
						
					}
					else 
					{
						actualFiles=4;
						PanelUploaders.add(FU);
						PanelUploaders.add(FU1);
						PanelUploaders.add(FU2);
						PanelUploaders.add(FU3);
						submitButton.setEnabled(false);
						startNewBlobstoreSessionSimple();
						PlusButton.setVisible(true);
					}
				
			}
		});
		verticalPanel_2.add(submitButton);
		submitButton.setWidth("100%");
		submitButton.setEnabled(false);
		submitButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");

			}
		});

		submitButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});

		submitButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});

		submitButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");

			}
		});

		submitButton.setStyleName("gwt-ButtonCenter");

		// form.addSubmitHandler(new FormPanel.SubmitHandler() {
		// public void onSubmit(SubmitEvent event) {
		// if (!"".equalsIgnoreCase(fileUpload.getFilename())) {
		// GWT.log("UPLOADING FILE????" + fileUpload.getFilename(),
		// null);
		// // NOW WHAT????
		// } else {
		// event.cancel(); // cancel the event
		// }
		//
		// }
		// });
		
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

	private void startNewBlobstoreSessionSimple() {
		String A=GWT.getHostPageBaseURL();
		
		A=A+"upload";
				form.setAction(A);
				submitButton.setEnabled(true);

	}
	
	private void startNewBlobstoreSessionPDF() {
		String A=GWT.getHostPageBaseURL();
		
		A=A+"pdf2pngserverlet";
				form.setAction(A);
				submitButton.setEnabled(true);

	}
}
