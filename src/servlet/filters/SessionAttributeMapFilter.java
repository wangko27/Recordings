package servlet.filters;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.SAMFacade;
import servlet.ServletExtension;
import util.StringUtil;

public class SessionAttributeMapFilter  extends ServletExtension implements Filter   
{
	private static final long serialVersionUID = 1L;
	
	public static final String SAM_PREFIX = "s";
	public static final String RECORDINGFILTER_PREFIX = "rf";
	public static final String SEPERATOR = "_";

	@Override
	public void doFilter(ServletRequest servletReq, ServletResponse servletRes, FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest req = (HttpServletRequest) servletReq; 
		HttpServletResponse res = (HttpServletResponse) servletRes;
		SAMFacade sam = new SAMFacade();

		Map<String, String[]> params = req.getParameterMap();
	    Iterator<String> i = params.keySet().iterator();
	    
	    while ( i.hasNext() )
        {
	    	String key = (String) i.next();
	    	String value = ((String[]) params.get( key ))[ 0 ];
	    	
	    	if( StringUtil.hasValue(key) && StringUtil.hasValue(value) && isSAM(key) )
	    	{
	    		//System.out.println("KEY: " + key + "    VALUE: " + value);
	    		sam.setParam(req, key, value);
	    	}
        }
			
		chain.doFilter(req, res); 
	}
	
	public static boolean isSAM( String param )
	{
		boolean isSAM = false;
		
		if( StringUtil.hasValue(param) )
		{
			int separators = StringUtil.getNumOfOccurances(param, SEPERATOR);
			String SAMRegex = "s_.+_.+";
			
			if( separators == 2 && param.matches(SAMRegex) )
				isSAM = true;
		}
		
		return isSAM;
	}
	
	public static String getSecondLevelNamespace( String param )
	{
		String secondLevelNamespace = null;
		
		try
		{
			secondLevelNamespace = param.split(SEPERATOR)[2];
		}
		catch( Exception e )
		{
			secondLevelNamespace = null;
		}
		
		return secondLevelNamespace;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException 
	{
		// we have to implement this... 
	}

}
