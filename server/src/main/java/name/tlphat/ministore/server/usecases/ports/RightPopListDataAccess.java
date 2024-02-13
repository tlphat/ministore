package name.tlphat.ministore.server.usecases.ports;

public interface RightPopListDataAccess {

    boolean isKeyExisted(String key);

    boolean isListEmpty(String key);

    String rightPop(String key);

    void removeList(String key);
}
