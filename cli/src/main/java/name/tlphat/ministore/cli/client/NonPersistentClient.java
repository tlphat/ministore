package name.tlphat.ministore.cli.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class NonPersistentClient implements Client {

    private static final String PROMPT_MESSAGE = "> ";
    private static final String EXIT_COMMAND = "exit";
    private static final String SERVER_RESPONSE_END = " ";

    @Override
    public void execute(String serverAddress, int serverPort) {
        try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print(PROMPT_MESSAGE); // NOSONAR
            String message = consoleReader.readLine();

            while (!EXIT_COMMAND.equals(message)) {
                try (Socket socket = new Socket(serverAddress, serverPort);
                     InputStream inputStream = socket.getInputStream();
                     OutputStream outputStream = socket.getOutputStream();
                     PrintWriter serverWriter = new PrintWriter(outputStream, true);
                     BufferedReader serverReader = new BufferedReader(new InputStreamReader(inputStream))
                ) {
                    // Send the message to the server
                    serverWriter.println(message);

                    // Read and display the response from the server
                    String response = serverReader.readLine();
                    while (!SERVER_RESPONSE_END.equals(response)) {
                        System.out.println(response); // NOSONAR
                        response = serverReader.readLine();
                    }
                }

                System.out.print(PROMPT_MESSAGE); // NOSONAR
                message = consoleReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
