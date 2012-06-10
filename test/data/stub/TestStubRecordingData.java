package data.stub;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.junit.Test;

import bean.City;
import bean.Recording;
import data.StubRecordingData;
import data.common.IRecordingData;
import exception.RecordingException;

public class TestStubRecordingData
{
	@Test
	public void groupRecordingsByCount_givenCollectionWithNullFields_doesNotIncludeThem()
	{
		Recording bean1 = new Recording(); 
		
		Collection<Recording> beans = new ArrayList<Recording>(); 
		beans.add(bean1); 
		
		IRecordingData data = new StubRecordingData();
		
		try 
		{
			Map<Comparable<?>, Integer> results = data.groupRecordingsByCount("Link", beans);
			assertTrue("results were " + results, results.size() == 0); 
			
			
		} catch (Exception e)
		{
			e.printStackTrace(); 
			assertTrue(false); 
		} 
	}
	
	@Test
	public void groupRecordingsByCount_givenCollectionWithMonthFieldsSet_returnsCorrectResults()
	{
		Recording bean1 = new Recording(); 
		bean1.setMonth(1); 
		Recording bean2 = new Recording(); 
		bean2.setMonth(2); 
		Recording bean3 = new Recording(); 
		bean3.setMonth(1);
		
		Collection<Recording> beans = new ArrayList<Recording>(); 
		beans.add(bean3); 
		beans.add(bean2); 
		beans.add(bean1); 
		
		IRecordingData data = new StubRecordingData();
		
		try 
		{
			Map<Comparable<?>, Integer> results = data.groupRecordingsByCount("Month", beans);
			assertTrue("results wwere " + results, results.containsKey(1) && results.containsKey(2)); 
			
			
		} catch (Exception e)
		{
			e.printStackTrace(); 
			assertTrue(false); 
		} 
	}
	
	@Test
	public void groupRecordingsByCommonValue_givenValueAndDataSet_returnsCorrectMap() throws RecordingException
	{
		City c1 = new City("Raleigh");
		City c2 = new City("Raleigh");
		City c3= new City("Asheville");
		
		Recording bean1 = new Recording(); 
		bean1.setMonth(1); 
		bean1.setCity(c1); 
		Recording bean2 = new Recording(); 
		bean2.setMonth(1);
		bean2.setCity(c2); 
		Recording bean3 = new Recording(); 
		bean3.setMonth(1);
		bean3.setCity(c3); 
		
		Collection<Recording> beans = new ArrayList<Recording>(); 
		beans.add(bean3); 
		beans.add(bean2); 
		beans.add(bean1); 
		
		StubRecordingData data = new StubRecordingData(); 
		
		Map<Comparable<?>, Integer > results = data.groupRecordingsByCommonValue("City", c1, "Month", beans); //TODO city is no longer a primitive
		
		assertTrue(results.containsKey(1) && results.get(1)== 2); 
	}
	
	
}
