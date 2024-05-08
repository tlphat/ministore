package name.tlphat.ministore.server.persistence.impl;

import name.tlphat.ministore.server.persistence.SnapshotLoader;
import name.tlphat.ministore.server.persistence.constants.SnapshotLoaderError;
import name.tlphat.ministore.server.persistence.dto.SnapshotLoaderResponse;
import name.tlphat.ministore.server.persistence.ports.SnapshotLoaderDataAccess;
import name.tlphat.ministore.server.persistence.ports.SnapshotLoaderView;

public class SnapshotLoaderImpl implements SnapshotLoader {

  private final SnapshotLoaderDataAccess dataAccess;
  private final SnapshotLoaderView view;

  public SnapshotLoaderImpl(SnapshotLoaderDataAccess dataAccess, SnapshotLoaderView view) {
    this.dataAccess = dataAccess;
    this.view = view;
  }

  @Override
  public SnapshotLoaderResponse load(String path) {
    if (dataAccess.failToConnect(path)) {
      return view.prepareFailedView(SnapshotLoaderError.STORAGE_CONNECT_FAILED);
    }

    dataAccess.load(path);

    return view.prepareSuccessfulView();
  }
}
