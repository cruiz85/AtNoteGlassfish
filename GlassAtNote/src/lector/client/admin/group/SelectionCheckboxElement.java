package lector.client.admin.group;

import lector.share.model.client.StudentClient;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.Label;

public class SelectionCheckboxElement extends Composite {

	private StudentClient UserCheck;
	private SimpleCheckBox CheckBox;
	
	public SelectionCheckboxElement(StudentClient user1) {
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		initWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "");
		UserCheck=user1;
		CheckBox = new SimpleCheckBox();
		horizontalPanel.add(CheckBox);
		
		Label TextoName = new Label("New label");
		horizontalPanel.add(TextoName);
		TextoName.setText(user1.getFirstName()+ " " + user1.getLastName() + " : " + user1.getEmail());
	}

	
	public StudentClient getStudentClient() {
		return UserCheck;
	}
	
	public void setStudentClient(StudentClient userCheck) {
		UserCheck = userCheck;
	}
	
public SimpleCheckBox getCheckBox() {
	return CheckBox;
}

public void setCheckBox(SimpleCheckBox checkBox) {
	CheckBox = checkBox;
}


public void setCheckBoxState(boolean b) {
	CheckBox.setValue(b);
	
}

}
