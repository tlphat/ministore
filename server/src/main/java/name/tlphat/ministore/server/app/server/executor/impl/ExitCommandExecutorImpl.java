package name.tlphat.ministore.server.app.server.executor.impl;

import name.tlphat.ministore.server.app.server.executor.CommandExecutor;
import name.tlphat.ministore.server.app.server.dto.Tokens;

public class ExitCommandExecutorImpl implements CommandExecutor {

    @Override
    public String execute(Tokens tokens) {
        return "OK";
    }
}