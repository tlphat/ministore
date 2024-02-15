package name.tlphat.ministore.server.persistence;

import name.tlphat.ministore.server.persistence.dto.SnapshotLoaderResponse;

public interface SnapshotLoader {

    SnapshotLoaderResponse load(String path);
}
