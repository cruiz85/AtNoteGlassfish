package lector.client.admin.catalog;

import java.util.List;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.ConstantsError;
import lector.client.controler.ConstantsInformation;
import lector.client.controler.Controlador;

import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;
import lector.share.model.Language;
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
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;

public class CatalogAdmintrationEntryPoint implements EntryPoint {

	private static final String ADMINISTRATIONCATALOGENTRYPOINT = "Catalog Administration entry point";

	private static final int NCampos = 3;
	
	private static String CATALOGEMENUITEM="Catalogue Administration";
	private static String NEWCATALOGMENUITEM = "New";
	private static String BACKMENUITEM="Back";
	
	private static final String CATALOGEMENUITEM_RESET="Catalogue Administration";
	private static final String NEWCATALOGMENUITEM_RESET = "New";
	private static final String BACKMENUITEM_RESET="Back";
	
	private MenuItem CatalogMenuItem;
	private MenuItem NewCatalogItem;
	private MenuItem BackMenuItem;
	
	private TextBox CatalogMenuItemTextBox;
	private TextBox NewCatalogItemTextBox;
	private TextBox BackMenuItemTextBox;
	
	private VerticalPanel Actual;
	private CatalogAdmintrationEntryPoint yo;
	private GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);

	private AbsolutePanel PanelEdicion;
	private RootPanel rootPanel;
	private DockLayoutPanel PanelFondoGeneral;


	/**
	 * Metodo de entrada que pinta la ventana para el entry point
	 */
	public void onModuleLoad() {
		rootPanel = RootPanel.get();
		rootPanel.setSize("100%", "100%");
		
		yo=this;
		
		PanelFondoGeneral = new DockLayoutPanel(Unit.PX);
		rootPanel.add(PanelFondoGeneral,0,0);
		PanelFondoGeneral.setSize("100%", "100%");
		
		MenuBar menuBar = new MenuBar(false);
		PanelFondoGeneral.addNorth(menuBar, 25.0);
		menuBar.setWidth("100%");
		
		CatalogMenuItem = new MenuItem(CATALOGEMENUITEM, false, (Command) null);
		CatalogMenuItem.setHTML(CATALOGEMENUITEM);
		CatalogMenuItem.setEnabled(false);
		menuBar.addItem(CatalogMenuItem);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		
		NewCatalogItem = new MenuItem(NEWCATALOGMENUITEM, false, new Command() {
			public void execute() {
				NewCatalogPopupPanel NL=new NewCatalogPopupPanel(yo);
				NL.center();
				
			}
		});
		NewCatalogItem.setHTML(NEWCATALOGMENUITEM);
		menuBar.addItem(NewCatalogItem);
		
		MenuItemSeparator separator_2 = new MenuItemSeparator();
		menuBar.addSeparator(separator_2);
		
		BackMenuItem = new MenuItem(BACKMENUITEM, false, new Command() {
			public void execute() {
				Controlador.change2Administrator();
			}
		});
		BackMenuItem.setHTML(BACKMENUITEM);
		menuBar.addItem(BackMenuItem);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		PanelFondoGeneral.add(verticalPanel);
		verticalPanel.setStyleName("fondoLogo");
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setSize("100%", "100%");
		
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

		PanelEdicion = new AbsolutePanel();
		if (ActualState.isLanguageActive())
			closeEditPanel();
		
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
	
	/**
	 * Funcion de cerrado del panel de edicion
	 */
	public void closeEditPanel() {
		rootPanel.remove(PanelEdicion);
		rootPanel.add(PanelEdicion, PanelFondoGeneral.getOffsetWidth()
				- Constants.TAMANOBOTOBEDITOFF, 0);
		PanelEdicion.setSize(Constants.TAMANOBOTOBEDITOFF+Constants.PX, "50px");
		PanelEdicion.clear();
		PanelEdicion.setStyleName("");
		Button Boton = new Button();
		PanelEdicion.add(Boton, 0, 0);
		Boton.setHTML(ConstantsInformation.EDIT_BOTTON);
		Boton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				OpenEditPanel();

			}
		});

	}

	/**
	 * Funcion de apertura del panel de edicion
	 */
	public void OpenEditPanel() {
		rootPanel.remove(PanelEdicion);
		rootPanel.add(PanelEdicion, 0, 0);
		PanelEdicion.setSize(PanelFondoGeneral.getOffsetWidth() + "px",
				PanelFondoGeneral.getOffsetHeight() + "px");
		PanelEdicion.clear();
		PanelEdicion.setStyleName("BlancoTransparente");
		Button Boton = new Button();
		PanelEdicion.add(Boton, PanelEdicion.getOffsetWidth()
				- Constants.TAMANOBOTOBEDITON, 0);
		Boton.setHTML(ConstantsInformation.END_EDIT_BOTTON);
		Boton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				closeEditPanel();

				if (!CatalogMenuItemTextBox.getText().isEmpty())
					CATALOGEMENUITEM = CatalogMenuItemTextBox
							.getText();
				else
					CATALOGEMENUITEM = CATALOGEMENUITEM_RESET;

				if (!NewCatalogItemTextBox.getText().isEmpty())
					NEWCATALOGMENUITEM = NewCatalogItemTextBox.getText();
				else
					NEWCATALOGMENUITEM = NEWCATALOGMENUITEM_RESET;

				if (!BackMenuItemTextBox.getText().isEmpty())
					BACKMENUITEM = BackMenuItemTextBox.getText();
				else
					BACKMENUITEM = BACKMENUITEM_RESET;

				ParsearFieldsAItems();
				SaveChages();
			}
		});

		CatalogMenuItemTextBox = new TextBox();
		CatalogMenuItemTextBox.setText(CATALOGEMENUITEM);
		CatalogMenuItemTextBox.setSize(
				CatalogMenuItem.getOffsetWidth() + "px",
				CatalogMenuItem.getOffsetHeight() + "px");
		PanelEdicion.add(CatalogMenuItemTextBox,
				CatalogMenuItem.getAbsoluteLeft(),
				CatalogMenuItem.getAbsoluteTop());

		NewCatalogItemTextBox = new TextBox();
		NewCatalogItemTextBox.setText(NEWCATALOGMENUITEM);
		NewCatalogItemTextBox.setSize(NewCatalogItem.getOffsetWidth()
				+ "px", NewCatalogItem.getOffsetHeight() + "px");
		PanelEdicion.add(NewCatalogItemTextBox,
				NewCatalogItem.getAbsoluteLeft(),
				NewCatalogItem.getAbsoluteTop());

		BackMenuItemTextBox = new TextBox();
		BackMenuItemTextBox.setText(BACKMENUITEM);
		BackMenuItemTextBox.setSize(
				BackMenuItem.getOffsetWidth() + "px",
				BackMenuItem.getOffsetHeight() + "px");
		PanelEdicion.add(BackMenuItemTextBox,
				BackMenuItem.getAbsoluteLeft(),
				BackMenuItem.getAbsoluteTop());

	}

	/**
	 * Guarda los cambios en el lenguaje actual
	 */
	protected void SaveChages() {
		Language LanguageActual = ActualState.getActualLanguage();
		String CatalogAdmintrationEntryPointLanguageConfiguration = toFile();
		LanguageActual
				.setCatalogAdmintrationEntryPointLanguageConfiguration(CatalogAdmintrationEntryPointLanguageConfiguration);
		ActualState.saveLanguageActual(LanguageActual);
	}

	/**
	 * Parsea los elementos con el texto actual
	 */
	public void ParsearFieldsAItems() {
		CatalogMenuItem.setHTML(CATALOGEMENUITEM);
		NewCatalogItem.setHTML(NEWCATALOGMENUITEM);
		BackMenuItem.setText(BACKMENUITEM);
	}

	/**
	 * Obtiene el texto a salvar de las etiquetras en el proceso de salvado en el lenguaje
	 * @return Texto con las etiquetas a salvar
	 */
	public static String toFile() {
		StringBuffer SB = new StringBuffer();
		SB.append(CATALOGEMENUITEM + "\r\n");
		SB.append(NEWCATALOGMENUITEM + "\r\n");
		SB.append(BACKMENUITEM + "\r\n");
		return SB.toString();
	}

	/**
	 * Carga el texto entrada en las etiquetas del sistema
	 * @param Entrada Texto que entrara en las etiquetas
	 */
	public static void FromFile(String Entrada) {
		if (Entrada.length()==0) 
			ParsearFieldsAItemsRESET();
		else
		{
		String[] Lista = Entrada.split("\r\n");
		if (Lista.length >= NCampos) {
			if (!Lista[0].isEmpty())
				CATALOGEMENUITEM = Lista[0];
			else
				CATALOGEMENUITEM = CATALOGEMENUITEM_RESET;
			if (!Lista[1].isEmpty())
				NEWCATALOGMENUITEM = Lista[1];
			else
				NEWCATALOGMENUITEM = NEWCATALOGMENUITEM_RESET;
			if (!Lista[2].isEmpty())
				BACKMENUITEM = Lista[2];
			else
				BACKMENUITEM = BACKMENUITEM_RESET;
			
		} else
			Logger.GetLogger().severe(
					CatalogAdmintrationEntryPoint.class.toString(),
					ActualState.getUser().toString(),
					ConstantsError.ERROR_LOADING_LANGUAGE_IN
							+ ADMINISTRATIONCATALOGENTRYPOINT);
		ParsearFieldsAItemsRESET();
		}
	}
	
	/**
	 * Parsea los elementos a default en caso de error.
	 */
	private static void ParsearFieldsAItemsRESET() {
		CATALOGEMENUITEM = CATALOGEMENUITEM_RESET;
		NEWCATALOGMENUITEM = NEWCATALOGMENUITEM_RESET;
		BACKMENUITEM = BACKMENUITEM_RESET;

		
	}
}
