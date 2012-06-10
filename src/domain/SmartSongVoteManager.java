package domain;

import bean.SmartSong;
import domain.security.UserManager;
import exception.RecordingException;

public class SmartSongVoteManager extends BasicVoteManager<SmartSong> 
{
	private SmartSongManager smartSongManager; 

	public SmartSongVoteManager(UserManager manager) 
	{
		super(manager);
	}

	public SmartSongVoteManager(SmartSongManager manager, UserManager user)
	{
		super(user); 
		this.smartSongManager = manager; 
	}

	@Override
	protected int voteForObject(int upOrDownVote, SmartSong object)
			throws RecordingException {

		
		if(object == null) 
			throw new RecordingException(new IllegalArgumentException("invalid song")); 				
		
		SmartSong updatedSong = new SmartSong();
		updatedSong.setId(object.getId()); 
		updatedSong.setUpvotes(object.getUpvotes()); 
		updatedSong.setDownvotes(object.getDownvotes()); 
		
		if(upOrDownVote < 0)
		{		 
			updatedSong.setDownvotes(object.getDownvotes() + 1); 
			smartSongManager.update(updatedSong); 
		}
		else
		{
			updatedSong.setUpvotes(object.getUpvotes() + 1); 
			smartSongManager.update(updatedSong); 
		}
		
		return updatedSong.getUpvotes() - updatedSong.getDownvotes(); 
	}

}
