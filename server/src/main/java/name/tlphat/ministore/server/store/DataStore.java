package name.tlphat.ministore.server.store;

public interface DataStore {

    String getStringValue(String key);

    void setString(String key, String value);

    void appendToList(String key, String value);

    boolean isListExisted(String key);

    int getListSize(String key);

    String getAndPopRightmostElement(String key);

    boolean isStringExisted(String key);

    void removeList(String key);

    String getListElementAtIndex(String key, int index);
}
