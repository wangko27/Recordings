package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.RandomRecordingManager;
import exception.RecordingException;

@WebServlet("/RandomRecordingController")
public class RandomRecordingController extends ServletExtension {
	
	private static final long serialVersionUID = 1L;	
	private RandomRecordingManager randomRecordingManager = new RandomRecordingManager();

	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(req,response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			int id = randomRecordingManager.getRandomRecordingId();
			redirect("individualRecording?id=" + id, response);
		}
		catch( Exception e )
		{
			addErrorMessage(req, new RecordingException(e));
		}
	}
}
