package data.security;

import java.util.Date;

import bean.Recording;
import bean.security.RecordingUserActivity;
import bean.security.UserActivity;
import data.BasicHibernateDataSource;
import data.common.IUserData;

public class RecordingUserData extends BasicHibernateDataSource implements IUserData
{

	@Override
	public UserActivity getUserEntryByTargetId(String user, int targetId) 
	{
		return  super.queryDatabaseForItem(RecordingUserActivity.class, "from RecordingUserActivity where recordingfk=" + targetId + " and user='" + user + "'");
	}

	@Override
	public void addOrUpdateUserEntry(Date currentTime, String user, int targetId) 
	{
		RecordingUserActivity activity = super.queryDatabaseForItem(RecordingUserActivity.class, "from RecordingUserActivity where user='" + user + "' and recordingfk=" + targetId);
		
		if(activity == null) 
			activity = new RecordingUserActivity();
				
		activity.setTime(currentTime); 
		activity.setUser(user);
		Recording recording = new Recording(); 
		recording.setId(targetId); 
		activity.setRecording(recording); 
		
		super.saveOrUpdateItem(RecordingUserActivity.class, activity); 		
	}

}
