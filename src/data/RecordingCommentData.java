package data;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.BasicCommentBean;
import bean.RecordingComment;
import data.common.ICommentData;
import exception.RecordingException;

public class RecordingCommentData extends BasicCommentData implements ICommentData
{

	@Override
	public Collection<BasicCommentBean> getAllComments() 
	{
		return super.queryDatabaseForList(BasicCommentBean.class, "from RecordingComment"); 
	}
	
	public List<BasicCommentBean> getSortedComments(int objectId)
	{
		return super.queryDatabaseForList(BasicCommentBean.class, "from RecordingComment as comment where comment.recording.id = " + objectId + " order by comment.bob desc, comment.voteRank desc, comment.timestamp desc"); 
	}

	@Override
	public Map<Comparable<?>, Integer> groupItemsByCount(String fieldName) throws RecordingException 
	{
		try
		{
			List<Object[]> queryResults = super.queryDatabaseForListUsingSQL(Object[].class, "select " + fieldName + ", cast(count(*) as unsigned integer) from recordingcomment group by " + fieldName );
			
			LinkedHashMap<Comparable<?>, Integer> resultMap = new LinkedHashMap<Comparable<?>, Integer>();
			
			for(Object[] obj : queryResults)
			{
				if(obj[0] instanceof String) 
				{
					String value = (String)obj[0]; 
					value = value.substring(0, Math.min(value.length(), 30)); 
					obj[0] = value; 
				}
				
				resultMap.put((Comparable<?>)obj[0], ((BigInteger)obj[1]).intValue()); 
			}
						
			return resultMap;  
		}
		catch(Exception e)
		{
			throw new RecordingException(e); 
		}
	}

	@Override
	public Map<Comparable<?>, Integer> groupItemsByCommonValue(
			String commonValueColumn, Comparable<?> commonValue,
			String variableColumn)
			throws RecordingException {
		
		// LinkedHashMap guarantees elements appear in the order they were inserted
		Map<Comparable<?>, Integer> results = new LinkedHashMap<Comparable<?>, Integer>(); 

		try
		{
			Method method = RecordingComment.class.getMethod("get" + variableColumn); 
			Method commonValueMethod = RecordingComment.class.getMethod("get" + commonValueColumn); 
			
			for(BasicCommentBean bean : getAllComments())
			{
				Comparable<?> result = (Comparable<?>) method.invoke(bean);
				Comparable<?> beanCommonValue = (Comparable<?>) commonValueMethod.invoke(bean); 
				
				if(beanCommonValue == null || !beanCommonValue.equals(commonValue)) continue; 
				if(result == null) continue; 
				
				if(results.containsKey(result))
					results.put(result, results.get(result) + 1); 
				else
					results.put(result, 1); 
			}
		}
		catch(Exception exc)
		{
			exc.printStackTrace(); 
			return null; 
		}
		
		return results; 
	}

	@Override
	public BasicCommentBean getCommentById(int id) 
	{
		BasicCommentBean comment = super.queryDatabaseForItem(BasicCommentBean.class, "from RecordingComment where id = " + id); 
		
		return comment;
	}
	
}
