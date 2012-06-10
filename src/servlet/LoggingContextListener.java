package servlet;

import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import logging.RecordingsLogger;

public class LoggingContextListener implements ServletContextListener
{

	@Override
	public void contextDestroyed(ServletContextEvent event) 
	{
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event)
	{
		ServletContext context = event.getServletContext(); 
		System.setProperty("rootPath", context.getRealPath("/")); 
		System.out.println(context.getRealPath("/")); 
		
		Calendar instance = Calendar.getInstance(); 
		System.setProperty("filePath", instance.get(Calendar.MONTH) + "_" + instance.get(Calendar.DAY_OF_MONTH) + "_" + instance.get(Calendar.YEAR) + ".log"); 
	}

	
}
