package servlet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

@WebServlet("/RecordingsListController")
public class RecordingsListController extends ServletExtension {
	
	private static final long serialVersionUID = 1L;	
	
	private RecordingManager recordingManager; 
	private SimpleEntityManager simpleEntityManager;
	private static final String SAM_PREFIX = "rf";

	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(req,response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		this.recordingManager = new RecordingManager(new RecordingData());; 
		this.simpleEntityManager = new SimpleEntityManager(new SimpleEntityData());
		
		try
		{
			sendRecordingList(req);
		}
		catch( Exception e )
		{
			addErrorMessage(req, new RecordingException(e));
		}
		
		loadPage(req, response);
	}
	
	private void sendRecordingList( HttpServletRequest req ) throws RecordingException
	{
		HttpSession session = req.getSession();
		String sourceEvent = (String) req.getParameter("sourceEvent");
		String pref = (String) req.getParameter("pref");
		Collection<Recording> recordingList = (Collection<Recording>) session.getAttribute("recordingList");
		
		// determine whether to use the recordingList from session or a fresh one
		if( (StringUtil.hasValue(sourceEvent) && sourceEvent.equals("search")) || 
			(StringUtil.hasValue(pref) && pref.equals("sam")) ||
			recordingList == null )
		{
			RecordingSearchBean searchBean = getSearchBean(req);
			setSessionRecordingSearchBean(req, searchBean);
			recordingList = recordingManager.getAllRecordings(searchBean);
			
			session.setAttribute("recordingList", recordingList);
		}
		
		req.setAttribute("recordingList", recordingList);
	}
	
	private RecordingSearchBean getSessionRecordingSearchBean( HttpServletRequest req )
	{
		HttpSession session = req.getSession();
		RecordingSearchBean recordingSearchBean = null;
		
		Object recordingSearchBean_session = session.getAttribute("recordingSearchBean");
		if(  recordingSearchBean_session != null )
			recordingSearchBean = (RecordingSearchBean) recordingSearchBean_session;
		
		return recordingSearchBean;
	}
	
	private void setSessionRecordingSearchBean( HttpServletRequest req, RecordingSearchBean recordingSearchBean )
	{
		HttpSession session = req.getSession();
		if( recordingSearchBean != null )
			session.setAttribute("recordingSearchBean", recordingSearchBean);
		else
			session.setAttribute("recordingSearchBean", getEmptyRecordingSearchBean());
	}
	
	private RecordingSearchBean getEmptyRecordingSearchBean()
	{
		RecordingSearchBean emptyRSB = new RecordingSearchBean();
		emptyRSB.setRecording(new Recording());
		
		return emptyRSB;
	}
	
	private RecordingSearchBean getSearchBean( HttpServletRequest req ) throws RecordingException
	{
		RecordingSearchBean searchBean = new RecordingSearchBean();
		Recording recording = new Recording();
		
		String year = getParamFromSAM(req, SAM_PREFIX, "year");
		String month = getParamFromSAM(req, SAM_PREFIX, "month");
		String date = getParamFromSAM(req, SAM_PREFIX, "date");
		String country = getParamFromSAM(req, SAM_PREFIX, "country");
		String city = getParamFromSAM(req, SAM_PREFIX, "city");
		String venue = getParamFromSAM(req, SAM_PREFIX, "venue");
		String recordingType = getParamFromSAM(req, SAM_PREFIX, "recordingType");
		String quality = getParamFromSAM(req, SAM_PREFIX, "quality");
		
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
		loadJSP(req, response, "/jsp/mainFilterTables/recordingFilterTable.jsp");
	}
}
