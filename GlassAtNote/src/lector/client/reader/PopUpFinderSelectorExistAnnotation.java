package lector.client.reader;


import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.EntitdadObject;
import lector.client.controler.catalogo.Finder;
import lector.client.controler.catalogo.FinderKeys;
import lector.client.controler.catalogo.FinderOwnGrafo;
import lector.client.controler.catalogo.client.EntityCatalogElements;
import lector.client.controler.catalogo.graph.BotonGrafo;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.EntryClient;
import lector.share.model.client.TypeClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;

public class PopUpFinderSelectorExistAnnotation extends PopupPanel {

	private Finder finder;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private EntityCatalogElements father;
	private Finder finderrefresh;

	public PopUpFinderSelectorExistAnnotation(CatalogoClient CatalogoIn, EntityCatalogElements entity, Finder finderin) {
		super(false);
		setModal(true);
		setGlassEnabled(true);
		father=entity;
		FinderOwnGrafo.setButtonTipoGrafo(new BotonGrafo(
				"prototipo", new VerticalPanel(), new HorizontalPanel(),finder));
		FinderOwnGrafo.setBotonClickGrafo(new ClickHandler() {

			public void onClick(ClickEvent event) {
				EntityCatalogElements E=(EntityCatalogElements) ((BotonGrafo)event.getSource()).getEntidad();
				AsyncCallback<Void> LLamada=new AsyncCallback<Void>() {
					
					public void onSuccess(Void result) {
//						finderrefresh.RefrescaLosDatos();
//						//	scrollPanel.setWidget(finder);
//						finderrefresh.setSize("100%","100%");
						finderrefresh.RefrescaLosDatos();
						SelectorTypePopUpAnnotacion.RestoreFinderButtonActio();
						//	scrollPanel.setWidget(finder);
						//finderrefresh.setSize("100%","100%");
						hide();
						
					}
					
					public void onFailure(Throwable caught) {
						Window.alert(ActualState.getLanguage().getE_Saving());
						
					}
				};
				if (father==null)
				bookReaderServiceHolder.addChildEntry(E.getEntry().getId(), Constants.CATALOGID, LLamada);
				else 
					bookReaderServiceHolder.addChildEntry(E.getEntry().getId(), father.getEntry().getId(), LLamada);
				
			}
		});
		
		finderrefresh=finderin;
		
		if (ActualState.getReadingactivity().getVisualization()==null||ActualState.getReadingactivity().getVisualization().equals(Constants.VISUAL_ARBOL))
        {
		finder = new FinderOwnGrafo(CatalogoIn);
		FinderOwnGrafo.setButtonTipoGrafo(new BotonGrafo(
				"prototipo", new VerticalPanel(), new HorizontalPanel(),finder));
		FinderOwnGrafo.setBotonClickGrafo(new ClickHandler() {

			public void onClick(ClickEvent event) {
				EntityCatalogElements E=(EntityCatalogElements) ((BotonGrafo)event.getSource()).getEntidad();
				AsyncCallback<Void> LLamada=new AsyncCallback<Void>() {
					
					public void onSuccess(Void result) {
//						finderrefresh.RefrescaLosDatos();
//						//	scrollPanel.setWidget(finder);
//						finderrefresh.setSize("100%","100%");
						finderrefresh.RefrescaLosDatos();
						SelectorTypePopUpAnnotacion.RestoreFinderButtonActio();
						//	scrollPanel.setWidget(finder);
						//finderrefresh.setSize("100%","100%");
						hide();
						
					}
					
					public void onFailure(Throwable caught) {
						Window.alert(ActualState.getLanguage().getE_Saving());
						
					}
				};
				if (father==null)
					if (father==null)
						bookReaderServiceHolder.addChildEntry(E.getEntry().getId(), Constants.CATALOGID, LLamada);
						else 
							bookReaderServiceHolder.addChildEntry(E.getEntry().getId(), father.getEntry().getId(), LLamada);
				
			}
		});
        }
        else 
        {
        	 finder= new FinderKeys();
        	 finder.setCatalogo(CatalogoIn);
             finder.RefrescaLosDatos();
             FinderKeys.setButtonTipo(new BotonGrafo(
     				"prototipo", new VerticalPanel(), new HorizontalPanel(),finder));
             FinderKeys.setBotonClick(new ClickHandlerMioSelectorExist(this,finderrefresh,father));
        }
		
		
		
		SimplePanel S= new SimplePanel();
		S.setSize("100%", "100%");
		S.add(finder);

		
		finder.setSize("100%", "100%");
		
		DockLayoutPanel DLP=new DockLayoutPanel(Unit.EM);
		setWidget(DLP);
		DLP.setSize(Window.getClientWidth()-100+"px", Window.getClientHeight()-100+"px");
		
		MenuBar menuBar = new MenuBar(false);
		DLP.addNorth(menuBar, 1.9);
		
		MenuItem mntmClose = new MenuItem(ActualState.getLanguage().getClose(), false, new Command() {
			public void execute() {
				hide();
			}
		});
		menuBar.addItem(mntmClose);
		DLP.add(S);
		finder.setCatalogo(CatalogoIn);
	}

}
