package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlbumData {
	
	public List<String> getAllRecordingTypesIdentifiedByTitle() throws Exception
	{
		List<String> recordingsIdentifiedByTitle = new ArrayList<String>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			conn = DataHelper.getJDBCConnection();
			stmt = conn.createStatement();
			
			String sql = "select r.name " +
						 "from recording r " +
						 "where r.recordingtypefk in ( " +
						 "     select rt.id " +
						 "     from lkprecordingtype rt " +
						 "     where rt.value in ('Album','Studio Bootleg','Compilation','Interview', 'Radio', 'TV', 'Single') " +
						 ")";
			
			rs = stmt.executeQuery(sql);
			
			while( rs.next() )
				recordingsIdentifiedByTitle.add(rs.getString(1));
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
		
		return recordingsIdentifiedByTitle;
	}
	
	public List<String> getAllAlbums() throws Exception
	{
		List<String> recordingsIdentifiedByTitle = new ArrayList<String>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			conn = DataHelper.getJDBCConnection();
			stmt = conn.createStatement();
			
			String sql = "select r.name " +
						 "from recording r " +
						 "where r.recordingtypefk = ( " +
						 "     select rt.id " +
						 "     from lkprecordingtype rt " +
						 "     where rt.value = 'Album' " +
						 ") " +
						 "order by year, month, date";
			
			rs = stmt.executeQuery(sql);
			
			while( rs.next() )
				recordingsIdentifiedByTitle.add(rs.getString(1));
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
		
		return recordingsIdentifiedByTitle;
	}

}
