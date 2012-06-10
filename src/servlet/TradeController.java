package servlet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Recording;
import data.RecordingData;
import domain.RecordingManager;
import exception.RecordingException;

@WebServlet("/TradeController")
public class TradeController extends ServletExtension 
{
	private static final long serialVersionUID = 1L;	

	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(req,response);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
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
		RecordingManager recordingManager = new RecordingManager(new RecordingData()); 
		Collection<Recording> recordingList = (Collection<Recording>) recordingManager.getAllRecordingsForTrade();
		
		req.setAttribute("recordingList", recordingList);
	}
	
	private void loadPage( HttpServletRequest req, HttpServletResponse response ) throws ServletException, IOException
	{
		loadJSP(req, response, "/jsp/trade.jsp");
	}

}
