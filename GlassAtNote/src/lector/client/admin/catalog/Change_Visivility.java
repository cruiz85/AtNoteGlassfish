package lector.client.admin.catalog;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.CalendarNow;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;
import lector.share.model.client.CatalogoClient;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.MenuItemSeparator;


public class Change_Visivility extends PopupPanel {

	private CheckBox chckbxNewCheckBox;
	private PopupPanel Yo;
	private NewAdminCatalogs Father;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private CatalogoClient C;


	public Change_Visivility(CatalogoClient Cin,NewAdminCatalogs Fatherin) {
		super(false);
		setModal(true);
		
		if (!Cin.getProfessorId().equals(ActualState.getUser().getId()))
			{
			Window.alert(InformationConstants.CANTPRIVATICE_A_CATALOG_THAT_YOU_DONT_CREATE);
			hide();
			}
		
		Yo=this;
		Father=Fatherin;
		C=Cin;
		FlowPanel flowPanel = new FlowPanel();
		setWidget(flowPanel);
		flowPanel.setSize("100%", "100%");
		
		MenuBar menuBar = new MenuBar(false);
		flowPanel.add(menuBar);
		
		MenuItem mntmNewItem = new MenuItem(C.getCatalogName(), true,(Command)null);
		mntmNewItem.setEnabled(false);
		menuBar.addItem(mntmNewItem);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		
		MenuItem mntmCancel = new MenuItem("Cancel", true, new Command() {
			public void execute() {
				Yo.hide();
				
			}
		});
		menuBar.addItem(mntmCancel);
		
		MenuItem mntmSave = new MenuItem("Save", true, new Command() {
			public void execute() {
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto("Loading...");
				C.setIsPrivate(chckbxNewCheckBox.getValue());
				Logger.GetLogger().info(
						this.getClass().getName(),
						ActualState.getUser().toString(),
						"Change visivility of catalog " + C.getCatalogName() + " to " + C.getIsPrivate());
				bookReaderServiceHolder.saveCatalog(C, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						Father.refresh();
						LoadingPanel.getInstance().hide();
						Yo.hide();
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(ErrorConstants.ERROR_CHANGE_VISIVILITY_OF_CATALOG);
						Logger.GetLogger().severe(this.getClass().getName(),
						ActualState.getUser().toString(),
						ErrorConstants.ERROR_CHANGE_VISIVILITY_OF_CATALOG);
						LoadingPanel.getInstance().hide();
						
					}
				});
			}
		});
		menuBar.addItem(mntmSave);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flowPanel.add(verticalPanel);
		verticalPanel.setSize("199px", "116px");
		
		chckbxNewCheckBox = new CheckBox("New check box");
		chckbxNewCheckBox.setHTML("Private");
		verticalPanel.add(chckbxNewCheckBox);
	}

}
