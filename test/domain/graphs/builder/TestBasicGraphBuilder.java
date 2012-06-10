package domain.graphs.builder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bean.Venue;
import bean.graphs.GraphBean;
import bean.graphs.GraphRequestBean;
import data.StubRecordingData;
import domain.RecordingManager;
import domain.common.IGraphDataAccessor;
import exception.RecordingException;

public class TestBasicGraphBuilder 
{

	private BasicGraphBuilder target; 
	
	private class ConcreteGraphBuilder extends BasicGraphBuilder {

		public ConcreteGraphBuilder(IGraphDataAccessor dataAccessor) {
			super(dataAccessor);
		}

		@Override
		public GraphBean<?, ?> getBasicGraph(GraphRequestBean request)
				throws RecordingException {
			return null;
		}}
	
	@Before
	public void setup()
	{		
		target = new ConcreteGraphBuilder( new RecordingManager(new StubRecordingData())); 
	}
	
	@Test
	public void isNumeric_givenString_returnsFalse()
	{
		assertFalse(target.isNumeric("testString")); 
	}

	@Test
	public void isNumeric_givenNumericType_returnsTrue()
	{
		assertTrue(target.isNumeric(new Integer(3))); 
		assertTrue(target.isNumeric(3)); 
		assertTrue(target.isNumeric(3.0f));
		assertTrue(target.isNumeric(3.0));
		assertTrue(target.isNumeric(new Double(5.0)));
		assertTrue(target.isNumeric(new Float(5.0f)));
	}

	@Test
	public void addToTicksList_givenNumericalValues_ticksListIsEmpty()
	{
		List<Comparable> testTicks = new ArrayList<Comparable>(); 
		
		Number result = target.addToTicksList(testTicks, 3);
		assertTrue(testTicks.size() == 0);
		assertTrue(result.equals(3)); 
	}

	@Test
	public void addToTicksList_givenIdenticalStringValues_ticksListSizeIsJustOne()
	{
		List<Comparable> testTicks = new ArrayList<Comparable>(); 
		
		target.addToTicksList(testTicks, "hello");
		target.addToTicksList(testTicks, "hello");
		
		assertTrue(testTicks.size() == 1);		
	}

	@Test
	public void addToTicksList_givenIdenticalObjectValues_ticksListSizeIsJustOne()
	{
		List<Comparable> testTicks = new ArrayList<Comparable>(); 
		
		Venue test = new Venue(); 
		test.setValue("hello"); 
		
		Venue test2 = new Venue(); 
		test2.setValue("hello"); 
		
		Number fResult = target.addToTicksList(testTicks, test);
		Number sResult = target.addToTicksList(testTicks, test2);
				
		assertTrue(fResult.equals(0) && sResult.equals(0));
		assertTrue("tick size was " + testTicks.size(), testTicks.size() == 1);
	}

	@Test
	public void addToTicksList_givenStringValues_ticksListContainsString()
	{
		List<Comparable> testTicks = new ArrayList<Comparable>(); 
		
		Number result = target.addToTicksList(testTicks, "hello");
		assertTrue(testTicks.size() == 1);
		assertTrue("result was " + result, result.equals(0)); 
	}

}
