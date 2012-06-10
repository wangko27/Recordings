package data.db.RecordingData;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import bean.Recording;
import bean.Song;
import bean.SongInstance;
import data.HibernateTest;
import data.RecordingData;

public class TestRecordingData_bySong extends HibernateTest  {
	
	private RecordingData data = new RecordingData();
	
	@Test
	public void testGetRecordingsAssociatedWithSong()
	{
		int songId = 515;
		List<Recording> recordings = data.getRecordingsAssociatedWithSong(songId);
		
		for (Recording recording : recordings) {
			List<SongInstance> songInstances = recording.getSongInstances();
			boolean hasSong = false;
			for (SongInstance songInstance : songInstances) {
				Song song = songInstance.getSong();
				
				if(songId == song.getId() )
				{
					hasSong = true;
					break;
				}
			}
			
			assertTrue(hasSong);
		}
	}

}
