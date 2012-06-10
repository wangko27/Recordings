package bean;

import data.common.IConstructableFromStrings;

public class City extends SimpleBean implements Comparable<SimpleBean>, IConstructableFromStrings 
{
	public City() {
		super();
	}
	
	public City( int id ) {
		super(id);
	}
	
	public City( String value ) {
		super(value);
	}

	@Override 
	public Comparable<?> createObjectFromString(String source) 
	{
		return new City(source); 
	}
}
