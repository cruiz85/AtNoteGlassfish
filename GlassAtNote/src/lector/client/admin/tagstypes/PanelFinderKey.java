package lector.client.admin.tagstypes;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ConstantsError;
import lector.client.controler.catalogo.FinderKeys;
import lector.share.model.client.CatalogoClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class PanelFinderKey extends Composite {
	
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private HorizontalPanel horizontalPanel;
	
	public PanelFinderKey(Long long1) {
		
		horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		initWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		bookReaderServiceHolder.loadCatalogById(long1, new AsyncCallback<CatalogoClient>() {
			
			public void onSuccess(CatalogoClient result) {
				FinderKeys FK=new FinderKeys();
				FK.setCatalogo(result);
				horizontalPanel.add(FK);
				
			}
			
			public void onFailure(Throwable caught) {
				Window.alert(ConstantsError.ERROR_RETRIVING_CATALOG);
				
			}
		});
	}

}
