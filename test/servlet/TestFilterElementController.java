package servlet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;

import bean.City;
import bean.Country;
import bean.Quality;
import bean.RecordingType;
import bean.Venue;
import domain.RecordingManager;
import domain.SimpleEntityManager;

public class TestFilterElementController extends BasicServletTester 
{
	private FilterElementController controller;
	private RecordingManager stubRecordingManager; 
	private SimpleEntityManager stubSimpleEntityManager; 
	
	@Override
	protected boolean useDatabase() 
	{
		return false;
	}

	@Override
	public void postServletSetup() 
	{
		stubRecordingManager = mock(RecordingManager.class); 
		stubSimpleEntityManager = mock(SimpleEntityManager.class); 
		controller = new FilterElementController(stubRecordingManager, stubSimpleEntityManager); 
	}
	
	@Test
	public void validFilterRequest() throws ServletException, IOException
	{
		when(stubRequest.getParameter("year")).thenReturn("1999");
		when(stubRequest.getParameter("month")).thenReturn("3"); 
		when(stubRequest.getParameter("date")).thenReturn("23"); 
		when(stubRequest.getParameter("country")).thenReturn("USA"); 
		when(stubRequest.getParameter("city")).thenReturn("Raleigh"); 
		when(stubRequest.getParameter("venue")).thenReturn("None"); 
		when(stubRequest.getParameter("recordingType")).thenReturn("Show");
		when(stubRequest.getParameter("quality")).thenReturn("Good");
		
		controller.doGet(stubRequest, stubResponse); 
		
		// ensure parameters are accessed
		verify(stubRequest).getParameter("year"); 
		verify(stubRequest).getParameter("month");
		verify(stubRequest).getParameter("date"); 
		verify(stubRequest).getParameter("country"); 
		verify(stubRequest).getParameter("city"); 
		verify(stubRequest).getParameter("venue");
		verify(stubRequest).getParameter("recordingType"); 
		verify(stubRequest).getParameter("quality"); 
		
		// ensure simplebeans are retrieved
		verify(stubSimpleEntityManager).getSimpleBean("USA", Country.class);
		verify(stubSimpleEntityManager).getSimpleBean("Raleigh", City.class); 
		verify(stubSimpleEntityManager).getSimpleBean("None", Venue.class); 
		verify(stubSimpleEntityManager).getSimpleBean("Show", RecordingType.class); 
		verify(stubSimpleEntityManager).getSimpleBean("Good", Quality.class); 
	}
	
	
}
