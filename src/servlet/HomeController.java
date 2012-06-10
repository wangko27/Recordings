package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.RecordingData;
import data.SimpleEntityData;
import domain.AlbumManager;
import domain.RecordingManager;
import domain.SimpleEntityManager;
import enumeration.Frequency;
import exception.RecordingException;

@WebServlet("/HomeController")
public class HomeController extends ServletExtension 
{
	private static final long serialVersionUID = 1L;
	
	private SimpleEntityManager simpleEntityManager;

	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(req,response);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		this.simpleEntityManager = new SimpleEntityManager(new SimpleEntityData());
		
		try
		{
			sendAlbumsToView(req);
			sendFrequenciesToView(req);
			
			req.setAttribute("venues", simpleEntityManager.getAllVenues());
			req.setAttribute("countries", simpleEntityManager.getAllCountries());
			req.setAttribute("cities", simpleEntityManager.getAllCities());
			req.setAttribute("recordingTypes", simpleEntityManager.getAllRecordingTypes());
			req.setAttribute("qualities", simpleEntityManager.getAllQualities());
		}
		catch( Exception e )
		{
			addErrorMessage(req, new RecordingException(e));
		}
		
		loadPage(req, response);
	}
	
	private void sendAlbumsToView( HttpServletRequest req ) throws Exception
	{
		AlbumManager albumManager = new AlbumManager();
		
		List<String> albums = albumManager.getAllAlbums();
		
		req.setAttribute("albums", albums);
	}
	
	private void sendFrequenciesToView( HttpServletRequest req )
	{
		List<Frequency> frequencies = new ArrayList<Frequency>();
		frequencies.add(Frequency.RARE);
		frequencies.add(Frequency.MEDIUM);
		frequencies.add(Frequency.COMMON);
		req.setAttribute("frequencies", frequencies);
	}
	
	private void loadPage( HttpServletRequest req, HttpServletResponse response ) throws ServletException, IOException
	{
		/* add js include files */ 
		Collection<String> embeddedJs = new ArrayList<String>();
		// must be listed in the order of dependency!
		addJQuery("jquery-1.5.2.min.js", embeddedJs);
		addJQuery("jquery.tablesorter.min.js", embeddedJs);
		addJQuery("home.js", embeddedJs);
		addJQuery("mainfilter.js", embeddedJs);
		
		/* set up embedded css include files */ 
		Collection<String> embeddedCSS = new ArrayList<String>(); 
		addCSS("demo_table.css", embeddedCSS);
		addCSS("mainfilter.css", embeddedCSS);
		addCSS("tabular.css", embeddedCSS);
		addCSS("button.css", embeddedCSS);
		
		/* set up embedded page (home.jsp) and include js/css files */ 
		setEmbeddedPage(req, "home.jsp"); 
		this.setEmbeddedJs(req, embeddedJs); 
		this.setEmbeddedCSS(req, embeddedCSS); 
		
		loadJSP(req, response, "/jsp/pageShell.jsp");
	}
}
