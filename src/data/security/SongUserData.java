package data.security;

import java.util.Date;

import bean.Song;
import bean.security.SongUserActivity;
import bean.security.UserActivity;
import data.BasicHibernateDataSource;
import data.common.IUserData;

public class SongUserData extends BasicHibernateDataSource implements IUserData
{

	@Override
	public UserActivity getUserEntryByTargetId(String user, int targetId) 
	{		
		return super.queryDatabaseForItem(SongUserActivity.class, "from SongUserActivity where songfk = " + targetId + " and user='" + user + "'");						
	}

	@Override
	public void addOrUpdateUserEntry(Date currentTime, String user, int targetId)
	{	
		SongUserActivity activity = super.queryDatabaseForItem(SongUserActivity.class, "from SongUserActivity where user='" + user + "' and songfk=" + targetId);
		
		if(activity == null) 
			activity = new SongUserActivity();		
				
		Song song = new Song(); 
		song.setId(targetId); 
		
		activity.setUser(user); 
		activity.setTime(currentTime); 
		activity.setSong(song); 
		
		super.saveOrUpdateItem(SongUserActivity.class, activity);
	}

}
