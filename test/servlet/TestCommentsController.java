package servlet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import bean.BasicCommentBean;
import bean.RecordingComment;
import data.RecordingCommentData;
import data.common.ICommentData;
import data.common.IUserData;
import domain.CommentsManager;
import domain.security.UserActivityManager;
import domain.security.UserManager;

public class TestCommentsController  
{
	BasicCommentController controller = null; 	
	UserManager stubUserManager; 
	UserActivityManager stubActivityManager; 
	IUserData stubUserData; 	
	HttpServletRequest stubRequest; 
	HttpServletResponse stubResponse; 
	StringWriter responseStream; 
	CommentsManager stubCommentsManager; 
	
	@Before
	public void setup() throws IOException
	{
		RecordingCommentData stubRecordingCommentData = mock(RecordingCommentData.class);
		stubUserData = mock(IUserData.class); 
		stubActivityManager = mock(UserActivityManager.class); 
		stubUserManager = new UserManager(stubActivityManager);  		
		stubRequest = mock(HttpServletRequest.class); 
		when(stubRequest.getRemoteAddr()).thenReturn("test");		
		stubCommentsManager = mock(CommentsManager.class); 
		
		controller = new RecordingCommentController(new CommentsManager(stubRecordingCommentData, RecordingComment.class), stubUserManager);

		refreshResponseStream(); 
	}
	
	private void refreshResponseStream() throws IOException
	{
		responseStream = new StringWriter(); 
		PrintWriter pw = new PrintWriter(responseStream); 
		stubResponse = mock(HttpServletResponse.class); 
		when(stubResponse.getWriter()).thenReturn(pw); 
	}
	
	@Test
	public void doGet_unsupportedUrlString_shouldNotThrowException() 
	{		
		
		try
		{
			HttpServletRequest stubRequest = mock(HttpServletRequest.class);
			when(stubRequest.getParameter("ajax")).thenReturn("true");
			
			controller.doGet(stubRequest, null);
		}
		catch(Exception exc)
		{
			assertTrue(false); 
		}
	}
	
	@Test
	public void doGet_testACommentPostWithNullFieldValues_shouldReturnBlankFieldError() throws ServletException, IOException
	{
		when(stubRequest.getParameter("postcomment")).thenReturn("true");
		when(stubRequest.getParameter("type")).thenReturn("recording"); 
		when(stubRequest.getParameter("validation")).thenReturn(""); 
		when(stubRequest.getParameter("id")).thenReturn(3 + "");
		
		controller.doGet(stubRequest, stubResponse); 
		  
		String result = responseStream.getBuffer().toString(); 
		assertTrue( " result was " + result, result.contains("BLANK_FIELD")); 
	} 
	
	@Test	
	public void doGet_testTooManyUserRequests() throws ServletException, IOException
	{
		when(stubRequest.getRemoteAddr()).thenReturn("test"); 
		when(stubRequest.getParameter("postcomment")).thenReturn("true");
		when(stubRequest.getParameter("type")).thenReturn("recording"); 
		when(stubRequest.getParameter("validation")).thenReturn(""); 
		when(stubRequest.getParameter("id")).thenReturn(3 + "");		
		when(stubRequest.getParameter("user")).thenReturn("jim");
		when(stubRequest.getParameter("text")).thenReturn("text");			
		
		controller.doGet(stubRequest, stubResponse);
		refreshResponseStream() ;
		controller.doGet(stubRequest, stubResponse);		
		 
		String result = responseStream.getBuffer().toString(); 
		
		// check for too many comments error message
		assertTrue("result was " + result, result.contains("TOO_MANY_COMMENTS")); 
	}
	
	@Test
	public void doGet_onlyCertainNumberOfUpvotesPerDay() throws ServletException, IOException
	{
		BasicCommentBean bean = mock(BasicCommentBean.class); 
		when(stubCommentsManager.getCommentById(Mockito.anyInt())).thenReturn(bean);
		when(stubCommentsManager.voteCountToJSONString(Mockito.anyInt())).thenReturn("\"voteRank\":\"1\""); 
		
		controller =   new ConcreteCommentController(stubCommentsManager, stubUserManager);  	
		
		for(int i=0;i<1000;i++) 
		{
			makeValidUpvoteRequest();  
		}
		
		String errorMessage = makeValidUpvoteRequest(); 
		assertTrue("error was " + errorMessage, errorMessage.contains("TOO_MANY_VOTES")); 
	}
	
	private String makeValidUpvoteRequest() throws ServletException, IOException
	{
		when(stubRequest.getParameter("votecomment")).thenReturn("true");
		when(stubRequest.getParameter("commentid")).thenReturn("1");			
		
		refreshResponseStream(); 

		controller.doGet(stubRequest, stubResponse);
		
		String result = responseStream.getBuffer().toString();
		return result; 
	}
	
	@Test
	public void doGet_testUpvoteRequestWithInvalidFields() throws IOException, ServletException
	{
		ICommentData stubCommentData = mock(ICommentData.class); 
		BasicCommentBean bean = mock(BasicCommentBean.class); 
		when(stubCommentData.getCommentById(Mockito.anyInt())).thenReturn(bean); 		
		controller = new RecordingCommentController(new CommentsManager(stubCommentData, RecordingComment.class), stubUserManager); 
		
		when(stubRequest.getParameter("votecomment")).thenReturn("true");
		when(stubRequest.getParameter("commentid")).thenReturn(null);				
		
		controller.doGet(stubRequest, stubResponse);
		
		String result = responseStream.getBuffer().toString();
				
		assertTrue("result was " + result, result.contains("BLANK_FIELD")); 
	}
	
	@Test
	public void doGet_testAValidCommentUpvoteRequest() throws ServletException, IOException
	{
		BasicCommentBean bean = mock(BasicCommentBean.class); 		
		when(stubCommentsManager.voteCountToJSONString(Mockito.anyInt())).thenReturn("\"voteRank\":\"1\"");
		when(stubCommentsManager.getCommentById(Mockito.anyInt())).thenReturn(bean); 
		
		controller = new ConcreteCommentController(stubCommentsManager, stubUserManager); 
		 
		String result = makeValidUpvoteRequest(); 
		
		// a valid comment upvote result will be a message saying SUCCESS
		assertTrue("result was " + result, result.contains("\"voteRank\":\"1\"")); 
	}
	
	@Test
	public void doGet_testAValidCommentPostRequest() throws ServletException, IOException
	{
		when(stubRequest.getParameter("postcomment")).thenReturn("true");
		when(stubRequest.getParameter("type")).thenReturn("recording"); 
		when(stubRequest.getParameter("validation")).thenReturn(""); 
		when(stubRequest.getParameter("id")).thenReturn(3 + "");
		
		when(stubRequest.getParameter("user")).thenReturn("jim");
		when(stubRequest.getParameter("text")).thenReturn("text");			
		
		controller.doGet(stubRequest, stubResponse); 
		
		String result = responseStream.getBuffer().toString(); 
		
		// checking for username ensures that the comment bean was converted to JSON 
		assertTrue("result was " + result, result.contains("\"username\":\"jim\"")); 
	}		
	
	@Test
	public void doGet_invalidCommentPostDoesNotUpdateUserActivityEntry() throws ServletException, IOException
	{
		when(stubRequest.getParameter("postcomment")).thenReturn("true");
		when(stubRequest.getParameter("type")).thenReturn("recording"); 
		when(stubRequest.getParameter("validation")).thenReturn(""); 
		when(stubRequest.getParameter("id")).thenReturn(3 + "");
		
		when(stubRequest.getParameter("user")).thenReturn("");
		when(stubRequest.getParameter("text")).thenReturn("text");
		
		controller.doGet(stubRequest, stubResponse); 
		
		// make sure it's invalid the first time 
		String result = responseStream.getBuffer().toString(); 
		assertTrue(" result was " + result, result.contains("BLANK_FIELD"));
		
		refreshResponseStream(); 
		controller.doGet(stubRequest, stubResponse); 
		
		result = responseStream.getBuffer().toString(); 
		// ensure it doesn't display the "TOO MANY COMMENTS" message
		assertFalse(" result was " + result, result.contains("TOO_MANY_COMMENTS")); 
		assertTrue(" result was " + result, result.contains("BLANK_FIELD")); 
	}
}
