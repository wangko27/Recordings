package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.JDBCHelper;
import exception.RecordingException;

@WebServlet("/AboutController")
public class AboutController extends ServletExtension {
	
	private static final long serialVersionUID = 1L;	

	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			sendFacts(req);
			
			Collection<String> embeddedJs = new ArrayList<String>();
			addJQuery("jquery-1.5.2.min.js", embeddedJs);
			
			this.setEmbeddedJs(req, embeddedJs);
			
			setEmbeddedPage(req, "about.jsp"); 
			loadJSP(req, response, "jsp/pageShell.jsp");
		}
		catch( Exception e )
		{
			addErrorMessage(req, new RecordingException(e));
			setEmbeddedPage(req, "home.jsp");
		}
	}
	
	private void sendFacts( HttpServletRequest req )
	{
		sendFact(req, "uniqueSongs", 
				"select count(1)" +
				" from lkpsong");
		
		sendFact(req, "totalCities", 
				"select count(1)" +
				" from lkpcity");
		
		sendFact(req, "totalCountries", 
				"select count(1)" +
				" from lkpcountry");
		
		sendFact(req, "totalVenues", 
				"select count(1)" +
				" from lkpvenue");
		
		sendFact(req, "totalSongInstances", 
				"select count(1)" +
				" from songinstance");
		
		sendFact(req, "totalRecordings", 
				"select count(1)" +
				" from recording");
	}
	
	private void sendFact( HttpServletRequest req, String name, String query )
	{
		req.setAttribute(name, JDBCHelper.scalerQuery(query));
	}
}
