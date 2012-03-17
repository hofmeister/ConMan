package com.vonhof.conman;

import java.util.Collection;

public abstract class ConBalancer {

    /**
     * Choose the provider to use for the subsequent connection.
     * @param providers
     * @return the next provider or null if none was found
     */
    public abstract ConProvider choose(Collection<ConProvider> providers);
}
