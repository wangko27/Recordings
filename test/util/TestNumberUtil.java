package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class TestNumberUtil {
	
	@Test
	public void testGetNextHighestModValue()
	{
		assertEquals(5, NumberUtil.getNextHighestModValue(4,5));
		assertEquals(20, NumberUtil.getNextHighestModValue(17,5));
		assertEquals(20, NumberUtil.getNextHighestModValue(20,5));
		assertEquals(20, NumberUtil.getNextHighestModValue(20,20));
	}

	@Test
	public void testGetRandomNumber()
	{
		int max = 15;
		Set<Integer> intSet = new HashSet<Integer>();
		for( int x = 0; x < 99999; x++ )
		{
			int random = NumberUtil.getRandomNumber(max);
			
			assertTrue("random: " + random, random <= max );
			assertTrue("random: " + random, random > 0 );
			
			intSet.add(random);
		}
		
		assertEquals(max, intSet.size());
	}
}
