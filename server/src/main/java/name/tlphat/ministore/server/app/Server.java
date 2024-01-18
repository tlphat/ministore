package name.tlphat.ministore.server.app;

import java.io.IOException;

public interface Server {

    void handleConnection() throws IOException;
}
