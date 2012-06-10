
package domain.security;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import bean.security.UserActivity;
import data.common.IUserData;

public class TestUserActivityManager 
{
	private IUserData stubUserData; 
	private UserActivityManager manager; 
	private UserActivity stubBean; 
	
	@Before 
	public void setup()
	{
		stubUserData = mock(IUserData.class);
		manager = new UserActivityManager(stubUserData); 
		stubBean = mock(UserActivity.class); 
	}
	
	@Test
	public void whenUserVotesOnObject_nextVoteDetectsThatUserHasVoted()
	{
		manager.updateUserActivityEntry("test", 1);
		when(stubUserData.getUserEntryByTargetId("test",1)).thenReturn(stubBean); 
		assertTrue(manager.userHasVotedForObjectBefore("test", 1));  
	}
	
	@Test
	public void updateUserActivity_makesACallToUpdateMethodOnDataSource()
	{
		manager.updateUserActivityEntry("test", 1); 
		verify(stubUserData).addOrUpdateUserEntry((Date) Mockito.anyObject(), Mockito.anyString(), Mockito.anyInt()); 
	}
}
