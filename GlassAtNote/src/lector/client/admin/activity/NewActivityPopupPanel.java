package lector.client.admin.activity;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.ErrorConstants;
import lector.client.reader.LoadingPanel;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.ReadingActivityClient;
import lector.client.controler.InformationConstants;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
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

public class NewActivityPopupPanel extends PopupPanel {

	private static final String ACTIVITY_ADMINISTRATION = "New Activity Popup";
	
	private static final int NCampos=1;
	
	private static final int DecoradorWidth = 2;


	
	private String INSERT_NAME_LABEL="Insert the name for the new Activity";
	private String CREATE_BUTTON = "Create";
	private String CANCEL_BUTTON = "Cancel";
	
	private String INSERT_NAME_LABEL_RESET="Insert the name for the new Activity";
	private String CREATE_BUTTON_RESET = "Create";
	private String CANCEL_BUTTON_RESET = "Cancel";
	
	private Label InsertNameLabel;
	private Button CreateButton;
	private Button CancelButton;
	
	private TextBox InsertNameLabelTextBox;
	private TextBox CreateButtonTextBox;
	private Button CancelButtonTextBox;
	
	private TextBox InsertionTextTextBox;
	private PopupPanel Yo;
	private NewAdminActivities Father;
	private AbsolutePanel General;

	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);

	public NewActivityPopupPanel(NewAdminActivities Fatherin) {
		super(true);
		this.Father = Fatherin;
		Yo = this;
		General = new AbsolutePanel();
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setSpacing(3);
		//setWidget(verticalPanel);
		setWidget(General);
		General.setSize("269px", "160px");
		General.add(verticalPanel,0,0);
		verticalPanel.setSize("100%", "100%");

		InsertNameLabel = new Label(INSERT_NAME_LABEL);
		verticalPanel.add(InsertNameLabel);
		InsertNameLabel.setSize("100%", "100%");

		InsertionTextTextBox = new TextBox();
		verticalPanel.add(InsertionTextTextBox);
		InsertionTextTextBox.setWidth("50%");

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setSpacing(4);
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setWidth("100%");

		CreateButton = new Button(CREATE_BUTTON);
		CreateButton.setSize("100%", "100%");
		CreateButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		CreateButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});
		CreateButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			}
		});
		CreateButton.setStyleName("gwt-ButtonCenter");
		CreateButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				String S = InsertionTextTextBox.getText();
				if (S.isEmpty())
					Window.alert(InformationConstants.THE_NAME_CAN_NOT_BE_EMPTY);
				else {
					ReadingActivityClient A = new ReadingActivityClient();
					A.setName(S);
					A.setProfessor((ProfessorClient)ActualState.getUser());
					A.setIsFreeTemplateAllowed(true);
					A.setVisualization(Constants.VISUAL_ARBOL);
					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto(InformationConstants.SAVING);
					bookReaderServiceHolder.saveReadingActivity(A,
							new AsyncCallback<Void>() {

								public void onFailure(Throwable caught) {
									Window.alert(ErrorConstants.ERROR_SAVING_ACTIVITY);
									LoadingPanel.getInstance().hide();
								}

								public void onSuccess(Void result) {
									LoadingPanel.getInstance().hide();
									Father.refresh();
									Yo.hide();
									
								}
							});
				}

			}
		});
		horizontalPanel.add(CreateButton);

		CancelButton = new Button(CANCEL_BUTTON);
		CancelButton.setSize("100%", "100%");
		CancelButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		CancelButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});
		CancelButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			}
		});
		CancelButton.setStyleName("gwt-ButtonCenter");
		CancelButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Yo.hide();
			}
		});
		horizontalPanel.add(CancelButton);
		super.setGlassEnabled(true);
	}

}
