package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logging.RecordingsLogger;
import util.HTMLSanitizer;
import util.StringUtil;
import bean.BasicCommentBean;
import bean.CommentValidationBean;
import bean.CommentValidationBean.CommentValidationResult;
import bean.VotingResult;
import bean.VotingResult.VotingMessage;
import domain.CommentValidator;
import domain.CommentVoteManager;
import domain.CommentsManager;
import domain.security.UserManager;
import exception.RecordingException;


@WebServlet("/CommentsController")
public abstract class BasicCommentController extends ServletExtension
{	
	private CommentsManager manager; 
	private CommentVoteManager voteManager;
	
	
	public BasicCommentController()
	{
		this(null); 
	}
	
	protected BasicCommentController(CommentsManager manager)
	{
		createUserManager(null); 
		setCommentManager(manager);  
	}		
	
	protected BasicCommentController(CommentsManager manager, UserManager userManager)
	{
		createUserManager(userManager); 
		setCommentManager(manager);
	}
	
	protected abstract void createUserManager(UserManager optionalUserManager); 
	protected abstract UserManager getUserManager(); 
	protected abstract CommentsManager createManager();	
	
	/**
	 * this method should return a new BasicCommentBean, with all of its properties unique
	 * to the subclassing controller set. 
	 * @param req the http request from which to get parameters
	 * @return a new BasicCommentBean 
	 */
	protected abstract BasicCommentBean makeCommentBean(HttpServletRequest req);
	
	public void init(ServletConfig config)
	{
		setCommentManager(createManager()); 
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse response)
	{
		doGet(req, response);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse response) 
	{
		if(manager == null) 
			throw new NullPointerException("FATAL: manager was not initialized in servlet."); 
		
		try
		{
			this.loadPage(req, response); 
		}
		catch( Exception e )
		{
			addErrorMessage(req, new RecordingException(e));
		}		
	}	
	
	private void loadPage( HttpServletRequest req, HttpServletResponse response ) throws ServletException, IOException, RecordingException
	{
		if(req.getParameter("postcomment") != null)
		{			
			processCommentPostRequest(req, response); 
		}
		else if (req.getParameter("votecomment") != null)
		{
			processCommentUpvoteRequest(req, response); 
		}
		else if (req.getParameter("deletecomment") != null)
		{
			processCommentDeleteRequest( req,  response); 
		}
		else if (req.getParameter("editcomment") != null)
		{
			processCommentEditRequest( req,  response); 
		}
		else
		{
			// oops 
		}
	}
	
	private void processCommentDeleteRequest(HttpServletRequest req, HttpServletResponse response) 
	{
		try
		{
			// TODO: doesn't work for song comments 
			String commentId = req.getParameter("commentId");
			
			if( StringUtil.hasValue(commentId) )
			{
				int id = StringUtil.stringToInt(commentId);
				
				BasicCommentBean comment =  manager.getCommentById(id);
				comment.setText("SKIP" + comment.getText());
				
				manager.saveComment(comment);
				
				RecordingsLogger.info("Comment " + id + " was deleted", req.getRemoteAddr()); 
			}
		}
		catch( Exception e )
		{
			addErrorMessage(req, new RecordingException(e));
		}
	}
	
	private void processCommentEditRequest(HttpServletRequest req, HttpServletResponse response) 
	{
		try
		{
			// TODO: doesn't work for song comments 
			String commentId = req.getParameter("commentId");
			String text = req.getParameter("text");
			
			if( StringUtil.hasValue(commentId) && StringUtil.hasValue(text) )
			{
				int id = StringUtil.stringToInt(commentId);
				String trimmedText = new String(text.trim());
				
				BasicCommentBean comment =  manager.getCommentById(id);
				String oldText = comment.getText();
				comment.setText(trimmedText);
				
				manager.saveComment(comment);
				
				RecordingsLogger.info("Comment " + id + " was saved. Old=" + oldText + " ||| New=" + trimmedText, req.getRemoteAddr());
			}
		}
		catch( Exception e )
		{
			addErrorMessage(req, new RecordingException(e));
		}
	}
	
	private void processCommentUpvoteRequest(HttpServletRequest req, HttpServletResponse response ) throws IOException, RecordingException
	{
		super.noCaching(response); 
		
		int commentId = 0; 		
		
		try
		{
			commentId = Integer.parseInt(req.getParameter("commentid" )); 
		}
		catch(Exception exc)
		{
			PrintWriter out = response.getWriter();
			out.write(new CommentValidationBean("There was a problem looking up the comment", CommentValidationResult.BLANK_FIELD).toJSONString()); 		
			out.close();
			return; 
		}
		
		BasicCommentBean comment = manager.getCommentById(commentId);
		
		if(comment == null) 
		{
			PrintWriter out = response.getWriter();
			out.write(new CommentValidationBean("Invalid comment", CommentValidationResult.BLANK_FIELD).toJSONString());
			out.close(); 
			return; 
		}
		
		VotingResult voteResult = voteManager.castUserVote(1,comment, req.getRemoteAddr(), commentId); 		
		if(voteResult.getResult() == VotingMessage.TOO_MANY_VOTES || voteResult.getResult() == VotingMessage.ALREADY_VOTED)
		{
			PrintWriter out = response.getWriter(); 
			out.write(new CommentValidationBean(voteResult.getMessage(), CommentValidationResult.TOO_MANY_VOTES).toJSONString());
			out.close(); 
			return;  
		}		 
		
		String result =manager.voteCountToJSONString(voteResult.getNewObjectVoteCount());   
		
		PrintWriter out = response.getWriter(); 
		out.write(result);
		out.close(); 
		
		RecordingsLogger.info("Upvoted comment " + commentId, req.getRemoteAddr()); 
	}
	
	private void processCommentPostRequest(HttpServletRequest req, HttpServletResponse response ) throws IOException
	{	
		// make sure the user hasn't posted too much			
		if(!getUserManager().userHasBeenInactiveForADuration(req.getRemoteAddr(),UserManager.MIN_TIME_BETWEEN_UPDATES))
		{			
			CommentValidationBean validationResult = new CommentValidationBean("You have posted too many comments too quickly. Please wait a second to post another one. ", CommentValidationResult.TOO_MANY_COMMENTS);
			PrintWriter out = response.getWriter(); 
			out.write(validationResult.toJSONString()); 
			out.close();   
			return;   
		}  		
			
		// a null userValidationText value is considered "ignore user validation" 
		String userValidationText = req.getParameter("validation"); 
		if(userValidationText == null) userValidationText = "invalid";
		BasicCommentBean unvalidatedBean = makeCommentBean(req);		
		CommentValidationBean validationResult = new CommentValidator().validate(userValidationText,  unvalidatedBean); 
		
		if(validationResult.getResult() != CommentValidationResult.SUCCESS)
		{				
			// failure
			PrintWriter out = response.getWriter();  
			out.write(validationResult.toJSONString()); 
			out.close(); 
			return; 
		}
		else
		{
			// only update the user activity if the comment was posted successfully 
			getUserManager().updateUser(Calendar.getInstance().getTime(), req.getRemoteAddr());
		}
		
		BasicCommentBean validatedBean = unvalidatedBean; 				
		BasicCommentBean resultBean =  makeCommentPost(validatedBean);
		
		// send comment bean as a JSON string
		String jsonString = manager.commentToJSONString(resultBean);
		PrintWriter out = response.getWriter(); 
		out.write(jsonString); 
		out.close(); 
		RecordingsLogger.info("Comment was posted by " + resultBean.getUsername() + " on " + req.getRequestURI(), req.getRemoteAddr()); 
		RecordingsLogger.info("JSON: " + jsonString); 
	}

	/**
	 * sends a bean to the comment manager for persistence to the database. 
	 * @param bean the bean to send
	 * @param req 
	 * @return the updated bean containing its new Id 
	 */
	protected BasicCommentBean makeCommentPost(BasicCommentBean bean)
	{
		
		Long commentId = manager.saveComment(bean);
		bean.setId(commentId.intValue()); 
		
		return bean; 
	}
		
	protected void setCommentManager(CommentsManager manager)
	{
		this.manager = manager; 
		createUserManager(null); 
		voteManager = new CommentVoteManager(manager, getUserManager()); 
	}
	
	/**
	 * sets properties of a BasicCommentBean, ignoring any properties specific to eg. RecordingCommentBean
	 * @param bean the bean to update. 
	 * @param req the request containing the parameters 
	 */
	protected void setGeneralCommentBeanProperties(BasicCommentBean bean, HttpServletRequest req)
	{		
		bean.setText(HTMLSanitizer.sanitize(req.getParameter("text"))); 
		bean.setUsername(HTMLSanitizer.sanitize(req.getParameter("user"))); 
		bean.setTimestamp(Calendar.getInstance().getTime()); 		
	}
}
