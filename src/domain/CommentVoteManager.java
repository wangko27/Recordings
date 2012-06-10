package domain;

import bean.BasicCommentBean;
import domain.security.UserManager;

public class CommentVoteManager extends BasicVoteManager<BasicCommentBean>
{
	private CommentsManager commentManager; 
		
	public CommentVoteManager(CommentsManager commentManager, UserManager manager) {
		super(manager);
		this.commentManager = commentManager; 
	}		
	
	@Override 
	protected int voteForObject(int upOrDownVote, BasicCommentBean commentBean)
	{
		commentBean.changeVoteRank(1); 		
		commentManager.saveOrUpdateComment(commentBean); 		
		return commentBean.getVoteRank();
	}
}
