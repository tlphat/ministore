package name.tlphat.ministore.server.controllers.impl;

import name.tlphat.ministore.server.adapters.GetStringDataAccessImpl;
import name.tlphat.ministore.server.adapters.GetStringViewImpl;
import name.tlphat.ministore.server.adapters.RightPushListDataAccessImpl;
import name.tlphat.ministore.server.adapters.SetStringDataAccessImpl;
import name.tlphat.ministore.server.adapters.SetStringViewImpl;
import name.tlphat.ministore.server.controllers.DataController;
import name.tlphat.ministore.server.store.DataStore;
import name.tlphat.ministore.server.usecases.GetStringUseCase;
import name.tlphat.ministore.server.usecases.RightPushListUseCase;
import name.tlphat.ministore.server.usecases.SetStringUseCase;
import name.tlphat.ministore.server.usecases.adapters.RightPushListViewImpl;
import name.tlphat.ministore.server.usecases.impl.GetStringImpl;
import name.tlphat.ministore.server.usecases.impl.RightPushListImpl;
import name.tlphat.ministore.server.usecases.impl.SetStringImpl;
import name.tlphat.ministore.server.usecases.ports.GetStringDataAccess;
import name.tlphat.ministore.server.usecases.ports.GetStringView;
import name.tlphat.ministore.server.usecases.ports.RightPushListDataAccess;
import name.tlphat.ministore.server.usecases.ports.RightPushListView;
import name.tlphat.ministore.server.usecases.ports.SetStringDataAccess;
import name.tlphat.ministore.server.usecases.ports.SetStringView;

public class DataControllerImpl implements DataController {

    private final DataStore dataStore;

    public DataControllerImpl(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public String getStringValue(String key) {
        final GetStringDataAccess dataAccess = new GetStringDataAccessImpl(dataStore);
        final GetStringView view = new GetStringViewImpl();
        final GetStringUseCase useCase = new GetStringImpl(dataAccess, view);
        return useCase.get(key);
    }

    @Override
    public String setStringValue(String key, String value) {
        final SetStringDataAccess dataAccess = new SetStringDataAccessImpl(dataStore);
        final SetStringView view = new SetStringViewImpl();
        final SetStringUseCase useCase = new SetStringImpl(dataAccess, view, DataConfig.MAX_LENGTH_STRING_VALUE);
        return useCase.set(key, value);
    }

    @Override
    public String rightPushStringList(String key, String value) {
        final RightPushListDataAccess dataAccess = new RightPushListDataAccessImpl(dataStore);
        final RightPushListView view = new RightPushListViewImpl();
        final RightPushListUseCase useCase = new RightPushListImpl(
            dataAccess,
            view,
            DataConfig.MAX_LENGTH_STRING_VALUE
        );
        return useCase.rightPush(key, value);
    }
}
