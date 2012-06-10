package data.db;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import data.HibernateTest;
import data.TagData;
import data.common.ITagData;

public class TestTagData extends HibernateTest {
	private ITagData data = new TagData();

	@Test
	public void testGetAllSongTags()
	{
		assertNotNull(data.getAllSongTags());
	}
	
	@Test
	public void testGetAllRecordingTags()
	{
		assertNotNull(data.getAllRecordingTags());
	}
}
