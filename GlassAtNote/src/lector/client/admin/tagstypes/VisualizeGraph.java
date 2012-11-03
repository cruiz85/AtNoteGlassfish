package lector.client.admin.tagstypes;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.grafo.PanelGrafo;
import lector.client.controler.ErrorConstants;
import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;
import lector.share.model.client.CatalogoClient;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;

public class VisualizeGraph extends DialogBox {
	
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private ScrollPanel simplePanel;
	
	public VisualizeGraph(CatalogoClient long1) {
		super(true);
		setSize("100%", "100%");
		setHTML("Graph");
		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
		setWidget(dockLayoutPanel);
		dockLayoutPanel.setSize(Window.getClientWidth()-100+"px", Window.getClientHeight()-100+"px");
		
		MenuBar menuBar = new MenuBar(false);
		dockLayoutPanel.addNorth(menuBar, 1.9);
		
		MenuItem mntmNewItem = new MenuItem("Close", false, new Command() {
			public void execute() {
				hide();
			}
		});
		mntmNewItem.setHTML("Close");
		menuBar.addItem(mntmNewItem);
		simplePanel = new ScrollPanel();
		dockLayoutPanel.add(simplePanel);
		simplePanel.setSize(Window.getClientWidth()-100+"px", Window.getClientHeight()-124+"px");
		PanelGrafo PG=new PanelGrafo(long1);
		simplePanel.add(PG);
		
//		bookReaderServiceHolder.loadCatalogById(long1.getId(), new AsyncCallback<CatalogoClient>() {
//			
//			@Override
//			public void onSuccess(CatalogoClient result) {
//				PanelGrafo PG=new PanelGrafo(result);
//				simplePanel.add(PG);
//				
//			}
//			
//			@Override
//			public void onFailure(Throwable caught) {
//				Window.alert(ErrorConstants.ERROR_LOADING_CATALOG);
//				Logger.GetLogger().severe(Yo.getClass().toString(), ErrorConstants.ERROR_LOADING_CATALOG);
//				LoadingPanel.getInstance().hide();
//				
//			}
//		});
		
//		PanelFinderKey PFK=new PanelFinderKey(long1);
//		simplePanel.add(PFK);
		
		
	}

}
