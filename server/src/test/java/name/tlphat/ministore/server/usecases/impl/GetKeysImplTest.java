package name.tlphat.ministore.server.usecases.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import name.tlphat.ministore.server.usecases.GetKeysUseCase;
import name.tlphat.ministore.server.usecases.dto.Key;
import name.tlphat.ministore.server.usecases.ports.GetKeysDataAccess;
import name.tlphat.ministore.server.usecases.ports.GetKeysView;
import org.junit.jupiter.api.Test;

class GetKeysImplTest {

  @Test
  void returnsKeyList() {
    final String expected = "1)\t\tkeys1\t\tstring\n2)\t\tkeys2\t\tlist";

    final GetKeysDataAccess dataAccess = new StubDataAccess();
    final GetKeysView view = new StubView();
    final GetKeysUseCase useCase = new GetKeysImpl(dataAccess, view);
    final String actual = useCase.getKeys();

    assertEquals(expected, actual);
  }

  private static class StubDataAccess implements GetKeysDataAccess {

    @Override
    public List<Key> getKeys() {
      return null;
    }
  }

  private static class StubView implements GetKeysView {

    @Override
    public String prepareSuccessfulView(List<Key> keys) {
      return "1)\t\tkeys1\t\tstring\n2)\t\tkeys2\t\tlist";
    }
  }
}
