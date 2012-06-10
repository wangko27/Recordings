package bean.graphs;

public class GraphRequestBean 
{
	private String xAxisField; 
	private String yAxisField;
	private String graphType;
	private String graphSpecialization; 
	private String graphStyle;
	private String graphDataSource; 
	
	private String graphTargetColumn; 
	private String graphTargetColumnValue; 
	
	
	
	public String getGraphSpecialization() {
		return graphSpecialization;
	}
	public void setGraphSpecialization(String graphSpecialization) {
		this.graphSpecialization = graphSpecialization;
	}
	public String getGraphTargetColumn() {
		return graphTargetColumn;
	}
	public void setGraphTargetColumn(String graphTargetColumn) {
		this.graphTargetColumn = graphTargetColumn;
	}
	public String getGraphTargetColumnValue() {
		return graphTargetColumnValue;
	}
	public void setGraphTargetColumnValue(String graphTargetColumnValue) {
		this.graphTargetColumnValue = graphTargetColumnValue;
	}
	public String getGraphDataSource() {
		return graphDataSource;
	}
	public void setGraphDataSource(String graphDataSource) {
		this.graphDataSource = graphDataSource;
	}
	public String getGraphStyle() {
		return graphStyle;
	}
	public void setGraphStyle(String graphStyle) {
		this.graphStyle = graphStyle;
	}
	public String getGraphType() {
		return graphType;
	}
	public void setGraphType(String graphType) {
		this.graphType = graphType;
	}
	public String getxAxisField() {
		return xAxisField;
	}
	public void setxAxisField(String xAxisField) {
		this.xAxisField = xAxisField;
	}
	public String getyAxisField() {
		return yAxisField;
	}
	public void setyAxisField(String yAxisField) {
		this.yAxisField = yAxisField;
	} 
	
	
}
