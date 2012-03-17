package com.vonhof.conman.balancer;

import com.vonhof.conman.ConBalancer;
import com.vonhof.conman.ConProvider;
import com.vonhof.conman.ConProvider.Health;
import java.util.Collection;

public class RoundRobinBalancer extends ConBalancer {
    private volatile int offset = 0;

    private final boolean useTired;

    public RoundRobinBalancer(boolean useTired) {
        this.useTired = useTired;
    }

    public RoundRobinBalancer() {
        this(true);
    }


    @Override
    public ConProvider choose(final Collection<ConProvider> providers) {
        synchronized(providers) {
            ConProvider tiredProvider = null;
            int tiredOffset = -1;

            if (offset > (providers.size()-1))
                offset = 0;

            int i = 0;
            for(ConProvider p:providers) {
                i++;
                if (i < offset)
                    continue;

                if (p.getHealth().isAnyOf(Health.ALIVE)) {
                    offset = i+1;
                    return p;
                }

                if (useTired
                        && tiredProvider != null
                        && p.getHealth().isAnyOf(Health.TIRED)) {
                    tiredProvider = p;
                    tiredOffset = i+1;
                }
            }

            if (tiredProvider != null) {
                offset = tiredOffset;
            }
            return tiredProvider;
        }
    }

}
