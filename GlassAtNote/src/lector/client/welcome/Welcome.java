package lector.client.welcome;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.book.reader.ImageService;
import lector.client.book.reader.ImageServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.logger.Logger;
import lector.share.model.LocalBook;
import lector.share.model.Professor;
import lector.share.model.Student;
import lector.share.model.UserApp;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.StudentClient;
import lector.share.model.client.UserClient;

import com.google.appengine.api.datastore.Text;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.FocusPanel;

public class Welcome implements EntryPoint {
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	static ImageServiceAsync imageServiceHolder = GWT
			.create(ImageService.class);
	private Button btnNewButton;
	private HorizontalPanel horizontalPanel;
	private RootPanel Footer;

////	 DESCOMENTAR EN DESARROLLO, CREA UN USUARIO ROOT.
//	 private void callUserRoot() {
//	 UserApp adminUser2 = new UserApp();
//	 adminUser2.setEmail("root");
//	 adminUser2.setProfile(Constants.PROFESSOR);
//	 bookReaderServiceHolder.saveUser(adminUser2,
//	 new AsyncCallback<Boolean>() {
//	 public void onSuccess(Boolean result) {
//	 }
//	
//	 public void onFailure(Throwable caught) {
//	 Window.alert("Ha fallado Cesar");
//	 }
//	 });
//	 }
	FormPanel formPanel = new FormPanel();

	public void onModuleLoad() {
//		 callUserRoot();
	
//		// POST request test

//		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
//		formPanel.setMethod(FormPanel.METHOD_POST);
//		formPanel.addStyleName("table-center");
//		formPanel.addStyleName("demo-FormPanel");
//		TextArea textArea = new TextArea();
//		textArea.setText("Esto es una descripción");
//		textArea.setName("description");
//		formPanel.add(textArea);
//		formPanel.setAction("http://127.0.0.1:8888/rs/AtNote/html/produce");
//		formPanel.submit();
//		
		Button button = new Button("loading");
		button.setEnabled(false);
		button.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				formPanel.submit();
			}
		});

		RootPanel rootPanel = RootPanel.get();
		Footer = RootPanel.get("footer");
		rootPanel.setSize("100%", "100%");
		rootPanel.setStyleName("Root");

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setStyleName("fondoLogo");
		rootPanel.add(verticalPanel, 0, 0);
		// rootPanel.add(verticalPanel);
		verticalPanel.setSize("100%", "100%");

		VerticalPanel verticalPanel_1 = new VerticalPanel();
//		verticalPanel_1.setStyleName("fondoLogo");
		verticalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_1
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(verticalPanel_1);

		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		verticalPanel_1.add(horizontalPanel_1);
		horizontalPanel_1
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		Image image_1 = new Image("Logo.jpg");
		image_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PopupPanel About = new About();
				About.center();
			}
		});
		//horizontalPanel_1.add(image_1);
		
		FocusPanel simplePanel = new FocusPanel();
		simplePanel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PopupPanel About = new About();
				About.center();
			}
		});
		horizontalPanel_1.add(simplePanel);
		simplePanel.setSize("728px", "343px");

		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2.setStyleName("AzulTransparente");
		horizontalPanel_2
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_2.setSpacing(10);
		verticalPanel_1.add(horizontalPanel_2);
		
		HorizontalPanel horizontalPanel_6 = new HorizontalPanel();
		horizontalPanel_6.setStyleName("BlancoTransparente");
		horizontalPanel_6.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_6.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_2.add(horizontalPanel_6);
		horizontalPanel_6.setWidth("90px");
		
				Image image = new Image("logo_ucm.jpg");
				horizontalPanel_6.add(image);
				image.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						Window.open("http://www.ucm.es", "_blank", null);

					}
				});
		image.setSize("75px", "78px");

		HorizontalPanel horizontalPanel_4 = new HorizontalPanel();
		horizontalPanel_2.add(horizontalPanel_4);
		
		HorizontalPanel horizontalPanel_7 = new HorizontalPanel();
		horizontalPanel_7.setStyleName("BlancoTransparente");
		horizontalPanel_7.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_7.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_2.add(horizontalPanel_7);
		horizontalPanel_7.setWidth("180px");
		
				Image image_2 = new Image("logo-leethi_fa.gif");
				horizontalPanel_7.add(image_2);
				image_2.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						Window.open("http://www.ucm.es/info/leethi/", "_blank", null);

					}
				});
		image_2.setSize("164px", "72px");

		HorizontalPanel horizontalPanel_5 = new HorizontalPanel();
		horizontalPanel_2.add(horizontalPanel_5);
		
		HorizontalPanel horizontalPanel_8 = new HorizontalPanel();
		horizontalPanel_8.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_8.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_8.setStyleName("BlancoTransparente");
		horizontalPanel_2.add(horizontalPanel_8);
		horizontalPanel_8.setWidth("220px");

		Image image_3 = new Image("ISLA.jpg");
		horizontalPanel_8.add(image_3);
		image_3.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.open("http://ilsa.fdi.ucm.es/", "_blank", null);

			}
		});
		image_3.setSize("201px", "69px");

		Label lblNewLabel_1 = new Label(
				"Developers: Cesar Ruiz, Joaquin Gayoso");
		// verticalPanel_1.add(lblNewLabel_1);
		SimplePanel S = new SimplePanel();
		Footer.add(S);
		S.setWidget(lblNewLabel_1);
		lblNewLabel_1.setStyleName("Desarrolladores");

		HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
		verticalPanel_1.add(horizontalPanel_3);
		horizontalPanel_3.setHeight("70px");

		horizontalPanel = new HorizontalPanel();
		verticalPanel_1.add(horizontalPanel);

		Label lblCollaborativeAnnotationOf = new Label(
				"Collaborative Annotation of Digitalized Literary Texts");
		lblCollaborativeAnnotationOf.setStyleName("TituloWelcome");
		// verticalPanel.add(lblCollaborativeAnnotationOf);
		lblCollaborativeAnnotationOf.setWidth("1112px");
		 
		 HorizontalPanel horizontalPanel_9 = new HorizontalPanel();
		 horizontalPanel_9.setSpacing(5);
		 horizontalPanel_9.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		 horizontalPanel_9.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		 horizontalPanel_9.setStyleName("AzulTransparente");
		 horizontalPanel.add(horizontalPanel_9);
		 horizontalPanel_9.setWidth("184px");
		 
		 HorizontalPanel horizontalPanel_10 = new HorizontalPanel();
		 horizontalPanel_10.setSpacing(3);
		 horizontalPanel_10.setStyleName("BlancoTransparente");
		 horizontalPanel_9.add(horizontalPanel_10);

		 btnNewButton = new Button("Log In");
		 horizontalPanel_10.add(btnNewButton);
		 btnNewButton.addClickHandler(new ClickHandler() {
		 public void onClick(ClickEvent event) {
		 Controlador.change2Login();
		 Footer.clear();
		 }
		 });
		 btnNewButton.setText("Log In");
		 
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
	btnNewButton.setSize("164px", "50px");
		
	}
}
