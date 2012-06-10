package domain.security;

import java.util.Calendar;

import bean.security.UserActivity;
import data.common.IUserData;

public class UserActivityManager 
{
	private IUserData userDataSource; 
	
	public UserActivityManager(IUserData userData)
	{
		userDataSource = userData; 
	}
	
	public boolean userHasVotedForObjectBefore(String user, int objectId)
	{
		UserActivity lastActivity = userDataSource.getUserEntryByTargetId(user, objectId); 
		
		return lastActivity != null; 
	}	
	
	public void updateUserActivityEntry(String user, int objectId)
	{
		userDataSource.addOrUpdateUserEntry(Calendar.getInstance().getTime(), user, objectId); 
	}
}
