package lector.client.login;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.controler.ErrorConstants;
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
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

public class UserEdition implements EntryPoint {

	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private TextBox NameText;
	private TextBox ApellidosText;
	
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		RootPanel RootMenu = RootPanel.get("Menu");
		
		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);
		
		MenuItem CloseBoton = new MenuItem("Close", false, new Command() {
			public void execute() {
				if (ActualState.getUser() instanceof ProfessorClient)
					Controlador.change2Administrator();
				else if (ActualState.getUser() instanceof StudentClient)
					 Controlador.change2MyActivities();
			}
		});
		menuBar.addItem(CloseBoton);
		
		SimplePanel simplePanel = new SimplePanel();
		rootPanel.add(simplePanel,0,25);
		simplePanel.setSize("100%", "100%");
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setSpacing(10);
		verticalPanel_1.setStyleName("fondoLogo");
		verticalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		simplePanel.setWidget(verticalPanel_1);
		verticalPanel_1.setSize("100%", "100%");
		
		VerticalPanel PanelCampos = new VerticalPanel();
		PanelCampos.setStyleName("AzulTransparente");
		verticalPanel_1.add(PanelCampos);
		PanelCampos.setSpacing(10);
		PanelCampos.setWidth("");
		
		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2.setStyleName("AzulTransparente");
		horizontalPanel_2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		PanelCampos.add(horizontalPanel_2);
		horizontalPanel_2.setWidth("100%");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setStyleName("BlancoTransparente");
		horizontalPanel_2.add(horizontalPanel);
		horizontalPanel.setWidth("70px");
		
		Label lblNewLabel = new Label("First Name");
		horizontalPanel.add(lblNewLabel);
		
		
		NameText = new TextBox();
		NameText.setMaxLength(25);
		NameText.setVisibleLength(25);
		horizontalPanel_2.add(NameText);
		NameText.setWidth("90%");
		NameText.setText(ActualState.getUser().getFirstName());
		
		HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
		horizontalPanel_3.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		PanelCampos.add(horizontalPanel_3);
		horizontalPanel_3.setWidth("100%");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_3.add(horizontalPanel_1);
		horizontalPanel_1.setWidth("70px");
		
		Label lblNewLabel_1 = new Label("Last Name");
		lblNewLabel_1.setStyleName("BlancoTransparente");
		horizontalPanel_1.add(lblNewLabel_1);
		
		ApellidosText = new TextBox();
		ApellidosText.setVisibleLength(25);
		ApellidosText.setMaxLength(120);
		horizontalPanel_3.add(ApellidosText);
		ApellidosText.setWidth("90%");
		ApellidosText.setText(ActualState.getUser().getLastName());
		
		HorizontalPanel horizontalPanel_4 = new HorizontalPanel();
		horizontalPanel_4.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_4.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		PanelCampos.add(horizontalPanel_4);
		horizontalPanel_4.setWidth("100%");
					
					VerticalPanel horizontalPanel_5 = new VerticalPanel();
					horizontalPanel_5.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
					horizontalPanel_5.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
					horizontalPanel_5.setStyleName("BlancoTransparente");
					horizontalPanel_4.add(horizontalPanel_5);
					horizontalPanel_5.setWidth("79px");
					
					HorizontalPanel horizontalPanel_6 = new HorizontalPanel();
					horizontalPanel_5.add(horizontalPanel_6);
					
					Button btnNewButton = new Button("Save");
					horizontalPanel_6.add(btnNewButton);
					btnNewButton.addClickHandler(new ClickHandler() {
						
						public void onClick(ClickEvent event) {
							((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
							
						}
					});
					
						btnNewButton.addMouseDownHandler(new MouseDownHandler() {
							public void onMouseDown(MouseDownEvent event) {
								((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
							}
						});
						

						btnNewButton.addMouseOutHandler(new MouseOutHandler() {
							public void onMouseOut(MouseOutEvent event) {
								((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
						}
	});
						

						btnNewButton.addMouseOverHandler(new MouseOverHandler() {
							public void onMouseOver(MouseOverEvent event) {
								
								((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
							
						}
	});
						
								btnNewButton.setStyleName("gwt-ButtonCenter");
								btnNewButton.addClickHandler(new ClickHandler() {
									private UserClient AU;

									public void onClick(ClickEvent event) {
										AU=ActualState.getUser();
										AU.setFirstName(NameText.getText());
										AU.setLastName(ApellidosText.getText());
										LoadingPanel.getInstance().center();
										LoadingPanel.getInstance().setLabelTexto("Updating...");
									
										bookReaderServiceHolder.saveUser(AU, new AsyncCallback<Void>() {
											
											public void onSuccess(Void result) {
												
												ActualState.setUser(AU);
												LoadingPanel.getInstance().hide();
												if (ActualState.getUser() instanceof ProfessorClient)
													Controlador.change2Administrator();
												else if (ActualState.getUser() instanceof StudentClient)
													Controlador.change2MyActivities();
																
														
													}
													

											
											public void onFailure(Throwable caught) {
												Window.alert(ErrorConstants.ERROR_UPDATING_USER_DATA);
												LoadingPanel.getInstance().hide();
												
											}
										});
									}
								});

	}

}
