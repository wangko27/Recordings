package data.db.SongInstanceData;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import bean.City;
import bean.SongInstance;
import data.HibernateTest;
import data.SongInstanceData;
import data.common.ISongInstanceData;
import exception.RecordingException;

public class TestSongInstanceData extends HibernateTest {

	private ISongInstanceData data = new SongInstanceData();
	
	@Test
	public void testGetSongInstance()
	{
		SongInstance songInstance = data.getSongInstance(34);
		
		assertNotNull(songInstance);
		assertNotNull(songInstance.getRecording());
		assertNotNull(songInstance.getSong());
	}
	
	@Test
	public void testGetAllSongInstances()
	{
		List<SongInstance> songInstances = data.getAllSongInstances();
		
		assertNotNull(songInstances);
		assertTrue(!songInstances.isEmpty());
	}
	
	@Test
	public void groupItemsByCount_groupsBySongName()
	{
		Map<Comparable<?>, Integer> results = data.groupSongsByCount("song.alias"); 
		
		assertNotNull(results); 
		assertTrue(results.size() > 0); 
	}
	
	@Test
	public void groupItemsByCount_groupsByRecordingCityName()
	{
		Map<Comparable<?>, Integer> results = data.groupSongsByCount("recording.city"); 
		
		assertNotNull(results); 
		assertTrue(results.size() > 0); 
	}
	
	@Test
	public void groupItemsByCount_groupsByEveryField()
	{
		// fields in SongInstance
		groupSongsByCountForASpecificField("trackListing"); 
		
		// fields in Recording
		groupSongsByCountForASpecificField("recording.city"); 
		groupSongsByCountForASpecificField("recording.year"); 
		groupSongsByCountForASpecificField("recording.month");
		groupSongsByCountForASpecificField("recording.sublocation"); 
		
		// fields in Song
		groupSongsByCountForASpecificField("song.alias"); 
	}
		
	private void groupSongsByCountForASpecificField(String field)
	{
		Map<Comparable<?>, Integer> results = data.groupSongsByCount(field);
		
		assertNotNull(results); 
		assertTrue(results.size() > 0);
	}
	
	@Test
	public void groupItemsByCount_worksWithPrimitiveFields()
	{
		Map<Comparable<?>, Integer> results = data.groupSongsByCount("trackListing"); 
		
		assertNotNull(results); 
		assertTrue(results.size() > 0); 
	}
	
	@Test
	public void groupItemsByCommonValue_smokeTest() throws RecordingException
	{
		Map<Comparable<?>, Integer>  results = data.groupSongsByCommonValue("trackListing", "trackListing", 3, "trackListing"); 
		
		assertNotNull(results); 
	}
	
	@Test
	public void groupItemsByCommonValue_noFieldReturnsNull() throws RecordingException
	{
		// song instance
		groupItemsByCommonValueGivenParameters(false, "trackListing","trackListing", 3);
		groupItemsByCommonValueGivenParameters(false, "section","section", 3);
		// song
		groupItemsByCommonValueGivenParameters(false, "song.value","song.value", "test");
		groupItemsByCommonValueGivenParameters(false, "song.alias", "song.alias", "test");
		// recording
		groupItemsByCommonValueGivenParameters(false, "recording.city", "recording.city.value", new City("Raleigh").getValue());
		groupItemsByCommonValueGivenParameters(false, "recording.year", "recording.year", 1986); 
		groupItemsByCommonValueGivenParameters(false, "recording.sublocation","recording.sublocation",  "test");
	}
	
	@Test
	public void getNumberOfNewSongsPerYear_returnsSomething()
	{
		Map<?, ?> results = data.getNumberOfNewSongsPerYear();
		
		assertNotNull(results); 
		assertTrue(results.size() > 0); 
	}
	
	@Test
	public void groupItemsByCommonValue_worksForNonPrimitiveRecordingField() throws RecordingException
	{
		Map<Comparable<?>, Integer> results = data.groupSongsByCommonValue("recording.city", "recording.city.value", new City("Raleigh").getValue(), "trackListing"); 
		
		
		assertNotNull(results);  
	}
	
	private void groupItemsByCommonValueGivenParameters(boolean requiresResultSizeGreaterThanZero, String testColumn,String columnValueSpecifier,  Comparable<?> commonValue) throws RecordingException
	{
		Map<Comparable<?>, Integer> results = data.groupSongsByCommonValue(testColumn, columnValueSpecifier, commonValue, testColumn); 
		
		assertNotNull(results); 
		if(requiresResultSizeGreaterThanZero) assertTrue(results.size() > 0); 
	}
}
