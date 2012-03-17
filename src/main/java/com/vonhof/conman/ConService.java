package com.vonhof.conman;

import com.vonhof.conman.balancer.FirstIsBestBalancer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ConService<T> {
    private final String id;
    private final List<ConProvider> providers = new ArrayList<ConProvider>();
    private ConBalancer balancer = new FirstIsBestBalancer();

    public ConService(String id) {
        this.id = id;
    }


    public ConService(String id, ConProvider<T> ... prvs) {
        this.id = id;
        addProviders(prvs);
    }

    public void setBalancer(ConBalancer balancer) {
        this.balancer = balancer;
    }

    public void addProviders(ConProvider<T> ... prvs) {
        for(ConProvider<T> p:prvs)
            providers.add(p);
    }

    public ConProvider<T> getProvider() {
        return balancer.choose(providers);
    }

    public T getConnection() throws IOException {
        ConProvider<T> provider = getProvider();
        if (provider != null)
            return provider.getConnection();
        return null;
    }

    /**
     * An id uniquely identifying this external ressource. The id should be of the content that is provided - not the
     * connections. (Not "JDBC" but "mydb" )
     * @return
     */
    public String getId() {
        return id;
    };


}
