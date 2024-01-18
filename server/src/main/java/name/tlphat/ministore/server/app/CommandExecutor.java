package name.tlphat.ministore.server.app;

import name.tlphat.ministore.server.app.dto.Tokens;

public interface CommandExecutor {

    String execute(Tokens tokens);
}
