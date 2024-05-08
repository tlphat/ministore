package name.tlphat.ministore.server.adapters;

import java.util.List;
import java.util.stream.Stream;
import name.tlphat.ministore.server.store.DataStore;
import name.tlphat.ministore.server.usecases.dto.Key;
import name.tlphat.ministore.server.usecases.dto.KeyType;
import name.tlphat.ministore.server.usecases.ports.GetKeysDataAccess;

public class GetKeysDataAccessImpl implements GetKeysDataAccess {

  private final DataStore dataStore;

  public GetKeysDataAccessImpl(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  @Override
  public List<Key> getKeys() {
    final List<String> singletonKeys = dataStore.getSingletonKeys();
    final List<String> listKeys = dataStore.getListKeys();

    return Stream.concat(
            singletonKeys.stream().map(s -> new Key(s, KeyType.SINGLETON)),
            listKeys.stream().map(s -> new Key(s, KeyType.LIST)))
        .toList();
  }
}
