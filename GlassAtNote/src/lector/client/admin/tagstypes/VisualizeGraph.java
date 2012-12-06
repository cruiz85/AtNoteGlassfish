package lector.client.admin.tagstypes;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.OwnGraph.PanelGrafo;
import lector.share.model.client.CatalogoClient;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class VisualizeGraph extends DialogBox {
	
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private ScrollPanel simplePanel;
	private PanelGrafo PG;
	private VerticalPanel VP;
	private CatalogoClient Catalog;
	
	public VisualizeGraph(CatalogoClient long1) {

		super(true);
		Catalog=long1;
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
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		simplePanel.setWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		PG=new PanelGrafo();
		horizontalPanel.add(PG);
		PanelGrafo.setAccionAsociada(null);

		
		
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

	public void Lanza() {
		
		PG.Go(Catalog);
		PG.Pinta();
		PG.refreshsize();
		
	}

}
