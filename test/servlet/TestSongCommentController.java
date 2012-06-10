package servlet;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import bean.BasicCommentBean;
import data.SongCommentData;
import domain.CommentsManager;
import domain.security.UserManager;

public class TestSongCommentController 
{
	private SongCommentController controller; 
	private HttpServletRequest stubRequest;
	private HttpServletResponse stubResponse; 
	private CommentsManager stubManager;
	private UserManager stubUserManager; 
	StringWriter stubWriter; 
	
	@Before
	public void setup() throws IOException
	{
		mock(SongCommentData.class); 
		stubUserManager = mock(UserManager.class); 
		stubManager = mock(CommentsManager.class);		
		controller = new SongCommentController(stubManager, stubUserManager); 		
		stubRequest = mock(HttpServletRequest.class);
		stubResponse = mock(HttpServletResponse.class); 
		mock(HttpSession.class);  
		stubWriter = new StringWriter(); 
		when(stubResponse.getWriter()).thenReturn(new PrintWriter(stubWriter)); 
	}
	
	@Test
	public void makeCommentBean_worksCorrectly() throws ServletException, IOException
	{		
		when(stubRequest.getParameter("postcomment")).thenReturn("true");
		when(stubRequest.getParameter("type")).thenReturn("recording"); 
		when(stubRequest.getParameter("validation")).thenReturn( ""); 
		when(stubRequest.getParameter("id")).thenReturn(3 + "");
		when(stubRequest.getParameter("user")).thenReturn("jim");
		when(stubRequest.getParameter("text")).thenReturn("text");	
		when(stubRequest.getRemoteAddr()).thenReturn("test"); 
		when(stubManager.commentToJSONString((BasicCommentBean)Mockito.any())).thenReturn("success"); 
		 
		controller.doGet(stubRequest, stubResponse); 
		
		String result = stubWriter.getBuffer().toString(); 
		assertTrue("result was " + result, result.contains("success")); 
	}
	
	@Test
	public void makeSureInitializeWorks()
	{
		controller = new SongCommentController(stubManager, stubUserManager); 
		controller.init(null); 
	}
}
