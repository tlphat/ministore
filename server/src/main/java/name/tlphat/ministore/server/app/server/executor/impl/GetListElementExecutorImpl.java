package name.tlphat.ministore.server.app.server.executor.impl;

import name.tlphat.ministore.server.app.server.dto.Tokens;
import name.tlphat.ministore.server.app.server.executor.CommandExecutor;
import name.tlphat.ministore.server.controllers.DataController;

public class GetListElementExecutorImpl implements CommandExecutor {

    private final DataController dataController;

    public GetListElementExecutorImpl(DataController dataController) {
        this.dataController = dataController;
    }

    @Override
    public String execute(Tokens tokens) {
        final String key = tokens.arguments().get(0);
        final String index = tokens.arguments().get(1);
        return dataController.getListElement(key, index);
    }
}
