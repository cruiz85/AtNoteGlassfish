package lector.client.admin.activity;

import lector.share.model.ReadingActivity;
import lector.share.model.client.ReadingActivityClient;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BottonActivity extends Button {

	private ReadingActivityClient readingActivity;
	private VerticalPanel Actual;
	private VerticalPanel Normal;
	private VerticalPanel Selected;

	public BottonActivity(VerticalPanel Normalin, VerticalPanel Selectedin,
			ReadingActivityClient readingActivityClient) {
		super(readingActivityClient.getName());
		this.readingActivity = readingActivityClient;
		Normal = Normalin;
		Selected = Selectedin;
		Actual = Normal;
		Actual.add(this);
	}

	public ReadingActivityClient getReadingActivity() {
		return readingActivity;
	}

	public void setReadingActivity(ReadingActivityClient readingActivity) {
		this.readingActivity = readingActivity;
	}

	public VerticalPanel getSelected() {
		return Selected;
	}

	public void setSelected(VerticalPanel selected) {
		Selected = selected;
	}

	public void swap() {
		if (Actual == Normal) {
			Actual.remove(this);
			Actual = Selected;
			Selected.add(this);
		} else if (Actual == Selected) {
			Selected.remove(this);
			Actual = Normal;
			Normal.add(this);
		}
	}

}
