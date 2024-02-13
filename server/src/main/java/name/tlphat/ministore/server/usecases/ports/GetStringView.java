package name.tlphat.ministore.server.usecases.ports;

import name.tlphat.ministore.server.usecases.constants.GetStringError;

public interface GetStringView {

    String prepareSuccessfulView(String response);

    String prepareFailedView(GetStringError error);
}
