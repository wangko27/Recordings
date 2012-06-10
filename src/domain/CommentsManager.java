package domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.BasicCommentBean;

import com.google.gson.Gson;

import data.common.ICommentData;
import domain.common.IGraphDataAccessor;
import exception.RecordingException;

/**
 *  manages comment data and provides methods to retrieve and update it 
 *
 */
public class CommentsManager implements IGraphDataAccessor
{
	private ICommentData dataSource;
	private Class<?> dataClass; 
	
	public CommentsManager(ICommentData dataSource, Class<?> dataClass)
	{
		this.dataSource = dataSource; 
		this.dataClass = dataClass; 
	}

	@Override
	public Map<Comparable<?>, Integer> groupItemsByCount(String fieldName)
			throws RecordingException {

		return dataSource.groupItemsByCount(fieldName); 
	}

	@Override
	public Collection<?> getAllItems() {
		return dataSource.getAllComments();
	}

	@Override
	public Class<?> getDataClass() {
		return dataClass; 
	}
	
	public BasicCommentBean getCommentById(int id)
	{
		return dataSource.getCommentById(id); 
	}

	@Override
	public Map<Comparable<?>, Integer> groupItemsByCommonValue(
			String commonValueColumn, Comparable<?> commonValue,
			String variableColumn) throws RecordingException {
		
		
		return dataSource.groupItemsByCommonValue(commonValueColumn, commonValue, variableColumn); 		
	}
	
	/**
	 * gets a list of sorted comments, sorted first by whether or not they're posted
	 * by Bob and second by posting date
	 * @param objectId the id of the object (Recording, Song, etc.) to retrieve comments for 
	 * @return a sorted list of BasicCommentBeans 
	 */
	public List<BasicCommentBean> getSortedCommentsForSpecificBean(int objectId)
	{
		return dataSource.getSortedComments(objectId); 
	}

	@Override
	public String dataClassString() {
		return "Comments";
	}
	
	/**
	 * persists a comment bean to the data layer 
	 * @param commentBean the bean to persist
	 * @return the id of the newly inserted comment bean 
	 */
	public Long saveComment(BasicCommentBean commentBean)
	{
		return dataSource.saveComment(commentBean); 
	}
	
	public void saveOrUpdateComment(BasicCommentBean commentBean)
	{
		dataSource.saveOrUpdateComment(commentBean); 
	}
	
	
	/**
	 * converts a comment bean to a JSON string 
	 * @param commentBean the comment bean to convert
	 * @return a JSON string representing the bean 
	 */
	public String commentToJSONString(BasicCommentBean commentBean)
	{
		Map<String, Object> comment = new HashMap<String, Object>(); 
		comment.put("voteRank", commentBean.getVoteRank()); 
		comment.put("id", commentBean.getId()); 
		comment.put("bob", commentBean.getBob()); 
		comment.put("timestamp", commentBean.getTimestamp()); 
		comment.put("text", commentBean.getText()); 
		comment.put("username", commentBean.getUsername()); 
		
		return new Gson().toJson(comment); 
		// return new Gson().toJson(commentBean); 
	}
	
	public String voteCountToJSONString(int votes)
	{
		Map<String, Integer> results = new HashMap<String, Integer>(); 
		results.put("voteRank", votes);
		return new Gson().toJson(results); 
	}
}
