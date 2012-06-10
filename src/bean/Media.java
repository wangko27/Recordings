package bean;

public class Media extends SimpleBean implements Comparable<SimpleBean> 
{
	public Media() {
		super();
	}
	public Media( String value ) {
		super(value);
	}
	public Media( SimpleTestBean testBean ) {
		super(testBean);
	}
}
