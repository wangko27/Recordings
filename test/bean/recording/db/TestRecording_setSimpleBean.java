package bean.recording.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import bean.City;
import bean.Country;
import bean.Format;
import bean.Media;
import bean.Recording;
import bean.RecordingType;
import bean.Venue;
import data.HibernateTest;
import data.RecordingData;
import data.SimpleEntityData;
import domain.RecordingManager;
import domain.SimpleEntityManager;

public class TestRecording_setSimpleBean  extends HibernateTest 
{
	private RecordingManager recordingManager = new RecordingManager(new RecordingData());
	private SimpleEntityManager simpleEntityManager = new SimpleEntityManager(new SimpleEntityData());
	private Recording recording = null;
	
	@Override
	public void before()
	{
		recording = recordingManager.getRecording(22);
	}
	
	@Test
	public void testSetSimpleBean_venue()
	{
		try 
		{
			Venue venue = (Venue) simpleEntityManager.getSimpleBean("Bookies", Venue.class);
			recording.setSimpleBean(venue);
			
			assertEquals(venue, recording.getVenue());
		}
		catch (Exception e) 
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testSetSimpleBean_city()
	{
		try 
		{
			City city = (City) simpleEntityManager.getSimpleBean("San Diego", City.class);
			recording.setSimpleBean(city);
			
			assertEquals(city, recording.getCity());
		}
		catch (Exception e) 
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testSetSimpleBean_country()
	{
		try 
		{
			Country country = (Country) simpleEntityManager.getSimpleBean("USA", Country.class);
			recording.setSimpleBean(country);
			
			assertEquals(country, recording.getCountry());
		}
		catch (Exception e) 
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testSetSimpleBean_format()
	{
		try 
		{
			Format format = (Format) simpleEntityManager.getSimpleBean("WAV", Format.class);
			recording.setSimpleBean(format);
			
			assertEquals(format, recording.getFormat());
		}
		catch (Exception e) 
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testSetSimpleBean_media()
	{
		try 
		{
			Media media = (Media) simpleEntityManager.getSimpleBean("Digital", Media.class);
			recording.setSimpleBean(media);
			
			assertEquals(media, recording.getMedia());
		}
		catch (Exception e) 
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testSetSimpleBean_recordingType()
	{
		try 
		{
			RecordingType recordingType = (RecordingType) simpleEntityManager.getSimpleBean("Show", RecordingType.class);
			recording.setSimpleBean(recordingType);
			
			assertEquals(recordingType, recording.getRecordingType());
		}
		catch (Exception e) 
		{
			fail(e.getMessage());
		}
	}
}
