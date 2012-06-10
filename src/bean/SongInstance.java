package bean;

public class SongInstance 
{
	private int id;
	private Integer trackListing;
	private Integer section;
	
	private Recording recording;
	private Song song;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getTrackListing() {
		return trackListing;
	}
	public void setTrackListing(Integer trackListing) {
		this.trackListing = trackListing;
	}
	public Integer getSection() {
		return section;
	}
	public void setSection(Integer section) {
		this.section = section;
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
	
	@Override
	public String toString() {
		return this.song.toString();
	}
}
