package name.tlphat.ministore.server.adapters;

import name.tlphat.ministore.server.persistence.dto.SnapshotWriterResponse;
import name.tlphat.ministore.server.persistence.dto.SnapshotWriterStatus;
import name.tlphat.ministore.server.persistence.ports.SnapshotWriterView;

public class SnapshotWriterViewImpl implements SnapshotWriterView {

  @Override
  public SnapshotWriterResponse prepareSuccessfulView() {
    return new SnapshotWriterResponse(SnapshotWriterStatus.OK, null);
  }
}
