package data.common;

import java.util.Date;

import bean.security.UserActivity;

public interface IUserData 
{
	public UserActivity getUserEntryByTargetId(String user, int targetId);
	public void addOrUpdateUserEntry(Date currentTime, String user, int targetId); 
}
