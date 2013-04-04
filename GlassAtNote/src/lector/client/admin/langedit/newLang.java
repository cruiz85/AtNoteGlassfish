package lector.client.admin.langedit;

import java.util.List;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.ConstantsError;
import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;
import lector.share.model.Language;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

public class newLang extends PopupPanel {

	
	private static String INSERT_NAME_NEW_LANGUAGE_LABEL = "Insert the name for the new Language";
	
	private PopupPanel Me;
	private NewAdminLangs Father;
	private TextBox textBox;
	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	private Language LSave;
	
	public newLang(NewAdminLangs Fatherin) {
		super(true);
		this.Father=Fatherin;
		Me=this;
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setSpacing(3);
		setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		Label label = new Label(newLang.INSERT_NAME_NEW_LANGUAGE_LABEL );
		verticalPanel.add(label);
		label.setSize("100%", "100%");
		
		textBox = new TextBox();
		verticalPanel.add(textBox);
		textBox.setWidth("98%");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setSpacing(4);
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setWidth("100%");
		
		Button btnNewButton = new Button("Create");
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
		btnNewButton.setStyleName("gwt-ButtonCenter");
		btnNewButton.addClickHandler(new ClickHandler() {
			private String S;

			public void onClick(ClickEvent event) {
				S=textBox.getText();
				if (S.isEmpty()|| S.length()<2) 
					Window.alert("The Name can be more lenght or equal than two");
				else {	
					
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto("Saving...");
					bookReaderServiceHolder.getLanguagesNames(new AsyncCallback<List<String>>() {
						
						public void onSuccess(List<String> result) {
							LoadingPanel.getInstance().hide();
							
							Language L=new Language(S);
							
							
							List<String> LL =result;
							boolean encontrado=false;
							for (String LLL : LL) {
								encontrado=LLL.equals(S);
								if (encontrado) break;
							}
							
							
							if (!encontrado) 
								{
								LoadingPanel.getInstance().center();
								LoadingPanel.getInstance().setLabelTexto("Saving...");
								LSave=L;
								bookReaderServiceHolder.saveLanguage(L, new AsyncCallback<Void>(){
								

								public void onFailure(Throwable caught) {
									LoadingPanel.getInstance().hide();
									Window.alert(ConstantsError.ERROR_SAVING_LANGUAGE);
									Logger.GetLogger().severe(this.getClass().getName(), 
											ActualState.getUser().toString(),
											ConstantsError.ERROR_RETRIVING_LANGUAGES);
									
								}

								public void onSuccess(Void result) {
									Logger.GetLogger().info(this.getClass().getName(), 
											ActualState.getUser().toString(),
											"Create a language named : " + LSave.getName());
									LoadingPanel.getInstance().hide();
									Father.refresh();
									Me.hide();
									
								}
								
							});
								}
							else{ 
								Window.alert("This Language exist previusly");
							LoadingPanel.getInstance().hide();
							}
							
							
						}
						
						public void onFailure(Throwable caught) {
							LoadingPanel.getInstance().hide();
							Window.alert(ConstantsError.ERROR_RETRIVING_LANGUAGES);
							Logger.GetLogger().severe(this.getClass().getName(), 
									ActualState.getUser().toString(),
									ConstantsError.ERROR_RETRIVING_LANGUAGES);
							
						}
					});
					
			}
				}
				
			
		});
		horizontalPanel.add(btnNewButton);
		
		Button btnNewButton_1 = new Button("Cancel");
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
		btnNewButton_1.setStyleName("gwt-ButtonCenter");
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Me.hide();
			}
		});
		horizontalPanel.add(btnNewButton_1);
		super.setGlassEnabled(true);
	}

}
