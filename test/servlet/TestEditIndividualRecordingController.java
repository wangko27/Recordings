package servlet;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.mockito.Mockito;

import bean.Recording;

import domain.RecordingManager;
import domain.SimpleEntityManager;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class TestEditIndividualRecordingController extends BasicServletTester
{
	private EditIndividualRecordingController controller; 
	private RecordingManager recordingManager; 
	private SimpleEntityManager simpleEntityManager; 
	
	@Override
	protected boolean useDatabase()
	{
		return false;
	} 
	
	@Override
	public void postServletSetup() 
	{
		recordingManager = mock(RecordingManager.class); 
		simpleEntityManager = mock(SimpleEntityManager.class); 
		controller = new EditIndividualRecordingController(recordingManager, simpleEntityManager); 
	}

	@Test
	public void testInvalidRecordingId() throws ServletException, IOException
	{
		when(stubRequest.getParameter("id")).thenReturn("foo"); 
		
		controller.doGet(stubRequest, stubResponse); 
	}
	
	@Test
	public void testListsArePopulated() throws ServletException, IOException
	{
		when(stubRequest.getParameter("id")).thenReturn("1"); 
		
		controller.doGet(stubRequest, stubResponse); 
		verify(stubRequest).setAttribute(Mockito.matches("cities"), Mockito.any());
		verify(stubRequest).setAttribute(Mockito.matches("formats"), Mockito.any());
		verify(stubRequest).setAttribute(Mockito.matches("medias"), Mockito.any());
		verify(stubRequest).setAttribute(Mockito.matches("qualities"), Mockito.any());
		verify(stubRequest).setAttribute(Mockito.matches("recordingTypes"), Mockito.any());
		verify(stubRequest).setAttribute(Mockito.matches("songs"), Mockito.any());
		verify(stubRequest).setAttribute(Mockito.matches("venues"), Mockito.any());		
	}
	
	@Test
	public void testRecordingUpdate() throws ServletException, IOException
	{
		Recording recording = new Recording(); 
		recording.setMonth(0); 
		
		when(stubRequest.getParameter("id")).thenReturn("1");
		when(stubRequest.getParameter("month")).thenReturn("1");
		when(stubRequest.getParameter("year")).thenReturn("1");
		when(stubRequest.getParameter("date")).thenReturn("1");
		when(stubRequest.getParameter("name")).thenReturn("foo");
		when(stubRequest.getParameter("newCity")).thenReturn("raleigh");
		when(stubRequest.getParameter("newVenue")).thenReturn("foo");
		when(stubRequest.getParameter("newQuality")).thenReturn("foo");		
		when(stubRequest.getParameter("newFormat")).thenReturn("foo"); 
		when(stubRequest.getParameter("newRecordingType")).thenReturn("foo");
		when(stubRequest.getParameter("newMedia")).thenReturn("foo");
		when(stubRequest.getParameter("newCountry")).thenReturn("foo");
		when(recordingManager.getRecording(Mockito.anyInt())).thenReturn(recording); 		
		
		controller.doPost(stubRequest, stubResponse); 
		
		verify(recordingManager).saveOrUpdate((Recording) Mockito.any()); 
		
		assertTrue("Month was " + recording.getMonth(), recording.getMonth() == 1);
		assertTrue("Year was " + recording.getYear(), recording.getYear() == 1);
		assertTrue("Date was " + recording.getDate(), recording.getDate() == 1);
		assertTrue("Name was " + recording.getDate(), recording.getName() == "foo");
		assertTrue("City was " + recording.getCity(), recording.getCity().getValue().equals("raleigh"));
		assertTrue("Venue was " + recording.getVenue(), recording.getVenue().getValue().equals("foo"));
		assertTrue("Quality was " + recording.getQuality(), recording.getQuality().getValue().equals("foo"));
		assertTrue("Format was " + recording.getFormat(), recording.getFormat().getValue().equals("foo"));
		assertTrue("Recording type was " + recording.getRecordingType(), recording.getRecordingType().getValue().equals("foo"));
		assertTrue("Media was " + recording.getMedia(), recording.getMedia().getValue().equals("foo"));
		assertTrue("Country was " + recording.getCountry(), recording.getCountry().getValue().equals("foo"));
	}
}
