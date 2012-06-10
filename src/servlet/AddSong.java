package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.StringUtil;
import bean.Song;
import data.SimpleEntityData;
import domain.SimpleEntityManager;
import exception.RecordingException;

@WebServlet("/AddSong")
public class AddSong extends ServletExtension {
	
	private static final long serialVersionUID = 1L;	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(req,response);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			String value = req.getParameter("value");
			
			if( StringUtil.hasValue(value) )
			{
				SimpleEntityManager simpleEntityManager = new SimpleEntityManager(new SimpleEntityData());
				
				Song song = new Song();
				
				song.setValue(value);
				
				simpleEntityManager.saveOrUpdateSimpleBean(song);
			}
		}
		catch( Exception e )
		{
			addErrorMessage(req, new RecordingException(e));
		}
	}
	
	
}