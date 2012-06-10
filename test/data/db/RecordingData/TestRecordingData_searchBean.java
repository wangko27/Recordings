package data.db.RecordingData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import bean.City;
import bean.Country;
import bean.Recording;
import bean.RecordingSearchBean;
import bean.Venue;
import data.HibernateTest;
import data.RecordingData;
import data.SimpleEntityData;
import data.common.IRecordingData;
import domain.SimpleEntityManager;

public class TestRecordingData_searchBean  extends HibernateTest 
{
	private IRecordingData data;
	
	@Override 
	public void before()
	{
		data = new RecordingData();
	}
	
	@Test
	public void testGetAllRecordings_venue()
	{
		RecordingSearchBean searchBean = new RecordingSearchBean();
		int bottomLine_id = 207;
		SimpleEntityManager simpleEntityManager = new SimpleEntityManager(new SimpleEntityData());
		Venue venue = (Venue)simpleEntityManager.getSimpleBean(bottomLine_id, Venue.class);
		searchBean.getRecording().setVenue(venue);
		List<Recording> recordings = (List<Recording>) data.getAllRecordings(searchBean);
		
		assertTrue(!recordings.isEmpty());
		assertNotNull(recordings);
		for (Recording recording : recordings) {
			assertEquals(bottomLine_id, recording.getVenue().getId());
		}
	}
	
	@Test
	public void testGetAllRecordings_location()
	{
		RecordingSearchBean searchBean = new RecordingSearchBean();
		int USA_id = 8;
		int boston_id = 114;
		SimpleEntityManager simpleEntityManager = new SimpleEntityManager(new SimpleEntityData());
		Country country = (Country)simpleEntityManager.getSimpleBean(USA_id, Country.class);
		City city = (City)simpleEntityManager.getSimpleBean(boston_id, City.class);
		searchBean.getRecording().setCountry(country);
		searchBean.getRecording().setCity(city);
		List<Recording> recordings = (List<Recording>) data.getAllRecordings(searchBean);
		
		assertTrue(!recordings.isEmpty());
		assertNotNull(recordings);
		for (Recording recording : recordings) {
			assertEquals(USA_id, recording.getCountry().getId());
			assertEquals(boston_id, recording.getCity().getId());
		}
	}
	
	@Test
	public void testGetAllRecordings_year()
	{
		RecordingSearchBean searchBean = new RecordingSearchBean();
		searchBean.getRecording().setYear(new Integer(1977));
		searchBean.getRecording().setMonth(new Integer(3));
		List<Recording> recordings = (List<Recording>) data.getAllRecordings(searchBean);

		assertTrue(!recordings.isEmpty());
		assertNotNull(recordings);
		for (Recording recording : recordings)
		{
			assertEquals(new Integer(1977), recording.getYear());
			assertEquals(new Integer(3), recording.getMonth());
		}
	}
	
	@Test
	public void testGetAllRecordings_all()
	{
		List<Recording> recordings = (List<Recording>) data.getAllRecordings(new RecordingSearchBean());
		
		assertNotNull(recordings);
	}
}
