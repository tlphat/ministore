package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.constants.RightPushStringError;
import name.tlphat.ministore.server.usecases.ports.RightPushListDataAccess;
import name.tlphat.ministore.server.usecases.ports.RightPushListView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RightPushListImplTest {

    private RightPushListImpl useCase;

    private RightPushListDataAccessStub dataAccess;

    @BeforeEach
    void setup() {
        dataAccess = new RightPushListDataAccessStub();
        final RightPushListViewStub view = new RightPushListViewStub();
        useCase = new RightPushListImpl(dataAccess, view, 5);
    }

    @Test
    void rightPushOk() {
        final String actual = useCase.rightPush("key", "value");
        assertEquals("OK", actual);
    }

    @Test
    void rightPushValueAppended() {
        useCase.rightPush("key", "value");
        assertEquals("appended", dataAccess.status);
    }

    @Test
    void rightPushStringExceedsSizeLimit() {
        final String actual = useCase.rightPush("key", "valueeeee");
        assertEquals("VALUE_SIZE_TOO_LARGE", actual);
    }

    private static class RightPushListDataAccessStub implements RightPushListDataAccess {

        private String status = "unchanged";

        @Override
        public void appendRightToList(String key, String value) {
            status = "appended";
        }
    }

    private static class RightPushListViewStub implements RightPushListView {

        @Override
        public String prepareSuccessfulView() {
            return "OK";
        }

        @Override
        public String prepareFailedView(RightPushStringError error) {
            return "VALUE_SIZE_TOO_LARGE";
        }
    }
}
