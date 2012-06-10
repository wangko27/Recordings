package bean;

public class QuickSearchBean {
	
	private int id;
	private String category;
	private String value;
	private String formattedValue;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setFormattedValue(String formattedValue) {
		this.formattedValue = formattedValue;
	}
	public String getFormattedValue() {
		return formattedValue;
	}
}
