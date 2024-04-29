package name.tlphat.ministore.cli;

import name.tlphat.ministore.cli.client.Client;
import name.tlphat.ministore.cli.client.NonPersistentClient;

public class Application {

    public static void main(String[] args) {
        final Client client = new NonPersistentClient();
        client.execute("127.0.0.1", 3078);
    }
}
