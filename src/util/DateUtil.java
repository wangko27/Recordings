package util;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil 
{
	public static String getTwoDigitNum( Integer num )
	{
		String twoDigitNum = "";
		
		if( num == null )
			twoDigitNum = "?";
		else if( num >= 10 )
			twoDigitNum = num.toString();
		else
			twoDigitNum = "0" + num.toString();
		
		return twoDigitNum;
	}
	
	public static String getAbbreviatedYear( Integer year )
	{
		String abbreviatedYear = "";
		
		SimpleDateFormat format = new SimpleDateFormat("yy");
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		
		abbreviatedYear = format.format(c.getTime());
		
		return abbreviatedYear;
	}
	
	public static String getMonthNameFromMonthNum( int monthNum )
	{
		return new DateFormatSymbols().getMonths()[monthNum-1]; 
	}
	
	public static String getSupplementedDateValue( int date )
	{
		String supplementedDateValue = "";
		
		if( date > 10 && date < 20 )
			supplementedDateValue = date + "th";
		else if( date == 1 || date == 21 || date == 31 )
			supplementedDateValue = date + "st";
		else if( date == 2 || date == 22 || date == 32 )
			supplementedDateValue = date + "nd";
		else if( date == 3 || date == 23 )
			supplementedDateValue = date + "rd";
		else
			supplementedDateValue = date + "th";
		
		
		return supplementedDateValue;
	}
	
	public static boolean validDate( Integer date )
	{
		boolean valid = false;
		
		if( date != null && date >= 1 && date <= 32 )
			valid = true;
		
		return valid;
	}
	
	public static boolean validMonth( Integer month )
	{
		boolean valid = false;
		
		if( month != null && month >= 1 && month <= 12 )
			valid = true;
		
		return valid;
	}
	
	public static boolean validYear( Integer year )
	{
		boolean valid = false;
		
		if( year != null && year >= 1950 && year <= 2100 )
			valid = true;
		
		return valid;
	}
}
