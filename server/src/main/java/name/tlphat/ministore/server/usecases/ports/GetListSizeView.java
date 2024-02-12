package name.tlphat.ministore.server.usecases.ports;

import name.tlphat.ministore.server.usecases.constants.GetListSizeError;

public interface GetListSizeView {

    String prepareSuccessfulView(int size);

    String prepareFailedView(GetListSizeError error);
}
