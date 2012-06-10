package logging;

import java.util.Calendar;

import org.apache.log4j.Logger;

public class RecordingsLogger 
{
	private static RecordingsLogger instance = new RecordingsLogger(); 
	
	public static void setInstance(RecordingsLogger logger)
	{
		instance = logger; 
	}
	
	public static RecordingsLogger getInstance()
	{
		if(instance == null)
			instance = new RecordingsLogger(); 
		
		return instance; 
	}
	
	private Logger getLogger()
	{
		return Logger.getRootLogger(); 
	}
	
	public void logInfo(Object message, String user)
	{
		if(user == null) 
			getLogger().info(Calendar.getInstance().getTime().toString() + " --- " +  message);
		else
			getLogger().info(Calendar.getInstance().getTime().toString() + " --- " + user +  " --- "  +  message);
	}
	
	public void logWarning(Object message)
	{
		getLogger().trace(message); 
	}
	
	public void logError(Object message)
	{
		getLogger().error(Calendar.getInstance().getTime().toString() + " --- " + message);
	}
	
	public void logDebug(Object message)
	{
		getLogger().debug(message); 
	}
	
	public static void info(Object message)
	{
		 getInstance().logInfo(message, null); 
	}
	
	public static void info(Object message, String user)
	{
		getInstance().logInfo(message, user); 
	}
	
	public static void warning(Object message)
	{
		getInstance().logWarning(message); 
	}
	
	public static void error(Object message)
	{
		getInstance().logError(message);  
	}
	
	public static void debug(Object message)
	{
		getInstance().logDebug(message); 
	}
}
