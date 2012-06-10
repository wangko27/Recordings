package data.stub;

import static org.junit.Assert.*;

import org.junit.Test;

import data.StubSimpleEntityData;
import data.ValueGenerator;

public class TestStubSimpleEntityData {

	private StubSimpleEntityData data = new StubSimpleEntityData();
	
	@Test
	public void getAllVenues()
	{
		assertNotNull(data.getAllVenues());
		
		assertEquals(data.getAllVenues().size(), ValueGenerator.venue.length);
	}
	
	@Test
	public void getAllRecordingTypes()
	{
		assertNotNull(data.getAllRecordingTypes());
		
		assertEquals(data.getAllRecordingTypes().size(), ValueGenerator.recordingType.length);
	}
}
