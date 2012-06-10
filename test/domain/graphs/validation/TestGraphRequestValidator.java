package domain.graphs.validation;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import bean.graphs.GraphRequestBean;
import bean.graphs.GraphValidationBean;
import bean.graphs.GraphValidationBean.GraphValidationResult;
import domain.graphs.validation.GraphRequestValidator;
import exception.RecordingException;

public class TestGraphRequestValidator
{
	private GraphRequestValidator testValidator; 
	
	@Before
	public void setup()
	{
		testValidator = new GraphRequestValidator(); 
	}
	
	@Test
	public void validate_returnsInvalidTarget_whenPassedInBeanIsNull() throws RecordingException
	{
		GraphRequestValidator validator = new GraphRequestValidator(); 
		GraphValidationBean result = validator.validate(null); 
		
		assertTrue("result validation bean was null", result != null); 
		assertTrue(result.getResult() == GraphValidationResult.INVALID_TARGET); 
	}
	
	@Test
	public void validate_givenTwoIdenticalAxes_resultIsError() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setxAxisField("test"); 
		bean.setyAxisField("test"); 
		
		GraphRequestValidator validator = new GraphRequestValidator(); 
		GraphValidationBean result = validator.validate(bean); 
				
		assertTrue(result.getResult() == GraphValidationResult.AXES_ARE_IDENTICAL); 
	}
	
	@Test
	public void validate_givenAxisWithValidFields_returnsSuccess() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setxAxisField("Month"); 
		bean.setyAxisField("Year");
		
		GraphRequestValidator validator = new GraphRequestValidator(); 
		GraphValidationBean result = validator.validate(bean); 
		
		assertTrue("result returned was " + result.getResult(), result.getResult() == GraphValidationResult.SUCCESS); 
	}
	
	@Test
	public void validate_GivenAxisWithInvalidFields_returnsError() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setxAxisField("testing y"); 
		bean.setyAxisField("testing x");
		
		GraphRequestValidator validator = new GraphRequestValidator(); 
		GraphValidationBean result = validator.validate(bean); 
		
		assertTrue("result returned was " + result.getResult(), result.getResult() == GraphValidationResult.NON_EXISTENT_FIELD); 
	}
	
	@Test
	public void validate_WhenGraphTypeIsFrequency_YAxisCanBeNull() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setxAxisField("Year"); 
		bean.setyAxisField(null);
		bean.setGraphType("frequency"); 
		
		GraphRequestValidator validator = new GraphRequestValidator(); 
		GraphValidationBean result = validator.validate(bean); 
		
		assertTrue("result returned was " + result.getResult(), result.getResult() == GraphValidationResult.SUCCESS);	
	}
	
	@Test
	public void validate_WhenGraphTypeIsFrequency_BothAxesCanBeIdentical() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setxAxisField("Month"); 
		bean.setyAxisField("Month");
		bean.setGraphType("frequency"); 
		
		GraphRequestValidator validator = new GraphRequestValidator(); 
		GraphValidationBean result = validator.validate(bean); 
		
		assertTrue("result returned was " + result.getResult(), result.getResult() == GraphValidationResult.SUCCESS);
	}
	
	@Test
	public void validate_WhenGraphTypeIsGeneric_NoAxisIsNull() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setxAxisField(null); 
		bean.setyAxisField(null);
		bean.setGraphType("generic"); 
		
		GraphRequestValidator validator = new GraphRequestValidator(); 
		GraphValidationBean result = validator.validate(bean); 
		
		assertTrue("result returned was " + result.getResult(), result.getResult() == GraphValidationResult.NULL_FIELD);
	}
	
	@Test
	public void validate_WhenGraphTypeIsGenericAndGraphStyleIsPie_IdenticalAxesCauseErrors() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setxAxisField("Month"); 
		bean.setyAxisField("Month");
		bean.setGraphType("generic");
		bean.setGraphStyle("pie"); 
		
		GraphRequestValidator validator = new GraphRequestValidator(); 
		GraphValidationBean result = validator.validate(bean); 
		
		assertTrue("result returned was " + result.getResult(), result.getResult() == GraphValidationResult.AXES_ARE_IDENTICAL);	
	}
	
	@Test
	public void validate_WhenGraphTypeIsFrequencyWithValue_BothAxesCanBeIdentical() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setxAxisField("Month"); 
		bean.setyAxisField("Month");
		bean.setGraphType("frequencywithvalue");
		bean.setGraphTargetColumn("Month");
		bean.setGraphTargetColumnValue("1"); 
		
		GraphRequestValidator validator = new GraphRequestValidator(); 
		GraphValidationBean result;
		
		result = validator.validate(bean);
		
		assertTrue("result was " + result.getResult(), result.getResult() == GraphValidationResult.SUCCESS); 
	}
	
	@Test
	public void validate_whenGraphTypeIsFrequencyWithValue_ColumnMustBeValid() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setxAxisField("Month"); 
		bean.setyAxisField("Month");
		bean.setGraphType("frequencywithvalue");
		bean.setGraphTargetColumn("foo"); 
		
		GraphValidationResult result = new GraphRequestValidator().validate(bean).getResult();
		
		assertTrue("result was " + result, result == GraphValidationResult.INVALID_TARGET); 
	}
	
	@Test
	public void validate_whenGraphTypeIsFrequencyWithValue_ColumnValueMustBeNonNull() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setxAxisField("Month"); 
		bean.setyAxisField("Month");
		bean.setGraphType("frequencywithvalue");
		bean.setGraphTargetColumn("Month");
		
		GraphValidationResult result = new GraphRequestValidator().validate(bean).getResult();
		
		assertTrue("result was " + result, result == GraphValidationResult.INVALID_COLUMN_VALUE);
	}
	
	@Test
	public void validate_whenGraphTypeIsFrequencyWithValue_ColumnValueMustHaveMaxLength() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setxAxisField("Month"); 
		bean.setyAxisField("Month");
		bean.setGraphType("frequencywithvalue");
		bean.setGraphTargetColumn("Month");
		
		StringBuilder longString = new StringBuilder(); 
		for(int i=0;i<3000;i++) longString.append("*"); 
		
		bean.setGraphTargetColumnValue( longString.toString()); 
		
		GraphValidationResult result = new GraphRequestValidator().validate(bean).getResult();
		
		assertTrue("result was " + result, result == GraphValidationResult.COLUMN_VALUE_TOO_LONG);
	}
	
	@Test
	public void validate_whenGraphTypeIsFrequencyWithValue_ColumnMustImplementInterfaceOrBePrimitive() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setxAxisField("Month"); 
		bean.setyAxisField("Month");
		bean.setGraphType("frequencywithvalue");
		bean.setGraphTargetColumnValue("1"); 
		
		bean.setGraphTargetColumn("Month");		
		assertTrue("int result was " + testValidator.validate(bean).getResult(), GraphValidationResult.SUCCESS == testValidator.validate(bean).getResult());
		
		bean.setGraphTargetColumn("Sublocation");		
		assertTrue("String result was " + testValidator.validate(bean).getResult(), GraphValidationResult.SUCCESS == testValidator.validate(bean).getResult());
		
		bean.setGraphTargetColumn("City");		
		assertTrue("Implementor result was " + testValidator.validate(bean).getResult(), GraphValidationResult.SUCCESS == testValidator.validate(bean).getResult());		
	}
	
	@Test
	public void validate_whenGraphTypeIsFrequencyWithValue_ColumnMustHaveValidColumnValue() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setxAxisField("Month"); 
		bean.setyAxisField("Month");
		bean.setGraphType("frequencywithvalue");
		bean.setGraphTargetColumn("Month");
		bean.setGraphTargetColumnValue("abc");
		
		assertTrue("validation on invalid int was " + testValidator.validate(bean).getResult(),  testValidator.validate(bean).getResult() == GraphValidationResult.INVALID_COLUMN_VALUE); 
	}
	
	@Test
	public void validate_whenGraphNeedsYAxisAndHasInvalidField_returnsNonExistentField() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setxAxisField("Month"); 
		bean.setyAxisField("Shrimp");
		bean.setGraphType("generic");
		
		assertTrue("validation result was " + testValidator.validate(bean).getResult(), testValidator.validate(bean).getResult() == GraphValidationResult.NON_EXISTENT_FIELD); 
	}
	
	@Test
	public void validate_validFirstAppearanceGraph_isValidatedAsOK() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setGraphType("specialized"); 
		bean.setGraphSpecialization("firstappearance"); 
		
		GraphValidationBean result = testValidator.validate(bean);
		
		assertTrue("result was " + result.getResult(), result.getResult() == GraphValidationResult.SUCCESS); 
	}
	
	@Test
	public void validate_nonColumnField() throws RecordingException
	{
		GraphRequestBean bean = new GraphRequestBean(); 
		bean.setGraphType("frequency"); 
		bean.setxAxisField("Votes"); 
		
		GraphValidationBean result = testValidator.validate(bean); 
		
		assertTrue("result was " + result.getResult(), result.getResult() == GraphValidationResult.SUCCESS); 
	}
}
