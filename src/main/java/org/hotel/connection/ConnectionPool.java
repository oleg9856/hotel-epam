package org.hotel.connection;

import lombok.extern.log4j.Log4j2;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation Connection
 * through DataSource and with ConnectionPool to database
 *
 * @author O.Fursovych
 */
@Log4j2
public class ConnectionPool {

    private static DataSource ds;
    private static ConnectionPool instance;

    /**
     * Private constructor that initializes DataSource
     * with ConnectionPool
     */
    private ConnectionPool() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/hotel_schema");
            log.debug("DataSource is initialize");
        } catch (NamingException e) {
            log.error("Error in initialization DataSource", e);
            throw new IllegalStateException(e);
        }
    }
    // singleton

    public static synchronized ConnectionPool getInstance() {
        if (instance == null)
            instance = new ConnectionPool();
        return instance;
    }

    public Connection getConnection() throws SQLException{
        return ds.getConnection();
    }

}
