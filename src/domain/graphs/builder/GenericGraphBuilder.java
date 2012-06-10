package domain.graphs.builder;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;

import bean.graphs.GraphBean;
import bean.graphs.GraphRequestBean;
import domain.common.ICustomGraphRequestValidator;
import domain.common.IGraphDataAccessor;
import exception.RecordingException;

public class GenericGraphBuilder  extends BasicGraphBuilder
{

	public GenericGraphBuilder(IGraphDataAccessor dataAccessor) {
		super(dataAccessor);
	}

	public GraphBean<?, ?> getBasicGraph(GraphRequestBean request) throws RecordingException
	{
		GraphBean<?, ?> generatedGraph = null; 
		
		generatedGraph = getGenericGraph(getDataAccess().getAllItems(), request.getxAxisField(), request.getyAxisField());
		
		generatedGraph.setxAxisLabel(request.getxAxisField());
		generatedGraph.setyAxisLabel(request.getyAxisField());
		generatedGraph.setGraphTitle(getDataAccess().dataClassString()+  ": " + request.getxAxisField() + " vs " + request.getyAxisField() );
		
		// determine whether x,y axes need to be rounded
		Class<?> xaxisType = getExpectedType(request.getxAxisField()); 
		Class<?> yaxisType = getExpectedType(request.getyAxisField()); 	
		
		if(isInteger(xaxisType))
			generatedGraph.setRoundXAxis(true);
		if(isInteger(yaxisType))
			generatedGraph.setRoundYAxis(true); 
		
		return generatedGraph; 
	}
	
	private Comparable<?> getObjectValue(String fieldName, IGraphDataAccessor dataSource, Object bean) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		if(dataSource instanceof ICustomGraphRequestValidator)
		{
			ICustomGraphRequestValidator dataAccess = (ICustomGraphRequestValidator) getDataAccess(); 
			Object target = dataAccess.getDataSourceToInvokeGetMethodsOn(bean, fieldName);  
			
			if(target == null) return null; 
			
			return (Comparable<?>) target.getClass().getMethod("get" + fieldName).invoke(target); 
		}
		else
		{
			return (Comparable<?>) getDataAccess().getDataClass().getMethod("get" + fieldName).invoke(bean); 
		}
	}
	
	/**
	 * generates a generic scatter plot of two variables in a graphable Bean. 
	 * 
	 * @param xAxisField the column for the x axis to retrieve the data points
	 * @param yAxisField the column on the y axis 
	 * @precondition xAxisField and yAxisField are valid fields in the bean given by getDataAccess().getDataClass() 
	 * @precondition xaxisType and yaxisType match return types of get.. methods 
	 * @return a GraphBean representing a scatter plot of the x vs. y variables. 
	 * @throws RecordingException 
	 */
	@SuppressWarnings("unchecked")
	protected GraphBean<Number,Number> getGenericGraph( Collection<?> items, String xAxisField, String yAxisField) throws RecordingException
	{
		GraphBean<Number, Number> resultGraph = new GraphBean<Number, Number>(); 
		
		try
		{						
			for(Object bean : items)
			{
				Comparable<?> xField = getObjectValue(xAxisField, getDataAccess(), bean); 
				Comparable<?> yField = getObjectValue(yAxisField, getDataAccess(), bean); 

				if(xField == null || yField == null) continue; 
				
				addToTicksList(resultGraph.getxTicks(),xField);  
				addToTicksList(resultGraph.getyTicks(), yField);  						
			}
			
			// build tick marks first
			Collections.sort(resultGraph.getxTicks());
			Collections.sort(resultGraph.getyTicks()); 
			
			for(Object bean : items)
			{
				// note that xField and yField are guaranteed to be of type X/Y since xaxisType and yaxisType specify the return types of the get methods. 
				Comparable<?> xField = getObjectValue(xAxisField, getDataAccess(), bean); 
				Comparable<?> yField = getObjectValue(yAxisField, getDataAccess(), bean); 
				
				if(xField == null || yField == null) continue; 
				 
				Number xPoint = addToTicksList(resultGraph.getxTicks(),xField);  
				Number yPoint = addToTicksList(resultGraph.getyTicks(),yField);  
									
				resultGraph.addDataPoint(xPoint,yPoint); 
			}
			
			return resultGraph; 
		}
		catch (IllegalArgumentException e) {			
			throw new RecordingException(e); 
		} catch (SecurityException e) {			
			throw new RecordingException(e); 
		} catch (IllegalAccessException e) {			
			throw new RecordingException(e); 
		} catch (InvocationTargetException e) {			
			throw new RecordingException(e); 
		} catch (NoSuchMethodException e) {			
			throw new RecordingException("method caller violated precondition: get methods for both fields must exist", e);
		} 
			
	}

}
