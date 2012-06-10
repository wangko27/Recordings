package domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import bean.Recording;
import bean.Song;
import bean.SongInstance;
import data.common.ISongInstanceData;
import domain.common.ICustomGraphRequestValidator;
import domain.common.IGraphDataAccessor;
import exception.RecordingException;

public class SongInstanceManager implements IGraphDataAccessor, ICustomGraphRequestValidator 
{
	private ISongInstanceData dataSource;
	
	public SongInstanceManager(ISongInstanceData dataSource)
	{
		this.dataSource = dataSource; 
	}
	
	public SongInstance getSongInstance( int id )
	{
		return dataSource.getSongInstance(id);
	}
	
	public List<SongInstance> getAllSongInstances()
	{
		return dataSource.getAllSongInstances();
	}
	
	public void saveOrUpdateSongInstance( SongInstance songInstance ) throws Exception
	{
		try
		{
			dataSource.saveOrUpdate(songInstance);
		}
		catch( Exception e )
		{
			throw new Exception(e);
		}
	}
	
	public void deleteSongInstance( SongInstance songInstance )
	{
		dataSource.delete(songInstance);
	}

	@Override
	public Map<Comparable<?>, Integer> groupItemsByCount(String fieldName)
			throws RecordingException {

		return dataSource.groupSongsByCount(convertRawFieldNameToActualDatabaseField(fieldName,false));
	}

	@Override
	public Collection<?> getAllItems() 
	{
		return dataSource.getAllSongInstances(); 
	}
	
	
	
	@Override
	public Class<?> getDataClass() {
		return SongInstance.class;
	}

	@Override
	public Map<Comparable<?>, Integer> groupItemsByCommonValue(
			String commonValueColumn, Comparable<?> commonValue,
			String variableColumn) throws RecordingException {
		
		String actualCommonValueColumn = convertRawFieldNameToActualDatabaseField(commonValueColumn, false);
		String actualCommonValueSpecifier = convertRawFieldNameToActualDatabaseField(commonValueColumn, true);
		Comparable<?> actualCommonValue = getHQLType(commonValue); 
		String actualVariableColumn = convertRawFieldNameToActualDatabaseField(variableColumn, false); 
		
		return dataSource.groupSongsByCommonValue(actualCommonValueColumn, actualCommonValueSpecifier, actualCommonValue, actualVariableColumn); 
	} 

	public Map<Comparable<?>, Integer> getNumberOfNewSongsPerYear()
	{
		return dataSource.getNumberOfNewSongsPerYear(); 
	}
	
	@Override
	public String dataClassString() 
	{
		return "Song" ;
	}
	
	private Comparable<?> getHQLType(Comparable<?> value)
	{
		if(value instanceof Number)
			return value; 
		else
			return value.toString(); 
	}
	
	private boolean isRecordingField(String fieldName)
	{
		String fieldNameLowercase = fieldName.toLowerCase(); 
		return (fieldNameLowercase.equals("name") || fieldNameLowercase.equals("sublocation") || fieldNameLowercase.equals("month") || fieldNameLowercase.equals("year") ||  fieldNameLowercase.equals("venue") || fieldNameLowercase.equals("quality") || fieldNameLowercase.equals("city") || fieldNameLowercase.equals("format") || fieldNameLowercase.equals("media") || fieldNameLowercase.equals("recordingtype") || fieldNameLowercase.equals("country")); 
	}
	
	private boolean isSongField(String fieldName)
	{
		String fieldNameLowercase = fieldName.toLowerCase(); 
		return fieldNameLowercase.equals("value") || fieldNameLowercase.equals("alias") || fieldNameLowercase.equals("votes"); 
	}

	@Override
	public boolean validateField(String fieldName) 
	{
		String fieldNameLowercase = fieldName.toLowerCase(); 
		
		if(isRecordingField(fieldName))
		{
			return true; 
		}			 
		else if (isSongField(fieldName))
		{
			return true; 
		}		
		else if (fieldNameLowercase.equals("tracklisting") || fieldNameLowercase.equals("section"))
			return true; 
			
		return false;  
	}

	@SuppressWarnings("unchecked") 
	@Override
	public Class<? extends Comparable<?>> getExpectedType(String fieldName) 
	{
		try
		{
			fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1); 
			
			if(isRecordingField(fieldName))
			{
				return (Class<? extends Comparable<?>>) Recording.class.getMethod("get" + fieldName).getReturnType(); 
			}
			else if (isSongField(fieldName))
			{				
				return (Class<? extends Comparable<?>>) Song.class.getMethod("get" + fieldName).getReturnType(); 
			}

			return (Class<? extends Comparable<?>>) SongInstance.class.getMethod("get" + fieldName).getReturnType(); 
		}
		catch(Exception exc)
		{
			exc.printStackTrace(); 
			return null; 
		}
	}

	/**
	 * NOTE THERE IS NO REASON FOR THIS TO BE HERE NOW
	 * This was an ugly hack, now we can replace this with the SmartSongInstance view
	 * instead of leaking database information up in domain.  
	 * 
	 * TODO get rid of the need for this method 
	 */
	private String convertRawFieldNameToActualDatabaseField(String fieldName, boolean useValue)
	{
		String fieldNameLowercase = fieldName.toLowerCase(); 
		String actualDatabaseField = null; 
		
		if(fieldNameLowercase.equals("venue") || fieldNameLowercase.equals("quality") || fieldNameLowercase.equals("city") || fieldNameLowercase.equals("format") || fieldNameLowercase.equals("media") || fieldNameLowercase.equals("recordingtype") || fieldNameLowercase.equals("country"))
		{
			actualDatabaseField = "recording." + fieldNameLowercase; 
			if(useValue) actualDatabaseField +=  ".value";			
		}			 
		else if (fieldNameLowercase.equals("year") || fieldNameLowercase.equals("month") || fieldNameLowercase.equals("sublocation"))
		{
			actualDatabaseField = "recording." + fieldNameLowercase; 
		}		
		else if (fieldNameLowercase.equals("value") || fieldNameLowercase.equals("alias"))
		{ 
			actualDatabaseField = "song." + fieldNameLowercase; 
			if(useValue) actualDatabaseField += ".value";			
		}
		else if (fieldNameLowercase.equals("votes"))
		{
			actualDatabaseField = "song.upvotes - songinstance.song.downvotes";			
		}
		else
		{
			actualDatabaseField =fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1); 
		}
		
		return actualDatabaseField; 
		
	}
	
	public Object getDataSourceToInvokeGetMethodsOn(Object instance, String fieldName)
	{
		if(!(instance instanceof SongInstance))
			throw new IllegalArgumentException("type was " + instance.getClass()); 
		
		SongInstance sInstance = (SongInstance) instance; 
		
		if(isRecordingField(fieldName))
			return  sInstance.getRecording(); 
		else if (isSongField(fieldName))
			return sInstance.getSong(); 
		else
			return instance; 
	}
}
