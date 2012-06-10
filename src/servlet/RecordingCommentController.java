package servlet;

import javax.servlet.http.HttpServletRequest;

import bean.BasicCommentBean;
import bean.Recording;
import bean.RecordingComment;
import data.RecordingCommentData;
import data.security.RecordingCommentUserData;
import domain.CommentsManager;
import domain.security.UserActivityManager;
import domain.security.UserManager;

public class RecordingCommentController extends BasicCommentController 
{		
	private UserManager userManager; 
	
	public RecordingCommentController()
	{		
		super(null);		
	}
	
	public RecordingCommentController(CommentsManager manager)
	{
		super(manager); 
	}		
	
	public RecordingCommentController(CommentsManager manager, UserManager userManager)
	{
		super(manager, userManager); 
	}	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected BasicCommentBean makeCommentBean(HttpServletRequest req)
	{
		BasicCommentBean bean = new RecordingComment();
		Recording tempRecording = new Recording(); 
		tempRecording.setId(Integer.parseInt(req.getParameter("id"))); 
		((RecordingComment)bean).setRecording(tempRecording); 

		super.setGeneralCommentBeanProperties(bean, req); 
		
		return bean;  
	}

	@Override
	protected CommentsManager createManager() 
	{
		return new CommentsManager(new RecordingCommentData(), RecordingComment.class); 	
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
			userManager = new UserManager(new UserActivityManager(new RecordingCommentUserData()));
		else
			userManager = optionalUserManager; 
	}

	

}
