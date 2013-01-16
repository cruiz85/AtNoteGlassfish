package lector.client.admin.activity;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;
import lector.share.model.Language;
import lector.share.model.client.CatalogoClient;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

public class SeleccionCatalogoPopupPanel extends PopupPanel {

	private static final String SelectionCatalogPopupPanel = "Selection Catalog Popup";
	
	private static final int NCampos=2;

	private static final int DecoradorWidth = 2;
	
	public static String BOTTON_TEACHER_CATALOG="Teacher Catalog";
	public static String BOTTON_OPEN_CATALOG="Open Catalog";
	
	public static final String BOTTON_TEACHER_CATALOG_RESET="Teacher Catalog";
	public static final String BOTTON_OPEN_CATALOG_RESET="Open Catalog";
	
	private Button SelectTeacherCatalogButton;
	private Button SelectOpenCatalogButton;
	
	private TextBox SelectTeacherCatalogButtonTextBox;
	private TextBox SelectOpenCatalogButtonTextBox;
	
	private CatalogoClient Catalogo;
	private Label LPrivate;
	private Label LPublic;
	private EditorActivityPopupPanel Father;

	private AbsolutePanel GeneralPanel;

	private VerticalPanel PanelActivity;
	private SimplePanel EditorZone;
	private AbsolutePanel PanelEdicion;
	

	
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	
	public SeleccionCatalogoPopupPanel(CatalogoClient catalogo, Label catalogLabel,
			Label openCatalogLabel, EditorActivityPopupPanel yo) {
		
		super(true);
		Catalogo=catalogo;
		LPrivate=catalogLabel;
		LPublic=openCatalogLabel;
		Father=yo;
		GeneralPanel = new AbsolutePanel();
		setAnimationEnabled(true);
		HorizontalPanel PanelActivityH = new HorizontalPanel();
		PanelActivity = new VerticalPanel();
		PanelActivity.add(PanelActivityH);
		PanelActivityH.setSpacing(4);
		EditorZone=new SimplePanel();
		EditorZone.setHeight(Constants.TAMANO_PANEL_EDICION);
		PanelActivity.add(EditorZone);
		GeneralPanel.add(PanelActivity,0,0);
		setWidget(GeneralPanel);
		PanelActivityH.setSize("100%", "100%");
		
		SelectTeacherCatalogButton = new Button(SeleccionCatalogoPopupPanel.BOTTON_TEACHER_CATALOG);
		SelectTeacherCatalogButton.setSize("100%", "100%");
		SelectTeacherCatalogButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		SelectTeacherCatalogButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});
		SelectTeacherCatalogButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			}
		});
		SelectTeacherCatalogButton.setStyleName("gwt-ButtonCenter");
		SelectTeacherCatalogButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				LPrivate.setText("Teacher Catalog :"
						+ Catalogo.getCatalogName());
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto(InformationConstants.LOADING);
				bookReaderServiceHolder.loadCatalogById(Catalogo.getId(), new AsyncCallback<CatalogoClient>() {
					
					@Override
					public void onSuccess(CatalogoClient result) {
						LoadingPanel.getInstance()
						.hide();
						Father.setSelectedCatalog(result);
						Father.setPanel_Selecion_Default_Visibility(true);
						hide();
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance()
						.hide();
						Window.alert(ErrorConstants.ERROR_LOADING_CATALOG);
						hide();
						
					}
				});
				
				
			}
		});
		PanelActivityH.add(SelectTeacherCatalogButton);
		
		SelectOpenCatalogButton = new Button(SeleccionCatalogoPopupPanel.BOTTON_OPEN_CATALOG);
		SelectOpenCatalogButton.setSize("100%", "100%");
		SelectOpenCatalogButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		SelectOpenCatalogButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});
		SelectOpenCatalogButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			}
		});
		SelectOpenCatalogButton.setStyleName("gwt-ButtonCenter");
		SelectOpenCatalogButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				LPublic.setText("Open Catalog :"
						+ Catalogo.getCatalogName());
				Father.setSelectedCatalogPublic(Catalogo);
				hide();
			}
		});
		PanelActivityH.add(SelectOpenCatalogButton);
		PanelEdicion=new AbsolutePanel();
	}

	@Override
	public void center() {
		super.center();
		EditorZone.setVisible(false);
		if (ActualState.isLanguageActive())
			{
			EditorZone.setVisible(true);
			closeEditPanel();
			}
	}
	
	private void closeEditPanel() {
		GeneralPanel.remove(PanelEdicion);
		GeneralPanel.add(PanelEdicion,PanelActivity.getOffsetWidth()-40, 0);
		PanelEdicion.setSize("40px","50px");
		PanelEdicion.clear();
		PanelEdicion.setStyleName("");
		Button Boton=new Button();
		PanelEdicion.add(Boton,0,0);
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
		GeneralPanel.add(PanelEdicion,0,0);
		PanelEdicion.setSize(PanelActivity.getOffsetWidth()+"px",PanelActivity.getOffsetHeight()+"px");
		PanelEdicion.clear();
		PanelEdicion.setStyleName("BlancoTransparente");
		Button Boton=new Button();
		
		Boton.setHTML(InformationConstants.END_EDIT_BOTTON);
		Boton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				closeEditPanel();
				
				if (!SelectTeacherCatalogButtonTextBox.getText().isEmpty())
					BOTTON_TEACHER_CATALOG=SelectTeacherCatalogButtonTextBox.getText();
				else BOTTON_TEACHER_CATALOG=BOTTON_TEACHER_CATALOG_RESET;

				if (!SelectOpenCatalogButtonTextBox.getText().isEmpty())
					BOTTON_OPEN_CATALOG=SelectOpenCatalogButtonTextBox.getText();
			else BOTTON_OPEN_CATALOG=BOTTON_OPEN_CATALOG_RESET;
				
				ParsearFieldsAItems();
				SaveChages();
			}
		});
		SelectTeacherCatalogButtonTextBox=new TextBox();
		SelectTeacherCatalogButtonTextBox.setText(BOTTON_TEACHER_CATALOG);
		SelectTeacherCatalogButtonTextBox.setSize(SelectTeacherCatalogButton.getOffsetWidth()+"px", SelectTeacherCatalogButton.getOffsetHeight()+"px");
		PanelEdicion.add(SelectTeacherCatalogButtonTextBox, SelectTeacherCatalogButton.getAbsoluteLeft()-PanelActivity.getAbsoluteLeft()-DecoradorWidth, SelectTeacherCatalogButton.getAbsoluteTop()-PanelActivity.getAbsoluteTop()-DecoradorWidth);
		
		SelectOpenCatalogButtonTextBox=new TextBox();
		SelectOpenCatalogButtonTextBox.setText(BOTTON_OPEN_CATALOG);
		SelectOpenCatalogButtonTextBox.setSize(SelectOpenCatalogButton.getOffsetWidth()+"px", SelectOpenCatalogButton.getOffsetHeight()+"px");
		PanelEdicion.add(SelectOpenCatalogButtonTextBox, SelectOpenCatalogButton.getAbsoluteLeft()-PanelActivity.getAbsoluteLeft()-DecoradorWidth, SelectOpenCatalogButton.getAbsoluteTop()-PanelActivity.getAbsoluteTop()-DecoradorWidth);
		
		PanelEdicion.add(Boton,PanelEdicion.getOffsetWidth()-Constants.TAMANOBOTOBEDITON, 0);
	}

	protected void SaveChages() {
		Language LanguageActual = ActualState.getActualLanguage();
		String SeleccionCatalogoPopupPanelLanguageConfiguration=toFile();
		LanguageActual.setSeleccionCatalogoPopupPanelLanguageConfiguration(SeleccionCatalogoPopupPanelLanguageConfiguration);
		ActualState.saveLanguageActual(LanguageActual);
		
	}

	public String toFile() {
		StringBuffer SB=new StringBuffer();
		SB.append(BOTTON_TEACHER_CATALOG + '\n');
		SB.append( BOTTON_OPEN_CATALOG + '\n' );
		return SB.toString();
	}

	public static void FromFile(String Entrada) {
		String[] Lista = Entrada.split("\n");
		if (Lista.length >= NCampos) {
			if (!Lista[0].isEmpty())
				BOTTON_TEACHER_CATALOG = Lista[0];
			else BOTTON_TEACHER_CATALOG=BOTTON_TEACHER_CATALOG_RESET;
			if (!Lista[1].isEmpty())
				BOTTON_OPEN_CATALOG = Lista[1];
			else BOTTON_OPEN_CATALOG=BOTTON_OPEN_CATALOG_RESET;
		}
		else 
			Logger.GetLogger().severe(EditorActivityPopupPanel.class.toString(), ActualState.getUser().toString(), ErrorConstants.ERROR_LOADING_LANGUAGE_IN  + SelectionCatalogPopupPanel);	
	}
	
	protected void ParsearFieldsAItems() {

		SelectTeacherCatalogButton.setHTML(BOTTON_TEACHER_CATALOG);	
		SelectOpenCatalogButton.setHTML(BOTTON_OPEN_CATALOG);
		
	}

}
