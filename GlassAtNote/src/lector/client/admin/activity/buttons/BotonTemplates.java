package lector.client.admin.activity.buttons;

import lector.share.model.Language;
import lector.share.model.Template;
import lector.share.model.client.TemplateClient;

import com.google.gwt.user.client.ui.Button;

public class BotonTemplates extends Button {
 
	
	private TemplateClient template;
	public BotonTemplates(TemplateClient templatein) {
		super(templatein.getName());
		template=templatein;
		
	}
	
public TemplateClient getTemplate() {
	return template;
}

public void setTemplate(TemplateClient template) {
	this.template = template;
}


}
