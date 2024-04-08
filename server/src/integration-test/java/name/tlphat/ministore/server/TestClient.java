package name.tlphat.ministore.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class TestClient implements AutoCloseable {

    private final Socket socket;
    private final PrintWriter writer;
    private final BufferedReader reader;

    public TestClient() throws IOException {
        final String serverAddress = "127.0.0.1";
        final int serverPort = 56789;

        socket = new Socket(serverAddress, serverPort);

        final InputStream inputStream = socket.getInputStream();
        reader = new BufferedReader(new InputStreamReader(inputStream));

        final OutputStream outputStream = socket.getOutputStream();
        writer = new PrintWriter(outputStream, true);
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public BufferedReader getReader() {
        return reader;
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
