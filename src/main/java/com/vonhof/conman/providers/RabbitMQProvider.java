package com.vonhof.conman.providers;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.vonhof.conman.ConProvider;
import com.vonhof.conman.ConProvider.Health;
import java.io.IOException;

public class RabbitMQProvider implements ConProvider<Connection> {

    private final ConnectionFactory factory = new ConnectionFactory();
    private Connection healthConnection;

    public RabbitMQProvider(String host, int port, int timeout) {
        factory.setHost(host);
        factory.setPort(port);
        factory.setConnectionTimeout(timeout);
    }
    public RabbitMQProvider(String host, int port, int timeout,String username,String password) {
        this(host, port, timeout);
        factory.setUsername(username);
        factory.setPassword(password);
    }

    public Health getHealth() {
        try {
            if (healthConnection == null || !healthConnection.isOpen()) {
                final long before = System.currentTimeMillis();
                healthConnection = factory.newConnection();
                final long time = System.currentTimeMillis()-before;
                if (time > 10000)
                    return Health.TIRED;
                return Health.ALIVE;
            } else {
                return Health.ALIVE;
            }
        } catch (Throwable ex) {
            return Health.DEAD;
        }
    }

    public int getHealthCheckInterval() {
        return 120;
    }

    public Connection getConnection() throws IOException {
        return factory.newConnection();
    }

}
