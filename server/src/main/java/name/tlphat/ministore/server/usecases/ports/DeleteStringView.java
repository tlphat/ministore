package name.tlphat.ministore.server.usecases.ports;

import name.tlphat.ministore.server.usecases.constants.DeleteStringError;

public interface DeleteStringView {

  String prepareSuccessfulView();

  String prepareFailedView(DeleteStringError error);
}
