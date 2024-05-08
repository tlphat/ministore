package name.tlphat.ministore.server.usecases.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import name.tlphat.ministore.server.usecases.GetListElementUseCase;
import name.tlphat.ministore.server.usecases.constants.GetListElementError;
import name.tlphat.ministore.server.usecases.ports.GetListElementDataAccess;
import name.tlphat.ministore.server.usecases.ports.GetListElementView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GetListElementImplTest {

  private GetListElementUseCase useCase;

  @BeforeEach
  void setup() {
    final GetListElementDataAccess dataAccess = new GetListElementDataAccessStub();
    final GetListElementView view = new GetListElementViewStub();
    useCase = new GetListElementImpl(dataAccess, view);
  }

  @ParameterizedTest
  @CsvSource({
    "key, 2, value",
    "not_existed, 2, NOT_EXISTED",
    "key, -1, INDEX_OUT_OF_BOUND",
    "key, 10, INDEX_OUT_OF_BOUND",
  })
  void getElementAtIndex(String key, int index, String expected) {
    final String actual = useCase.getElementAtIndex(key, index);
    assertEquals(expected, actual);
  }

  private static class GetListElementDataAccessStub implements GetListElementDataAccess {

    @Override
    public boolean isListExisted(String key) {
      return !"not_existed".equals(key);
    }

    @Override
    public int getListSize(String key) {
      return 10;
    }

    @Override
    public String getElementAtIndex(String key, int index) {
      return "value";
    }
  }

  private static class GetListElementViewStub implements GetListElementView {

    @Override
    public String prepareSuccessfulView(String data) {
      return "value";
    }

    @Override
    public String prepareFailedView(GetListElementError error) {
      return error.toString();
    }
  }
}
