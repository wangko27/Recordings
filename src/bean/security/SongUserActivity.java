package bean.security;

import bean.Song;

public class SongUserActivity extends UserActivity 
{
	private Song song;

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}
	
	
}
