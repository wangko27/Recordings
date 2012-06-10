package data.db.RecordingData;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

import bean.Recording;
import bean.RecordingType;
import data.RecordingData;
import data.SimpleEntityData;

public class TestRecordingData_create {
	
	private RecordingData data = new RecordingData();
	
	@Test
	public void testThis()
	{
		assertTrue(true);
	}
	
	@Ignore
	@Test
	public void testCreateRecording()
	{
		SimpleEntityData simpleEntityData = new SimpleEntityData();
		RecordingType recordingType = (RecordingType) simpleEntityData.getSimpleBean("Show", RecordingType.class);
		Recording recording = new Recording();
		recording.setRecordingType(recordingType);
		
		try
		{
			data.saveOrUpdate(recording);
		}
		catch( Exception e )
		{
			fail(e.getMessage());
		}
		
		assertTrue(true);
	}

}
