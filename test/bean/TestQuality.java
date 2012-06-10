package bean; 

import static org.junit.Assert.*;

import org.junit.Test;

public class TestQuality 
{

	@Test
	public void testComparingOfQuality()
	{
		Quality q1 = new Quality("Excellent"); 
		Quality q2 = new Quality("Very Good"); 
		Quality q3 = new Quality("Good/Very Good"); 
		Quality q4 = new Quality("Good"); 
		Quality q5 = new Quality("Fair"); 
		Quality q6 = new Quality("Poor/Good"); 
		Quality q7 = new Quality("Poor"); 
	
		assertTrue(q1.compareTo(q2) > 0 && q2.compareTo(q3) > 0 && q3.compareTo(q4) > 0
				   && q4.compareTo(q5) > 0 && q5.compareTo(q6) > 0 && q6.compareTo(q7) > 0); 
	}
}
