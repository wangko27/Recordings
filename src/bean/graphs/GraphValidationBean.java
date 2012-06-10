package bean.graphs;

import com.google.gson.Gson;

public class GraphValidationBean 
{
	public enum GraphValidationResult
	{
		SUCCESS,
		INVALID_TARGET, 
		AXES_ARE_IDENTICAL,
		NON_EXISTENT_FIELD,
		NOT_IMPLEMENTED, 
		NULL_FIELD, 
		INVALID_COLUMN_VALUE, 
		COLUMN_VALUE_TOO_LONG,
		INCORRECT_GRAPH_TYPE
	}
	
	private String message; 
	private GraphValidationResult result; 
	
	public GraphValidationBean(String message, GraphValidationResult result)
	{
		this.message = message; 
		this.result = result; 
	}

	/**
	 * gets the error message associated with this GraphValidationResult. 
	 * @return the error message (if any) 
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * retrieves the result of validating a GraphRequestBean. Only GraphValidationResult.SUCCESS is acceptable. 
	 * @return the GraphValidationResult value indicating success or error
	 */
	public GraphValidationResult getResult() {
		return result;
	}
	
	public String toJSONString()
	{
		return new Gson().toJson(this); 
	}
}
