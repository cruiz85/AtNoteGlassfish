package lector.client.controler;

import java.util.Calendar;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class CalendarNow {


	public static String GetDateNow()
	{
		Date now = new Date();
		return DateTimeFormat.getFormat("yyyy.MM.dd HH:mm").format(now);
	}
}
