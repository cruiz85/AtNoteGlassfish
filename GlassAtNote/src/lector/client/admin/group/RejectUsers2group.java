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

public class RejectUsers2group extends PopupPanel {

	private GroupAndUserPanel GAUP;
	private ScrollPanel InsertionPanel;
//	private StackPanelMio stackPanel_1;
	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	private RejectUsers2group Yo;
	
	public RejectUsers2group(GroupAndUserPanel GAUPin) {
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
		
		Button Acept = new Button("Reject");
		Acept.addClickHandler(new ClickHandler() {
			private ArrayList<String> userIdsName;

			public void onClick(ClickEvent event) {
				ArrayList<Long> userIds=new ArrayList<Long>();
				userIdsName= new ArrayList<String>();
				for (Widget widget : InsertionPanel) {
					SelectionCheckboxElement SCE=(SelectionCheckboxElement)widget;
					if (SCE.getCheckBox().getValue()){
						userIds.add(SCE.getStudentClient().getId());
						userIdsName.add(SCE.getStudentClient().toString());
					}
				}
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto(ConstantsInformation.REJECTING);
				bookReaderServiceHolder.removeStudentsToBeValidated(userIds, GAUP.getMygroup().getId(), new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						Logger.GetLogger().severe(Yo.getClass().toString(),
								ActualState.getUser().toString(),
								" Rejected in Group " + GAUP.getMygroup().getName() + " Users : " +userIdsName);
						LoadingPanel.getInstance().hide();
						GAUP.refresh();
						hide();						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert(ConstantsError.ERROR_IN_REJECTION);
						Logger.GetLogger().severe(Yo.getClass().toString(),
								ActualState.getUser().toString(),
								ConstantsError.ERROR_IN_REJECTION  + GAUP.getMygroup().getName() + " Users : " +userIdsName);
						
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
		
				
		
		
		
//		stackPanel_1 = new StackPanelMio();
//		stackPanel_1.setBotonTipo(new BotonesStackPanelGroupUserMio(
//				"prototipo", new VerticalPanel(),null));
//		
//		stackPanel_1.setBotonClick(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				UserClient UsuarioNombre=((BotonesStackPanelGroupUserMio)event.getSource()).getUser();

//						ArrayList<Long> ListaYa=GAUP.getMygroup().getUsersIds();
//						if (!noestaenlalista(result,ListaYa)){
//							GAUP.getMygroup().getUsersIds().add(result.getId());
//						bookReaderServiceHolder.saveGroup(GAUP.getMygroup(), new AsyncCallback<Void>() {
//
//							public void onFailure(Throwable caught) {
//								Window.alert("The group could not saved");
//								
//							}
//
//							public void onSuccess(Void result) {
//								GAUP.refresh();
//								
//							}
//						});
//						}
//						else Window.alert("The user was in the list before");
						
						
//					}
//				});
//
//			}
//
////			private boolean noestaenlalista(UserApp userEnter, ArrayList<Long> listaYa) {
////				for (Long UsersT : listaYa) {
////					if (UsersT.equals(userEnter.getId()))return true;
////				}
////				return false;
////			}
//		});
List<StudentClient> result=GAUP.getMygroup().getRemainingUsers();
for (UserClient User1 : result) {
	InsertionPanel.add(new SelectionCheckboxElement((StudentClient) User1));
	
}
//						stackPanel_1.addBotonLessTen(E);
					
			
//				stackPanel_1.ClearEmpty();
				
//			}
			
//		
//		stackPanel_1.setSize("100%", "100%");
//		verticalPanel.add(stackPanel_1);
//	
		
		
		
		
		
		
	}
	
	public void setGAUP(GroupAndUserPanel gAUP) {
		GAUP = gAUP;
	}

}
