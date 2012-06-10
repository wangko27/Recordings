package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import bean.RecordingType;
import enumeration.SequentialDirection;

public class SequentialRecordingIdData {
	
	
	public Integer getSequentialRecordingId(int id, SequentialDirection sequentialDirection) throws Exception
	{		
		return getSequentialRecordingId(id, sequentialDirection, new RecordingType("Show")); 
	}
	
	public Integer getSequentialRecordingId(int id, SequentialDirection sequentialDirection, RecordingType type) throws Exception
	{
		// determine if this is getting the 
		// next or previous recording Id
		String orderDirection = "asc";
		if( sequentialDirection.equals(SequentialDirection.PREVIOUS) )
			orderDirection = "desc";
		
		Integer nextId = null;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			conn = DataHelper.getJDBCConnection();
			stmt = conn.createStatement();
			
			String sql = 
						"select r.id " + 
						"from recording r " + 
						"where r.recordingtypefk = ( " + 
						"    select rt.id " + 
						"    from lkprecordingtype rt " + 
						"    where rt.value = '" + type.getValue() + "' " + 
						") " + 
						"and (r.year <> 0 or r.month  <> 0 or r.date <> 0) " +
						"order by r.year " + orderDirection + ", r.month " + orderDirection + ", r.date " + orderDirection;
			
			rs = stmt.executeQuery(sql);
			
			Integer currentId = null; 
			while( rs.next() )
			{
				currentId = rs.getInt(1);
				
				if( currentId == id )
				{
					if( rs.next() )
						nextId = rs.getInt(1);
					break;
				}
			}
		}
		catch( Exception e )
		{
			throw new Exception(e);
		}
		finally
		{
			stmt.close();
			rs.close();
			conn.close();
		}
		
		return nextId;
	}
}
