package name.tlphat.ministore.server.persistence.ports;

import name.tlphat.ministore.server.persistence.dto.SnapshotWriterResponse;

public interface SnapshotWriterView {

  SnapshotWriterResponse prepareSuccessfulView();
}
