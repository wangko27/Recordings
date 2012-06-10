package data;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import bean.BasicCommentBean;
import data.common.ICommentData;
import exception.RecordingException;

public abstract class BasicCommentData extends BasicHibernateDataSource implements ICommentData 
{
	@Override
	public Collection<BasicCommentBean> getAllComments()
	{
		return null;
	}

	@Override
	public abstract Map<Comparable<?>, Integer> groupItemsByCount(String fieldName) throws RecordingException;

	@Override
	public abstract Map<Comparable<?>, Integer> groupItemsByCommonValue(
			String commonValueColumn, Comparable<?> commonValue,
			String variableColumn) throws RecordingException;
	

	@Override
	public abstract List<BasicCommentBean> getSortedComments(int objectId);

	@Override 
	public void saveOrUpdateComment(BasicCommentBean bean)
	{
		super.saveOrUpdateItem(BasicCommentBean.class, bean); 
	}

	@Override
	public Long saveComment(BasicCommentBean bean)
	{
		return super.saveItem(bean); 
	}
	
	
	@Override
	public BasicCommentBean getCommentById(int id) {
		return null;
	}

}
