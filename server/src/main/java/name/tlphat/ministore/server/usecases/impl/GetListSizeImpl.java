package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.GetListSizeUseCase;
import name.tlphat.ministore.server.usecases.constants.GetListSizeError;
import name.tlphat.ministore.server.usecases.ports.GetListSizeDataAccess;
import name.tlphat.ministore.server.usecases.ports.GetListSizeView;

public class GetListSizeImpl implements GetListSizeUseCase {

  private final GetListSizeDataAccess dataAccess;
  private final GetListSizeView view;

  public GetListSizeImpl(GetListSizeDataAccess dataAccess, GetListSizeView view) {
    this.dataAccess = dataAccess;
    this.view = view;
  }

  @Override
  public String getSize(String key) {
    if (!dataAccess.isListExisted(key)) {
      return view.prepareFailedView(GetListSizeError.NOT_EXISTED);
    }

    final int listSize = dataAccess.getListSize(key);
    return view.prepareSuccessfulView(listSize);
  }
}
