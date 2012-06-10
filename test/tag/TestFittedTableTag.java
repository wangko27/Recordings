package tag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import bean.TableElement;

public class TestFittedTableTag 
{
	// in response to bug
	// Result: My code is working properly.
	// For some reason the browser is rendering the table weird
	// in some situations. It's not consistent.
	@Test
	public void testTwoCols24Rows()
	{
		FittedTableTag tag = new FittedTableTag();
		
		List<TableElement> tableElements = new ArrayList<TableElement>();
		for( int x = 0; x < 24; x++ )
			tableElements.add(new TableElement("col" + x,"value" + x));
		
		tag.setCols(4);
		tag.setTableElements(tableElements);
		
		String html = tag.generateHTML();
		
		// the bug is that for some reason when 24 rows are present,
		// it's just keeping everything in one big column
		assertEquals(6, getNumOfOccurences(html, "<tr>"));
		assertEquals(24, getNumOfOccurences(html, "<th class="));
		assertEquals(24, getNumOfOccurences(html, "<td"));
	}
	
	@Test
	public void testFourColsThreeGaps()
	{
		FittedTableTag tag = new FittedTableTag();
		
		List<TableElement> tableElements = new ArrayList<TableElement>();
		for( int x = 0; x < 17; x++ )
			tableElements.add(new TableElement("col" + x,"value" + x));
		
		tag.setCols(4);
		tag.setTableElements(tableElements);
		
		String html = tag.generateHTML();
		
		assertEquals(6, getNumOfOccurences(html, "&nbsp;"));
	}
	
	@Test
	public void testThreeColsTwoGaps()
	{
		FittedTableTag tag = new FittedTableTag();
		
		List<TableElement> tableElements = new ArrayList<TableElement>();
		for( int x = 0; x < 7; x++ )
			tableElements.add(new TableElement("col" + x,"value" + x));
		
		tag.setCols(3);
		tag.setTableElements(tableElements);
		
		String html = tag.generateHTML();
		
		assertEquals(4, getNumOfOccurences(html, "&nbsp;"));
	}
	
	@Test
	public void testThreeColsOneGap()
	{
		FittedTableTag tag = new FittedTableTag();
		
		List<TableElement> tableElements = new ArrayList<TableElement>();
		for( int x = 0; x < 8; x++ )
			tableElements.add(new TableElement("col" + x,"value" + x));
		
		tag.setCols(3);
		tag.setTableElements(tableElements);
		
		String html = tag.generateHTML();
		
		assertEquals(2, getNumOfOccurences(html, "&nbsp;"));
	}
	
	@Test
	public void testTwoColsOneGap()
	{
		FittedTableTag tag = new FittedTableTag();
		
		List<TableElement> tableElements = new ArrayList<TableElement>();
		for( int x = 0; x < 7; x++ )
			tableElements.add(new TableElement("col" + x,"value" + x));
		
		tag.setCols(2);
		tag.setTableElements(tableElements);
		
		String html = tag.generateHTML();
		
		assertEquals(2, getNumOfOccurences(html, "&nbsp;"));
	}
	
	@Test
	public void testOneColNoGap()
	{
		FittedTableTag tag = new FittedTableTag();
		
		int rows = 6;
		List<TableElement> tableElements = new ArrayList<TableElement>();
		
		for( int x = 0; x < rows; x++ )
			tableElements.add(new TableElement("col" + x,"value" + x));
		
		tag.setCols(1);
		tag.setTableElements(tableElements);
		
		String html = tag.generateHTML();
		
		assertEquals(rows, getNumOfOccurences(html, "<tr>"));
		
		// make sure that each header and value occur only once
		for( int x = 0; x < rows; x++ )
		{
			assertEquals(1, getNumOfOccurences(html, "col" + x));
			assertEquals(1, getNumOfOccurences(html, "value" + x));
		}
	}
	
	@Test
	public void testTwoColsNoGap()
	{
		FittedTableTag tag = new FittedTableTag();
		
		int rows = 8;
		List<TableElement> tableElements = new ArrayList<TableElement>();
		
		for( int x = 0; x < rows; x++ )
			tableElements.add(new TableElement("col" + x,"value" + x));
		
		tag.setCols(2);
		tag.setTableElements(tableElements);
		
		String html = tag.generateHTML();
		
		assertEquals(rows/2, getNumOfOccurences(html, "<tr>"));
		
		// make sure that each header and value occur only once
		for( int x = 0; x < rows; x++ )
		{
			assertEquals(1, getNumOfOccurences(html, "col" + x));
			assertEquals(1, getNumOfOccurences(html, "value" + x));
		}
	}
	
	@Test
	public void testThreeColsNoGap()
	{
		FittedTableTag tag = new FittedTableTag();
		
		int rows = 9;
		List<TableElement> tableElements = new ArrayList<TableElement>();
		
		for( int x = 0; x < rows; x++ )
			tableElements.add(new TableElement("col" + x,"value" + x));
		
		tag.setCols(3);
		tag.setTableElements(tableElements);
		
		String html = tag.generateHTML();
		
		assertEquals(rows/3, getNumOfOccurences(html, "<tr>"));
		
		// make sure that each header and value occur only once
		for( int x = 0; x < rows; x++ )
		{
			assertEquals(1, getNumOfOccurences(html, "col" + x));
			assertEquals(1, getNumOfOccurences(html, "value" + x));
		}
	}
	
	@Test
	public void testFourColsNoGap()
	{
		FittedTableTag tag = new FittedTableTag();
		
		int rows = 12;
		List<TableElement> tableElements = new ArrayList<TableElement>();
		
		for( int x = 0; x < rows; x++ )
			tableElements.add(new TableElement("col" + x,"value" + x));
		
		tag.setCols(4);
		tag.setTableElements(tableElements);
		
		String html = tag.generateHTML();
		
		assertEquals(rows/4, getNumOfOccurences(html, "<tr>"));
		
		// make sure that each header and value occur only once
		for( int x = 0; x < rows; x++ )
		{
			// 1 was clashing with 10
			if( x != 1 )
			{
				assertEquals(1, getNumOfOccurences(html, "col" + x));
				assertEquals(1, getNumOfOccurences(html, "value" + x));
			}
		}
	}

	private int getNumOfOccurences( String s, String match )
	{
		return s.split(match).length-1;
	}
	
	@Test
	public void testCssClassIsIncluded()
	{
		FittedTableTag tag = new FittedTableTag();
		List<TableElement> elements = new ArrayList<TableElement>(); 
		elements.add(new TableElement("foo", 3)); 
		tag.setTableElements(elements); 
		tag.setCols(3); 
		tag.setCssclass("fooclass"); 
		
		String result = tag.generateHTML();				
		
		assertTrue("results were " + result,  result.contains("fooclass")); 
	}

}
