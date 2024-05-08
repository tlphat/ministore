package name.tlphat.ministore.server.adapters;

import name.tlphat.ministore.server.store.DataStore;
import name.tlphat.ministore.server.usecases.ports.GetListElementDataAccess;

public class GetListElementDataAccessImpl implements GetListElementDataAccess {

  private final DataStore dataStore;

  public GetListElementDataAccessImpl(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  @Override
  public boolean isListExisted(String key) {
    return dataStore.isListExisted(key);
  }

  @Override
  public int getListSize(String key) {
    return dataStore.getListSize(key);
  }

  @Override
  public String getElementAtIndex(String key, int index) {
    return dataStore.getListElementAtIndex(key, index);
  }
}
