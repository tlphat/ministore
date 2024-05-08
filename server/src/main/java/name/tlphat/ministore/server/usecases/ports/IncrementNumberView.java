package name.tlphat.ministore.server.usecases.ports;

import name.tlphat.ministore.server.usecases.constants.IncrementNumberError;

public interface IncrementNumberView {

  String prepareSuccessfulView();

  String prepareFailedView(IncrementNumberError error);
}
