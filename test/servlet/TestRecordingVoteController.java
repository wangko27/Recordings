package servlet;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import bean.Recording;
import bean.VotingResult;
import bean.VotingResult.VotingMessage;
import domain.RecordingManager;
import domain.RecordingVoteManager;
import domain.security.UserManager;
import exception.RecordingException;

public class TestRecordingVoteController
{
	private RecordingVoteController controller; 
	private UserManager userManager;
	private RecordingManager recordingManager; 
	private RecordingVoteManager voteManager; 
	private HttpServletRequest stubRequest; 
	private HttpServletResponse stubResponse; 
	private PrintWriter injectedPrintWriter;
	private StringWriter injectedStringWriter; 

	
	@Before
	public void setup() throws IOException, RecordingException
	{
		recordingManager = mock(RecordingManager.class); 
		userManager= mock(UserManager.class); 
		voteManager = mock(RecordingVoteManager.class); 
		stubRequest = mock(HttpServletRequest.class); 
		stubResponse = mock(HttpServletResponse.class); 
		controller = new RecordingVoteController(recordingManager, userManager, voteManager);
		
		injectedStringWriter= new StringWriter(); 
		injectedPrintWriter = new PrintWriter(injectedStringWriter);
		when(stubResponse.getWriter()).thenReturn(injectedPrintWriter);
		when(stubRequest.getParameter("id")).thenReturn("1");		
		when(stubRequest.getRemoteAddr()).thenReturn("test"); 		
		when(recordingManager.getRecording(1)).thenReturn(new Recording()); 
		VotingResult fakeValidResult = new VotingResult(VotingMessage.SUCCESS, "success", 1); 		
		when(voteManager.castUserVote(Mockito.anyInt(), (Recording) Mockito.any(), Mockito.anyString(), Mockito.anyInt())).thenReturn(fakeValidResult);
	}
		
	
	@Test
	public void testValidUpVoteRequest()
	{			
		when(stubRequest.getParameter("vote")).thenReturn("up"); 
		
		controller.doPost(stubRequest, stubResponse); 
		
		String result = injectedStringWriter.getBuffer().toString(); 
		assertTrue("result was " + result, result.contains("\"votes\":1")); 
	}
	
	@Test 
	public void testValidDownVoteRequest() throws RecordingException
	{		 
		when(stubRequest.getParameter("vote")).thenReturn("down"); 
		when(voteManager.castUserVote(Mockito.anyInt(), (Recording) Mockito.any(), Mockito.anyString(), Mockito.anyInt())).thenReturn(new VotingResult(VotingMessage.SUCCESS, "success", -1));
		
		
		controller.doPost(stubRequest, stubResponse); 
		 
		String result = injectedStringWriter.getBuffer().toString(); 
		assertTrue("result was " + result, result.contains("\"votes\":-1")); 
	}
	
	@Test
	public void userPostsToInvalidRecording_doesNotTryToCastVote() throws RecordingException 
	{
		when(stubRequest.getParameter("id")).thenReturn("256"); 
		when(stubRequest.getParameter("vote")).thenReturn("up"); 
		when(recordingManager.getRecording(256)).thenReturn(null); 				
		
		controller.doPost(stubRequest, stubResponse); 

		// should not make it to this method call, should quit before then 
		verify(voteManager, never()).castUserVote(1, null, "test", 1); 		
	}
	
	@Test
	public void userPostsToInvalidRecording_writesErrorMessage() throws RecordingException 
	{
		when(stubRequest.getParameter("id")).thenReturn("256"); 
		when(stubRequest.getParameter("vote")).thenReturn("up"); 
		when(recordingManager.getRecording(256)).thenReturn(null); 				
		
		controller.doPost(stubRequest, stubResponse); 

		String result = injectedStringWriter.getBuffer().toString(); 
		assertTrue("result was " + result, result.contains("\"message\":"));  
	}
	
	
	@Test
	public void userPassesEmptyURLString_returnsErrorBean()
	{
		when(stubRequest.getParameter("id")).thenReturn(null); 
		when(stubRequest.getParameter("vote")).thenReturn(null); 
		
		controller.doPost(stubRequest, stubResponse); 
		
		String result = injectedStringWriter.getBuffer().toString(); 
		assertTrue("result was " + result, result.contains("\"message\":")); 
	}
	
	@Test
	public void userPassesURLStringWithNonNumericalId_returnsErrorBean()
	{
		when(stubRequest.getParameter("id")).thenReturn("john"); 
		when(stubRequest.getParameter("vote")).thenReturn("up"); 
		
		controller.doPost(stubRequest, stubResponse); 
		
		String result = injectedStringWriter.getBuffer().toString(); 
		assertTrue("result was " + result, result.contains("\"message\":"));
	}
	
	@Test
	public void userPostsTooManyUpvotes() throws RecordingException
	{		
		when(stubRequest.getParameter("id")).thenReturn("1"); 
		when(stubRequest.getParameter("vote")).thenReturn("up"); 
		
		VotingResult fakeInvalidResult = new VotingResult(VotingMessage.TOO_MANY_VOTES, "too many votes"); 		
		when(voteManager.castUserVote(Mockito.anyInt(), (Recording) Mockito.any(), Mockito.anyString(), Mockito.anyInt())).thenReturn(fakeInvalidResult);

		controller.doPost(stubRequest, stubResponse); 
		
		String result = injectedStringWriter.getBuffer().toString(); 
		assertTrue("result was " + result, result.contains("\"message\":"));
	}
	
	@Test
	public void userCantVoteForSameObjectTwice() throws RecordingException 
	{
		when(stubRequest.getParameter("id")).thenReturn("1"); 
		when(stubRequest.getParameter("vote")).thenReturn("up"); 
		
		VotingResult alreadyVotedResult = new VotingResult(VotingMessage.ALREADY_VOTED, "no voted"); 
		when(voteManager.castUserVote(Mockito.anyInt(), (Recording) Mockito.any(), Mockito.anyString(), Mockito.anyInt())).thenReturn(alreadyVotedResult);
		
		controller.doPost(stubRequest, stubResponse); 
		
		String result = injectedStringWriter.getBuffer().toString(); 
		assertTrue("result was " + result, result.contains("\"message\":"));
	}
}
