package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCHelper {
	
	public static String scalerQuery( String sql )
	{
		String result = "";
		
		try 
		{
			Connection jdbcConnection = DataHelper.getJDBCConnection();
			
			Statement statement = jdbcConnection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			if( rs.next() )
				result = rs.getString(1);
		}
		catch( Exception e)
		{
		}
		
		return result;
	}

}
