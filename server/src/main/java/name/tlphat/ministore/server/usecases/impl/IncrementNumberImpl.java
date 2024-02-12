package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.IncrementNumberUseCase;
import name.tlphat.ministore.server.usecases.constants.IncrementNumberError;
import name.tlphat.ministore.server.usecases.ports.IncrementNumberDataAccess;
import name.tlphat.ministore.server.usecases.ports.IncrementNumberView;

public class IncrementNumberImpl implements IncrementNumberUseCase {

    private final IncrementNumberDataAccess dataAccess;
    private final IncrementNumberView view;

    public IncrementNumberImpl(
        IncrementNumberDataAccess dataAccess,
        IncrementNumberView view
    ) {
        this.dataAccess = dataAccess;
        this.view = view;
    }

    @Override
    public String increment(String key) {
        if (!dataAccess.isKeyExisted(key)) {
            return view.prepareFailedView(IncrementNumberError.KEY_NOT_EXISTED);
        }

        dataAccess.increment(key);

        return view.prepareSuccessfulView();
    }
}
