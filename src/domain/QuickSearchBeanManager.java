package domain;

import java.util.List;

import bean.QuickSearchBean;
import data.QuickSearchBeanData;

public class QuickSearchBeanManager {
	
	private QuickSearchBeanData data;
	
	public QuickSearchBeanManager() {
		data = new QuickSearchBeanData();
	}
	
	public List<QuickSearchBean> getAllQuickSearchBeans()
	{
		return data.getAllQuickSearchBeans();
	}
	
	public List<QuickSearchBean> getAllQuickSearchBeans( QuickSearchBean searchBean )
	{
		List<QuickSearchBean> smartSongs = null;
		if( searchBean != null )
			smartSongs = data.getAllQuickSearchBeans(searchBean);
		else
			smartSongs = data.getAllQuickSearchBeans();
		
		return smartSongs;
	}
}
