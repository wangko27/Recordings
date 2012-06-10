package domain;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bean.Format;
import bean.Media;
import bean.Quality;
import bean.Recording;
import bean.RecordingType;
import bean.SmartSong;
import bean.Song;
import bean.SongInstance;
import bean.TableElement;

public class TestTableElementManager 
{
	private TableElementManager tableElementManager; 
	
	@Before
	public void before()
	{
		tableElementManager = new TableElementManager(); 
	}
	
	@Test
	public void testSongToTableElements()
	{
		SmartSong song = new SmartSong();		
		
		song.setFirstPlayed(0); 
		song.setLastPlayed(1); 
		
		List<TableElement> results = tableElementManager.songToTableElements(song);
		
		assertTrue(collectionContains(results, "First Appearance", "0", null));
		assertTrue(collectionContains(results, "Last Appearance", "1", null));
	}
	
	@Test
	public void testRecordingToTableElements()
	{
		Recording recording = new Recording(); 
		recording.setRecordingType(new RecordingType("Show")); 
		recording.setName("foo"); 
		recording.setMedia(new Media("cd"));
		recording.setQuality(new Quality("terrible")); 
		recording.setFormat(new Format("wav"));
		
		List<TableElement> results = tableElementManager.recordingToTableElements(recording);
		
		assertTrue(collectionContains(results, "Type", "Show", null)); 
		assertTrue(collectionContains(results, "Name", "foo", null)); 
		assertTrue(collectionContains(results, "Media", "cd", null)); 
		assertTrue(collectionContains(results, "Best Known Quailty", "terrible", null)); 
		assertTrue(collectionContains(results, "Best Known Format", "wav", null)); 
	}
	
	@Test
	public void testSongInstanceToTableElements()
	{
		SongInstance songInstance = new SongInstance(); 
		Song song = new Song(); 
		song.setValue("a"); 
		song.setId(1); 
		songInstance.setSong(song); 
		songInstance.setTrackListing(1); 
		
		List<SongInstance> songInstances = new ArrayList<SongInstance>(); 
		songInstances.add(songInstance); 
		
		List<TableElement> results = tableElementManager.songInstancesToTableElements(songInstances); 
		
		assertTrue(collectionContains(results, "1", "a", "song?id=1")); 
	}

	
	
	private boolean collectionContains(Collection<TableElement> collection, String label, String value, String href)
	{
		for(TableElement element : collection)
		{
			if( (label == null || element.getLabel().equals(label)) && (value == null || element.getValue().equals(value)) && (href == null || element.getHref().equals(href) ))
				return true; 
		}
		
		return false; 
	}
	

}
