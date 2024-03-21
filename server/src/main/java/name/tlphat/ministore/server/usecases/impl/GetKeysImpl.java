package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.GetKeysUseCase;
import name.tlphat.ministore.server.usecases.dto.Key;
import name.tlphat.ministore.server.usecases.ports.GetKeysDataAccess;
import name.tlphat.ministore.server.usecases.ports.GetKeysView;

import java.util.List;

public class GetKeysImpl implements GetKeysUseCase {

    private final GetKeysDataAccess dataAccess;
    private final GetKeysView view;

    public GetKeysImpl(
        GetKeysDataAccess dataAccess,
        GetKeysView view
    ) {
        this.dataAccess = dataAccess;
        this.view = view;
    }

    @Override
    public String getKeys() {
        final List<Key> keys = dataAccess.getKeys();
        return view.prepareSuccessfulView(keys);
    }
}
