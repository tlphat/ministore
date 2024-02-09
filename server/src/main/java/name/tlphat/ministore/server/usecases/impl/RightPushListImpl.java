package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.RightPushListUseCase;
import name.tlphat.ministore.server.usecases.ports.RightPushListDataAccess;

public class RightPushListImpl implements RightPushListUseCase {

    private final RightPushListDataAccess dataAccess;

    public RightPushListImpl(RightPushListDataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public String rightPush(String key, String value) {
        dataAccess.appendRightToList(key, value);
        return "OK";
    }
}
