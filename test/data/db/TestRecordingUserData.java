package data.db;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import data.HibernateTest;
import data.security.RecordingUserData;

public class TestRecordingUserData  extends HibernateTest 
{

	private RecordingUserData userData; 

	public void before()
	{
		userData = new RecordingUserData(); 
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
			assertTrue(false); 
		}
	}
}
