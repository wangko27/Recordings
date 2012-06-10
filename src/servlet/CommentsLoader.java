package servlet;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import bean.BasicCommentBean;
import data.common.ICommentData;
import domain.CommentsManager;

public class CommentsLoader
{
	private ICommentData dataSource; 
	private Class<?> dataClass; 
	private String sourceName; 
	
	public CommentsLoader(ICommentData dataSource, Class<?> dataClass, String sourceName)
	{
		this.dataSource = dataSource; 
		this.dataClass = dataClass; 
		this.sourceName = sourceName; 
	}
	
	public void loadCommentsAndSetAttributes( int id, HttpServletRequest req, Object sourceObject)
	{
		CommentsManager commentsManager = new CommentsManager(dataSource,dataClass); 
		
		/***********load comments data*/ 
		Collection<?> comments = new ArrayList<BasicCommentBean>(); 
		comments = commentsManager.getSortedCommentsForSpecificBean(id); 
		req.setAttribute("commentData",comments ); 		
		req.setAttribute("commentSource", sourceName); 
		req.setAttribute("commentSourceId", id); 
		req.setAttribute("commentSourceObject", sourceObject); 
		req.setAttribute("commentControllerUrl", req.getRequestURI().toString()  + "?" + req.getQueryString()); 
		/*************************/ 	
	}
}
