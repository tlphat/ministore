package name.tlphat.ministore.server.adapters;

import name.tlphat.ministore.server.store.DataStore;
import name.tlphat.ministore.server.usecases.ports.RightPushListDataAccess;

public class RightPushListDataAccessImpl implements RightPushListDataAccess {

    private final DataStore dataStore;

    public RightPushListDataAccessImpl(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void appendRightToList(String key, String value) {
        dataStore.appendToList(key, value);
    }
}
