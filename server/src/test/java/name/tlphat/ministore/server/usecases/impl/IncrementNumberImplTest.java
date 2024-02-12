package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.IncrementNumberUseCase;
import name.tlphat.ministore.server.usecases.constants.IncrementNumberError;
import name.tlphat.ministore.server.usecases.ports.IncrementNumberDataAccess;
import name.tlphat.ministore.server.usecases.ports.IncrementNumberView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IncrementNumberImplTest {

    private IncrementNumberUseCase useCase;

    private DataAccessStub dataAccess;

    @BeforeEach
    void setup() {
        dataAccess = new DataAccessStub();
        final IncrementNumberView view = new ViewStub();
        useCase = new IncrementNumberImpl(dataAccess, view);
    }

    @Test
    void incrementNumberReturnsOk() {
        assertEquals("OK", useCase.increment("key"));
    }

    @Test
    void incrementNumberActuallyIncreaseNumber() {
        useCase.increment("key");
        assertTrue(dataAccess.incremented);
    }

    @Test
    void incrementNumberNotExistedReturnsError() {
        assertEquals("KEY_NOT_EXISTED", useCase.increment("not_existed"));
    }

    @Test
    void incrementNumberDoNotIncreaseWhenKeyNotExisted() {
        useCase.increment("not_existed");
        assertFalse(dataAccess.incremented);
    }

    private static class DataAccessStub implements IncrementNumberDataAccess {

        private boolean incremented;

        @Override
        public void increment(String key) {
            incremented = true;
        }

        @Override
        public boolean isKeyExisted(String key) {
            return !"not_existed".equals(key);
        }
    }

    private static class ViewStub implements IncrementNumberView {

        @Override
        public String prepareSuccessfulView() {
            return "OK";
        }

        @Override
        public String prepareFailedView(IncrementNumberError error) {
            return "KEY_NOT_EXISTED";
        }
    }
}
