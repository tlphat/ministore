package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.entities.BasicString;
import name.tlphat.ministore.server.entities.String;
import name.tlphat.ministore.server.usecases.RightPushListUseCase;
import name.tlphat.ministore.server.usecases.constants.RightPushStringError;
import name.tlphat.ministore.server.usecases.ports.RightPushListDataAccess;
import name.tlphat.ministore.server.usecases.ports.RightPushListView;

public class RightPushListImpl implements RightPushListUseCase {

  private final RightPushListDataAccess dataAccess;
  private final RightPushListView view;
  private final int maxNumberOfCharacters;

  public RightPushListImpl(
      RightPushListDataAccess dataAccess, RightPushListView view, int maxNumberOfCharacters) {
    this.dataAccess = dataAccess;
    this.view = view;
    this.maxNumberOfCharacters = maxNumberOfCharacters;
  }

  @Override
  public java.lang.String rightPush(java.lang.String key, java.lang.String value) {
    // Check if value exceeds the limited number of characters
    final String stringValue = new BasicString(value);
    if (stringValue.exceedsSizeLimit(maxNumberOfCharacters)) {
      return view.prepareFailedView(RightPushStringError.VALUE_SIZE_TOO_LARGE);
    }

    dataAccess.appendRightToList(key, value);

    return view.prepareSuccessfulView();
  }
}
