package name.tlphat.ministore.server.usecases.ports;

import name.tlphat.ministore.server.usecases.constants.SetStringError;

public interface SetStringView {

    String prepareSuccessfulView();
    String prepareErrorView(SetStringError error);
}
