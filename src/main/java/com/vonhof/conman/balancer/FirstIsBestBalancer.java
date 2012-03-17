package com.vonhof.conman.balancer;

import com.vonhof.conman.ConBalancer;
import com.vonhof.conman.ConProvider;
import com.vonhof.conman.ConProvider.Health;
import java.util.Collection;

public class FirstIsBestBalancer extends ConBalancer {

    @Override
    public ConProvider choose(Collection<ConProvider> providers) {
        ConProvider tiredProvider = null;
        for(ConProvider p:providers) {
            if (p.getHealth().isAnyOf(Health.ALIVE)) {
                return p;
            }
            if (tiredProvider != null
                    && p.getHealth().isAnyOf(Health.TIRED)) {
                tiredProvider = p;
            }
        }

        return tiredProvider;
    }

}
