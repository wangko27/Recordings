package bean.recording.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import bean.City;
import bean.Country;
import bean.Format;
import bean.Media;
import bean.Quality;
import bean.Recording;
import bean.RecordingType;
import bean.SimpleBean;
import bean.Venue;
import data.HibernateTest;
import data.RecordingData;
import domain.RecordingManager;

public class TestRecording_getSimpleBean extends HibernateTest {
	
	private RecordingManager recordingManager = new RecordingManager(new RecordingData());
	private Recording recording = null;
	
	@Override 
	public void before()
	{
		recording = recordingManager.getRecording(22);
	}
	
	@Test
	public void testGetSimpleValue_venue()
	{
		try
		{
			Venue venue= recording.getVenue();
			SimpleBean simpleBean = recording.getSimpleBean("Venue");
			
			if( venue == null )
				assertNull(simpleBean);
			else
				assertEquals(venue.getValue(), simpleBean.getValue());
		}
		catch( Exception e )
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetSimpleValue_city()
	{
		try
		{
			City city = recording.getCity();
			SimpleBean simpleBean = recording.getSimpleBean("city");
			
			if( city == null )
				assertNull(simpleBean);
			else
				assertEquals(city.getValue(), simpleBean.getValue());
		}
		catch( Exception e )
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetSimpleValue_country()
	{
		try
		{
			Country country = recording.getCountry();
			SimpleBean simpleBean = recording.getSimpleBean("country");
			
			if( country == null )
				assertNull(simpleBean);
			else
				assertEquals(country.getValue(), simpleBean.getValue());
		}
		catch( Exception e )
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetSimpleValue_media()
	{
		try
		{
			Media media = recording.getMedia();
			SimpleBean simpleBean = recording.getSimpleBean("media");
			
			if( media == null )
				assertNull(simpleBean);
			else
				assertEquals(media.getValue(), simpleBean.getValue());
		}
		catch( Exception e )
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetSimpleValue_recordingType()
	{
		try
		{
			RecordingType recordingType = recording.getRecordingType();
			SimpleBean simpleBean = recording.getSimpleBean("recordingType");
			
			if( recordingType == null )
				assertNull(simpleBean);
			else
				assertEquals(recordingType.getValue(), simpleBean.getValue());
		}
		catch( Exception e )
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetSimpleValue_format()
	{
		try
		{
			Format format= recording.getFormat();
			SimpleBean simpleBean = recording.getSimpleBean("format");
			
			if( format == null )
				assertNull(simpleBean);
			else
				assertEquals(format.getValue(), simpleBean.getValue());
		}
		catch( Exception e )
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetSimpleValue_quailty()
	{
		try
		{
			Recording recording = recordingManager.getRecording(22);
			
			Quality quality = recording.getQuality();
			SimpleBean simpleBean = recording.getSimpleBean("Quality");
			
			if( quality == null )
				assertNull(simpleBean);
			else
				assertEquals(quality.getValue(), simpleBean.getValue());
		}
		catch( Exception e )
		{
			fail(e.getMessage());
		}
	}
}
