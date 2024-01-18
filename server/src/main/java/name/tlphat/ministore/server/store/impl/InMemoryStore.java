package name.tlphat.ministore.server.store.impl;

import name.tlphat.ministore.server.store.DataStore;

import java.util.HashMap;
import java.util.Map;

public class InMemoryStore implements DataStore {

    private final Map<String, Object> data;

    public InMemoryStore() {
        data = new HashMap<>();
        data.put("x", "value");
    }

    @Override
    public String getValue(String key) {
        return (String) data.get(key);
    }
}
