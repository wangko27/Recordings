package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logging.RecordingsLogger;

import bean.ErrorBean;
import bean.VotingResult;
import bean.VotingResult.VotingMessage;
import domain.BasicVoteManager;
import domain.ErrorProcessor;
import exception.RecordingException;

public abstract class BasicVoteController<T> extends ServletExtension
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

	/**
	 * called by tomcat on startup. 
	 */
	public abstract void init(ServletConfig config);
	
	/**
	 * retrieves a target bean based on its id
	 * @param id id of the target bean
	 * @return the bean 
	 */
	public abstract T getBeanById(int id);
	
	/**
	 * gets the vote manager this controller is using
	 * @return
	 */
	protected abstract BasicVoteManager<T> getVoteManager(); 				
	
	@Override 
	public void doPost(HttpServletRequest req, HttpServletResponse response)
	{		
		try {
			processAJAXRequest(req, response);
		}
		catch(Exception exc)
		{
			addErrorMessage(req, new RecordingException(exc));
		} 
	}
	
	private void sendAJAXError(HttpServletResponse response, ErrorBean error) throws IOException
	{
		PrintWriter pw = response.getWriter(); 
		pw.write(ErrorProcessor.toJSON(error)); 
		pw.close(); 
	}
	
	private void processAJAXRequest( HttpServletRequest req, HttpServletResponse response ) throws ServletException, IOException, RecordingException
	{
		if(req.getParameter("id") == null || req.getParameter("vote") == null)
		{
			sendAJAXError(response, new ErrorBean("invalid url string")); 
			return; 
		}
		
		int id = 0;
		
		try
		{
			id = Integer.parseInt(req.getParameter("id"));
		}
		catch(NumberFormatException exc)
		{
			sendAJAXError(response, new ErrorBean("invalid id")); 
			return; 
		}
		
		String vote = req.getParameter("vote");  
		int deltaVote = 0; 		
		
		if(vote.equalsIgnoreCase("up"))
			deltaVote = 1;
		else
			deltaVote = -1; 
		
		T targetBean = getBeanById(id); 
		
		if(targetBean == null) 
		{
			sendAJAXError(response, new ErrorBean("invalid item id"));
			return; 
		}
		
		VotingResult voteResult = getVoteManager().castUserVote(deltaVote, targetBean, req.getRemoteAddr(), id);
		
		if(voteResult.getResult() == VotingMessage.TOO_MANY_VOTES)
		{
			sendAJAXError(response, new ErrorBean("You have cast too many votes today")); 
			return; 
		}
		else if (voteResult.getResult() == VotingMessage.ALREADY_VOTED)
		{
			sendAJAXError(response, new ErrorBean("You already voted for this!")); 
			return; 
		}
		
		
		int netVotes = voteResult.getNewObjectVoteCount();  
		
		PrintWriter pw;
		try 
		{
			pw = response.getWriter();
			pw.write(BasicVoteManager.getJSONString(netVotes)); 
		}
		catch (IOException e) 
		{ 
			e.printStackTrace();
		} 		
		
		RecordingsLogger.info("Voted " + deltaVote + " on " + id + " at " + req.getRequestURI(), req.getRemoteAddr()); 
		RecordingsLogger.info("Result: " + voteResult.getMessage()); 
	}	
}
