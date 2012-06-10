package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logging.RecordingsLogger;

import util.StringUtil;
import bean.SimpleBean;
import data.SimpleEntityData;
import domain.LookupValuesManager;
import domain.NotUsedManager;
import domain.SimpleEntityManager;
import exception.RecordingException;

@WebServlet("/LookupValuesController")
public class LookupValuesController extends ServletExtension {
	
	private static final long serialVersionUID = 1L;	
	private LookupValuesManager lookupValuesManager = null; 
	private SimpleEntityManager simpleEntityManager = null;
	private NotUsedManager notUsedManager = null; 

	public LookupValuesController()
	{
		lookupValuesManager = new LookupValuesManager(); 
		simpleEntityManager = new SimpleEntityManager(new SimpleEntityData()); 
		notUsedManager = new NotUsedManager(); 
	}
	
	// constructor used for dependency injection for testing 
	public LookupValuesController(LookupValuesManager manager, SimpleEntityManager simpleEntityManager, NotUsedManager notUsedManager)
	{
		lookupValuesManager = manager;
		this.simpleEntityManager = simpleEntityManager;
		this.notUsedManager = notUsedManager; 
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(req,response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			String action = req.getParameter("action");
			
			if( StringUtil.hasValue(action) )
			{
				if( action.equals("edit") )
				{
					updateLookupValue(req);
					sendDefaultValues(req);
					RecordingsLogger.info("Edit " + req.getQueryString(), req.getRemoteAddr()); 
				}
				else if( action.equals("merge") )
				{
					mergeLookupValues(req);
					sendDefaultValues(req);
					RecordingsLogger.info("Merge " + req.getQueryString(), req.getRemoteAddr()); 
				}
				else if( action.equals("notuseddelete") )
					deleteSimpleValue(req);
			}
		}
		catch( Exception e )
		{
			addErrorMessage(req, new RecordingException(e));
		}
		
		loadPage(req, response);
	}
	
	private void sendDefaultValues( HttpServletRequest req ) 
	{
		String category = req.getParameter("category");
		String mergeId = req.getParameter("mergeId"); // TODO: code smell
		
		req.setAttribute("category", category);
		req.setAttribute("mergeId", mergeId);
	}
	 
	private void updateLookupValue( HttpServletRequest req ) throws RecordingException
	{
		String category = req.getParameter("category");
		String originalId = req.getParameter("originalId");
		String updatedValue = req.getParameter("updatedValue");
		
		if( StringUtil.hasValue(category) && StringUtil.hasValue(originalId) && StringUtil.hasValue(updatedValue) )
			lookupValuesManager.updateLookupValue(category, originalId, updatedValue);
	}
	
	private void mergeLookupValues( HttpServletRequest req ) throws Exception
	{
		String category = req.getParameter("category");
		String originalId = req.getParameter("originalId");
		String mergeId = req.getParameter("mergeId");
		
		try
		{
			if( StringUtil.hasValue(category) && StringUtil.hasValue(originalId) && StringUtil.hasValue(mergeId) )
				lookupValuesManager.mergeLookupValues(category, originalId, mergeId);
		} 
		catch( Exception e )
		{
			throw new Exception(e);
		}
	}
	
	private void sendLookupValues( HttpServletRequest req )
	{		
		List<String> category = new ArrayList<String>();
		category.add("Song");
		category.add("Venue");
		category.add("City");
		category.add("Country");
		category.add("Format");
		category.add("Media");
		category.add("Quality");
		category.add("RecordingType");
		
		req.setAttribute("categories", category);

		List<List<SimpleBean>> s = new ArrayList<List<SimpleBean>>();
		s.add((List) simpleEntityManager.getAllSongs());
		s.add((List) simpleEntityManager.getAllVenues());
		s.add((List) simpleEntityManager.getAllCities());
		s.add((List) simpleEntityManager.getAllCountries());
		s.add((List) simpleEntityManager.getAllFormats());
		s.add((List) simpleEntityManager.getAllMedias());
		s.add((List) simpleEntityManager.getAllQualities());
		s.add((List) simpleEntityManager.getAllRecordingTypes());
		
		req.setAttribute("lookupValues", s);
	}
	
	private void loadPage( HttpServletRequest req, HttpServletResponse response ) throws ServletException, IOException
	{
		sendLookupValues(req);
		
		req.setAttribute("notUsedSimpleValues", notUsedManager.getAllNotUsed());
		
		Collection<String> embeddedCSS = new ArrayList<String>(); 
		//addCSS("individualrecordingstyle.css", embeddedCSS);
		
		Collection<String> embeddedJs = new ArrayList<String>();
		addJQuery("jquery-1.5.2.min.js", embeddedJs);
		addJQuery("lookupValues.js", embeddedJs);
		
		this.setEmbeddedJs(req, embeddedJs); 
		this.setEmbeddedCSS(req, embeddedCSS); 
		
		setEmbeddedPage(req, "lookupValues.jsp"); 
		loadJSP(req, response, "/jsp/pageShell.jsp");
	}
	
	private void deleteSimpleValue( HttpServletRequest req ) throws Exception 
	{
		int id = StringUtil.stringToInt(req.getParameter("id"));
		String category = req.getParameter("category");
		
		Class<?> simpleBeanClass = Class.forName("bean." + category);
		
		SimpleBean simpleBean = simpleEntityManager.getSimpleBean(id, simpleBeanClass);
		
		simpleEntityManager.delete(simpleBean);
	}
}
