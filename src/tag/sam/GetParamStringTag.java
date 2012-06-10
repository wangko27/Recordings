package tag.sam;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import servlet.SAMFacade;

public class GetParamStringTag extends TagSupport 
{
	private static final long serialVersionUID = 1L;
	
	private String prefix;
	private String name;
	private String val;
	
	/**
	 * where execution starts for this tag
	 */
	public int doStartTag() throws JspException
    {
        try
        {
            pageContext.getOut().print(SAMFacade.getParamString(prefix, name, val));
        }
        catch( Exception e) { }
 
        return SKIP_BODY;
    }

	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
}	
