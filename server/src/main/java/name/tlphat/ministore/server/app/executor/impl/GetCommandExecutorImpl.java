package name.tlphat.ministore.server.app.executor.impl;

import name.tlphat.ministore.server.app.executor.CommandExecutor;
import name.tlphat.ministore.server.app.dto.Tokens;
import name.tlphat.ministore.server.controllers.DataController;

public class GetCommandExecutorImpl implements CommandExecutor {

    private final DataController dataController;

    public GetCommandExecutorImpl(DataController dataController) {
        this.dataController = dataController;
    }

    @Override
    public String execute(Tokens tokens) {
        final String key = tokens.arguments().get(0);
        return dataController.getStringValue(key);
    }
}
