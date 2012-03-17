package com.vonhof.conman;

import java.io.IOException;

/**
 * Interface defining a connection provider
 * @author Henrik Hofmeister <henrik@newdawn.dk>
 */
public interface ConProvider<T> {
    /**
     * Gets the health of the provider.
     * This method is called at most at the frequency returned by getHelthCheckInterval()
     * @return
     */
    public Health getHealth();

    /**
     * The interval in which health is checked - measured in seconds
     * @return
     */
    public int getHealthCheckInterval();

    /**
     * Get connection to this provider. Is called only after a positive health check (Possibly TIRED)
     * @return
     */
    public T getConnection() throws IOException;

    /**
     * Health enum that provides 3 states:
     * DEAD = Provider not available
     * TIRED = Provider available - but seems under pressure. Its optional to add support for "TIRED"
     * ALIVE = Provider alive and kicking
     */
    public static enum Health {
        DEAD,
        TIRED,
        ALIVE;

        public boolean isAnyOf(Health ... healths) {
            for(Health h:healths)
                if (h.equals(this)) {
                    return true;
                }
            return false;
        }
    }
}
