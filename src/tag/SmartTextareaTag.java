package tag;

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Displays a textarea that is aware of it's contents.
 * 
 * It can use this information to control it's size.
 */
public class SmartTextareaTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private List<String> contents;
	private String id;
	
	/**
	 * where execution starts for this tag
	 */
	public int doStartTag() throws JspException
    {
        try
        {
        	String html = getTextareaMarkup();
            pageContext.getOut().print(html);
        }
        catch( Exception e) { }
 
        return SKIP_BODY;
    }
	
	/**
	 * Generates the html markup that will make up the textarea
	 * @return
	 */
	protected final String getTextareaMarkup()
	{
		final String CRLF = "\r\n";
		int rows = 10;
		int cols = determineCols();
		String html = "<textarea name=\"" + this.name + "\" cols=\"" + cols + "\" rows=\"" + rows + "\" ";
		
		// if an id is to be added
		if( this.id != null && !this.id.equals(""))
			html += "id=\"" + this.id + "\"";
		
		html += " >";
		
		if( !this.name.equals("columns") && !this.contents.isEmpty() )
			html += CRLF + CRLF;
		
		Iterator<String> i = this.contents.iterator();
		while( i.hasNext() )
		{
			String row = (String)i.next();
			html += row;
			
			if( i.hasNext() || this.name.equals("columns") )
				html += CRLF;
		}
		
		html += "</textarea>";
		
		return html;
	}
	
	/**
	 * Determines the number of columns to add to the text area.
	 * This is determined by setting the number of columns
	 * to the number of characters of the longest value of the contents
	 * @return
	 */
	private int determineCols()
	{
		int cols = this.name.length();
		
		if( !this.contents.isEmpty() )
			cols = determineLongestWidth();
		
		return cols;
	}
	
	/**
	 * Determines the longest row in the 
	 * textarea's contents
	 * @return
	 */
	private int determineLongestWidth()
	{
		int longestWidth = 0;
		for ( String row : this.contents ) {
			if( row != null && row.length() > longestWidth )
				longestWidth = row.length();
		}
		
		return longestWidth;
	}
	
	/*
	 *  getters and setters
	 */
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getContents() {
		return contents;
	}
	public void setContents(List<String> contents) {
		this.contents = contents;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}