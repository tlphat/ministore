package name.tlphat.ministore.server.persistence.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import name.tlphat.ministore.server.persistence.SnapshotLoader;
import name.tlphat.ministore.server.persistence.constants.SnapshotLoaderError;
import name.tlphat.ministore.server.persistence.dto.SnapshotLoaderResponse;
import name.tlphat.ministore.server.persistence.dto.SnapshotLoaderStatus;
import name.tlphat.ministore.server.persistence.ports.SnapshotLoaderDataAccess;
import name.tlphat.ministore.server.persistence.ports.SnapshotLoaderView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SnapshotLoaderImplTest {

  private SnapshotLoader loader;

  private DataAccessStub dataAccess;

  @BeforeEach
  void setup() {
    dataAccess = new DataAccessStub();
    final SnapshotLoaderView view = new ViewStub();

    loader = new SnapshotLoaderImpl(dataAccess, view);
  }

  @Test
  void returnOk() {
    final SnapshotLoaderResponse expected =
        new SnapshotLoaderResponse(SnapshotLoaderStatus.OK, null);
    final SnapshotLoaderResponse actual = loader.load("path");
    assertEquals(expected, actual);
  }

  @Test
  void dataGotLoaded() {
    loader.load("path");
    assertTrue(dataAccess.loaded);
  }

  @Test
  void returnErrorWhenStorageConnectionFailed() {
    final SnapshotLoaderResponse expected =
        new SnapshotLoaderResponse(SnapshotLoaderStatus.ERROR, "STORAGE_CONNECTION_FAILED");
    final SnapshotLoaderResponse actual = loader.load("error");
    assertEquals(expected, actual);
  }

  @Test
  void dataNotLoadedWhenStorageConnectionFailed() {
    loader.load("error");
    assertFalse(dataAccess.loaded);
  }

  private static class DataAccessStub implements SnapshotLoaderDataAccess {

    private boolean loaded;

    @Override
    public void load(String path) {
      loaded = true;
    }

    @Override
    public boolean failToConnect(String path) {
      return "error".equals(path);
    }
  }

  private static class ViewStub implements SnapshotLoaderView {

    @Override
    public SnapshotLoaderResponse prepareSuccessfulView() {
      return new SnapshotLoaderResponse(SnapshotLoaderStatus.OK, null);
    }

    @Override
    public SnapshotLoaderResponse prepareFailedView(SnapshotLoaderError error) {
      return new SnapshotLoaderResponse(SnapshotLoaderStatus.ERROR, "STORAGE_CONNECTION_FAILED");
    }
  }
}
