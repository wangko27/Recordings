package data.db.RecordingData;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import bean.BasicCommentBean;
import bean.Recording;
import bean.RecordingComment;
import data.HibernateTest;
import data.RecordingCommentData;
import domain.CommentsManager;
import exception.RecordingException;

public class TestRecordingCommentData extends HibernateTest {
	
	private RecordingCommentData data;
	
	@Override 
	public void before()
	{
		data = new RecordingCommentData();
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
	public void getSortedComments_returnsNonEmptyCollection()
	{
		List<BasicCommentBean> comments = (List<BasicCommentBean>) data.getSortedComments(1); 
		
		assertNotNull("comments is null", comments);		
		assertTrue("comments is empty", !comments.isEmpty());
	}
	
	@Test
	public void testGetAllRecordingComments()
	{
		List<BasicCommentBean> comments = (List<BasicCommentBean>) data.getAllComments();
		
		assertNotNull("comments is null", comments);		
		assertTrue("comments is empty", !comments.isEmpty());
	}	
	
	@Test
	public void testSavingAComment()
	{
		Recording recording = new Recording();
		recording.setId(1); 
		
		RecordingComment comment = new RecordingComment(); 
		comment.setTimestamp(Calendar.getInstance().getTime()); 
		comment.setRecording(recording); 
		comment.setUsername("jim"); 
		comment.setText("here is some text from TestRecordingCommentData.testSavingAComment"); 
		
		Long result = (new CommentsManager(new RecordingCommentData(), RecordingComment.class)).saveComment(comment);
	
		assertTrue(result.longValue() > 0); 
	}
	
	@Test
	public void testGroupItemsByCommonValue() throws RecordingException
	{
		Map<Comparable<?>, Integer> results = new RecordingCommentData().groupItemsByCommonValue("Username", "Chris", "Text");
		assertNotNull(results); 	
	}
}
