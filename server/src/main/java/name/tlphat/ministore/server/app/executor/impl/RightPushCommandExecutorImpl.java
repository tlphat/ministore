package name.tlphat.ministore.server.app.executor.impl;

import name.tlphat.ministore.server.app.dto.Tokens;
import name.tlphat.ministore.server.app.executor.CommandExecutor;
import name.tlphat.ministore.server.controllers.DataController;

public class RightPushCommandExecutorImpl implements CommandExecutor {

    private final DataController dataController;

    public RightPushCommandExecutorImpl(DataController dataController) {
        this.dataController = dataController;
    }

    @Override
    public String execute(Tokens tokens) {
        final String key = tokens.arguments().get(0);
        final String value = tokens.arguments().get(1);
        return dataController.rightPushStringList(key, value);
    }
}
