package data.db.RecordingData;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import bean.Recording;
import data.HibernateTest;
import data.RecordingData;

public class TestRecordingData_delete extends HibernateTest {
	
	private RecordingData data = new RecordingData();
	
	@Test
	// note: will fail on local machine 
	public void testDelete()
	{
		Recording recording = data.getRecording(322);
		assertNotNull(recording);
		
		data.delete(recording);
		
		Recording recording1 = data.getRecording(322);
		assertNull(recording1);
	}
}
