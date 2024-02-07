package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.SetStringUseCase;
import name.tlphat.ministore.server.usecases.constants.SetStringError;
import name.tlphat.ministore.server.usecases.ports.SetStringDataAccess;
import name.tlphat.ministore.server.usecases.ports.SetStringView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SetStringImplTest {

    private static class StubSetStringDataAccess implements SetStringDataAccess {

        private String value;

        @Override
        public void set(String key, String value) {
            this.value = value;
        }
    }

    private static class StubSetStringView implements  SetStringView {

        @Override
        public String prepareSuccessfulView() {
            return "successful";
        }

        @Override
        public String prepareErrorView(SetStringError error) {
            return "error";
        }
    }

    private StubSetStringDataAccess stubDataAccess;
    private SetStringUseCase useCase;

    @BeforeEach
    void setup() {
        stubDataAccess = new StubSetStringDataAccess();
        final SetStringView stubView = new StubSetStringView();
        useCase = new SetStringImpl(stubDataAccess, stubView, 6);
    }

    @Test
    void setStringSuccessfully() {
        final String expected = "successful";

        final String actual = useCase.set("key", "value");

        assertEquals(expected, actual);
        assertEquals("value", stubDataAccess.value);
    }

    @Test
    void setStringExceedsSizeLimit() {
        final String expected = "error";

        final String actual = useCase.set("key", "valueee");

        assertEquals(expected, actual);
        assertNull(stubDataAccess.value);
    }
}
