package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.StringUtil;
import bean.Recording;
import bean.Song;
import bean.SongInstance;
import data.RecordingData;
import data.SimpleEntityData;
import domain.RecordingManager;
import domain.SimpleEntityManager;
import exception.RecordingException;

@WebServlet("/AddBlankSongs")
public class AddBlankSongs extends ServletExtension {
	
	private static final long serialVersionUID = 1L;	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(req,response);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			String id = req.getParameter("id");
			String blankSongs = req.getParameter("blankSongs");
			
			if( StringUtil.hasValue(id) && StringUtil.hasValue(blankSongs) )
			{
				int int_id = StringUtil.stringToInt(id);
				int int_blankSongs = StringUtil.stringToInt(blankSongs);
				
				RecordingManager recordingManager = new RecordingManager( new RecordingData() );
				Recording recording = recordingManager.getRecording(int_id);
				
				List<SongInstance> songInstances = recording.getSongInstances();

				// get the "dummy" song that will be added
				SimpleEntityManager simpleEntityManager = new SimpleEntityManager( new SimpleEntityData() );
				int DUMMY_SONG_ID = 356; /* interview... */
				Song song = (Song) simpleEntityManager.getSimpleBean(DUMMY_SONG_ID, Song.class);
				
				// add that "dummy" song the specified number of times to the show
				for( int x = 0; x < int_blankSongs; x++ )
				{
					SongInstance si = new SongInstance();
					si.setRecording(recording);
					si.setSong(song);
					songInstances.add(si);
				}
				
				recordingManager.saveOrUpdate(recording);
			}
		}
		catch( Exception e )
		{
			addErrorMessage(req, new RecordingException(e));
		}
	}
	
	
}