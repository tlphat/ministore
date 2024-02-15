package name.tlphat.ministore.server.adapters;

import name.tlphat.ministore.server.persistence.constants.SnapshotLoaderError;
import name.tlphat.ministore.server.persistence.dto.SnapshotLoaderResponse;
import name.tlphat.ministore.server.persistence.dto.SnapshotLoaderStatus;
import name.tlphat.ministore.server.persistence.ports.SnapshotLoaderView;

public class SnapshotLoaderViewImpl implements SnapshotLoaderView {

    @Override
    public SnapshotLoaderResponse prepareSuccessfulView() {
        return new SnapshotLoaderResponse(
            SnapshotLoaderStatus.OK,
            null
        );
    }

    @Override
    public SnapshotLoaderResponse prepareFailedView(SnapshotLoaderError error) {
        return new SnapshotLoaderResponse(
            SnapshotLoaderStatus.ERROR,
            error.toString()
        );
    }
}
