package bean;

import java.io.Serializable;

import enumeration.Frequency;

public class SmartSong implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int id;
	private String value;
	private String alias;
	private int firstPlayed;
	private int lastPlayed;
	private int count;
	private int frequency;
	private int upvotes;
	private int downvotes;
	private String album1;
	private String album2;
	private String album3;
	
	public String getFrequencyDisplay()
	{
		Frequency frequency = Frequency.getFrequencyFromValue(this.frequency);
		
		return frequency.getDisplay();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFirstPlayed() {
		return firstPlayed;
	}
	public void setFirstPlayed(int firstPlayed) {
		this.firstPlayed = firstPlayed;
	}
	public int getLastPlayed() {
		return lastPlayed;
	}
	public void setLastPlayed(int lastPlayed) {
		this.lastPlayed = lastPlayed;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public int getUpvotes() {
		return upvotes;
	}
	public void setUpvotes(int upvotes) {
		this.upvotes = upvotes;
	}
	public int getDownvotes() {
		return downvotes;
	}
	public void setDownvotes(int downvotes) {
		this.downvotes = downvotes;
	}
	public String getAlbum1() {
		return album1;
	}
	public void setAlbum1(String album1) {
		this.album1 = album1;
	}
	public String getAlbum2() {
		return album2;
	}
	public void setAlbum2(String album2) {
		this.album2 = album2;
	}
	public String getAlbum3() {
		return album3;
	}
	public void setAlbum3(String album3) {
		this.album3 = album3;
	}
}
