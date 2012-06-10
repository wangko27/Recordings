package data.common;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import bean.Recording;
import bean.RecordingSearchBean;
import exception.RecordingException;

public interface IRecordingData
{	
	Recording getRecording(int id);
	
	Collection<Recording> getAllRecordings();
	Collection<Recording> getAllRecordingsForTrade();
	Collection<Recording> getAllRecordings( RecordingSearchBean searchBean );
	void saveOrUpdate( Recording recording );
	
	public List<Recording> getRecordingsAssociatedWithSong( int songId );
	
	/**
	 * Creates a map with each value of a column being the key and the number of recordings
	 * that have that value as the value
	 * 
	 * @param fieldName name of the column to search for 
	 * @param recordings optional collection to search 
	 * @return a map representing the number of recordings for a given value of the column 
	 * @throws RecordingException 
	 */
	public  Map<Comparable<?>, Integer> groupRecordingsByCount(String fieldName, Collection<Recording> recordings) throws RecordingException ;
	
	/**
	 * same as groupRecordingsByCount, but only uses recordings where the value of the column specified matches the value 
	 * 
	 * @param commonValueColumn the column name to use when filtering the recordings
	 * @param commonValue the value of the column that the recording must have in order to be in the results
	 * @param variableColumn the column that the map will be generated for
	 * @param recordings
	 * @return
	 * @throws RecordingException
	 */
	public Map<Comparable<?>, Integer> groupRecordingsByCommonValue(String commonValueColumn, Comparable<?> commonValue, String variableColumn, Collection<Recording> recordings) throws RecordingException;

	void delete(Recording recording);		
}
