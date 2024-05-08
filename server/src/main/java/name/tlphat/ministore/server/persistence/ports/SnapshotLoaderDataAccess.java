package name.tlphat.ministore.server.persistence.ports;

public interface SnapshotLoaderDataAccess {

  void load(String path);

  boolean failToConnect(String path);
}
