package bean;

import data.common.IConstructableFromStrings;

public class Venue extends SimpleBean implements Comparable<SimpleBean>, IConstructableFromStrings
{
	public Venue() {
		super();
	}
	
	public Venue( int id ) {
		super(id);
	}
	
	public Venue(String value)
	{
		this.value = value; 
	}
	
	public Venue( SimpleTestBean testBean )
	{
		this.id = testBean.getId();
		this.value = testBean.getValue();
	}

	@Override
	public Comparable<?> createObjectFromString(String source) {
		return new Venue(source); 
	}
}
