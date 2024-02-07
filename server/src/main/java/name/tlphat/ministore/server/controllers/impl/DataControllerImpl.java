package name.tlphat.ministore.server.controllers.impl;

import name.tlphat.ministore.server.adapters.GetStringDataAccessImpl;
import name.tlphat.ministore.server.adapters.GetStringViewImpl;
import name.tlphat.ministore.server.adapters.SetStringDataAccessImpl;
import name.tlphat.ministore.server.adapters.SetStringViewImpl;
import name.tlphat.ministore.server.controllers.DataController;
import name.tlphat.ministore.server.store.DataStore;
import name.tlphat.ministore.server.usecases.GetStringUseCase;
import name.tlphat.ministore.server.usecases.SetStringUseCase;
import name.tlphat.ministore.server.usecases.impl.GetStringImpl;
import name.tlphat.ministore.server.usecases.impl.SetStringImpl;
import name.tlphat.ministore.server.usecases.ports.GetStringDataAccess;
import name.tlphat.ministore.server.usecases.ports.GetStringView;
import name.tlphat.ministore.server.usecases.ports.SetStringDataAccess;
import name.tlphat.ministore.server.usecases.ports.SetStringView;

public class DataControllerImpl implements DataController {

    private final DataStore dataStore;
    private final GetStringView getStringView;
    private final SetStringView setStringView;

    public DataControllerImpl(DataStore dataStore) {
        this.dataStore = dataStore;
        getStringView = new GetStringViewImpl();
        setStringView = new SetStringViewImpl();
    }

    public String getStringValue(String key) {
        final GetStringDataAccess getStringDataAccess = new GetStringDataAccessImpl(dataStore);
        final GetStringUseCase getStringUseCase = new GetStringImpl(getStringDataAccess, getStringView);
        return getStringUseCase.get(key);
    }

    @Override
    public String setStringValue(String key, String value) {
        final SetStringDataAccess setStringDataAccess = new SetStringDataAccessImpl(dataStore);
        final SetStringUseCase setStringUseCase = new SetStringImpl(
            setStringDataAccess,
            setStringView,
            DataConfig.MAX_LENGTH_STRING_VALUE
        );
        return setStringUseCase.set(key, value);
    }
}
