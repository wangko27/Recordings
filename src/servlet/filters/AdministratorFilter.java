package servlet.filters;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.ServletExtension;

public class AdministratorFilter  extends ServletExtension implements Filter   
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest request = (HttpServletRequest) req; 
		HttpServletResponse hsResponse = (HttpServletResponse) response; 
			
		if( request.getSession().getAttribute("authenticated") != null )
		{
			// user is authenticated
			chain.doFilter(req, response); 
		}
		else
		{
			Collection<String> jsCollection = this.addJQuery("jquery-1.5.2.min.js", null); 
			this.setEmbeddedPage(request, "accessdenied.jsp"); 			
			this.setEmbeddedJs(request, jsCollection); 
			this.loadJSP(request, hsResponse, "/jsp/pageShell.jsp");   
		}				
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException 
	{
		// we have to implement this... 
	}

}
