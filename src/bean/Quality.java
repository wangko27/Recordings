package bean;

import data.common.IConstructableFromStrings;

public class Quality extends SimpleBean implements Comparable<Quality>, IConstructableFromStrings
{
	public Quality() {
		super();
	}
	public Quality( SimpleTestBean testBean )
	{
		super(testBean);
	}
	
	public Quality(String source) {
		this.value = source; 
	}
	public int getQualityInteger()
	{
		if(this.value.equals("Excellent"))
			return 7;
		else if (this.value.equals("Very Good"))
			return 6; 
		else if (this.value.equals("Good/Very Good"))
			return 5; 
		else if (this.value.equals("Good"))
			return 4;
		else if (this.value.equals("Fair"))
			return 3; 
		else if (this.value.equals("Poor/Good"))
			return 2; 
		else if (this.value.equals("Poor"))
			return 1; 		
		else
			return 0; 
	}
	@Override
	public int compareTo(Quality arg0) {
		return getQualityInteger() - arg0.getQualityInteger(); 
	}
	@Override
	public Comparable<?> createObjectFromString(String source) 
	{
		return new Quality(source); 
	}
}
