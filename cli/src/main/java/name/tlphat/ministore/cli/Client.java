package name.tlphat.ministore.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public void execute() {
        String serverAddress = "127.0.0.1"; // Replace with the server IP address
        int serverPort = 3078; // Replace with the server port number

        try (Socket socket = new Socket(serverAddress, serverPort);
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream();
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter serverWriter = new PrintWriter(outputStream, true);
             BufferedReader serverReader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            System.out.printf(String.format("Connected to the server %s:%d%n", serverAddress, serverPort));

            // Read messages from the console and send them to the server
            while (true) {
                System.out.print("> ");
                String message = consoleReader.readLine();

                // Send the message to the server
                serverWriter.println(message);

                // Exit the loop if the user enters 'quit'
                if ("exit".equalsIgnoreCase(message)) {
                    break;
                }

                // Read and display the response from the server
                String response = serverReader.readLine();
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Connection closed.");
    }
}
