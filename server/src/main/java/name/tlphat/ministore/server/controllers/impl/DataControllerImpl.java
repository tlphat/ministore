package name.tlphat.ministore.server.controllers.impl;

import name.tlphat.ministore.server.adapters.GetStringDataAccessImpl;
import name.tlphat.ministore.server.adapters.GetStringViewImpl;
import name.tlphat.ministore.server.controllers.DataController;
import name.tlphat.ministore.server.store.DataStore;
import name.tlphat.ministore.server.usecases.GetStringUseCase;
import name.tlphat.ministore.server.usecases.impl.GetStringImpl;
import name.tlphat.ministore.server.usecases.ports.GetStringDataAccess;
import name.tlphat.ministore.server.usecases.ports.GetStringView;

public class DataControllerImpl implements DataController {

    private final DataStore dataStore;
    private final GetStringView getStringView;

    public DataControllerImpl(DataStore dataStore) {
        this.dataStore = dataStore;
        getStringView = new GetStringViewImpl();
    }

    public String getStringValue(String key) {
        final GetStringDataAccess getStringDataAccess = new GetStringDataAccessImpl(dataStore);
        final GetStringUseCase getStringUseCase = new GetStringImpl(getStringDataAccess, getStringView);
        return getStringUseCase.get(key);
    }
}
