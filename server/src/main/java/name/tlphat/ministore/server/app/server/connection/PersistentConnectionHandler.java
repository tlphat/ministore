package name.tlphat.ministore.server.app.server.connection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import name.tlphat.ministore.server.app.server.connection.ConnectionHandler;
import name.tlphat.ministore.server.app.server.dto.CommandType;
import name.tlphat.ministore.server.app.server.dto.Error;
import name.tlphat.ministore.server.app.server.dto.Tokens;
import name.tlphat.ministore.server.app.server.executor.CommandExecutor;
import name.tlphat.ministore.server.app.server.executor.CommandExecutorFactory;
import name.tlphat.ministore.server.app.server.parser.CommandParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

@Slf4j
@RequiredArgsConstructor
public class PersistentConnectionHandler implements ConnectionHandler {

    private final Socket socket;
    private final CommandParser commandParser;
    private final CommandExecutorFactory commandExecutorFactory;

    @Override
    public void handle() {
        final SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
        log.info("Connection received, handling connection from remote address {}", remoteSocketAddress);

        readAndExecuteCommand(remoteSocketAddress);

        closeConnection(remoteSocketAddress);
    }

    private void readAndExecuteCommand(SocketAddress remoteSocketAddress) {
        try {
            boolean receivedExitCommand = false;

            while (!receivedExitCommand) {
                final String command = readCommand(socket);
                log.info("Command received: {}", command);

                final Tokens tokens = commandParser.parse(command);
                log.info("Token parsed: {}", tokens);

                receivedExitCommand = (tokens.commandType() == CommandType.EXIT);

                executeCommand(socket, command, tokens);
            }
        } catch (IOException e) {
            log.error("Error while handling connection from remote address {}", remoteSocketAddress, e);
        }
    }

    private void closeConnection(SocketAddress remoteSocketAddress) {
        log.info("Closing connection from remote address {}", remoteSocketAddress);
        try {
            socket.close();
        } catch (IOException e) {
            log.error("Error while closing the connection from remote address {}", remoteSocketAddress, e);
        }
    }

    private static String readCommand(Socket socket) throws IOException {
        final InputStream inputStream = socket.getInputStream();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return bufferedReader.readLine();
    }

    private void executeCommand(Socket socket, String command, Tokens tokens) throws IOException {
        try {
            final CommandExecutor commandExecutor = commandExecutorFactory.getCommandExecutor(tokens.commandType());
            final String response = commandExecutor.execute(tokens);

            log.info("Response: {}", response);

            sendResponse(socket, response);
        } catch (Exception ex) {
            log.error("Error executing command {}", command, ex);

            sendResponse(socket, Error.INTERNAL_ERROR.toString());
        }
    }

    private static final String TERMINATED_SPACE = " ";

    private static void sendResponse(Socket socket, String response) throws IOException {
        final OutputStream outputStream = socket.getOutputStream();
        final PrintWriter printWriter = new PrintWriter(outputStream);
        response.lines().forEach(printWriter::println);
        printWriter.println(TERMINATED_SPACE);
        printWriter.flush();
    }
}
