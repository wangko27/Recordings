package bean.recording;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

import bean.City;
import bean.Country;
import bean.Recording;
import bean.RecordingType;

public class TestRecording_formattedFields 
{
	@Test
	public void testGetFormattedShortDateString()
	{
		Recording recording = new Recording();
		RecordingType type = new RecordingType();
		type.setValue("Album");
		recording.setRecordingType(type);
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1985);
		
		recording.setDate(cal.get(Calendar.DATE));
		recording.setMonth(cal.get(Calendar.MONTH));
		recording.setYear(cal.get(Calendar.YEAR));
		
		assertEquals("1985", recording.getFormattedShortDateString());
	}
	
	@Test
	public void testGetFormattedLocation_nothing()
	{
		Recording recording = new Recording();
		
		assertEquals("Unknown Location", recording.getFormattedLocation());
	}
	
	@Test
	public void testGetFormattedLocation_onlyCountry()
	{
		Recording recording = new Recording();
		Country country = new Country("USA");
		
		recording.setCountry(country);
		
		assertEquals("USA", recording.getFormattedLocation());
	}
	
	@Test
	public void testGetFormattedLocation_noSubLocation()
	{
		Recording recording = new Recording();
		City city = new City("Boston");
		Country country = new Country("USA");
		
		recording.setCity(city);
		recording.setCountry(country);
		
		assertEquals("Boston, USA", recording.getFormattedLocation());
	}
	
	@Test
	public void testGetFormattedLocation_full()
	{
		Recording recording = new Recording();
		City city = new City("Boston");
		Country country = new Country("USA");
		
		recording.setCity(city);
		recording.setCountry(country);
		recording.setSublocation("Cambridge");
		
		assertEquals("Boston, Cambridge, USA", recording.getFormattedLocation());
	}
	
	@Test
	public void testGetFormattedShortDateString_noDate()
	{
		Recording recording = new Recording();
		
		assertEquals("unk",recording.getFormattedShortDateString());
	}
	
	@Test
	public void testGetFormattedShortDateString_missingMonthAndDate()
	{
		Recording recording = new Recording();
		recording.setYear(1999);
		
		assertEquals("1999",recording.getFormattedShortDateString());
	}
	
	@Test
	public void testGetFormattedShortDateString_missingYear()
	{
		Recording recording = new Recording();
		recording.setMonth(8);
		recording.setDate(3);
		
		//assertEquals("8/3/?",recording.getFormattedShortDateString());
		assertEquals("?/08/03",recording.getFormattedShortDateString());
	}
	
	@Test
	public void testGetFormattedShortDateString_missingDate()
	{
		Recording recording = new Recording();
		recording.setMonth(8);
		recording.setYear(1999);
		
		assertEquals("1999/08/?",recording.getFormattedShortDateString());
	}
	
	@Test
	public void testGetFormattedShortDateString_full()
	{
		Recording recording = new Recording();
		recording.setMonth(8);
		recording.setDate(3);
		recording.setYear(1999);
		
		assertEquals("1999/08/03",recording.getFormattedShortDateString());
	}
	
	@Test
	public void testGetFormattedLongDateString_noDate()
	{
		Recording recording = new Recording();
		
		assertEquals("Unknown Date",recording.getFormattedLongDateString());
	}
	
	@Test
	public void testGetFormattedLongDateString_missingMonthAndDate()
	{
		Recording recording = new Recording();
		recording.setYear(1999);
		
		assertEquals("1999",recording.getFormattedLongDateString());
	}
	
	@Test
	public void testGetFormattedLongDateString_missingYear()
	{
		Recording recording = new Recording();
		recording.setMonth(8);
		recording.setDate(3);
		
		assertEquals("August 3rd",recording.getFormattedLongDateString());
	}
	
	@Test
	public void testGetFormattedLongDateString_missingDate()
	{
		Recording recording = new Recording();
		recording.setMonth(8);
		recording.setYear(1999);
		
		assertEquals("August 1999",recording.getFormattedLongDateString());
	}
	
	@Test
	public void testGetFormattedLongDateString_full()
	{
		Recording recording = new Recording();
		recording.setMonth(8);
		recording.setDate(3);
		recording.setYear(1999);
		
		assertEquals("August 3rd, 1999",recording.getFormattedLongDateString());
	}
	
	@Test
	public void testGetFormattedLongDateString_noDate_StudioBootleg()
	{
		Recording recording = new Recording();
		
		RecordingType type = new RecordingType();
		type.setValue("Studio Bootleg");
		recording.setRecordingType(type);
		
		assertEquals("",recording.getFormattedLongDateString());
	}
	
	@Test
	public void testGetFormattedShortDateString_noDate_StudioBootleg()
	{
		Recording recording = new Recording();
		
		RecordingType type = new RecordingType();
		type.setValue("Studio Bootleg");
		recording.setRecordingType(type);
		
		assertEquals("",recording.getFormattedShortDateString());
	}
}
