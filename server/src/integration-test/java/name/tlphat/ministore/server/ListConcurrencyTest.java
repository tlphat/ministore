package name.tlphat.ministore.server;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ListConcurrencyTest {

    @BeforeAll
    static void setup() {
        final TestServer application = new TestServer(56788);
        application.start();
        await().until(application::isReady);
    }

    @AfterAll
    static void cleanup() {
        // TODO: shutdown the application
    }

    @Test
    void rightPushAndRightPopOperations() throws IOException, InterruptedException {
        final List<String> expected = List.of("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "NOT_EXISTED");

        // thread-per-core model
        int numberOfCores = Runtime.getRuntime().availableProcessors();
        final ExecutorService executorService = Executors.newFixedThreadPool(numberOfCores);

        final int numberOfThreads = 10;
        final List<PushTask> pushTasks = IntStream.range(0, numberOfThreads)
                                                  .mapToObj(ignored -> new PushTask())
                                                  .toList();
        executorService.invokeAll(pushTasks);

        final List<PopTask> popTasks = IntStream.range(0, numberOfThreads)
                                                .mapToObj(ignored -> new PopTask())
                                                .toList();
        final List<String> actual = new ArrayList<>();
        executorService.invokeAll(popTasks).forEach(future -> {
            try {
                actual.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new IllegalArgumentException();
            }
        });

        try (TestClient client = new TestClient()) {
            final ServerWrapper serverWrapper = new ServerWrapper(client.getReader(), client.getWriter());
            actual.add(serverWrapper.sendCommand("eget x 0"));
            serverWrapper.sendCommand("exit");
        }

        assertEquals(expected, actual);
    }

    private static class PushTask implements Callable<Void> {

        @Override
        public Void call() throws IOException {
            try (TestClient client = new TestClient()) {
                final ServerWrapper serverWrapper = new ServerWrapper(client.getReader(), client.getWriter());
                serverWrapper.sendCommand("rpush x 1");
                serverWrapper.sendCommand("exit");
                return null;
            }
        }
    }

    private static class PopTask implements Callable<String> {

        @Override
        public String call() throws IOException {
            try (TestClient client = new TestClient()) {
                final ServerWrapper serverWrapper = new ServerWrapper(client.getReader(), client.getWriter());
                final String response = serverWrapper.sendCommand("rpop x");
                serverWrapper.sendCommand("exit");
                return response;
            }
        }
    }
}
