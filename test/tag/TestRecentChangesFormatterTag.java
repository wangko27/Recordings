package tag;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;


public class TestRecentChangesFormatterTag 
{

	RecentChangesFormatterTag tag; 
	
	@Before 
	public void setup()
	{
		tag = new RecentChangesFormatterTag(); 
	}
	
	@Test
	public void testTruncatingSummary()
	{		
		tag.setItem("summary"); 
		tag.setSummaryLength(1); 
		tag.setSummary("foobar"); 
		
		String result = tag.generateText();
		
		assertEquals("f ...", result); 
		// 3 for ... and 1 for .
		assertEquals(4 + tag.getSummaryLength(), result.length()); 
	}		
	
	@Test
	public void testPublishDateFormatting()
	{
		Calendar fakeDate = Calendar.getInstance(); 
		fakeDate.set(Calendar.MONTH, Calendar.JANUARY); 
		fakeDate.set(Calendar.DAY_OF_MONTH, 1); 
		fakeDate.set(Calendar.YEAR, 2011); 
		fakeDate.set(Calendar.HOUR, 3); 
		fakeDate.set(Calendar.MINUTE, 56); 
		
		tag.setItem("date"); 
		tag.setDate(fakeDate.getTime()); 
		String result = tag.generateText();
		
		assertTrue(result, result.contains("01/01/11 at 03:56")); 
	}
	
	
}
