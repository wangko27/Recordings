package data.common;

import java.util.List;

import bean.City;
import bean.Country;
import bean.Format;
import bean.Media;
import bean.Quality;
import bean.RecordingType;
import bean.SimpleBean;
import bean.Song;
import bean.Venue;

public interface ISimpleEntityData {
	
	public List<Venue> getAllVenues();
	public List<RecordingType> getAllRecordingTypes();
	public List<Quality> getAllQualities();
	public List<Format> getAllFormats();
	public List<Media> getAllMedias();
	public List<City> getAllCities();
	public List<Country> getAllCountries();
	public List<Song> getAllSongs();
	
	void saveOrUpdateSimpleBean(SimpleBean simpleBean);
	SimpleBean getSimpleBean( Integer id, Class<?> type);
	SimpleBean getSimpleBean(String value, Class<?> type);
	SimpleBean getSimpleBean(Integer id, String type);
	void delete(SimpleBean simpleBean);
}
