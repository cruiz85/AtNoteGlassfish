package lector.client.admin.group;

import java.util.List;

import lector.client.admin.users.EntidadUser;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.share.model.client.StudentClient;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;

public class NewUser2group extends PopupPanel {

	private GroupAndUserPanel GAUP;
//	private StackPanelMio stackPanel_1;
	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	
	public NewUser2group(GroupAndUserPanel GAUPin) {
		super(true);
		setGlassEnabled(true);
		GAUP=GAUPin;
		
		ScrollPanel scrollPanel = new ScrollPanel();
		setWidget(scrollPanel);
		scrollPanel.setSize("443px", "411px");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		scrollPanel.setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		MenuBar menuBar = new MenuBar(false);
		verticalPanel.add(menuBar);
		
		MenuItem mntmClose = new MenuItem("Close", false, new Command() {
			public void execute() {
				hide();
			}
		});
		menuBar.addItem(mntmClose);
		
				
		
		
		
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
List<StudentClient> result=GAUP.getMygroup().getRemainingStudents();
				if (result.size()<10)
				{
					Long IDi=0l;
					for (StudentClient User1 : result) {
						EntidadUser E=new EntidadUser(User1.getEmail());
//						stackPanel_1.addBotonLessTen(E);
						IDi++;
					}
					
				}else			
				{
					Long IDi=0l;
					for (StudentClient User1 : result)  {
						EntidadUser E=new EntidadUser(User1.getEmail());
//						stackPanel_1.addBoton(E);
						IDi++;
					}
				}
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
