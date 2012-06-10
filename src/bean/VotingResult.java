package bean;

public class VotingResult
{
	public enum VotingMessage
	{
		SUCCESS,
		TOO_MANY_VOTES, 
		ALREADY_VOTED
	}	
	
	VotingMessage result; 
	String message; 
	int newObjectVoteCount;
	
	public VotingResult(VotingMessage result, String message)
	{
		this.result = result; 
		this.message = message; 
	}
	
	public VotingResult(VotingMessage result, String message, int newVoteCount)
	{
		this(result, message); 
		this.newObjectVoteCount = newVoteCount; 
	}

	public VotingMessage getResult() {
		return result;
	}

	public String getMessage() {
		return message;
	}

	public int getNewObjectVoteCount() {
		return newObjectVoteCount;
	}			
}