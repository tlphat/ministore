package name.tlphat.ministore.cli.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class PersistentClient implements Client {

    @Override
    public void execute(String serverAddress, int serverPort) {
        try (Socket socket = new Socket(serverAddress, serverPort);
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream();
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter serverWriter = new PrintWriter(outputStream, true);
             BufferedReader serverReader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            System.out.printf(String.format("Connected to the server %s:%d%n", serverAddress, serverPort)); // NOSONAR

            // Read messages from the console and send them to the server
            while (true) {
                System.out.print("> "); // NOSONAR
                String message = consoleReader.readLine();

                // Send the message to the server
                serverWriter.println(message);

                // Exit the loop if the user enters 'quit'
                if ("exit".equalsIgnoreCase(message)) {
                    break;
                }

                // Read and display the response from the server
                String response = serverReader.readLine();
                while (!" ". equals(response)) {
                    System.out.println(response); // NOSONAR
                    response = serverReader.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Connection closed."); // NOSONAR
    }
}
