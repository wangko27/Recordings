package data.db.SequentialRecordingIdData;

import static org.junit.Assert.*;

import org.junit.Test;

import data.SequentialRecordingIdData;
import enumeration.SequentialDirection;

public class TestSequentialRecordingIdData_NEXT {
	
	private SequentialRecordingIdData data = new SequentialRecordingIdData();
	
	@Test
	public void testGetNextRecordingId_lowerBoundry()
	{
		try
		{
			int startingId = -5;
			Integer nextRecordingId = data.getSequentialRecordingId(startingId, SequentialDirection.NEXT);
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
			Integer nextRecordingId = data.getSequentialRecordingId(startingId, SequentialDirection.NEXT);
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
			int startingId = 2;
			Integer nextRecordingId = data.getSequentialRecordingId(startingId, SequentialDirection.NEXT);
			assertNotNull(nextRecordingId);
			assertEquals(new Integer(startingId+1), nextRecordingId);
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
			int startingId = 1;
			Integer nextRecordingId = data.getSequentialRecordingId(startingId, SequentialDirection.NEXT);
			assertNotNull(nextRecordingId);
			assertEquals(new Integer(startingId+1), nextRecordingId);
		}
		catch( Exception e )
		{
			fail(e.getMessage());
		}
	}

}
