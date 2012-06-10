package data.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import util.StringUtil;
import bean.SmartSong;
import data.HibernateTest;
import data.SmartSongData;

public class TestSmartSongData extends HibernateTest {

	private SmartSongData data = new SmartSongData();
	
	@Test
	public void testGetAllSmartSongs_album2()
	{
		try
		{
			final String ALBUM = "Jonathan Sings Demos";
			SmartSong smartSong = new SmartSong();
			smartSong.setAlbum1(ALBUM);
			List<SmartSong> smartSongs = data.getAllSmartSongs(smartSong);
			
			assertNotNull(smartSongs);
			assertTrue(!smartSongs.isEmpty());
			
			for (SmartSong smartSong2 : smartSongs)
				assertTrue(hasAlbum(smartSong2, ALBUM));
			
			assertTrue( smartSongs.size() > 3 );
		}
		catch( Exception e )
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetAllSmartSongs_album1()
	{
		try
		{
			final String ALBUM = "Jonathan Sings";
			SmartSong smartSong = new SmartSong();
			smartSong.setAlbum1(ALBUM);
			List<SmartSong> smartSongs = data.getAllSmartSongs(smartSong);
			
			assertNotNull(smartSongs);
			assertTrue(!smartSongs.isEmpty());
			
			for (SmartSong smartSong2 : smartSongs)
				assertTrue(hasAlbum(smartSong2, ALBUM));
		}
		catch( Exception e )
		{
			fail(e.getMessage());
		}
	}
	
	/**
	 * If any of the passed-in smartSong's album fields equal 
	 * the passed in album string then return true.
	 * @param smartSong
	 * @param album
	 * @return
	 */
	private boolean hasAlbum( SmartSong smartSong, String album )
	{
		boolean hasAlbum = false;

		if( StringUtil.hasValue(smartSong.getAlbum1()) && smartSong.getAlbum1().equals(album) ||
			StringUtil.hasValue(smartSong.getAlbum2()) && smartSong.getAlbum2().equals(album) ||
			StringUtil.hasValue(smartSong.getAlbum3()) && smartSong.getAlbum3().equals(album) )
		{
			hasAlbum = true;
		}
			
		return hasAlbum;
	}

	@Test
	public void smokeTest()
	{
		try
		{
			data.getAllSmartSongs();
			assertTrue(true); 
		}
		catch(Exception exc)
		{
			fail(); 
		}
	}
	
	@Test
	public void testGetAllSmartSongs_firstPlayed()
	{
		SmartSong smartSong = new SmartSong();
		smartSong.setFirstPlayed(1977);
		List<SmartSong> smartSongs = data.getAllSmartSongs(smartSong);
		
		assertNotNull(smartSongs);
		assertTrue(!smartSongs.isEmpty());
		
		for (SmartSong smartSong2 : smartSongs) {
			assertEquals(1977, smartSong2.getFirstPlayed());
		}
	}
	
	@Test
	public void testGetAllSmartSongs_emptySearchBean()
	{
		List<SmartSong> smartSongs = data.getAllSmartSongs(new SmartSong());
		
		assertNotNull(smartSongs);
		assertTrue(!smartSongs.isEmpty());
	}
	
	@Test
	public void testGetAllSmartSongs_noSearchBean()
	{
		List<SmartSong> smartSongs = data.getAllSmartSongs();
		
		assertNotNull(smartSongs);
		assertTrue(!smartSongs.isEmpty());
	}
	
	@Test
	public void testUpdateSmartSong()
	{
		SmartSong song = data.getSmartSong(610); 
		assertNotNull(song); 
		
		try
		{
			data.saveOrUpdateSmartSong(song);
		}
		catch(Exception exc)
		{

			fail(); 
		}
		
	}
}
