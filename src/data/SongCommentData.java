package data;

import java.math.BigInteger;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.BasicCommentBean;
import data.common.ICommentData;
import exception.RecordingException;

public class SongCommentData extends BasicHibernateDataSource implements ICommentData
{

	@Override
	public Collection<BasicCommentBean> getAllComments() 
	{
		return super.queryDatabaseForList(BasicCommentBean.class, "from SongComment"); 
	}

	@Override
	public Map<Comparable<?>, Integer> groupItemsByCount(String fieldName) throws RecordingException 
	{
		try
		{
			List<Object[]> queryResults = super.queryDatabaseForListUsingSQL(Object[].class, "select " + fieldName + ", cast(count(*) as unsigned integer) from songcomment group by " + fieldName + " order by rand() limit 10"); 
			
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
	public List<BasicCommentBean> getSortedComments(int objectId)
	{
		return super.queryDatabaseForList(BasicCommentBean.class, "from SongComment as comment where comment.song.id = " + objectId + " order by comment.bob desc, comment.voteRank desc, comment.timestamp desc");
	}

	@Override
	public Map<Comparable<?>, Integer> groupItemsByCommonValue(
			String commonValueColumn, Comparable<?> commonValue,
			String variableColumn)
			throws RecordingException {
		return null;
	}

	@Override
	public BasicCommentBean getCommentById(int id) 
	{	
		BasicCommentBean comment = super.queryDatabaseForItem(BasicCommentBean.class, "from SongComment where id = " + id); 
		
		return comment;
	}
	
	@Override
	public Long saveComment(BasicCommentBean bean)
	{
		return super.saveItem(bean); 
	}
	
	@Override 
	public void saveOrUpdateComment(BasicCommentBean bean)
	{
		super.saveOrUpdateItem(BasicCommentBean.class, bean);  
	}

	
}
