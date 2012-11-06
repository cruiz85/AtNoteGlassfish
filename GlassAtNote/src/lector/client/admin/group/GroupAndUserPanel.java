package lector.client.admin.group;

import java.util.ArrayList;
import java.util.List;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.CalendarNow;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.logger.Logger;
import lector.client.login.ActualUser;
import lector.client.reader.LoadingPanel;
import lector.share.model.GroupApp;
import lector.share.model.UserApp;
import lector.share.model.client.GroupClient;
import lector.share.model.client.StudentClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;

public class GroupAndUserPanel extends Composite {

	private GroupAndUserPanel Yo;
	private GroupClient Mygroup;
	private VerticalPanel Panel_Usuarios;
	private SimplePanel PanelMioDentro;
	private Groupadministration Father;

	
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);

	public GroupAndUserPanel(GroupClient Grupo,SimplePanel panel_grupo_seleccionado,Groupadministration Fatherin) {

		Yo = this;
		Mygroup = Grupo;

		PanelMioDentro=panel_grupo_seleccionado;
		
		Father = Fatherin;	
		FlowPanel flowPanel = new FlowPanel();
		flowPanel.setStyleName("BlancoTransparente");
		initWidget(flowPanel);
		flowPanel.setSize("100%", "100%");
		
		MenuBar menuBar_2 = new MenuBar(false);
		flowPanel.add(menuBar_2);
		
		MenuItem mntmNewItem_1 = new MenuItem("Group: " + Mygroup.getName(), false, (Command) null);
		mntmNewItem_1.setEnabled(false);
		menuBar_2.addItem(mntmNewItem_1);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar_2.addSeparator(separator);
		
		MenuItem mntmNewItem_2 = new MenuItem("Delete Group", false, new Command() {
			public void execute() {
				if (Window.confirm(InformationConstants.ARE_YOU_SURE_DELETE_GROUP))
				{
					LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto("Deleting...");
				bookReaderServiceHolder.deleteGroup(Mygroup.getId(), new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert(ErrorConstants.ERROR_DELETING_GROUP);
						Logger.GetLogger().severe(this.getClass().getName(), "Usuario: " + ActualUser.getUser().getEmail()
								+ " "+ ErrorConstants.ERROR_DELETING_GROUP  + " at " + CalendarNow.GetDateNow() );
						
					}

					public void onSuccess(Void result) {
						Logger.GetLogger().info(this.getClass().getName(), "Usuario: " + ActualUser.getUser().getEmail()
								+ " delete a group "+ Mygroup.getClass().toString() + " named " + Mygroup.getName() + " at " + CalendarNow.GetDateNow() );
						LoadingPanel.getInstance().hide();
						PanelMioDentro.clear();
						Father.refreshGroups();
						
					}
				});
				}
			}
		});
		mntmNewItem_2.setHTML("Delete Group");
		menuBar_2.addItem(mntmNewItem_2);

		MenuItem mntmNewItem = new MenuItem("Confirm Pendent Users", false, new Command() {
			public void execute() {
				AcceptUsers2group NU = new AcceptUsers2group(Yo);
				NU.center();
				NU.setModal(true);
			}
		});
		menuBar_2.addItem(mntmNewItem);
		 mntmNewItem.setHTML("Confirm Pendent Users");
		 
		 MenuItem mntmNewItem_3 = new MenuItem("Reject Pendent Users", false, new Command() {
				public void execute() {
					AcceptUsers2group NU = new AcceptUsers2group(Yo);
					NU.center();
					NU.setModal(true);
				}
			});
			menuBar_2.addItem(mntmNewItem_3);
			mntmNewItem_3.setHTML("Remove Pendent Users");

		Panel_Usuarios = new VerticalPanel();
		flowPanel.add(Panel_Usuarios);
		Panel_Usuarios.setWidth("100%");

		refrescaElGrupo();

	}

	

	

	private void refrescaElGrupo() {
		List<StudentClient> result = Mygroup.getParticipatingStudents();
		for (int i = 0; i < result.size() - 1; i++) {
			ButtonGroupMio User = new ButtonGroupMio(result.get(i));
			User.setHTML("<img src=\"Users.gif\">" + result.get(i).getEmail());
			Panel_Usuarios.add(User);
			User.setSize("100%", "100%");
			User.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button) event.getSource()).setStyleName("gwt-ButtonTOP");
				}
			});
			User.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button) event.getSource())
							.setStyleName("gwt-ButtonTOPOver");
				}
			});
			User.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button) event.getSource()).setStyleName("gwt-ButtonPush");
				}
			});
			User.setStyleName("gwt-ButtonTOP");
			User.addClickHandler(new ClickHandler() {

				private StudentClient Borrar;

				public void onClick(ClickEvent event) {
					if (Window
							.confirm("Are you sure to remove the user from the list")) {
						

						ButtonGroupMio BGM = ((ButtonGroupMio) event
								.getSource());
						Borrar=(StudentClient)BGM.getUsuario();
						LoadingPanel.getInstance().center();
						LoadingPanel.getInstance().setLabelTexto("Deleting...");
						bookReaderServiceHolder.removeStudentParticipatingInGroup(BGM.getUsuario().getId(), Mygroup.getId(),new AsyncCallback<Void>() {

							public void onFailure(Throwable caught) {
								LoadingPanel.getInstance().hide();
								Window.alert(ErrorConstants.ERROR_DELETING_USER1  + Borrar + ErrorConstants.ERROR_DELETING_USER2 + Mygroup.getName() );
								Logger.GetLogger().severe(this.getClass().getName(), "Usuario: " + ActualUser.getUser().getEmail()
										+ " "+ ErrorConstants.ERROR_DELETING_USER1  + Borrar + ErrorConstants.ERROR_DELETING_USER2 + Mygroup.getName() + " at " + CalendarNow.GetDateNow() );
								
							}

							public void onSuccess(Void result) {
								Logger.GetLogger().info(this.getClass().getName(), "Usuario: " + ActualUser.getUser().getEmail()
										+ " delete a user "+ Borrar + " from " + Mygroup.getName() + " at " + CalendarNow.GetDateNow() );
								LoadingPanel.getInstance().hide();
								Panel_Usuarios.clear();
								refrescaElGrupo();
								
							}
						} );
						
						//TODO CESAR
//						bookReaderServiceHolder.removeUserAndGroupRelation(BGM
//								.getUsuario().getId(),
//
//						Mygroup.getId(), new AsyncCallback<Void>() {
//
//							public void onFailure(Throwable caught) {
//								Window.alert("The user could not be removed from his/her group");
//
//							}
//
//							public void onSuccess(Void result) {
//								refresh();
//							}
//						});
						

					}

				}
			});
		}
		if (!result.isEmpty()) {
			ButtonGroupMio User = new ButtonGroupMio(
					result.get(result.size() - 1));
			User.setHTML("<img src=\"Users.gif\">"
					+ result.get(result.size() - 1).getEmail());
			Panel_Usuarios.add(User);
			User.setSize("100%", "100%");
			User.setStyleName("gwt-ButtonBotton");
			User.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button) event.getSource())
							.setStyleName("gwt-ButtonBotton");
				}
			});
			User.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					((Button) event.getSource())
							.setStyleName("gwt-ButtonBottonOver");
				}
			});
			User.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button) event.getSource())
							.setStyleName("gwt-ButtonPushBotton");
				}
			});
			User.addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					if (Window
							.confirm("Are you sure to remove the user from the list")) {
					ButtonGroupMio BGM = ((ButtonGroupMio) event
								.getSource());

//					//TODO CESAR
//						bookReaderServiceHolder.removeUserAndGroupRelation(BGM
//								.getUsuario().getId(),
//						// bookReaderServiceHolder
//						// .removeUserAndGroupRelation(
//						// result.getId(),
//								Mygroup.getId(), new AsyncCallback<Void>() {
//
//									public void onFailure(Throwable caught) {
//										Window.alert("The user could not be removed from his/her group");
//
//									}
//
//									public void onSuccess(Void result) {
//										refresh();
//									}
//								});
//
					}

				}
			});
		}
		
	}





	public GroupClient getMygroup() {
		return Mygroup;
	}

	public void refresh() {
		Panel_Usuarios.clear();
		
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto(InformationConstants.LOADING);
		bookReaderServiceHolder.loadGroupById(Mygroup.getId(),
				new AsyncCallback<GroupClient>() {

					public void onSuccess(GroupClient result) {
						Mygroup=result;
						LoadingPanel.getInstance().hide();
						refrescaElGrupo();

					}

					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert(ErrorConstants.ERROR_REFRESH_GROUP + Mygroup.getName());

					}
				});
	}
}
