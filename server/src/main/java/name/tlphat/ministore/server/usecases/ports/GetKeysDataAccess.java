package name.tlphat.ministore.server.usecases.ports;

import java.util.List;
import name.tlphat.ministore.server.usecases.dto.Key;

public interface GetKeysDataAccess {

  List<Key> getKeys();
}
