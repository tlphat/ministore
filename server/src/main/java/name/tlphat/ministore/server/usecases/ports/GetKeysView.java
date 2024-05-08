package name.tlphat.ministore.server.usecases.ports;

import java.util.List;
import name.tlphat.ministore.server.usecases.dto.Key;

public interface GetKeysView {

  String prepareSuccessfulView(List<Key> keys);
}
