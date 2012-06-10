package domain;

import java.util.List;

import data.AlbumData;

public class AlbumManager {
	
	private AlbumData albumData;
	
	public AlbumManager() {
		albumData = new AlbumData();
	}

	public List<String> getAllAlbums() throws Exception
	{
		return albumData.getAllAlbums();
	}

}
