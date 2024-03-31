package name.tlphat.ministore.server.app.server.executor.impl;

import name.tlphat.ministore.server.app.server.dto.Tokens;
import name.tlphat.ministore.server.app.server.executor.CommandExecutor;
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
