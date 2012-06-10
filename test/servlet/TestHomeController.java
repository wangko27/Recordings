package servlet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;

import org.junit.Test;
import org.mockito.Mockito;

import bean.Recording;
import bean.RecordingSearchBean;
import domain.RecordingManager;
import domain.SimpleEntityManager;
import servlet.BasicServletTester; 

public class TestHomeController extends BasicServletTester 
{
	private HomeController homeController; 
	private SimpleEntityManager simpleEntityManager;
	private RecordingManager recordingManager; 
	

	@Override
	protected boolean useDatabase() 
	{
		return false;
	}
	
	@Override
	public void postServletSetup() 
	{
		simpleEntityManager = mock(SimpleEntityManager.class); 
		recordingManager = mock(RecordingManager.class); 
		//homeController = new HomeController(recordingManager, simpleEntityManager); 
	}
		
	@Test
	public void testBasicRecordingListRetrieval() throws ServletException, IOException
	{			
		Collection<Recording> collection = new ArrayList<Recording>(); 
		when(recordingManager.getAllRecordings((RecordingSearchBean) Mockito.any())).thenReturn(collection); 
		
		homeController.doPost(stubRequest, stubResponse); 
		
		verify(stubSession).setAttribute("recordingList",collection); 
		
	}
	
	@Test
	public void testRecordingListRetrievalFromSession() throws ServletException, IOException
	{
		Collection<Recording> collection = new ArrayList<Recording>(); 
		
		when(stubSession.getAttribute("recordingList")).thenReturn(collection); 
		homeController.doPost(stubRequest, stubResponse); 
		
		verify(recordingManager, never()).getAllRecordings((RecordingSearchBean) Mockito.any());		
	}

}
