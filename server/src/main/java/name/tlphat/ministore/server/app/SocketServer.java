package name.tlphat.ministore.server.app;

import lombok.extern.slf4j.Slf4j;
import name.tlphat.ministore.server.app.dto.CommandType;
import name.tlphat.ministore.server.app.dto.Tokens;
import name.tlphat.ministore.server.app.executor.CommandExecutor;
import name.tlphat.ministore.server.app.executor.CommandExecutorFactory;
import name.tlphat.ministore.server.app.parser.CommandParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

@Slf4j
public class SocketServer implements Server, AutoCloseable {

    private final CommandParser commandParser;
    private final CommandExecutorFactory commandExecutorFactory;

    private final ServerSocket serverSocket;
    private final String internalErrorMessage;

    public SocketServer(
        CommandParser commandParser,
        CommandExecutorFactory commandExecutorFactory,
        int port,
        String internalErrorMessage
    ) throws IOException {

        this.commandParser = commandParser;
        this.commandExecutorFactory = commandExecutorFactory;
        serverSocket = new ServerSocket(port);
        this.internalErrorMessage = internalErrorMessage;
    }

    private boolean receivedExitCommand = false;

    @Override
    public void handleConnection() throws IOException {
        try (final Socket socket = serverSocket.accept()) {
            final SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
            log.info("Connection received, handling connection from remote address {}", remoteSocketAddress);

            while (!receivedExitCommand) {
                final String command = readCommand(socket);
                log.info("Command received: {}", command);

                final Tokens tokens = commandParser.parse(command);
                log.info("Token parsed: {}", tokens);
                turnOnExitFlagUponReceiveExitCommand(tokens);

                try {
                    final String response = execute(tokens);
                    log.info("Response: {}", response);

                    sendResponse(socket, response);
                } catch (Exception ex) {
                    log.error("Error executing command {}", command, ex);

                    sendResponse(socket, internalErrorMessage);
                }
            }

            log.info("Closing connection from remote address {}", remoteSocketAddress);
        }
    }

    private static String readCommand(Socket socket) throws IOException {
        final InputStream inputStream = socket.getInputStream();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return bufferedReader.readLine();
    }

    private void turnOnExitFlagUponReceiveExitCommand(Tokens tokens) {
        if (tokens.commandType() == CommandType.EXIT) {
            receivedExitCommand = true;
        }
    }

    private String execute(Tokens tokens) {
        final CommandExecutor commandExecutor = commandExecutorFactory.getCommandExecutor(tokens.commandType());
        return commandExecutor.execute(tokens);
    }

    private static void sendResponse(Socket socket, String response) throws IOException {
        final OutputStream outputStream = socket.getOutputStream();
        final PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.println(response);
        printWriter.flush();
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }
}
