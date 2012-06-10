package tag.sam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import servlet.SAMFacade;

public class GetParamTag extends TagSupport 
{
	private static final long serialVersionUID = 1L;
	
	private String var;
	private String val;
	private String samVal;
	private String pref;
	private String defaultVal;
	
	/**
	 * where execution starts for this tag
	 */
	public int doStartTag() throws JspException
    {
        try
        {
            pageContext.setAttribute(var, determineParam());
        }
        catch( Exception e) { }
 
        return SKIP_BODY;
    }
	
	public String determineParam()
	{
		SAMFacade samFacade = new SAMFacade();
		return samFacade.determineParam((HttpServletRequest)pageContext.getRequest(), val, samVal, pref, defaultVal);
	}

	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public String getSamVal() {
		return samVal;
	}
	public void setSamVal(String samVal) {
		this.samVal = samVal;
	}
	public String getPref() {
		return pref;
	}
	public void setPref(String pref) {
		this.pref = pref;
	}
	public String getDefaultVal() {
		return defaultVal;
	}
	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}
}	
