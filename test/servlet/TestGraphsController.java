package servlet;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import data.StubRecordingData;
import domain.RecordingManager;
import domain.graphs.GraphsManager;
import exception.RecordingException;

public class TestGraphsController 
{
	private HttpServletRequest stubbedRequest; 
	private HttpServletResponse stubbedResponse; 
	
	@Before
	public void setup()
	{
		stubbedRequest = mock(HttpServletRequest.class); 
		stubbedResponse = mock(HttpServletResponse.class);
		
		RequestDispatcher dispatcher = mock(RequestDispatcher.class); 
		when(stubbedRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(dispatcher);
	}
	
	@Test
	public void defaultGetRequest_retrievesListOfCannedGraphs() throws  IOException, ServletException
	{					
		new GraphsController().doGet(stubbedRequest, stubbedResponse); 
				
		verify(stubbedRequest).setAttribute(Mockito.eq("cannedgraphs"), Mockito.anyString()); 
	}
	 
	@Test
	public void graphRequest_trimsLeadingSpaces_fromFieldNames() throws IOException, ServletException, RecordingException 
	{
		GraphsManager stubManager = new GraphsManager(new RecordingManager(new StubRecordingData()), null); 
		
		StringWriter sw = new StringWriter(); 
		PrintWriter  pw = new PrintWriter(sw); 
		
		when(stubbedRequest.getParameter("getgraph")).thenReturn("true"); 
		when(stubbedRequest.getParameter("x")).thenReturn("Year ");
		when(stubbedRequest.getParameter("y")).thenReturn("Year ");
		when(stubbedRequest.getParameter("type")).thenReturn("frequency");
		when(stubbedRequest.getParameter("style")).thenReturn("line");
		when(stubbedRequest.getParameter("graphdatasource")).thenReturn("recording");
		
		when(stubbedResponse.getWriter()).thenReturn(pw); 
		
		GraphsController controller = new GraphsController(); 
		controller.setGraphsManager(stubManager); 
		controller.doGet(stubbedRequest, stubbedResponse); 
		
		String result = sw.getBuffer().toString();  
		// containing xticks means the graph has data 
		assertTrue("result was " + result, result.contains("xticks")); 
	}
	
	@Test 
	public void graphRequest_nullFields_sendsErrorBean() throws ServletException, IOException
	{
		StringWriter sw = new StringWriter(); 
		PrintWriter  pw = new PrintWriter(sw); 
		
		when(stubbedRequest.getParameter("getgraph")).thenReturn("true"); 
		when(stubbedResponse.getWriter()).thenReturn(pw); 
		
		new GraphsController().doGet(stubbedRequest, stubbedResponse); 
		
		String result = sw.getBuffer().toString(); 
		assertTrue("result was " + result, result.contains("NULL_FIELD")); 
	}  
}
