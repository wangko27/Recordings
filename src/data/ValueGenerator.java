package data;

import java.lang.reflect.Field;

import bean.City;
import bean.Country;
import bean.SimpleTestBean;

public class ValueGenerator {
	
	public static String[] venue = { "The Hammersmith", "Cat's Cradle", "Kings", "Local 506", "The Riverside", "The Milky Way", "The Ritz", "Boston Center For The Arts", "The Cave", "The Pit", "Beer Bar" };
	public static String[] link = { "http://google.com", "http://yahoo.com", "http://espn.com", "http://cnn.com", "http://nytimes.com", "http://bbcnews.com", "http://sas.com" };
	public static String[] sublocation = { "near the college", "Cary", "", "", "", "", "", "", "", "", "Brooklyn" };
	public static String[] media = { "CD", "DVD", "TV" };
	public static String[] formatType = { "WAV", "MP3", "TAPE" };
	public static String[] quality = { "Good", "Poor", "Excellent", "Very Good" };
	public static String[] recordingType = { "Show", "Studio Bootleg", "Compilation", "TV", "Radio", "Interview", "Single" };
	
	public static SimpleTestBean getSimpleTestBean( String valueType, int id )
	{
		SimpleTestBean testBean = new SimpleTestBean();
		
		try 
		{
			String[] valueArray = null;
			Field[] methods = ValueGenerator.class.getFields();
			for (Field field : methods) 
			{
				if( field.getName().equals(valueType) )
				{
					valueArray = (String[]) field.get(null);
				}
			}
			
			int valueId = id%valueArray.length;
			testBean.setId(valueId);
			testBean.setValue(valueArray[valueId]);
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
		return testBean;
	}
	
	public static int getYear( int id )
	{
		return 1970 + id%42;
	}
	
	public static int getMonth( int id )
	{
		return 1 + id%12;
	}
	
	public static int getDate( int id )
	{
		return 1 + id%28;
	}
	
	public static City getCity(int id)
	{
		String[] city = {"Paris", "New York", "Birmingham", "Chapel Hill", "London", "Asheville", "Long Beach"};
		
		int valueId = id%city.length;
		
		City c = new City(valueId);
		c.setValue(city[valueId]);
		
		return c; 
	}
	
	public static Country getCountry(int id)
	{
		String[] country = {"France", "USA", "USA", "USA", "England", "USA", "USA"};
		
		int valueId = id%country.length; 
		
		Country c = new Country(valueId);
		c.setValue(country[valueId]);
		
		return c;
	}
	
}
