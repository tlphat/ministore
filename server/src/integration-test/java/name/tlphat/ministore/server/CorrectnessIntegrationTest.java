package name.tlphat.ministore.server;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CorrectnessIntegrationTest {

    @BeforeAll
    static void setup() {
        final TestServer application = new TestServer(56789);
        application.start();
        await().until(application::isReady);
    }

    @AfterAll
    static void cleanup() {
        // TODO: shutdown the application
    }

    @Test
    @Order(1)
    void stringOperations() throws IOException {
        final String expected = "15";

        String actual;
        try (TestClient client = new TestClient()) {
            final ServerWrapper serverWrapper = new ServerWrapper(client.getReader(), client.getWriter());
            serverWrapper.sendCommand("set x 15");
            actual = serverWrapper.sendCommand("get x");
            serverWrapper.sendCommand("exit");
        }

        assertEquals(expected, actual);
    }

    @Test
    @Order(2)
    void listOperations() throws IOException {
        final String expected = "11";

        String actual;
        try (TestClient client = new TestClient()) {
            final ServerWrapper serverWrapper = new ServerWrapper(client.getReader(), client.getWriter());
            serverWrapper.sendCommand("rpush y 11");
            serverWrapper.sendCommand("rpush y 12");
            actual = serverWrapper.sendCommand("eget y 0");
            serverWrapper.sendCommand("rpop y");
            serverWrapper.sendCommand("exit");
        }

        assertEquals(expected, actual);
    }

    @Test
    @Order(3)
    void getKeys() throws IOException {
        final String expected = "1)\t\tx\t\tSINGLETON\n2)\t\ty\t\tLIST";

        String actual;
        try (TestClient client = new TestClient()) {
            final ServerWrapper serverWrapper = new ServerWrapper(client.getReader(), client.getWriter());
            actual = serverWrapper.sendCommand("keys *");
            serverWrapper.sendCommand("del x");
            serverWrapper.sendCommand("rpop y");
            serverWrapper.sendCommand("exit");
        }

        assertEquals(expected, actual);
    }
}
