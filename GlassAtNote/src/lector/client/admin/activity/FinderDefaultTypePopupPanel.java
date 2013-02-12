package lector.client.admin.activity;

import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.controler.catalogo.FinderKeys;
import lector.client.logger.Logger;
import lector.client.reader.BotonesStackPanelReaderSelectMio;
import lector.share.model.Language;
import lector.share.model.client.CatalogoClient;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FinderDefaultTypePopupPanel extends PopupPanel {

	private static final String FINDER_DEFAULT_TYPE_NAME = "Finder default type popup";
	
	private static final int NCampos=1;
	
	private static final int DecoradorWidth = 2;
	
	private static String CLOSE = "Close";
	
	private static final String CLOSE_RESET = "Close";
	
	private MenuItem CloseButton;
	
	private TextBox CloseButtonTextBox;
	
	private FinderDefaultTypePopupPanel Yo;
	private AbsolutePanel GeneralPanel;
	private AbsolutePanel PanelEdicion;

	

	public FinderDefaultTypePopupPanel(EditorActivityPopupPanel EA,
			CatalogoClient selectedCatalog) {
		super(true);
		setGlassEnabled(true);
		setModal(false);
		Yo=this;
		setAnimationEnabled(true);
		DockLayoutPanel dockPanel = new DockLayoutPanel(Unit.PX);
		
		MenuBar menuBar = new MenuBar(false);
        dockPanel.addNorth(menuBar, 20);
        menuBar.setSize("100%", "20px");
        CloseButton = new MenuItem("New item", false, new Command() {
        	public void execute() {
        		hide();
        	}

		 });
        CloseButton.setHTML(FinderDefaultTypePopupPanel.CLOSE );
        menuBar.addItem(CloseButton);
        
      dockPanel.setSize("100%", "100%");
      GeneralPanel = new AbsolutePanel();
     // setWidget(dockPanel);
      setWidget(GeneralPanel);
      GeneralPanel.add(dockPanel,0,0);
        dockPanel.setSize(Window.getClientWidth()-100+"px", Window.getClientHeight()-100+"px");
        GeneralPanel.setSize(Window.getClientWidth()-100+"px", Window.getClientHeight()-100+"px");
        FinderKeys.setButtonTipo(new BotonesStackPanelReaderSelectMio("prototipo", new VerticalPanel(), new HorizontalPanel()));
		 FinderKeys.setBotonClick(new MioSelectorDefaultClickHandler(EA,Yo));
        FinderKeys FK= new FinderKeys();
        dockPanel.add(FK);
        FK.setSize("100%", "100%");
        FK.setCatalogo(selectedCatalog);
        FK.RefrescaLosDatos();
        PanelEdicion=new AbsolutePanel();
        
        
	}

	public void closeEditPanel()
	{
		GeneralPanel.remove(PanelEdicion);
		GeneralPanel.add(PanelEdicion, GeneralPanel.getOffsetWidth()-40, 0);
		PanelEdicion.setSize(Constants.TAMANOBOTOBEDITOFF+Constants.PX,"50px");
		PanelEdicion.clear();
		PanelEdicion.setStyleName("");
		Button Boton=new Button();
		PanelEdicion.add(Boton,0, 0);
		Boton.setHTML(InformationConstants.EDIT_BOTTON);
		Boton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				OpenEditPanel();
				
			}
		});
		
	}
	
	public void OpenEditPanel() {
		GeneralPanel.remove(PanelEdicion);
		GeneralPanel.add(PanelEdicion, 0, 0);
		PanelEdicion.setSize(GeneralPanel.getOffsetWidth()+"px",GeneralPanel.getOffsetHeight()+"px");
		PanelEdicion.clear();
		PanelEdicion.setStyleName("BlancoTransparente");
		Button Boton=new Button();
		PanelEdicion.add(Boton,PanelEdicion.getOffsetWidth()-65, 0);
		Boton.setHTML(InformationConstants.END_EDIT_BOTTON);
		Boton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				closeEditPanel();
				
				if (!CloseButtonTextBox.getText().isEmpty())
					CLOSE=CloseButtonTextBox.getText();
				else CLOSE=CLOSE_RESET;	
				ParsearFieldsAItems();
				SaveChages();
			}
		});
		CloseButtonTextBox=new TextBox();
		CloseButtonTextBox.setText(CLOSE);
		CloseButtonTextBox.setSize(CloseButton.getOffsetWidth()+"px", CloseButton.getOffsetHeight()+"px");
		PanelEdicion.add(CloseButtonTextBox, CloseButton.getAbsoluteLeft()-GeneralPanel.getAbsoluteLeft()-DecoradorWidth, CloseButton.getAbsoluteTop()-GeneralPanel.getAbsoluteTop()-DecoradorWidth);
		
		
	}
	
	protected void SaveChages() {
		Language LanguageActual = ActualState.getActualLanguage();
			String FinderDefaultTypeLanguageConfiguration=toFile();
		LanguageActual.setFinderDefaultTypeLanguageConfiguration(FinderDefaultTypeLanguageConfiguration);
		ActualState.saveLanguageActual(LanguageActual);
	}

	public void ParsearFieldsAItems()
	{
	CloseButton.setHTML(CLOSE);	
	}
	
	public static String toFile() {
		StringBuffer SB=new StringBuffer();
		SB.append(CLOSE+"\r\n");
		return SB.toString();
	}
	
	public static void FromFile(String Entrada) {
		if (Entrada.length()==0) 
			ParsearFieldsAItemsRESET();
		else
		{
		String[] Lista = Entrada.split("\r\n");
		if (Lista.length >= NCampos) {
			if (!Lista[0].isEmpty())
				CLOSE = Lista[0];
			else CLOSE=CLOSE_RESET;
		}
		else 
			Logger.GetLogger().severe(FinderDefaultTypePopupPanel.class.toString(), ActualState.getUser().toString(), ErrorConstants.ERROR_LOADING_LANGUAGE_IN  + FINDER_DEFAULT_TYPE_NAME);
		ParsearFieldsAItemsRESET();
		}
	}
	
	private static void ParsearFieldsAItemsRESET() {
		 CLOSE=CLOSE_RESET;
		
	}
	
	@Override
	public void center() {
		super.center();
		if (ActualState.isLanguageActive())
			closeEditPanel();
	}
	
}
