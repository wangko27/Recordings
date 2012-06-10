package servlet;

import javax.servlet.ServletConfig;

import bean.Recording;
import data.RecordingData;
import data.security.RecordingUserData;
import domain.BasicVoteManager;
import domain.RecordingManager;
import domain.RecordingVoteManager;
import domain.security.UserActivityManager;
import domain.security.UserManager;

public class RecordingVoteController extends BasicVoteController<Recording>  
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RecordingVoteManager voteManager; 
	private RecordingManager recordingManager; 
	
	public RecordingVoteController()
	{
		UserManager user = new UserManager(new UserActivityManager(new RecordingUserData()));
		RecordingManager manager = new RecordingManager(new RecordingData()); 
		initialize(manager, user,new RecordingVoteManager(manager, user)); 
	}
	
	public RecordingVoteController(RecordingManager recManager, UserManager userManager, RecordingVoteManager voteManager)
	{
		initialize(recManager, userManager, voteManager); 
	}
	
	public void init(ServletConfig config)
	{
		UserManager manager = new UserManager(new UserActivityManager(new RecordingUserData())); 
		RecordingManager recManager = new RecordingManager(new RecordingData()); 
		initialize(recManager,  manager, new RecordingVoteManager(recManager, manager)); 
	}
	
	private void initialize(RecordingManager recManager,  UserManager userManager, RecordingVoteManager voteManager)
	{
		this.voteManager = voteManager; 
		this.recordingManager = recManager; 
	}				
	
	public Recording getBeanById(int id)
	{
		return recordingManager.getRecording(id); 
	}

	@Override
	protected BasicVoteManager<Recording> getVoteManager() 
	{
		return voteManager; 
	}
}
