package name.tlphat.ministore.server.usecases.ports;

public interface GetListSizeDataAccess {

  int getListSize(String key);

  boolean isListExisted(String key);
}
