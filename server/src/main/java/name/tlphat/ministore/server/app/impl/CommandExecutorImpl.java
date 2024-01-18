package name.tlphat.ministore.server.app.impl;

import name.tlphat.ministore.server.app.CommandExecutor;
import name.tlphat.ministore.server.app.dto.CommandType;
import name.tlphat.ministore.server.app.dto.Tokens;
import name.tlphat.ministore.server.controllers.DataController;

public class CommandExecutorImpl implements CommandExecutor {

    private final DataController dataController;

    public CommandExecutorImpl(DataController dataController) {
        this.dataController = dataController;
    }

    @Override
    public String execute(Tokens tokens) {
        if (tokens.commandType() == CommandType.GET) {
            return dataController.getStringValue(tokens.arguments().get(0));
        }

        return "Operation not supported";
    }
}
