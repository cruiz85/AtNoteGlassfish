package lector.client.admin.catalog;

import java.util.ArrayList;
import java.util.List;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.ConstantsError;
import lector.client.controler.ConstantsInformation;
import lector.client.controler.Controlador;

import lector.client.reader.LoadingPanel;
import lector.share.model.client.CatalogoClient;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;

public class CatalogAdmintrationEntryPoint implements EntryPoint {

	private static final String ADMINISTRATIONCATALOGENTRYPOINT = "Catalog Administration entry point";

	private static final int NCampos = 7;
	
	private VerticalPanel Actual;
	private CatalogAdmintrationEntryPoint yo;
	private GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);

	private static String CATALOGEMENUITEM="Catalogue Administration";
	private static String NEWCATALOGMENUITEM = "New";
	private static String BACKMENUITEM="Back";

	/**
	 * Metodo de entrada que pinta la ventana para el entry point
	 */
	public void onModuleLoad() {
		RootPanel RootTXOriginal = RootPanel.get();
		RootPanel RootMenu = RootPanel.get("Menu");
		RootTXOriginal.setSize("100%", "100%");
		RootMenu.setStyleName("Root");
		RootTXOriginal.setStyleName("Root");
		
		yo=this;
		
		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);
		menuBar.setWidth("100%");
		
		MenuItem menuItem = new MenuItem(CATALOGEMENUITEM, false, (Command) null);
		menuItem.setHTML(CATALOGEMENUITEM);
		menuItem.setEnabled(false);
		menuBar.addItem(menuItem);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		
		MenuItem mntmNewItem = new MenuItem(NEWCATALOGMENUITEM, false, new Command() {
			public void execute() {
				NewCatalogPopupPanel NL=new NewCatalogPopupPanel(yo);
				NL.center();
				
			}
		});
		mntmNewItem.setHTML(NEWCATALOGMENUITEM);
		menuBar.addItem(mntmNewItem);
		
		MenuItemSeparator separator_2 = new MenuItemSeparator();
		menuBar.addSeparator(separator_2);
		
		MenuItem mntmBack = new MenuItem(BACKMENUITEM, false, new Command() {
			public void execute() {
				Controlador.change2Administrator();
			}
		});
		mntmBack.setHTML(BACKMENUITEM);
		menuBar.addItem(mntmBack);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setStyleName("fondoLogo");
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		RootTXOriginal.add(verticalPanel,0,25);
		verticalPanel.setSize("100%", "96%");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(15);
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setWidth("100%");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setSpacing(10);
		horizontalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_1.setStyleName("AzulTransparente");
		horizontalPanel.add(horizontalPanel_1);
		horizontalPanel_1.setWidth("420px");
		
		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2.setSpacing(6);
		horizontalPanel_2.setStyleName("BlancoTransparente");
		horizontalPanel_1.add(horizontalPanel_2);
		
		Actual = new VerticalPanel();
		horizontalPanel_2.add(Actual);
		Actual.setStyleName("BlancoTransparente");
		Actual.setWidth("400px");
		Actual.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
//		HorizontalPanel horizontalPanel = new HorizontalPanel();
//		Actual.add(horizontalPanel);
//		horizontalPanel.setHeight("20px");
		

		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto(ConstantsInformation.LOADING);
bookReaderServiceHolder.getVisbibleCatalogsByProfessorId(ActualState.getUser().getId(), new AsyncCallback<List<CatalogoClient>>() {

	public void onFailure(Throwable caught) {
		LoadingPanel.getInstance().hide();
		
	}

	public void onSuccess(List<CatalogoClient> result) {
		LoadingPanel.getInstance().hide();
		List<CatalogoClient> CatalogMostrar=result;
		for (int i = 0; i < CatalogMostrar.size()-1; i++) {
			CatalogoClient C=CatalogMostrar.get(i);
			CatalogButton nue=new CatalogButton(Actual,new VerticalPanel(),C);
			nue.setSize("100%", "100%");

			nue.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					CatalogSeleccionMenuPopupPanel panel=new CatalogSeleccionMenuPopupPanel((CatalogButton)event.getSource(),yo);
					panel.showRelativeTo((CatalogButton)event.getSource());
				}
			});
			nue.setStyleName("gwt-ButtonTOP");
			nue.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonPush");
				}
			});
			nue.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
				}
			});
			nue.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
				}
			});
		}
		if (!CatalogMostrar.isEmpty())
		{
			CatalogoClient C=CatalogMostrar.get(CatalogMostrar.size()-1);
			CatalogButton nue=new CatalogButton(Actual,new VerticalPanel(),C);
			nue.setSize("100%", "100%");

			nue.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					CatalogSeleccionMenuPopupPanel panel=new CatalogSeleccionMenuPopupPanel((CatalogButton)event.getSource(),yo);
					panel.showRelativeTo((CatalogButton)event.getSource());
				}
			});
			nue.setStyleName("gwt-ButtonBotton");
			nue.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
				}
			});
			nue.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
				}
			});
			nue.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
				}
			});
		}
		
		
	}});

		
		
	}
	
	/**
	 * Refresca los botones de catalogo asociados
	 */
	public void refresh()
	{
		Actual.clear();
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto(ConstantsInformation.LOADING);
bookReaderServiceHolder.getVisbibleCatalogsByProfessorId(ActualState.getUser().getId(), new AsyncCallback<List<CatalogoClient>>() {

	public void onFailure(Throwable caught) {
		Window.alert(ConstantsError.ERROR_LOADING_CATALOG_VISIBLE_PROFESOR);
		LoadingPanel.getInstance().hide();
		
	}

	public void onSuccess(List<CatalogoClient> result) {
		LoadingPanel.getInstance().hide();
		List<CatalogoClient> CatalogMostrar=result;
		for (int i = 0; i < CatalogMostrar.size()-1; i++) {
			CatalogoClient C=CatalogMostrar.get(i);
			CatalogButton nue=new CatalogButton(Actual,new VerticalPanel(),C);
			nue.setSize("100%", "100%");

			nue.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					CatalogSeleccionMenuPopupPanel panel=new CatalogSeleccionMenuPopupPanel((CatalogButton)event.getSource(),yo);
					panel.showRelativeTo((CatalogButton)event.getSource());
				}
			});
			nue.setStyleName("gwt-ButtonTOP");
			nue.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonPush");
				}
			});
			nue.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
				}
			});
			nue.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
				}
			});
		}
		if (!CatalogMostrar.isEmpty())
		{
			CatalogoClient C=CatalogMostrar.get(CatalogMostrar.size()-1);
			CatalogButton nue=new CatalogButton(Actual,new VerticalPanel(),C);
			nue.setSize("100%", "100%");;

			nue.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					CatalogSeleccionMenuPopupPanel panel=new CatalogSeleccionMenuPopupPanel((CatalogButton)event.getSource(),yo);
					panel.showRelativeTo((CatalogButton)event.getSource());
				}
			});
			nue.setStyleName("gwt-ButtonBotton");
			nue.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
				}
			});
			nue.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
				}
			});
			nue.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
				}
			});
		}
		
	}});

			
		
	}
}
