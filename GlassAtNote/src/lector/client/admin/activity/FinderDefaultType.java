package lector.client.admin.activity;

import lector.client.catalogo.FinderKeys;
import lector.client.login.ActualUser;
import lector.client.reader.BotonesStackPanelReaderSelectMio;
import lector.client.reader.ButtonTipo;
import lector.client.reader.ClickHandlerMioSelector;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.TypeClient;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FinderDefaultType extends PopupPanel {


	private static String CLOSE = "Close";
	
	private FinderDefaultType Yo;

	public FinderDefaultType(EditorActivity EA,
			CatalogoClient selectedCatalog) {
		super(true);
		setGlassEnabled(true);
		setModal(false);
		Yo=this;
		
		DockLayoutPanel dockPanel = new DockLayoutPanel(Unit.EM);
		
		MenuBar menuBar = new MenuBar(false);
        dockPanel.addNorth(menuBar, 1.9);
        menuBar.setSize("100%", "20px");
        MenuItem mntmNewItem_1 = new MenuItem("New item", false, new Command() {
        	public void execute() {
        		hide();
        	}

		 });
        mntmNewItem_1.setHTML(FinderDefaultType.CLOSE );
        menuBar.addItem(mntmNewItem_1);
        
      dockPanel.setSize("100%", "100%");
      setWidget(dockPanel);
        dockPanel.setSize(Window.getClientWidth()-100+"px", Window.getClientHeight()-100+"px");
        FinderKeys.setButtonTipo(new BotonesStackPanelReaderSelectMio("prototipo", new VerticalPanel(), new HorizontalPanel()));
		 FinderKeys.setBotonClick(new ClickHandlerMioSelectorDefault(EA,Yo));
        FinderKeys FK= new FinderKeys();
        dockPanel.add(FK);
        FK.setSize("100%", "100%");
        FK.setCatalogo(selectedCatalog);
        FK.RefrescaLosDatos();
	}

	
}
