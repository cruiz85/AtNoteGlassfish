package lector.client.admin.activity;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Constants;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.reader.LoadingPanel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
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
	
	private static final int NCampos=4;
	
	private static final int DecoradorWidth = 2;
	
	private static String DELETE="Delete";
	public static String ARE_YOU_SURE_DELETE_READING_ACTIVITY = "Are you sure to delete the Reading Activity?, all the anotations will be delete too";
	private static String CHANGE_VISIBILITY = "Edit Visibility";
	private static String EDIT="Edit";
	
	private Button DeleteButton;
	private Button EditVisivitlityButton;
	private Button EditButton;
	
	private ActivityBotton BLan;
	private AdminActivitiesEntryPoint Father;

	private AbsolutePanel GeneralPanel;

	private VerticalPanel PanelActivity;

	private SimplePanel EditorZone;


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
				LoadingPanel.getInstance().setLabelTexto(InformationConstants.DELETING);
				bookReaderServiceHolder.deleteReadingActivity(BLan.getReadingActivity().getId(), new AsyncCallback<Void>() {
					
					public void onSuccess(Void result) {
						LoadingPanel.getInstance().hide();
						Father.refresh();
						hide();
					}
					
					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
					Window.alert(ErrorConstants.ERROR_DELETING_READING_ACTIVITY);
						
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
	}

	@Override
	public void show() {
		super.show();
		GeneralPanel.setSize(PanelActivity.getOffsetWidth()+Constants.PX, PanelActivity.getOffsetHeight()+Constants.PX);
	}
}
