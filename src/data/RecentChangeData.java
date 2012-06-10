package data;

import java.util.Collection;


import bean.RecentChange;

public class RecentChangeData extends BasicHibernateDataSource 
{
	public Collection<RecentChange> getTopRecentChanges(int count)
	{
		// don't return comments with the text SKIP in front of them
		String query = "from RecentChange where summary not like 'SKIP%'";
		return this.queryDatabaseForList(RecentChange.class, query, count); 
	}
	
}
