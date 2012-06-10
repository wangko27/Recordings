package data;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

public class SimpleEntityData extends BasicHibernateDataSource implements ISimpleEntityData
{
	@Override
	public void saveOrUpdateSimpleBean( SimpleBean simpleBean )
	{
		super.saveOrUpdateItem(SimpleBean.class, simpleBean); 
	}
	
	@Override
	public void delete( SimpleBean simpleBean )
	{
		super.delete(simpleBean); 
	}
	
	
	@Override
	public List<Song> getAllSongs()
	{
		List<Song> songs = super.queryDatabaseForList(Song.class, "from Song order by value");
		
		return songs;
	}

	@Override
	public List<Venue> getAllVenues() 
	{
		List<Venue> venues = super.queryDatabaseForList(Venue.class, "from Venue order by value"); 
		
		return venues;
	}
	
	@Override
	public List<Quality> getAllQualities() 
	{
		List<Quality> qualities = super.queryDatabaseForList(Quality.class, "from Quality order by value"); 
		
		return qualities;
	}

	@Override
	public List<RecordingType> getAllRecordingTypes() {
		List<RecordingType> recordingTypes = super.queryDatabaseForList(RecordingType.class, "from RecordingType order by value"); 
		
		return recordingTypes;
	}
	
	@Override
	public List<City> getAllCities() {
		List<City> cities = super.queryDatabaseForList(City.class, "from City order by value"); 
		
		return cities;
	}
	
	@Override
	public List<Country> getAllCountries() {
		List<Country> countries = null;
		countries = super.queryDatabaseForList(Country.class, "from Country order by value"); 
		
		return countries;
	}
	
	@Override
	public List<Format> getAllFormats() {
		List<Format> formats = super.queryDatabaseForList(Format.class, "from Format order by value"); 
		
		return formats;
	}
	
	@Override
	public List<Media> getAllMedias() {
		List<Media> medias = super.queryDatabaseForList(Media.class, "from Media order by value"); 
		
		return medias;
	}
	
	@Override
	// TODO txn
	public SimpleBean getSimpleBean(Integer id, String type) 
	{
		SimpleBean simpleBean = null;
		Session session = DataHelper.getSessionFactory().openSession();
		
		Query query = session.createQuery("from " + type + " where id = " + id);
		
		simpleBean = (SimpleBean)query.uniqueResult();
		
		session.close();
		
		return simpleBean;
	}
	
	@Override
	public SimpleBean getSimpleBean(Integer id, Class<?> type) 
	{
		return getSimpleBean(id, type.getSimpleName());
	}
	
	@Override
	// TODO txn
	public SimpleBean getSimpleBean(String value, Class<?> type) 
	{
		SimpleBean simpleBean = null;
		Session session = DataHelper.getSessionFactory().openSession();
		
		Query query = session.createQuery("from " + type.getSimpleName() + " where value = :value"); 
		query.setParameter("value", value); 
		
		simpleBean = (SimpleBean)query.uniqueResult();
		
		session.close();
		
		return simpleBean;
	}

}
