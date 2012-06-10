package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.StringUtil;

@WebServlet("/TabSessionController")
public class TabSessionController extends ServletExtension
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(req, response);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		if( StringUtil.hasValue((String)req.getParameter("tab")) )
		{
			String tab = (String)req.getParameter("tab");
			
			if( StringUtil.hasValue(tab) )
			{
				req.getSession().setAttribute("session_tab", tab);
			}
		}
	}
}
