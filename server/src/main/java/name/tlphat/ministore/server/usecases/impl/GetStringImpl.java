package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.GetStringUseCase;
import name.tlphat.ministore.server.usecases.constants.GetStringError;
import name.tlphat.ministore.server.usecases.ports.GetStringDataAccess;
import name.tlphat.ministore.server.usecases.ports.GetStringView;

public class GetStringImpl implements GetStringUseCase {

  private final GetStringDataAccess dataAccess;
  private final GetStringView view;

  public GetStringImpl(GetStringDataAccess dataAccess, GetStringView view) {
    this.dataAccess = dataAccess;
    this.view = view;
  }

  @Override
  public String get(String key) {
    if (!dataAccess.isKeyExisted(key)) {
      return view.prepareFailedView(GetStringError.NOT_EXISTED);
    }

    final String response = dataAccess.get(key);

    return view.prepareSuccessfulView(response);
  }
}
