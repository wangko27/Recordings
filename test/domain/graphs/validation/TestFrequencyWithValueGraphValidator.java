package domain.graphs.validation;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import bean.graphs.GraphRequestBean;
import bean.graphs.GraphValidationBean.GraphValidationResult;
import domain.common.ICustomGraphRequestValidator;
import domain.graphs.validation.FrequencyWithValueGraphValidator;
import exception.RecordingException;

public class TestFrequencyWithValueGraphValidator 
{

	private class StubDataType
	{
		private String stubString;
		
		@SuppressWarnings("unused")
		public String getStubString() { return stubString; } 
	}
	 
	private class StubBean implements ICustomGraphRequestValidator
	{
		private StubDataType value = null; 
		private double doubleValue; 
		private int intValue; 
		private float floatValue; 
		
		@SuppressWarnings("unused")
		public int getIntValue() {
			return intValue;
		}

		@SuppressWarnings("unused")
		public float getFloatValue() {
			return floatValue;
		}

		@SuppressWarnings("unused")
		public StubDataType getValue() { return value; }
		
		@SuppressWarnings("unused")
		public double getDoubleValue() { return doubleValue; }

		@Override
		public boolean validateField(String fieldName) {
			return true;
		}

		@Override
		public Class<? extends Comparable<?>> getExpectedType(String fieldName) {
			return String.class;
		}

		@Override
		public Object getDataSourceToInvokeGetMethodsOn(Object instance,
				String fieldName) {
			return null;
		} 
	}
	
	@Test
	public void validate_testNullBean() throws RecordingException
	{		
		assertTrue(new FrequencyWithValueGraphValidator(String.class).validate(null).getResult() == GraphValidationResult.INVALID_TARGET);  
	}
	
	@Test
	public void validate_givenPrimitiveNumericalTypes_columnIsConsideredValid() throws RecordingException
	{
		GraphRequestBean graphRequest = new GraphRequestBean(); 
		graphRequest.setGraphTargetColumn("DoubleValue"); 
		graphRequest.setGraphTargetColumnValue("3.0"); 
		
		FrequencyWithValueGraphValidator validator = new FrequencyWithValueGraphValidator(StubBean.class);
		
		assertTrue("result was " + validator.validate(graphRequest).getResult(), validator.validate(graphRequest).getResult() == GraphValidationResult.SUCCESS);
		graphRequest.setGraphTargetColumn("FloatValue"); 
		graphRequest.setGraphTargetColumnValue("3.0"); 
		assertTrue("result was " + validator.validate(graphRequest).getResult(), validator.validate(graphRequest).getResult() == GraphValidationResult.SUCCESS);
		graphRequest.setGraphTargetColumn("IntValue"); 
		graphRequest.setGraphTargetColumnValue("3");
		assertTrue("result was " + validator.validate(graphRequest).getResult(), validator.validate(graphRequest).getResult() == GraphValidationResult.SUCCESS);
		
	}
	
	@Test
	public void validate_whenGivenAColumnThatIsInvalidForFWV_returnsInvalidTarget() throws RecordingException
	{
		GraphRequestBean graphRequest = new GraphRequestBean(); 
		graphRequest.setGraphTargetColumn("Value"); 
		graphRequest.setGraphTargetColumnValue("hello"); 
				
		FrequencyWithValueGraphValidator validator = new FrequencyWithValueGraphValidator(StubBean.class);
		
		GraphValidationResult result = validator.validate(graphRequest).getResult();
		assertTrue("result was " + result, result == GraphValidationResult.INVALID_TARGET); 
		
	}
	
	@Test
	public void validate_whenGivenAColumnFromAnotherBean_columnValidatesAsOK() throws RecordingException
	{
		GraphRequestBean graphRequest = new GraphRequestBean(); 
		graphRequest.setGraphTargetColumn("StubString"); 
		graphRequest.setGraphTargetColumnValue("hello"); 
				
		FrequencyWithValueGraphValidator validator = new FrequencyWithValueGraphValidator(StubBean.class);
		
		GraphValidationResult result = validator.validate(graphRequest, new StubBean()).getResult();
		assertTrue("result was " + result, result == GraphValidationResult.SUCCESS); 		
	}
}
