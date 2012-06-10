package domain;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import bean.Recording;
import bean.RecordingSearchBean;
import bean.RecordingType;
import data.SimpleEntityData;
import data.common.IRecordingData;
import domain.common.IGraphDataAccessor;
import exception.RecordingException;

public class RecordingManager implements IGraphDataAccessor 
{
	private IRecordingData recordingData;
	
	public RecordingManager(IRecordingData recordingData)
	{
		this.recordingData = recordingData;  
	}
	
	public Collection<Recording> getAllRecordings( RecordingSearchBean searchBean )
	{
		return recordingData.getAllRecordings(searchBean); 
	}
	
	public Collection<Recording> getAllRecordings()
	{
		return recordingData.getAllRecordings(); 
	}
	
	public Collection<Recording> getAllRecordingsForTrade()
	{
		return recordingData.getAllRecordingsForTrade(); 
	}
	
	public void saveOrUpdate( Recording recording )
	{
		recordingData.saveOrUpdate(recording);
	}
	
	public void delete( Recording recording )
	{
		if( recording != null )
			recordingData.delete(recording);
	}
	
	public Recording createBlankRecording()
	{
		SimpleEntityData simpleEntityData = new SimpleEntityData();
		RecordingType recordingType = (RecordingType) simpleEntityData.getSimpleBean("Show", RecordingType.class);
		Recording recording = new Recording();
		recording.setRecordingType(recordingType);
		recording.setDateAdded(Calendar.getInstance().getTime()); 
		recording.setJon(0);
		
		recordingData.saveOrUpdate(recording);
		
		return recording;
	}
	
	public List<Recording> getRecordingsAssociatedWithSong( int songId )
	{
		return recordingData.getRecordingsAssociatedWithSong(songId);
	}
	
	/**
	 * same as getAllRecordings(), but returns a Collection<?> instead of 
	 * specifically a collection of recording beans. 
	 */
	public Collection<?> getAllItems()
	{
		return recordingData.getAllRecordings(); 
	}
	
	public Recording getRecording( int id )
	{
		return recordingData.getRecording(id);
	}
	
	/**
	 * this method takes a list of Recording objects and converts it into a map, where each Key represents
	 * a different value of a column and each Value represents the number of Recordings that have that value
	 * for the column. 
	 *  
	 * @param fieldName the column name. Assumes that RecordingBean has a "get___" method 
	 * @param recordings a collection of Recording objects. If null, queries the database instead 
	 * @return A Map representing each value of the Column and the number of Recordings that have that value
	 * @throws RecordingException 
	 */
	public Map<Comparable<?>, Integer> groupRecordingsByCount(String fieldName, Collection<Recording> recordings) throws RecordingException
	{
		return recordingData.groupRecordingsByCount(fieldName, recordings); 
	}
	
	/**
	 * implements the IGraphDataAcessor interface. Same as groupRecordingsByCount() but makes a call
	 * to getAllRecordings() by default
	 * @throws RecordingException 
	 * 
	 */
	public Map<Comparable<?>, Integer> groupItemsByCount(String fieldName) throws RecordingException
	{
		return recordingData.groupRecordingsByCount(fieldName, getAllRecordings()); 
	}

	@Override	
	public Class<?> getDataClass()
	{
		return Recording.class; 
	} 

	@Override 
	public Map<Comparable<?>, Integer> groupItemsByCommonValue(String commonValueColumn, Comparable<?> commonValue, String variableColumn) throws RecordingException 
	{	
		return recordingData.groupRecordingsByCommonValue(commonValueColumn, commonValue, variableColumn, getAllRecordings()); 
	}

	@Override
	public String dataClassString() 
	{
		return "Recording"; 
	}
}
