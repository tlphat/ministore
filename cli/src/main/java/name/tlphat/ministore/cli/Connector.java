package name.tlphat.ministore.cli;

public interface Connector {

    Connection connect(String serverIp, String serverPort);
}
