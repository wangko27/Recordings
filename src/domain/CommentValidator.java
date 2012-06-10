package domain;

import bean.BasicCommentBean;
import bean.CommentValidationBean;
import bean.CommentValidationBean.CommentValidationResult;

/**
 *  handles validation of CommentBeans. 
 *
 */
public class CommentValidator 
{
	public static final int MAX_LONG_FIELD_LENGTH = 3000; 
	public static final int MAX_DEFAULT_FIELD_LENGTH = 300; 
	
	public CommentValidator()
	{
		
	}
	
	/**
	 * validates (or not) a comment bean and returns the result 
	 * 
	 * @param userValidationResult the value that the user entered for the security question. if this is null, then user validation is not checked. 
	 * @param bean the bean to validate
	 * @return a validation bean representing either success or describing what went wrong
	 */
	public CommentValidationBean validate(String userValidationResult, BasicCommentBean bean)
	{	
		if(!userValidationWasSuccess(userValidationResult))
			return new CommentValidationBean("An error occurred...Are there special characters in your comment?", CommentValidationResult.USER_ENTERED_VALIDATION_FAILED); 
		
		// validate each field separately 
		CommentValidationBean result = validateField(bean.getUsername(), "Please enter a username", "The username was " ); 
		if(result.getResult() != CommentValidationResult.SUCCESS) return result;  
		
		result = validateField(bean.getText(), "Please enter some comment text", "The comment text was ", MAX_LONG_FIELD_LENGTH); 
		if(result.getResult() != CommentValidationResult.SUCCESS) return result;
		
		return new CommentValidationBean("OK", CommentValidationResult.SUCCESS); 
	}
	
	/**
	 * determines whether or not the user entered the correct answer to the security question
	 * @param userValidationResult the user's result
	 * @return true if the user entered the correct result 
	 */
	private boolean userValidationWasSuccess(String userValidationResult)
	{
		if(userValidationResult == null) return true; 		
		
		// here we're seeing if a bot has populated the result field. 
		return userValidationResult.equals(""); 
	}
	
	/**
	 * computes the result of the security question. Hard coded for the moment 
	 * @return
	 */
	public int computeValidationResult()
	{
		return 4; 
	}
	
	private CommentValidationBean validateField( String fieldValue, String blankMessage, String lengthMessage)
	{
		return validateField( fieldValue, blankMessage, lengthMessage, MAX_DEFAULT_FIELD_LENGTH); 
	}
	
	/**
	 * validates  a comment field value that the user has entered
	 * @param fieldName the name of the field 
	 * @param fieldValue the value that the user entered
	 * @return a comment validation bean representing the result 
	 */
	private CommentValidationBean validateField( String fieldValue, String blankMessage, String lengthMessage, int maxLength)
	{
		if(fieldValue == null) 
			return new CommentValidationBean(blankMessage, CommentValidationResult.BLANK_FIELD); 
		
		if(fieldValue.length() == 0)
			return new CommentValidationBean(blankMessage, CommentValidationResult.BLANK_FIELD); 
		
		if(fieldValue.length() > maxLength)
			return new CommentValidationBean(lengthMessage + (fieldValue.length() - maxLength) + " characters too long", CommentValidationResult.FIELD_TOO_LONG); 
		
		return new CommentValidationBean("OK", CommentValidationResult.SUCCESS); 
	}
	
}
