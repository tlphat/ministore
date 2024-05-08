package name.tlphat.ministore.server.adapters;

import name.tlphat.ministore.server.store.DataStore;
import name.tlphat.ministore.server.usecases.ports.DeleteStringDataAccess;

public class DeleteStringDataAccessImpl implements DeleteStringDataAccess {

  private final DataStore dataStore;

  public DeleteStringDataAccessImpl(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  @Override
  public void removeKey(String key) {
    dataStore.removeKey(key);
  }

  @Override
  public boolean keyNotExisted(String key) {
    return !dataStore.isStringExisted(key);
  }
}
