package lector.client.login.bookselec;

import lector.share.model.ReadingActivity;
import lector.share.model.client.ReadingActivityClient;

import com.google.gwt.user.client.ui.Button;

public class ButtonActivityReader extends Button {
private ReadingActivityClient RA;


public ButtonActivityReader(ReadingActivityClient Rain) {
	super(Rain.getName());
	this.RA=Rain;
	
}

public void setRA(ReadingActivityClient rA) {
	RA = rA;
}

public ReadingActivityClient getRA() {
	return RA;
}
}
