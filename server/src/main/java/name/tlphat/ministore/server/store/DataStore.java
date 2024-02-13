package name.tlphat.ministore.server.store;

public interface DataStore {

    String getValue(String key);

    boolean setString(String key, String value);

    boolean appendToList(String key, String value);

    boolean isListExisted(String key);

    int getListSize(String key);

    String getAndPopRightmostElement(String key);

    boolean isStringExisted(String key);
}
