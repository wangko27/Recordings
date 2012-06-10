package data;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import bean.SongInstance;
import data.common.ISongInstanceData;
import exception.RecordingException;

public class SongInstanceData extends BasicHibernateDataSource implements ISongInstanceData 
{
	@Override 
	public void saveOrUpdate( SongInstance songInstance ) throws Exception
	{
		try
		{
			super.saveOrUpdateItem(SongInstance.class, songInstance); 
		}
		catch( Exception e )
		{
			throw new Exception(e);
		}
	}
	
	@Override
	public void delete(SongInstance songInstance) {
		super.delete(songInstance); 
	}
	
	@Override
	public List<SongInstance> getAllSongInstances() 
	{
		return (List<SongInstance>) super.queryDatabaseForList(SongInstance.class, "from SongInstance"); 
	}
	
	@Override
	public SongInstance getSongInstance(int id) 
	{
		String query = "from SongInstance where id = " + id; 
		
		return super.queryDatabaseForItem(SongInstance.class, query); 	
	}

	private String fieldToHQL(String fieldName)
	{
		return "songinstance." + fieldName; 
	}
	
	@Override
	public Map<Comparable<?>, Integer> groupSongsByCount(String fieldName)
	{
		// LinkedHashMap guarantees elements appear in the order they were inserted
		Map<Comparable<?>, Integer> results = new LinkedHashMap<Comparable<?>, Integer>(); 
		    
		String hql = "select " + fieldToHQL(fieldName) + ", count(*) from SongInstance as songinstance group by " + fieldToHQL(fieldName); 
		
		List<Object[]> queryResults = super.queryDatabaseForList(Object[].class, hql); 		
		for(Object[] obj : queryResults)
		{
			results.put( (Comparable<?>) obj[0], new Integer( ((Long)obj[1]).intValue()));
		}

		return results; 
	}
	
	@Override
	public Map<Comparable<?>, Integer> groupSongsByCommonValue(
			String commonValueColumn,String commonValueSpecifier,  Comparable<?> commonValue,
			String variableColumn)
			throws RecordingException 
	{
		try
		{
			Map<Comparable<?>, Integer> results = new LinkedHashMap<Comparable<?>, Integer>(); 		
			String hql = "select " + fieldToHQL(variableColumn) + ", count(*) from SongInstance as songinstance where songinstance." + commonValueSpecifier + " = :commonValue group by " + fieldToHQL(variableColumn);		
			
			Map<String, Object> parameters= new HashMap<String, Object>(); 
			parameters.put("commonValue", commonValue); 
			List<Object[]> queryResults = super.queryDatabaseForListUsingParameters(Object[].class, hql, parameters); 
			
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

	@Override
	public Map<Comparable<?>, Integer> getNumberOfNewSongsPerYear()
	{
		String sql = "select year, cast(count(*) as unsigned) as frequency from (select min(recording.year) as year, song.value from recording, lkpsong as song, songinstance where songinstance.recordingfk = recording.id and songinstance.songfk = song.id group by song.value order by recording.year) as songappearances group by year;";
		
		List<Object[]> queryResults = super.queryDatabaseForListUsingSQL(Object[].class, sql);
		Map<Comparable<?>, Integer> results = new HashMap<Comparable<?>, Integer>(); 
		for(Object[] row : queryResults)
		{			
			results.put( (Comparable<?>) row[0], new Integer( ((BigInteger)row[1]).intValue())); 
		}
		
		return results; 
	}

}
