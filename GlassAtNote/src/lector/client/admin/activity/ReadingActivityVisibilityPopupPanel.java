package lector.client.admin.activity;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.ConstantsError;
import lector.client.controler.ConstantsInformation;
import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;
import lector.share.model.Language;
import lector.share.model.client.ReadingActivityClient;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

public class ReadingActivityVisibilityPopupPanel extends PopupPanel {
	
	private static final String READING_ACTIVITY_VISIVILITY = "Reading activity visisivility popup";
	
	private static final int NCampos=4;
	
	private static final int DecoradorWidth = 2;
	
	private static String USE_CHECK_BOX_BELOW_TO_SET_PRIVICY_TO_YOUR_ACTIVITY= "Use check box below to set the privacy of your activity";
	private static String PRIVATE_CHECK_BOX = "Private";
	private static String ACCEPT = "ACCEPT";
	private static String CANCEL = "CANCEL";
	
	private static final String USE_CHECK_BOX_BELOW_TO_SET_PRIVICY_TO_YOUR_ACTIVITY_RESET= "Use check box below to set the privacy of your activity";
	private static final String PRIVATE_CHECK_BOX_RESET = "Private";
	private static final String ACCEPT_RESET = "ACCEPT";
	private static final String CANCEL_RESET = "CANCEL";
	
	private Label LabelInformation;
	private CheckBox PrivateAcceptCheckBox;
	private Button AcceptButton;
	private Button CancelButton;
	
	private TextBox LabelInformationTextBox;
	private TextBox PrivateAcceptCheckBoxTextBox;
	private TextBox AcceptButtonTextBox;
	private TextBox CancelButtonTextBox;
	
	private static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private ReadingActivityClient readingActivityClient;
	private AdminActivitiesEntryPoint father;

	private AbsolutePanel GeneralPanel;

	private AbsolutePanel PanelEdicion;

	private SimplePanel EditorZone;

	private VerticalPanel PanelActivity;
	
	
	
	
	

	public ReadingActivityVisibilityPopupPanel(ReadingActivityClient readingActivityClientin, AdminActivitiesEntryPoint fatherin) {
		setGlassEnabled(true);
		setAnimationEnabled(true);
		readingActivityClient=readingActivityClientin;
		father=fatherin;
		
		GeneralPanel = new AbsolutePanel();
		PanelEdicion=new AbsolutePanel();
		
		PanelActivity = new VerticalPanel();
		PanelActivity.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		PanelActivity.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		PanelActivity.setSpacing(9);
		EditorZone=new SimplePanel();
		EditorZone.setHeight(Constants.TAMANO_PANEL_EDICION);
		PanelActivity.add(EditorZone);
		//setWidget(verticalPanel);
		GeneralPanel.add(PanelActivity,0,0);
		GeneralPanel.setSize(Window.getClientWidth()+Constants.PX, Window.getClientHeight()+Constants.PX);
		setWidget(GeneralPanel);
		//PanelActivity.setSize("100%", "100%");
		
		LabelInformation = new Label(ReadingActivityVisibilityPopupPanel.USE_CHECK_BOX_BELOW_TO_SET_PRIVICY_TO_YOUR_ACTIVITY);
		PanelActivity.add(LabelInformation);
		
		PrivateAcceptCheckBox = new CheckBox(ReadingActivityVisibilityPopupPanel.PRIVATE_CHECK_BOX );
		PanelActivity.add(PrivateAcceptCheckBox);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setSpacing(5);
		PanelActivity.add(horizontalPanel);
		
		AcceptButton = new Button(ReadingActivityVisibilityPopupPanel.ACCEPT);
		AcceptButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				readingActivityClient.setPrivacy(PrivateAcceptCheckBox.getValue());
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto(ConstantsInformation.SAVING);
				bookReaderServiceHolder.saveReadingActivity(readingActivityClient, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						LoadingPanel.getInstance().hide();
						hide();
						father.refresh();
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert(ConstantsError.ERROR_SAVING_ACTIVITY);
					}
				});
			}
		});
		horizontalPanel.add(AcceptButton);
		
		
		AcceptButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});

		AcceptButton.addMouseOutHandler(new MouseOutHandler() {
	public void onMouseOut(MouseOutEvent event) {
		((Button) event.getSource())
				.setStyleName("gwt-ButtonCenter");
	}
});

		AcceptButton
		.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");

			}
		});

		AcceptButton.setStyleName("gwt-ButtonCenter");

		CancelButton = new Button(ReadingActivityVisibilityPopupPanel.CANCEL);
		CancelButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		
		CancelButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});

CancelButton.addMouseOutHandler(new MouseOutHandler() {
	public void onMouseOut(MouseOutEvent event) {
		((Button) event.getSource())
				.setStyleName("gwt-ButtonCenter");
	}
});

CancelButton
		.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");

			}
		});

CancelButton.setStyleName("gwt-ButtonCenter");
		horizontalPanel.add(CancelButton);
	}

	@Override
	public void show() {
		super.show();
		GeneralPanel.setSize(PanelActivity.getOffsetWidth()+Constants.PX, PanelActivity.getOffsetHeight()+Constants.PX);
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
		PanelEdicion.setSize(Constants.TAMANOBOTOBEDITOFF+Constants.PX,"50px");
		PanelEdicion.clear();
		PanelEdicion.setStyleName("");
		Button Boton=new Button();
		PanelEdicion.add(Boton,0,0);
		Boton.setHTML(ConstantsInformation.EDIT_BOTTON);
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
		
		Boton.setHTML(ConstantsInformation.END_EDIT_BOTTON);
		Boton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				closeEditPanel();
				
				if (!LabelInformationTextBox.getText().isEmpty())
					USE_CHECK_BOX_BELOW_TO_SET_PRIVICY_TO_YOUR_ACTIVITY=LabelInformationTextBox.getText();
				else USE_CHECK_BOX_BELOW_TO_SET_PRIVICY_TO_YOUR_ACTIVITY=USE_CHECK_BOX_BELOW_TO_SET_PRIVICY_TO_YOUR_ACTIVITY_RESET;

				if (!PrivateAcceptCheckBoxTextBox.getText().isEmpty())
					PRIVATE_CHECK_BOX=PrivateAcceptCheckBoxTextBox.getText();
			else PRIVATE_CHECK_BOX=PRIVATE_CHECK_BOX_RESET;
			

				if (!AcceptButtonTextBox.getText().isEmpty())
					ACCEPT=AcceptButtonTextBox.getText();
			else ACCEPT=ACCEPT_RESET;
				
				
				if (!CancelButtonTextBox.getText().isEmpty())
					CANCEL=CancelButtonTextBox.getText();
				else CANCEL=CANCEL_RESET;
				
				ParsearFieldsAItems();
				SaveChages();
				repaint();
			}
		});
		LabelInformationTextBox=new TextBox();
		LabelInformationTextBox.setText(USE_CHECK_BOX_BELOW_TO_SET_PRIVICY_TO_YOUR_ACTIVITY);
		LabelInformationTextBox.setSize(LabelInformation.getOffsetWidth()+"px", LabelInformation.getOffsetHeight()+"px");
		PanelEdicion.add(LabelInformationTextBox, LabelInformation.getAbsoluteLeft()-PanelActivity.getAbsoluteLeft()-DecoradorWidth, LabelInformation.getAbsoluteTop()-PanelActivity.getAbsoluteTop()-DecoradorWidth);
		
		PrivateAcceptCheckBoxTextBox=new TextBox();
		PrivateAcceptCheckBoxTextBox.setText(PRIVATE_CHECK_BOX);
		PrivateAcceptCheckBoxTextBox.setSize(PrivateAcceptCheckBox.getOffsetWidth()+"px", PrivateAcceptCheckBox.getOffsetHeight()+"px");
		PanelEdicion.add(PrivateAcceptCheckBoxTextBox, PrivateAcceptCheckBox.getAbsoluteLeft()-PanelActivity.getAbsoluteLeft()-DecoradorWidth, PrivateAcceptCheckBox.getAbsoluteTop()-PanelActivity.getAbsoluteTop()-DecoradorWidth);
		
		AcceptButtonTextBox=new TextBox();
		AcceptButtonTextBox.setText(ACCEPT);
		AcceptButtonTextBox.setSize(AcceptButton.getOffsetWidth()+"px", AcceptButton.getOffsetHeight()+"px");
		PanelEdicion.add(AcceptButtonTextBox, AcceptButton.getAbsoluteLeft()-PanelActivity.getAbsoluteLeft()-DecoradorWidth, AcceptButton.getAbsoluteTop()-PanelActivity.getAbsoluteTop()-DecoradorWidth);
		
		CancelButtonTextBox=new TextBox();
		CancelButtonTextBox.setText(CANCEL);
		CancelButtonTextBox.setSize(CancelButton.getOffsetWidth()+"px", CancelButton.getOffsetHeight()+"px");
		PanelEdicion.add(CancelButtonTextBox, CancelButton.getAbsoluteLeft()-PanelActivity.getAbsoluteLeft()-DecoradorWidth, CancelButton.getAbsoluteTop()-PanelActivity.getAbsoluteTop()-DecoradorWidth);
		
		PanelEdicion.add(Boton,PanelEdicion.getOffsetWidth()-Constants.TAMANOBOTOBEDITON, 0);
	}

	protected void SaveChages() {
		Language LanguageActual = ActualState.getActualLanguage();
		String ReadingActivityVisibilityPopupPanelLanguageConfiguration=toFile();
		LanguageActual.setReadingActivityVisibilityPopupPanelLanguageConfiguration(ReadingActivityVisibilityPopupPanelLanguageConfiguration);
		ActualState.saveLanguageActual(LanguageActual);
		
	}

	public String toFile() {
		StringBuffer SB=new StringBuffer();
		SB.append(USE_CHECK_BOX_BELOW_TO_SET_PRIVICY_TO_YOUR_ACTIVITY + "\r\n");
		SB.append( PRIVATE_CHECK_BOX + "\r\n" );
		SB.append( ACCEPT + "\r\n" );
		SB.append( CANCEL + "\r\n" );
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
				USE_CHECK_BOX_BELOW_TO_SET_PRIVICY_TO_YOUR_ACTIVITY = Lista[0];
			else USE_CHECK_BOX_BELOW_TO_SET_PRIVICY_TO_YOUR_ACTIVITY=USE_CHECK_BOX_BELOW_TO_SET_PRIVICY_TO_YOUR_ACTIVITY_RESET;
			if (!Lista[1].isEmpty())
				PRIVATE_CHECK_BOX = Lista[1];
			else PRIVATE_CHECK_BOX=PRIVATE_CHECK_BOX_RESET;
			if (!Lista[2].isEmpty())
				ACCEPT = Lista[2];
			else ACCEPT=ACCEPT_RESET;
			if (!Lista[3].isEmpty())
				CANCEL = Lista[3];
			else CANCEL=CANCEL_RESET;
		}
		else 
			Logger.GetLogger().severe(ReadingActivityVisibilityPopupPanel.class.toString(), ActualState.getUser().toString(), ConstantsError.ERROR_LOADING_LANGUAGE_IN  + READING_ACTIVITY_VISIVILITY);	
		ParsearFieldsAItemsRESET();
		}
	}
	
	private static void ParsearFieldsAItemsRESET() {
		USE_CHECK_BOX_BELOW_TO_SET_PRIVICY_TO_YOUR_ACTIVITY=USE_CHECK_BOX_BELOW_TO_SET_PRIVICY_TO_YOUR_ACTIVITY_RESET;
		PRIVATE_CHECK_BOX=PRIVATE_CHECK_BOX_RESET;
		ACCEPT=ACCEPT_RESET;
		CANCEL=CANCEL_RESET;
		
	}
	
	protected void ParsearFieldsAItems() {

		LabelInformation.setText(USE_CHECK_BOX_BELOW_TO_SET_PRIVICY_TO_YOUR_ACTIVITY);	
		PrivateAcceptCheckBox.setHTML(PRIVATE_CHECK_BOX);
		AcceptButton.setHTML(ACCEPT);
		CancelButton.setHTML(CANCEL);
		
		
	}
	
	private void repaint() {
		hide();
		center();

	}
	
}
