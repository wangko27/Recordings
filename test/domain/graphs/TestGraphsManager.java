package domain.graphs;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import bean.Recording;
import bean.RecordingComment;
import bean.graphs.GraphBean;
import bean.graphs.GraphRequestBean;
import data.RecordingCommentData;
import data.StubRecordingData;
import domain.CommentsManager;
import domain.RecordingManager;
import domain.SongInstanceManager;
import domain.common.IGraphDataAccessor;
import exception.RecordingException;

public class TestGraphsManager 
{
	GraphsManager manager; 
	
	private SongInstanceManager stubSongInstanceManager; 
	
	@Before
	public void startUp()
	{
		stubSongInstanceManager = mock(SongInstanceManager.class); 
		when(stubSongInstanceManager.getNumberOfNewSongsPerYear()).thenReturn(new HashMap<Comparable<?>, Integer>()); 
		
		RecordingCommentData stubRecordingCommentData = mock(RecordingCommentData.class); 
		manager = new GraphsManager(new RecordingManager(new StubRecordingData()), new CommentsManager( stubRecordingCommentData, RecordingComment.class), stubSongInstanceManager); 
		
	}
	

	@Test
	public void toJSONString_givenGraphBeanWith2DDataSet_returnsCorrectString()
	{
		GraphBean<Double, Double> bean = new GraphBean<Double,Double>(); 
		bean.addDataPoint(1.0, 2.0); 
		bean.addDataPoint(2.0,4.0);
		
		String result = manager.toJSONString(bean); 
		
		assertTrue("result string was null", result != null); 
		assertTrue("result string was " + result, result.contains("[1.0,2.0]") && result.contains("[2.0,4.0]")); 		
	}
	
	@Test
	public void toJSONString_givenGraphBeanWith1DDataSet_returnsCorrectString()
	{
		GraphBean<Double,Double> bean = new GraphBean<Double,Double>(); 
		bean.addDataPoint(1.0); 
		bean.addDataPoint(2.0);
		
		String result = manager.toJSONString(bean); 
		
		assertTrue("result string was null", result != null); 
		assertTrue("result string was " + result, result.contains("[1.0]") && result.contains("[2.0]"));
	}
	
	@Test
	public void getExpectedType_givenAStringField_returnsStringType() throws RecordingException
	{
		Class<?> type = manager.getExpectedType("Sublocation"); 				
		
		assertTrue("result class was " + type,  type.equals(String.class)); 
	}	

	
	@Test
	public void isInteger_worksForAppropriateValues()
	{

		Class<?> intType = int.class;
		Class<?> intType2 = Integer.class;
		
		assertTrue(manager.isInteger(intType2)); 
		assertTrue(manager.isInteger(intType)); 
	}
	
	@Test
	public void getGraphDataType_givenARecordingGraphRequest_returnsCorrectDataAccessType()
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setGraphDataSource("recording"); 
		
		IGraphDataAccessor result = manager.getGraphDataType(bean); 
		assertTrue(result.getDataClass().equals(Recording.class)); 
	}
	
	@Test
	public void getGraphDataType_givenARecordingCommentGraphRequest_returnsCorrectDataAccessType()
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setGraphDataSource("recordingcomments"); 
		
		IGraphDataAccessor result = manager.getGraphDataType(bean); 
		assertTrue(result.getDataClass().equals(RecordingComment.class)); 
	}
	
	@Test
	public void getGraph_testInvalidGraphType() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setGraphType("unsupported"); 
		bean.setGraphDataSource("recording"); 
		
		GraphBean<?, ?> result = manager.getGraph(bean); 
		
		assertTrue(result ==null); 
	} 
	
	@Test
	public void getGraph_generatesCorrectFrequencyGraph() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setGraphType("frequency");
		bean.setxAxisField("Month"); 
		bean.setGraphDataSource("recording"); 
		
		GraphBean<?, ?> result = manager.getGraph(bean); 
		
		assertTrue(result != null); 
		assertTrue(result.getyAxisLabel().equals("Frequency")); 
		assertTrue(result.getType().equals("frequency")); 
	}
	
	@Test
	public void getGraph_whenStyleIsPie_generatesCorrectFrequencyGraph() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setGraphType("frequency");
		bean.setxAxisField("Month"); 
		bean.setGraphDataSource("recording"); 
		bean.setGraphStyle("pie"); 
		
		GraphBean<?, ?> result = manager.getGraph(bean); 
		
		assertTrue(result != null); 
		assertTrue(result.getData().size() > 0); 
		assertTrue(result.getStyle().equals("pie")); 
		assertTrue(result.getType().equals("frequency")); 
	}
	
	@Test
	public void getGraph_givenCorrectGraphRequestForFrequencyWithValue_returnsValidGraphBean() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setGraphType("frequencywithvalue");
		bean.setxAxisField("Month");
		bean.setGraphTargetColumn("Year"); 
		bean.setGraphTargetColumnValue("1988"); 
		bean.setGraphDataSource("recording"); 
		
		GraphBean<?, ?> result = manager.getGraph(bean); 
		
		assertTrue(result != null); 
		assertTrue(result.getData().size() > 0); 
		assertTrue(result.getType().equals("frequencywithvalue")); 
	}
	
	@Test
	public void getGraph_frequencyWitValue_worksForVariousTypes() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setGraphType("frequencywithvalue");
		bean.setxAxisField("Month");
		bean.setGraphTargetColumn("Sublocation"); 
		bean.setGraphTargetColumnValue("Brooklyn"); 
		bean.setGraphDataSource("recording"); 
		
		GraphBean<?, ?> result = manager.getGraph(bean); 
		
		assertTrue(result != null); 
		assertTrue(result.getData().size() > 0); 
		assertTrue(result.getType().equals("frequencywithvalue")); 
	}
	
	@Test
	public void getGraph_getGenericGraph_generatesValidGraphForValidData() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setGraphType("generic");
		bean.setxAxisField("City");
		bean.setyAxisField("Country"); 
		bean.setGraphDataSource("recording"); 
	
		GraphBean<?, ?> result = manager.getGraph(bean); 
		
		assertTrue(result != null); 
		assertTrue(result.getData().size() > 0); 
		assertTrue(result.getType().equals("generic")); 
	}

	@Test
	public void getGraph_getGenericGraph_whenFieldsAreIntegers_axisShouldBeRounded() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setGraphType("generic");
		bean.setxAxisField("Month");
		bean.setyAxisField("Year"); 
		bean.setGraphDataSource("recording"); 
	
		GraphBean<?, ?> result = manager.getGraph(bean); 
		
		assertTrue(result.getRoundXAxis()); 
		assertTrue(result.getRoundYAxis()); 
	}

	@Test
	public void getGraph_getSpecializedFirstAppearanceGraph_returnsNonNull() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setGraphType("specialized"); 
		bean.setGraphSpecialization("firstappearance"); 
		bean.setGraphDataSource("songinstance"); 
		
		GraphBean<?, ?> result = manager.getGraph(bean); 
		
		assertNotNull(result); 
	}
	
	@Test
	public void getGraph_worksForVoteFrequencyGraph() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setGraphType("frequency"); 
		bean.setGraphDataSource("recording"); 
		bean.setxAxisField("Votes"); 
		
		GraphBean<?, ?> result = manager.getGraph(bean); 
		
		assertNotNull(result); 
	}
	
	@Test
	public void getGraph_worksForVoteFrequencyWithValue() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setGraphType("frequencywithvalue"); 
		bean.setGraphDataSource("recording"); 
		bean.setxAxisField("Year"); 
		bean.setGraphTargetColumn("Votes"); 
		bean.setGraphTargetColumnValue("0"); 
		
		GraphBean<?, ?> result = manager.getGraph(bean); 
		
		assertNotNull(result); 
	}
	
	@Test
	public void getGraph_worksForVoteGenericGraph() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setGraphType("generic"); 
		bean.setGraphDataSource("recording"); 
		bean.setxAxisField("Year"); 
		bean.setyAxisField("Votes");  
		
		GraphBean<?, ?> result = manager.getGraph(bean); 
		
		assertNotNull(result); 
	}
}
