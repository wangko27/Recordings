package domain;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import bean.BasicCommentBean;
import bean.CommentValidationBean;
import bean.CommentValidationBean.CommentValidationResult;

public class TestCommentValidator 
{
	
	private CommentValidator validator; 
	private BasicCommentBean validCommentBean; 
	
	@Before 
	public void setup()
	{
		validator = new CommentValidator(); 
		validCommentBean = mock(BasicCommentBean.class);
		
		when(validCommentBean.getId()).thenReturn(1); 
		when(validCommentBean.getText()).thenReturn("foo"); 
		when(validCommentBean.getUsername()).thenReturn("foo"); 
	}
	
	@Test
	public void testValidCommentRequest()
	{
		CommentValidationBean result = validator.validate("", validCommentBean); 
		
		assertTrue("result was " + result.getResult(),  result.getResult() == CommentValidationResult.SUCCESS); 
	}
	
	@Test 
	public void testBotCommentRequest()
	{
		CommentValidationBean result = validator.validate("4", validCommentBean); 
		
		assertTrue("result was " + result.getResult(), result.getResult() == CommentValidationResult.USER_ENTERED_VALIDATION_FAILED); 
	}
	
	@Test 
	public void commentBeanWithAllValidFields_returnsSuccess()
	{
		BasicCommentBean bean = mock(BasicCommentBean.class);
		when(bean.getText()).thenReturn("hello there");  
		when(bean.getUsername()).thenReturn("bob"); 
		
		CommentValidationBean result = new CommentValidator().validate(null, bean); 
		
		assertTrue("result was " + result.getResult(), result.getResult() == CommentValidationResult.SUCCESS); 
	}
	
	@Test
	public void commentBeanWithBlankFields_returnsFailure()
	{
		BasicCommentBean bean = mock(BasicCommentBean.class);
		bean.setText(""); 
		when(bean.getUsername()).thenReturn("bob"); 
		
		CommentValidationBean result = new CommentValidator().validate(null, bean); 
		
		assertTrue("result was " + result.getResult(), result.getResult() == CommentValidationResult.BLANK_FIELD);
		
		when(bean.getText()).thenReturn("bob"); 
		when(bean.getUsername()).thenReturn(""); 
		
		result = new CommentValidator().validate(null, bean); 
		
		assertTrue("result was " + result.getResult(), result.getResult() == CommentValidationResult.BLANK_FIELD);		
	}
	
	
	@Test
	public void commentBeanWithLongFields_returnsFailure()
	{
		BasicCommentBean bean = mock(BasicCommentBean.class);
		
		StringBuilder longString = new StringBuilder(); 
		for(int i=0;i<3000;i++) longString.append("longstring"); 
		
		when(bean.getText()).thenReturn(longString.toString()); 
		when(bean.getUsername()).thenReturn("bob"); 
		
		CommentValidationBean result = new CommentValidator().validate(null, bean); 		

		assertTrue("result was " + result.getResult(), result.getResult() == CommentValidationResult.FIELD_TOO_LONG);
		
		when(bean.getText()).thenReturn("foo"); 
		when(bean.getUsername()).thenReturn(longString.toString()); 
		
		result = new CommentValidator().validate(null, bean); 		

		assertTrue("result was " + result.getResult(), result.getResult() == CommentValidationResult.FIELD_TOO_LONG);		
	}
	 
	@Test
	public void commentBeanWithInvalidUserValidation_returnsFailure()
	{
		BasicCommentBean bean = mock(BasicCommentBean.class);
		when(bean.getText()).thenReturn("foo"); 
		when(bean.getUsername()).thenReturn("bob"); 
		
		CommentValidationBean result = new CommentValidator().validate("invalid", bean); 
		
		assertTrue("result was " + result.getResult(), result.getResult() == CommentValidationResult.USER_ENTERED_VALIDATION_FAILED);
	}
	
	@Test
	public void commentBeanWithValidUserValidation_returnsSuccess()
	{
		BasicCommentBean bean = mock(BasicCommentBean.class);
		when(bean.getText()).thenReturn("foo"); 
		when(bean.getUsername()).thenReturn("bob"); 
		
		CommentValidationBean result = new CommentValidator().validate( "", bean); 
		
		assertTrue("result was " + result.getResult(), result.getResult() == CommentValidationResult.SUCCESS);
		
	}
	
	@Test
	public void commentBeanWithNullFields_returnsFailure()
	{
		BasicCommentBean bean = mock(BasicCommentBean.class);		
		
		CommentValidationBean result = new CommentValidator().validate(  "", bean); 
		
		assertTrue("result was " + result.getResult(), result.getResult() == CommentValidationResult.BLANK_FIELD);		
	}
}
