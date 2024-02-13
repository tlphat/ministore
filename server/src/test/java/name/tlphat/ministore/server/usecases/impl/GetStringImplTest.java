package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.GetStringUseCase;
import name.tlphat.ministore.server.usecases.constants.GetStringError;
import name.tlphat.ministore.server.usecases.ports.GetStringDataAccess;
import name.tlphat.ministore.server.usecases.ports.GetStringView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetStringImplTest {

    private GetStringUseCase useCase;

    @BeforeEach
    void setup() {
        final GetStringDataAccess dataAccess = new DataAccessStub();
        final GetStringView view = new ViewStub();
        useCase = new GetStringImpl(dataAccess, view);
    }

    @Test
    void getExpectedString() {
        assertEquals("value", useCase.get("key"));
    }

    @Test
    void getStringKeyNotExisted() {
        assertEquals("NOT_EXISTED", useCase.get("not_existed"));
    }

    private static class DataAccessStub implements GetStringDataAccess {

        @Override
        public String get(String key) {
            return "value";
        }

        @Override
        public boolean isKeyExisted(String key) {
            return !"not_existed".equals(key);
        }
    }

    private static class ViewStub implements GetStringView {

        @Override
        public String prepareSuccessfulView(String response) {
            return response;
        }

        @Override
        public String prepareFailedView(GetStringError error) {
            return "NOT_EXISTED";
        }
    }
}
