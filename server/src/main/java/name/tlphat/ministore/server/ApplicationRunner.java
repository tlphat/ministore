package name.tlphat.ministore.server;

import lombok.extern.slf4j.Slf4j;
import name.tlphat.ministore.server.app.Server;
import name.tlphat.ministore.server.controllers.SnapshotController;
import name.tlphat.ministore.server.controllers.impl.SnapshotControllerImpl;
import name.tlphat.ministore.server.persistence.dto.SnapshotLoaderResponse;
import name.tlphat.ministore.server.persistence.dto.SnapshotLoaderStatus;
import name.tlphat.ministore.server.persistence.dto.SnapshotWriterResponse;
import name.tlphat.ministore.server.persistence.dto.SnapshotWriterStatus;
import name.tlphat.ministore.server.store.DataStore;
import name.tlphat.ministore.server.store.impl.InMemoryStore;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ApplicationRunner {

    private static final int SERVER_PORT = 3078;
    private static final String SNAPSHOT_PATH = "./snapshot.txt";
    private static final long SNAPSHOT_WRITE_DURATION_SECOND = 10;

    private final DataStore dataStore;
    private final SnapshotController snapshotController;

    public ApplicationRunner() {
        dataStore = new InMemoryStore();
        snapshotController = new SnapshotControllerImpl(dataStore);
    }

    public void run() {
        log.info("Application is starting");

        loadingSnapshot();
        scheduleWritingSnapshot();

        try (final ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            final Server server = new Server(dataStore, serverSocket);
            log.info("Server is ready to serve connections");

            //noinspection InfiniteLoopStatement
            while (true) {
                // This method blocks until accepting a new connection,
                // then spawn a new thread to handle that connection
                server.handleNewConnection();
            }
        } catch (IOException e) {
            log.error("Error while setting up the server", e);
            System.exit(1);
        }
    }

    private void loadingSnapshot() {
        final SnapshotLoaderResponse result = snapshotController.load(SNAPSHOT_PATH);

        if (result.status() == SnapshotLoaderStatus.OK) {
            log.info("Loaded snapshot from disk successfully");
        } else {
            log.warn("Error loading snapshot from disk {}. Initiated server with empty data.", result.message());
        }
    }

    private void scheduleWritingSnapshot() {
        final ScheduledExecutorService writerExecutor = new ScheduledThreadPoolExecutor(1);
        writerExecutor.scheduleAtFixedRate(
            new SnapshotWritingRunnable(),
            SNAPSHOT_WRITE_DURATION_SECOND,
            SNAPSHOT_WRITE_DURATION_SECOND,
            TimeUnit.SECONDS
        );
    }

    private class SnapshotWritingRunnable implements Runnable {

        @Override
        public void run() {
            final SnapshotWriterResponse result = snapshotController.write(SNAPSHOT_PATH);

            if (result.status() == SnapshotWriterStatus.OK) {
                log.info("Wrote snapshot to disk successfully");
            } else {
                log.warn("Error writing snapshot to disk {}. Will try again in next event", result.message());
            }
        }
    }
}
