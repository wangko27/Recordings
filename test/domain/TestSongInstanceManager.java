package domain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import bean.City;
import bean.Format;
import data.SongInstanceData;
import exception.RecordingException;

public class TestSongInstanceManager
{
	private SongInstanceManager manager; 
	private SongInstanceData stubData; 
	
	@Before 
	public void setup()
	{
		stubData = mock(SongInstanceData.class); 
		manager = new SongInstanceManager(stubData); 
	}

	@Test
	public void validateField_worksOnSongInstancePrimitiveTypes()
	{
		boolean result = manager.validateField("TrackListing"); 		
		assertTrue(result); 
		
		result = manager.validateField("Section"); 
		assertTrue(result); 
	}
	
	@Test
	public void validateField_worksOnForeignKeyTypes()
	{
		boolean result = manager.validateField("Alias"); 		
		assertTrue(result); 
		
		result = manager.validateField("Year"); 
		assertTrue(result); 
	}	
	
	
	@Test
	public void groupItemsByCommonValue_worksForSongFields() throws RecordingException
	{
		SongInstanceData stubSongInstanceData = mock(SongInstanceData.class);
		
		Map<Comparable<?>, Integer> fakeQueryResults = new HashMap<Comparable<?>, Integer>();
		fakeQueryResults.put("song", 1); 
		when(stubSongInstanceData.groupSongsByCommonValue(Mockito.contains("song."), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
			.thenReturn(fakeQueryResults);  
		
		manager = new SongInstanceManager(stubSongInstanceData); 
		Map<Comparable<?>, Integer> results = manager.groupItemsByCommonValue("Alias", "test", "Alias");
		
		assertNotNull(results); 
		assertTrue(results.containsKey("song")); 
	}
	
	@Test
	public void getExpectedType_worksForRecordingFields() 
	{
		assertTrue(manager.getExpectedType("City").equals(City.class));
		assertTrue(manager.getExpectedType("Sublocation").equals(String.class));  
		assertTrue(manager.getExpectedType("Year").equals(Integer.class));  
		assertTrue(manager.getExpectedType("Format").equals(Format.class));
		assertTrue(manager.getExpectedType("Month").equals(Integer.class));
		assertTrue(manager.getExpectedType("Name").equals(String.class));
	}
	
	@Test
	public void getExpectedType_worksForSongFields()
	{
		assertTrue(manager.getExpectedType("Alias").equals(String.class));
		assertTrue(manager.getExpectedType("Value").equals(String.class));  
	}
	
	@Test
	public void getExpectedType_worksForSongInstanceTypes()
	{
		assertTrue(manager.getExpectedType("TrackListing").equals(Integer.class));
		assertTrue(manager.getExpectedType("Section").equals(Integer.class));  
	}
	
	@Test
	public void getNumberOfNewSongSperYear_returnsNonNull()
	{
		when(stubData.getNumberOfNewSongsPerYear()).thenReturn(new HashMap<Comparable<?>, Integer>()); 
		Map<Comparable<?>, Integer> results = manager.getNumberOfNewSongsPerYear();
		
		assertNotNull(results); 
	}
}
