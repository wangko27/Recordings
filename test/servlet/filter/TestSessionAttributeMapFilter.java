package servlet.filter;

import static org.junit.Assert.*;

import org.junit.Test;

import servlet.filters.SessionAttributeMapFilter;

public class TestSessionAttributeMapFilter {
	
	@Test
	public void testIsSAM()
	{
		assertFalse(SessionAttributeMapFilter.isSAM(""));
		assertFalse(SessionAttributeMapFilter.isSAM(null));
		assertFalse(SessionAttributeMapFilter.isSAM("notsam"));
		assertFalse(SessionAttributeMapFilter.isSAM("not_sam"));
		assertFalse(SessionAttributeMapFilter.isSAM("not_sam_"));
		assertFalse(SessionAttributeMapFilter.isSAM("s_not_sam_"));
		assertFalse(SessionAttributeMapFilter.isSAM("s_sam_"));
		assertFalse(SessionAttributeMapFilter.isSAM("s_sam"));
		assertFalse(SessionAttributeMapFilter.isSAM("r_sam_blah"));
		assertFalse(SessionAttributeMapFilter.isSAM("1_sam_blah"));
		
		assertTrue(SessionAttributeMapFilter.isSAM("s_sam_blah"));
		assertTrue(SessionAttributeMapFilter.isSAM("s_s_s"));
	}
	
	@Test
	public void testGetSecondLevelNamespace()
	{
		assertEquals(null, SessionAttributeMapFilter.getSecondLevelNamespace(null));
		assertEquals(null, SessionAttributeMapFilter.getSecondLevelNamespace(""));
		assertEquals(null, SessionAttributeMapFilter.getSecondLevelNamespace("broken"));
		assertEquals(null, SessionAttributeMapFilter.getSecondLevelNamespace("s_samparam"));
		assertEquals("param", SessionAttributeMapFilter.getSecondLevelNamespace("s_sam_param"));
	}

}
