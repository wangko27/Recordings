package domain.common;


public interface ICustomGraphRequestValidator
{

	public boolean validateField(String fieldName); 
	public Class<? extends Comparable<?>> getExpectedType(String fieldName); 
	public Object getDataSourceToInvokeGetMethodsOn(Object instance, String fieldName); 
}
