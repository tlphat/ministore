package name.tlphat.ministore.server.adapters;

import name.tlphat.ministore.server.persistence.ports.SnapshotLoaderDataAccess;
import name.tlphat.ministore.server.store.DataStore;

import java.io.FileInputStream;
import java.io.IOException;

public class SnapshotLoaderFileDataAccess implements SnapshotLoaderDataAccess {

    private final DataStore dataStore;

    public SnapshotLoaderFileDataAccess(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void load(String path) {
        try (final FileInputStream inputStream = new FileInputStream(path)) {
            final byte[] data = new byte[inputStream.available()];
            //noinspection ResultOfMethodCallIgnored
            inputStream.read(data); // NOSONAR
            dataStore.deserializeFromData(data);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean failToConnect(String path) {
        try (final FileInputStream ignored = new FileInputStream(path)) {
            return false;
        } catch (IOException e) {
            return true;
        }
    }
}
