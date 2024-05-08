package name.tlphat.ministore.server.adapters;

import name.tlphat.ministore.server.store.DataStore;
import name.tlphat.ministore.server.usecases.ports.SetStringDataAccess;

public class SetStringDataAccessImpl implements SetStringDataAccess {

  private final DataStore dataStore;

  public SetStringDataAccessImpl(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  @Override
  public void set(String key, String value) {
    dataStore.setString(key, value);
  }
}
