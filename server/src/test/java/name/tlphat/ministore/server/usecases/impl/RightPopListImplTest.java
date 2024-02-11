package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.RightPopListUseCase;
import name.tlphat.ministore.server.usecases.constants.RightPopListError;
import name.tlphat.ministore.server.usecases.ports.RightPopListDataAccess;
import name.tlphat.ministore.server.usecases.ports.RightPopListView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        "not_existed, KEY_NOT_EXISTED",
        "empty_list, LIST_ALREADY_EMPTY",
    })
    void rightPopValue(String key, String expected) {
        assertEquals(expected, useCase.rightPop(key));
    }

    @Test
    void rightPopActuallyPopValue() {
        final String key = "key";
        useCase.rightPop(key);
        assertEquals(key, dataAccess.invokedKey);
    }

    @Test
    void rightPopListNotExistedCheckNoValueGotPopped() {
        final String key = "not_existed";
        useCase.rightPop(key);
        assertNull(dataAccess.invokedKey);
    }

    @Test
    void rightPopListEmptyNotValueGotPopped() {
        final String key = "empty_list";
        useCase.rightPop(key);
        assertNull(dataAccess.invokedKey);
    }

    private static class RightPopListDataAccessStub implements RightPopListDataAccess {

        private String invokedKey;

        @Override
        public boolean isKeyExisted(String key) {
            return !"not_existed".equals(key);
        }

        @Override
        public boolean isListEmpty(String key) {
            return "empty_list".equals(key);
        }

        @Override
        public String rightPop(String key) {
            invokedKey = key;
            return "value";
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
