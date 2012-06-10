package servlet;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestSAMFacade {
	
	@Test
	public void testGetParamString()
	{
		assertEquals("", SAMFacade.getParamString("", "name", "val"));
		assertEquals("", SAMFacade.getParamString("pre", "", "val"));
		assertEquals("", SAMFacade.getParamString("pre", "", ""));
		
		assertEquals("", SAMFacade.getParamString(null, "name", "val"));
		assertEquals("", SAMFacade.getParamString("pre", null, "val"));
		assertEquals("", SAMFacade.getParamString("pre", "name", null));
		
		assertEquals("", SAMFacade.getParamString("", "", ""));
		assertEquals("", SAMFacade.getParamString(null, null, null));
		
		assertEquals("s_pre_name=val", SAMFacade.getParamString("pre", "name", "val"));
		assertEquals("s_s_s=val", SAMFacade.getParamString("s", "s", "val"));
	}
}
