package domain;

import java.util.List;

import bean.SmartSong;
import data.SmartSongData;

public class SmartSongManager {

	private SmartSongData data;
	
	public SmartSongManager() {
		data = new SmartSongData();
	}
	
	public SmartSongManager(SmartSongData data)
	{
		this.data = data; 
	}
	
	public List<SmartSong> getAllSmartSongs()
	{
		return data.getAllSmartSongs();
	}
	
	public SmartSong getSmartSong( int id )
	{
		return data.getSmartSong(id);
	}
	
	public List<SmartSong> getAllSmartSongs( SmartSong searchBean )
	{
		List<SmartSong> smartSongs = null;
		if( searchBean != null )
			smartSongs = data.getAllSmartSongs(searchBean);
		else
			smartSongs = data.getAllSmartSongs();
		
		return smartSongs;				
	}
	
	public void update(SmartSong smartSong)
	{
		data.saveOrUpdateSmartSong(smartSong); 
	}
}
