package data.common;

import bean.SmartSong;

public interface ISmartSong 
{

	public void saveOrUpdateSmartSong(SmartSong smartSong);
	public SmartSong getSmartSong(int id); 
}
