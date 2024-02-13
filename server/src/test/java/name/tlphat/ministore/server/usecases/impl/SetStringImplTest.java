package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.SetStringUseCase;
import name.tlphat.ministore.server.usecases.constants.SetStringError;
import name.tlphat.ministore.server.usecases.ports.SetStringDataAccess;
import name.tlphat.ministore.server.usecases.ports.SetStringView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SetStringImplTest {

    private SetStringUseCase useCase;

    private DataAccessStub dataAccess;

    @BeforeEach
    void setup() {
        dataAccess = new DataAccessStub();
        final SetStringView view = new ViewStub();
        useCase = new SetStringImpl(dataAccess, view, 6);
    }

    @Test
    void setStringSuccessfully() {
        assertEquals("OK", useCase.set("key", "value"));
    }

    @Test
    void setStringActuallyStoreNewString() {
        useCase.set("key", "value");
        assertTrue(dataAccess.updated);
    }

    @Test
    void setStringExceedsSizeLimit() {
        assertEquals("VALUE_SIZE_TOO_LARGE", useCase.set("key", "valueee"));
    }

    @Test
    void setStringExceedsSizeLimitDoesNotStore() {
        useCase.set("key", "valueee");
        assertFalse(dataAccess.updated);
    }

    private static class DataAccessStub implements SetStringDataAccess {

        private boolean updated;

        @Override
        public void set(String key, String value) {
            updated = true;
        }
    }

    private static class ViewStub implements  SetStringView {

        @Override
        public String prepareSuccessfulView() {
            return "OK";
        }

        @Override
        public String prepareErrorView(SetStringError error) {
            return "VALUE_SIZE_TOO_LARGE";
        }
    }
}
