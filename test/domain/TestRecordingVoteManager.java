package domain;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import bean.Recording;
import bean.VotingResult;
import bean.VotingResult.VotingMessage;
import domain.security.UserManager;
import exception.RecordingException;

public class TestRecordingVoteManager
{
	private RecordingVoteManager manager; 
	private RecordingManager recordingManager; 
	private UserManager userManager; 
	private Recording stubRecording; 
	
	@Before 
	public void setup()
	{
		recordingManager = mock(RecordingManager.class); 
		userManager = mock(UserManager.class); 
		stubRecording = mock(Recording.class); 
		
		when(userManager.updateUserCommentActivity((Date)Mockito.any(), Mockito.anyString(), Mockito.anyInt())).thenReturn(new VotingResult(VotingMessage.SUCCESS, "")); 
		
		manager = new RecordingVoteManager(recordingManager, userManager); 
	}
	
	@Test
	public void voteForObject_objectGetsUpVoteCountChanged() throws RecordingException
	{ 
		manager.castUserVote(1, stubRecording, "test", 1);
		verify(stubRecording).setUpvotes(1); 				
	}
	
	@Test
	public void voteForObject_objectGetsDownVoteCountChanged() throws RecordingException
	{ 
		manager.castUserVote(-1, stubRecording, "test", 1);
		verify(stubRecording).setDownvotes(1); 				
	}
}
