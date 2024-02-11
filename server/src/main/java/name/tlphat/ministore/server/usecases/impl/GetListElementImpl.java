package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.GetListElementUseCase;
import name.tlphat.ministore.server.usecases.constants.GetListElementError;
import name.tlphat.ministore.server.usecases.ports.GetListElementDataAccess;
import name.tlphat.ministore.server.usecases.ports.GetListElementView;

public class GetListElementImpl implements GetListElementUseCase {

    private final GetListElementDataAccess dataAccess;
    private final GetListElementView view;

    public GetListElementImpl(
        GetListElementDataAccess dataAccess,
        GetListElementView view
    ) {
        this.dataAccess = dataAccess;
        this.view = view;
    }

    @Override
    public String getElementAtIndex(String key, int index) {
        if (indexOutOfLowerBound(index)) {
            return view.prepareFailedView(GetListElementError.INDEX_OUT_OF_BOUND);
        }

        if (!dataAccess.isListExisted(key)) {
            return view.prepareFailedView(GetListElementError.LIST_NOT_EXISTED);
        }

        if (indexOutOfUpperBound(key, index)) {
            return view.prepareFailedView(GetListElementError.INDEX_OUT_OF_BOUND);
        }

        final String element = dataAccess.getElementAtIndex(key, index);

        return view.prepareSuccessfulView(element);
    }

    private boolean indexOutOfLowerBound(int index) {
        return index < 0;
    }

    private boolean indexOutOfUpperBound(String key, int index) {
        return index >= dataAccess.getListSize(key);
    }
}
