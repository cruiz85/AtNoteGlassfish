package lector.client.admin.catalog;

import lector.client.admin.tagstypes.EditorTagsAndTypes;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Controlador;
import lector.client.controler.ConstantsError;

import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;
import lector.client.controler.ConstantsInformation;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
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

public class CatalogSeleccionMenuPopupPanel extends PopupPanel {

	private static String CHANGE_VISIBILITYBUTTON = "Change Visibility";
	private static String EDITBUTTON = "Edit";
	private static String SELECTBUTTON = "Select";
	private static String DELETEBUTTON = "Delete";
	
	private static final String CHANGE_VISIBILITYBUTTON_RESET = "Change Visibility";
	private static final String EDITBUTTON_RESET = "Edit";
	private static final String SELECTBUTTON_RESET = "Select";
	private static final String DELETEBUTTON_RESET = "Delete";
	
	private Button SelectButton;
	private Button Deletebutton;
	private Button EditButton;
	private Button ChangeVisivilityButton;
	
	private TextBox SelectButtonTextBox;
	private TextBox DeletebuttonTextBox;
	private TextBox EditButtonTextBox;
	private TextBox ChangeVisivilityButtonTextBox;
	
	private CatalogButton BLan;
	private CatalogAdmintrationEntryPoint Father;
	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	
	public CatalogSeleccionMenuPopupPanel(CatalogButton BL, CatalogAdmintrationEntryPoint Fatherin) {
		super(true);
		BLan=BL;
		setSize("100%", "100%");
		Father=Fatherin;
		VerticalPanel verticalPanel = new VerticalPanel();
		setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		SelectButton = new Button(SELECTBUTTON);
		SelectButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				BLan.swap();
				hide();
			}
		});
		SelectButton.setSize("100%", "100%");
		
		Deletebutton = new Button(DELETEBUTTON);
		Deletebutton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				if (Window
						.confirm(ConstantsInformation.ARE_YOU_SURE_DELETE_CATALOG
								+ BLan.getCatalog().getCatalogName()))
				{
				hide();
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto(ConstantsInformation.DELETING);
				bookReaderServiceHolder.deleteCatalog(BLan.getCatalog().getId(), new AsyncCallback<Void>() {
					
					public void onSuccess(Void result) {
						Logger.GetLogger().info(this.getClass().getName(),
								ActualState.getUser().toString(),
								"Delete a catalog :" + BLan.getCatalog().getCatalogName() );
						LoadingPanel.getInstance().hide();
						Father.refresh();
					}
					
					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
					Window.alert(ConstantsError.ERROR_DELETING_CATALOG);
					Logger.GetLogger().severe(this.getClass().getName(),
							ActualState.getUser().toString(),
							ConstantsError.ERROR_DELETING_CATALOG);
						
					}
				});
				
				}
			}
		});
		Deletebutton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		Deletebutton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		Deletebutton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		Deletebutton.setStyleName("gwt-ButtonTOP");
		verticalPanel.add(Deletebutton);
		Deletebutton.setSize("100%", "100%");
		
		EditButton = new Button(EDITBUTTON);
		EditButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
				Logger.GetLogger().info(this.getClass().getName(),
						ActualState.getUser().toString(),
						"Usuario: " + ActualState.getUser()
						+ " edit a catalog " + BLan.getCatalog().getCatalogName());
				EditorTagsAndTypes.setCatalogo(BLan.getCatalog());
				Controlador.change2EditorTagsAndTypes();
				
			}
		});
		EditButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		EditButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		EditButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		EditButton.setStyleName("gwt-ButtonTOP");
		verticalPanel.add(EditButton);
		EditButton.setSize("100%", "100%");
		
		ChangeVisivilityButton = new Button(CHANGE_VISIBILITYBUTTON);
		ChangeVisivilityButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
				ChangeVisivilityPopupPanel Nuevo=new ChangeVisivilityPopupPanel(BLan.getCatalog(), Father);
				Nuevo.center();
			}
		});
		
		ChangeVisivilityButton.setStyleName("gwt-ButtonBotton");
		ChangeVisivilityButton.setSize("100%", "100%");
		ChangeVisivilityButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonBotton");
			}
		});
		ChangeVisivilityButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonBottonOver");
			}
		});
		ChangeVisivilityButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPushBotton");
			}
		});
		verticalPanel.add(ChangeVisivilityButton);
	}

}
