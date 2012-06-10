package domain.graphs.validation;

import bean.graphs.GraphRequestBean;
import bean.graphs.GraphValidationBean;
import bean.graphs.GraphValidationBean.GraphValidationResult;

public class SpecializedGraphValidator 
{
	public GraphValidationBean validate(GraphRequestBean bean)
	{
		if(bean.getGraphType() == null) 
			return new GraphValidationBean("The graph type was null", GraphValidationResult.NULL_FIELD); 
		
		if(!bean.getGraphType().toLowerCase().equals("specialized"))
			return new GraphValidationBean("The graph is not specialized", GraphValidationResult.INCORRECT_GRAPH_TYPE); 
		
		if(bean.getGraphSpecialization() == null)
			return new GraphValidationBean("The graph specialization was null", GraphValidationResult.NULL_FIELD); 			
		
		if(!bean.getGraphType().toLowerCase().equals("specialized"))
			return new GraphValidationBean("The graph type you have chosen is not specialized", GraphValidationResult.INCORRECT_GRAPH_TYPE);						
		
		return new GraphValidationBean("success", GraphValidationResult.SUCCESS); 
	}		
}
