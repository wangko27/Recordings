package domain.graphs.builder;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bean.Format;
import bean.Recording;
import bean.Song;
import bean.SongInstance;
import bean.graphs.GraphBean;
import bean.graphs.GraphRequestBean;
import data.RecordingData;
import data.SongInstanceData;
import data.StubRecordingData;
import domain.RecordingManager;
import domain.SongInstanceManager;
import exception.RecordingException;

public class TestGenericGraphBuilder 
{

	private GenericGraphBuilder manager; 
	
	@Before
	public void setup()
	{
		manager = new GenericGraphBuilder(new RecordingManager(new StubRecordingData())); 
	}
	
	@Test
	public void getGenericGraph_givenNumericDataSets_TicksAreEmpty() throws RecordingException
	{
		Collection<Recording> dataset = new ArrayList<Recording>(); 
		Recording bean1 = new Recording(); 
		bean1.setMonth(3); 
		bean1.setYear(5); 
		
		dataset.add(bean1); 
		
		GraphBean<Number, Number> resultGraph = manager.getGenericGraph(dataset, "Month", "Year");
						
		assertTrue("result xticks was " + resultGraph.getxTicks(), resultGraph.getxTicks().size() == 0); 
	}

	@Test
	public void getGenericGraph_duplicateTicksAreAvoided() throws RecordingException
	{
		Collection<Recording> dataset = new ArrayList<Recording>(); 
		Recording bean1 = new Recording(); 
		bean1.setMonth(3); 
		bean1.setSublocation("test"); 
		
		Recording bean2 = new Recording(); 
		bean2.setSublocation("test"); 
		bean2.setMonth(2); 
		
		dataset.add(bean1);
		dataset.add(bean2); 
		
		GraphBean<Number, Number> resultGraph = manager.getGenericGraph(dataset, "Sublocation", "Month");
						
		assertTrue("result xticks was " + resultGraph.getxTicks(), resultGraph.getxTicks().size() == 1); 
	}

	@Test
	public void getGenericGraph_givenStringDataSets_returnsDataPoints() throws RecordingException
	{
		Collection<Recording> dataset = new ArrayList<Recording>(); 
		Recording bean1 = new Recording(); 
		bean1.setLink("link 1"); 
		bean1.setSublocation("subl 1");
		
		dataset.add(bean1); 
		
		GraphBean<Number, Number> resultGraph = manager.getGenericGraph(dataset, "Link", "Sublocation");
				
		assertTrue("result data array was " + resultGraph.getDataArrays()[0][0], resultGraph.getDataArrays()[0][0].equals(0));
		assertTrue("result xticks was " + resultGraph.getxTicks(), resultGraph.getxTicks().get(0).equals("link 1")); 
	}

	@Test
	public void getGenericGraph_whenThereAreRepeatedTickMarksOnTheXAxis_dataSetContainsReferencesToThem() throws RecordingException
	{
		Collection<Recording> dataset = new ArrayList<Recording>(); 
		Recording bean1 = new Recording(); 
		bean1.setLink("link 1"); 
		bean1.setFormat(new Format("same"));
		
		Recording bean2 = new Recording(); 
		bean2.setLink("link 2"); 
		bean2.setFormat(new Format("same")); 
		
		dataset.add(bean1); 
		dataset.add(bean2); 
		
		GraphBean<Number, Number> results = manager.getGenericGraph(dataset, "Format","Link");
		
		assertTrue("result size was " + results.getxTicks().size(), results.getxTicks().size() == 1); 
		assertTrue("result was " + results.getData().get(1).getData()[0].toString(),  (Integer)results.getData().get(1).getData()[0] == 0); 
	}

	@Test
	public void getGenericGraph_whenThereAreRepeatedTickMarksOnTheYAxis_dataSetContainsReferencesToThem() throws RecordingException
	{
		Collection<Recording> dataset = new ArrayList<Recording>(); 
		Recording bean1 = new Recording(); 
		bean1.setSublocation("sublocation 1"); 
		bean1.setFormat(new Format("same"));
		
		Recording bean2 = new Recording(); 
		bean2.setSublocation("sublocation 2"); 
		bean2.setFormat(new Format("same")); 
		
		dataset.add(bean1); 
		dataset.add(bean2); 
		 
		GraphBean<Number, Number> results = manager.getGenericGraph(dataset, "Sublocation","Format");
		
		assertTrue("result was " + results.getData().get(1).getData()[1].toString(),  (Integer)results.getData().get(1).getData()[1] == 0); 
	}

	@Test
	public void getGenericGraph_givenDataThatShouldBeSorted_TickMarksAndDataIsSorted() throws RecordingException
	{			
		GraphBean<Number, Number> results = manager.getGenericGraph(prepareDataSetThatsNotSorted(), "Sublocation","Format");
		
		Format fta = new Format(); 
		fta.setValue("a"); 
		
		Format ftb = new Format();
		ftb.setValue("b"); 
		
		assertTrue("result data points were" + results.getData(), results.getData().get(0).getData()[0].equals(1) && results.getData().get(1).getData()[0].equals(0));
		assertTrue("result x ticks were " + results.getxTicks(), results.getxTicks().get(0).equals("a") && results.getxTicks().get(1).equals("b"));
		assertTrue("result y ticks were " + results.getyTicks(), results.getyTicks().get(0).equals(fta) && results.getyTicks().get(1).equals(ftb));
		assertTrue("result data points were" + results.getData(), results.getData().get(0).getData()[1].equals(1) && results.getData().get(1).getData()[1].equals(0));		 	
	}

	@Test
	public void getGenericGraph_givenDataWithNullFields_doesNotIncludeThem()
	{
		Collection<Recording> dataset = new ArrayList<Recording>(); 
		Recording bean1 = new Recording(); 
		bean1.setLink(null); 
		bean1.setSublocation(null);
		
		dataset.add(bean1); 
	
		
		try
		{
			GraphBean<Number, Number> resultGraph = manager.getGenericGraph(dataset, "Link", "Sublocation");
			assertTrue("result data was " + resultGraph.getData(), resultGraph.getData().size() == 0 );
		}	
		catch(Exception e)
		{
			assertTrue(false); 
		}
		
	}
	
	@Test
	public void getGraph_worksOkWithNullFieldsFromOtherBeans() 
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setGraphType("generic"); 
		bean.setxAxisField("Year"); 
		bean.setyAxisField("Value"); 
		
		SongInstanceData stubSongInstanceData = mock(SongInstanceData.class);
		
		SongInstance songInstance = new SongInstance(); 
		songInstance.setRecording(null); 
		Song song = new Song(); 
		song.setValue("test"); 
		songInstance.setSong(song); 				
		List<SongInstance> data = new ArrayList<SongInstance>();
		data.add(songInstance); 
		
		when(stubSongInstanceData.getAllSongInstances()).thenReturn(data); 
		
		try
		{
			new GenericGraphBuilder(new SongInstanceManager(stubSongInstanceData)).getGraph(bean);
			assertTrue(true); 
		}
		catch(RecordingException exc)
		{
			assertTrue(false); 
		}
	}
	
	@Test
	public void getGraph_worksWithBeansThatReferenceOtherBeans() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setGraphType("generic"); 
		bean.setxAxisField("Year"); 
		bean.setyAxisField("Value"); 
		
		SongInstanceData stubSongInstanceData = mock(SongInstanceData.class);
		
		SongInstance songInstance = new SongInstance(); 
		songInstance.setRecording(new Recording()); 
		Song song = new Song(); 
		song.setValue("test"); 
		songInstance.setSong(song); 				
		List<SongInstance> data = new ArrayList<SongInstance>();
		data.add(songInstance); 
		
		when(stubSongInstanceData.getAllSongInstances()).thenReturn(data); 
		
		try
		{
			new GenericGraphBuilder(new SongInstanceManager(stubSongInstanceData)).getGraph(bean);
			assertTrue(true); 
		}
		catch(RecordingException exc)
		{
			assertTrue(false); 
		}
		
	}
	
	@Test
	public void getGraph_ignoresSimpleBeansWithNullValues() throws RecordingException
	{
		GraphRequestBean request = new GraphRequestBean(); 
		request.setGraphType("generic"); 
		request.setxAxisField("Year"); 
		request.setyAxisField("Country"); 		
		
		RecordingData recordingData = mock(RecordingData.class); 
		RecordingManager recordingManager = new RecordingManager(recordingData); 
		when(recordingData.getAllRecordings()).thenReturn(GraphTestUtils.getRecordingListWithNullSimpleBeanValues()); 
		
		manager = new GenericGraphBuilder(recordingManager); 		
		
		try
		{
			manager.getGraph(request);
			assertTrue(true); 
		}
		catch(Exception exc)
		{
			exc.printStackTrace(); 
			fail(); 
		}
		
	}
	
	private Collection<Recording> prepareDataSetThatsNotSorted()
	{
		Collection<Recording> dataset = new ArrayList<Recording>(); 
		Recording bean1 = new Recording(); 
		bean1.setSublocation("a"); 
		bean1.setFormat(new Format("a"));
		
		Recording bean2 = new Recording(); 
		bean2.setSublocation("b"); 
		bean2.setFormat(new Format("b")); 
		
		dataset.add(bean2); 
		dataset.add(bean1);
		
		return dataset; 	
	}

}
