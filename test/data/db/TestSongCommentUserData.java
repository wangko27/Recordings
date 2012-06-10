package data.db;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import data.HibernateTest;
import data.security.SongCommentUserData;

public class TestSongCommentUserData extends HibernateTest
{
	private SongCommentUserData userData; 
	
	@Override
	public void before()
	{
		userData = new SongCommentUserData(); 
	}
	
	
	@Test
	public void smokeTest()
	{
		try
		{
			userData.getUserEntryByTargetId("test", 1); 
			assertTrue(true); 
		}
		catch(Exception exc)
		{
			exc.printStackTrace(); 
			fail(); 
		}
	}
}
