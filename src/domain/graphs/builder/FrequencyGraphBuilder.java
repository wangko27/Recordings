package domain.graphs.builder;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import bean.graphs.GraphBean;
import bean.graphs.GraphRequestBean;
import domain.common.IGraphDataAccessor;
import exception.RecordingException;

public class FrequencyGraphBuilder extends BasicGraphBuilder
{

	public FrequencyGraphBuilder(IGraphDataAccessor dataAccessor) {
		super(dataAccessor);
	}
	
	
	public GraphBean<?, ?> getBasicGraph(GraphRequestBean request) throws RecordingException
	{
		GraphBean<?, ?> generatedGraph; 
		
		Map<Comparable<?>, Integer> results = getDataAccess().groupItemsByCount(request.getxAxisField());
		
		// pie graphs need the special style and data set generated 
		if(request.getGraphStyle() != null && request.getGraphStyle().equals("pie"))
		{				  
			generatedGraph = getPieGraph(results); 
			generatedGraph.setStyle("pie"); 
		}
		else
			generatedGraph = getFrequencyGraph(results);
		
		generatedGraph.setxAxisLabel(request.getxAxisField()); 
		generatedGraph.setyAxisLabel("Frequency"); 
		generatedGraph.setGraphTitle("Number of " + getDataAccess().dataClassString() + "s per " + request.getxAxisField());
		
		// determine whether we need to round x values or not 
		Class<? extends Comparable<?>> xaxisType = getExpectedType(request.getxAxisField());
		if(isInteger(xaxisType))
			generatedGraph.setRoundXAxis(true);
		
		return generatedGraph; 
	}
	

	/**
	 *  generates a plot of a given field and the number of a Bean that have the same value for 
	 *  each value of the field  that exists 
	 *  
	 * @param results a Map where each pair represents a value of the field and the number of occurrences for that field value
	 * @return a GraphBean object that represents the frequency Graph 
	 */
	protected final GraphBean<Double, Integer> getFrequencyGraph(Map<Comparable<?>, Integer> results)
	{				
		GraphBean<Double, Integer> actualGraph = new GraphBean<Double, Integer>(); 
			
		for(Entry<Comparable<?>, Integer> dataPoint : results.entrySet())
		{
			if(dataPoint.getKey() == null) continue; 
			
			if(!isNumeric(dataPoint.getKey()))
			{				
				actualGraph.getxTicks().add( dataPoint.getKey());
			}
		}
		
		Collections.sort(actualGraph.getxTicks()); 
		
		for(Entry<Comparable<?>, Integer> dataPoint : results.entrySet())
		{
			if(dataPoint.getKey() == null) continue; 
			
			if(!isNumeric(dataPoint.getKey()))
			{				
				actualGraph.addDataPoint( new Double(actualGraph.getxTicks().indexOf(dataPoint.getKey())) , new Integer(dataPoint.getValue()));
			}
			else
				actualGraph.addDataPoint( Double.parseDouble(dataPoint.getKey().toString())  , new Integer(dataPoint.getValue())); 
		}
		
		return actualGraph; 
	}				
	
	/**
	 * creates a pie chart from a generated map of column value->number of beans
	 * @param frequency a map that represents the number of beans that contain each value of a column 
	 * @return a GraphBean that represents a pie chart for the given map
	 */
	protected GraphBean<?, Integer> getPieGraph(Map<Comparable<?>, Integer> frequency)
	{		
		GraphBean<String, Integer> actualGraph = new GraphBean<String, Integer>();
		
		for(Entry<?, Integer> dataPoint : frequency.entrySet())
		{
			actualGraph.addDataPoint( dataPoint.getKey().toString() , new Integer(dataPoint.getValue()));			
		}
		
		return actualGraph; 
	}
}
