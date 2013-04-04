package lector.client.admin.langedit;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Controlador;
import lector.client.controler.ConstantsError;
import lector.client.controler.ConstantsInformation;
import lector.client.logger.Logger;

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

public class SeleccionMenu extends PopupPanel {
	
	public static final String SELECTION_MENU_NAME="Selection Language Popup Menu";
	
	private static String SELECT = "Select";
	private static String DELETE = "Delete";
	private static String EDIT = "Edit";
	
	private Button DeleteButton;
	private Button EditButton;
	private Button SelectButton;
	
	private BottonLang BotonLangPressed;
	private NewAdminLangs FatherNewAdminLangs;

	
	
	
	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	
	public SeleccionMenu(BottonLang BL, NewAdminLangs Fatherin) {
		super(true);
		BotonLangPressed=BL;
		setSize("100%", "100%");
		FatherNewAdminLangs=Fatherin;
		VerticalPanel verticalPanel = new VerticalPanel();
		setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		DeleteButton = new Button(SeleccionMenu.DELETE );
		DeleteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (Window
						.confirm(ConstantsInformation.ARE_YOU_SURE_DELETE_LANGUAGE + BotonLangPressed.getLanguage().getName()))
				{
				bookReaderServiceHolder.deleteLanguage(BotonLangPressed.getLanguage().getId(), new AsyncCallback<Void>() {
					
					public void onSuccess(Void result) {
						Logger.GetLogger().info(this.getClass().getName(),
								ActualState.getUser().toString(),
								"Delete a language " + BotonLangPressed.getLanguage().getName());
						FatherNewAdminLangs.refresh();
						hide();
						
					}
					
					public void onFailure(Throwable caught) {
						Window.alert(ConstantsError.ERROR_DELETING_LANGUAGE);
						Logger.GetLogger().severe(this.getClass().getName(),
								ActualState.getUser().toString(),
								ConstantsError.ERROR_DELETING_LANGUAGE);
						
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
		verticalPanel.add(DeleteButton);
		DeleteButton.setSize("100%", "100%");
		
		SelectButton = new Button(SeleccionMenu.SELECT);
		SelectButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonPush");
			}
		});
		SelectButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOP");
			}
		});
		SelectButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonTOPOver");
			}
		});
		SelectButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.alert(ConstantsInformation.CHANGE_LANGUAGE_WARNING);
						ActualState.setActualLanguage(BotonLangPressed.getLanguage());
				hide();
			}
		});
		SelectButton.setStyleName("gwt-ButtonTOP");
		verticalPanel.add(SelectButton);
		SelectButton.setSize("100%", "100%");
		
		EditButton = new Button(SeleccionMenu.EDIT);
		EditButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Logger.GetLogger().info(this.getClass().getName(),
						ActualState.getUser().toString(),
						"Start edit a language " + BotonLangPressed.getLanguage().getName());
				EditordeLenguajes.setLenguajeActual(BotonLangPressed.getLanguage());
				Controlador.change2EditorLenguaje();
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
		
		verticalPanel.add(EditButton);
		EditButton.setSize("100%", "100%");
	}

}
