package data;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;

/**
 * Every db-hitting class should extend this class -- it handles session creation and destruction and emulates the servlet
 * filter as part of open session in view  
 *
 */
public abstract class HibernateTest
{
	/**
	 * Override and return true to have the database dropped and recreated before each testcase runs. 
	 */
	protected boolean needsDatabaseReset()
	{
		return false; 
	}
	
	@Before 
	public void Before() throws IOException
	{
		if(needsDatabaseReset())
			resetDatabase(); 
		
		SessionFactory sf = DataHelper.getSessionFactory();
		Session session = sf.openSession(); 
		session.beginTransaction(); 
		DataHelper.setOpenSession(session); 
		
		before(); 
	}
	
	@After 
	public void After()
	{
		Session session = DataHelper.getOpenSession();
		session.close(); 
		DataHelper.setOpenSession(null); 
		
		after(); 
	}
	
	protected void after()
	{
		
	}
	
	protected void before()
	{
		return; 
	}
	
	public static void resetDatabase() throws IOException
	{
		Runtime runtime = Runtime.getRuntime(); 
		runtime.exec("cmd /c start scripts\\resetdatabase.bat"); 
	}
}
