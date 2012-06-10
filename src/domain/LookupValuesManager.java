package domain;

import java.util.Collection;
import java.util.List;

import util.StringUtil;
import bean.BasicCommentBean;
import bean.Recording;
import bean.SimpleBean;
import bean.Song;
import bean.SongComment;
import bean.SongInstance;
import data.RecordingData;
import data.SimpleEntityData;
import data.SongCommentData;
import data.SongInstanceData;
import data.common.ISimpleEntityData;
import exception.RecordingException;

public class LookupValuesManager {
	
	private ISimpleEntityData simpleEntityData;
	
	public LookupValuesManager() 
	{
		simpleEntityData = new SimpleEntityData();
	}
	
	public LookupValuesManager(ISimpleEntityData simpleEntityData)
	{
		this.simpleEntityData = simpleEntityData; 
	}

	public void updateLookupValue(String category, String originalId, String updatedValue) throws RecordingException 
	{
		if( StringUtil.hasValue(category) && StringUtil.hasValue(originalId) && StringUtil.hasValue(updatedValue) )
		{
			SimpleBean simpleBean = simpleEntityData.getSimpleBean(StringUtil.stringToInt(originalId), category);
			
			simpleBean.setValue(updatedValue);
			
			simpleEntityData.saveOrUpdateSimpleBean(simpleBean);
		}
	}

	public void mergeLookupValues(String category, String originalId, String mergeId) throws Exception 
	{
		try
		{
			if( StringUtil.hasValue(category) && StringUtil.hasValue(originalId) && StringUtil.hasValue(mergeId) )
			{
				SimpleBean mergeFromSimpleBean = simpleEntityData.getSimpleBean(StringUtil.stringToInt(originalId), category);
				SimpleBean mergeToSimpleBean = simpleEntityData.getSimpleBean(StringUtil.stringToInt(mergeId), category);
				
				if( category.equals("Song") )
				{
					// need to fix iterate over songinstance, songtag, songcomment, and useractivity
					mergeSongForSongInstance(mergeFromSimpleBean, (Song) mergeToSimpleBean);
					mergeSongCommentForSong(mergeFromSimpleBean, (Song) mergeToSimpleBean);
					// TODO: song tag needs to merged over (not implemented)
				}
				else
				{
					mergeSimpleBeanForRecordings(category, mergeFromSimpleBean, mergeToSimpleBean);
				}
	
				simpleEntityData.delete(mergeFromSimpleBean);
			}
		}
		catch( Exception e )
		{
			throw new Exception(e);
		}
	}
	
	private void mergeSongForSongInstance( SimpleBean mergeFromSimpleBean, Song mergeToSimpleBean ) throws Exception
	{
		try
		{
			SongInstanceManager songInstanceManager = new SongInstanceManager(new SongInstanceData());
	
			List<SongInstance> allSongInstances = songInstanceManager.getAllSongInstances();
			for (SongInstance songInstance : allSongInstances) 
			{
				Song song = songInstance.getSong();
				
				if( song != null && song.getValue().equals(mergeFromSimpleBean.getValue()) )
				{
					songInstance.setSong(mergeToSimpleBean);
					songInstanceManager.saveOrUpdateSongInstance(songInstance);
				}
			}
		}
		catch( Exception e )
		{
			throw new Exception(e);
		}
	}
	
	private void mergeSongCommentForSong( SimpleBean mergeFromSimpleBean, Song mergeToSimpleBean )
	{
		CommentsManager commentsManager = new CommentsManager(new SongCommentData(), SongComment.class);

		List<BasicCommentBean> allSongComments = commentsManager.getSortedCommentsForSpecificBean(mergeFromSimpleBean.getId());
		for (BasicCommentBean songComment : allSongComments) 
		{
			SongComment newSongComment = new SongComment();
			newSongComment.setBob(songComment.getBob());
			newSongComment.setSong(mergeToSimpleBean);
			newSongComment.setText(songComment.getText());
			newSongComment.setTimestamp(songComment.getTimestamp());
			newSongComment.setUsername(songComment.getUsername());
			newSongComment.setVoteRank(songComment.getVoteRank());
			
			mergeToSimpleBean.getComments().add(newSongComment);
			simpleEntityData.saveOrUpdateSimpleBean(mergeToSimpleBean);
		}
	}
	
	private void mergeSimpleBeanForRecordings( String category, SimpleBean mergeFromSimpleBean, SimpleBean mergeToSimpleBean ) throws Exception 
	{
		RecordingManager recordingManager = new RecordingManager(new RecordingData());
		
		// iterate over the recordings to find matches of the simple value about to be merged
		Collection<Recording> recordings = recordingManager.getAllRecordings();
		for (Recording recording : recordings) 
		{
			SimpleBean simpleBean = recording.getSimpleBean(category);
			
			// if the currently iterated recording has the simple bean that's about to be merged
			if( simpleBean != null && simpleBean.getValue().equals(mergeFromSimpleBean.getValue()) )
			{
				recording.setSimpleBean(mergeToSimpleBean);
				recordingManager.saveOrUpdate(recording);
			}
		} 
	}
}
