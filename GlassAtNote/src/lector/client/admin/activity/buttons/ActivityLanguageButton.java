package lector.client.admin.activity.buttons;

import lector.share.model.Language;

import com.google.gwt.user.client.ui.Button;

public class ActivityLanguageButton extends Button {
 
	
	private Language language;
	public ActivityLanguageButton(Language languagein) {
		super(languagein.getName());
		language=languagein;
		
	}
	
public Language getLanguage() {
	return language;
}

public void setLanguage(Language language) {
	this.language = language;
}


}
