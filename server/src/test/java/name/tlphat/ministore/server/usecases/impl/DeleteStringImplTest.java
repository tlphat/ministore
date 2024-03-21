package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.DeleteStringUseCase;
import name.tlphat.ministore.server.usecases.constants.DeleteStringError;
import name.tlphat.ministore.server.usecases.ports.DeleteStringDataAccess;
import name.tlphat.ministore.server.usecases.ports.DeleteStringView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteStringImplTest {

    private DeleteStringUseCase useCase;

    private StubDataAccess dataAccess;

    @BeforeEach
    void setup() {
        dataAccess = new StubDataAccess();
        final DeleteStringView view = new StubView();
        useCase = new DeleteStringImpl(dataAccess, view);
    }

    @Test
    void returnsOk() {
        final String expected = "OK";
        final String actual = useCase.delete("key");
        assertEquals(expected, actual);
    }

    @Test
    void deletesFromDatastore() {
        useCase.delete("key");
        assertTrue(dataAccess.deleted);
    }

    @Test
    void returnsErrorWhenDeleteNonExistedKey() {
        final String expected = "NOT_EXISTED";
        final String actual = useCase.delete("not_existed");
        assertEquals(expected, actual);
    }

    private static class StubView implements DeleteStringView {

        @Override
        public String prepareSuccessfulView() {
            return "OK";
        }

        @Override
        public String prepareFailedView(DeleteStringError error) {
            return "NOT_EXISTED";
        }
    }

    private static class StubDataAccess implements DeleteStringDataAccess {

        private boolean deleted = false;

        @Override
        public void removeKey(String key) {
            deleted = true;
        }

        @Override
        public boolean keyNotExisted(String key) {
            return "not_existed".equals(key);
        }
    }
}
