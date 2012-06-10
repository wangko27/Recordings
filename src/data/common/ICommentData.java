package data.common;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import bean.BasicCommentBean;
import exception.RecordingException;

/**
 *  provides behavior to handle retrieving
 *  comments from the database. 
 *
 */
public interface ICommentData 
{
	/**
	 * gets all of the comments in the database
	 * @return collection of BasicCommentBeans representing all comments
	 */
	public Collection<BasicCommentBean> getAllComments();
	
	/**
	 * generates a map, with the key being each possible value of the provided column, and the value being
	 * the number of times the key appears in the database for each comment
	 * 
	 * @param fieldName the column to generate the map for
	 * @return 
	 * @throws RecordingException
	 */
	public Map<Comparable<?>, Integer> groupItemsByCount(String fieldName) throws RecordingException;
	
	public Map<Comparable<?>, Integer> groupItemsByCommonValue(String commonValueColumn, Comparable<?> commonValue, String variableColumn) throws RecordingException;
	
	public List<BasicCommentBean> getSortedComments(int objectId);
	
	public Long saveComment(BasicCommentBean bean); 
	
	public BasicCommentBean getCommentById(int id);
	
	public void saveOrUpdateComment(BasicCommentBean bean); 
}
