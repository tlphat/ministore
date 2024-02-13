package name.tlphat.ministore.server.usecases.impl;

import name.tlphat.ministore.server.usecases.GetListSizeUseCase;
import name.tlphat.ministore.server.usecases.constants.GetListSizeError;
import name.tlphat.ministore.server.usecases.ports.GetListSizeDataAccess;
import name.tlphat.ministore.server.usecases.ports.GetListSizeView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetListSizeImplTest {

    private GetListSizeUseCase useCase;

    @BeforeEach
    void setup() {
        final GetListSizeDataAccess dataAccess = new DataAccessStub();
        final GetListSizeView view = new ViewStub();
        useCase = new GetListSizeImpl(dataAccess, view);
    }

    @Test
    void getSize() {
        assertEquals("5", useCase.getSize("key"));
    }

    @Test
    void getSizeOfListNotExisted() {
        assertEquals("NOT_EXISTED", useCase.getSize("not_existed"));
    }

    private static class DataAccessStub implements GetListSizeDataAccess {

        @Override
        public int getListSize(String key) {
            return 5;
        }

        @Override
        public boolean isListExisted(String key) {
            return !"not_existed".equals(key);
        }
    }

    private static class ViewStub implements GetListSizeView {

        @Override
        public String prepareSuccessfulView(int size) {
            return String.valueOf(size);
        }

        @Override
        public String prepareFailedView(GetListSizeError error) {
            return "NOT_EXISTED";
        }
    }
}
