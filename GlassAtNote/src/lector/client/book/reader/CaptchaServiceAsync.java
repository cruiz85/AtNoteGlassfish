package lector.client.book.reader;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CaptchaServiceAsync {
	
	void performSignup(String userCaptcha, AsyncCallback<Boolean> callback);
	
}
