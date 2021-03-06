package lector.client.admin.catalog;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.CalendarNow;
import lector.client.controler.ConstantsError;
import lector.client.controler.ConstantsInformation;
import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;
import lector.share.model.Catalogo;
import lector.share.model.client.CatalogoClient;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.CheckBox;

public class NewCatalogPopupPanel extends PopupPanel {

	public static String INSERT_NAME_NEW_CATALOG_LABEL="Insert the name for the new Catalogue and Visibility\r\n";
	
	private PopupPanel Me;
	private CatalogAdmintrationEntryPoint Father;
	private TextBox textBox;
	private CheckBox chckbxNewCheckBox;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private CatalogoClient NuevoC;

	public NewCatalogPopupPanel(CatalogAdmintrationEntryPoint Fatherin) {
		super(true);
		this.Father = Fatherin;
		Me = this;
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setSpacing(3);
		setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");

		Label lblInsertTheName = new Label(NewCatalogPopupPanel.INSERT_NAME_NEW_CATALOG_LABEL);
		verticalPanel.add(lblInsertTheName);
		lblInsertTheName.setSize("100%", "100%");

		textBox = new TextBox();
		verticalPanel.add(textBox);
		textBox.setWidth("98%");
		
		chckbxNewCheckBox = new CheckBox("Private");
		chckbxNewCheckBox.setHTML("Private");
		verticalPanel.add(chckbxNewCheckBox);

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setSpacing(4);
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setWidth("100%");

		Button btnNewButton = new Button("Create");
		btnNewButton.setSize("100%", "100%");
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
			public void onClick(ClickEvent event) {

				String S = textBox.getText();
				if (S.isEmpty() || S.length() < 2)
					Window.alert("The Name can be more lenght or equal than two");
				else {
					CatalogoClient C = new CatalogoClient();
					C.setCatalogName(S);
					C.setProfessorId(ActualState.getUser().getId());
					C.setIsPrivate(chckbxNewCheckBox.getValue());
					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto(ConstantsInformation.SAVING);
					NuevoC=C;
					bookReaderServiceHolder.saveCatalog(C,
							new AsyncCallback<Void>() {

								public void onSuccess(Void result) {
									Logger.GetLogger().info(this.getClass().getName(),
											ActualState.getUser().toString(),
											"Create a catalog " + NuevoC.getCatalogName());
									Father.refresh();
									LoadingPanel.getInstance().hide();

								}

								public void onFailure(Throwable caught) {
									LoadingPanel.getInstance().hide();
									Window.alert(ConstantsError.ERROR_SAVING_CATALOG);
									Logger.GetLogger().severe(this.getClass().getName(),
											ActualState.getUser().toString(),
											"Create a catalog " + NuevoC.getCatalogName());

								}
							});

					Me.hide();
				}

			}
		});
		horizontalPanel.add(btnNewButton);

		Button btnNewButton_1 = new Button("Cancel");
		btnNewButton_1.setSize("100%", "100%");
		btnNewButton_1.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		btnNewButton_1.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});
		btnNewButton_1.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			}
		});
		btnNewButton_1.setStyleName("gwt-ButtonCenter");
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Me.hide();
			}
		});
		horizontalPanel.add(btnNewButton_1);
		super.setGlassEnabled(true);
	}

}
