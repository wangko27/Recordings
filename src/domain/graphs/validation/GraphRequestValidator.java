package domain.graphs.validation;

import bean.Recording;
import bean.graphs.GraphRequestBean;
import bean.graphs.GraphValidationBean;
import bean.graphs.GraphValidationBean.GraphValidationResult;
import domain.common.ICustomGraphRequestValidator;
import domain.graphs.validation.FrequencyWithValueGraphValidator;
import exception.RecordingException;

public final class GraphRequestValidator 
{
	private Class<?> targetType = Recording.class; 

	public GraphRequestValidator(Class<?> targetType)
	{
		this.targetType = targetType; 
	}
	
	public GraphRequestValidator()
	{
		
	}
	
	
	
	public GraphValidationBean validate(GraphRequestBean target) throws RecordingException
	{
		return validate(target, null); 
	}
	
	/**
	 * performs validation on a GraphRequestBean
	 * @param target the GraphRequestBean to validate
	 * @return a GraphValidationBean representing the result. A successful result means GraphValidationBean.getResult() == GraphValidationResult.SUCCESS
	 * @throws RecordingException
	 */
	public GraphValidationBean validate(GraphRequestBean target, ICustomGraphRequestValidator validator) throws RecordingException
	{
		if(target == null)
			return new GraphValidationBean("An invalid bean was passed to validate()", GraphValidationResult.INVALID_TARGET);				
	
		if(target.getGraphType() != null && target.getGraphType().toLowerCase().equals("specialized"))
		{
			return new SpecializedGraphValidator().validate(target); 
		}
		
		if(target.getxAxisField() == null ||  (target.getyAxisField() == null && needsYAxis(target)) )
			return new GraphValidationBean("An axis field was null", GraphValidationResult.NULL_FIELD); 
		
		if(needsYAxis(target) && target.getxAxisField().equals(target.getyAxisField()))
			return new GraphValidationBean("You cannot have both axes with the same column", GraphValidationResult.AXES_ARE_IDENTICAL); 
		
		if(!validateField(target.getxAxisField(), validator))
			return new GraphValidationBean("The x axis field, " + target.getxAxisField() + ", does not exist", GraphValidationResult.NON_EXISTENT_FIELD); 

		// check y-axis validity only if the graph needs the y-axis 
		if(needsYAxis(target) &&  !validateField(target.getyAxisField(), validator))
			return new GraphValidationBean("The y axis field, " + target.getyAxisField() + ", does not exist", GraphValidationResult.NON_EXISTENT_FIELD); 
		
		if(target.getGraphType() != null &&  target.getGraphType().equals("frequencywithvalue"))
		{
			GraphValidationBean result = null; 
			if(validator == null)  
				result = new FrequencyWithValueGraphValidator(targetType).validate(target);
			else
				result = new FrequencyWithValueGraphValidator(targetType).validate(target, validator);
			
			if(result.getResult() != GraphValidationResult.SUCCESS)
				return result; 
		}
		
		return new GraphValidationBean("Success", GraphValidationResult.SUCCESS); 
	}
	
	
	/**
	 * determines if a graph requires a Y axis (and a valid y-axis field) or not
	 * @param request the GraphRequestBean corresponding to the graph in question
	 * @return true if the y axis field must be valid
	 */
	private boolean needsYAxis(GraphRequestBean request)
	{
		if(request.getGraphType() == null) 
			return true; 
		
		if(request.getGraphStyle() != null && request.getGraphType().equals("frequency") &&  request.getGraphStyle().equals("pie"))
			return false; 
		
		return !request.getGraphType().equals("frequency") && !request.getGraphType().equals("frequencywithvalue"); 			
	}
	
	/**
	 * determines if a RecordingBean has a get method corresponding to the provided column 
	 * @param field the column (such as 'Month' etc.). Note that the field must be capitalized. 
	 * @return true if the method getField() exists, false otherwise 
	 * @throws RecordingException
	 */
	private boolean validateField(String field, ICustomGraphRequestValidator validator) throws RecordingException
	{
		if(validator != null)
			return validator.validateField(field) ; 
		
		String methodName = "get" + field; 
		try 
		{
			targetType.getMethod(methodName);
		}
		catch(Exception exc)
		{
			return false; 
		} 
		
		return true; 				
	}			
}
