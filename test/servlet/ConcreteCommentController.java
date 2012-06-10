package servlet;

import javax.servlet.http.HttpServletRequest;

import bean.BasicCommentBean;
import domain.CommentsManager;
import domain.security.UserManager;

public class ConcreteCommentController extends BasicCommentController  
{
	private UserManager userManager; 
	
	
	protected ConcreteCommentController(CommentsManager manager, UserManager userManager)
	{
		this.userManager = userManager; 
		setCommentManager(manager);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected UserManager getUserManager() 
	{
		return userManager; 
	}
	
	// these methods below are not used in testing since we call the constructor directly 
	
	@Override
	protected void createUserManager(UserManager optionalUserManager) 
	{
		return; 
	}

	@Override
	protected CommentsManager createManager() 
	{
		return null;
	}

	@Override
	protected BasicCommentBean makeCommentBean(HttpServletRequest req)
	{
		return null;
	}

}
