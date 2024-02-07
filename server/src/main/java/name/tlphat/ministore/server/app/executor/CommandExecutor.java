package name.tlphat.ministore.server.app.executor;

import name.tlphat.ministore.server.app.dto.Tokens;

public interface CommandExecutor {

    String execute(Tokens tokens);
}
