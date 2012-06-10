package domain;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import bean.VotingResult;
import bean.VotingResult.VotingMessage;

import com.google.gson.Gson;

import domain.security.UserManager;
import exception.RecordingException;

public abstract class BasicVoteManager<T>
{				
	private UserManager userManager; 
	
	public BasicVoteManager(UserManager manager)
	{
		this.userManager = manager; 
	}
	
	/**
	 * 
	 * @precondition: user can vote for this item 
	 * @param object
	 * @return
	 * @throws RecordingException 
	 */
	protected abstract int voteForObject(int upOrDownVote, T object) throws RecordingException; 
	
	/**
	 * changes an object's vote count by the indicated amount
	 * @param upOrDownVote the change in the vote rank 
	 * @param object the object to vote on 
	 * @param user the user voting
	 * @return a voting result indicating if the user was able to vote or not. Also returns the new vote count of the object
	 * @throws RecordingException 
	 */
	public VotingResult castUserVote(int upOrDownVote, T object, String user, int objectId) throws RecordingException
	{		
		userManager.resetUserVotesIfAble(Calendar.getInstance().getTime(), user);
		VotingResult userCanVote = userManager.updateUserCommentActivity(Calendar.getInstance().getTime(), user, objectId);
		
		if(userCanVote.getResult() == VotingMessage.TOO_MANY_VOTES)
			return new VotingResult(VotingMessage.TOO_MANY_VOTES, "You have cast too many votes today");
		else if (userCanVote.getResult() == VotingMessage.ALREADY_VOTED)
			return new VotingResult(VotingMessage.ALREADY_VOTED, " You have already voted for this!"); 	

		int newUserVoteCount = voteForObject(upOrDownVote, object); 
		
		return new VotingResult(VotingMessage.SUCCESS, "Success", newUserVoteCount); 
	}
	
	public boolean userCanVoteOnObject(String user, int objectId)
	{			
		return false; 		
	}
	

	public static String getJSONString(int netVotes)
	{
		Map<String, Integer> results = new HashMap<String, Integer>(); 
		
		results.put("votes", netVotes); 
		
		return new Gson().toJson(results); 
	}
}
