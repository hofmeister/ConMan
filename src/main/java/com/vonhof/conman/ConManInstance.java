package com.vonhof.conman;

import java.util.HashMap;
import java.util.Map;

public class ConManInstance {
    private final Map<String,ConService> registry = new HashMap<String, ConService>();

    protected ConManInstance() {

    }

    public void register(ConService service) {
        registry.put(service.getId().toLowerCase(),service);
    }

    public ConService get(String serviceId) {
        return registry.get(serviceId.toLowerCase());
    }
    
}
