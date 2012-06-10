package data.common;

import java.util.List;
import java.util.Map;

import bean.SongInstance;
import exception.RecordingException;

public interface ISongInstanceData {
	
	public void saveOrUpdate(SongInstance songInstance) throws Exception;
	public void delete(SongInstance songInstance);
	public List<SongInstance> getAllSongInstances();
	public SongInstance getSongInstance(int id);
	public Map<Comparable<?>, Integer> groupSongsByCount(String fieldName); 

	public Map<Comparable<?>, Integer> groupSongsByCommonValue(
			String commonValueColumn,String commonValueSpecifier,  Comparable<?> commonValue,
			String variableColumn)
			throws RecordingException;
	
	public Map<Comparable<?>, Integer> getNumberOfNewSongsPerYear(); 
}
