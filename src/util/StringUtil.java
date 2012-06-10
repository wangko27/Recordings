
package util;

import exception.RecordingException;

public class StringUtil {
	
	public static boolean hasValue( String s )
	{
		boolean hasValue = false;
		
		if( s != null && !s.equals("") )
			hasValue = true;
		
		return hasValue;
	}
	
	public static int stringToInt( String s ) throws RecordingException
	{
		int i = 0;
		try
		{
			i = Integer.parseInt(s);
		}
		catch (Exception e)
		{
			throw new RecordingException(e);
		}
		
		return i;
	}
	
	public static int getNumOfOccurances( String s, String match )
	{
		int numOfOccurances = 0;
		
		if( hasValue(s) && hasValue(match) )
		{
			String regEx = "[^" + match + "]";
			numOfOccurances = s.replaceAll(regEx, "").length();
		}
		
		return numOfOccurances;
	}
}
