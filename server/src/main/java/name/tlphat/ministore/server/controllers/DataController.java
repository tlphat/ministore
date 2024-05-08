package name.tlphat.ministore.server.controllers;

public interface DataController {

  String getStringValue(String key);

  String setStringValue(String key, String value);

  String deleteString(String key);

  String rightPushStringList(String key, String value);

  String rightPopStringList(String key);

  String getListElement(String key, String indexInString);

  String getKeys();
}
