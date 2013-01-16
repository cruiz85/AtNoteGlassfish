package lector.client.book.reader;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("book.reader/captcha")
public interface CaptchaService extends RemoteService {
	
	boolean performSignup(String userCaptcha);
	
}
