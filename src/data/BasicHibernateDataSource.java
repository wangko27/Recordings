package data;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
 
public abstract class BasicHibernateDataSource 
{
	protected <T> List<T> queryDatabaseForListUsingParameters(Class<T> expectedType, String hql, Map<String, Object> parameters) throws Exception 
	{
		Session session = DataHelper.getOpenSession(); 
		
		Query query = session.createQuery(hql);
		for(Entry<String, Object> parameter : parameters.entrySet())
		{
			query.setParameter(parameter.getKey(),parameter.getValue()); 
		}

		@SuppressWarnings("unchecked")
		List<T> results = (List<T>) query.list(); 
		return results; 
	}
	
	protected void delete(Object obj)
	{
		Session session = DataHelper.getOpenSession(); 
		session.delete(obj); 
	}
	
	protected <T> List<T> queryDatabaseForList(Class<T> expectedType, String hql)
	{
		Session session =DataHelper.getOpenSession(); 
		
		Query query = session.createQuery(hql);
		
		@SuppressWarnings("unchecked")
		List<T> resultList =  (List<T>)query.list(); 
		
		
		return resultList; 
	}
	
    protected <T> List<T> queryDatabaseForList(Class<T> expectedType, String hql, int maxResults)
	{
		Session session = DataHelper.getOpenSession(); 
		
		Query query = session.createQuery(hql);
		query.setMaxResults(maxResults); 
		
		@SuppressWarnings("unchecked")
		List<T> resultList =  (List<T>)query.list(); 
		
		return resultList; 
	}
	
	protected <T> List<T> queryDatabaseForListUsingSQL(Class<T> expectedType, String sql)
	{
		Session session = DataHelper.getOpenSession(); 
		
		Query query = session.createSQLQuery(sql);
		
		@SuppressWarnings("unchecked")
		List<T> resultList =  (List<T>)query.list(); 
		
		return resultList; 
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T queryDatabaseForItem(Class<T> expectedType, String hql)
	{
		T result = null;
		Session session = DataHelper.getOpenSession(); 
		
		Query query = session.createQuery(hql);
		
		result = (T)query.uniqueResult();
		
		return result;
	}
	
	protected <T> void saveOrUpdateItem(Class<T> typeOfObjectToSave, T objectToSave)
	{
		Session session = DataHelper.getOpenSession();  
		session.saveOrUpdate(objectToSave); 
	}
	
	protected Long saveItem(Object bean)
	{
		Session session = DataHelper.getOpenSession(); 
		
		return new Long( ((Integer)session.save(bean)).intValue());
	}
}
