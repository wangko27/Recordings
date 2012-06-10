package bean;

public class Format extends SimpleBean implements Comparable<SimpleBean> 
{
	public Format() {
		super();
	}
	
	public Format( SimpleTestBean testBean )
	{
		super(testBean);
	}
	
	public Format(String value)
	{
		super(value);
	}
}
