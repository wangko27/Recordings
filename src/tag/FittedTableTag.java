package tag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import util.NumberUtil;
import util.StringUtil;
import bean.TableElement;

/**
 */
public class FittedTableTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	
	private List<TableElement> tableElements;
	private int cols;
	private String id;
	private String cssclass;
	private boolean autoFontSize = false;
	
	/**
	 * where execution starts for this tag
	 */
	public int doStartTag() throws JspException
    {
        try
        {
        	if( cols == 0 )
        		cols = determineCols();
        	
            pageContext.getOut().print(generateHTML());
            
            // set this so the next time the tag is called it's called fresh
            cols = 0;
        }
        catch( Exception e) { }
 
        return SKIP_BODY;
    }
	
	public int determineCols()
	{
		int colsToUse = 0;
		
		int maxItemLengthThreshold = 30;
		int longestItemLength = getLongestItemLength();
		
		int total = tableElements.size();
		
		if( total <= 5 )
			colsToUse = 1;
		else if( total <= 10 )
			colsToUse = 2;
		else if( total <= 15 )
			colsToUse = 3;
		else
			colsToUse = 4;
		
		if( this.tableElements.size() > 4 && colsToUse >= 3 && longestItemLength > maxItemLengthThreshold )
			colsToUse--;
		
		return colsToUse;
	}
	
	private int getLongestItemLength()
	{
		int longestItemLength = 0;
		
		for (TableElement tableElement : tableElements) {
			String value = tableElement.getValue();
			if( StringUtil.hasValue(value) && value.length() > longestItemLength )
				longestItemLength = value.length();
		}
		
		return longestItemLength;
	}
	
	public String generateHTML()
	{
		String html = "";
		
		int total = tableElements.size();
		
		// put in empty elements to make the table "complete"
		if( (total % cols) != 0 )
		{
			// the number of gaps is the total cells minus the actual total values
			int gap = NumberUtil.getNextHighestModValue(total, cols) - total;
			
			// fill in the list with the empty cells/gaps
			for( int x = 0; x < gap; x++ )
				tableElements.add(new TableElement("&nbsp;","&nbsp;"));
			
			total = tableElements.size();
		}
		
		html += "<table";
		
		// add id and class attributes
		if( StringUtil.hasValue(this.id) )
			html += " id = \"" + this.id + "\"";
		
		html += " class = \"stdtable ";
		if( StringUtil.hasValue(this.cssclass) )
			html += this.cssclass;
		html += "\"";

		// apply auto font size
		if( autoFontSize )
			html += " style=\"font-size:" + getAutoFontSize() + "\"";
		
		// end of table starting tag
		html += ">";
		
		for( int i = 0; i < (total/cols); i++)
		{
			html += "<tr>";
			for( int j = 0; j < cols; j++ )
			{
				int index = (j*(total/cols)) + i;
				
				TableElement tableElement = tableElements.get(index);
				
				html += "<th class=\"label\">" + tableElement.getLabel() + "</th>";
				
				if( StringUtil.hasValue(tableElement.getHref()) )
					html += "<td style=\"font-size:" + getTDFontSize(tableElement.getValue()) + "\"><a href=\"" + tableElement.getHref() + "\">" + tableElement.getValue() + "</a></td>";
				else
					html += "<td style=\"font-size:" + getTDFontSize(tableElement.getValue()) + "\">" + tableElement.getValue() + "</td>";
			}
			html += "</tr>";
		}
		html += "</table>";
		
		return html;
	}
	
	/**
	 * Adjust the font size of the individual records
	 * contained within the td tag
	 * @param value
	 * @return
	 */
	private String getTDFontSize( String value )
	{
		int fontSize = 100;
		
		// only adjust fontsize if value has more than 15 characters
		if( tableElements.size() > 15)
		{
			if( value.length() > 20 )
				fontSize = 95;
			if( value.length() > 25 )
				fontSize = 85;
		}
		
		return fontSize + "%";
	}
	
	/**
	 * Adjust the font size of the table as a whole
	 * @return
	 */
	private String getAutoFontSize()
	{
		int fontSize = 100;
		int numOfElements = tableElements.size();

		if( numOfElements < 4 )
		{
			fontSize = 120;
		}
		else
		{
			int baseNum = 100;
			fontSize += (baseNum / numOfElements);
		}
		
		return fontSize + "%";
	}

	public List<TableElement> getTableElements() {
		return tableElements;
	}
	public void setTableElements(List<TableElement> tableElements) {
		this.tableElements = tableElements;
	}
	public int getCols() {
		return cols;
	}
	public void setCols(int cols) {
		this.cols = cols;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCssclass() {
		return cssclass;
	}
	public void setCssclass(String cssclass) {
		this.cssclass = cssclass;
	}
	public boolean isAutoFontSize() {
		return autoFontSize;
	}
	public void setAutoFontSize(boolean autoFontSize) {
		this.autoFontSize = autoFontSize;
	}
}