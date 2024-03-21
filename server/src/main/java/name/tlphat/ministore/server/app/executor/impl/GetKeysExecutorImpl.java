package name.tlphat.ministore.server.app.executor.impl;

import name.tlphat.ministore.server.app.dto.Tokens;
import name.tlphat.ministore.server.app.executor.CommandExecutor;
import name.tlphat.ministore.server.controllers.DataController;

public class GetKeysExecutorImpl implements CommandExecutor {
    private final DataController dataController;

    public GetKeysExecutorImpl(DataController dataController) {
        this.dataController = dataController;
    }

    @Override
    public String execute(Tokens tokens) {
        return dataController.getKeys();
    }
}
