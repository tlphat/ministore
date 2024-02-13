package name.tlphat.ministore.server.usecases.ports;

public interface GetStringDataAccess {

    String get(String key);

    boolean isKeyExisted(String key);
}
