package name.tlphat.ministore.server.controllers.impl;

import name.tlphat.ministore.server.adapters.SnapshotLoaderFileDataAccess;
import name.tlphat.ministore.server.adapters.SnapshotLoaderViewImpl;
import name.tlphat.ministore.server.adapters.SnapshotWriterFileDataAccess;
import name.tlphat.ministore.server.adapters.SnapshotWriterViewImpl;
import name.tlphat.ministore.server.controllers.SnapshotController;
import name.tlphat.ministore.server.persistence.SnapshotLoader;
import name.tlphat.ministore.server.persistence.SnapshotWriter;
import name.tlphat.ministore.server.persistence.dto.SnapshotLoaderResponse;
import name.tlphat.ministore.server.persistence.dto.SnapshotWriterResponse;
import name.tlphat.ministore.server.persistence.impl.SnapshotLoaderImpl;
import name.tlphat.ministore.server.persistence.impl.SnapshotWriterImpl;
import name.tlphat.ministore.server.persistence.ports.SnapshotLoaderDataAccess;
import name.tlphat.ministore.server.persistence.ports.SnapshotLoaderView;
import name.tlphat.ministore.server.persistence.ports.SnapshotWriterDataAccess;
import name.tlphat.ministore.server.persistence.ports.SnapshotWriterView;
import name.tlphat.ministore.server.store.DataStore;

public class SnapshotControllerImpl implements SnapshotController {

    private final DataStore dataStore;

    public SnapshotControllerImpl(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public SnapshotLoaderResponse load(String path) {
        final SnapshotLoaderDataAccess dataAccess = new SnapshotLoaderFileDataAccess(dataStore);
        final SnapshotLoaderView view = new SnapshotLoaderViewImpl();
        final SnapshotLoader loader = new SnapshotLoaderImpl(dataAccess, view);
        return loader.load(path);
    }

    @Override
    public SnapshotWriterResponse write(String path) {
        final SnapshotWriterDataAccess dataAccess = new SnapshotWriterFileDataAccess(dataStore);
        final SnapshotWriterView view = new SnapshotWriterViewImpl();
        final SnapshotWriter writer = new SnapshotWriterImpl(dataAccess, view);
        return writer.write(path);
    }
}
