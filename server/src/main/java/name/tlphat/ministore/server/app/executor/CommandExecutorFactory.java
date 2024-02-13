package name.tlphat.ministore.server.app.executor;

import name.tlphat.ministore.server.app.dto.CommandType;
import name.tlphat.ministore.server.app.executor.impl.ExitCommandExecutorImpl;
import name.tlphat.ministore.server.app.executor.impl.GetCommandExecutorImpl;
import name.tlphat.ministore.server.app.executor.impl.GetListElementExecutorImpl;
import name.tlphat.ministore.server.app.executor.impl.RightPopCommandExecutorImpl;
import name.tlphat.ministore.server.app.executor.impl.RightPushCommandExecutorImpl;
import name.tlphat.ministore.server.app.executor.impl.SetCommandExecutorImpl;
import name.tlphat.ministore.server.app.executor.impl.UnsupportedCommandExecutorImpl;
import name.tlphat.ministore.server.controllers.DataController;

public class CommandExecutorFactory {

    private final DataController dataController;

    public CommandExecutorFactory(DataController dataController) {
        this.dataController = dataController;
    }

    public CommandExecutor getCommandExecutor(CommandType commandType) {
        return switch (commandType) {
            case GET -> new GetCommandExecutorImpl(dataController);
            case SET -> new SetCommandExecutorImpl(dataController);
            case RPUSH -> new RightPushCommandExecutorImpl(dataController);
            case RPOP -> new RightPopCommandExecutorImpl(dataController);
            case EGET -> new GetListElementExecutorImpl(dataController);
            case EXIT -> new ExitCommandExecutorImpl();
            default -> new UnsupportedCommandExecutorImpl();
        };
    }
}
