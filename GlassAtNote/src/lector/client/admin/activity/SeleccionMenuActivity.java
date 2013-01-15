package lector.client.admin.activity;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.reader.LoadingPanel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
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

public class SeleccionMenuActivity extends PopupPanel {

	private static String CHANGE_VISIBILITY = "Edit Visibility";
	private static String EDIT="Edit";
	private static String DELETE="Delete";
	public static String ARE_YOU_SURE_DELETE_READING_ACTIVITY = "Are you sure to delete the Reading Activity?, all the anotations will be delete too";
	
	private ActivityBotton BLan;
	private AdminActivitiesEntryPoint Father;
	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	
	
	
	public SeleccionMenuActivity(ActivityBotton BL, AdminActivitiesEntryPoint Fatherin) {
		super(true);
		BLan=BL;
//		setSize("", "");
		Father=Fatherin;
		VerticalPanel verticalPanel = new VerticalPanel();
		setWidget(verticalPanel);
		//verticalPanel.setSize("100%", "100%");
				
		Button btnNewButton_1 = new Button(SeleccionMenuActivity.DELETE);
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (Window.confirm(SeleccionMenuActivity.ARE_YOU_SURE_DELETE_READING_ACTIVITY)){
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
		btnNewButton_1.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		btnNewButton_1.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		btnNewButton_1.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		btnNewButton_1.setStyleName("gwt-ButtonTOP");
		verticalPanel.add(btnNewButton_1);
		btnNewButton_1.setSize("100%", "100%");
		
		
		
		Button btnNewButton_3 = new Button(SeleccionMenuActivity.CHANGE_VISIBILITY );
		btnNewButton_3.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ReadingActivityVisibilityChangePanel RAVC= new ReadingActivityVisibilityChangePanel(BLan.getReadingActivity(),Father);
				RAVC.center();
			}
		});
		verticalPanel.add(btnNewButton_3);
		btnNewButton_3.setSize("100%", "100%");
		btnNewButton_3.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		btnNewButton_3.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		btnNewButton_3.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		btnNewButton_3.setStyleName("gwt-ButtonTOP");
		
		Button btnNewButton_2 = new Button(SeleccionMenuActivity.EDIT );
		btnNewButton_2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				EditorActivityPopupPanel EA=new EditorActivityPopupPanel(BLan.getReadingActivity());
				EA.center();
				EA.setModal(true);
				hide();
			}
		});
		btnNewButton_2.setStyleName("gwt-ButtonBotton");
		btnNewButton_2.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
			}
		});
		btnNewButton_2.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
			}
		});
		btnNewButton_2.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
			}
		});
		verticalPanel.add(btnNewButton_2);
		btnNewButton_2.setSize("100%", "100%");
	}

}
