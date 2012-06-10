package domain.db;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;

import org.junit.Test;

import bean.City;
import data.HibernateTest;
import data.SongInstanceData;
import domain.SongInstanceManager;
import exception.RecordingException;

public class TestSongInstanceManager extends HibernateTest
{
	private SongInstanceManager manager = new SongInstanceManager(new SongInstanceData());
	
	
	@Test
	public void testGetAllSongInstances()
	{
		Collection<?> songInstances = manager.getAllItems();
		
		assertNotNull(songInstances);
		assertTrue(!songInstances.isEmpty());
	}
	
	@Test
	public void groupItemsByCount_groupsBySongName() throws RecordingException
	{
		Map<Comparable<?>, Integer> results = manager.groupItemsByCount("Alias"); 
		
		assertNotNull(results); 
		assertTrue(results.size() > 0); 
	}
	
	@Test
	public void groupItemsByCount_groupsByRecordingCityName() throws RecordingException
	{
		Map<Comparable<?>, Integer> results = manager.groupItemsByCount("City"); 
		
		assertNotNull(results); 
		assertTrue(results.size() > 0); 
	}
	
	@Test
	public void groupItemsByCount_groupsByEveryField() throws RecordingException
	{
		// fields in SongInstance
		groupByField("TrackListing"); 
		
		// fields in Recording
		groupByField("City"); 
		groupByField("Year"); 
		groupByField("Month");
		groupByField("Sublocation"); 
		
		// fields in Song
		groupByField("Alias"); 
	}
	
	private void groupByField(String field) throws RecordingException
	{
		Map<Comparable<?>, Integer> results = manager.groupItemsByCount(field);
		
		assertNotNull(results); 
		assertTrue(results.size() > 0);
	}
	
	@Test
	public void groupItemsByCount_worksWithPrimitiveFields() throws RecordingException
	{
		Map<Comparable<?>, Integer> results = manager.groupItemsByCount("trackListing"); 
		
		assertNotNull(results); 
		assertTrue(results.size() > 0); 
	}
	
	@Test
	public void groupItemsByCommonValue_worksWithNumericalTypes() throws RecordingException
	{
		Map<Comparable<?>, Integer> results = manager.groupItemsByCommonValue("trackListing", 3, "trackListing");  
		
		assertNotNull(results);
	}
	
	@Test
	public void groupItemsByCommonValue_worksWithVariousTypes() throws RecordingException
	{
		// recording
		aTestRunFor_groupItemsByCommonValue("City", new City("Raleigh"), "City");
		aTestRunFor_groupItemsByCommonValue("Year",1983, "Year");
		aTestRunFor_groupItemsByCommonValue("Sublocation", "test", "Sublocation");
		// song
		aTestRunFor_groupItemsByCommonValue("Alias","Test", "Alias"); 
	}
	
	@Test
	public void groupItemsByCount_worksOnSongVotes() throws RecordingException
	{
		Map<Comparable<?>, Integer> results = manager.groupItemsByCount("votes"); 
		
		assertNotNull(results); 
	}
	
	@Test
	public void groupItemsByCommonValue_worksOnSongVotes() throws RecordingException
	{	
		Map<Comparable<?>, Integer> results = manager.groupItemsByCommonValue("votes", 3, "year");
		
		assertNotNull(results); 
	}
	
	private void aTestRunFor_groupItemsByCommonValue(String commonValueColumn, Comparable<?> commonValue, String variableColumn) throws RecordingException
	{
		Map<Comparable<?>, Integer> results = manager.groupItemsByCommonValue(commonValueColumn, commonValue, variableColumn);  
		
		assertNotNull(results);
	}
}
