package enumeration;

import bean.RecentChange;

public enum RecentChangeType
{	
	
	RECORDING,
	SONG,
	SONGCOMMENT,
	RECORDINGCOMMENT;
	
	/**
	 * Generates a link for the web page to a recent change item. 
	 * 
	 * Recording comment: assumes that change.getRecording() is non-null
	 * Song comment: assumes change.getSong() is non-null
	 * 
	 * @param change
	 * @param referenceId
	 * @return
	 */
	public String getLink(RecentChange change, int referenceId)
	{				
		if(this == RECORDING)
		{			
			return "individualRecording?id=" + referenceId; 
		}
		else if (this == SONG)
		{
			return "song?id=" + referenceId; 
		}
		else if (this == RECORDINGCOMMENT && change.getRecording() != null)
		{			
			return "individualRecording?id=" + change.getRecording().getId() + "#comment_" + referenceId; 
		}
		else if (this == SONGCOMMENT && change.getSong() != null)
		{
			return "song?id=" + change.getSong().getId() + "#comment_" + referenceId; 
		}
		
		return ""; 
	}

	public String getTitle() 
	{
		if(this == RECORDING) return "New Recording"; 
		if(this == SONG) return "New Song"; 
		if(this == RECORDINGCOMMENT) return "Recording Comment"; 
		if(this == SONGCOMMENT) return "Song Comment"; 
		
		return "undefined"; 
	}

	public String getQuickSummary(String summary, int length) 
	{
		if(summary.length() < length) return summary; 
	
		return summary.substring(0, summary.length()-4) + " ..."; 
	}
}
