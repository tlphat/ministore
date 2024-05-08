package name.tlphat.ministore.server.persistence;

import name.tlphat.ministore.server.persistence.dto.SnapshotWriterResponse;

public interface SnapshotWriter {

  SnapshotWriterResponse write(String path);
}
