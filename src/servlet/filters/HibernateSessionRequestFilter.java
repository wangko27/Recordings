package servlet.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;

import data.DataHelper;
 

public class HibernateSessionRequestFilter implements Filter {
 
    private SessionFactory sf;
 
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
 
    	Session session = null; 
    	
        try {        	
        	session = sf.openSession(); 
        	
            session.beginTransaction();
        	DataHelper.setOpenSession(session); 
            
            // Call the next filter (continue request processing)
            chain.doFilter(request, response);
 
            // Commit and cleanup
            session.getTransaction().commit();
            session.close(); 
            DataHelper.setOpenSession(null); 
 
        } catch (StaleObjectStateException staleEx) {
            // Rollback, close everything, possibly compensate for any permanent changes
            // during the conversation, and finally restart business conversation. Maybe
            // give the user of the application a chance to merge some of his work with
            // fresh data... what you do here depends on your applications design.
            throw staleEx;
        } catch (Throwable ex) {
            // Rollback only
            ex.printStackTrace();
            try {
                if (session != null && session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                    session.close(); 
                    DataHelper.setOpenSession(null); 
                }
            } catch (Throwable rbEx) {
            }
 
            // Let others handle it... maybe another interceptor for exceptions?
            throw new ServletException(ex);
        }
    }
 
    public void init(FilterConfig filterConfig) throws ServletException {
        sf = DataHelper.getSessionFactory();
    }
 
    public void destroy() {}
 
}
