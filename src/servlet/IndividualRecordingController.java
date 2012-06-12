package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logging.RecordingsLogger;
import bean.Recording;
import bean.RecordingComment;
import data.RecordingCommentData;
import data.RecordingData;
import domain.RecordingManager;
import domain.TableElementManager;
import exception.RecordingException;


@WebServlet("/IndividualRecordingController")
public class IndividualRecordingController extends ServletExtension 
{
	private static final long serialVersionUID = 1L;
	
	// neither of these managers have state 
	private RecordingManager recordingManager = new RecordingManager(new RecordingData()); 
	private TableElementManager tableElementManager = new TableElementManager();
	private PrevNextRecordingIdHelper helper = new PrevNextRecordingIdHelper(); 
	private CommentsLoader commentsLoader = new CommentsLoader(new RecordingCommentData(), RecordingComment.class, "recording"); 
	
	public IndividualRecordingController()
	{
		// required by servlet API 
	}
	
	public IndividualRecordingController(RecordingManager recordingManager, TableElementManager tableElementManager, PrevNextRecordingIdHelper helper, CommentsLoader commentsLoader)
	{
		this.recordingManager = recordingManager; 
		this.tableElementManager = tableElementManager; 
		this.helper = helper;
		this.commentsLoader = commentsLoader; 
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(req, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			loadPage(req, response);
		}
		catch( Exception e )
		{
			this.addErrorMessage(req, new RecordingException(e)); 
			Collection<String> embeddedJs = this.addJQuery("jquery-1.5.2.min.js", null);
			this.setEmbeddedJs(req, embeddedJs);
			setEmbeddedPage(req, "error.jsp");
			
			loadJSP(req, response, "/jsp/pageShell.jsp"); 
		}
	}
	
	private void logRequest(HttpServletRequest req)
	{
		try
		{
			RecordingsLogger.info("request recording id: " +  req.getParameter("id"));
		}
		catch(Exception exc)
		{
			// not important to log logging exceptions!
		}
	}
	
	private void loadPage(HttpServletRequest req, HttpServletResponse response ) throws Exception
	{		
		logRequest(req); 
		
		String idParam = req.getParameter("id");
		int id = Integer.parseInt(idParam); //TODO: vulnerable to NPE or ParseException

		Recording recording = recordingManager.getRecording(id); 
		
		helper.sendPrevAndNextRecordingId(recording, req);

		req.setAttribute("recordingTableElements", tableElementManager.recordingToTableElements(recording));
		req.setAttribute("songInstanceTableElements", tableElementManager.songInstancesToTableElements(recording.getSongInstances()));
				
		/***********load comments data*/
		commentsLoader.loadCommentsAndSetAttributes(id, req, recording); 
		/*************************/ 				
				
		/** suppport votes **/ 
		req.setAttribute("id", id);
		/*************************/ 				
		
		Collection<String> embeddedCSS = new ArrayList<String>(); 
		addCSS("individualrecordingstyle.css", embeddedCSS);
		addCSS("votes.css", embeddedCSS); 
		
		Collection<String> embeddedJs = new ArrayList<String>();
		addJQuery("jquery-1.5.2.min.js", embeddedJs);
		addJQuery("votes.js", embeddedJs); 	
		addJQuery("recordingvotes.js", embeddedJs); 					
		
		this.setEmbeddedJs(req, embeddedJs); 
		this.setEmbeddedCSS(req, embeddedCSS); 
		
		req.setAttribute("recording", recording);
		
		setEmbeddedPage(req, "individualRecording.jsp"); 
		loadJSP(req, response, "/jsp/pageShell.jsp");
	}
}
