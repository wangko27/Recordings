package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Recording;
import data.RecordingData;
import domain.RecordingManager;
import exception.RecordingException;

@WebServlet("/DeleteRecordingController")
public class DeleteRecordingController extends ServletExtension 
{
	private static final long serialVersionUID = 1L;	
	private RecordingManager recordingManager = new RecordingManager(new RecordingData()); 
	
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			Recording recording = getRecording(req);
			
			recordingManager.delete(recording);
			
			// TODO: update recording list in session
			
			redirect("home", response);
		}
		catch( Exception e )
		{
			addErrorMessage(req, new RecordingException(e));
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(req, response);
	}
	
	private Recording getRecording( HttpServletRequest req )
	{
		String idParam = req.getParameter("id");
		int id = Integer.parseInt(idParam); //TODO: vulnerable to NPE or ParseException
		Recording recording = recordingManager.getRecording(id);
		
		return recording;
	}
}
