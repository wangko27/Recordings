package data.db.SongInstanceData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import bean.Recording;
import bean.Song;
import bean.SongInstance;
import data.HibernateTest;
import data.RecordingData;
import data.SimpleEntityData;
import data.SongInstanceData;
import domain.RecordingManager;
import domain.SimpleEntityManager;

public class TestSongInstanceData_CRUD extends HibernateTest {
	
	private SongInstanceData data = new SongInstanceData();
	private SimpleEntityData simpleEntityData = new SimpleEntityData();
	
	@Test
	@Ignore 
	public void testAdd()
	{
		try
		{
			SongInstance songInstance = new SongInstance();
			
			final int Emaline_Id = 174;
			
			RecordingManager recordingManager = new RecordingManager(new RecordingData());
			Recording recording = recordingManager.getRecording(1);
			SimpleEntityManager simpleEntityManager = new SimpleEntityManager(new SimpleEntityData());
			Song song = (Song) simpleEntityManager.getSimpleBean(Emaline_Id, Song.class);
			
			songInstance.setTrackListing(16);
			songInstance.setRecording(recording);
			songInstance.setSong(song);
		
			data.saveOrUpdate(songInstance);
			
			
			
			
		}
		catch( Exception e )
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	// note: will fail on local machine 
	public void testDelete()
	{
		int songInstanceId = 49;
		SongInstance songInstance = data.getSongInstance(songInstanceId);
		
		try
		{
			data.delete(songInstance);
		}
		catch( Exception e )
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	@Ignore 
	public void testSaveOrUpdate_updateTrackListing() throws Exception
	{
		int songInstanceId = 5342;
		int songId = 1;
		
		SongInstance songInstance = data.getSongInstance(songInstanceId);
		Song song = (Song) simpleEntityData.getSimpleBean(songId, Song.class);
		
		int originalTrackListing = songInstance.getTrackListing();
		
		songInstance.setSong(song);
		songInstance.setTrackListing(originalTrackListing + 1);
		
		data.saveOrUpdate(songInstance);
		
		SongInstance songInstance_updated = data.getSongInstance(songInstanceId);
		
		assertEquals(new Integer(originalTrackListing + 1), songInstance_updated.getTrackListing());
	}

}
