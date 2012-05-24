package uib.info323.twitterAWSM.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
	
	public static Date parseDate(String date) throws ParseException {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.parse(date);
	}

}
