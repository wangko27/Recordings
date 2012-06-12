package servlet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.RecentChange;
import domain.RecentChangesManager;
import exception.RecordingException;

public class RecentChangesController extends ServletExtension 
{
	private RecentChangesManager recentChangesManager; 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// for testing dependency injection 
	public RecentChangesController(RecentChangesManager recentChangesManager)
	{
		this.recentChangesManager = recentChangesManager; 
	}
	public RecentChangesController()
	{
		recentChangesManager = new RecentChangesManager(); 
	}
	

	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			loadPage(req, response);
		}
		catch( Exception e )
		{
			this.addErrorMessage(req, new RecordingException(e)); 
			Collection<String> embeddedJs = this.addJQuery("jquery-1.5.2.min.js", null);
			this.setEmbeddedJs(req, embeddedJs);
			setEmbeddedPage(req, "error.jsp");
			
			loadJSP(req, response, "/jsp/pageShell.jsp"); 
		}
		
	}


	private void loadPage(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException
	{
		Collection<RecentChange> objects = recentChangesManager.getMostRecentChanges(5); 		
		
		req.setAttribute("recentchanges", objects); 
		this.loadJSP(req, response, "jsp/recentchanges.jsp"); 	
	}
}
