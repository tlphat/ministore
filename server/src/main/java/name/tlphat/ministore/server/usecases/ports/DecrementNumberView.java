package name.tlphat.ministore.server.usecases.ports;

import name.tlphat.ministore.server.usecases.constants.DecrementNumberError;

public interface DecrementNumberView {

    String prepareSuccessfulView();

    String prepareFailedView(DecrementNumberError error);
}
