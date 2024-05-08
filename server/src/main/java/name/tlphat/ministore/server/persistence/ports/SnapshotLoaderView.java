package name.tlphat.ministore.server.persistence.ports;

import name.tlphat.ministore.server.persistence.constants.SnapshotLoaderError;
import name.tlphat.ministore.server.persistence.dto.SnapshotLoaderResponse;

public interface SnapshotLoaderView {

  SnapshotLoaderResponse prepareSuccessfulView();

  SnapshotLoaderResponse prepareFailedView(SnapshotLoaderError error);
}
