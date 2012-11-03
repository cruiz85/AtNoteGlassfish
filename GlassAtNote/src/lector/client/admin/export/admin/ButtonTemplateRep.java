package lector.client.admin.export.admin;

import lector.share.model.TemplateCategory;
import lector.share.model.client.TemplateCategoryClient;

import com.google.gwt.user.client.ui.Button;

public class ButtonTemplateRep extends Button {

	private RepresentacionTemplateCategory T;
	
	public ButtonTemplateRep(String name, RepresentacionTemplateCategory t) {
		super(name);
		T=t;
	}
	
	public TemplateCategoryClient getT() {
		return T.getT();
	}
	
	public RepresentacionTemplateCategory getTRep() {
		return T;
	}

}
