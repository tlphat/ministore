package name.tlphat.ministore.server;

import lombok.extern.slf4j.Slf4j;
import name.tlphat.ministore.server.app.CommandExecutor;
import name.tlphat.ministore.server.app.CommandParser;
import name.tlphat.ministore.server.app.impl.CommandExecutorImpl;
import name.tlphat.ministore.server.app.impl.CommandParserImpl;
import name.tlphat.ministore.server.app.impl.SocketServer;
import name.tlphat.ministore.server.controllers.DataController;
import name.tlphat.ministore.server.controllers.impl.DataControllerImpl;
import name.tlphat.ministore.server.store.DataStore;
import name.tlphat.ministore.server.store.impl.InMemoryStore;

import java.io.IOException;

@Slf4j
public class Application {

    private static final int SERVER_PORT = 3078;

    public static void main(String[] args) {
        log.info("Application is starting");

        final CommandParser commandParser = new CommandParserImpl();

        final DataStore dataStore = new InMemoryStore();
        final DataController dataController = new DataControllerImpl(dataStore);
        final CommandExecutor commandExecutor = new CommandExecutorImpl(dataController);

        try (final SocketServer server = new SocketServer(commandParser, commandExecutor, SERVER_PORT)) {
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
