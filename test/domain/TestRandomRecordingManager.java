package domain;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import bean.Recording;

public class TestRandomRecordingManager {
	
	private RecordingManager recordingManager = null; 
	private RandomRecordingManager manager = null; 
	
	@Before
	public void setup()
	{
		recordingManager = mock(RecordingManager.class);
		manager = new RandomRecordingManager(recordingManager);
		
		Collection<Recording> recordings = new ArrayList<Recording>(); 
		for(int i=0;i<100;i++)
		{
			Recording rec = new Recording(); 
			rec.setId(i); 
			recordings.add(rec); 
		}
		
		when(recordingManager.getAllRecordings()).thenReturn(recordings); 
	}
	
	@Test
	public void testGetRandomNumber()
	{
		Set<Integer> intSet = new HashSet<Integer>();
		for( int x = 0; x < 5; x++ )
		{
			int randomRecordingId = manager.getRandomRecordingId();
			assertTrue(randomRecordingId > 0);
			
			intSet.add(randomRecordingId);
		}
		
		assertTrue(intSet.size() > 3);
	}

}
