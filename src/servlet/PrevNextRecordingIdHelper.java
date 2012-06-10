package servlet;

import javax.servlet.http.HttpServletRequest;

import bean.Recording;
import domain.SequentialRecordingIdManager;

public class PrevNextRecordingIdHelper {
	
	public void sendPrevAndNextRecordingId(Recording recording, HttpServletRequest req) throws Exception
	{	
		SequentialRecordingIdManager sequentialRecordingIdManager = new SequentialRecordingIdManager(); 
		
		Integer previousId = sequentialRecordingIdManager.getPreviousRecordingId(recording); 
		Integer nextId =  sequentialRecordingIdManager.getNextRecordingId(recording); 
		
		req.setAttribute("previousId", previousId );
		req.setAttribute("nextId", nextId);
		
		if(nextId != null || previousId != null) 
			req.setAttribute("recordingType", recording.getRecordingType().getValue()); 
	}
}
