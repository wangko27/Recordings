package domain.graphs.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Country;
import bean.Recording;

public class GraphTestUtils 
{

	public static List<Recording> getRecordingListWithNullSimpleBeanValues()
	{
		List<Recording> badList = new ArrayList<Recording>();
		
		Recording badRecording = new Recording();
		badRecording.setYear(1999); 
		badRecording.setCountry(new Country(null)); 
		badList.add(badRecording);
		
		Recording otherBadRecording = new Recording(); 
		otherBadRecording.setYear(1999); 
		otherBadRecording.setCountry(new Country("Raleigh")); 
		badList.add(otherBadRecording); 
		
		return badList; 
	}
	
	public static Map<Comparable<?>, Integer> getRecordingMapWithNullSimpleBeanValues()
	{
		HashMap<Comparable<?>, Integer> badList = new HashMap<Comparable<?>, Integer>();
		
		Country country = new Country(null); 
		badList.put(country, 1);
		badList.put(new Country("Raleigh"), 2); 
		
		return badList; 
	}
}
