package bean;

import com.google.gson.Gson;

public class CommentValidationBean 
{
	public enum CommentValidationResult
	{
		SUCCESS, 
		BLANK_FIELD,
		FIELD_TOO_LONG, 
		USER_ENTERED_VALIDATION_FAILED, 
		TOO_MANY_COMMENTS, 
		TOO_MANY_VOTES
	};  
	
	private CommentValidationResult result; 
	private String message;
	
	public CommentValidationBean(String message, CommentValidationResult result) {
		super();
		this.result = result;
		this.message = message;
	}

	public CommentValidationResult getResult() {
		return result;
	}

	public String getMessage() {
		return message;
	}
	
	public String toJSONString()
	{
		return new Gson().toJson(this); 
	}
}
