package name.tlphat.ministore.server.app.server.executor.impl;

import name.tlphat.ministore.server.app.server.dto.Tokens;
import name.tlphat.ministore.server.app.server.executor.CommandExecutor;

public class UnsupportedCommandExecutorImpl implements CommandExecutor {

  @Override
  public String execute(Tokens tokens) {
    return "Operation not supported";
  }
}
