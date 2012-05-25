package uib.info323.twitterAWSM.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
	
	/**
	 * A convenience method to parse a twitter-api
	 * request date string to a java.util.Date object.
	 * @param date
	 * @return The Date object
	 * @throws ParseException
	 */
	public static String parseRequestDate(Date date) throws ParseException {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}
	
	/**
	 * A convenience method to parse a twitter-api
	 * request date string to a java.util.Date object.
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date parseResponseDate(String date) throws ParseException {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return dateFormat.parse(date);
	}
}
