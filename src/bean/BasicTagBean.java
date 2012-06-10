package bean;

public abstract class BasicTagBean implements Comparable<BasicTagBean>
{
	protected int id;
	protected String text;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public int compareTo(BasicTagBean bean)
	{
		if( bean != null )
		{
			if( getId() > bean.getId() )
				return 1; 
			else if( getId() == bean.getId() )
				return 0;
		}
		
		return -1; 
	}
	
	public boolean equals(Object obj)
	{
		if(!(obj instanceof BasicTagBean))
			throw new ClassCastException(); 
		
		BasicTagBean bean = (BasicTagBean) obj; 
		
		if( bean != null )
		{
			if( this.getText() != null && bean.getText() != null && this.getText().equals(bean.getText()) )
				return true;
		}

		return false; 
	}
	
	public int hashCode()
	{
		return getText().hashCode() ^ getId();
	}
}
