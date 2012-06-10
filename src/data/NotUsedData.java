package data;

import java.util.List;

import bean.NotUsed;

public class NotUsedData extends BasicHibernateDataSource 
{
	public List<NotUsed> getAllNotUsed()
	{
		return super.queryDatabaseForList(NotUsed.class, "from NotUsed"); 		
	}
}
