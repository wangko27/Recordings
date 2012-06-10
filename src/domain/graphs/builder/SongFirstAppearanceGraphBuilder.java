package domain.graphs.builder;

import bean.graphs.GraphBean;
import bean.graphs.GraphRequestBean;
import domain.SongInstanceManager;
import domain.graphs.SpecializedGraphParameters;
import exception.RecordingException;

public class SongFirstAppearanceGraphBuilder extends FrequencyGraphBuilder 
{		
	private SongInstanceManager songInstanceManager; 
	
	public SongFirstAppearanceGraphBuilder(SongInstanceManager manager) {
		super(manager);
		
		songInstanceManager = manager; 
	}

	public static boolean graphParametersAreValid(SpecializedGraphParameters parameters)
	{
		return parameters.getDataSource() instanceof SongInstanceManager; 			
	}

	@Override
	public GraphBean<?, ?> getBasicGraph(GraphRequestBean request)
			throws RecordingException {

		
		GraphBean<?, ?> result =super.getFrequencyGraph(songInstanceManager.getNumberOfNewSongsPerYear());
		result.setGraphTitle("Number of New Songs Per Year"); 
		result.setxAxisLabel("Year"); 
		result.setyAxisLabel("New Song Count"); 
		return result;   		
	}

}
