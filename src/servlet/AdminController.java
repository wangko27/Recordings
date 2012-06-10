package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logging.RecordingsLogger;
import bean.AuthenticationResult;
import domain.security.UserManager;
import exception.RecordingException;

public class AdminController extends ServletExtension
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserManager userManager = new UserManager(); 

	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			if(req.getParameter("logout") != null)
				doLogout(req, response); 
			else				
				doAuthentication(req, response); 
		}
		catch( Exception e )
		{
			addErrorMessage(req, new RecordingException(e));
		}
	}
	
	private void doLogout(HttpServletRequest req, HttpServletResponse response)
	{
		req.getSession().removeAttribute("authenticated"); 
	}

	private void doAuthentication(HttpServletRequest req, HttpServletResponse response) throws IOException
	{
		PrintWriter pw = response.getWriter(); 
		AuthenticationResult result = null; 
		
		if(userManager.userIsAuthenticated("test", req.getParameter("password")))
		{			
			if(req.getSession(false) == null)
			{
				RecordingsLogger.info("session was null"); 
			}
			
			result = new AuthenticationResult("success"); 	
			req.getSession().setAttribute("authenticated", true); 
			debugSession(req.getSession()); 
		}
		else
		{
			result = new AuthenticationResult("failure"); 
		} 
		
		pw.write(userManager.authenticationResultToJSON(result)); 
		pw.close(); 
		
		RecordingsLogger.info("Logged in from " + req.getRemoteAddr(), req.getRemoteAddr()); 
	}	
	
	private void debugSession(HttpSession session)
	{
		String logMessage = "\n**************** session\n"; 
		logMessage += "id: " + session.getId() + "\n";
		logMessage += "creation time: " +  session.getCreationTime() + "\n"; 
		logMessage += "access time: " + session.getLastAccessedTime() + "\n";
		if(session.getServletContext() != null) 
		{
			logMessage += "context name: " + session.getServletContext().getServletContextName() + "\n";
			RecordingsLogger.info("Path: " + this.getServletContext().getRealPath("/"));
		}
		logMessage += "attributes: \n";  		
		Enumeration<String> attributes = session.getAttributeNames(); 
		
		if(attributes == null) return; // probably unit testing 
		
		while(attributes.hasMoreElements())
		{
			String element = attributes.nextElement(); 
			if(!element.equals("recordingList"))
				logMessage += element + " -- " + session.getAttribute(element) + "\n";
			else
				logMessage += "recordingList -- truncated \n"; 
		}
		
		RecordingsLogger.info(logMessage); 
	}
	
	
	

}
