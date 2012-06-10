package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logging.RecordingsLogger;

import util.StringUtil;
import bean.City;
import bean.Country;
import bean.Format;
import bean.Media;
import bean.Quality;
import bean.Recording;
import bean.RecordingType;
import bean.Venue;
import data.RecordingData;
import data.SimpleEntityData;
import domain.RecordingManager;
import domain.SimpleEntityManager;
import exception.RecordingException;


@WebServlet("/EditIndividualRecordingsController")
public class EditIndividualRecordingController extends ServletExtension 
{
	private static final long serialVersionUID = 1L;	
	private RecordingManager recordingManager = new RecordingManager(new RecordingData()); 
	private SimpleEntityManager simpleEntityManager = new SimpleEntityManager(new SimpleEntityData()); 	
	
	
	public EditIndividualRecordingController()
	{
		
	}
	
	public EditIndividualRecordingController(RecordingManager recordingManager, SimpleEntityManager simpleEntityManager)
	{
		this.recordingManager = recordingManager; 
		this.simpleEntityManager = simpleEntityManager; 
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		Recording recording = null; 
		
		try
		{
			recording = getRecording(req); 
			loadPage(req, response, recording);
		}
		catch(Exception exc)
		{
			addErrorMessage(req, new RecordingException(exc));
			Collection<String> embeddedJs = this.addJQuery("jquery-1.5.2.min.js", null);
			this.setEmbeddedJs(req, embeddedJs);
			setEmbeddedPage(req, "error.jsp"); 
			loadJSP(req, response, "/jsp/pageShell.jsp");
		}
				
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		
		Recording recording = null; 
		
		try
		{
			recording = getRecording(req);
			loadRecordingFromView(req, recording);		
			loadPage(req, response, recording);
		}
		catch( Exception e )
		{
			addErrorMessage(req, new RecordingException(e));
			Collection<String> embeddedJs = this.addJQuery("jquery-1.5.2.min.js", null);
			this.setEmbeddedJs(req, embeddedJs);
			setEmbeddedPage(req, "error.jsp"); 
			loadJSP(req, response, "/jsp/pageShell.jsp");
		}
						
	}
	
	private Recording getRecording( HttpServletRequest req ) throws RecordingException
	{
		try
		{
			String idParam = req.getParameter("id");
			int id = Integer.parseInt(idParam);
			Recording recording = recordingManager.getRecording(id);
			
			return recording;
		}
		catch(NumberFormatException exc)
		{
			throw new RecordingException("The recording id '" +  req.getParameter("id") + "' is invalid", exc ); 
		}
	} 
	
	private void loadRecordingFromView( HttpServletRequest req, Recording recording ) throws RecordingException
	{
		Integer month = null;
		Integer date = null;
		Integer year = null;
		Venue venue = null;
		Quality quality = null;
		Format format = null;
		RecordingType recordingType = null;
		Media media = null;
		City city = null;
		Country country = null;
		String name = null;
		Integer jon = 0;
		String jonnote = null;
		
		if( StringUtil.hasValue(req.getParameter("month")) )
			month = StringUtil.stringToInt(req.getParameter("month"));
		if( StringUtil.hasValue(req.getParameter("date")) )
			date = StringUtil.stringToInt(req.getParameter("date"));
		if( StringUtil.hasValue(req.getParameter("year")) )
			year = StringUtil.stringToInt(req.getParameter("year"));
		if( StringUtil.hasValue(req.getParameter("name")) )
			name = req.getParameter("name");
		
		if( StringUtil.hasValue(req.getParameter("jon")) )
		{
			if( req.getParameter("jon").equals("on") )
				jon = 1;
		}
		if( StringUtil.hasValue(req.getParameter("jonnote")) )
			jonnote = req.getParameter("jonnote");
		
		// venue 
		if( StringUtil.hasValue(req.getParameter("newVenue")) )
		{
			Venue newVenue = new Venue(req.getParameter("newVenue"));
			simpleEntityManager.saveOrUpdateSimpleBean(newVenue);
			venue = newVenue;
		}
		else if( StringUtil.hasValue(req.getParameter("venueid")) )
			venue = (Venue) simpleEntityManager.getSimpleBean(StringUtil.stringToInt(req.getParameter("venueid")), Venue.class);
		
		// quality
		if( StringUtil.hasValue(req.getParameter("newQuality")) )
		{
			Quality newQuality = new Quality(req.getParameter("newQuality"));
			simpleEntityManager.saveOrUpdateSimpleBean(newQuality);
			quality = newQuality;
		}
		else if( StringUtil.hasValue(req.getParameter("qualityid")) )
			quality = (Quality) simpleEntityManager.getSimpleBean(StringUtil.stringToInt(req.getParameter("qualityid")), Quality.class);
		
		// format
		if( StringUtil.hasValue(req.getParameter("newFormat")) )
		{
			Format newFormat = new Format(req.getParameter("newFormat"));
			simpleEntityManager.saveOrUpdateSimpleBean(newFormat);
			format = newFormat;
		}
		else if( StringUtil.hasValue(req.getParameter("formatid")) )
			format = (Format) simpleEntityManager.getSimpleBean(StringUtil.stringToInt(req.getParameter("formatid")), Format.class);
		
		// recordingType
		if( StringUtil.hasValue(req.getParameter("newRecordingType")) )
		{
			RecordingType newRecordingType = new RecordingType(req.getParameter("newRecordingType"));
			simpleEntityManager.saveOrUpdateSimpleBean(newRecordingType);
			recordingType = newRecordingType; 
		}
		else if( StringUtil.hasValue(req.getParameter("recordingtypeid")) )
			recordingType = (RecordingType) simpleEntityManager.getSimpleBean(StringUtil.stringToInt(req.getParameter("recordingtypeid")), RecordingType.class);
		
		// media
		if( StringUtil.hasValue(req.getParameter("newMedia")) )
		{
			Media newMedia = new Media(req.getParameter("newMedia"));
			simpleEntityManager.saveOrUpdateSimpleBean(newMedia);
			media = newMedia;
		} 
		else if( StringUtil.hasValue(req.getParameter("mediaid")) )
			media = (Media) simpleEntityManager.getSimpleBean(StringUtil.stringToInt(req.getParameter("mediaid")), Media.class);
		
		// city
		if( StringUtil.hasValue(req.getParameter("newCity")) )
		{
			City newCity = new City(req.getParameter("newCity"));
			simpleEntityManager.saveOrUpdateSimpleBean(newCity);
			city = newCity;
		}
		else if( StringUtil.hasValue(req.getParameter("cityid")) )
			city = (City) simpleEntityManager.getSimpleBean(StringUtil.stringToInt(req.getParameter("cityid")), City.class);
		
		// country
		if( StringUtil.hasValue(req.getParameter("newCountry")) )
		{
			Country newCountry = new Country(req.getParameter("newCountry"));
			simpleEntityManager.saveOrUpdateSimpleBean(newCountry);
			country = newCountry;
		}
		else if( StringUtil.hasValue(req.getParameter("countryid")) )
			country = (Country) simpleEntityManager.getSimpleBean(StringUtil.stringToInt(req.getParameter("countryid")), Country.class);
		
		recording.setMonth(month);
		recording.setDate(date);
		recording.setYear(year);
		recording.setVenue(venue);
		recording.setQuality(quality);
		recording.setFormat(format);
		recording.setRecordingType(recordingType);
		recording.setMedia(media);
		recording.setCity(city);
		recording.setCountry(country);
		recording.setName(name);
		recording.setJon(jon);
		recording.setJonnote(jonnote);
		
		recordingManager.saveOrUpdate(recording);
		
		RecordingsLogger.info("Edited " + recording.getId(), req.getRemoteAddr()); 
	}
	
	private void loadPage( HttpServletRequest req, HttpServletResponse response, Recording recording ) throws Exception
	{
		if(recording != null)
		{
			new PrevNextRecordingIdHelper().sendPrevAndNextRecordingId(recording, req);
			req.setAttribute("recording", recording);
		}
		
		req.setAttribute("venues", simpleEntityManager.getAllVenues());
		req.setAttribute("qualities", simpleEntityManager.getAllQualities());
		req.setAttribute("formats", simpleEntityManager.getAllFormats());
		req.setAttribute("recordingTypes", simpleEntityManager.getAllRecordingTypes());
		req.setAttribute("medias", simpleEntityManager.getAllMedias());
		req.setAttribute("cities", simpleEntityManager.getAllCities());
		req.setAttribute("countries", simpleEntityManager.getAllCountries());
		
		req.setAttribute("songs", simpleEntityManager.getAllSongs());
		
		// css
		Collection<String> embeddedCSS = new ArrayList<String>(); 
		addCSS("individualrecordingstyle.css", embeddedCSS);
		this.setEmbeddedCSS(req, embeddedCSS);
		
		// js
		Collection<String> embeddedJs = new ArrayList<String>();
		addJQuery("jquery-1.5.2.min.js", embeddedJs);
		addJQuery("editIndividualRecording.js", embeddedJs);
		addJQuery("editSongInstance.js", embeddedJs);
		this.setEmbeddedJs(req, embeddedJs); 
		
		setEmbeddedPage(req, "editIndividualRecording.jsp"); 
		loadJSP(req, response, "/jsp/pageShell.jsp");
	}
}
