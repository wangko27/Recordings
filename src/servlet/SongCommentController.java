package servlet;

import javax.servlet.http.HttpServletRequest;

import bean.BasicCommentBean;
import bean.Song;
import bean.SongComment;
import data.SongCommentData;
import data.security.SongCommentUserData;
import domain.CommentsManager;
import domain.security.UserActivityManager;
import domain.security.UserManager;

public class SongCommentController extends BasicCommentController
{
	private UserManager userManager = new UserManager(); 
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public SongCommentController()
	{
		super(null); 
	}
	
	public SongCommentController(CommentsManager manager) 
	{
		super(manager);
	}
	
	public SongCommentController(CommentsManager manager, UserManager userManager)
	{
		super(manager, userManager); 
	}	

	@Override
	protected CommentsManager createManager() 
	{		
		return new CommentsManager(new SongCommentData(), SongComment.class); 
	}

	@Override
	protected BasicCommentBean makeCommentBean(HttpServletRequest req) 
	{		
		BasicCommentBean bean = new SongComment();
		Song tempSong = new Song(); 
		tempSong.setId(Integer.parseInt(req.getParameter("id"))); 
		((SongComment)bean).setSong(tempSong); 

		super.setGeneralCommentBeanProperties(bean, req); 
		
		return bean;  		
	}

	@Override
	protected UserManager getUserManager()
	{
		return userManager;
	}

	@Override
	protected void createUserManager(UserManager optionalUserManager) 
	{
		if(optionalUserManager == null) 
			userManager = new UserManager(new UserActivityManager( new SongCommentUserData()));
		else
			userManager = optionalUserManager; 
		
	}
	
	
}
