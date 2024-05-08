package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.DeleteStringUseCase;
import name.tlphat.ministore.server.usecases.constants.DeleteStringError;
import name.tlphat.ministore.server.usecases.ports.DeleteStringDataAccess;
import name.tlphat.ministore.server.usecases.ports.DeleteStringView;

public class DeleteStringImpl implements DeleteStringUseCase {

  private final DeleteStringDataAccess dataAccess;
  private final DeleteStringView view;

  public DeleteStringImpl(DeleteStringDataAccess dataAccess, DeleteStringView view) {
    this.dataAccess = dataAccess;
    this.view = view;
  }

  @Override
  public String delete(String key) {
    if (dataAccess.keyNotExisted(key)) {
      return view.prepareFailedView(DeleteStringError.NOT_EXISTED);
    }

    dataAccess.removeKey(key);

    return view.prepareSuccessfulView();
  }
}
