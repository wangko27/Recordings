package servlet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.mockito.Mockito;

import bean.SimpleBean;

import domain.LookupValuesManager;
import domain.NotUsedManager;
import domain.SimpleEntityManager;
import exception.RecordingException;

public class TestLookupValuesController extends BasicServletTester
{
	@Override
	protected boolean useDatabase() 
	{
		return false;
	}

	private LookupValuesManager lookupValuesManager;
	private SimpleEntityManager simpleEntityManager; 
	private NotUsedManager notUsedManager; 
	private LookupValuesController controller; 
	
	@Override
	public void postServletSetup() 
	{
		lookupValuesManager = mock(LookupValuesManager.class);
		simpleEntityManager = mock(SimpleEntityManager.class); 
		notUsedManager = mock(NotUsedManager.class); 
		controller = new LookupValuesController(lookupValuesManager, simpleEntityManager, notUsedManager); 
	}
	
	@Test
	public void testValidDeleteSimpleValue() throws ServletException, IOException
	{
		when(stubRequest.getParameter("action")).thenReturn("notuseddelete"); 
		when(stubRequest.getParameter("id")).thenReturn("1");
		when(stubRequest.getParameter("category")).thenReturn("City"); 
		
		controller.doPost(stubRequest, stubResponse); 
		
		verify(simpleEntityManager).delete((SimpleBean) Mockito.any()); 
	}

	@Test
	public void testValidMerge() throws Exception
	{
		when(stubRequest.getParameter("action")).thenReturn("merge"); 
		when(stubRequest.getParameter("originalId")).thenReturn("1");
		when(stubRequest.getParameter("mergeId")).thenReturn("2");
		when(stubRequest.getParameter("category")).thenReturn("City"); 
		
		controller.doPost(stubRequest, stubResponse);
		
		verify(lookupValuesManager).mergeLookupValues("City", "1", "2"); 
	}
	
	@Test
	public void testValidUpdate() throws RecordingException, ServletException, IOException
	{
		when(stubRequest.getParameter("action")).thenReturn("edit"); 
		when(stubRequest.getParameter("originalId")).thenReturn("1");
		when(stubRequest.getParameter("updatedValue")).thenReturn("2");
		when(stubRequest.getParameter("category")).thenReturn("City"); 
		
		controller.doPost(stubRequest, stubResponse); 
		
		verify(lookupValuesManager).updateLookupValue("City", "1", "2"); 
	}
	
	
}
