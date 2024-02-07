package name.tlphat.ministore.server.app.impl;

import name.tlphat.ministore.server.app.CommandExecutor;
import name.tlphat.ministore.server.app.dto.Tokens;
import name.tlphat.ministore.server.controllers.DataController;

public class CommandExecutorImpl implements CommandExecutor {

    private final DataController dataController;

    public CommandExecutorImpl(DataController dataController) {
        this.dataController = dataController;
    }

    @Override
    public String execute(Tokens tokens) {
        return switch (tokens.commandType()) {
            case GET -> executeGetString(tokens);
            case SET -> executeSetString(tokens);
            case EXIT -> "OK";
            default -> "Operation not supported";
        };
    }

    private String executeGetString(Tokens tokens) {
        final String key = tokens.arguments().get(0);
        return dataController.getStringValue(key);
    }

    private String executeSetString(Tokens tokens) {
        final String key = tokens.arguments().get(0);
        final String value = tokens.arguments().get(1);
        return dataController.setStringValue(key, value);
    }
}
