package domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import domain.security.UserManager;

public class TestUserManager 
{
	UserManager userManager; 
	
	@Before 
	public void setup()
	{
		userManager = new UserManager(); 
	}
	
	@Test
	public void ifUserHasNotBeenAdded_userIsAdded()
	{
		userManager.updateUser(null, "test"); 
		
		assertTrue(userManager.getUserCount() == 1); 
	}
	
	@Test
	public void userHasBeenInactiveForADuration_returnsTrueForInactiveUser()
	{
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(0); 
				
		userManager.updateUser(instance.getTime(), "test");		
		assertTrue(userManager.userHasBeenInactiveForADuration("test", -1));  		
	}
	
	@Test
	public void userHasBeenInactiveForADuration_returnsFalseForActiveUser()
	{
		Calendar instance = Calendar.getInstance();		
				
		userManager.updateUser(instance.getTime(), "test");		
		assertFalse(userManager.userHasBeenInactiveForADuration("test", 3000));  
	}
	
	@Test
	public void userGetsAddedToCommentActivityList()
	{
		userManager = new UserManager(5, 1); 
		
		userManager.updateUserCommentActivity(Calendar.getInstance().getTime(), "test", 1);
		assertTrue(userManager.getNumberOfUsersBeingTrackedForVotes() == 1); 
	}
	
	@Test
	public void userVotesGetIncremented()
	{
		userManager = new UserManager(5, 1); 
		
		userManager.updateUserCommentActivity(Calendar.getInstance().getTime(), "test", 1);
		assertTrue(userManager.getUserVoteCount("test") == 1);
	}
	
	@Test
	public void userVoteCountIsClearedAfterAwhile()
	{		
		userManager = new UserManager(5, 1); 
		
		Date startTime = new Date(); 
		startTime.setTime(0); 
		userManager.updateUserCommentActivity(startTime, "test", 1);
		Date newTime = new Date(); 
		newTime.setTime(6); 
		userManager.resetUserVotesIfAble(newTime, "test"); 
		
		assertTrue(userManager.getNumberOfUsersBeingTrackedForVotes() == 0); 
	}
	
	@Test
	public void userCanOnlyPostSoManyVotesAtOnce()
	{
		userManager = new UserManager(5, 1); 
		
		userManager.updateUserCommentActivity(Calendar.getInstance().getTime(), "test", 1);
		userManager.updateUserCommentActivity(Calendar.getInstance().getTime(), "test", 1);
		assertTrue("vote count was " + userManager.getUserVoteCount("test"), userManager.getUserVoteCount("test") == 1);
	}
	
	@Test
	public void getUserVoteCount_NonExistentUser_doesntThrowNPE()
	{
		userManager = new UserManager(5, 1); 
		userManager.getUserVoteCount("foo"); 
		
		assertTrue(true);
	}
}
