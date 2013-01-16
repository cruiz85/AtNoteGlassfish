package lector.server.capcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lector.client.book.reader.CaptchaService;

import nl.captcha.Captcha;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class CaptchaServiceImpl extends RemoteServiceServlet implements CaptchaService {

	public boolean performSignup(String userCaptcha) {
		
		HttpServletRequest request = getThreadLocalRequest();
		
		HttpSession session = request.getSession();

		Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);
		
		return captcha.isCorrect(userCaptcha);
		
	}
}
