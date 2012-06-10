package servlet.db;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import org.hibernate.exception.GenericJDBCException;
import org.junit.Test;

import servlet.BasicServletTester;
import servlet.SongVoteController;

public class TestSongVoteController extends BasicServletTester
{
	private SongVoteController songVoteController; 
	private Random random = new Random(); 
	
	@Override
	protected boolean useDatabase() 
	{
		return true;
	}

	@Override
	public void postServletSetup() 
	{
		songVoteController = new SongVoteController(); 
	}
	
	
	@Test
	public void upvoteSong() throws FileNotFoundException, IOException
	{
		when(stubRequest.getParameter("id")).thenReturn("640"); 
		when(stubRequest.getParameter("vote")).thenReturn("up"); 
		when(stubRequest.getRemoteAddr()).thenReturn(random.nextInt() + ""); 
	
		try
		{
			songVoteController.doPost(stubRequest, stubResponse);	
		}
		catch(GenericJDBCException exc)
		{
			exc.printStackTrace(); 
			fail(); 
		}	
	}

}
