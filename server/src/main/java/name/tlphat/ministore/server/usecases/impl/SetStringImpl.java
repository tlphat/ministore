package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.entities.string.BasicString;
import name.tlphat.ministore.server.entities.string.String;
import name.tlphat.ministore.server.usecases.SetStringUseCase;
import name.tlphat.ministore.server.usecases.constants.SetStringError;
import name.tlphat.ministore.server.usecases.ports.SetStringDataAccess;
import name.tlphat.ministore.server.usecases.ports.SetStringView;

public class SetStringImpl implements SetStringUseCase {

    private final SetStringDataAccess dataAccess;
    private final SetStringView view;
    private final int maxNumberOfCharacters;

    public SetStringImpl(
        SetStringDataAccess dataAccess,
        SetStringView view,
        int maxNumberOfCharacters
    ) {
        this.dataAccess = dataAccess;
        this.view = view;
        this.maxNumberOfCharacters = maxNumberOfCharacters;
    }

    @Override
    public java.lang.String set(java.lang.String key, java.lang.String value) {
        final String stringValue = new BasicString(value);
        if (stringValue.exceedsSizeLimit(maxNumberOfCharacters)) {
            return view.prepareErrorView(SetStringError.VALUE_SIZE_TOO_LARGE);
        }

        dataAccess.set(key, value);

        return view.prepareSuccessfulView();
    }
}
