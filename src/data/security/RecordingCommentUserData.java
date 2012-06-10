package data.security;

import java.util.Date;

import bean.RecordingComment;
import bean.security.RecordingCommentUserActivity;
import bean.security.UserActivity;
import data.BasicHibernateDataSource;
import data.common.IUserData;

public class RecordingCommentUserData extends BasicHibernateDataSource implements IUserData 
{

	@Override
	public UserActivity getUserEntryByTargetId(String user, int targetId)
	{
		return  super.queryDatabaseForItem(RecordingCommentUserActivity.class, "from bean.security.RecordingCommentUserActivity where recordingcommentfk=" + targetId + " and user='" + user + "'"); 
	}
	
	@Override
	public void addOrUpdateUserEntry(Date currentTime,  String user, int targetId) 
	{				 		
		RecordingCommentUserActivity activity = super.queryDatabaseForItem(RecordingCommentUserActivity.class, "from RecordingCommentUserActivity where user='" + user + "' and recordingcommentfk=" + targetId);
		
		if(activity == null) 
			activity = new RecordingCommentUserActivity();
				
		activity.setTime(currentTime); 
		activity.setUser(user);
		RecordingComment recordingComment = new RecordingComment(); 
		recordingComment.setId(targetId); 
		activity.setRecordingComment(recordingComment); 
		
		super.saveOrUpdateItem(RecordingCommentUserActivity.class, activity); 		
	}

}
