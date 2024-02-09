package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.ports.RightPushListDataAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RightPushListImplTest {

    private RightPushListImpl useCase;

    private RightPushListDataAccessStub dataAccess;

    @BeforeEach
    void setup() {
        dataAccess = new RightPushListDataAccessStub();
        useCase = new RightPushListImpl(dataAccess);
    }

    @Test
    void rightPushOk() {
        final String result = useCase.rightPush("key", "value");
        assertEquals("OK", result);
    }

    @Test
    void rightPushValueAppended() {
        useCase.rightPush("key", "value");
        assertEquals("appended", dataAccess.status);
    }

    private static class RightPushListDataAccessStub implements RightPushListDataAccess {

        private String status;

        @Override
        public void appendRightToList(String key, String value) {
            status = "appended";
        }
    }
}
