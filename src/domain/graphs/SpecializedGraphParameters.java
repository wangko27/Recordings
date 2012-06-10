package domain.graphs;

import domain.common.IGraphDataAccessor;
import domain.graphs.builder.SpecializedGraphBuilderFactory.SpecializedGraphType;

public class SpecializedGraphParameters 
{

	private SpecializedGraphType graphType;
	private IGraphDataAccessor dataSource; 

	public SpecializedGraphParameters(String graphType, IGraphDataAccessor dataSource)
	{
		SpecializedGraphType result = SpecializedGraphType.fromString(graphType); 
		
		if(result == null) 
			throw new IllegalArgumentException(graphType + " is not a specialized graph type");
		else
			this.graphType = result; 
		 
		this.dataSource = dataSource; 
	}
	
	public SpecializedGraphParameters(SpecializedGraphType graphType, IGraphDataAccessor dataSource)
	{
		this.graphType = graphType; 
		this.dataSource = dataSource; 
	}
		
	public SpecializedGraphType getGraphType() {
		return graphType;
	}

	public IGraphDataAccessor getDataSource() {
		return dataSource;
	} 	
	
	
	
}
