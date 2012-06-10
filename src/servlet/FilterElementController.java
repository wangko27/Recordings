package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.StringUtil;
import bean.City;
import bean.Country;
import bean.Quality;
import bean.Recording;
import bean.RecordingSearchBean;
import bean.RecordingType;
import bean.Venue;
import data.RecordingData;
import data.SimpleEntityData;
import domain.RecordingManager;
import domain.SimpleEntityManager;
import exception.RecordingException;

@WebServlet("/FilterElementController")
public class FilterElementController extends ServletExtension 
{
	private static final long serialVersionUID = 1L;	
	private RecordingManager recordingManager;  
	private SimpleEntityManager simpleEntityManager; 
	
	public FilterElementController()
	{
		recordingManager = new RecordingManager(new RecordingData());
		simpleEntityManager = new SimpleEntityManager(new SimpleEntityData());
	}
	
	public FilterElementController(RecordingManager recordingManager, SimpleEntityManager simpleEntityManager)
	{
		this.recordingManager = recordingManager; 
		this.simpleEntityManager = simpleEntityManager; 
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			RecordingSearchBean searchBean = getSearchBean(req);
			req.setAttribute("recordingList", recordingManager.getAllRecordings(searchBean));
		}
		catch( Exception e )
		{
			addErrorMessage(req, new RecordingException(e));
		}
		
		loadPage(req, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			RecordingSearchBean searchBean = getSearchBean(req);
			req.setAttribute("recordingList", recordingManager.getAllRecordings(searchBean));
		}
		catch( Exception e )
		{
			addErrorMessage(req, new RecordingException(e));
		}
		
		loadPage(req, response);
	}
	
	private RecordingSearchBean getSearchBean( HttpServletRequest req ) throws RecordingException
	{
		RecordingSearchBean searchBean = new RecordingSearchBean();
		Recording recording = new Recording();
		
		String year = req.getParameter("year");
		String month = req.getParameter("month");
		String date = req.getParameter("date");
		String country = req.getParameter("country");
		String city = req.getParameter("city");
		String venue = req.getParameter("venue");
		String recordingType = req.getParameter("recordingType");
		String quality = req.getParameter("quality");
		
		if( StringUtil.hasValue(year) )
			recording.setYear(StringUtil.stringToInt(year));
		if( StringUtil.hasValue(month) )
			recording.setMonth(StringUtil.stringToInt(month));
		if( StringUtil.hasValue(date) )
			recording.setDate(StringUtil.stringToInt(date));
		if( StringUtil.hasValue(country) )
			recording.setCountry((Country)simpleEntityManager.getSimpleBean(country, Country.class));
		if( StringUtil.hasValue(city) )
			recording.setCity((City)simpleEntityManager.getSimpleBean(city, City.class));
		if( StringUtil.hasValue(venue) )
			recording.setVenue((Venue)simpleEntityManager.getSimpleBean(venue, Venue.class));
		if( StringUtil.hasValue(recordingType) )
			recording.setRecordingType((RecordingType)simpleEntityManager.getSimpleBean(recordingType, RecordingType.class));
		if( StringUtil.hasValue(quality) )
			recording.setQuality((Quality)simpleEntityManager.getSimpleBean(quality, Quality.class));

		searchBean.setRecording(recording);
		return searchBean;
	}
	
	private void loadPage( HttpServletRequest req, HttpServletResponse response ) throws ServletException, IOException
	{
		req.setAttribute("venues", simpleEntityManager.getAllVenues());
		req.setAttribute("countries", simpleEntityManager.getAllCountries());
		req.setAttribute("cities", simpleEntityManager.getAllCities());
		req.setAttribute("recordingTypes", simpleEntityManager.getAllRecordingTypes());
		req.setAttribute("qualities", simpleEntityManager.getAllQualities());
		
		loadJSP(req, response, "/jsp/filterElements.jsp");
	}

}
