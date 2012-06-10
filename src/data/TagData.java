package data;

import java.util.List;

import bean.RecordingTag;
import bean.SongTag;
import data.common.ITagData;

public class TagData extends BasicHibernateDataSource implements ITagData 
{
	public List<SongTag> getAllSongTags()
	{
		List<SongTag> songTags = super.queryDatabaseForList(SongTag.class, "from SongTag");
		
		return songTags;
	}
	
	public List<RecordingTag> getAllRecordingTags()
	{
		List<RecordingTag> recordingTags = super.queryDatabaseForList(RecordingTag.class, "from RecordingTag");
		
		return recordingTags;
	}
}
