package lector.client.admin.group;

import java.util.ArrayList;
import java.util.List;

import lector.client.admin.users.EntidadUser;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.ConstantsError;
import lector.client.controler.ConstantsInformation;
import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;
import lector.share.model.client.StudentClient;
import lector.share.model.client.UserClient;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class AcceptUsers2group extends PopupPanel {

	private GroupAndUserPanel GAUP;
	private ScrollPanel InsertionPanel;
//	private StackPanelMio stackPanel_1;
	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	private AcceptUsers2group Yo;
	
	public AcceptUsers2group(GroupAndUserPanel GAUPin) {
		super(true);
		setGlassEnabled(true);
		GAUP=GAUPin;
		Yo=this;
		SimplePanel simple = new SimplePanel();
		setWidget(simple);
		simple.setSize("474px", "512px");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		simple.setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		MenuBar menuBar = new MenuBar(false);
		verticalPanel.add(menuBar);
		
		MenuItem mntmClose = new MenuItem("Close", false, new Command() {
			public void execute() {
				hide();
			}
		});
		menuBar.addItem(mntmClose);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		
		MenuItem SalectAll = new MenuItem("New item", false, new Command() {
			public void execute() {
				for (Widget widget : InsertionPanel) {
					SelectionCheckboxElement SCE=(SelectionCheckboxElement)widget;
					SCE.setCheckBoxState(true);
				}
			}
		});
		SalectAll.setHTML("SelectAll");
		menuBar.addItem(SalectAll);
		
		MenuItem UnselectAll = new MenuItem("New item", false, new Command() {
			public void execute() {
				for (Widget widget : InsertionPanel) {
					SelectionCheckboxElement SCE=(SelectionCheckboxElement)widget;
					SCE.setCheckBoxState(true);
				}
			}
		});
		UnselectAll.setHTML("UnselectAll");
		menuBar.addItem(UnselectAll);
		
		InsertionPanel = new ScrollPanel();
		verticalPanel.add(InsertionPanel);
		InsertionPanel.setHeight("434px");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setWidth("100%");
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		horizontalPanel.add(verticalPanel_1);
		
		Button Acept = new Button("Acept");
		Acept.addClickHandler(new ClickHandler() {
			private ArrayList<String> userIdsName;

			public void onClick(ClickEvent event) {
				ArrayList<Long> userIds=new ArrayList<Long>();
				userIdsName=new ArrayList<String>();
				for (Widget widget : InsertionPanel) {
					SelectionCheckboxElement SCE=(SelectionCheckboxElement)widget;
					if (SCE.getCheckBox().getValue()){
						userIds.add(SCE.getStudentClient().getId());
						userIdsName.add(SCE.getStudentClient().toString());
					}
						
				}
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto(ConstantsInformation.VALIDATING);
				bookReaderServiceHolder.validateStudentsToBeInGroup(userIds, GAUP.getMygroup().getId(), new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						LoadingPanel.getInstance().hide();
						Logger.GetLogger().severe(Yo.getClass().toString(), 
								  ActualState.getUser().toString(),
								  "Validate in group " + GAUP.getMygroup().getName() + " Users : " +userIdsName);
						GAUP.refresh();
						hide();						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert(ConstantsError.ERROR_IN_VALIDATION);
						Logger.GetLogger().severe(Yo.getClass().toString(), 
												  ActualState.getUser().toString(),
												  ConstantsError.ERROR_IN_VALIDATION + " " + GAUP.getMygroup().getName() +  ConstantsError.ERROR_IN_VALIDATION2  +userIdsName);
						hide();	
					}
				});
			}
		});
		verticalPanel_1.add(Acept);
		Acept.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});

		Acept.addMouseOutHandler(new MouseOutHandler() {
	public void onMouseOut(MouseOutEvent event) {
		((Button) event.getSource())
				.setStyleName("gwt-ButtonCenter");
	}
});

		Acept
		.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");

			}
		});

		Acept.setStyleName("gwt-ButtonCenter");
		Acept.setSize("164px", "50px");
		
				
		

List<StudentClient> result=GAUP.getMygroup().getRemainingUsers();
for (UserClient User1 : result) {
	InsertionPanel.add(new SelectionCheckboxElement((StudentClient) User1));
	
}
		
		
		
		
		
		
	}
	
	public void setGAUP(GroupAndUserPanel gAUP) {
		GAUP = gAUP;
	}

}
