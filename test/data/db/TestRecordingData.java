package data.db;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import bean.City;
import bean.Country;
import bean.Format;
import bean.Media;
import bean.Quality;
import bean.Recording;
import bean.RecordingComment;
import bean.RecordingType;
import bean.SongInstance;
import bean.Venue;
import data.HibernateTest;
import data.RecordingData;
import data.common.IRecordingData;
import exception.RecordingException;

public class TestRecordingData extends HibernateTest 
{
	private IRecordingData data = new RecordingData();
	
	@Test
	public void testGetRecording_comments()
	{
		Recording recording = data.getRecording(4);
		assertNotNull(recording);
		
		List<RecordingComment> comments = recording.getComments();
		
		assertNotNull(comments);
		
		for ( RecordingComment comment : comments) 
		{
			assertNotNull(comment);
		}
	}

	@Test
	public void testGetRecording_songInstances()
	{
		Recording recording = data.getRecording(3);
		assertNotNull(recording);
		
		List<SongInstance> songInstances = recording.getSongInstances();
		
		assertNotNull(songInstances);
		
		for ( SongInstance songInstance : songInstances) 
		{
			assertNotNull(songInstance);
		}
	}
	
	@Test
	public void testGetRecording_manyToOneValues()
	{
		Recording recording = data.getRecording(3);
		assertNotNull(recording);
		
		assertNotNull(recording.getVenue().getValue());
		assertNotNull(recording.getCity().getValue());
		assertNotNull(recording.getCountry().getValue());
		assertNotNull(recording.getQuality().getValue());
	}
	
	@Test
	public void testGetAllRecordings()
	{
		List<Recording> recordings = (List<Recording>) data.getAllRecordings();
		assertNotNull(recordings);
		assertTrue(!recordings.isEmpty());
	}
	
	@Test
	public void testGetRecording()
	{
		Recording recording = data.getRecording(3);
		assertNotNull(recording);
	}
	
	@Test
	public void groupItemsByCount_returnsNonEmptyResultSet() throws RecordingException
	{
		Map<Comparable<?>, Integer> results = data.groupRecordingsByCount("Month", null);
		
		assertNotNull(results); 
		assertTrue(results.size() > 0); 
	}
	
	@Test
	public void groupItemsByCount_usingForeignKeys_returnsNonEmptyResultSet() throws RecordingException
	{
		Map<Comparable<?>, Integer> results = data.groupRecordingsByCount("Quality", null);
		
		assertNotNull(results); 
		assertTrue(results.size() > 0); 
	}
	
	@Test
	public void groupItemsByCommonValue_returnsNonEmptyResultSet() throws RecordingException 
	{
		Map<Comparable<?>, Integer> results = data.groupRecordingsByCommonValue("Month", 1, "Year", null);
		
		assertNotNull(results); 
		assertTrue(results.size() > 0); 
	}
	
	@Test
	public void groupItemsByCount_worksOnNonColumn() throws RecordingException
	{
		Map<Comparable<?>, Integer> results = data.groupRecordingsByCount("Votes", null); 
		
		assertNotNull(results); 
		assertTrue(results.size() > 0); 
	}
	
	@Test
	public void groupItemsByCommonValue_worksOnNonColumn() throws RecordingException
	{
		Map<Comparable<?>, Integer> results = data.groupRecordingsByCommonValue("Votes", 0, "Year", null);  
		
		assertNotNull(results); 
		assertTrue(results.size() > 0);
	}
	
	@Test
	public void groupItemsByCommonValue_worksOkWithNonPrimitiveTypes() throws RecordingException
	{
		Map<Comparable<?>, Integer> results = data.groupRecordingsByCommonValue("City", new City("Boston"), "Quality", null);
		
		assertNotNull(results); 
		assertTrue(results.size() > 0); 
	}
	
	@Test
	public void groupItemsByCount_worksOnAllFields() throws RecordingException
	{
		try
		{
			data.groupRecordingsByCount("RecordingType", null);
			data.groupRecordingsByCount("Year", null);
			data.groupRecordingsByCount("Format", null);
			data.groupRecordingsByCount("Link", null);
			data.groupRecordingsByCount("City", null);
			data.groupRecordingsByCount("Media", null);
			data.groupRecordingsByCount("Quality", null);
			data.groupRecordingsByCount("Votes", null);
			data.groupRecordingsByCount("Sublocation", null);
			data.groupRecordingsByCount("Name", null);
			data.groupRecordingsByCount("Country", null);
			data.groupRecordingsByCount("Month", null);
			
			assertTrue(true); 
		}
		catch(Exception exc) 
		{
			fail(); 
		}
		
	}
	
	@Test
	public void groupItemsByCount_canSortCollectionEvenThoughItsLazilyLoaded() throws RecordingException
	{
		Collection<Recording> results = data.getAllRecordings(); 
		List<Country> unsortedValues = new ArrayList<Country>(); 
		
		for(Recording recording : results)
		{
			if(recording.getCountry() != null)
				unsortedValues.add(recording.getCountry()); 
		}
		
		Collections.sort(unsortedValues); 
	}
	
	@Test
	public void deleteRecording()
	{
		Recording recording = data.getRecording(1); 
		data.delete(recording);
		assertTrue(true); 
	}
	
	@Ignore 
	@Test
	public void smokeTest_Update()
	{
		Venue venue = new Venue(); 
		venue.setId(1); 
		
		City city = new City(); 
		city.setId(1); 
		
		Country country = new Country(); 
		country.setId(1); 
		
		Quality quality = new Quality(); 
		quality.setId(1); 
		
		Format format = new Format(); 
		format.setId(1); 
		
		Media media = new Media(); 
		media.setId(1);  
		
		RecordingType recType = new RecordingType(); 
		recType.setId(1); 
		
		Recording recording = new Recording(); 
		recording.setVenue(venue); 
		recording.setYear(2012); 
		recording.setCity(city); 
		recording.setQuality(quality); 
		recording.setCountry(country); 
		recording.setFormat(format); 
		recording.setMedia(media); 
		recording.setRecordingType(recType); 
		data.saveOrUpdate(recording);		
	}
}
