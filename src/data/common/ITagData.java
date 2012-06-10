package data.common;

import java.util.List;

import bean.RecordingTag;
import bean.SongTag;

public interface ITagData {
	
	public List<SongTag> getAllSongTags();
	public List<RecordingTag> getAllRecordingTags();

}
