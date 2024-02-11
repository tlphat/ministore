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
        if (!dataAccess.isListExisted(key)) {
            return view.prepareFailedView(GetListElementError.LIST_NOT_EXISTED);
        }

        if (index < 0) {
            return view.prepareFailedView(GetListElementError.INDEX_OUT_OF_BOUND);
        }

        final int listSize = dataAccess.getListSize(key);
        if (index >= listSize) {
            return view.prepareFailedView(GetListElementError.INDEX_OUT_OF_BOUND);
        }

        final String element = dataAccess.getElementAtIndex(key, index);

        return view.prepareSuccessfulView(element);
    }
}
