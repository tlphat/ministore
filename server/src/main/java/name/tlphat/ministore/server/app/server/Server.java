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

/**
 * Represents a server that accepts non-persistent client connections and handles them
 * asynchronously.
 */
@Slf4j
public class Server {

  private final CommandParser commandParser;
  private final CommandExecutorFactory commandExecutorFactory;

  private final ServerSocket serverSocket;

  /**
   * Constructs a Server object wrapping a parser, an executor generator, and a socket instance.
   *
   * @param dataStore non-null store instance
   * @param serverSocket non-null server socket
   */
  public Server(DataStore dataStore, ServerSocket serverSocket) {
    this.serverSocket = serverSocket;

    commandParser = new CommandParserImpl();

    final DataController dataController = new DataControllerImpl(dataStore);
    commandExecutorFactory = new CommandExecutorFactory(dataController);
  }

  /**
   * Listens to a new client connection and creates a new thread to handle it. This method uses
   * non-persistent handler, which closes the connection right after sending the response to client.
   * Also, this method got blocked until a new connection is accepted. Any IOException got thrown is
   * silently ignored, and an error will be logged.
   */
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
