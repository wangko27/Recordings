package data.db;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import bean.RecordingType;
import data.SequentialRecordingIdData;
import enumeration.SequentialDirection;

public class TestSequentialRecordingData 
{
	private SequentialRecordingIdData sequentialRecordingIdData; 
	
	@Before 
	public void setup()
	{
		sequentialRecordingIdData = new SequentialRecordingIdData(); 
	}
	
	@Test
	public void testOnRegularRecordings() throws Exception
	{
		Integer result = sequentialRecordingIdData.getSequentialRecordingId(1, SequentialDirection.NEXT);
		
		assertNotNull(result);
		assertTrue(" next result was " + result, result == 2);
		
		result = sequentialRecordingIdData.getSequentialRecordingId(2, SequentialDirection.PREVIOUS); 
		assertNotNull(result); 
		assertTrue(" prev result was " + result, result == 1); 
	}
	
	@Test
	public void testOnAlbums() throws Exception
	{
		Integer result = sequentialRecordingIdData.getSequentialRecordingId(321, SequentialDirection.NEXT, new RecordingType("album"));
		assertNotNull(result);
		assertTrue("next result was " + result, result > 50); 
		
		result = sequentialRecordingIdData.getSequentialRecordingId(result, SequentialDirection.PREVIOUS, new RecordingType("album"));
		assertNotNull(result);
		assertTrue("prev result was " + result, result > 50); 
	}	
}
