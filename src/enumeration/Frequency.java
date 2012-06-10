package enumeration;

public enum Frequency {
	
	RARE("Rare", 1), 
	MEDIUM("Medium", 2),
	COMMON("Common", 3);
	
	private String display;
	private int value;
	
	private Frequency( String display, int value )
	{
		this.display = display;
		this.value = value;
	}
	
	public static Frequency getFrequencyFromValue( int value )
	{
		Frequency frequency = null;
		for( Frequency f : Frequency.values() )
		{
			if( f.getValue() == value )
			{
				frequency = f;
				break;
			}
		}
		
		return frequency;
	}

	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}	
