package cn.li.component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitApplication implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String showPath = servletContextEvent.getServletContext().getContextPath()+"/upload/product/";
        String showHousePath = servletContextEvent.getServletContext().getContextPath()+"/upload/house/";
        servletContextEvent.getServletContext().setAttribute("showPath",showPath);
        servletContextEvent.getServletContext().setAttribute("showHousePath",showHousePath);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
