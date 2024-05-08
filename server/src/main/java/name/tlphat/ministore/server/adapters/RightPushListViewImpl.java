package name.tlphat.ministore.server.adapters;

import name.tlphat.ministore.server.usecases.constants.RightPushStringError;
import name.tlphat.ministore.server.usecases.ports.RightPushListView;

public class RightPushListViewImpl implements RightPushListView {

  @Override
  public String prepareSuccessfulView() {
    return "OK";
  }

  @Override
  public String prepareFailedView(RightPushStringError error) {
    return error.toString();
  }
}
