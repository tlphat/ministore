package name.tlphat.ministore.server.store.impl;

import name.tlphat.ministore.server.store.DataStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryStore implements DataStore {

    private final Map<String, String> stringData;
    private final Map<String, List<String>> stringListData;

    public InMemoryStore() {
        stringData = new HashMap<>();
        stringListData = new HashMap<>();
    }

    @Override
    public String getStringValue(String key) {
        return stringData.get(key);
    }

    @Override
    public void setString(String key, String value) {
        stringData.put(key, value);
    }

    @Override
    public void appendToList(String key, String value) {
        final List<String> data = stringListData.getOrDefault(key, new ArrayList<>());
        data.add(value);
        stringListData.put(key, data);
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

    @Override
    public boolean isStringExisted(String key) {
        return stringData.containsKey(key);
    }

    @Override
    public void removeList(String key) {
        stringListData.remove(key);
    }

    @Override
    public String getListElementAtIndex(String key, int index) {
        final List<String> list = stringListData.get(key);

        if (list == null) {
            throw new IllegalArgumentException();
        }

        return list.get(index);
    }
}
