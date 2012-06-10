package data.db;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import data.HibernateTest;
import data.security.RecordingCommentUserData;

public class TestRecordingCommentUserData extends HibernateTest 
{
	private RecordingCommentUserData userData; 
	
	@Override
	public void before()
	{
		userData = new RecordingCommentUserData(); 
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
