package com.vonhof.conman.providers;

import com.vonhof.conman.ConProvider;
import com.vonhof.conman.ConProvider.Health;
import com.vonhof.conman.providers.WebServiceProvider.WebConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebServiceProvider implements ConProvider<WebConnection> {
    private final String baseURL;
    private final String checkPath;

    public WebServiceProvider(String baseUrl, String checkPath) {
        this.baseURL = baseUrl;
        this.checkPath = checkPath;
    }

    public Health getHealth() {
        HttpURLConnection conn = null;
        try {
            final long before = System.currentTimeMillis();
            getConnection().open(checkPath);
            final long time = System.currentTimeMillis() - before;
            if (time > 10000)
                return Health.TIRED;
            return Health.ALIVE;
        } catch (Throwable ex) {
            return Health.DEAD;
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    public int getHealthCheckInterval() {
        return 120;
    }

    public WebConnection getConnection() throws IOException {
        return new WebConnection(baseURL);
    }

    public static class WebConnection {
        protected final String baseURL;

        private WebConnection(String baseURL) {
            this.baseURL = baseURL;
        }

        public HttpURLConnection open(String path) throws MalformedURLException, IOException {
            URL url = new URL(baseURL+path);
            return (HttpURLConnection) url.openConnection();
        }
    }

}
