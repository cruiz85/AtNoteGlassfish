package lector.client.logger;

import lector.client.book.reader.LoggerService;
import lector.client.book.reader.LoggerServiceAsync;
import lector.client.controler.CalendarNow;
import lector.client.controler.ConstantsError;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Logger {

	private static String PREFIX_CONSTANT_LOG= "@NOTE LOG : " ;
	private static String PREFIX_CONSTANT_ERROR= "@NOTE ERROR : " ;
	private static String AT = " AT ";
	private static String CAUSE_BY="CAUSE ";
	private static String FROM_USER = " FROM USER ";		
	private static String LOGS_ERROR="Error recording the log";
	
	
	private static AsyncCallback<Void> callback= new AsyncCallback<Void>() {

		public void onFailure(Throwable caught) {
			callbackfailture();
			
		}

		public void onSuccess(Void result) {

			
		}
	};
	
	private static Logger Log;
	
	static LoggerServiceAsync loggerServiceHolder = GWT
	.create(LoggerService.class);
	
	
	public void severe(String className, String User, String cause) {
		loggerServiceHolder.severe(PREFIX_CONSTANT_ERROR + className, PREFIX_CONSTANT_ERROR + CAUSE_BY + cause + FROM_USER + User + AT + GetDateNow(), callback);

	}

	public void warning(String className, String text) {
		loggerServiceHolder.warning(className, text, callback);

	}

	
	public void info(String className, String User, String cause,AsyncCallback<Void> callbackin) {
		loggerServiceHolder.info(PREFIX_CONSTANT_LOG+className,PREFIX_CONSTANT_LOG + CAUSE_BY + cause + FROM_USER + User + AT + GetDateNow(), callbackin);

	}
	
	public void info(String className, String User, String cause) {
		loggerServiceHolder.info(PREFIX_CONSTANT_LOG+className,PREFIX_CONSTANT_LOG + CAUSE_BY + cause + FROM_USER + User + AT + GetDateNow(), callback);

	}

	public void finest(String className, String text) {
		loggerServiceHolder.finest(className, text, callback);

	}
	
	
	public static void callbackfailture()
	{
		Window.alert(Logger.LOGS_ERROR);
	}
	
	
	public static String GetDateNow()
	{
		return CalendarNow.GetDateNow();
	}
	public static Logger GetLogger(){
		//Window.alert("GetLogger");
		if (Log==null) Log=new Logger();
		return Log;
	}
}
