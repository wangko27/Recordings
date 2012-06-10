package bean;

import java.util.Date;

public class SongComment extends BasicCommentBean 
{
	private Song song;

	public SongComment() {
		super();
	}
	public SongComment(String userName, String text, Date time, int bob)
	{
		super(userName, text, time, bob); 
	}
	
	public Song getSong() {
		return song;
	}
	public void setSong(Song song) {
		this.song = song;
	}
}
