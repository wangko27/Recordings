package domain.graphs.builder;

import java.util.List;

import bean.graphs.GraphBean;
import bean.graphs.GraphRequestBean;
import domain.common.ICustomGraphRequestValidator;
import domain.common.IGraphDataAccessor;
import exception.RecordingException;

/**
 * 
 * represents a graph builder with one access point (getGraph) which can be called
 * with a GraphRequestBean to generate a graph. 
 *
 */
public abstract class BasicGraphBuilder
{
	private IGraphDataAccessor dataSource; 
	
	public BasicGraphBuilder(IGraphDataAccessor dataAccessor)
	{
		dataSource = dataAccessor; 
	}
	
	/**
	 * determines if an Object is an instance of a numerical type
	 * @param obj object to test
	 * @return true if the object is numeric, false otherwise
	 */
	protected boolean isNumeric(Object obj)
	{
		return obj instanceof Number;
	}
	
	/**
	 * adds an object to a GraphBean's list of tick marks, while checking to see
	 * if the object is already there and if it's numeric or not  
	 * 
	 * @param list the tick list of the GraphBean 
	 * @param point the data value to add to the list
	 * @return a numerical value representing the index in the tick list or just the data value if it was numeric
	 */
	protected final Number addToTicksList(List<Comparable> list, Object point)
	{
		if(isNumeric(point))
		{
			return (Number) point; 
		}
		else
		{
			int existingIndex = list.indexOf(point); 
			
			if(existingIndex > -1)
				return existingIndex;  
			
			list.add((Comparable<?>) point); 
			return list.size() - 1; 
		}	
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
	
	protected IGraphDataAccessor getDataAccess()
	{
		return dataSource; 
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
	
	/**
	 * gets a graph, given the graph request, with no other parameters like title etc. set
	 * 
	 * @param request the graph request bean 
	 * @return a graph bean representing the generated data
	 * @throws RecordingException
	 */
	protected abstract GraphBean<?, ?> getBasicGraph(GraphRequestBean request) throws RecordingException;
	
	/**
	 * a facade-style method that should be the only method accessed externally by other classes. 
	 * 
	 * @param request A GraphRequestBean that represents the parameters of the graph request
	 * @return a graph bean representing the generated data
	 * @throws RecordingException
	 */
	public GraphBean<?, ?> getGraph(GraphRequestBean request) throws RecordingException
	{
		GraphBean<?, ?> basicGraph = getBasicGraph(request);
		
		return basicGraph; 
	}
	
	public String[] getAxesLabels(GraphRequestBean request)
	{
		return new String[] { request.getxAxisField(), request.getyAxisField() };  
	}
}
