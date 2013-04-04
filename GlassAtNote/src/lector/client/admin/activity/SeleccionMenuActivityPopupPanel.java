package lector.client.admin.activity;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.ConstantsError;
import lector.client.controler.ConstantsInformation;
import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;
import lector.share.model.Language;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

public class SeleccionMenuActivityPopupPanel extends PopupPanel {

	private static final String SELECTION_MENU_ACTIVITY = "Selection Menu  Popup";
	
	private static final int NCampos=3;
	
	private static final int DecoradorWidth = 2;
	
	private static String DELETE="Delete";
	private static String CHANGE_VISIBILITY = "Edit Visibility";
	private static String EDIT="Edit";
	
	public static String ARE_YOU_SURE_DELETE_READING_ACTIVITY = "Are you sure to delete the Reading Activity?, all the anotations will be delete too";
	
	
	private static String DELETE_RESET="Delete";
	private static String CHANGE_VISIBILITY_RESET = "Edit Visibility";
	private static String EDIT_RESET="Edit";
	
	private Button DeleteButton;
	private Button EditVisivitlityButton;
	private Button EditButton;
	
	private TextBox DeleteButtonTextBox;
	private TextBox EditVisivitlityButtonTextBox;
	private TextBox EditButtonTextBox;
	
	private ActivityBotton BLan;
	private AdminActivitiesEntryPoint Father;

	private AbsolutePanel GeneralPanel;

	private VerticalPanel PanelActivity;

	private SimplePanel EditorZone;

	private AbsolutePanel PanelEdicion;


	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	
	
	
	public SeleccionMenuActivityPopupPanel(ActivityBotton BL, AdminActivitiesEntryPoint Fatherin) {
		super(true);
		BLan=BL;
//		setSize("", "");
		Father=Fatherin;
		 PanelActivity = new VerticalPanel();
		 GeneralPanel = new AbsolutePanel();
		 EditorZone=new SimplePanel();
		 EditorZone.setVisible(false);
		EditorZone.setHeight(Constants.TAMANO_PANEL_EDICION);
		PanelActivity.add(EditorZone);
		PanelActivity.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		GeneralPanel.add(PanelActivity,0,0);
		GeneralPanel.setSize(Window.getClientWidth()+Constants.PX, Window.getClientHeight()+Constants.PX);
		setWidget(GeneralPanel);
		
		//PanelActivity.setSize("100%", "100%");
				
		DeleteButton = new Button(SeleccionMenuActivityPopupPanel.DELETE);
		DeleteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//TODO Mensaje en Informacion
				if (Window.confirm(SeleccionMenuActivityPopupPanel.ARE_YOU_SURE_DELETE_READING_ACTIVITY)){
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto(ConstantsInformation.DELETING);
				bookReaderServiceHolder.deleteReadingActivity(BLan.getReadingActivity().getId(), new AsyncCallback<Void>() {
					
					public void onSuccess(Void result) {
						LoadingPanel.getInstance().hide();
						Father.refresh();
						hide();
					}
					
					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
					Window.alert(ConstantsError.ERROR_DELETING_READING_ACTIVITY);
						
					}
				});
				}
			}
		});
		DeleteButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		DeleteButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		DeleteButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		DeleteButton.setStyleName("gwt-ButtonTOP");
		PanelActivity.add(DeleteButton);
		DeleteButton.setSize("100%", "100%");
		
		
		
		EditVisivitlityButton = new Button(SeleccionMenuActivityPopupPanel.CHANGE_VISIBILITY );
		EditVisivitlityButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ReadingActivityVisibilityPopupPanel RAVC= new ReadingActivityVisibilityPopupPanel(BLan.getReadingActivity(),Father);
				RAVC.center();
			}
		});
		PanelActivity.add(EditVisivitlityButton);
		EditVisivitlityButton.setSize("100%", "100%");
		EditVisivitlityButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		EditVisivitlityButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		EditVisivitlityButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		EditVisivitlityButton.setStyleName("gwt-ButtonTOP");
		
		EditButton = new Button(SeleccionMenuActivityPopupPanel.EDIT );
		EditButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				EditorActivityPopupPanel EA=new EditorActivityPopupPanel(BLan.getReadingActivity());
				EA.center();
				EA.setModal(true);
				hide();
			}
		});
		EditButton.setStyleName("gwt-ButtonBotton");
		EditButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
			}
		});
		EditButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
			}
		});
		EditButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
			}
		});
		PanelActivity.add(EditButton);
		EditButton.setSize("100%", "100%");
		PanelEdicion=new AbsolutePanel();
	}

	@Override
	public void show() {
		super.show();
		EditorZone.setVisible(false);
		GeneralPanel.setSize(PanelActivity.getOffsetWidth()+Constants.PX, PanelActivity.getOffsetHeight()+Constants.PX);
		
		if (ActualState.isLanguageActive())
			{
			GeneralPanel.setHeight(Constants.TAMANO_PANEL_EDICION_INT+40+Constants.PX);
			EditorZone.setVisible(true);
			GeneralPanel.setSize(PanelActivity.getOffsetWidth()+Constants.PX, PanelActivity.getOffsetHeight()+Constants.PX);
			closeEditPanel();
			}
	}
	
	private void closeEditPanel() {
		GeneralPanel.remove(PanelEdicion);
		GeneralPanel.add(PanelEdicion,PanelActivity.getOffsetWidth()-40, 0);
		PanelEdicion.setSize(Constants.TAMANOBOTOBEDITOFF+Constants.PX,"50px");
		PanelEdicion.clear();
		PanelEdicion.setStyleName("");
		Button Boton=new Button();
		PanelEdicion.add(Boton,0,0);
		Boton.setHTML(ConstantsInformation.EDIT_BOTTON);
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
		
		Boton.setHTML(ConstantsInformation.END_EDIT_BOTTON);
		Boton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				closeEditPanel();
				
				if (!DeleteButtonTextBox.getText().isEmpty())
					DELETE=DeleteButtonTextBox.getText();
				else DELETE=DELETE_RESET;

				if (EditVisivitlityButtonTextBox.getText().isEmpty())
					CHANGE_VISIBILITY=EditVisivitlityButtonTextBox.getText();
			else CHANGE_VISIBILITY=CHANGE_VISIBILITY_RESET;
				
				if (!EditButtonTextBox.getText().isEmpty())
					EDIT=EditButtonTextBox.getText();
			else EDIT=EDIT_RESET;

				ParsearFieldsAItems();
				SaveChages();
			}
		});
		DeleteButtonTextBox=new TextBox();
		DeleteButtonTextBox.setText(DELETE);
		DeleteButtonTextBox.setSize(DeleteButton.getOffsetWidth()+"px", DeleteButton.getOffsetHeight()+"px");
		PanelEdicion.add(DeleteButtonTextBox, DeleteButton.getAbsoluteLeft()-PanelActivity.getAbsoluteLeft()-DecoradorWidth, DeleteButton.getAbsoluteTop()-PanelActivity.getAbsoluteTop()-DecoradorWidth);
		
		EditVisivitlityButtonTextBox=new TextBox();
		EditVisivitlityButtonTextBox.setText(CHANGE_VISIBILITY);
		EditVisivitlityButtonTextBox.setSize(EditVisivitlityButton.getOffsetWidth()+"px", EditVisivitlityButton.getOffsetHeight()+"px");
		PanelEdicion.add(EditVisivitlityButtonTextBox, EditVisivitlityButton.getAbsoluteLeft()-PanelActivity.getAbsoluteLeft()-DecoradorWidth, EditVisivitlityButton.getAbsoluteTop()-PanelActivity.getAbsoluteTop()-DecoradorWidth);
		
		EditButtonTextBox=new TextBox();
		EditButtonTextBox.setText(EDIT);
		EditButtonTextBox.setSize(EditButton.getOffsetWidth()+"px", EditButton.getOffsetHeight()+"px");
		PanelEdicion.add(EditButtonTextBox, EditButton.getAbsoluteLeft()-PanelActivity.getAbsoluteLeft()-DecoradorWidth, EditButton.getAbsoluteTop()-PanelActivity.getAbsoluteTop()-DecoradorWidth);
		
		PanelEdicion.add(Boton,PanelEdicion.getOffsetWidth()-Constants.TAMANOBOTOBEDITON, 0);
	}

	protected void SaveChages() {
		Language LanguageActual = ActualState.getActualLanguage();
		String SeleccionMenuActivityPopupPanelLanguageConfiguration=toFile();
		LanguageActual.setSeleccionMenuActivityPopupPanelLanguageConfiguration(SeleccionMenuActivityPopupPanelLanguageConfiguration);
		ActualState.saveLanguageActual(LanguageActual);
		
	}

	public String toFile() {
		StringBuffer SB=new StringBuffer();
		SB.append(DELETE + "\r\n");
		SB.append( CHANGE_VISIBILITY + "\r\n" );
		SB.append( EDIT + "\r\n" );
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
				DELETE = Lista[0];
			else DELETE=DELETE_RESET;
			if (!Lista[1].isEmpty())
				CHANGE_VISIBILITY = Lista[1];
			else CHANGE_VISIBILITY=CHANGE_VISIBILITY_RESET;
			if (!Lista[2].isEmpty())
				EDIT = Lista[2];
			else EDIT=EDIT_RESET;
		}
		else 
			Logger.GetLogger().severe(SeleccionMenuActivityPopupPanel.class.toString(), ActualState.getUser().toString(), ConstantsError.ERROR_LOADING_LANGUAGE_IN  + SELECTION_MENU_ACTIVITY);	
		ParsearFieldsAItemsRESET();
		}
	}
	
	private static void ParsearFieldsAItemsRESET() {
		DELETE=DELETE_RESET;
		CHANGE_VISIBILITY=CHANGE_VISIBILITY_RESET;
		EDIT=EDIT_RESET;
		
	}
	
	protected void ParsearFieldsAItems() {

		DeleteButton.setHTML(DELETE);	
		EditVisivitlityButton.setHTML(CHANGE_VISIBILITY);
		EditButton.setHTML(EDIT);
		
	}
}
