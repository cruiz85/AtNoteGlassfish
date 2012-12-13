package lector.client.admin.activity;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.reader.LoadingPanel;
import lector.share.model.client.ReadingActivityClient;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

public class ReadingActivityVisibilityChangePanel extends PopupPanel {
	

	private static String USE_CHECK_BOX_BELOW_TO_SET_PRIVICY_TO_YOUR_ACTIVITY= "Use check box below to set the privacy of your activity";
	private static String PRIVATE_CHECK_BOX = "Private";
	private static String ACCEPT = "ACCEPT";
	private static String CANCEL = "CANCEL";
	
	private static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private ReadingActivityClient readingActivityClient;
	private NewAdminActivities father;
	private CheckBox chckbxNewCheckBox;

	public ReadingActivityVisibilityChangePanel(ReadingActivityClient readingActivityClientin, NewAdminActivities fatherin) {
		setGlassEnabled(true);
		setAnimationEnabled(true);
		readingActivityClient=readingActivityClientin;
		father=fatherin;
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setSpacing(9);
		setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		Label lblNewLabel = new Label(ReadingActivityVisibilityChangePanel.USE_CHECK_BOX_BELOW_TO_SET_PRIVICY_TO_YOUR_ACTIVITY);
		verticalPanel.add(lblNewLabel);
		
		chckbxNewCheckBox = new CheckBox(ReadingActivityVisibilityChangePanel.PRIVATE_CHECK_BOX );
		verticalPanel.add(chckbxNewCheckBox);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setSpacing(5);
		verticalPanel.add(horizontalPanel);
		
		Button btnNewButton_1 = new Button(ReadingActivityVisibilityChangePanel.ACCEPT);
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				readingActivityClient.setPrivacy(chckbxNewCheckBox.getValue());
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto(LoadingPanel.SAVING);
				bookReaderServiceHolder.saveReadingActivity(readingActivityClient, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						LoadingPanel.getInstance().hide();
						hide();
						father.refresh();
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert(ErrorConstants.ERROR_SAVING_ACTIVITY);
					}
				});
			}
		});
		horizontalPanel.add(btnNewButton_1);
		
		
		btnNewButton_1.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});

		btnNewButton_1.addMouseOutHandler(new MouseOutHandler() {
	public void onMouseOut(MouseOutEvent event) {
		((Button) event.getSource())
				.setStyleName("gwt-ButtonCenter");
	}
});

		btnNewButton_1
		.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");

			}
		});

		btnNewButton_1.setStyleName("gwt-ButtonCenter");

		Button btnNewButton = new Button(ReadingActivityVisibilityChangePanel.CANCEL);
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		
		btnNewButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});

btnNewButton.addMouseOutHandler(new MouseOutHandler() {
	public void onMouseOut(MouseOutEvent event) {
		((Button) event.getSource())
				.setStyleName("gwt-ButtonCenter");
	}
});

btnNewButton
		.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");

			}
		});

btnNewButton.setStyleName("gwt-ButtonCenter");
		horizontalPanel.add(btnNewButton);
	}

}
