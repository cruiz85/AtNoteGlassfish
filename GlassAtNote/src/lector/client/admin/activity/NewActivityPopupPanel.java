package lector.client.admin.activity;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.ErrorConstants;
import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;
import lector.share.model.Language;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.ReadingActivityClient;
import lector.client.controler.InformationConstants;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
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
	
	private static final int NCampos=3;
	
	private static final int DecoradorWidth = 2;


	
	private static String INSERT_NAME_LABEL="Insert the name for the new Activity";
	private static String CREATE_BUTTON = "Create";
	private static String CANCEL_BUTTON = "Cancel";
	
	private static final String INSERT_NAME_LABEL_RESET="Insert the name for the new Activity";
	private static final String CREATE_BUTTON_RESET = "Create";
	private static final String CANCEL_BUTTON_RESET = "Cancel";
	
	private Label InsertNameLabel;
	private Button CreateButton;
	private Button CancelButton;
	
	private TextBox InsertNameLabelTextBox;
	private TextBox CreateButtonTextBox;
	private TextBox CancelButtonTextBox;
	
	private TextBox InsertionTextTextBox;
	private PopupPanel Yo;
	private AdminActivitiesEntryPoint Father;
	private AbsolutePanel GeneralPanel;

	private AbsolutePanel PanelEdicion;

	private VerticalPanel PanelActivity;

	private SimplePanel EditorZone;

	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);

	public NewActivityPopupPanel(AdminActivitiesEntryPoint Fatherin) {
		super(true);
		this.Father = Fatherin;
		Yo = this;
		setAnimationEnabled(true);
		GeneralPanel = new AbsolutePanel();
		PanelActivity = new VerticalPanel();
		EditorZone=new SimplePanel();
		EditorZone.setHeight(Constants.TAMANO_PANEL_EDICION);
		PanelActivity.add(EditorZone);
		PanelActivity.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		PanelActivity.setSpacing(3);
		//setWidget(verticalPanel);
		setWidget(GeneralPanel);
		GeneralPanel.setSize("269px", "160px");
		GeneralPanel.add(PanelActivity,0,0);
		PanelActivity.setSize("100%", "100%");

		InsertNameLabel = new Label(INSERT_NAME_LABEL);
		PanelActivity.add(InsertNameLabel);
		InsertNameLabel.setSize("100%", "100%");

		InsertionTextTextBox = new TextBox();
		PanelActivity.add(InsertionTextTextBox);
		InsertionTextTextBox.setWidth("50%");

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setSpacing(4);
		PanelActivity.add(horizontalPanel);
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
		PanelEdicion=new AbsolutePanel();
	}
	
	@Override
	public void center() {
		super.center();
		EditorZone.setVisible(false);
		if (ActualState.isLanguageActive())
			{
			EditorZone.setVisible(true);
			closeEditPanel();
			}
	}
	
	private void closeEditPanel() {
		GeneralPanel.remove(PanelEdicion);
		GeneralPanel.add(PanelEdicion,PanelActivity.getOffsetWidth()-40, 0);
		PanelEdicion.setSize("40px","50px");
		PanelEdicion.clear();
		PanelEdicion.setStyleName("");
		Button Boton=new Button();
		PanelEdicion.add(Boton,0,0);
		Boton.setHTML(InformationConstants.EDIT_BOTTON);
		Boton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				OpenEditPanel();
				
			}
		});
		
	}

	public void OpenEditPanel() {
		GeneralPanel.remove(PanelEdicion);
		GeneralPanel.add(PanelEdicion,0,0);
		PanelEdicion.setSize(PanelActivity.getOffsetWidth()+"px",PanelActivity.getOffsetHeight()+"px");
		PanelEdicion.clear();
		PanelEdicion.setStyleName("BlancoTransparente");
		Button Boton=new Button();
		
		Boton.setHTML(InformationConstants.END_EDIT_BOTTON);
		Boton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				closeEditPanel();
				
				if (!InsertNameLabelTextBox.getText().isEmpty())
					INSERT_NAME_LABEL=InsertNameLabelTextBox.getText();
				else INSERT_NAME_LABEL=INSERT_NAME_LABEL_RESET;

				if (!CreateButtonTextBox.getText().isEmpty())
					CREATE_BUTTON=CreateButtonTextBox.getText();
			else CREATE_BUTTON=CREATE_BUTTON_RESET;
			
				if (!CancelButtonTextBox.getText().isEmpty())
					CANCEL_BUTTON=CancelButtonTextBox.getText();
				else CANCEL_BUTTON=CANCEL_BUTTON_RESET;
				
				ParsearFieldsAItems();
				SaveChages();
			}
		});
		InsertNameLabelTextBox=new TextBox();
		InsertNameLabelTextBox.setText(INSERT_NAME_LABEL);
		InsertNameLabelTextBox.setSize(InsertNameLabel.getOffsetWidth()+"px", InsertNameLabel.getOffsetHeight()+"px");
		PanelEdicion.add(InsertNameLabelTextBox, InsertNameLabel.getAbsoluteLeft()-PanelActivity.getAbsoluteLeft()-DecoradorWidth, InsertNameLabel.getAbsoluteTop()-PanelActivity.getAbsoluteTop()-DecoradorWidth);
		
		CreateButtonTextBox=new TextBox();
		CreateButtonTextBox.setText(CREATE_BUTTON);
		CreateButtonTextBox.setSize(CreateButton.getOffsetWidth()+"px", CreateButton.getOffsetHeight()+"px");
		PanelEdicion.add(CreateButtonTextBox, CreateButton.getAbsoluteLeft()-PanelActivity.getAbsoluteLeft()-DecoradorWidth, CreateButton.getAbsoluteTop()-PanelActivity.getAbsoluteTop()-DecoradorWidth);
		
		
		CancelButtonTextBox=new TextBox();
		CancelButtonTextBox.setText(CANCEL_BUTTON);
		CancelButtonTextBox.setSize(CancelButton.getOffsetWidth()+"px", CancelButton.getOffsetHeight()+"px");
		PanelEdicion.add(CancelButtonTextBox, CancelButton.getAbsoluteLeft()-PanelActivity.getAbsoluteLeft()-DecoradorWidth, CancelButton.getAbsoluteTop()-PanelActivity.getAbsoluteTop()-DecoradorWidth);
		
		PanelEdicion.add(Boton,PanelEdicion.getOffsetWidth()-Constants.TAMANOBOTOBEDITON, 0);
	}

	protected void SaveChages() {
		Language LanguageActual = ActualState.getActualLanguage();
		String NewActivityPopupPanelLanguageConfiguration=toFile();
		LanguageActual.setNewActivityPopupPanelLanguageConfiguration(NewActivityPopupPanelLanguageConfiguration);
		ActualState.saveLanguageActual(LanguageActual);
		
	}

	public String toFile() {
		StringBuffer SB=new StringBuffer();
		SB.append(INSERT_NAME_LABEL + '\n');
		SB.append( CREATE_BUTTON + '\n' );
		SB.append( CANCEL_BUTTON + '\n' );
		return SB.toString();
	}

	public static void FromFile(String Entrada) {
		String[] Lista = Entrada.split("\n");
		if (Lista.length >= NCampos) {
			if (!Lista[0].isEmpty())
				INSERT_NAME_LABEL = Lista[0];
			else INSERT_NAME_LABEL=INSERT_NAME_LABEL_RESET;
			if (!Lista[1].isEmpty())
				CREATE_BUTTON = Lista[1];
			else CREATE_BUTTON=CREATE_BUTTON_RESET;
			if (!Lista[2].isEmpty())
				CANCEL_BUTTON = Lista[2];
			else CANCEL_BUTTON=CANCEL_BUTTON_RESET;
		}
		else 
			Logger.GetLogger().severe(EditorActivityPopupPanel.class.toString(), ActualState.getUser().toString(), ErrorConstants.ERROR_LOADING_LANGUAGE_IN  + ACTIVITY_ADMINISTRATION);	
	}
	
	protected void ParsearFieldsAItems() {

		InsertNameLabel.setText(INSERT_NAME_LABEL);	
		CreateButton.setHTML(CREATE_BUTTON);
		CancelButton.setHTML(CANCEL_BUTTON);
		
	}

}
