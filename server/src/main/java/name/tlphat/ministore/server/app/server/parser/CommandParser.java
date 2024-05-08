package name.tlphat.ministore.server.app.server.parser;

import name.tlphat.ministore.server.app.server.dto.Tokens;

public interface CommandParser {

  Tokens parse(String command);
}
