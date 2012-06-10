package servlet;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

import domain.security.UserManager;

public class TestAdminController 
{
	private AdminController controller; 
	private HttpServletRequest stubRequest;
	private HttpServletResponse stubResponse; 
	private HttpSession stubSession;  
	
	@Before
	public void setup()
	{
		controller = new AdminController(); 		
		stubRequest = mock(HttpServletRequest.class);
		stubResponse = mock(HttpServletResponse.class); 
		stubSession = mock(HttpSession.class);  
	}
	
	@Test
	public void validUser_sessionKeyIsSet() throws ServletException, IOException
	{
		when(stubRequest.getSession()).thenReturn(stubSession);
		when(stubRequest.getParameter("password")).thenReturn(UserManager.SUPER_SECRET_ADMIN_PASSWORD); 
		when(stubResponse.getWriter()).thenReturn(new PrintWriter(new StringWriter())); 
		
		AdminController controller = new AdminController(); 
		controller.doPost(stubRequest, stubResponse); 
		
		verify(stubSession).setAttribute("authenticated", true);
	}

	@Test
	public void invalidUser_getsErrorMessage() throws ServletException, IOException
	{
		StringWriter injectedStringWriter = new StringWriter(); 
		PrintWriter injectedPrintWriter = new PrintWriter(injectedStringWriter); 
		
		when(stubRequest.getSession()).thenReturn(stubSession);
		when(stubResponse.getWriter()).thenReturn(injectedPrintWriter); 
		
		controller.doPost(stubRequest, stubResponse); 
		
		String result = injectedStringWriter.getBuffer().toString(); 
		assertTrue(result.contains("failure")); 
	}	
	
	@Test
	public void validUser_sendsSuccessMessage() throws IOException, ServletException
	{
		StringWriter injectedStringWriter = new StringWriter(); 
		PrintWriter injectedPrintWriter = new PrintWriter(injectedStringWriter); 
		
		when(stubRequest.getSession()).thenReturn(stubSession);
		when(stubResponse.getWriter()).thenReturn(injectedPrintWriter); 
		when(stubRequest.getParameter("password")).thenReturn(UserManager.SUPER_SECRET_ADMIN_PASSWORD); 
		
		controller.doPost(stubRequest, stubResponse); 
		
		String result = injectedStringWriter.getBuffer().toString(); 
		assertTrue(result.contains("success")); 
	}
	
	@Test
	public void validUser_attemptsLogout_sessionIsCleared() throws ServletException, IOException
	{
		when(stubRequest.getSession()).thenReturn(stubSession);
		when(stubRequest.getParameter("logout")).thenReturn("true"); 
		
		controller.doPost(stubRequest, stubResponse); 
		
		verify(stubSession).removeAttribute("authenticated"); 
	}
}
