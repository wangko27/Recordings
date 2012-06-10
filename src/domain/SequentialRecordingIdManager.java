package domain;

import bean.Recording;
import bean.RecordingType;
import data.SequentialRecordingIdData;
import enumeration.SequentialDirection;

public class SequentialRecordingIdManager {
	
	private SequentialRecordingIdData data;
	
	public SequentialRecordingIdManager() {
		data = new SequentialRecordingIdData();
	}
	
	public Integer getNextRecordingId(Recording recording) throws Exception
	{
		int startingId = recording.getId(); 
		
		if(recording.getRecordingType().equals(new RecordingType("Show")))		
			return data.getSequentialRecordingId(startingId, SequentialDirection.NEXT);
		else
			return data.getSequentialRecordingId(startingId, SequentialDirection.NEXT, recording.getRecordingType()); 			
	}
	
	public Integer getPreviousRecordingId(Recording recording ) throws Exception
	{
		int startingId = recording.getId(); 
		
		if(recording.getRecordingType().equals(new RecordingType("Show")))		
			return data.getSequentialRecordingId(startingId, SequentialDirection.PREVIOUS);
		else
			return data.getSequentialRecordingId(startingId, SequentialDirection.PREVIOUS, recording.getRecordingType());
	}
}
