package data.db;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.Test;

import bean.RecentChange;
import data.HibernateTest;
import data.RecentChangeData;

public class TestRecentChangeData extends HibernateTest
{
	private RecentChangeData recentChangeData; 
	
	@Override
	public void before()
	{
		recentChangeData = new RecentChangeData(); 
	}
	
	@Test
	public void smokeTest()
	{
		Collection<?> results = recentChangeData.getTopRecentChanges(5); 
		assertNotNull(results); 
	}
	
	@Test
	public void testGetLink()
	{
		Collection<RecentChange> results = recentChangeData.getTopRecentChanges(5); 
		for(RecentChange change : results)
		{
			String link = change.getLink(); 
			assertNotNull(link); 
		}
	}
	
	/**
	 * If only 1 change is requested, then only 1 change should be returned
	 */
	@Test
	public void testONEReturned()
	{
		Collection<?> results = recentChangeData.getTopRecentChanges(1); 
		if( results != null )
			assertEquals(1, results.size()); 
	}
	
	/**
	 * Make sure that if there are 5 changes to return...that 5 changes are returned
	 */
	@Test
	public void testFIVEReturned()
	{
		Collection<?> results = recentChangeData.getTopRecentChanges(1); 
		if( results != null && results.size() > 4 )
			assertEquals(5, results.size());
	}
	
	/**
	 * Make sure that no changes with a prefix of SKIP are returned
	 */
	@Test
	public void testNoSKIP()
	{
		Collection<?> results = recentChangeData.getTopRecentChanges(5);
		if( results != null && results.size() > 4 )
		{
			for (Object rc_object: results) {
				RecentChange recentChange = (RecentChange)rc_object;
				assertTrue(!recentChange.getSummary().startsWith("SKIP"));
			}
		}
	}
}
