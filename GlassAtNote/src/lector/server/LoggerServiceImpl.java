package lector.server;


import lector.client.book.reader.LoggerService;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class LoggerServiceImpl extends RemoteServiceServlet implements
		LoggerService {

	private static LoggerServlet Loger;
	
	public void severe(String className, String text) {
		getLogger().severe(className, text);
	}

	public void warning(String className, String text) {
		getLogger().warning(className, text);

	}

	public void info(String className, String text) {
		getLogger().info(className, text);
	}

	public void finest(String className, String text) {
		getLogger().finest(className, text);

	}
	
	private LoggerServlet getLogger()
	{
		if (Loger==null) Loger=new LoggerServlet();
		return Loger;
	}

}
