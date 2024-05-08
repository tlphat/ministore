package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.DecrementNumberUseCase;
import name.tlphat.ministore.server.usecases.constants.DecrementNumberError;
import name.tlphat.ministore.server.usecases.ports.DecrementNumberDataAccess;
import name.tlphat.ministore.server.usecases.ports.DecrementNumberView;

public class DecrementNumberImpl implements DecrementNumberUseCase {

  private final DecrementNumberDataAccess dataAccess;
  private final DecrementNumberView view;

  public DecrementNumberImpl(DecrementNumberDataAccess dataAccess, DecrementNumberView view) {
    this.dataAccess = dataAccess;
    this.view = view;
  }

  @Override
  public String decrement(String key) {
    if (!dataAccess.isKeyExisted(key)) {
      return view.prepareFailedView(DecrementNumberError.NOT_EXISTED);
    }

    dataAccess.decrement(key);

    return view.prepareSuccessfulView();
  }
}
