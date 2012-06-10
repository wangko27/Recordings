package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.BasicCommentBean;
import bean.SmartSong;
import bean.SongComment;
import data.RecordingData;
import data.SongCommentData;
import domain.CommentsManager;
import domain.RecordingManager;
import domain.SmartSongManager;
import domain.TableElementManager;
import exception.RecordingException;


@WebServlet("/SongController")
public class SongController extends ServletExtension
{
	private static final long serialVersionUID = 1L;
	private SmartSongManager smartSongManager = new SmartSongManager();
	private TableElementManager tableElementManager = new TableElementManager();

	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			this.loadPage(req, response); 
		}
		catch( Exception e )
		{
			addErrorMessage(req, new RecordingException(e));
		}		
	}
	
	private void loadPage( HttpServletRequest req, HttpServletResponse response ) throws ServletException, IOException
	{
		String idParam = req.getParameter("id");
		int id = Integer.parseInt(idParam); //TODO: vulnerable to NPE or ParseException
		
		/* add js include files */ 
		Collection<String> embeddedJs = new ArrayList<String>();
		// must be listed in the order of dependency!
		addJQuery("jquery-1.5.2.min.js", embeddedJs);
		addJQuery("votes.js", embeddedJs); 
		addJQuery("songvotes.js", embeddedJs); 
		
		/* set up embedded css include files */ 
		Collection<String> embeddedCSS = new ArrayList<String>();
		addCSS("votes.css", embeddedCSS); 
		addCSS("song.css", embeddedCSS); 
		
		/* set up embedded page (home.jsp) and include js/css files */ 
		this.setEmbeddedJs(req, embeddedJs); 
		this.setEmbeddedCSS(req, embeddedCSS);
		
		SmartSong song = (SmartSong) smartSongManager.getSmartSong(id);
		req.setAttribute("song", song);
		
		req.setAttribute("songTableElements", tableElementManager.songToTableElements(song));
		
		RecordingManager recordingManager = new RecordingManager(new RecordingData());
		req.setAttribute("recordings", recordingManager.getRecordingsAssociatedWithSong(song.getId()));
		
		/***********load comments data*/ 
		Collection<?> comments = new ArrayList<BasicCommentBean>(); 
		comments = new CommentsManager(new SongCommentData(),SongComment.class).getSortedCommentsForSpecificBean(id); 
		req.setAttribute("commentData",comments ); 		
		req.setAttribute("commentSource", "song"); 
		req.setAttribute("commentSourceId", id); 
		req.setAttribute("commentSourceObject", song); 
		req.setAttribute("commentControllerUrl", req.getRequestURI().toString()  + "?" + req.getQueryString()); 
		
		setEmbeddedPage(req, "song.jsp"); 
		loadJSP(req, response, "/jsp/pageShell.jsp");
	}
}
