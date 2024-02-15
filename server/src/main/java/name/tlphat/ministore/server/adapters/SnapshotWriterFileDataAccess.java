package name.tlphat.ministore.server.adapters;

import com.sun.jdi.InternalException;
import name.tlphat.ministore.server.persistence.ports.SnapshotWriterDataAccess;
import name.tlphat.ministore.server.store.DataStore;

import java.io.FileOutputStream;
import java.io.IOException;

public class SnapshotWriterFileDataAccess implements SnapshotWriterDataAccess {

    private final DataStore dataStore;

    public SnapshotWriterFileDataAccess(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void persistData(String path) {
        try (final FileOutputStream outputStream = new FileOutputStream(path)) {
            final byte[] serializedData = dataStore.serializeData();
            outputStream.write(serializedData);
            outputStream.flush();
        } catch (IOException e) {
            throw new InternalException();
        }
    }
}
