package domain.security;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import bean.AuthenticationResult;
import bean.VotingResult;
import bean.VotingResult.VotingMessage;

import com.google.gson.Gson;

/**
 * provides methods and managers the tracking of user activity. Stores the last time
 * a user made a post to the site. 
 *
 */
public class UserManager
{
	private class UserVoteTracker
	{
		private Date earliestCommentPosted;
		private int numVotes; 
		
		public Date getEarliestCommentPosted()
		{
			return earliestCommentPosted; 
		}				
		
		public void setEarliestCommentPosted(Date earliestCommentPosted) {
			this.earliestCommentPosted = earliestCommentPosted;
		}

		public void changeVotes(int vote)
		{
			numVotes += vote; 
		}
		
		public int getNumVotes()
		{
			return numVotes; 
		}
		
	}
	
	public static final double MIN_TIME_BETWEEN_UPDATES = 5000; 
	public final int MAX_NUMBER_OF_VOTES_PER_TIME_INTERVAL;
	public final int TIME_UNTIL_VOTES_RESET; // in milliseconds 
	public static final String SUPER_SECRET_ADMIN_PASSWORD = "jdradmin"; 
	
	private ConcurrentHashMap<String, Date> lastUserActivity; 	
	private ConcurrentHashMap<String, UserVoteTracker> userCommentActivity; 
	
	private UserActivityManager userActivityManager; 
	
	public UserManager()
	{
		TIME_UNTIL_VOTES_RESET = 24 * 60 * 60 * 1000; 
		MAX_NUMBER_OF_VOTES_PER_TIME_INTERVAL = 30; 
		lastUserActivity = new ConcurrentHashMap<String, Date>(); 
		userCommentActivity = new ConcurrentHashMap<String, UserVoteTracker>(); 	
	}
	
	public UserManager(int timeUntilVotesReset, int votesPerTimeInterval)
	{
		TIME_UNTIL_VOTES_RESET = timeUntilVotesReset; 
		MAX_NUMBER_OF_VOTES_PER_TIME_INTERVAL = votesPerTimeInterval;
		userCommentActivity = new ConcurrentHashMap<String, UserVoteTracker>(); 
	}
	
	public UserManager(UserActivityManager userActivityManager)
	{
		this(); 
		this.userActivityManager = userActivityManager; 
	}
		
	/**
	 * determines if the user has been active for a specified duration of time 
	 * @param user the user IP address to check 
	 * @param milliSeconds the duration in milliseconds that the user needs to be inactive for 
	 * @return true if the user has been inactive for the specified amount of time
	 */
	public boolean userHasBeenInactiveForADuration(String user, double milliSeconds)
	{
		Date lastUserTime = getLastUserActivity(user);  
		
		if(lastUserTime == null) return true; 
		
		long difference = Calendar.getInstance().getTimeInMillis() - lastUserTime.getTime(); 		
		
		return difference > milliSeconds; 
	}
	
	/**
	 * updates the entry for a given user with the specified time 
	 * @param updateTime the time that the user made the most recent update 
	 * @param userIp the ip address identifying the user 
	 */
	public void updateUser(Date updateTime, String userIp)
	{
		// clear if there are too many users 
		if(lastUserActivity.size() > 50)
			lastUserActivity.clear(); 
		
		Date currentTime = Calendar.getInstance().getTime(); 
		
		if(updateTime != null) currentTime = updateTime; 
		
		lastUserActivity.putIfAbsent(userIp, currentTime); 
	}
	
	public void resetUserVotesIfAble(Date updateTime,  String userIp)
	{
		if(!userCommentActivity.containsKey(userIp)) return; 			
		
		Date currentTime = Calendar.getInstance().getTime(); 
		if(updateTime != null) currentTime = updateTime; 
		
		Long difference = currentTime.getTime() -   userCommentActivity.get(userIp).getEarliestCommentPosted().getTime(); 
		if(difference >= TIME_UNTIL_VOTES_RESET)
			userCommentActivity.remove(userIp); 
	}
	
	public int getNumberOfUsersBeingTrackedForVotes()
	{
		return userCommentActivity.size(); 
	}	
	
	public VotingResult updateUserCommentActivity(Date updateTime, String userIp, int objectId)
	{		
		if(userCommentActivity.size() > 50)
			userCommentActivity.clear(); 
		
		Date currentTime = Calendar.getInstance().getTime(); 
		if(updateTime != null) currentTime = updateTime; 
		

		if(userActivityManager != null && userActivityManager.userHasVotedForObjectBefore(userIp, objectId))
		{
			return new VotingResult(VotingMessage.ALREADY_VOTED, ""); 
		}
		
		UserVoteTracker userTracker = new UserVoteTracker(); 
		if(userCommentActivity.containsKey(userIp))
			userTracker = userCommentActivity.get(userIp); 
		else
			userCommentActivity.putIfAbsent(userIp, userTracker); 
		
		if(userTracker.getEarliestCommentPosted() != null) 
		{
			boolean userCanPostComment = userTracker.getNumVotes() < MAX_NUMBER_OF_VOTES_PER_TIME_INTERVAL;
			if(!userCanPostComment) return new VotingResult(VotingMessage.TOO_MANY_VOTES, ""); 
		}				
		
		if(userActivityManager != null) userActivityManager.updateUserActivityEntry(userIp, objectId); 		
		userTracker.setEarliestCommentPosted(currentTime); 				
		
		userTracker.changeVotes(1); 	
		
		return new VotingResult(VotingMessage.SUCCESS, ""); 
	}
	
	public int getUserCount()
	{
		return lastUserActivity.size(); 
	}
	
	public int getUserVoteCount(String user)
	{
		if(userCommentActivity.containsKey(user))
			return userCommentActivity.get(user).getNumVotes(); 
		else
			return 0; 
	}
	
	public boolean userIsAuthenticated(String user, String password)
	{
		if(password == null) return false; 
		
		return password.equals(SUPER_SECRET_ADMIN_PASSWORD); 		
	}
	
	public String authenticationResultToJSON(AuthenticationResult result)
	{
		return new Gson().toJson(result); 
	}
		
	private Date getLastUserActivity(String user)
	{
		if(lastUserActivity.containsKey(user))
			return lastUserActivity.get(user); 
		else
			return null; 
	}
}
