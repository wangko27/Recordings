package util;
import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;


public class HTMLSanitizer 
{
	
	public static String sanitize(String html)   
	{
		if(html == null) return ""; 
		
		return escapeHtml(html);
	}
}
