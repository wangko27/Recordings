package bean;

import java.util.Date;

public class RecordingComment extends BasicCommentBean 
{
	private Recording recording;

	public RecordingComment() {
		super();
	}
	public RecordingComment(String userName, String text, Date time, int bob)
	{
		super(userName, text, time, bob); 
	}
	
	public Recording getRecording() {
		return recording;
	}
	public void setRecording(Recording recording) {
		this.recording = recording;
	}
}
