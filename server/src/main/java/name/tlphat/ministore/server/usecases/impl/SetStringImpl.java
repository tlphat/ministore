package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.entities.string.BasicString;
import name.tlphat.ministore.server.entities.string.String;
import name.tlphat.ministore.server.usecases.SetStringUseCase;
import name.tlphat.ministore.server.usecases.constants.SetStringError;
import name.tlphat.ministore.server.usecases.ports.SetStringDataAccess;
import name.tlphat.ministore.server.usecases.ports.SetStringView;

public class SetStringImpl implements SetStringUseCase {

    private final SetStringDataAccess setStringDataAccess;
    private final SetStringView setStringView;
    private final int maxNumberOfCharacters;

    public SetStringImpl(
        SetStringDataAccess setStringDataAccess,
        SetStringView setStringView,
        int maxNumberOfCharacters
    ) {
        this.setStringDataAccess = setStringDataAccess;
        this.setStringView = setStringView;
        this.maxNumberOfCharacters = maxNumberOfCharacters;
    }

    @Override
    public java.lang.String set(java.lang.String key, java.lang.String value) {
        final String stringValue = new BasicString(value);
        if (stringValue.exceedsSizeLimit(maxNumberOfCharacters)) {
            return setStringView.prepareErrorView(SetStringError.VALUE_SIZE_TOO_LARGE);
        }

        setStringDataAccess.set(key, value);
        return setStringView.prepareSuccessfulView();
    }
}
