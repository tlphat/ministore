package name.tlphat.ministore.server.persistence.impl;

import name.tlphat.ministore.server.persistence.SnapshotWriter;
import name.tlphat.ministore.server.persistence.dto.SnapshotWriterResponse;
import name.tlphat.ministore.server.persistence.dto.SnapshotWriterStatus;
import name.tlphat.ministore.server.persistence.ports.SnapshotWriterDataAccess;
import name.tlphat.ministore.server.persistence.ports.SnapshotWriterView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SnapshotWriterImplTest {

    private SnapshotWriter writer;

    private DataAccessStub dataAccess;

    @BeforeEach
    void setup() {
        dataAccess = new DataAccessStub();
        final SnapshotWriterView view = new ViewStub();
        writer = new SnapshotWriterImpl(dataAccess, view);
    }

    @Test
    void returnOk() {
        final SnapshotWriterResponse expected = new SnapshotWriterResponse(SnapshotWriterStatus.OK, null);
        final SnapshotWriterResponse actual = writer.write("path");
        assertEquals(expected, actual);
    }

    @Test
    void dataGotPersisted() {
        writer.write("path");
        assertTrue(dataAccess.persisted);
    }

    private static class DataAccessStub implements SnapshotWriterDataAccess {

        private boolean persisted;

        @Override
        public void persistData(String path) {
            persisted = true;
        }
    }

    private static class ViewStub implements SnapshotWriterView {

        @Override
        public SnapshotWriterResponse prepareSuccessfulView() {
            return new SnapshotWriterResponse(SnapshotWriterStatus.OK, null);
        }
    }
}
