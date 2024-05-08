package name.tlphat.ministore.server.usecases.ports;

public interface DeleteStringDataAccess {

  void removeKey(String key);

  boolean keyNotExisted(String key);
}
