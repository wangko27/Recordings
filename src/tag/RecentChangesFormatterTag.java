package tag;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class RecentChangesFormatterTag extends TagSupport
{
	private int summaryLength; 
	private String item; 
	private String summary; 
	private Date date; 
	
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getSummaryLength() {
		return summaryLength;
	}

	public void setSummaryLength(int summaryLength) {
		this.summaryLength = summaryLength;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int doStartTag() throws JspException
	{
		try {
			pageContext.getOut().print(generateText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SKIP_BODY; 	
	}
	
	public String generateText()
	{
		if(item.equals("summary"))
		{
			if(summary.length() < summaryLength) return summary; 
				
			return summary.substring(0, summaryLength) + " ..."; 
		}
		else if (item.equals("date"))
		{
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy 'at' hh:mm aa"); 
			Calendar instance = Calendar.getInstance();
			instance.setTime(date); 
			
			format.setCalendar(instance); 
			return format.format(date); 
		}
		
		
		return "undefined"; 
	}
}
