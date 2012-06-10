package servlet.db;

import org.junit.Test;

import servlet.SongCommentController;
import bean.SongComment;
import data.SongCommentData;
import domain.CommentsManager;

public class TestSongCommentController 
{

	@Test
	public void makeSureInitializeWorks()
	{
		SongCommentController controller = new SongCommentController(new CommentsManager(new SongCommentData(), SongComment.class));
		controller.init(null); 
	}
}
