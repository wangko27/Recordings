package domain.graphs.builder;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import domain.SongInstanceManager;
import domain.common.IGraphDataAccessor;
import domain.common.ISpecializedGraphBuilderFactory;
import domain.graphs.SpecializedGraphParameters;
import domain.graphs.builder.SpecializedGraphBuilderFactory.SpecializedGraphType;

public class TestSpecializedGraphBuilderFactory 
{
	private ISpecializedGraphBuilderFactory factory; 
		
	@Before
	public void setup()
	{
		factory = new SpecializedGraphBuilderFactory(); 
	}
	
	@Test
	public void createSpecializedGraphBuilder_givenParametersForFirstAppearanceGraph_returnsInstance()
	{
		IGraphDataAccessor accessor = new SongInstanceManager(null);  
		 
		SpecializedGraphParameters parameters = new SpecializedGraphParameters(SpecializedGraphType.FIRSTAPPEARANCE, accessor);  
		BasicGraphBuilder builder = factory.createSpecializedGraphBuilder(parameters);
		
		assertNotNull(accessor); 
		assertTrue("returned graph builder is of type " + builder.getClass(), builder instanceof SongFirstAppearanceGraphBuilder); 
	}
	
	@Test
	public void specializedGraphBuilder_convertsFirstAppearanceStringToEnum()
	{
		SpecializedGraphType result = SpecializedGraphType.fromString("firstappearance"); 
		assertTrue("result was " + result, result == SpecializedGraphType.FIRSTAPPEARANCE); 
	}
}
