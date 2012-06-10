package bean;

import java.io.Serializable;

public class NotUsed implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String category;
	private String value;

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
}
