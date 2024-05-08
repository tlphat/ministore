package name.tlphat.ministore.server.app.server.executor;

import name.tlphat.ministore.server.app.server.dto.Tokens;

public interface CommandExecutor {

  String execute(Tokens tokens);
}
