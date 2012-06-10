package security.db;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.hibernate.NonUniqueResultException;
import org.junit.Test;

import bean.Country;
import data.HibernateTest;
import data.RecordingData;
import data.SimpleEntityData;
import exception.RecordingException;

public class TestInjectionVulnerabilities extends HibernateTest
{
	RecordingData recordingData = new RecordingData(); 
	String maliciousParameter = "non-existent' OR '1'='1 "; 
	
	
	@Test
	public void testHQLInjection_onRecordingData() throws RecordingException
	{
		Map<Comparable<?>, Integer> results = recordingData.groupRecordingsByCommonValue("Venue", maliciousParameter, "Year", null); 

		assertTrue("possible HQL injection vulnerability: results size was " + results.size() + " expected zero",  results.size() == 0); 	
	}
	
	@Test
	public void testHQLInjection_onSimpleEntityData() throws RecordingException
	{
		try
		{
			SimpleEntityData testData = new SimpleEntityData(); 
			testData.getSimpleBean(maliciousParameter, Country.class);
		}
		catch(NonUniqueResultException exc)
		{
			fail("possible HQL injection vulnerability: result was non-unique");
		}
	}
}
