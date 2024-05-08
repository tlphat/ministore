package name.tlphat.ministore.server.app.server.dto;

public enum CommandType {
  GET,
  SET,
  DEL,
  RPUSH,
  RPOP,
  EGET,
  KEYS,
  EXIT,
  UNSUPPORTED,
}
