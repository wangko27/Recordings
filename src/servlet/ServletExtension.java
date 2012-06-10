package servlet;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logging.RecordingsLogger;
import exception.RecordingException;


/**
 * Boiler-plate controller operations
 */
abstract public class ServletExtension extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// http://www.javapractices.com/topic/TopicAction.do?Id=181
	protected void redirect( String destinationURLPattern, HttpServletResponse response) throws IOException 
	{
		String urlWithSessionID = response.encodeRedirectURL(destinationURLPattern.toString());
		response.sendRedirect(urlWithSessionID);
	}
 
    /**
     * Generates a jsp page which customizes itself based on attributes provided
     * by the calling servlet in the HttpServletRequest object.
     * 
     * @param req - the request object to be passed to the page shell.
     * @param res - the response object to be passed to the page shell.
     * @return void
     */
    protected void loadJSP(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, java.io.IOException {
        request.getRequestDispatcher(page).forward(request, response);
    }
    
    /**
     * Determines whether there is a request parameter
     * with the passed in name, and if it has a value of 'yes'.
     * 
     * This can be used when there are multiple submit buttons
     * to differentiate which one was chosen
     * @param request
     * @param param
     * @return
     */
    protected boolean isParamSet( HttpServletRequest request, String param )
    {
    	boolean isParamSet = false;
    	
    	String paramValue = request.getParameter(param);
    	
    	if( paramValue != null )
    		isParamSet = true;
    	
    	return isParamSet;
    }
    
    /**
     * Sends an error message to the view
     * @param request
     * @param errorMessage
     */
    protected void addErrorMessage( HttpServletRequest request, String errorMessage )
    {
    	System.out.println("\n*****Error: " + errorMessage);
    	request.setAttribute("errorMessage", errorMessage);
    }
    
    /**
     * Prints stack trace, and then sends an error message to the view
     * @param request
     * @param mre
     */
    protected void addErrorMessage( HttpServletRequest request, RecordingException mre )
    {
    	StringBuilder sb = new StringBuilder(); 
    	sb.append(mre.getMessage() + '\n'); 
    	for(StackTraceElement element : mre.getStackTrace())
    	{
    		sb.append("     " + element.toString() + '\n'); 
    	}
    	RecordingsLogger.error(sb.toString()); 
    	
    	mre.printStackTrace();
    	addErrorMessage(request, mre.getMessage());
    }
    
    /**
     * Adds a jQuery/js file to a collection 
     * @param fileSource the filename of the file to add to the collection 
     * @param jQueryList the pre-existing collection of files. If null, creates a new collection
     * @return the collection of files
     */
    protected Collection<String> addJQuery(String fileSource, Collection<String> jQueryList)
    {
    	return addToList("js", fileSource, jQueryList); 
    }
    
    /**
     * adds a CSS file to a collection of CSS files
     * @param fileSource the filename (no paths) of the css file 
     * @param cssList the pre-existing collection of css files. If null, creates a new collection
     * @return the collection of css files 
     */
    protected Collection<String> addCSS(String fileSource, Collection<String> cssList)
    {
    	return addToList("css", fileSource, cssList);  
    }
    
    private Collection<String> addToList(String rootDir, String fileSource, Collection<String> targetList)
    {
    	Collection<String> results = targetList; 
    	if(results == null) results = new ArrayList<String>(); 
    		
    	results.add(rootDir + "/" + fileSource); 
    	
    	return results; 
    }
    
    /**
     * sets the page that pageShell will include in its body. 
     * @param req the current HTTP request object
     * @param pageFileName the name of the page that will be embedded into pageShell.jsp 
     */
    protected void setEmbeddedPage(HttpServletRequest req, String pageFileName)
    {
    	req.setAttribute("embeddedPage", pageFileName);     	   
    }
    
    /**
     * sets the embeddedJs attribute on pageShell.jsp that determines which js/jQuery files to load
     * @param req the current HTTP request 
     * @param jsFiles a collection of js/jQuery files to load 
     */
    protected void setEmbeddedJs(HttpServletRequest req, Collection<String> jsFiles)
    {
    	req.setAttribute("embeddedJs", jsFiles); 
    }
    
    /**
     * Sets the embeddedCSS attribute on pageShell.jsp that determines css files to load 
     * @param req current HTTP request 
     * @param cssFiles Collection of css file names to include with the current request     
     */
    protected void setEmbeddedCSS(HttpServletRequest req, Collection<String> cssFiles)
    {
    	req.setAttribute("embeddedCSS", cssFiles); 
    }
    
    /**
     * Convenience method to get a parameter from SAM
     * 
     * @param req
     * @param prefix
     * @param field
     * @return
     */
    protected String getParamFromSAM( HttpServletRequest req, String prefix, String field )
	{
		SAMFacade samFacade = new SAMFacade();
		return samFacade.determineParam(req, req.getParameter(field), "s_" + prefix + "_" + field, req.getParameter("pref"));
	}
    
    protected void noCaching(HttpServletResponse response)
    {
    	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
    	response.setHeader("Pragma","no-cache"); //HTTP 1.0
    	response.setDateHeader ("Expires", 0);
    }
}
