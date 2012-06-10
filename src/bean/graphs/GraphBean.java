package bean.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * a simple object representing data points and formatting paremeters for a chart
 * @author cefolger
 *
 */
public class GraphBean <X, Y>
{
	public final class GraphDataPoint<T, K>
	{
		private T x; 
		private K y; 
		
		private boolean oneDimensional; 
		
		public boolean isOneDimensional() 
		{
			return oneDimensional; 
		}
		
		public GraphDataPoint(T x, K y)
		{
			this.x = x; 
			this.y = y; 
		}
		
		public GraphDataPoint(T x)
		{
			this.x = x; 
			oneDimensional = true; 
		}
		
		public Object[] getData()
		{
			if(!oneDimensional) 
				return new Object[] { x, y};
			else
				return new Object[] { x }; 				
		}
			
		@Override
		public String toString()
		{
			if(oneDimensional)
				return x.toString(); 
			else
				return x.toString() + "," + y.toString(); 
		}
	}
	
	public GraphBean()
	{
		dimensions = new double[2]; 
		xTicks = new ArrayList<Comparable>(); 
		yTicks = new ArrayList<Comparable>(); 
	}
	
	private boolean oneDimensional; 
	private String xAxisLabel; 
	private String yAxisLabel; 
	private String graphTitle; 
	private double[] dimensions; 
	private  ArrayList<Comparable> xTicks; 
	private List<Comparable> yTicks; 
	private String style; 
	private String type; 

	private boolean roundXAxis; 
	private boolean roundYAxis; 
	
	

	public boolean getRoundXAxis() {
		return roundXAxis;
	}

	public void setRoundXAxis(boolean roundXAxis) {
		this.roundXAxis = roundXAxis;
	}

	public boolean getRoundYAxis() {
		return roundYAxis;
	}

	public void setRoundYAxis(boolean roundYAxis) {
		this.roundYAxis = roundYAxis;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getGraphTitle() {
		return graphTitle;
	}

	public void setGraphTitle(String graphTitle) {
		this.graphTitle = graphTitle;
	}

	public List<Comparable> getxTicks()
	{
		return xTicks; 
	}
	
	public List<Comparable> getyTicks()
	{
		return yTicks; 
	}
	
	public String getxAxisLabel() {
		return xAxisLabel;
	}

	public void setxAxisLabel(String xAxisLabel) {
		this.xAxisLabel = xAxisLabel;
	}

	public String getyAxisLabel() {
		return yAxisLabel;
	}

	public void setyAxisLabel(String yAxisLabel) {
		this.yAxisLabel = yAxisLabel;
	}

	public double[] getDimensions() 
	{
		return dimensions;
	}
	
	public boolean getOneDimensional()
	{
		return oneDimensional; 
	}
	
	public void setDimensions(double[] dimensions)
	{
		this.dimensions[0] = dimensions[0]; 
		this.dimensions[1] = dimensions[1]; 
	}

	private List<GraphDataPoint<X,Y>> data = new ArrayList<GraphDataPoint<X,Y>>(); 	
	
	public int getPointCount()
	{
		return data.size();  
	}
	
	public void addDataPoint(X x, Y y) 
	{
		data.add(new GraphDataPoint<X,Y>(x,y)); 
	} 
	
	public void addDataPoint(X x)
	{
		data.add(new GraphDataPoint<X,Y>(x)); 
		oneDimensional = true; 
	}
	
	public List<GraphDataPoint<X,Y>> getData()
	{
		return data; 
	}
	
	public Object[][] getDataArrays()
	{
		Object[][] results = new Object[data.size()][]; 
		
		for(int i=0;i<data.size();i++) 
		{
			results[i] = data.get(i).getData(); 
		}
		
		return results; 
	}
			
}
