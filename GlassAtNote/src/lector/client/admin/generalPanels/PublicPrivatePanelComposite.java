package lector.client.admin.generalPanels;

import lector.client.controler.ActualState;
import lector.client.controler.ErrorConstants;
import lector.client.logger.Logger;
import lector.share.model.Language;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;

public class PublicPrivatePanelComposite extends Composite {
	
	private static final String PUBLIC_PRIVATE_PANEL_COMPOSITE = "PublicPrivatePanel Composite";

	private static final int NCampos = 2;
	
	private static String PUBLIC_BUTTON="Public";
	private static String PRIVATE_BUTTON="Own";
	
	private static String PUBLIC_BUTTON_RESET="Public";
	private static String PRIVATE_BUTTON_RESET="Own";
	
	private MenuItem PublicButton;
	private MenuItem PrivateButton;
	
	private TextBox PublicButtonTextBox;
	private TextBox PrivateButtonTextBox;
	
	
	private VerticalPanel Private;
	private VerticalPanel Public;
	private VerticalPanel verticalPanelPrivateContainer;
	private VerticalPanel verticalPanelPublicContainer;
	
	

	public PublicPrivatePanelComposite() {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		verticalPanelPrivateContainer = new VerticalPanel();
		verticalPanel.add(verticalPanelPrivateContainer);
		verticalPanelPrivateContainer.setWidth("100%");
		
		MenuBar menuBar_1 = new MenuBar(false);
		verticalPanelPrivateContainer.add(menuBar_1);
		
		PrivateButton = new MenuItem(PublicPrivatePanelComposite.PRIVATE_BUTTON, false, (Command) null);
		menuBar_1.addItem(PrivateButton);
		
		Private = new VerticalPanel();
		verticalPanelPrivateContainer.add(Private);
		Private.setWidth("100%");
		
		verticalPanelPublicContainer = new VerticalPanel();
		verticalPanel.add(verticalPanelPublicContainer);
		verticalPanelPublicContainer.setWidth("100%");
		
		MenuBar menuBar = new MenuBar(false);
		verticalPanelPublicContainer.add(menuBar);
		
		PublicButton = new MenuItem(PublicPrivatePanelComposite.PUBLIC_BUTTON, false, (Command) null);
		menuBar.addItem(PublicButton);
		
		Public = new VerticalPanel();
		verticalPanelPublicContainer.add(Public);
		Public.setWidth("100%");
	}

	public VerticalPanel getPrivate() {
		return Private;
	}
	
	public VerticalPanel getPublic() {
		return Public;
	}

	public void ClearEmpty() {
		if (Public.getWidgetCount()==0) verticalPanelPublicContainer.setVisible(false);
		else verticalPanelPublicContainer.setVisible(true);
		if (Private.getWidgetCount()==0) verticalPanelPrivateContainer.setVisible(false);
		else verticalPanelPrivateContainer.setVisible(true);
		
	}

	public void OpenEditPanelonClick() {
		
		 if (!PublicButtonTextBox.getText().isEmpty())
			 PUBLIC_BUTTON=PublicButtonTextBox.getText();
		 else PUBLIC_BUTTON=PUBLIC_BUTTON_RESET;
		
		 if (!PrivateButtonTextBox.getText().isEmpty())
			 PRIVATE_BUTTON=PrivateButtonTextBox.getText();
		 else PRIVATE_BUTTON=PRIVATE_BUTTON_RESET;
		
	}

	public void OpenEditPanel(AbsolutePanel PanelEdicion) {
		
		PublicButtonTextBox=new TextBox();
		PublicButtonTextBox.setText(PUBLIC_BUTTON);
		PublicButtonTextBox.setSize(PublicButton.getOffsetWidth()+"px",
				 PublicButton.getOffsetHeight()+"px");
		 PanelEdicion.add(PublicButtonTextBox, PublicButton.getAbsoluteLeft(),
				 PublicButton.getAbsoluteTop());
		 
		 PrivateButtonTextBox=new TextBox();
		 PrivateButtonTextBox.setText(PRIVATE_BUTTON);
		 PrivateButtonTextBox.setSize(PrivateButton.getOffsetWidth()+"px",
				 PrivateButton.getOffsetHeight()+"px");
		 PanelEdicion.add(PrivateButtonTextBox, PrivateButton.getAbsoluteLeft(),
				 PrivateButton.getAbsoluteTop());
		
	}

	public void SaveChages() {
		
		Language LanguageActual = ActualState.getActualLanguage();
		 String PublicPrivatePanelCompositeLanguageConfiguration=toFile();
		 LanguageActual.setPublicPrivatePanelCompositeLanguageConfiguration(PublicPrivatePanelCompositeLanguageConfiguration);
		 ActualState.saveLanguageActual(LanguageActual);
		
	}

	public void ParsearFieldsAItems() {
		PublicButton.setHTML(PUBLIC_BUTTON);
		PrivateButton.setHTML(PRIVATE_BUTTON);
		
	}
	
	public String toFile() {
		StringBuffer SB = new StringBuffer();
		 SB.append(PUBLIC_BUTTON+"\r\n");
		 SB.append(PRIVATE_BUTTON+"\r\n");
		return SB.toString();
	}
	
	public static void FromFile(String Entrada) {
		 String[] Lista = Entrada.split("\r\n");
		 if (Lista.length >= NCampos) {
		 if (!Lista[0].isEmpty())
			 PUBLIC_BUTTON = Lista[0];
		 else PUBLIC_BUTTON=PUBLIC_BUTTON_RESET;
		 if (!Lista[1].isEmpty())
			 PRIVATE_BUTTON = Lista[1];
		 else PRIVATE_BUTTON=PRIVATE_BUTTON_RESET;
		 }
		 else
		 Logger.GetLogger().severe(PublicPrivatePanelComposite.class.toString(),
		 ActualState.getUser().toString(),
		 ErrorConstants.ERROR_LOADING_LANGUAGE_IN +
		 PUBLIC_PRIVATE_PANEL_COMPOSITE);
		
		 ParsearFieldsAItemsRESET();
	}
	
	private static void ParsearFieldsAItemsRESET() {
		PUBLIC_BUTTON=PUBLIC_BUTTON_RESET;
		PRIVATE_BUTTON=PRIVATE_BUTTON_RESET;
		
	}

	public void ClearALL() {
		Public.clear();
		Private.clear();
		
	}
	
}
