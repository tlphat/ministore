package name.tlphat.ministore.server;

import lombok.extern.slf4j.Slf4j;
import name.tlphat.ministore.server.app.executor.CommandExecutorFactory;
import name.tlphat.ministore.server.app.parser.CommandParser;
import name.tlphat.ministore.server.app.parser.CommandParserImpl;
import name.tlphat.ministore.server.app.SocketServer;
import name.tlphat.ministore.server.controllers.DataController;
import name.tlphat.ministore.server.controllers.impl.DataControllerImpl;
import name.tlphat.ministore.server.store.DataStore;
import name.tlphat.ministore.server.store.impl.InMemoryStore;

import java.io.IOException;

@Slf4j
public class Application {

    private static final int SERVER_PORT = 3078;
    private static final String INTERNAL_ERROR_MESSAGE = "INTERNAL_ERROR";

    public static void main(String[] args) {
        log.info("Application is starting");

        final CommandParser commandParser = new CommandParserImpl();

        final DataStore dataStore = new InMemoryStore();
        final DataController dataController = new DataControllerImpl(dataStore);
        final CommandExecutorFactory commandExecutorFactory = new CommandExecutorFactory(dataController);

        try (final SocketServer server = new SocketServer(commandParser, commandExecutorFactory, SERVER_PORT, INTERNAL_ERROR_MESSAGE)) {
            log.info("Server is ready to serve connections");

            //noinspection InfiniteLoopStatement
            while (true) {
                server.handleConnection();
            }
        } catch (IOException e) {
            log.error("Error while running application", e);
            System.exit(1);
        } catch (Exception e) {
            log.error("Operation not supported");
        }
    }
}
