package name.tlphat.ministore.server.controllers;

public interface DataController {

    String getStringValue(String key);

    String setStringValue(String key, String value);
}
