package domain;

import java.util.Collection;

import util.StringUtil;
import bean.City;
import bean.Country;
import bean.Format;
import bean.Media;
import bean.Quality;
import bean.RecordingType;
import bean.SimpleBean;
import bean.Song;
import bean.Venue;
import data.common.ISimpleEntityData;

public class SimpleEntityManager {
	
	private ISimpleEntityData simpleEntityData;
	
	public SimpleEntityManager( ISimpleEntityData simpleEntityData ) {
		this.simpleEntityData = simpleEntityData;
	}
	
	public void saveOrUpdateSimpleBean( SimpleBean simpleBean ) 
	{
		if( simpleBean != null )
			simpleEntityData.saveOrUpdateSimpleBean(simpleBean);
	}
	
	public void delete( SimpleBean simpleBean ) 
	{
		if( simpleBean != null )
			simpleEntityData.delete(simpleBean);
	}
	
	public SimpleBean getSimpleBean( Integer id, Class<?> type )
	{
		SimpleBean simpleBean = null;
		
		if( id != null )
			simpleBean = simpleEntityData.getSimpleBean(id, type);
		
		return simpleBean;
	}
	
	public SimpleBean getSimpleBean( String value, Class<?> type )
	{
		SimpleBean simpleBean = null;
		
		if( StringUtil.hasValue(value) )
			simpleBean = simpleEntityData.getSimpleBean(value, type);
		
		return simpleBean;
	}
	
	public Collection<Venue> getAllVenues()
	{
		return simpleEntityData.getAllVenues();
	}
	
	public Collection<RecordingType> getAllRecordingTypes()
	{
		return simpleEntityData.getAllRecordingTypes();
	}
	
	public Collection<Quality> getAllQualities()
	{
		return simpleEntityData.getAllQualities();
	}
	
	public Collection<Format> getAllFormats()
	{
		return simpleEntityData.getAllFormats();
	}
	
	public Collection<Media> getAllMedias()
	{
		return simpleEntityData.getAllMedias();
	}
	
	public Collection<City> getAllCities()
	{
		return simpleEntityData.getAllCities();
	}
	
	public Collection<Country> getAllCountries()
	{
		return simpleEntityData.getAllCountries();
	}
	
	public Collection<Song> getAllSongs()
	{
		return simpleEntityData.getAllSongs();
	}
}
