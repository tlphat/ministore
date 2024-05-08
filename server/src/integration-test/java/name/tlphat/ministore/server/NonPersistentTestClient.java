package name.tlphat.ministore.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class NonPersistentTestClient {

  private final String serverAddress;
  private final int serverPort;

  public NonPersistentTestClient(String serverAddress, int serverPort) {
    this.serverAddress = serverAddress;
    this.serverPort = serverPort;
  }

  public String sendCommand(String command) throws IOException {
    try (Socket socket = new Socket(serverAddress, serverPort);
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter serverWriter = new PrintWriter(outputStream, true);
        BufferedReader serverReader = new BufferedReader(new InputStreamReader(inputStream))) {
      serverWriter.println(command);

      String response = serverReader.readLine();

      final StringBuilder stringBuilder = new StringBuilder();
      boolean firstAppend = true;

      while (!" ".equals(response)) {
        if (firstAppend) {
          firstAppend = false;
        } else {
          stringBuilder.append('\n');
        }
        stringBuilder.append(response);

        response = serverReader.readLine();
      }

      return stringBuilder.toString();
    }
  }
}
