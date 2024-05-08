package name.tlphat.ministore.server.persistence.ports;

public interface SnapshotWriterDataAccess {

  void persistData(String path);
}
