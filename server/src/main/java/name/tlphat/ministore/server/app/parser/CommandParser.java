package name.tlphat.ministore.server.app.parser;

import name.tlphat.ministore.server.app.dto.Tokens;

public interface CommandParser {

    Tokens parse(String command);
}
