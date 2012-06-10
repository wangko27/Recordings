package servlet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logging.RecordingsLogger;

import org.mockito.Mockito;

import data.HibernateTest;

public abstract class BasicServletTester extends HibernateTest
{
	protected HttpServletRequest stubRequest; 
	protected HttpServletResponse stubResponse; 
	
	protected HttpSession stubSession; 
	protected RequestDispatcher stubRequestDispatcher; 
	protected PrintWriter stubPrintWriter;
	
	protected RecordingsLogger stubLogger; 
	
	/**
	 * override this method to determine whether database support (via Hibernate) will be enabled for the test class
	 * @return true, if the test database will be used for this test class 
	 */
	protected abstract boolean useDatabase(); 
	
	@Override 
	public void Before() throws IOException
	{
		if(useDatabase())
			super.Before(); 
		
		before(); 
	}
	
	@Override 
	public void After()
	{
		if(useDatabase())
			super.After(); 
		
		after();
	}
	
	@Override 
	public void before()
	{
		stubRequest = mock(HttpServletRequest.class); 
		stubResponse = mock(HttpServletResponse.class); 
		stubSession = mock(HttpSession.class); 
		stubRequestDispatcher = mock(RequestDispatcher.class); 
		stubPrintWriter = mock(PrintWriter.class); 
		stubLogger = mock(RecordingsLogger.class); 
		
		try {
			when(stubResponse.getWriter()).thenReturn(stubPrintWriter);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		when(stubRequest.getSession()).thenReturn(stubSession); 
		when(stubRequest.getRequestDispatcher((String) Mockito.any())).thenReturn(stubRequestDispatcher); 
		
		RecordingsLogger.setInstance(stubLogger); 
		
		postServletSetup(); 
	}
	
	/**
	 * perform additional setup for the test class after the initial setup has been done (for stub request/response/etc.) 
	 */
	public abstract void postServletSetup(); 	
	
}
