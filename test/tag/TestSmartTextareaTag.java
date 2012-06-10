package tag;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestSmartTextareaTag extends SmartTextareaTag
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SmartTextareaTag tag; 
	private List<String> contents; 
	
	@Before
	public void before()
	{
		tag = new SmartTextareaTag(); 
		contents = new ArrayList<String>(); 
		
		tag.setContents(contents); 
	}
	
	@Test
	public void getTextareaMarkup_columnResizesToText()
	{
		tag.setName("test"); 
		String line1 = "here is a line"; 
		String line2 = "here is another very long line";
		
		int maxLength = Math.max(line1.length(), line2.length()); 
		
		contents.add(line1); 
		contents.add(line2); 
		
		tag.setContents(contents); 
		
		assertTrue(tag.getTextareaMarkup().contains("cols=\"" + maxLength + "\""));  
	}
	
}
