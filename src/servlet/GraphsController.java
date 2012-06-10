package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logging.RecordingsLogger;

import bean.graphs.GraphBean;
import bean.graphs.GraphRequestBean;
import bean.graphs.GraphValidationBean;
import bean.graphs.GraphValidationBean.GraphValidationResult;
import domain.common.ICustomGraphRequestValidator;
import domain.graphs.GraphsManager;
import domain.graphs.validation.GraphRequestValidator;
import exception.RecordingException;

@WebServlet("/GraphsController")
public class GraphsController extends ServletExtension 
{
	private ThreadLocal<GraphsManager> graphsManager = new ThreadLocal<GraphsManager>(); 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void setGraphsManager(GraphsManager graphsManager)
	{
		this.graphsManager.set(graphsManager); 
	}

	/**
	 * gets the current GraphsManager, but also creates a new one of it doesn't exist
	 * @return the current GraphsManager
	 */
	public GraphsManager getGraphsManager()
	{
		if(graphsManager.get() == null) graphsManager.set(new GraphsManager());  
		
		return graphsManager.get(); 
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			
		}
		catch( Exception e )
		{
			addErrorMessage(req, new RecordingException(e)); 
		}
		
		loadPage(req, response);
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{	
		if (req.getParameter("pregenerated") != null)
		{			
			req.setAttribute("pregeneratedlink",   req.getQueryString().substring( "pregenerated=yes".length() + 1 ) );
			loadPage(req, response); 
		}
		else if(req.getParameter("getgraph") != null)
		{
			this.processAJAXRequest(req, response); 
		}		
		else			
			loadPage(req, response);
	}
	
	private GraphRequestBean buildRequest(HttpServletRequest req)
	{
		try
		{
			// construct GraphRequest bean from request
			String xAxisField = req.getParameter("x").trim(); 
			String yAxisField = req.getParameter("y").trim(); 			
			GraphRequestBean request = new GraphRequestBean(); 
			request.setxAxisField(xAxisField); 
			request.setyAxisField(yAxisField); 		
			request.setGraphType(req.getParameter("type").trim()); 
			request.setGraphStyle(req.getParameter("style").trim()); 
			request.setGraphDataSource(req.getParameter("graphdatasource").trim());  
			 
			if(request.getGraphType().equals("specialized"))
				request.setGraphSpecialization(req.getParameter("specialization")); 
			
			if(request.getGraphType().equals("frequencywithvalue"))
			{
				request.setGraphTargetColumn(req.getParameter("fwvColumn").trim()); 
				request.setGraphTargetColumnValue(req.getParameter("fwvColumnValue").trim()); 
			}
			
			if(getGraphsManager().getGraphDataType(request) == null)
				throw new Exception();   
			
			return request; 
		}
		catch(Exception exc)
		{
			GraphRequestBean bean = new GraphRequestBean(); 
			bean.setGraphType("null"); 
			bean.setGraphDataSource("recording"); 
			return bean; 
		}
	}
	
	/**
	 * processes AJAX requests made by the user clicking on the "graph" button. 
	 * Writes a JSON string representing a data set to the response. 
	 * @param req
	 * @param response
	 * @throws IOException
	 */
	private void processAJAXRequest(HttpServletRequest req, HttpServletResponse response) throws IOException
	{		
		try
		{
			GraphRequestBean request = buildRequest(req); 
			
			// validate GraphRequest 
			GraphRequestValidator validator = new GraphRequestValidator(getGraphsManager().getGraphDataType(request).getDataClass());
			GraphValidationBean validationResult  = null; 
			
			if(getGraphsManager().getGraphDataType(request) instanceof ICustomGraphRequestValidator)
				validationResult = validator.validate(request, (ICustomGraphRequestValidator)getGraphsManager().getGraphDataType(request)); 
			else
				validationResult = validator.validate(request);						 
			
			// validation fails so write back an error message
			if(validationResult.getResult() != GraphValidationResult.SUCCESS)
			{
				PrintWriter pw = response.getWriter(); 
				pw.write(validationResult.toJSONString()); 
				pw.close();
				return; 
			}						
	
			// TODO bug: if the server throws an exception at this point, the script no longer works on the client. 
			// retrieve the graph and write a JSON string of the graph 
			GraphBean<?,?> generatedGraph = getGraphsManager().getGraph(request); 								
			
			PrintWriter pw =  response.getWriter();
			pw.write(getGraphsManager().toJSONString(generatedGraph));
			
			RecordingsLogger.info("Graph", req.getRemoteAddr()); 
			RecordingsLogger.info("JSON: " + getGraphsManager().toJSONString(generatedGraph)); 
	
			pw.close(); 			
			
		}
		catch(RecordingException exc)
		{
			this.addErrorMessage(req, exc); 
		}
	}
	
	private void loadPage( HttpServletRequest req, HttpServletResponse response ) throws ServletException, IOException
	{				
		setEmbeddedPage(req, "graphs.jsp");		 
		
		// set up jquery plug ins for jqPlot
		Collection<String> embeddedJs = this.addJQuery("jquery-1.5.2.min.js", null);
		this.addJQuery("excanvas.min.js", embeddedJs); 		
		this.addJQuery("jquery.jqplot.min.js", embeddedJs);
		this.addJQuery("jqplot.pieRenderer.min.js", embeddedJs);
		this.addJQuery("jqplot.highlighter.mod.js", embeddedJs);
		this.addJQuery("jqplot.percentdisplay.js", embeddedJs); 
		this.addJQuery("graphs.ui.js", embeddedJs);
		this.addJQuery("graphs.processing.js", embeddedJs); 
		this.addJQuery("graphs.js", embeddedJs);
		
		// set up css 
		Collection<String> embeddedCSS = this.addCSS("jquery.jqplot.min.css", null);
		this.addCSS("graphs.css", embeddedCSS); 
		
		// set embedded attributes on page shell
		this.setEmbeddedJs(req, embeddedJs); 
		this.setEmbeddedCSS(req, embeddedCSS); 
		
		// set canned graphs 
		req.setAttribute("cannedgraphs", getGraphsManager().getCannedGraphs()); 
		loadJSP(req, response, "/jsp/pageShell.jsp");
	}

}
