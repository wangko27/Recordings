package domain;

import java.util.Collection;

import bean.RecentChange;
import data.RecentChangeData;

public class RecentChangesManager 
{
	private RecentChangeData recentChangeData; 
	
	public RecentChangesManager()
	{
		recentChangeData = new RecentChangeData(); 
	}
	
	public RecentChangesManager(RecentChangeData data)
	{
		recentChangeData = data; 
	}

	public Collection<RecentChange> getMostRecentChanges(int count)
	{		
		return recentChangeData.getTopRecentChanges(count); 
	}
}
