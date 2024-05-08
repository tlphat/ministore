package name.tlphat.ministore.server.app.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;
import name.tlphat.ministore.server.app.server.connection.ConnectionHandler;
import name.tlphat.ministore.server.app.server.connection.NonPersistentConnectionHandler;
import name.tlphat.ministore.server.app.server.executor.CommandExecutorFactory;
import name.tlphat.ministore.server.app.server.parser.CommandParser;
import name.tlphat.ministore.server.app.server.parser.CommandParserImpl;
import name.tlphat.ministore.server.controllers.DataController;
import name.tlphat.ministore.server.controllers.impl.DataControllerImpl;
import name.tlphat.ministore.server.store.DataStore;

@Slf4j
public class Server {

  private final CommandParser commandParser;
  private final CommandExecutorFactory commandExecutorFactory;

  private final ServerSocket serverSocket;

  public Server(DataStore dataStore, ServerSocket serverSocket) {
    this.serverSocket = serverSocket;

    commandParser = new CommandParserImpl();

    final DataController dataController = new DataControllerImpl(dataStore);
    commandExecutorFactory = new CommandExecutorFactory(dataController);
  }

  public void handleNewConnection() {
    try {
      final Socket socket = serverSocket.accept();
      final ConnectionHandler connectionHandler =
          new NonPersistentConnectionHandler(socket, commandParser, commandExecutorFactory);

      final Thread connectionHandlingThread = new Thread(connectionHandler::handle);
      connectionHandlingThread.start();
    } catch (IOException e) {
      log.error("Error while accepting new connection", e);
    }
  }
}
