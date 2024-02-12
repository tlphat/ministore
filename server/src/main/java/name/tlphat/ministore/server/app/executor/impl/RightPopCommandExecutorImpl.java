package name.tlphat.ministore.server.app.executor.impl;

import name.tlphat.ministore.server.app.dto.Tokens;
import name.tlphat.ministore.server.app.executor.CommandExecutor;
import name.tlphat.ministore.server.controllers.DataController;

public class RightPopCommandExecutorImpl implements CommandExecutor {

    private final DataController dataController;

    public RightPopCommandExecutorImpl(DataController dataController) {
        this.dataController = dataController;
    }

    @Override
    public String execute(Tokens tokens) {
        return dataController.rightPopStringList(tokens.arguments().get(0));
    }
}
