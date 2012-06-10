package domain.graphs.validation;

import bean.Recording;
import bean.graphs.GraphRequestBean;
import bean.graphs.GraphValidationBean;
import bean.graphs.GraphValidationBean.GraphValidationResult;
import data.common.IConstructableFromStrings;
import domain.common.ICustomGraphRequestValidator;
import exception.RecordingException;

public class FrequencyWithValueGraphValidator
{
	private Class<?> targetType = Recording.class; 
	
	public FrequencyWithValueGraphValidator(Class<?> targetType)
	{
		this.targetType = targetType; 
	}
	
	public GraphValidationBean validate(GraphRequestBean target ) throws RecordingException
	{
		return validate(target, null); 
	}
	
	public GraphValidationBean validate(GraphRequestBean target, ICustomGraphRequestValidator validator) throws RecordingException
	{
		if(target == null) 
			return new GraphValidationBean("Invalid bean passed to validateFrequencyWithValue()", GraphValidationResult.INVALID_TARGET); 
		
		// validate column 
		if(!validateField(target.getGraphTargetColumn(),validator ))
			return new GraphValidationBean("The column, " + target.getGraphTargetColumn() + ", does not exist", GraphValidationResult.INVALID_TARGET); 
		
		if(target.getGraphTargetColumnValue() == null)
			return new GraphValidationBean("The column value was not specified", GraphValidationResult.INVALID_COLUMN_VALUE); 
		
		if(target.getGraphTargetColumnValue().length() > 30)
			return new GraphValidationBean("The column value was too longer", GraphValidationResult.COLUMN_VALUE_TOO_LONG); 
		
		if(!this.isValidColumn(target.getGraphTargetColumn(),validator))
			return new GraphValidationBean("The column, " + target.getGraphTargetColumn() + ", is not a valid column for this graph type", GraphValidationResult.INVALID_TARGET); 
		
		if(!this.isValidColumnValue(target.getGraphTargetColumn(), target.getGraphTargetColumnValue(), validator))
			return new GraphValidationBean("The value, " + target.getGraphTargetColumnValue() + ", for " + target.getGraphTargetColumn() + " is invalid", GraphValidationResult.INVALID_COLUMN_VALUE); 
		
		return new GraphValidationBean("Success", GraphValidationResult.SUCCESS); 
	}
	
	/**
	 * determines if a bean has a get method corresponding to the provided column 
	 * @param field the column (such as 'Month' etc.). Note that the field must be capitalized. 
	 * @return true if the method getField() exists, false otherwise 
	 * @throws RecordingException
	 */
	private boolean validateField(String field, ICustomGraphRequestValidator validator) throws RecordingException
	{
		if(validator != null) 
			return validator.validateField(field); 
		
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
	
	private boolean isValidColumnValue(String column, String columnValue, ICustomGraphRequestValidator validator) throws RecordingException
	{
		try 
		{
			Class<?> beanMethodReturnType = null; 
			
			if(validator != null) 
				beanMethodReturnType = validator.getExpectedType(column); 
			else				
				beanMethodReturnType = targetType.getMethod("get" + column).getReturnType();
			
			// if the value is not a valid number, the exception will be caught
			if(Integer.class.isAssignableFrom(beanMethodReturnType) || int.class.isAssignableFrom(beanMethodReturnType))
			{
				Integer.parseInt(columnValue); 
			}						
			else if (Double.class.isAssignableFrom(beanMethodReturnType) || double.class.isAssignableFrom(beanMethodReturnType))
			{
				Double.parseDouble(columnValue); 
			}
			
		}
		catch(Exception exc) // number format exception 
		{
			return false; 
		}
		
		return true; 
	}
	
	private  boolean isValidColumn(String column, ICustomGraphRequestValidator validator)
	{
		try
		{
			Class<?> beanMethodReturnType = null; 
			
			if(validator != null) 
				beanMethodReturnType = validator.getExpectedType(column); 
			else
				beanMethodReturnType = targetType.getMethod("get" + column).getReturnType(); 			
			
			// numerical primitives are allowed
			if(Number.class.isAssignableFrom(beanMethodReturnType))
				return true; 
			
			if(double.class.isAssignableFrom(beanMethodReturnType) || float.class.isAssignableFrom(beanMethodReturnType) || int.class.isAssignableFrom(beanMethodReturnType))
				return true; 
			
			// as are strings
			if(String.class.isAssignableFrom(beanMethodReturnType))
				return true; 
			
			if(IConstructableFromStrings.class.isAssignableFrom(beanMethodReturnType))
				return true; 
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace(); 
			return false; 
		}  

		
		return false; 
	}
	
}
