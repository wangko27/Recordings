package data.db;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import bean.QuickSearchBean;
import data.HibernateTest;
import data.QuickSearchBeanData;

public class TestQuickSearchBeanData extends HibernateTest {
	
	private QuickSearchBeanData data = new QuickSearchBeanData();
	
	@Test
	public void getAllQuickSearchBeans()
	{
		Collection<QuickSearchBean> quickSearchBeans = data.getAllQuickSearchBeans();
		assertNotNull(quickSearchBeans);
		assertTrue(!quickSearchBeans.isEmpty());
	}
}
