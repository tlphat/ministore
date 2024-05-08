package name.tlphat.ministore.server.adapters;

import name.tlphat.ministore.server.usecases.constants.RightPopListError;
import name.tlphat.ministore.server.usecases.ports.RightPopListView;

public class RightPopListViewImpl implements RightPopListView {

  @Override
  public String prepareSuccessfulView(String data) {
    return data;
  }

  @Override
  public String prepareFailedView(RightPopListError error) {
    return error.toString();
  }
}
