package data;

import java.util.ArrayList;
import java.util.List;

import bean.City;
import bean.Country;
import bean.Format;
import bean.Media;
import bean.Quality;
import bean.RecordingType;
import bean.SimpleBean;
import bean.SimpleTestBean;
import bean.Song;
import bean.Venue;
import data.common.ISimpleEntityData;

public class StubSimpleEntityData implements ISimpleEntityData {

	@Override
	public List<Venue> getAllVenues() {
		List<Venue> venues = new ArrayList<Venue>();
		
		for( int x = 0; x < ValueGenerator.venue.length; x++ )
		{
			SimpleTestBean simpleBean = ValueGenerator.getSimpleTestBean("venue", x);
			
			Venue venue = new Venue();
			venue.setId(simpleBean.getId());
			venue.setValue(simpleBean.getValue());
			
			venues.add(venue);
		}
		
		return venues;
	}
	
	@Override
	public List<RecordingType> getAllRecordingTypes() {
		List<RecordingType> types = new ArrayList<RecordingType>();
		
		for( int x = 0; x < ValueGenerator.recordingType.length; x++ )
		{
			SimpleTestBean simpleBean = ValueGenerator.getSimpleTestBean("recordingType", x);
			
			RecordingType type = new RecordingType();
			type.setId(simpleBean.getId());
			type.setValue(simpleBean.getValue());
			
			types.add(type);
		}
		
		return types;
	}

	@Override
	public List<Song> getAllSongs() {

		return null;
	}

	@Override
	public List<Quality> getAllQualities() {

		return null;
	}

	@Override
	public List<Format> getAllFormats() {
	
		return null;
	}

	@Override
	public List<City> getAllCities() {
	
		return null;
	}

	@Override
	public List<Country> getAllCountries() {
		
		return null;
	}

	@Override
	public List<Media> getAllMedias() {
		
		return null;
	}

	@Override
	public void saveOrUpdateSimpleBean(SimpleBean simpleBean) {
		
	}

	@Override
	public SimpleBean getSimpleBean(Integer id, Class<?> type) {
		return null;
	}

	@Override
	public SimpleBean getSimpleBean(String value, Class<?> type) {
		return null;
	}

	@Override
	public SimpleBean getSimpleBean(Integer id, String type) {
		return null;
	}

	@Override
	public void delete(SimpleBean simpleBean) {
		
	}
}
