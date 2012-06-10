package data.db.SequentialRecordingIdData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import data.SequentialRecordingIdData;
import enumeration.SequentialDirection;

public class TestSequentialRecordingIdData_PREV {
	
	private SequentialRecordingIdData data = new SequentialRecordingIdData();
	
	@Test
	public void testGetNextRecordingId_lowerBoundry2()
	{
		try
		{
			int startingId = 1;
			Integer nextRecordingId = data.getSequentialRecordingId(startingId, SequentialDirection.PREVIOUS);
			assertNull(nextRecordingId);
		}
		catch( Exception e )
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetNextRecordingId_lowerBoundry1()
	{
		try
		{
			int startingId = -5;
			Integer nextRecordingId = data.getSequentialRecordingId(startingId, SequentialDirection.PREVIOUS);
			assertNull(nextRecordingId);
		}
		catch( Exception e )
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetNextRecordingId_higherBoundry()
	{
		try
		{
			int startingId = 9999;
			Integer nextRecordingId = data.getSequentialRecordingId(startingId, SequentialDirection.PREVIOUS);
			assertNull(nextRecordingId);
		}
		catch( Exception e )
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetNextRecordingId_standard2()
	{
		try
		{
			int startingId = 3;
			Integer nextRecordingId = data.getSequentialRecordingId(startingId, SequentialDirection.PREVIOUS);
			assertNotNull(nextRecordingId);
			assertEquals(new Integer(startingId-1), nextRecordingId);
		}
		catch( Exception e )
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetNextRecordingId_standard1()
	{
		try
		{
			int startingId = 2;
			Integer nextRecordingId = data.getSequentialRecordingId(startingId, SequentialDirection.PREVIOUS);
			assertNotNull(nextRecordingId);
			assertEquals(new Integer(startingId-1), nextRecordingId);
		}
		catch( Exception e )
		{
			fail(e.getMessage());
		}
	}
	
	

}
