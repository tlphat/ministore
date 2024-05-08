package name.tlphat.ministore.server.controllers;

import name.tlphat.ministore.server.persistence.dto.SnapshotLoaderResponse;
import name.tlphat.ministore.server.persistence.dto.SnapshotWriterResponse;

public interface SnapshotController {

  SnapshotLoaderResponse load(String path);

  SnapshotWriterResponse write(String path);
}
