package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.ports.GetStringDataAccess;
import name.tlphat.ministore.server.usecases.ports.GetStringView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetStringImplTest {

    @Test
    void getExpectedString() {
        final String expected = "value";

        final GetStringDataAccess stubGetStringDataAccess = key -> "value";
        final GetStringView stubGetStringView = key -> key;
        final GetStringImpl getStringUseCase = new GetStringImpl(stubGetStringDataAccess, stubGetStringView);
        final String actual = getStringUseCase.get("key");

        assertEquals(expected, actual);
    }
}
