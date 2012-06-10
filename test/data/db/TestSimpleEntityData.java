package data.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import bean.City;
import bean.Country;
import bean.Format;
import bean.Media;
import bean.Quality;
import bean.RecordingType;
import bean.Song;
import bean.Venue;
import data.HibernateTest;
import data.SimpleEntityData;
import data.common.ISimpleEntityData;

public class TestSimpleEntityData extends HibernateTest 
{

	private ISimpleEntityData data = new SimpleEntityData();
	
	@Test
	public void testGetSong()
	{
		int songId = 231; // happy birthday
		Song song = (Song) data.getSimpleBean(songId, Song.class);
		
		assertNotNull(song);
	}
	
	@Test
	public void testGetSimpleBean_value()
	{
		assertEquals("USA", ((Country)data.getSimpleBean("USA", Country.class)).getValue());
	}
	
	@Test
	public void testClassSimpleName()
	{
		assertEquals("City", City.class.getSimpleName());
	}
	
	@Test
	public void testSaveOrUpdateVenue()
	{
		Venue venue = new Venue();
		
		venue.setValue("test venue");
		
		data.saveOrUpdateSimpleBean(venue);
	}
	
	@Test
	public void testGetVenue()
	{
		Venue v = (Venue) data.getSimpleBean(1, Venue.class);
		
		assertNotNull(v);
	}

	@Test
	public void testGetAllSongs()
	{
		List<Song> songs = data.getAllSongs();
		
		assertNotNull(songs);
		assertTrue(!songs.isEmpty());
	}
	
	@Test
	public void testGetAllVenues()
	{
		List<Venue> venues = data.getAllVenues();
		
		assertNotNull(venues);
		assertTrue(!venues.isEmpty());
	}
	
	@Test
	public void testGetAllQuailties()
	{
		List<Quality> qualities = data.getAllQualities();
		
		assertNotNull(qualities);
		assertTrue(!qualities.isEmpty());
	}
	
	@Test
	public void testGetAllRecordingTypes()
	{
		List<RecordingType> recordingTypes = data.getAllRecordingTypes();
		
		assertNotNull(recordingTypes);
		assertTrue(!recordingTypes.isEmpty());
	}
	
	@Test
	public void testGetAllCities()
	{
		List<City> cities = data.getAllCities();
		
		assertNotNull(cities);
		assertTrue(!cities.isEmpty());
	}
	
	@Test
	public void testGetAllCountries()
	{
		List<Country> countries = data.getAllCountries();
		
		assertNotNull(countries);
		assertTrue(!countries.isEmpty());
	}
	
	@Test
	public void testGetAllFormats()
	{
		List<Format> formats = data.getAllFormats();
		
		assertNotNull(formats);
		assertTrue(!formats.isEmpty());
	}
	
	@Test
	public void testGetAllMedias()
	{
		List<Media> medias = data.getAllMedias();
		
		assertNotNull(medias);
		assertTrue(!medias.isEmpty());
	}
	
	@Test
	public void testRetrievalByValue()
	{
		Country country = (Country) data.getSimpleBean("USA", Country.class);
		assertNotNull(country); 
		assertTrue(country.getValue().equals("USA")); 
	}
}
