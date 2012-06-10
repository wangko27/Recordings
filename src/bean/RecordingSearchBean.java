package bean;

public class RecordingSearchBean 
{
	private Recording recording; 
	
	public RecordingSearchBean()
	{
		recording = new Recording(); 
	}

	public Recording getRecording()
	{
		return recording; 
	}
	public void setRecording( Recording recording )
	{
		this.recording = recording;
	}
}
