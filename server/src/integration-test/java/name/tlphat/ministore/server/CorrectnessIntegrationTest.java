package name.tlphat.ministore.server;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CorrectnessIntegrationTest {

  private static NonPersistentTestClient client;

  private static final String TEST_SERVER_ADDRESS = "127.0.0.1";
  private static final int TEST_SERVER_PORT = 56789;

  @BeforeAll
  static void setup() {
    final TestServer application = new TestServer(TEST_SERVER_PORT);
    application.start();
    await().until(application::isReady);

    client = new NonPersistentTestClient(TEST_SERVER_ADDRESS, TEST_SERVER_PORT);
  }

  @AfterAll
  static void cleanup() {
    // TODO: shutdown the application
  }

  @Test
  @Order(1)
  void stringOperations() throws IOException {
    final String expected = "15";

    client.sendCommand("set x 15");
    final String actual = client.sendCommand("get x");

    assertEquals(expected, actual);
  }

  @Test
  @Order(2)
  void listOperations() throws IOException {
    final String expected = "11";

    client.sendCommand("rpush y 11");
    client.sendCommand("rpush y 12");
    final String actual = client.sendCommand("eget y 0");
    client.sendCommand("rpop y");

    assertEquals(expected, actual);
  }

  @Test
  @Order(3)
  void getKeys() throws IOException {
    final String expected = "1)\t\tx\t\tSINGLETON\n2)\t\ty\t\tLIST";

    final String actual = client.sendCommand("keys *");
    client.sendCommand("del x");
    client.sendCommand("rpop y");

    assertEquals(expected, actual);
  }
}
