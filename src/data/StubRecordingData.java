package data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import bean.Format;
import bean.Media;
import bean.Quality;
import bean.Recording;
import bean.RecordingSearchBean;
import bean.RecordingType;
import bean.Venue;
import data.common.IRecordingData;
import exception.RecordingException;

public class StubRecordingData implements IRecordingData
{
	/* Idea:
	 * 
	 * When retrieving a test recording bean the id serves as the main identifier (primary key)
	 * This way we can have a set of numbers (keys) that we can use to ensure that our
	 * data is consistent (we can hard-code our unit tests for this data)
	 */
	
	
	@Override
	public Collection<Recording> getAllRecordings() 
	{
		Collection<Recording> results = new ArrayList<Recording>(); 
		
		for( int x = 0; x < 100; x++ )
		{
			Recording testRecording = getRecording(x);
			results.add(testRecording); 
		}
		
		return results; 
	}
	
	public Recording getRecording( int id )
	{
		Recording testRecording = new Recording();
		
		testRecording.setId(id);
		testRecording.setVenue(new Venue(ValueGenerator.getSimpleTestBean("venue", id))); 
		testRecording.setDate(ValueGenerator.getDate(id));
		testRecording.setMonth(ValueGenerator.getMonth(id)); 
		testRecording.setYear(ValueGenerator.getYear(id));
		testRecording.setLink(ValueGenerator.getSimpleTestBean("link", id).getValue());
		testRecording.setSublocation(ValueGenerator.getSimpleTestBean("sublocation", id).getValue());
		testRecording.setCity(ValueGenerator.getCity(id)); 
		testRecording.setCountry(ValueGenerator.getCountry(id)); 
		testRecording.setMedia(new Media(ValueGenerator.getSimpleTestBean("media", id)));
		testRecording.setFormat(new Format(ValueGenerator.getSimpleTestBean("formatType", id)));
		testRecording.setQuality(new Quality(ValueGenerator.getSimpleTestBean("quality", id)));
		testRecording.setRecordingType(new RecordingType(ValueGenerator.getSimpleTestBean("recordingType", id)));
		
		return testRecording;
	}
	
	public Map<Comparable<?>, Integer> groupRecordingsByCommonValue(String commonValueColumn, Comparable<?> commonValue, String variableColumn, Collection<Recording> recordings) throws RecordingException
	{
		if(recordings == null) 
			throw new NotImplementedException(); 
		
		// LinkedHashMap guarantees elements appear in the order they were inserted
		Map<Comparable<?>, Integer> results = new LinkedHashMap<Comparable<?>, Integer>(); 

		try
		{
			Method method = Recording.class.getMethod("get" + variableColumn); 
			Method commonValueMethod = Recording.class.getMethod("get" + commonValueColumn); 
			
			for(Recording bean : recordings)
			{
				Comparable<?> result = (Comparable<?>) method.invoke(bean);
				Comparable<?> beanCommonValue = (Comparable<?>) commonValueMethod.invoke(bean); 
				
				if(beanCommonValue == null || !beanCommonValue.equals(commonValue)) continue; 
				if(result == null) continue; 
				
				if(results.containsKey(result))
					results.put(result, results.get(result) + 1); 
				else
					results.put(result, 1); 
			}
		}
		catch(Exception exc)
		{
			exc.printStackTrace(); 
			return null; 
		}
		
		return results; 
	}
	
	/**
	 * this method takes a list of Recording objects and converts it into a map, where each Key represents
	 * a different value of a column and each Value represents the number of Recordings that have that value
	 * for the column.
	 * 
	 * @param fieldName the name of the column to match values in each RecordingBean
	 * @param recordings an optional collection of recordings to search. If null, searches the database 
	 */
	public  Map<Comparable<?>, Integer> groupRecordingsByCount(String fieldName, Collection<Recording> recordings)
	{
		if(recordings == null)
			throw new NotImplementedException(); 
		
		// LinkedHashMap guarantees elements appear in the order they were inserted
		Map<Comparable<?>, Integer> results = new LinkedHashMap<Comparable<?>, Integer>(); 
		    
		try
		{
			Method method = Recording.class.getMethod("get" + fieldName); 
			for(Recording bean : recordings)
			{
				Comparable<?> result = (Comparable<?>) method.invoke(bean);
				
				if(result == null) continue; 
				
				if(results.containsKey(result))
					results.put(result, results.get(result) + 1); 
				else
					results.put(result, 1); 
			}
		}
		catch(Exception exc)
		{
			exc.printStackTrace(); 
			return null; 
		}
		
		return results; 
	}

	@Override
	public void saveOrUpdate(Recording recording) {
		
	}

	@Override
	public Collection<Recording> getAllRecordings(RecordingSearchBean searchBean) {
		return null;
	}

	@Override
	public List<Recording> getRecordingsAssociatedWithSong(int songId) {
		return null;
	}

	@Override
	public void delete(Recording recording) {
		
	}

	@Override
	public Collection<Recording> getAllRecordingsForTrade() {
		// TODO Auto-generated method stub
		return null;
	}
}
