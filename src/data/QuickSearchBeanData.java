package data;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;

import bean.QuickSearchBean;

public class QuickSearchBeanData extends BasicHibernateDataSource {
	
	public List<QuickSearchBean> getAllQuickSearchBeans() 
	{
		return super.queryDatabaseForList(QuickSearchBean.class, "from QuickSearchBean"); 		
	}
	
	public List<QuickSearchBean> getAllQuickSearchBeans( QuickSearchBean searchBean ) 
	{
		List<QuickSearchBean> quickSearchBeans = null;
		Session session = DataHelper.getOpenSession(); 
		
		Example quickSearchBeanExample = Example.create(searchBean).excludeZeroes().enableLike(MatchMode.ANYWHERE);
		
		Criteria crit = session.createCriteria(QuickSearchBean.class);
		
		crit.add(quickSearchBeanExample);
		
		quickSearchBeans = (List<QuickSearchBean>) crit.list();
		
		return quickSearchBeans;
	}
}
