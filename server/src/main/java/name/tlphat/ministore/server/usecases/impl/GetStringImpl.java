package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.GetStringUseCase;
import name.tlphat.ministore.server.usecases.ports.GetStringDataAccess;
import name.tlphat.ministore.server.usecases.ports.GetStringView;

public class GetStringImpl implements GetStringUseCase {

    private final GetStringDataAccess getStringDataAccess;
    private final GetStringView getStringView;

    public GetStringImpl(
        GetStringDataAccess getStringDataAccess,
        GetStringView getStringView
    ) {
        this.getStringDataAccess = getStringDataAccess;
        this.getStringView = getStringView;
    }

    @Override
    public String get(String key) {
        final String response = getStringDataAccess.get(key);
        return getStringView.prepareSuccessfulView(response);
    }
}
