package bean;

import static org.junit.Assert.*;

import org.junit.Test;

import bean.SimpleBean;
 

public class TestSimpleBean 
{

	@Test
	public void compareTo_returnsPositiveForSortedStrings()
	{
		SimpleBean first = new SimpleBean("b"); 
		SimpleBean second = new SimpleBean("a"); 
		
		assertTrue(first.compareTo(second) > 0); 
	}
	
	@Test
	public void compareTo_returnsZeroForEqualStrings()
	{
		assertTrue(new SimpleBean("a").compareTo(new SimpleBean("a")) == 0); 
	}
	
	@Test
	public void compareTo_worksForNullValues()
	{
		assertTrue(new SimpleBean( (String)null).compareTo(new SimpleBean("a")) < 0);
		assertTrue(new SimpleBean( "a").compareTo(new SimpleBean( (String) null)) > 0); 
		assertTrue(new SimpleBean((String)null).compareTo(new SimpleBean((String)null)) == 0); 
	}
}
