package servlet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.StringUtil;
import bean.SmartSong;
import domain.SmartSongManager;
import exception.RecordingException;

@WebServlet("/SmartSongController")
public class SmartSongController extends ServletExtension {
	
	private static final long serialVersionUID = 1L;	
	private SmartSongManager smartSongManager = new SmartSongManager();

	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(req,response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			sendSongList(req);
		}
		catch( Exception e )
		{
			addErrorMessage(req, new RecordingException(e));
		}
		
		loadPage(req, response);
	}
	
	private void sendSongList( HttpServletRequest req ) throws RecordingException
	{
		HttpSession session = req.getSession();
		String sourceEvent = (String) req.getParameter("sourceEvent");
		String pref = (String) req.getParameter("pref");
		Collection<SmartSong> songList = (Collection<SmartSong>) session.getAttribute("songList");
		
		// determine whether to use the recordingList from session or a fresh one
		if( (StringUtil.hasValue(sourceEvent) && sourceEvent.equals("search")) || 
			(StringUtil.hasValue(pref) && pref.equals("sam")) ||
			songList == null )
		{
			SmartSong searchBean = getSearchBean(req);
			setSessionSmartSongSearchBean(req, searchBean);
			songList = smartSongManager.getAllSmartSongs(searchBean);
			
			session.setAttribute("songList", songList);
		}
		
		req.setAttribute("songList", songList);
	}
	
	private SmartSong getSearchBean( HttpServletRequest req ) throws RecordingException
	{
		SmartSong searchBean = new SmartSong();
		
		String firstPlayed = getParamFromSAM(req, "sf", "firstAppeared");
		String lastPlayed = getParamFromSAM(req, "sf", "lastAppeared");
		String howCommon = getParamFromSAM(req, "sf", "howCommon");
		String album = getParamFromSAM(req, "sf", "album1");
		
		if( StringUtil.hasValue(firstPlayed) )
			searchBean.setFirstPlayed(StringUtil.stringToInt(firstPlayed));
		if( StringUtil.hasValue(lastPlayed) )
			searchBean.setLastPlayed(StringUtil.stringToInt(lastPlayed));
		if( StringUtil.hasValue(howCommon) )
			searchBean.setFrequency(StringUtil.stringToInt(howCommon));
		if( StringUtil.hasValue(album) )
			searchBean.setAlbum1(album);

		return searchBean;
	}
	
	private void setSessionSmartSongSearchBean( HttpServletRequest req, SmartSong smartSong )
	{
		HttpSession session = req.getSession();
		if( smartSong != null )
			session.setAttribute("smartSongSearchBean", smartSong);
		else
			session.setAttribute("smartSongSearchBean", new SmartSong());
	}
	
	private void loadPage( HttpServletRequest req, HttpServletResponse response ) throws ServletException, IOException
	{
		loadJSP(req, response, "/jsp/mainFilterTables/songFilterTable.jsp");
	}
}
