package com.vonhof.conman.sql;

import com.vonhof.conman.ConService;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class ConDataSource implements DataSource {
    private PrintWriter logWriter = null;
    private final ConService<Connection> service;

    public ConDataSource(ConService<Connection> service) {
        this.service = service;
    }


    public Connection getConnection() throws SQLException {
        try {
            return service.getConnection();
        } catch (IOException ex) {
            if (ex.getCause() instanceof SQLException)
                throw (SQLException)ex.getCause();
            throw new SQLException(ex);
        }
    }

    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException("Multi data source requires you to set username and password within providers");
    }

    public PrintWriter getLogWriter() throws SQLException {
        return logWriter;
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        logWriter = out;
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException("Multi data source requires you to set username and password within providers");
    }

    public int getLoginTimeout() throws SQLException {
        throw new UnsupportedOperationException("Multi data source requires you to set username and password within providers");
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
