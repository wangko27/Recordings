package domain;

import java.util.List;

import bean.NotUsed;
import data.NotUsedData;

public class NotUsedManager {
	
	private NotUsedData data;
	
	public NotUsedManager() {
		data = new NotUsedData();
	}
	
	public List<NotUsed> getAllNotUsed()
	{
		return data.getAllNotUsed();
	}

}
