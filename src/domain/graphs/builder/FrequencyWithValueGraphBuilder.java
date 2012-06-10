package domain.graphs.builder;

import java.util.Map;

import bean.graphs.GraphBean;
import bean.graphs.GraphRequestBean;
import data.common.IConstructableFromStrings;
import domain.common.ICustomGraphRequestValidator;
import domain.common.IGraphDataAccessor;
import exception.RecordingException;

public class FrequencyWithValueGraphBuilder extends FrequencyGraphBuilder
{

	public FrequencyWithValueGraphBuilder(IGraphDataAccessor dataAccessor) 
	{
		super(dataAccessor);
	}


	public GraphBean<?, ?> getBasicGraph(GraphRequestBean request) throws RecordingException
	{
		GraphBean<?, ?> generatedGraph = null; 
		
		Comparable<?> commonValue = getColumnObject(request.getGraphTargetColumn(), request.getGraphTargetColumnValue()); 
		Map<Comparable<?>, Integer> resultMap = getDataAccess().groupItemsByCommonValue(request.getGraphTargetColumn(), commonValue, request.getxAxisField());
		generatedGraph = new FrequencyGraphBuilder(getDataAccess()).getFrequencyGraph(resultMap);
		generatedGraph.setGraphTitle("Number of " + getDataAccess().dataClassString() + "s per " + request.getxAxisField() + " with " + request.getGraphTargetColumn() + " = \"" + request.getGraphTargetColumnValue() + "\"" ); 
		
		
		// determine whether x,y axes need to be rounded
		Class<?> xaxisType = getExpectedType(request.getxAxisField()); 
		Class<?> yaxisType = getExpectedType(request.getGraphTargetColumn()); 	
		
		if(isInteger(xaxisType))
			generatedGraph.setRoundXAxis(true);
		if(isInteger(yaxisType))
			generatedGraph.setRoundYAxis(true);
		
		return generatedGraph; 
	}
	
	/**
	 * given a column and a proposed column value, attempts to create an object that matches the column type
	 * in the database and has the specified value
	 * 
	 * @param columnName the column to create the object for
	 * @param columnValue the value of the column, which the created object will contain
	 * @return a new object, matching the type of the column, and with the specified column value
	 * @throws RecordingException if the string cannot be used to create a new object of the column's type
	 */
	private Comparable<?> getColumnObject(String columnName, String columnValue) throws RecordingException
	{
		try  
		{
			Class<?> returnType = null; 
			
			if(getDataAccess() instanceof ICustomGraphRequestValidator)
				returnType =  ( (ICustomGraphRequestValidator) getDataAccess()).getExpectedType(columnName); 
			else				
				returnType =  getDataAccess().getDataClass().getMethod("get" + columnName).getReturnType();
			
			if(IConstructableFromStrings.class.isAssignableFrom(returnType))
			{
				return ((IConstructableFromStrings)returnType.newInstance()).createObjectFromString(columnValue); 
			}
			else if(String.class.isAssignableFrom(returnType)) 
			{
				return new String(columnValue); 
			}			
			else if(Integer.class.isAssignableFrom(returnType) || int.class.isAssignableFrom(returnType)) 
			{
				return Integer.parseInt(columnValue); 
			}
			else if (Double.class.isAssignableFrom(returnType))
			{
				return Double.parseDouble(columnValue); 
			}
			else
				return null; 
		}
		catch(Exception exc)
		{
			throw new RecordingException(exc); 
		}  
	}
}
