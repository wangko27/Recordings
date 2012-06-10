package enumeration;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import bean.RecentChange;
import bean.Recording;
import bean.Song;

public class TestRecentChangeType
{
	private RecentChangeType recentChangeType; 
	private RecentChange recentChange; 
	
	@Before
	public void setup()
	{
		recentChange = new RecentChange();
	}
	
	@Test
	public void testCorrectRecordingLink()
	{
		recentChangeType = RecentChangeType.RECORDING; 
		String result = recentChangeType.getLink(null, 1); 
		   
		assertEquals("individualRecording?id=1", result); 
	}
	
	@Test
	public void testCorrectSongLink()
	{
		recentChangeType = RecentChangeType.SONG; 
		String result = recentChangeType.getLink(null,1); 
		
		assertEquals("song?id=1", result); 
	}
	
	@Test
	public void testRecordingCommentLink()
	{
		recentChangeType = RecentChangeType.RECORDINGCOMMENT; 
		
		Recording recording = new Recording(); 
		recording.setId(1); 
		recentChange.setRecording(recording); 
		
		String result = recentChangeType.getLink(recentChange, 2);
		
		assertEquals("individualRecording?id=1#comment_2", result); 
	}
	
	@Test
	public void testSongCommentLink()
	{
		recentChangeType = RecentChangeType.SONGCOMMENT; 
		Song song = new Song(); 
		song.setId(2); 
		
		recentChange.setSong(song); 
		String result = recentChangeType.getLink(recentChange, 1); 
		
		assertEquals("song?id=2#comment_1", result); 
	}
}
