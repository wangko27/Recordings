package util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestStringUtil {
	
	@Test
	public void testGetNumOfOccurances()
	{
		assertEquals(0, StringUtil.getNumOfOccurances(null, null));
		assertEquals(0, StringUtil.getNumOfOccurances("", ""));
		assertEquals(0, StringUtil.getNumOfOccurances("test", null));
		assertEquals(0, StringUtil.getNumOfOccurances(null, "test"));
		assertEquals(0, StringUtil.getNumOfOccurances("test", "_"));
		assertEquals(1, StringUtil.getNumOfOccurances("test_test", "_"));
		assertEquals(2, StringUtil.getNumOfOccurances("test_test_test", "_"));
		assertEquals(1, StringUtil.getNumOfOccurances("_", "_"));
		assertEquals(2, StringUtil.getNumOfOccurances("__", "_"));
	}
}
