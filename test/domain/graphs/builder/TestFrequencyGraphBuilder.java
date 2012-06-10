package domain.graphs.builder;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import bean.graphs.GraphBean;
import data.StubRecordingData;
import domain.RecordingManager;
import exception.RecordingException;

public class TestFrequencyGraphBuilder 
{
	private FrequencyGraphBuilder manager; 
	
	@Before
	public void setup()
	{
		manager= new FrequencyGraphBuilder(new RecordingManager(new StubRecordingData())); 
	}

	@Test
	public void getFrequencyGraph_givenMapOfDateAndInt_xTicksContainsDatesAndResultIsMapOfInts()
	{
		Map<Comparable<?>, Integer> frequencyData = new LinkedHashMap<Comparable<?>, Integer>();
		
		Calendar date1 = Calendar.getInstance(); 		
		date1.set(2000, 1, 2); 
		
		Calendar date2 = Calendar.getInstance(); 
		date2.set(2001, 2, 3); 
		
		Calendar date3 = Calendar.getInstance();
		date3.set(2002, 5, 6); 
						
		frequencyData.put(date1.getTime(), 1); 
		frequencyData.put(date2.getTime(), 2); 
		frequencyData.put(date3.getTime(), 3); 
		
		GraphBean<Double, Integer> results = manager.getFrequencyGraph( frequencyData);
		
		List<?> xticks = results.getxTicks(); 
		assertTrue("xticks list was " + xticks.toString(),  xticks.get(0).toString().contains("2000") && xticks.get(1).toString().contains("2001") &&  xticks.get(2).toString().contains("2002"));		
		assertTrue("result data list was " + results.getData(), (Integer)results.getData().get(0).getData()[1] == 1 && (Integer)results.getData().get(1).getData()[1] == 2 && (Integer)results.getData().get(2).getData()[1] == 3); 		
	}

	@Test
	public void getFrequencyGraph_givenMapOfStringInt_xTicksContainsStringsAndResultIsAMapOfInts()
	{
	
		Map<Comparable<?>, Integer> frequencyData = new HashMap<Comparable<?>, Integer>();
		
		frequencyData.put("test1", 1); 
		frequencyData.put("test2", 2); 
		frequencyData.put("test3", 3); 
		
		GraphBean<Double, Integer> results = manager.getFrequencyGraph(frequencyData);
		
		List<?> xticks = results.getxTicks(); 
		assertTrue("xticks list was " + xticks.toString(),  xticks.contains("test1") && xticks.contains("test2") && xticks.contains("test3"));
		
		assertTrue("result data list was " + results.getData(), (Integer)results.getData().get(0).getData()[1] == 1 && (Integer)results.getData().get(1).getData()[1] == 2 && (Integer)results.getData().get(2).getData()[1] == 3); 
		
	}

	@Test
	public void getFrequencyGraph_givenDataThatShouldBeSorted_TickMarksAndDataIsSorted() throws RecordingException
	{			
	
		Map<Comparable<?>, Integer> data = new HashMap<Comparable<?>, Integer>(); 
		data.put("b", 3);
		data.put("a", 5); 
		
		GraphBean<Double, Integer> results = manager.getFrequencyGraph(data); 
		
		assertTrue("result data points were" + results.getData(), results.getData().get(0).getData()[0].equals(1.0) && results.getData().get(1).getData()[0].equals(0.0));
		assertTrue("result x ticks were " + results.getxTicks(), results.getxTicks().get(0).equals("a") && results.getxTicks().get(1).equals("b"));				 
	}

	@Test
	public void getFrequencyGraph_whenDataSetContainsNullValues_doesNotIncludeThemInFinalGraphBean()
	{
		Map<Comparable<?>, Integer> testMap = new HashMap<Comparable<?>, Integer>();
		testMap.put(null, 3); 
		
		GraphBean<?, ?> resultBean = manager.getFrequencyGraph(testMap);
		
		assertTrue("result bean size was " + resultBean.getData().size(), resultBean.getData().size() == 0); 
	}
	

}
