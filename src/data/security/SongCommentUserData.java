package data.security;

import java.util.Date;

import bean.SongComment;
import bean.security.SongCommentUserActivity;
import bean.security.UserActivity;
import data.BasicHibernateDataSource;
import data.common.IUserData;

public class SongCommentUserData extends BasicHibernateDataSource implements IUserData 
{
	@Override
	public UserActivity getUserEntryByTargetId(String user, int targetId) 
	{
		return super.queryDatabaseForItem(SongCommentUserActivity.class, "from SongCommentUserActivity where songcommentfk = " + targetId + " and user='" + user + "'"); 
	}

	@Override
	public void addOrUpdateUserEntry(Date currentTime, String user, int targetId) 
	{
		SongCommentUserActivity activity = super.queryDatabaseForItem(SongCommentUserActivity.class, "from SongCommentUserActivity where user='" + user + "' and songcommentfk=" + targetId);
		
		if(activity == null) 
			activity = new SongCommentUserActivity();		
				
		SongComment comment = new SongComment(); 
		comment.setId(targetId); 
		
		activity.setUser(user); 
		activity.setTime(currentTime); 
		activity.setSongComment(comment); 
		
		super.saveOrUpdateItem(SongCommentUserActivity.class, activity); 
	}

}
