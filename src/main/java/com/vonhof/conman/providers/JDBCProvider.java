package com.vonhof.conman.providers;

import com.vonhof.conman.ConProvider;
import com.vonhof.conman.ConProvider.Health;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCProvider implements ConProvider<Connection> {
    private final String url;
    private final String username;
    private final String password;

    private Connection healthConnection;

    public JDBCProvider(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }


    public Health getHealth() {
        
        try {
            if (healthConnection == null
                    || healthConnection.isClosed()) {

                    final long before = System.currentTimeMillis();
                    healthConnection = getConnection();
                    final long time = System.currentTimeMillis()-before;
                    if (time > 10000)
                        return Health.TIRED;
                    return Health.ALIVE;
            }
            if (healthConnection != null 
                    &&  healthConnection.isClosed()) {

                final long before = System.currentTimeMillis();
                final boolean valid = healthConnection.isValid(20000);
                final long time = System.currentTimeMillis()-before;
                if (valid && time > 10000)
                    return Health.TIRED;
                if (valid)
                    return Health.ALIVE;
            }
        } catch (Throwable ex) {

        }
        return Health.DEAD;
    }

    public int getHealthCheckInterval() {
        return 120;
    }

    public Connection getConnection() throws IOException {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            throw new IOException(username, ex);
        }
    }

}
