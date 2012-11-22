package lector.client.admin.activity;

import lector.client.catalogo.FinderKeys;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.TypeClient;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class FinderDefaultType extends PopupPanel {


	public FinderDefaultType(Label defaultTypeLabel, TypeClient defaultType,
			CatalogoClient selectedCatalog) {
		super(true);
		setGlassEnabled(true);
		setModal(false);
		FinderKeys FK= new FinderKeys();
		FK.setCatalogo(selectedCatalog);
		ScrollPanel SP=new ScrollPanel();
		DockLayoutPanel dockPanel = new DockLayoutPanel(Unit.EM);
      dockPanel.setSize("100%", "100%");
      setWidget(dockPanel);
		dockPanel.add(SP);
		SP.setSize("100%", "100%");
		SP.add(FK);
        dockPanel.setSize(Window.getClientWidth()-100+"px", Window.getClientHeight()-100+"px");
	}

	
}
