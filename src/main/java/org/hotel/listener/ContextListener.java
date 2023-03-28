package org.hotel.listener;

import org.hotel.connection.ConnectionPool;
import org.hotel.exception.ConnectionPoolException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
//        ConnectionPool connectionPool = ConnectionPool.getInstance();
//        try {
//            connectionPool.terminate();
//        } catch (ConnectionPoolException e) {
//            throw new IllegalStateException(e.getMessage(), e);
//        }
    }

}
