package bean;

import java.util.Date;

import enumeration.RecentChangeType;

public class RecentChange
{
	
	// quick summary of the change (comment quotes, etc. ) 
	private String summary; 
	// date it was posted
	private Date publishDate;
	// username posted (or Admin) 
	private String author; 
	// the id of this object (could be recording id, song id, etc.) 
	private int referenceId;
	// type of change
	private RecentChangeType type;
	// recording parent (for recording comments) 
	private Recording recording; 
	// song parent for song comments 
	private Song song; 
	
	public String getTitle()
	{
		return type.getTitle(); 
	}
	
	public Recording getRecording() {
		return recording;
		
	}
	
	public void setRecording(Recording recording) {
		this.recording = recording;
	}

	public Song getSong() {
		return song;
	}	

	public void setSong(Song song) {
		this.song = song;
	}

	public String getLink() {
		return type.getLink(this,referenceId);
	}

	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(int referenceId) {
		this.referenceId = referenceId;
	}
	public RecentChangeType getType() {
		return type;
	}
	public void setType(RecentChangeType type) {
		this.type = type;
	} 
	
	public String toString() {
		return this.summary;
	}
}
