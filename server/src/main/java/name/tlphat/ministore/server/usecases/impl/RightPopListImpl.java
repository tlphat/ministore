package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.RightPopListUseCase;
import name.tlphat.ministore.server.usecases.constants.RightPopListError;
import name.tlphat.ministore.server.usecases.ports.RightPopListDataAccess;
import name.tlphat.ministore.server.usecases.ports.RightPopListView;

public class RightPopListImpl implements RightPopListUseCase {

    private final RightPopListDataAccess dataAccess;
    private final RightPopListView view;

    public RightPopListImpl(
        RightPopListDataAccess dataAccess,
        RightPopListView view
    ) {
        this.dataAccess = dataAccess;
        this.view = view;
    }

    @Override
    public String rightPop(String key) {
        if (!dataAccess.isKeyExisted(key)) {
            return view.prepareFailedView(RightPopListError.KEY_NOT_EXISTED);
        }

        if (dataAccess.isListEmpty(key)) {
            return view.prepareFailedView(RightPopListError.LIST_ALREADY_EMPTY);
        }

        final String poppedElement = dataAccess.rightPop(key);

        return view.prepareSuccessfulView(poppedElement);
    }
}
