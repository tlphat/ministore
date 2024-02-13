package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.RightPopListUseCase;
import name.tlphat.ministore.server.usecases.constants.RightPopListError;
import name.tlphat.ministore.server.usecases.ports.RightPopListDataAccess;
import name.tlphat.ministore.server.usecases.ports.RightPopListView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RightPopListImplTest {

    private RightPopListUseCase useCase;

    private RightPopListDataAccessStub dataAccess;

    @BeforeEach
    void setup() {
        dataAccess = new RightPopListDataAccessStub();
        final RightPopListView view = new RightPopListViewStub();
        useCase = new RightPopListImpl(dataAccess, view);
    }

    @ParameterizedTest
    @CsvSource({
        "key, value",
        "not_existed, NOT_EXISTED",
        "one_element, value",
    })
    void rightPopResponseCheck(String key, String expected) {
        assertEquals(expected, useCase.rightPop(key));
    }

    @ParameterizedTest
    @CsvSource({
        "key, popped",
        "not_existed, not_invoked",
        "one_element, removed",
    })
    void rightPopDataAccessStatusCheck(String key, String expected) {
        useCase.rightPop(key);
        assertEquals(expected, dataAccess.status);
    }

    private static class RightPopListDataAccessStub implements RightPopListDataAccess {

        private String status = "not_invoked";

        @Override
        public boolean isKeyExisted(String key) {
            return !"not_existed".equals(key);
        }

        @Override
        public boolean isListEmpty(String key) {
            return "one_element".equals(key);
        }

        @Override
        public String rightPop(String key) {
            status = "popped";
            return "value";
        }

        @Override
        public void removeList(String key) {
            status = "removed";
        }
    }

    private static class RightPopListViewStub implements RightPopListView {

        @Override
        public String prepareSuccessfulView(String data) {
            return "value";
        }

        @Override
        public String prepareFailedView(RightPopListError error) {
            return error.toString();
        }
    }
}
