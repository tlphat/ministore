package name.tlphat.ministore.server.usecases.ports;

import name.tlphat.ministore.server.usecases.constants.GetListElementError;

public interface GetListElementView {

  String prepareSuccessfulView(String data);

  String prepareFailedView(GetListElementError error);
}
