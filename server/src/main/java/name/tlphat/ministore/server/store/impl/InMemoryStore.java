package name.tlphat.ministore.server.store.impl;

import name.tlphat.ministore.server.store.DataStore;

import java.util.HashMap;
import java.util.Map;

public class InMemoryStore implements DataStore {

    private final Map<String, Object> data;

    public InMemoryStore() {
        data = new HashMap<>();
    }

    @Override
    public String getValue(String key) {
        return (String) data.get(key);
    }

    @Override
    public boolean setString(String key, String value) {
        try {
            data.put(key, value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
