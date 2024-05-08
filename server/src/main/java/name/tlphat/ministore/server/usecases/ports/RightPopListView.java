package name.tlphat.ministore.server.usecases.ports;

import name.tlphat.ministore.server.usecases.constants.RightPopListError;

public interface RightPopListView {

  String prepareSuccessfulView(String data);

  String prepareFailedView(RightPopListError error);
}
