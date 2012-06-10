package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logging.RecordingsLogger;

import bean.Recording;
import data.RecordingData;
import domain.RecordingManager;
import exception.RecordingException;

@WebServlet("/CreateRecordingController")
public class CreateRecordingController extends ServletExtension 
{
	private static final long serialVersionUID = 1L;	
	private RecordingManager recordingManager = new RecordingManager(new RecordingData()); 
	
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			Recording recording = recordingManager.createBlankRecording();
			RecordingsLogger.info("Created recording " + recording.getId(), req.getRemoteAddr()); 
			
			redirect("editIndividualRecording?id=" + recording.getId(), response);
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
}
