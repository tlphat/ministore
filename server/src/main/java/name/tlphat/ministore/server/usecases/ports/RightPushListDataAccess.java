package name.tlphat.ministore.server.usecases.ports;

public interface RightPushListDataAccess {

    void appendRightToList(String key, String value);
}
