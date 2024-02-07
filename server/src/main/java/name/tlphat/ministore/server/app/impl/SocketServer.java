package name.tlphat.ministore.server.app.impl;

import lombok.extern.slf4j.Slf4j;
import name.tlphat.ministore.server.app.CommandExecutor;
import name.tlphat.ministore.server.app.CommandParser;
import name.tlphat.ministore.server.app.Server;
import name.tlphat.ministore.server.app.dto.CommandType;
import name.tlphat.ministore.server.app.dto.Tokens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class SocketServer implements Server, AutoCloseable {

    private final CommandParser commandParser;
    private final CommandExecutor commandExecutor;

    private final ServerSocket serverSocket;

    public SocketServer(
        CommandParser commandParser,
        CommandExecutor commandExecutor,
        int port
    ) throws IOException {
        this.commandParser = commandParser;
        this.commandExecutor = commandExecutor;
        serverSocket = new ServerSocket(port);
    }

    private boolean receivedExitCommand = false;

    @Override
    public void handleConnection() throws IOException {
        try (final Socket socket = serverSocket.accept()) {
            while (!receivedExitCommand) {
                log.info("Connection received, handling the connection");

                final InputStream inputStream = socket.getInputStream();
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                final String command = bufferedReader.readLine();

                log.info("Command received: {}", command);

                final Tokens tokens = commandParser.parse(command);
                log.info("Token parsed: {}", tokens);
                turnOnExitFlagUponReceiveExitCommand(tokens);

                final String response = commandExecutor.execute(tokens);
                log.info("Response: {}", response);

                final OutputStream outputStream = socket.getOutputStream();
                final PrintWriter printWriter = new PrintWriter(outputStream);
                printWriter.println(response);
                printWriter.flush();
            }
        }
    }

    private void turnOnExitFlagUponReceiveExitCommand(Tokens tokens) {
        if (tokens.commandType() == CommandType.EXIT) {
            receivedExitCommand = true;
        }
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }
}
