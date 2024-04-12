package name.tlphat.ministore.server.controllers.impl;

import name.tlphat.ministore.server.adapters.DeleteStringDataAccessImpl;
import name.tlphat.ministore.server.adapters.DeleteStringViewImpl;
import name.tlphat.ministore.server.adapters.GetKeysDataAccessImpl;
import name.tlphat.ministore.server.adapters.GetKeysViewImpl;
import name.tlphat.ministore.server.adapters.GetListElementDataAccessImpl;
import name.tlphat.ministore.server.adapters.GetListElementViewImpl;
import name.tlphat.ministore.server.adapters.GetStringDataAccessImpl;
import name.tlphat.ministore.server.adapters.GetStringViewImpl;
import name.tlphat.ministore.server.adapters.RightPopListDataAccessImpl;
import name.tlphat.ministore.server.adapters.RightPopListViewImpl;
import name.tlphat.ministore.server.adapters.RightPushListDataAccessImpl;
import name.tlphat.ministore.server.adapters.RightPushListViewImpl;
import name.tlphat.ministore.server.adapters.SetStringDataAccessImpl;
import name.tlphat.ministore.server.adapters.SetStringViewImpl;
import name.tlphat.ministore.server.controllers.DataController;
import name.tlphat.ministore.server.controllers.constants.GetListElementError;
import name.tlphat.ministore.server.store.DataStore;
import name.tlphat.ministore.server.usecases.DeleteStringUseCase;
import name.tlphat.ministore.server.usecases.GetKeysUseCase;
import name.tlphat.ministore.server.usecases.GetListElementUseCase;
import name.tlphat.ministore.server.usecases.GetStringUseCase;
import name.tlphat.ministore.server.usecases.RightPopListUseCase;
import name.tlphat.ministore.server.usecases.RightPushListUseCase;
import name.tlphat.ministore.server.usecases.SetStringUseCase;
import name.tlphat.ministore.server.usecases.impl.DeleteStringImpl;
import name.tlphat.ministore.server.usecases.impl.GetKeysImpl;
import name.tlphat.ministore.server.usecases.impl.GetListElementImpl;
import name.tlphat.ministore.server.usecases.impl.GetStringImpl;
import name.tlphat.ministore.server.usecases.impl.RightPopListImpl;
import name.tlphat.ministore.server.usecases.impl.RightPushListImpl;
import name.tlphat.ministore.server.usecases.impl.SetStringImpl;
import name.tlphat.ministore.server.usecases.ports.DeleteStringDataAccess;
import name.tlphat.ministore.server.usecases.ports.DeleteStringView;
import name.tlphat.ministore.server.usecases.ports.GetKeysDataAccess;
import name.tlphat.ministore.server.usecases.ports.GetKeysView;
import name.tlphat.ministore.server.usecases.ports.GetListElementDataAccess;
import name.tlphat.ministore.server.usecases.ports.GetListElementView;
import name.tlphat.ministore.server.usecases.ports.GetStringDataAccess;
import name.tlphat.ministore.server.usecases.ports.GetStringView;
import name.tlphat.ministore.server.usecases.ports.RightPopListDataAccess;
import name.tlphat.ministore.server.usecases.ports.RightPopListView;
import name.tlphat.ministore.server.usecases.ports.RightPushListDataAccess;
import name.tlphat.ministore.server.usecases.ports.RightPushListView;
import name.tlphat.ministore.server.usecases.ports.SetStringDataAccess;
import name.tlphat.ministore.server.usecases.ports.SetStringView;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class DataControllerImpl implements DataController {

    private final DataStore dataStore;

    private final ReadLock readLock;
    private final WriteLock writeLock;

    public DataControllerImpl(DataStore dataStore) {
        this.dataStore = dataStore;

        final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();
    }

    public String getStringValue(String key) {
        readLock.lock();

        try {
            final GetStringDataAccess dataAccess = new GetStringDataAccessImpl(dataStore);
            final GetStringView view = new GetStringViewImpl();
            final GetStringUseCase useCase = new GetStringImpl(dataAccess, view);
            return useCase.get(key);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public String setStringValue(String key, String value) {
        writeLock.lock();

        try {
            final SetStringDataAccess dataAccess = new SetStringDataAccessImpl(dataStore);
            final SetStringView view = new SetStringViewImpl();
            final SetStringUseCase useCase = new SetStringImpl(dataAccess, view, DataConfig.MAX_LENGTH_STRING_VALUE);
            return useCase.set(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public String deleteString(String key) {
        writeLock.lock();

        try {
            final DeleteStringDataAccess dataAccess = new DeleteStringDataAccessImpl(dataStore);
            final DeleteStringView view = new DeleteStringViewImpl();
            final DeleteStringUseCase useCase = new DeleteStringImpl(dataAccess, view);
            return useCase.delete(key);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public String rightPushStringList(String key, String value) {
        writeLock.lock();

        try {
            final RightPushListDataAccess dataAccess = new RightPushListDataAccessImpl(dataStore);
            final RightPushListView view = new RightPushListViewImpl();
            final RightPushListUseCase useCase = new RightPushListImpl(
                dataAccess,
                view,
                DataConfig.MAX_LENGTH_STRING_VALUE
            );
            return useCase.rightPush(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public String rightPopStringList(String key) {
        writeLock.lock();

        try {
            final RightPopListDataAccess dataAccess = new RightPopListDataAccessImpl(dataStore);
            final RightPopListView view = new RightPopListViewImpl();
            final RightPopListUseCase useCase = new RightPopListImpl(dataAccess, view);
            return useCase.rightPop(key);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public String getListElement(String key, String indexInString) {
        readLock.lock();

        try {
            int index = Integer.parseInt(indexInString);

            final GetListElementDataAccess dataAccess = new GetListElementDataAccessImpl(dataStore);
            final GetListElementView view = new GetListElementViewImpl();
            final GetListElementUseCase useCase = new GetListElementImpl(dataAccess, view);

            return useCase.getElementAtIndex(key, index);
        } catch (NumberFormatException ex) {
            return GetListElementError.INVALID_INDEX.toString();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public String getKeys() {
        readLock.lock();

        try {
            final GetKeysDataAccess dataAccess = new GetKeysDataAccessImpl(dataStore);
            final GetKeysView view = new GetKeysViewImpl();
            final GetKeysUseCase useCase = new GetKeysImpl(dataAccess, view);
            return useCase.getKeys();
        } finally {
            readLock.unlock();
        }
    }
}
