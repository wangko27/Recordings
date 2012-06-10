package domain;

import java.util.List;

import util.NumberUtil;

import bean.Recording;
import data.RecordingData;

public class RandomRecordingManager {
	
	private RecordingManager recordingManager; 
	
	public RandomRecordingManager()
	{
		recordingManager = new RecordingManager(new RecordingData()); 
	}
	
	public RandomRecordingManager(RecordingManager manager)
	{
		recordingManager = manager; 
	}
	
	/**
	 * Returns the id of a random recording
	 * @return
	 */
	public int getRandomRecordingId()
	{
		int randomRecordingId = 1;
		
		// get a random number from 1 to the number of recordings
		List<Recording> recordings = (List<Recording>) recordingManager.getAllRecordings();
		int randomNum = NumberUtil.getRandomNumber(recordings.size() - 1);
		
		// Iterate over the recordings until the loop index matches the random number.
		// This is because we can't rely on the PK's of the recordings to start at 1 and not have skips
		int i = 0;
		for (Recording recording : recordings) {
			if( i == randomNum )
			{
				randomRecordingId = recording.getId();
				break;
			}
			i++;
		}
		
		return randomRecordingId;
	}
}
