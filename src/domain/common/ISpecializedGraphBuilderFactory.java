package domain.common;

import java.util.Collection;

import domain.graphs.SpecializedGraphParameters;
import domain.graphs.builder.BasicGraphBuilder;
import domain.graphs.builder.SpecializedGraphBuilderFactory.SpecializedGraphType;

public interface ISpecializedGraphBuilderFactory
{
	BasicGraphBuilder createSpecializedGraphBuilder(SpecializedGraphParameters parameters);
	
	Collection<SpecializedGraphType> supportedGraphTypes(); 
}
