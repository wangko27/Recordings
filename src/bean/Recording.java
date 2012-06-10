package bean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.DateUtil;
import util.StringUtil;

public class Recording
{ 
	private int id;
	private Integer year; 
	private Integer month;
	private Integer date;
	private String link;
	private String sublocation;
	private String name;
	private Venue venue; 
	private Quality quality;
	private Format format;
	private Media media;
	private RecordingType recordingType;
	private Country country;
	private City city;
	private int upvotes; 
	private int downvotes; 
	private Date dateAdded;
	private int jon; // if I have this bootleg (for trading purposes)
	private String jonnote; // notes about my bootleg (for trading purposes)
	
	private List<SongInstance> songInstances;
	private List<RecordingComment> comments;
	
	public Recording()
	{
		month = 0; 
		date = 0; 
		year = 0; 
		
		this.songInstances = new ArrayList<SongInstance>();
		this.comments = new ArrayList<RecordingComment>();
	}
	
	public String getFormattedShortLocation()
	{
		String formattedLocation = "";
		boolean hasCity = this.city != null;
		boolean hasSublocation = StringUtil.hasValue(this.sublocation);
		boolean hasCountry = this.country != null;
		
		if( hasCity || hasSublocation || hasCountry )
		{
			if( hasCity )
			{
				formattedLocation += city.getValue();
			
				if( hasSublocation || hasCountry )
					formattedLocation += ", ";
			}
			
			if( hasCountry)
				formattedLocation += country.getValue();
		}
		else
		{
			formattedLocation = "Unknown Location";
		}
		
		return formattedLocation;
	}
	
	public String getFormattedLocation()
	{
		String formattedLocation = "";
		boolean hasCity = this.city != null;
		boolean hasSublocation = StringUtil.hasValue(this.sublocation);
		boolean hasCountry = this.country != null;
		
		if( hasCity || hasSublocation || hasCountry )
		{
			if( hasCity )
			{
				formattedLocation += city.getValue();
			
				if( hasSublocation || hasCountry )
					formattedLocation += ", ";
			}
			
			if( hasSublocation )
			{
				formattedLocation += this.sublocation;
			
				if( hasCountry )
					formattedLocation += ", ";
			}
			
			if( hasCountry)
				formattedLocation += country.getValue();
		}
		else
		{
			formattedLocation = "Unknown Location";
		}
		
		return formattedLocation;
	}
	
	public String getFormattedLongDateString()
	{
		String formattedLongDateString = "";
		
		if( DateUtil.validDate(this.date) || DateUtil.validMonth(this.month) || DateUtil.validYear(this.year) )
		{
			if( DateUtil.validMonth(this.month) )
				formattedLongDateString += DateUtil.getMonthNameFromMonthNum(this.month);
			
			if( DateUtil.validDate(this.date) )
				formattedLongDateString += " " + DateUtil.getSupplementedDateValue(this.date);
			
			if( DateUtil.validDate(this.date) && DateUtil.validYear(this.year) )
				formattedLongDateString += ",";
			
			if( DateUtil.validYear(this.year) && (DateUtil.validDate(this.date) || DateUtil.validMonth(this.month)) )
				formattedLongDateString += " ";
			
			if( DateUtil.validYear(this.year) )
				formattedLongDateString += this.year;
		}
		else
		{
			if( this.recordingType == null || 
					(this.recordingType != null && !this.recordingType.getValue().equals("Studio Bootleg") && 
												   !this.recordingType.getValue().equals("Interview") && 
												   !this.recordingType.getValue().equals("Radio") &&
												   !this.recordingType.getValue().equals("Compilation") &&
												   !this.recordingType.getValue().equals("TV") &&
												   !this.recordingType.getValue().equals("Single")) )
				formattedLongDateString += "Unknown Date";
		}
		
		return formattedLongDateString;
	}
	
	public String getFormattedVenue()
	{
		String formattedVenue = "Unknown Venue";
		
		if( this.getVenue() != null && StringUtil.hasValue(this.getVenue().getValue()) )
		{
			formattedVenue = this.getVenue().getValue();
		}
		
		return formattedVenue;
	}
	
	public String getFormattedShortDateString()
	{
		String formattedShortDateString = "";
		
		if( this.recordingType != null && StringUtil.hasValue(this.recordingType.getValue()) && this.recordingType.getValue().equals("Album") )
		{
			if( DateUtil.validYear(this.year) )
				formattedShortDateString += this.year;
		}
		else
		{
			// if only year
			if( DateUtil.validYear(this.year) && !DateUtil.validMonth(this.month) && !DateUtil.validDate(this.date) )
			{
				formattedShortDateString += this.year;
			}
			else if( DateUtil.validDate(this.date) || DateUtil.validMonth(this.month) || DateUtil.validYear(this.year) )
			{
				if( DateUtil.validYear(this.year) )
					formattedShortDateString += this.year;
				else
					formattedShortDateString += "?";
	
				formattedShortDateString += "/";
				
				if( DateUtil.validMonth(this.month) )
					formattedShortDateString += DateUtil.getTwoDigitNum(this.month);
				else
					formattedShortDateString += "?";
				
				formattedShortDateString += "/";
				
				if( DateUtil.validDate(this.date) )
					formattedShortDateString += DateUtil.getTwoDigitNum(this.date);
				else
					formattedShortDateString += "?";
			}
			else
			{
				if( this.recordingType == null || 
						(this.recordingType != null && !this.recordingType.getValue().equals("Studio Bootleg") && 
													   !this.recordingType.getValue().equals("Single") &&
													   !this.recordingType.getValue().equals("TV") &&
													   !this.recordingType.getValue().equals("Compilation") &&
													   !this.recordingType.getValue().equals("Radio") &&
													   !this.recordingType.getValue().equals("Interview")) )
					formattedShortDateString += "unk";
			}
		}

		return formattedShortDateString;
	}
	
	/**
	 * Returns the SimpleBean that has the concrete type
	 * with a name of the passed-in field
	 * @param field
	 * @return
	 * @throws Exception
	 */
	public SimpleBean getSimpleBean( String field ) throws Exception
	{
		SimpleBean simpleBean = null;
		
		// iterate through all the method names to find the get<Field>() method
		Method m[] = Recording.class.getDeclaredMethods();
		for (int i = 0; i < m.length; i++)
		{
			String methodName = m[i].getName();
			
			// method name must have at least three chars to be a getter
			if( methodName.length() > 3  )
			{
				String firstThreeChars = methodName.substring(0,3);
				String afterThreeChars = methodName.substring(3);
				
				if( firstThreeChars.equals("get") && afterThreeChars.toUpperCase().equals(field.toUpperCase()) )
				{
					simpleBean = (SimpleBean) m[i].invoke(this);
					break;
				}
			}
		}
		
		return simpleBean;
	}
	
	public void setSimpleBean( SimpleBean simpleBean ) throws Exception
	{
		Class<? extends SimpleBean> simpleBeanClass = simpleBean.getClass();
		
		Method m[] = Recording.class.getDeclaredMethods();
		for (int i = 0; i < m.length; i++)
		{
			String methodName = m[i].getName();
			
			// method name must have at least three chars to be a setter
			if( methodName.length() > 3  )
			{
				String firstThreeChars = methodName.substring(0,3);
				String afterThreeChars = methodName.substring(3);
				
				if( firstThreeChars.equals("set") && afterThreeChars.toUpperCase().equals(simpleBeanClass.getSimpleName().toUpperCase()) )
				{
					m[i].invoke(this, simpleBean);
					break;
				}
			}
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/* getters and setters */
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getDate() {
		return date;
	}
	public void setDate(Integer date) {
		this.date = date;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getSublocation() {
		return sublocation;
	}
	public void setSublocation(String sublocation) {
		this.sublocation = sublocation;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Venue getVenue() {
		return venue;
	}
	public void setVenue(Venue venue) {
		this.venue = venue;
	}
	public Quality getQuality() {
		return quality;
	}
	public void setQuality(Quality quality) {
		this.quality = quality;
	}
	public Format getFormat() {
		return format;
	}
	public void setFormat(Format format) {
		this.format = format;
	}
	public Media getMedia() {
		return media;
	}
	public void setMedia(Media media) {
		this.media = media;
	}
	
	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public RecordingType getRecordingType() {
		return recordingType;
	}
	public void setRecordingType(RecordingType recordingType) {
		this.recordingType = recordingType;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public List<SongInstance> getSongInstances() {
		return songInstances;
	}
	public void setSongInstances(List<SongInstance> songInstances) {
		this.songInstances = songInstances;
	}
	public List<RecordingComment> getComments() {
		return comments;
	}
	public void setComments(List<RecordingComment> comments) {
		this.comments = comments;
	}

	public int getUpvotes() {
		return upvotes;
	}

	public void setUpvotes(int upvotes) {
		this.upvotes = upvotes;
	}

	public int getDownvotes() {
		return downvotes;
	}

	public void setDownvotes(int downvotes) {
		this.downvotes = downvotes;
	}
	
	public int getVotes()
	{
		return upvotes - downvotes; 
	}

	public int getJon() {
		return jon;
	}
	public void setJon(int jon) {
		this.jon = jon;
	}
	public String getJonnote() {
		return jonnote;
	}
	public void setJonnote(String jonnote) {
		this.jonnote = jonnote;
	}
}