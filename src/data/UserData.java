package data;

import java.util.Date;

import bean.security.UserActivity;
import data.common.IUserData;

public class UserData implements IUserData
{

	public UserData()
	{
		throw new IllegalArgumentException("oops"); 
	}

	@Override
	public UserActivity getUserEntryByTargetId(String user, int targetId) 
	{
		return null;
	}

	@Override
	public void addOrUpdateUserEntry(Date currentTime, String user, int targetId) {		
	}
}
