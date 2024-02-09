package name.tlphat.ministore.server.usecases.ports;

import name.tlphat.ministore.server.usecases.constants.RightPushStringError;

public interface RightPushListView {

    String prepareSuccessfulView();

    String prepareFailedView(RightPushStringError error);
}
