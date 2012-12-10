package lector.client.admin.generalPanels;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;

public class PublicPrivatePanel extends Composite {
	private static String PUBLIC_BUTTON="Public";
	private static String PRIVATE_BUTTON="Own";
	private VerticalPanel Private;
	private VerticalPanel Public;
	private VerticalPanel verticalPanelPrivateContainer;
	private VerticalPanel verticalPanelPublicContainer;

	public PublicPrivatePanel() {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		verticalPanelPrivateContainer = new VerticalPanel();
		verticalPanel.add(verticalPanelPrivateContainer);
		verticalPanelPrivateContainer.setWidth("100%");
		
		MenuBar menuBar_1 = new MenuBar(false);
		verticalPanelPrivateContainer.add(menuBar_1);
		
		MenuItem PrivateButton = new MenuItem(PublicPrivatePanel.PRIVATE_BUTTON, false, (Command) null);
		menuBar_1.addItem(PrivateButton);
		
		Private = new VerticalPanel();
		verticalPanelPrivateContainer.add(Private);
		Private.setWidth("100%");
		
		verticalPanelPublicContainer = new VerticalPanel();
		verticalPanel.add(verticalPanelPublicContainer);
		verticalPanelPublicContainer.setWidth("100%");
		
		MenuBar menuBar = new MenuBar(false);
		verticalPanelPublicContainer.add(menuBar);
		
		MenuItem PublicButton = new MenuItem(PublicPrivatePanel.PUBLIC_BUTTON, false, (Command) null);
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
	
}
