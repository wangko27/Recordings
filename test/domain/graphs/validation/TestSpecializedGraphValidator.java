package domain.graphs.validation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import bean.graphs.GraphRequestBean;
import bean.graphs.GraphValidationBean;
import bean.graphs.GraphValidationBean.GraphValidationResult;

public class TestSpecializedGraphValidator 
{

	private SpecializedGraphValidator validator; 
	private GraphRequestBean stubRequest; 
	
	@Before 
	public void setup()
	{
		validator = new SpecializedGraphValidator();
		stubRequest = mock(GraphRequestBean.class);
		when(stubRequest.getGraphType()).thenReturn("specialized"); 
	}

	@Test
	public void validate_nonSpecializedGraph_returnsError()
	{
		when(stubRequest.getGraphType()).thenReturn("foo"); 
		GraphValidationBean result = validator.validate(stubRequest);		
		assertTrue("result was " + result.getResult(), result.getResult() == GraphValidationResult.INCORRECT_GRAPH_TYPE); 
	}
	
	@Test
	public void validate_validFirstAppearanceGraph_isSuccessful()
	{		
		when(stubRequest.getGraphSpecialization()).thenReturn("firstappearance"); 
		when(stubRequest.getGraphType()).thenReturn("specialized"); 
		GraphValidationBean result = validator.validate(stubRequest); 
		assertNotNull(result); 
		assertTrue("result was " + result.getResult(), result.getResult() == GraphValidationResult.SUCCESS); 
	}
	
	@Test
	public void validate_nullFields_fails()
	{
		when(stubRequest.getGraphSpecialization()).thenReturn(null); 
		GraphValidationBean result = validator.validate(stubRequest);
		
		assertTrue("result was " + result.getResult(), result.getResult() == GraphValidationResult.NULL_FIELD); 
	}
	
}
