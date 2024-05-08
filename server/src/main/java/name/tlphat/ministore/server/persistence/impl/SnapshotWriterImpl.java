package name.tlphat.ministore.server.persistence.impl;

import name.tlphat.ministore.server.persistence.SnapshotWriter;
import name.tlphat.ministore.server.persistence.dto.SnapshotWriterResponse;
import name.tlphat.ministore.server.persistence.ports.SnapshotWriterDataAccess;
import name.tlphat.ministore.server.persistence.ports.SnapshotWriterView;

public class SnapshotWriterImpl implements SnapshotWriter {

  private final SnapshotWriterDataAccess dataAccess;
  private final SnapshotWriterView view;

  public SnapshotWriterImpl(SnapshotWriterDataAccess dataAccess, SnapshotWriterView view) {
    this.dataAccess = dataAccess;
    this.view = view;
  }

  @Override
  public SnapshotWriterResponse write(String path) {
    dataAccess.persistData(path);
    return view.prepareSuccessfulView();
  }
}
