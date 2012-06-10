package data;

import java.sql.Connection;
import java.sql.DriverManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Provides boilerplate data operations
 */
public final class DataHelper {
	
	private static SessionFactory sessionFactory = null;
	private static ThreadLocal<Session> openSession = new ThreadLocal<Session>(); 
	
	public static void setOpenSession(Session openSession) {
		DataHelper.openSession.set(openSession);
	}
	
	public static Session getOpenSession() {
		return DataHelper.openSession.get();
	}



	// set up session factory
	static 
	{
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}
	
	/**
	 * Returns a Hibernate session factory
	 * @return
	 */
	public static SessionFactory getSessionFactory()
	{		
		return sessionFactory;
	}
	
	
	/**
	 * Get's a raw JDBC connection
	 * @return
	 */
	public static Connection getJDBCConnection()
    {
		Connection con = null;
            
        try
        {
        	Class.forName("com.mysql.jdbc.Driver");
            
        	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jrrecord", "jrrecord", "jrsi1970");
        } 
        catch (Exception e)
        {
        	e.printStackTrace();
        }
            
        return con;
    }
	
	
}
