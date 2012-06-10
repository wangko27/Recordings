package servlet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.mockito.Mockito;

import bean.Recording;
import domain.RecordingManager;
import domain.TableElementManager;


public class TestIndividualRecordingController extends BasicServletTester
{
	private IndividualRecordingController controller;
	private RecordingManager stubRecordingManager; 
	private TableElementManager stubTableElementManager; 
	private PrevNextRecordingIdHelper stubHelper; 
	private CommentsLoader stubCommentsLoader; 
	
	private Recording recording; 
	
	@Override 
	public boolean useDatabase()
	{
		return false; 
	}
	
	@Override
	public void postServletSetup()
	{
		recording = new Recording(); 
		
		stubRecordingManager = mock(RecordingManager.class); 
		stubTableElementManager = mock(TableElementManager.class); 
		stubHelper = mock(PrevNextRecordingIdHelper.class); 
		stubCommentsLoader = mock(CommentsLoader.class); 
		
		controller = new IndividualRecordingController(stubRecordingManager, stubTableElementManager, stubHelper, stubCommentsLoader); 
	}
	
	@Test
	public void requestSetsAllRequiredAttributes() throws ServletException, IOException
	{
		when(stubRequest.getParameter("id")).thenReturn("1"); 
		when(stubRecordingManager.getRecording(1)).thenReturn(recording); 
		
		controller.doGet(stubRequest, stubResponse); 
		
		// verify we retrieved the recording
		verify(stubRecordingManager).getRecording(1); 
		// confirm comments loaded for recording that was requested
		verify(stubCommentsLoader).loadCommentsAndSetAttributes(1, stubRequest, recording);
		// verify id attribute is set for votes
		verify(stubRequest).setAttribute("id", 1); 
		// verify table element data is set
		verify(stubRequest).setAttribute(Mockito.eq("recordingTableElements"), Mockito.anyCollection());
		verify(stubRequest).setAttribute(Mockito.eq("songInstanceTableElements"), Mockito.anyCollection());
	}
	
	@Test
	public void invalidRequest() throws ServletException, IOException
	{
		when(stubRequest.getParameter("id")).thenReturn("foo"); 
		
		controller.doGet(stubRequest, stubResponse);		
		// make sure it's caught instead of throwing 
		verify(stubLogger).logError(Mockito.contains("NumberFormatException")); 
		
		when(stubRequest.getParameter("id")).thenReturn(null);
		
		controller.doGet(stubRequest, stubResponse);
		verify(stubLogger, times(2)).logError(Mockito.contains("NumberFormatException")); 
	}

}
