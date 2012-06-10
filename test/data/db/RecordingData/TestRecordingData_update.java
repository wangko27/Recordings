package data.db.RecordingData;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import bean.Recording;
import data.HibernateTest;
import data.RecordingData;
import data.common.IRecordingData;

public class TestRecordingData_update  extends HibernateTest 
{
	private IRecordingData data = new RecordingData();
	
	@Test
	public void testUpdate_monthWithNull()
	{
		int recordingId = 3;
		Recording recording = data.getRecording(recordingId);
		
		Integer newMonth = null;
		
		recording.setMonth(newMonth);
		
		data.saveOrUpdate(recording);
		
		Recording updatedRecording = data.getRecording(recordingId);
		
		assertEquals(newMonth, updatedRecording.getMonth());
	}
	
	@Test
	public void testUpdate_monthWithValue()
	{
		int recordingId = 5;
		Recording recording = data.getRecording(recordingId);
		
		int oldMonth = recording.getMonth();

		Integer newMonth = 0;
		if( oldMonth > 11 )
			newMonth = oldMonth - 1;
		else
			newMonth = oldMonth + 1;
		
		recording.setMonth(newMonth);
		
		data.saveOrUpdate(recording);
		
		Recording updatedRecording = data.getRecording(recordingId);
		
		assertEquals(newMonth, updatedRecording.getMonth());
	}
}
