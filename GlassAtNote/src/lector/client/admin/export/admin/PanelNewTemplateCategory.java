package lector.client.admin.export.admin;

import java.util.ArrayList;

import lector.client.book.reader.ExportService;
import lector.client.book.reader.ExportServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.ConstantsError;
import lector.client.controler.ConstantsInformation;
import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;
import lector.share.model.client.TemplateCategoryClient;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
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

public class PanelNewTemplateCategory extends PopupPanel {

	private static String SAVE = "Save";
	private static String CLOSE = "Close";
	private ExportServiceAsync exportServiceHolder = GWT
			.create(ExportService.class);
	private TemplateCategoryClient TC;
	private TextBox textBox;
	private EditTemplate PadreLLamada;
	private PanelNewTemplateCategory Yo;
	
	public PanelNewTemplateCategory(TemplateCategoryClient tC, EditTemplate yO) {
		
		TC=tC;
		PadreLLamada=yO;
		Yo=this;
		setGlassEnabled(true);
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setSpacing(1);
		setWidget(verticalPanel);
		verticalPanel.setSize("340px", "155px");
		
		Label lblNewLabel = new Label(ConstantsInformation.CREATE_NEW_TEMPLATECATEGORY + TC.getName());
		verticalPanel.add(lblNewLabel);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel);
		
		Label lblNewLabel_1 = new Label(ConstantsInformation.NAME);
		horizontalPanel.add(lblNewLabel_1);
		
		textBox = new TextBox();
		horizontalPanel.add(textBox);
		
		HorizontalPanel Botonera = new HorizontalPanel();
		Botonera.setSpacing(5);
		verticalPanel.add(Botonera);
		
		Button btnNewButton = new Button(CLOSE);
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		btnNewButton.setStyleName("gwt-ButtonCenter");
		btnNewButton.setSize("100%", "100%");

		btnNewButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		
		btnNewButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
		});
		
		btnNewButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			
		}
	});
		
		Botonera.add(btnNewButton);
		
		Button btnNewButton_1 = new Button(SAVE);
		btnNewButton_1.addClickHandler(new ClickHandler() {
			private TemplateCategoryClient T;

			public void onClick(ClickEvent event) {
				if (!textBox.getText().isEmpty()){
					if (TC.getId()==Constants.TEMPLATEID)
						T=new TemplateCategoryClient(textBox.getText(), new ArrayList<TemplateCategoryClient>(), new ArrayList<Long>(), null, TC.getTemplate());
					else T=new TemplateCategoryClient(textBox.getText(), new ArrayList<TemplateCategoryClient>(), new ArrayList<Long>(), TC, TC.getTemplate());
					LoadingPanel.getInstance().center();
					LoadingPanel.getInstance().setLabelTexto(ConstantsInformation.SAVING);
					exportServiceHolder.saveTemplateCategory(T , new AsyncCallback<Void>() {
						
						public void onSuccess(Void result) {
							Logger.GetLogger().info(this.getClass().getName(),
									ActualState.getUser().toString(),
									"Create a Template Category named " + T.getName());
							LoadingPanel.getInstance().hide();
							PadreLLamada.refresh();
							hide();
							
						}
						
						public void onFailure(Throwable caught) {
							LoadingPanel.getInstance().hide();
							Window.alert(ConstantsError.ERROR_SAVING_NEW_TEMPLATE_CATEGORY);
							Logger.GetLogger()
							.severe(Yo.getClass().toString(),
									ActualState.getUser().toString(),
									ConstantsError.ERROR_SAVING_NEW_TEMPLATE_CATEGORY);
							
						}
					});
				}
			}
		});
		Botonera.add(btnNewButton_1);
		TC=tC;
	
	
	btnNewButton_1.setStyleName("gwt-ButtonCenter");
	btnNewButton_1.setSize("100%", "100%");

	btnNewButton_1.addMouseDownHandler(new MouseDownHandler() {
		public void onMouseDown(MouseDownEvent event) {
			((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
		}
	});
	
	btnNewButton_1.addMouseOutHandler(new MouseOutHandler() {
		public void onMouseOut(MouseOutEvent event) {
			((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
	}
	});
	
	btnNewButton_1.addMouseOverHandler(new MouseOverHandler() {
		public void onMouseOver(MouseOverEvent event) {
			
			((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
		
	}
});
	
	}
}
