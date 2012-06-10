package servlet;

import javax.servlet.ServletConfig;

import bean.SmartSong;
import data.SmartSongData;
import data.security.SongUserData;
import domain.BasicVoteManager;
import domain.SmartSongManager;
import domain.SmartSongVoteManager;
import domain.security.UserActivityManager;
import domain.security.UserManager;


public class SongVoteController extends BasicVoteController<SmartSong>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BasicVoteManager<SmartSong> voteManager; 
	private SmartSongManager songManager; 
	
	public SongVoteController()
	{		
		UserManager user = new UserManager(new UserActivityManager(new SongUserData()));
		SmartSongManager manager = new SmartSongManager(new SmartSongData()); 
		initialize(manager, user,new SmartSongVoteManager(manager, user)); 
	}
	
	public SongVoteController(SmartSongManager recManager, UserManager userManager, SmartSongVoteManager voteManager)
	{
		initialize(recManager, userManager, voteManager); 
	}
	
	public void init(ServletConfig config)
	{
		UserManager manager = new UserManager(new UserActivityManager(new SongUserData()));
		SmartSongManager recManager = new SmartSongManager(new SmartSongData()); 
		initialize(recManager,  manager, new SmartSongVoteManager(recManager, manager)); 
	}
	
	private void initialize(SmartSongManager recManager,  UserManager userManager, SmartSongVoteManager voteManager)
	{
		this.voteManager = voteManager; 
		this.songManager = recManager; 
	}				
	
	public SmartSong getBeanById(int id)
	{
		return songManager.getSmartSong(id);  
	}

	@Override
	protected BasicVoteManager<SmartSong> getVoteManager() 
	{
		return voteManager; 
	}
}
