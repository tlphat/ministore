package name.tlphat.ministore.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ServerWrapper {

    private final BufferedReader serverReader;
    private final PrintWriter serverWriter;

    public ServerWrapper(BufferedReader serverReader, PrintWriter serverWriter) {
        this.serverReader = serverReader;
        this.serverWriter = serverWriter;
    }

    public String sendCommand(String command) throws IOException {
        serverWriter.println(command);

        final StringBuilder response = new StringBuilder();
        String nextLine = serverReader.readLine();
        while (!" ". equals(nextLine)) {
            if (!response.isEmpty()) {
                response.append('\n');
            }
            response.append(nextLine);

            nextLine = serverReader.readLine();
        }
        return response.toString();
    }
}
