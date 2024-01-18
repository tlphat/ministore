package name.tlphat.ministore.server.app;

import name.tlphat.ministore.server.app.dto.Tokens;

public interface CommandParser {

    Tokens parse(String command);
}
