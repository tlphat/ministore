package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.DecrementNumberUseCase;
import name.tlphat.ministore.server.usecases.constants.DecrementNumberError;
import name.tlphat.ministore.server.usecases.ports.DecrementNumberDataAccess;
import name.tlphat.ministore.server.usecases.ports.DecrementNumberView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DecrementNumberImplTest {

    private DecrementNumberUseCase useCase;

    private DataAccessStub dataAccess;

    @BeforeEach
    void setup() {
        dataAccess = new DataAccessStub();
        final DecrementNumberView view = new ViewStub();
        useCase = new DecrementNumberImpl(dataAccess, view);
    }

    @Test
    void decrementNumberReturnsOk() {
        assertEquals("OK", useCase.decrement("key"));
    }

    @Test
    void decrementNumberActuallyDecreaseNumber() {
        useCase.decrement("key");
        assertTrue(dataAccess.decremented);
    }

    @Test
    void decrementNumberNotExistedReturnsError() {
        assertEquals("NOT_EXISTED", useCase.decrement("not_existed"));
    }

    @Test
    void decrementNumberDoNotDecreaseWhenKeyNotExisted() {
        useCase.decrement("not_existed");
        assertFalse(dataAccess.decremented);
    }

    private static class DataAccessStub implements DecrementNumberDataAccess {

        private boolean decremented;

        @Override
        public void decrement(String key) {
            decremented = true;
        }

        @Override
        public boolean isKeyExisted(String key) {
            return !"not_existed".equals(key);
        }
    }

    private static class ViewStub implements DecrementNumberView {

        @Override
        public String prepareSuccessfulView() {
            return "OK";
        }

        @Override
        public String prepareFailedView(DecrementNumberError error) {
            return "NOT_EXISTED";
        }
    }
}
