package bean;

public class SimpleBean  
{
	protected int id;
	protected String value;

	public SimpleBean() {
	}
	
	public SimpleBean( int id ) {
		this.id = id;
	}
	
	public SimpleBean( SimpleTestBean testBean )
	{
		this.id = testBean.getId();
		this.value = testBean.getValue();
	}
	
	public SimpleBean( String value )
	{
		this.value = value;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString()
	{
		return this.value; 
	}
	
	public int compareTo(SimpleBean o)
	{
		String thisValue = this.getValue(); 
		String otherValue = o.getValue(); 
		
		if(thisValue == null && otherValue == null) return 0; 
		if(thisValue != null && otherValue == null) return 1; 
		if(thisValue == null && otherValue != null) return -1; 
		
		return thisValue.compareToIgnoreCase(otherValue); 
	}
	
	public boolean equals(Object obj)
	{
		if( !(obj instanceof SimpleBean))
			throw new ClassCastException();
		
		String thisValue = this.getValue(); 
		String otherValue = ( (SimpleBean) obj).getValue(); 
		
		if(thisValue == null && otherValue == null) return true; 
		if(thisValue == null || otherValue == null) return false; 
		
		return thisValue.equals(otherValue); 
	}
	
	public int hashCode()
	{
		return getValue().hashCode(); 		
	}


}
