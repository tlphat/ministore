package name.tlphat.ministore.server.adapters;

import name.tlphat.ministore.server.usecases.constants.SetStringError;
import name.tlphat.ministore.server.usecases.ports.SetStringView;

public class SetStringViewImpl implements SetStringView {

  @Override
  public String prepareSuccessfulView() {
    return "OK";
  }

  @Override
  public String prepareErrorView(SetStringError error) {
    return error.toString();
  }
}
