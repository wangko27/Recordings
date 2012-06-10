package servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import util.StringUtil;

public class SAMFacade {
	
	public static final String SAM_SESSION_NAME = "SAM";
	public static final String PREF_SAM = "sam";
	public static final String PREF_VAL = "val";
	private final String defaultPref = PREF_VAL;
	
	public String determineParam( HttpServletRequest req, String val, String key, String pref )
	{
		return determineParam(req, val, key, pref, null);
	}
	
	public String determineParam( HttpServletRequest req, String val, String key, String pref, String defaultVal )
	{
		String param = "";
		String preference = pref;
		if( !StringUtil.hasValue(pref) )
			preference = defaultPref;
		
		if( preference.equals(PREF_VAL) )
		{
			if( StringUtil.hasValue(val) )
				param = val;
			else
				param = defaultVal;
		}
		else if( preference.equals(PREF_SAM) )
		{
			if( StringUtil.hasValue(key) )
			{
				SAMFacade sam = new SAMFacade();
				param = sam.getParam(req, key);
			}
			else
				param = defaultVal;
		}
		
		if( param == null )
			param = "";
		
		return param;	
	}
	
	public String getParam( HttpServletRequest req, String key )
	{
		String param = null;
		if( req != null && StringUtil.hasValue(key) )
		{
			HttpSession session = req.getSession();
			
			Map<String,String> sam = (Map<String, String>) session.getAttribute(SAM_SESSION_NAME);
			
			if( sam != null )
				param = sam.get(key);
		}
		
		return param;
	}
	
	public void setParam( HttpServletRequest req, String key, String value )
	{
		if( req != null && StringUtil.hasValue(key) && StringUtil.hasValue(value) )
		{
			HttpSession session = req.getSession();
			
			Map<String,String> sam = (Map<String, String>) session.getAttribute(SAM_SESSION_NAME);
			
			if( sam == null )
				sam = new HashMap<String,String>();
			
			sam.put(key, value);
			
			session.setAttribute(SAM_SESSION_NAME, sam);
		}
	}
	
	public static String getParamString( String prefix, String name, String val )
	{
		String paramString = "";
		
		if( StringUtil.hasValue(prefix) && StringUtil.hasValue(name) && StringUtil.hasValue(val) )
			paramString = "s_" + prefix + "_" + name + "=" + val;
		
		return paramString;
	}
}
