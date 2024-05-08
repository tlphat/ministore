package name.tlphat.ministore.server.usecases.ports;

public interface IncrementNumberDataAccess {

  void increment(String key);

  boolean isKeyExisted(String key);
}
