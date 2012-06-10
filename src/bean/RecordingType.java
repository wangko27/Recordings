package bean;

public class RecordingType extends SimpleBean implements Comparable<SimpleBean> 
{
	
	public RecordingType() {
		super();
	}
	public RecordingType( String value ) 
	{
		super(value);
	}
	public RecordingType( SimpleTestBean testBean )
	{
		super(testBean);
	}
}
