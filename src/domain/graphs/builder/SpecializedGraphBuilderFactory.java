package domain.graphs.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import domain.SongInstanceManager;
import domain.common.ISpecializedGraphBuilderFactory;
import domain.graphs.SpecializedGraphParameters;

public class SpecializedGraphBuilderFactory implements ISpecializedGraphBuilderFactory
{
	public enum SpecializedGraphType
	{
		FIRSTAPPEARANCE;
		
		public static SpecializedGraphType fromString(String type)
		{
			if(type.toLowerCase().equals("firstappearance"))
				return FIRSTAPPEARANCE;
		
			return null; 
		}
	}; 
	
	@Override
	public BasicGraphBuilder createSpecializedGraphBuilder(
			SpecializedGraphParameters parameters) 
	{
		if(parameters.getGraphType() == SpecializedGraphType.FIRSTAPPEARANCE)
		{
			if(!SongFirstAppearanceGraphBuilder.graphParametersAreValid(parameters))
				throw new IllegalArgumentException("graph parameters are not valid");
			else
				return new SongFirstAppearanceGraphBuilder((SongInstanceManager)parameters.getDataSource()); 			
		}
			
		return null;  
	}

	@Override
	public Collection<SpecializedGraphType> supportedGraphTypes() 
	{
		List<SpecializedGraphType> supportedTypes = new ArrayList<SpecializedGraphType>(); 
		supportedTypes.add(SpecializedGraphType.FIRSTAPPEARANCE);
		
		return supportedTypes; 
		
	}	

}
