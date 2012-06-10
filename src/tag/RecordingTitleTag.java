package tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import util.StringUtil;

import bean.Recording;
import bean.RecordingType;

/**
 * A custom tag to make the Recording Title generation consistent 
 * across all pages and allow more fine-grained control. 
 * 
 * Note: format is not implemented yet (7-6-11)
 */
public class RecordingTitleTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	
	private Recording recording;
	private String link; // the value of the anchor tag's href attribute
	private String format;
	
	/**
	 * where execution starts for this tag
	 */
	public int doStartTag() throws JspException
    {
        try
        {
        	String html = getRecordingTitle();
            pageContext.getOut().print(html);
        }
        catch( Exception e) { }
 
        return SKIP_BODY;
    }
	
	/**
	 * @return
	 */
	private String getRecordingTitle()
	{
		String recordingTitle = "";
		
		// start link
		recordingTitle += "<a href=\"" + this.getLink() + "\">";
		
		// if it's an album, single or a studio bootleg, etc
		if( isIdentifiableByName(recording) ) 
		{
			String formattedLongDateString = recording.getFormattedLongDateString();
			
			if( StringUtil.hasValue(formattedLongDateString) )
				recordingTitle += "<span class=\"albumfont\">" + 
									formattedLongDateString + " - " + recording.getName() + 
								  "</span>";
			else
				recordingTitle += "<span class=\"albumfont\">" + 
									recording.getName() + 
								  "</span>";
		}
		else
		{
			// show long date and location
			recordingTitle += recording.getFormattedLongDateString() + " - " + recording.getFormattedLocation();
			
			// show venue if there is one
			if( recording.getVenue() != null && StringUtil.hasValue(recording.getVenue().getValue()) )
				recordingTitle += " - " + recording.getVenue().getValue();
		}
		
		// end link
		recordingTitle += "</a>";
		
		return recordingTitle;
	}
	
	/**
	 * Determines whether the passed-in recording should be visually 
	 * identified by it's name, rather than other fields such as venue
	 * 
	 * Ex: Recordings that are albums should be visually identified by name
	 *     Shows should be visually identified by venue, location, etc
	 * 
	 * @param recording
	 * @return
	 */
	private boolean isIdentifiableByName( Recording recording )
	{
		boolean isIdentifiableByTitle = false;
		
		RecordingType recordingType = recording.getRecordingType();
		
		// if the recording has a recording type
		if( recordingType != null && StringUtil.hasValue(recordingType.getValue()) )
		{
			// if the recording type warrants this recording being 
			// referenced by title rather than venue, etc
			if( recordingType.getValue().equals("Album") || 
				recordingType.getValue().equals("Single") || 
				recordingType.getValue().equals("Studio Bootleg") || 
				recordingType.getValue().equals("TV") ||
				recordingType.getValue().equals("Compilation") ||
				recordingType.getValue().equals("Radio") ||
				recordingType.getValue().equals("Interview") )
			{
				isIdentifiableByTitle = true;
			}
		}
		
		return isIdentifiableByTitle;
	}
	
	
	/* GETTERS AND SETTERS */
	
	public Recording getRecording() {
		return recording;
	}
	public void setRecording(Recording recording) {
		this.recording = recording;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
}