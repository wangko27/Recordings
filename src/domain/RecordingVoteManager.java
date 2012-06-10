package domain;

import bean.Recording;
import domain.security.UserManager;
import exception.RecordingException;

public class RecordingVoteManager extends BasicVoteManager<Recording> 
{
	private RecordingManager recordingManager; 
	
	public RecordingVoteManager(RecordingManager recordingManager, UserManager manager) 
	{
		super(manager);
		this.recordingManager = recordingManager; 
	}

	@Override
	protected int voteForObject(int upOrDownVote, Recording object) throws RecordingException
	{	
		if(object == null) 
			throw new RecordingException(new IllegalArgumentException("invalid recording")); 
		
		if(upOrDownVote < 0)
		{
			object.setDownvotes(object.getDownvotes() + 1); 	
			recordingManager.saveOrUpdate(object); 
		}
		else
		{
			object.setUpvotes(object.getUpvotes() + 1);
			recordingManager.saveOrUpdate(object); 
		}
		
		return object.getUpvotes() - object.getDownvotes(); 
	}

}
