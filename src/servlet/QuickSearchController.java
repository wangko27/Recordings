package servlet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.StringUtil;
import bean.QuickSearchBean;
import domain.QuickSearchBeanManager;
import exception.RecordingException;

@WebServlet("/QuickSearchController")
public class QuickSearchController extends ServletExtension {
	
	private static final long serialVersionUID = 1L;	
	private QuickSearchBeanManager quickSearchBeanManager = new QuickSearchBeanManager();

	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(req,response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			sendQuickSearchBeanList(req);
		}
		catch( Exception e )
		{
			addErrorMessage(req, new RecordingException(e));
		}
		
		loadPage(req, response);
	}
	
	private void sendQuickSearchBeanList( HttpServletRequest req ) throws RecordingException
	{
		HttpSession session = req.getSession();
		String sourceEvent = (String) req.getParameter("sourceEvent");
		Collection<QuickSearchBean> quickSearchBeanList = (Collection<QuickSearchBean>) session.getAttribute("quickSearchBeanList");
		
		// determine whether to use the recordingList from session or a fresh one
		if( StringUtil.hasValue(sourceEvent) && sourceEvent.equals("search") || quickSearchBeanList == null )
		{
			QuickSearchBean searchBean = getSearchBean(req);
			setSessionQuickSearchBean(req, searchBean);
			quickSearchBeanList = quickSearchBeanManager.getAllQuickSearchBeans(searchBean);
			
			session.setAttribute("quickSearchBeanList", quickSearchBeanList);
		}
		
		req.setAttribute("quickSearchBeanList", quickSearchBeanList);
	}
	
	private QuickSearchBean getSearchBean( HttpServletRequest req ) throws RecordingException
	{
		QuickSearchBean searchBean = new QuickSearchBean();
		
		String quickSearchValue = req.getParameter("quickSearchValue");
		
		if( StringUtil.hasValue(quickSearchValue) )
			searchBean.setValue(quickSearchValue);

		return searchBean;
	}
	
	private void setSessionQuickSearchBean( HttpServletRequest req, QuickSearchBean quickSearchBean )
	{
		HttpSession session = req.getSession();
		if( quickSearchBean != null )
			session.setAttribute("quickSearchBean", quickSearchBean);
		else
			session.setAttribute("quickSearchBean", new QuickSearchBean());
	}
	
	private void loadPage( HttpServletRequest req, HttpServletResponse response ) throws ServletException, IOException
	{
		loadJSP(req, response, "/jsp/mainFilterTables/quickSearchFilterTable.jsp");
	}
}
