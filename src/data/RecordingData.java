package data;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;

import bean.Recording;
import bean.RecordingSearchBean;
import data.common.IRecordingData;
import exception.RecordingException;

public class RecordingData extends BasicHibernateDataSource implements IRecordingData
{ 
	@Override 
	public void saveOrUpdate( Recording recording )
	{
		super.saveOrUpdateItem(Recording.class, recording); 
	}
	
	@Override 
	public void delete( Recording recording )
	{
		super.delete(recording); 
	}
	
	@Override
	public List<Recording> getAllRecordings( RecordingSearchBean searchBean ) 
	{
		List<Recording> recordings = null;
		Session session = DataHelper.getOpenSession(); 
		
		Recording recording = searchBean.getRecording();
		Example recordingExample = Example.create(recording).excludeZeroes();
		
		Criteria crit = session.createCriteria(Recording.class);
		
        if( recording.getCountry() != null )
        	crit.createCriteria("country").add(Example.create(recording.getCountry()));
        if( recording.getCity() != null )
        	crit.createCriteria("city").add(Example.create(recording.getCity()));
        if( recording.getVenue() != null )
        	crit.createCriteria("venue").add(Example.create(recording.getVenue()));
        if( recording.getRecordingType() != null )
        	crit.createCriteria("recordingType").add(Example.create(recording.getRecordingType()));
        if( recording.getQuality() != null )
        	crit.createCriteria("quality").add(Example.create(recording.getQuality()));
        
        crit.add(recordingExample);
        
        crit.addOrder(Order.desc("year"))
            .addOrder(Order.desc("month"))
            .addOrder(Order.desc("date"));
        
        recordings = (List<Recording>) crit.list();
		
		return recordings;
	}
	
	@Override
	public List<Recording> getAllRecordings() 
	{
		return super.queryDatabaseForList(Recording.class, "from Recording"); 		
	}
	
	@Override
	public List<Recording> getAllRecordingsForTrade() 
	{
		return super.queryDatabaseForList(Recording.class, "from Recording where jon = 1 order by recordingType,year,month,date"); 		
	}

	@Override
	public Recording getRecording(int id) 
	{
		return super.queryDatabaseForItem(Recording.class, "from Recording where id = " + id); 		
	}
	
	@Override
	public List<Recording> getRecordingsAssociatedWithSong( int songId )
	{
		List<Recording> recordings = null;
		Session session = DataHelper.getOpenSession(); 
		
		String hql = "from Recording r " +
					 "where r.id in (select si.recording.id " +
					                 "from SongInstance si " +
					                 "where si.song.id = " + songId + ")" +
					 "order by r.year, r.month, r.date";
		
		Query query = session.createQuery(hql);
		
		recordings = (List<Recording>)query.list();
		
		return recordings;
	}

	private String fieldToHQL(String fieldName, boolean useValue)
	{
		fieldName = fieldName.substring(0, 1).toLowerCase() +  fieldName.substring(1);  
		
		if(fieldName.equals("venue") || fieldName.equals("quality") || fieldName.equals("city") || fieldName.equals("format") || fieldName.equals("media") || fieldName.equals("recordingType") || fieldName.equals("country") )
		{
			if(useValue) 
				return "recording." + fieldName + ".value";
			else
				return "recording." + fieldName; 
		}			 
		else if (fieldName.equals("votes"))
		{
			return "recording.upvotes - recording.downvotes"; 
		}
		
		return fieldName; 
	}
	
	private Object getHQLType(String fieldName, Comparable<?> value)
	{
		fieldName = fieldName.toLowerCase(); 
		
		if(fieldName.equals("venue") || fieldName.equals("quality") || fieldName.equals("city") || fieldName.equals("format") || fieldName.equals("media") || fieldName.equals("recordingtype") || fieldName.equals("country") )
		{
			return value.toString(); 
		}			 
		
		return value; 
	}
	
	@Override
	public Map<Comparable<?>, Integer> groupRecordingsByCount(String fieldName,
			Collection<Recording> recordings) throws RecordingException {

		Map<Comparable<?>, Integer> results = new LinkedHashMap<Comparable<?>, Integer>(); 	
		String hql = "select " + fieldToHQL(fieldName, false) + ", count(*) from Recording as recording group by " + fieldToHQL(fieldName, true); 
		
		List<Object[]> queryResults = super.queryDatabaseForList(Object[].class, hql); 
		
		for(Object[] row : queryResults)
		{ 
			results.put( (Comparable<?>) row[0], new Integer( ((Long)row[1]).intValue())); 
		}	
		
		return results; 
	}
	
	

	@Override
	public Map<Comparable<?>, Integer> groupRecordingsByCommonValue(
			String commonValueColumn, Comparable<?> commonValue,
			String variableColumn, Collection<Recording> recordings)
			throws RecordingException 
	{
		try
		{
			String hql = "select " + fieldToHQL(variableColumn, false) + ", count(*) from Recording as recording where " + fieldToHQL(commonValueColumn, true) + " = :commonValue group by " + fieldToHQL(variableColumn, true);						
			Map<String, Object> queryParameters = new HashMap<String, Object>(); 
			queryParameters.put("commonValue",  getHQLType(commonValueColumn, commonValue)); 				
			
			List<Object[]> queryResults = super.queryDatabaseForListUsingParameters(Object[].class, hql, queryParameters);						
			Map<Comparable<?>, Integer> results = new LinkedHashMap<Comparable<?>, Integer>(); 
			for(Object[] row : queryResults)
			{ 
				results.put( (Comparable<?>) row[0], new Integer( ((Long)row[1]).intValue())); 
			}
			
			return results; 
		}
		catch(Exception exc)
		{
			throw new RecordingException(exc); 
		}
	}
}