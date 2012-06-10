package bean.graphs;

import static org.junit.Assert.*;

import org.junit.Test;

import bean.graphs.GraphBean;


public class TestGraphBean 
{
	
	@Test
	public void getDataArrays_withAValidSetOfDataPoints_Returns2DArrayRepresentation()
	{
		GraphBean<Integer, Integer> bean = new GraphBean<Integer,Integer>(); 
		bean.addDataPoint(0, 1); 
		bean.addDataPoint(1, 2); 
		
		Object[][] results = bean.getDataArrays(); 
		
		assertTrue("result array was null", results != null); 
		assertTrue("results were: " + results[0][0] + "," + results[0][1] + " :: "  + results[1][0] + "," + results[1][1], (Integer)results[0][0] == 0 && (Integer)results[0][1] == 1 && (Integer)results[1][0] == 1 && (Integer)results[1][1] == 2); 		
	}
	
	@Test
	public void getDataArray_worksWithOneDimensionalDataSets()
	{
		GraphBean<Integer, Integer> bean = new GraphBean<Integer,Integer>(); 
		bean.addDataPoint(1); 
		bean.addDataPoint(2); 
		
		Object[][] results = bean.getDataArrays(); 
		assertTrue("result array was null", results != null); 
		assertTrue("results were: " + results[0] + "," + results[1], (Integer)results[0][0] == 1 && (Integer)results[1][0] == 2); 
	}
}
