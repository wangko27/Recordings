package domain;

import java.util.ArrayList;
import java.util.List;

import util.StringUtil;

import bean.Recording;
import bean.SmartSong;
import bean.SongInstance;
import bean.TableElement;

public class TableElementManager {
	
	public List<TableElement> songToTableElements( SmartSong song )
	{
		List<TableElement> tableElements = new ArrayList<TableElement>();

		if( song.getCount() > 4 )
			tableElements.add(new TableElement("Known Recordings", song.getCount()));
		
		tableElements.add(new TableElement("First Appearance", song.getFirstPlayed()));
		tableElements.add(new TableElement("Last Appearance", song.getLastPlayed()));
		
		if( StringUtil.hasValue(song.getAlias()) )
			tableElements.add(new TableElement("Alias", song.getAlias()));
		
		return tableElements;
	}
	
	public List<TableElement> songInstancesToTableElements( List<SongInstance> songInstances )
	{
		List<TableElement> tableElements = new ArrayList<TableElement>();
		for (SongInstance songInstance : songInstances)
		{
			TableElement tableElement = null;
			
			if( songInstances.size() > 4 )
				tableElement = new TableElement(songInstance.getTrackListing().toString() + ".", songInstance.getSong().getValue());
			else
				tableElement = new TableElement("", songInstance.getSong().getValue());
			
			
			if( songInstance.getSong() != null )
				tableElement.setHref("song?id=" + songInstance.getSong().getId());
			tableElements.add(tableElement);
		}
		
		return tableElements;
	}
	
	public List<TableElement> recordingToTableElements( Recording recording )
	{
		List<TableElement> tableElements = new ArrayList<TableElement>();

		if( recording.getRecordingType() != null )
			tableElements.add(new TableElement("Type", recording.getRecordingType().getValue()));
		
		if( StringUtil.hasValue(recording.getName()) )
			tableElements.add(new TableElement("Name", recording.getName()));
		
		if( recording.getMedia() != null )
			tableElements.add(new TableElement("Media", recording.getMedia().getValue()));
		
		if( recording.getQuality() != null )
			tableElements.add(new TableElement("Best Known Quailty", recording.getQuality().getValue()));
		
		if( recording.getFormat() != null )
			tableElements.add(new TableElement("Best Known Format", recording.getFormat().getValue()));
		
		return tableElements;
	}
}
