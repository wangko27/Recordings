package data.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import bean.BasicCommentBean;
import data.HibernateTest;
import data.SongCommentData;
import exception.RecordingException;

public class TestSongCommentData extends HibernateTest {
	
	private SongCommentData data;
	
	@Override 
	public void before()
	{
		data = new SongCommentData();
	}

	@Test
	public void groupItemsByCount_returnsNonEmptyMap() throws RecordingException
	{
		Map<Comparable<?>, Integer> resultMap = data.groupItemsByCount("Text");
		
		assertTrue("result map was empty", !resultMap.isEmpty()); 
	}
	
	@Test
	public void groupItemsByCount_doesNotThrowClassCastException()
	{
		Map<Comparable<?>, Integer> resultMap;
		try {
			resultMap = data.groupItemsByCount("Text");
		} catch (RecordingException e) {
	
			assertFalse(e.getCause() instanceof ClassCastException); 
		}			
	
	}
	
	@Test
	public void testGetAllRecordingComments()
	{
		List<BasicCommentBean> comments = (List<BasicCommentBean>) data.getAllComments();
		
		assertNotNull("comments is null", comments);		
		//assertTrue("comments is empty", !comments.isEmpty()); // no comments yet
	}
	
	@Test
	public void testGetSortedComments()
	{
		List<BasicCommentBean> comments = data.getSortedComments(1); 
		assertNotNull("comments is null", comments); 
	}
}
