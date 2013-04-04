package lector.client.login.activitysel;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.ConstantsError;
import lector.client.controler.ConstantsInformation;
import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.LongBox;

public class AddGroupPanel extends PopupPanel {

	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private LongBox textBox;
	private AddGroupPanel Yo;

	public AddGroupPanel() {
		Yo = this;
		setGlassEnabled(true);
		setAnimationEnabled(true);
		setAutoHideEnabled(true);

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setSpacing(9);
		setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");

		Label lblNewLabel = new Label(
				ConstantsInformation.INSERT_ID_OF_THE_GROUP);
		verticalPanel.add(lblNewLabel);

		textBox = new LongBox();
		verticalPanel.add(textBox);

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);

		Button btnNewButton = new Button("Add");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Long I = textBox.getValue();
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto(
						ConstantsInformation.ADDING_TO_GROUP);
				bookReaderServiceHolder.addStudentToBeValidated(ActualState
						.getUser().getId(), I, new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						LoadingPanel.getInstance().hide();
						Window.alert(ConstantsInformation.YOUR_ADD_WAS_CORRECT_WAIT_FOR_TEACHER);
						Logger.GetLogger().info(
								Yo.getClass().toString(),
								ActualState.getUser().toString(),
								"User"+ ActualState.getUser().toString() + " add to Group" + textBox.getValue());
						hide();

					}

					@Override
					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert(ConstantsError.ERROR_IN_ADDITION_TO_A_GROUP);
						Logger.GetLogger().severe(
								Yo.getClass().toString(),
								ActualState.getUser().toString(),
								ConstantsError.ERROR_IN_ADDITION_TO_A_GROUP
										+ textBox.getValue());
						hide();
					}
				});
			}
		});
		horizontalPanel.add(btnNewButton);
		btnNewButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});

		btnNewButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});

		btnNewButton.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");

			}
		});

		btnNewButton.setStyleName("gwt-ButtonCenter");
		Button Cancel = new Button("Cancel");
		Cancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		Cancel.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});

		Cancel.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});

		Cancel.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");

			}
		});

		Cancel.setStyleName("gwt-ButtonCenter");
		horizontalPanel.add(Cancel);

	}

}
