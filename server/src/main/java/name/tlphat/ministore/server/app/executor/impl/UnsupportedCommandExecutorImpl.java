package name.tlphat.ministore.server.app.executor.impl;

import name.tlphat.ministore.server.app.executor.CommandExecutor;
import name.tlphat.ministore.server.app.dto.Tokens;

public class UnsupportedCommandExecutorImpl implements CommandExecutor {

    @Override
    public String execute(Tokens tokens) {
        return "Operation not supported";
    }
}