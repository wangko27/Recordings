package bean;

import data.common.IConstructableFromStrings;

public class Country extends SimpleBean implements Comparable<SimpleBean>, IConstructableFromStrings
{
	public Country() {
		super();
	}
	
	public Country( int id ) {
		super(id);
	}
	
	public Country( String value ) {
		super(value);
	}

	@Override
	public Comparable<?> createObjectFromString(String source)
	{
		return new Country(source); 
	}

}
