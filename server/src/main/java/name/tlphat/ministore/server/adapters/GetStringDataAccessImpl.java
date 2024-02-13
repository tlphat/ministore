package name.tlphat.ministore.server.adapters;

import name.tlphat.ministore.server.usecases.ports.GetStringDataAccess;
import name.tlphat.ministore.server.store.DataStore;

public class GetStringDataAccessImpl implements GetStringDataAccess {

    private final DataStore dataStore;

    public GetStringDataAccessImpl(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public String get(String key) {
        return dataStore.getStringValue(key);
    }

    @Override
    public boolean isKeyExisted(String key) {
        return dataStore.isStringExisted(key);
    }
}
