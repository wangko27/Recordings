package servlet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import bean.SmartSong;
import data.security.SongUserData;
import domain.SmartSongManager;
import domain.SmartSongVoteManager;
import domain.security.UserActivityManager;
import domain.security.UserManager;

public class TestSongVoteController 
{
	SongUserData stubUserData; 
	UserActivityManager userActivityManager; 
	UserManager userManager; 
	SongVoteController controller; 
	SmartSongManager songManager; 
	HttpServletRequest stubRequest; 
	HttpServletResponse stubResponse; 
	StringWriter stubWriter; 
	
	@Before
	public void setup() throws IOException
	{
		stubUserData = mock(SongUserData.class); 
		songManager = mock(SmartSongManager.class); 
		userActivityManager = new UserActivityManager(stubUserData); 
		userManager = new UserManager(userActivityManager); 
		controller = new SongVoteController(songManager, userManager, new SmartSongVoteManager(songManager, userManager));
		stubRequest = mock(HttpServletRequest.class); 
		stubResponse = mock(HttpServletResponse.class); 
		
		stubWriter = new StringWriter();
		PrintWriter pw = new PrintWriter(stubWriter); 
		
		when(stubResponse.getWriter()).thenReturn(pw);
		when(stubRequest.getParameter("id")).thenReturn("1");
		when(stubRequest.getRemoteAddr()).thenReturn("tests"); 
		when(stubRequest.getParameter("vote")).thenReturn("up");
		when(songManager.getSmartSong(Mockito.anyInt())).thenReturn(new SmartSong());
	}

	@Test
	public void songUserDataIsAccessed()
	{		 		
		controller.doPost(stubRequest, stubResponse); 
		
		verify(stubUserData).getUserEntryByTargetId(Mockito.anyString(), Mockito.anyInt());
		verify(stubUserData).addOrUpdateUserEntry((Date) Mockito.any(), Mockito.anyString(), Mockito.anyInt()); 
	}
}
