package data.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import util.StringUtil;
import data.JDBCHelper;

public class TestJDBCHelper 
{
	@Test
	public void smokeInt()
	{
		try
		{
			String result = JDBCHelper.scalerQuery("select 1 from dual");
			assertEquals(1, StringUtil.stringToInt(result));
		}
		catch( Exception e )
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void smokeString()
	{
		try
		{
			String result = JDBCHelper.scalerQuery("select 'jon' from dual");
			assertEquals("jon", result);
		}
		catch( Exception e )
		{
			fail(e.getMessage());
		}
	}

}
