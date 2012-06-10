package util;

import static org.junit.Assert.*;

import org.junit.Test;


public class TestHTMLSanitizer
{
	@Test
	public void testSanitize()
	{
		String result = HTMLSanitizer.sanitize("test&");
		
		assertTrue("result was " + result, result.equals("test&amp;")); 
	}
	
	@Test
	public void testSanitize_whenTextIsNull()
	{
		String result = HTMLSanitizer.sanitize(null);
		
		assertTrue("result was " + result, result.equals("")); 
	}
}
