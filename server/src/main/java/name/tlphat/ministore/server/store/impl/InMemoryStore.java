package name.tlphat.ministore.server.store.impl;

import lombok.extern.slf4j.Slf4j;
import name.tlphat.ministore.server.store.DataStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class InMemoryStore implements DataStore {

    private final Map<String, String> stringData;
    private final Map<String, List<String>> stringListData;

    public InMemoryStore() {
        stringData = new HashMap<>();
        stringListData = new HashMap<>();
    }

    @Override
    public String getValue(String key) {
        return stringData.get(key);
    }

    @Override
    public boolean setString(String key, String value) {
        try {
            stringData.put(key, value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean appendToList(String key, String value) {
        try {
            final List<String> data = stringListData.getOrDefault(key, new ArrayList<>());
            data.add(value);
            stringListData.put(key, data);
            return true;
        } catch (Exception ex) {
            log.error("Error appending to list of key {}, value {}", key, value, ex);
            return false;
        }
    }

    @Override
    public boolean isListExisted(String key) {
        return stringListData.containsKey(key);
    }

    @Override
    public int getListSize(String key) {
        final List<String> list = stringListData.get(key);

        if (list == null) {
            throw new IllegalArgumentException();
        }

        return list.size();
    }

    @Override
    public String getAndPopRightmostElement(String key) {
        final List<String> list = stringListData.get(key);

        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return list.remove(list.size() - 1);
    }
}
