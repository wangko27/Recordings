package domain.graphs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.RecordingComment;
import bean.graphs.GraphBean;
import bean.graphs.GraphRequestBean;

import com.google.gson.Gson;

import data.RecordingCommentData;
import data.RecordingData;
import data.SongInstanceData;
import domain.CommentsManager;
import domain.RecordingManager;
import domain.SongInstanceManager;
import domain.common.ICustomGraphRequestValidator;
import domain.common.IGraphDataAccessor;
import domain.graphs.builder.BasicGraphBuilder;
import domain.graphs.builder.FrequencyGraphBuilder;
import domain.graphs.builder.FrequencyWithValueGraphBuilder;
import domain.graphs.builder.GenericGraphBuilder;
import domain.graphs.builder.SpecializedGraphBuilderFactory;
import exception.RecordingException;

/**
 * 	 Handles generation of graphs and other operations for graph beans
 *   This class is not thread safe
 *  
 */
public class GraphsManager 
{
	private Map<String, String> cannedGraphs; 

	private Gson gsonParser; 
		
	private RecordingManager recordingManager = new RecordingManager(new RecordingData());
	private CommentsManager recordingCommentsManager = new CommentsManager(new RecordingCommentData(), RecordingComment.class);  
	private SongInstanceManager songInstanceManager = new SongInstanceManager(new SongInstanceData());  
	
	/** current manager to use for data access **/ 
	private IGraphDataAccessor dataLayer =  recordingManager; 
	
	public GraphsManager()
	{ 
		GraphRequestBean temp = new GraphRequestBean();
		gsonParser = new Gson(); 
		
		cannedGraphs = new HashMap<String, String>(); 
		
		temp = new GraphRequestBean(); 
		temp.setGraphDataSource("songinstance"); 
		temp.setGraphType("specialized"); 
		temp.setGraphSpecialization("firstappearance"); 				
		cannedGraphs.put("Number of songs making their first appearance per year",requestToJSONString(temp));
		
		temp = new GraphRequestBean(); 
		temp.setGraphDataSource("songinstance"); 
		temp.setGraphType("frequencywithvalue"); 
		temp.setxAxisField("Value");
		temp.setGraphTargetColumn("Year"); 
		temp.setGraphTargetColumnValue("1988"); 
		cannedGraphs.put("Most popular songs in 1988",requestToJSONString(temp));
		
		temp = new GraphRequestBean(); 
		temp.setGraphDataSource("recording"); 
		temp.setGraphType("generic"); 
		temp.setxAxisField("Year"); 
		temp.setyAxisField("Country"); 
		cannedGraphs.put("Countries visited each year", requestToJSONString(temp)); 
		
		temp.setGraphDataSource("songinstance"); 
		temp.setGraphType("frequencywithvalue"); 
		temp.setxAxisField("City"); 
		temp.setGraphTargetColumn("Value"); 
		temp.setGraphTargetColumnValue("Egyptian_Reggae"); 
		cannedGraphs.put("Cities featuring the song Egyptian Reggae",requestToJSONString(temp));
				
		temp = new GraphRequestBean(); 
		temp.setGraphDataSource("recording"); 
		temp.setxAxisField("City"); 
		temp.setGraphType("frequency"); 
		cannedGraphs.put("Most played-in cities",requestToJSONString(temp));
		
		temp = new GraphRequestBean(); 
		temp.setGraphDataSource("recording"); 
		temp.setxAxisField("Year"); 
		temp.setGraphTargetColumn("Venue");  
		temp.setGraphTargetColumnValue("Knitting_Factory"); 
		temp.setGraphType("frequencywithvalue"); 
		cannedGraphs.put("Visits to the Knitting Factory each year", requestToJSONString(temp)); 
	}
	
	public GraphsManager(RecordingManager recordingManager, CommentsManager recordingCommentsManager) 
	{
		this(); 
		this.recordingManager = recordingManager; 
		this.recordingCommentsManager = recordingCommentsManager;
	}
	
	public GraphsManager(RecordingManager recordingManager, CommentsManager recordingCommentsManager, SongInstanceManager songInstanceManager)
	{
		this(recordingManager, recordingCommentsManager); 
		this.songInstanceManager = songInstanceManager; 
	}

	
	private IGraphDataAccessor getDataAccess()
	{
		return dataLayer; 
	}
	
	private void setDataAccessor(IGraphDataAccessor access)
	{
		dataLayer = access; 
	}
	
	/**
	 * takes in a graph request and returns the correct IGraphDataAccessor that handles
	 * the type of data the request is graphing
	 * 
	 * @param request a graph request bean with a valid graph data source parameter
	 * @return the IGraphDataAccessor object that will retrieve the data for this bean
	 */
	public IGraphDataAccessor getGraphDataType(GraphRequestBean request)
	{		
		if(request.getGraphDataSource().equals("recording"))
			return recordingManager; 
		else if (request.getGraphDataSource().equals("recordingcomments"))
			return recordingCommentsManager; 
		else if (request.getGraphDataSource().equals("songinstance"))
			return songInstanceManager; 
		return null; 				
	}

	/**
	 * a single facade-type method that takes in a GraphRequestBean and generates a graph from it. 
	 * @param request the graph request object
	 * @precondition request must have been validated previously by a GraphRequestValidator
	 * @return a GraphBean representing the generated graph. 
	 * @throws RecordingException 
	 */
	public GraphBean<?, ?> getGraph(GraphRequestBean request) throws RecordingException
	{					
		setDataAccessor(getGraphDataType(request)); 
		BasicGraphBuilder builder = null; 		
		
		GraphBean<?, ?> generatedGraph = null; 
		
		if(request.getGraphType().equals("frequency")) builder = new FrequencyGraphBuilder(getDataAccess());  			 						
		else if (request.getGraphType().equals("generic"))	builder = new GenericGraphBuilder(getDataAccess()); 			
		else if (request.getGraphType().equals("frequencywithvalue")) builder = new FrequencyWithValueGraphBuilder(getDataAccess());  
		else if (request.getGraphType().equals("specialized"))
		{			
			SpecializedGraphParameters parameters = new SpecializedGraphParameters(request.getGraphSpecialization(), getDataAccess());
			builder = new SpecializedGraphBuilderFactory().createSpecializedGraphBuilder(parameters ); 
		}
		  
		if(builder == null) return null; 
		
		generatedGraph = builder.getGraph(request); 
		
		if(generatedGraph == null) 
			return null; 
		
		generatedGraph.setType(request.getGraphType()); 		
		
		return generatedGraph; 
	}	
	
	/**
	 * converts a GraphBean object to a JSON string for use in a jqPlot call
	 * @param bean the GraphBean to convert
	 * @return the resulting JSON string generated by our friendly JSON library
	 */
	public String toJSONString(GraphBean<?, ?> bean)
	{
		if(bean == null) return "null"; 
		
		Map<String, Object> results = new HashMap<String,Object>(); 
		results.put("data", bean.getDataArrays()); 				
		results.put("xticks", convertTicksToArray(bean.getxTicks()));
		results.put("yticks", convertTicksToArray(bean.getyTicks()));
		results.put("xlabel", bean.getxAxisLabel()); 
		results.put("ylabel", bean.getyAxisLabel()); 
		results.put("title", bean.getGraphTitle()); 
		results.put("style", bean.getStyle()); 
		results.put("type", bean.getType()); 
		results.put("roundxaxis", bean.getRoundXAxis()); 
		results.put("roundyaxis", bean.getRoundYAxis()); 
		
		return gsonParser.toJson(results); 
	}
	
	
	public String requestToJSONString(GraphRequestBean request)
	{
		Map<String, String> results = new HashMap<String, String>(); 
		results.put("graphdatasource", request.getGraphDataSource()); 
		results.put("type", request.getGraphType()); 
		results.put("x", request.getxAxisField());
		results.put("y", request.getyAxisField());
		if(request.getGraphType().toLowerCase().equals("specialized"))
				results.put("specialization", request.getGraphSpecialization());
		
		if(request.getGraphTargetColumn() != null)
		{
			results.put("fwvcolumnvalue", request.getGraphTargetColumnValue());
			results.put("fwvcolumn", request.getGraphTargetColumn()); 
		}	
		
		System.out.println("json: " + gsonParser.toJson(results)); 
		return gsonParser.toJson(results); 
	}    
	
	/**
	 * given an array of objects representing an axis' tick marks, converts it into a jqPlot-compatible
	 * array 2D array of objects.  
	 * 
	 * @param list the List of tick marks, retrieved from a GraphBean object
	 * @return the jqPlot-compatible array of tick marks for a given axis
	 */
	private Object[][] convertTicksToArray(List<Comparable> list)
	{
		Object[][] resultTicks = new Object[list.size()][];
		
		for(int i =0;i<list.size();i++)
		{
			resultTicks[i] = new Object[] { i, list.get(i).toString() }; 
		}
		
		return resultTicks; 
	}
	
	/**
	 * gets the type of a get method in RecordingBean
	 * @param field the name of the field to retrieve the get method type of
	 * @return the return type of the field's get method
	 * @throws RecordingException
	 * @precondition the field must have a get method.  
	 */
	protected  Class<? extends Comparable<?>> getExpectedType(String field) throws RecordingException
	{
		if(getDataAccess() instanceof ICustomGraphRequestValidator)
			return ( (ICustomGraphRequestValidator) getDataAccess()).getExpectedType(field); 
		
		try {
			return (Class<? extends Comparable<?>>) getDataAccess().getDataClass().getMethod("get" + field).getReturnType();
		} catch (SecurityException e) {
			throw new RecordingException(e); 
		} catch (NoSuchMethodException e) {
			throw new RecordingException("precondition was violated: the field " + field + " must have a valid get method",e); 
		}  		 
	}
	
	/**
	 * determines if the given class type is an integer 
	 * @param obj the class to test
	 * @return true if the class is either Integer or int
	 */
	protected boolean isInteger(Class<?> obj)
	{
		return obj.equals(Integer.class) || obj.equals(int.class); 
	}		
	
	
	
	public Map<String, String> getCannedGraphs()
	{
		return cannedGraphs; 
	}
}
