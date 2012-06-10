package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.StringUtil;
import bean.Recording;
import bean.Song;
import bean.SongInstance;
import data.RecordingData;
import data.SimpleEntityData;
import data.SongInstanceData;
import domain.RecordingManager;
import domain.SimpleEntityManager;
import domain.SongInstanceManager;
import exception.RecordingException;

public class SongInstanceController extends ServletExtension {
	
private static final long serialVersionUID = 1L;
	
	private SongInstanceManager songInstanceManager = new SongInstanceManager(new SongInstanceData());
	private RecordingManager recordingManager = new RecordingManager(new RecordingData());
	private SimpleEntityManager simpleEntityManager = new SimpleEntityManager(new SimpleEntityData());

	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			String action = req.getParameter("action");
			if( StringUtil.hasValue(action) )
			{
				SongInstance songInstance = loadSongInstance(req);
				if( action.equals("delete") )
				{
					songInstanceManager.deleteSongInstance(songInstance);
				}
				else
				{
					songInstanceManager.saveOrUpdateSongInstance(songInstance);
				}
			}
		}
		catch( Exception e )
		{
			addErrorMessage(req, new RecordingException(e));
		}
	}
	
	private SongInstance loadSongInstance( HttpServletRequest req ) throws RecordingException
	{
		Integer songInstanceId = null;
		Integer recordingId = null;
		Integer songId = null;
		Integer trackListing = null;
		Integer section = null;
		
		if( StringUtil.hasValue(req.getParameter("songInstanceId")))
			songInstanceId = StringUtil.stringToInt(req.getParameter("songInstanceId"));
		if( StringUtil.hasValue(req.getParameter("recordingId")))
			recordingId = StringUtil.stringToInt(req.getParameter("recordingId"));
		if( StringUtil.hasValue(req.getParameter("songId")))
			songId = StringUtil.stringToInt(req.getParameter("songId"));
		if( StringUtil.hasValue(req.getParameter("trackListing")))
			trackListing = StringUtil.stringToInt(req.getParameter("trackListing"));
		if( StringUtil.hasValue(req.getParameter("section")))
			section = StringUtil.stringToInt(req.getParameter("section"));
		
		SongInstance songInstance = null;
		if( songInstanceId != null )
			songInstance = songInstanceManager.getSongInstance(songInstanceId);
		else
		{
			songInstance = new SongInstance();
			Recording recording = recordingManager.getRecording(recordingId);
			songInstance.setRecording(recording);
		}

		Song song = (Song) simpleEntityManager.getSimpleBean(songId, Song.class);
		songInstance.setSong(song);
		songInstance.setTrackListing(trackListing);
		songInstance.setSection(section);
		
		return songInstance;
	}
}
