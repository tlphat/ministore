package name.tlphat.ministore.server.usecases.ports;

public interface GetListElementDataAccess {

  boolean isListExisted(String key);

  int getListSize(String key);

  String getElementAtIndex(String key, int index);
}
