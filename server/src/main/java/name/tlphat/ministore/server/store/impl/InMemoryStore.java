package name.tlphat.ministore.server.store.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import name.tlphat.ministore.server.store.Data;
import name.tlphat.ministore.server.store.DataStore;

public class InMemoryStore implements DataStore {

  private Map<String, String> stringData;
  private Map<String, List<String>> stringListData;

  public InMemoryStore() {
    stringData = new ConcurrentHashMap<>();
    stringListData = new ConcurrentHashMap<>();
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
  public synchronized void appendToList(String key, String value) {
    final List<String> data = stringListData.getOrDefault(key, new ArrayList<>());
    data.add(value);
    stringListData.put(key, data);
  }

  @Override
  public boolean isListExisted(String key) {
    return stringListData.containsKey(key);
  }

  @Override
  public synchronized int getListSize(String key) {
    final List<String> list = stringListData.get(key);

    if (list == null) {
      throw new IllegalArgumentException();
    }

    return list.size();
  }

  @Override
  public synchronized String getAndPopRightmostElement(String key) {
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
  public synchronized String getListElementAtIndex(String key, int index) {
    final List<String> list = stringListData.get(key);

    if (list == null) {
      throw new IllegalArgumentException();
    }

    return list.get(index);
  }

  @Override
  public byte[] serializeData() {
    try (final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ObjectOutputStream objectOutputStream =
            new ObjectOutputStream(byteArrayOutputStream)) {
      final Data data = new Data(stringData, stringListData);
      objectOutputStream.writeObject(data);
      objectOutputStream.flush();

      return byteArrayOutputStream.toByteArray();
    } catch (IOException e) {
      return new byte[] {};
    }
  }

  @Override
  public void deserializeFromData(byte[] data) {
    try (final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
      final Data obj = (Data) objectInputStream.readObject();
      stringData = obj.stringData();
      stringListData = obj.stringListData();
    } catch (IOException | ClassNotFoundException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public synchronized void removeKey(String key) {
    if (!stringData.containsKey(key)) {
      throw new IllegalArgumentException();
    }

    stringData.remove(key);
  }

  @Override
  public synchronized List<String> getSingletonKeys() {
    return stringData.keySet().stream().toList();
  }

  @Override
  public synchronized List<String> getListKeys() {
    return stringListData.keySet().stream().toList();
  }
}
