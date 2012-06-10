package bean;

public class TableElement 
{
	private String label;
	private String value;
	private String href;

	public TableElement() {
	}
	public TableElement( String label, String value ) {
		this.label = label;
		this.value = value;
	}
	public TableElement( String label, int value ) {
		this.label = label;
		
		Integer v = new Integer(value);
		this.value = v.toString();
	}

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	
	public String toString()
	{
		return this.label + " " + this.value;
	};
}
