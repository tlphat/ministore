package name.tlphat.ministore.server.usecases.ports;

public interface DecrementNumberDataAccess {

    void decrement(String key);

    boolean isKeyExisted(String key);
}
