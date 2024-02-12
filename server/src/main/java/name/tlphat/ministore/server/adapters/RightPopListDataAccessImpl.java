package name.tlphat.ministore.server.adapters;

import name.tlphat.ministore.server.store.DataStore;
import name.tlphat.ministore.server.usecases.ports.RightPopListDataAccess;

public class RightPopListDataAccessImpl implements RightPopListDataAccess {

    private final DataStore dataStore;

    public RightPopListDataAccessImpl(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public boolean isKeyExisted(String key) {
        return dataStore.isListExisted(key);
    }

    @Override
    public boolean isListEmpty(String key) {
        final int listSize = dataStore.getListSize(key);
        return listSize == 0;
    }

    @Override
    public String rightPop(String key) {
        return dataStore.getAndPopRightmostElement(key);
    }
}
