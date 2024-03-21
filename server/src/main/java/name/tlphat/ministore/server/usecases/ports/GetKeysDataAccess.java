package name.tlphat.ministore.server.usecases.ports;

import name.tlphat.ministore.server.usecases.dto.Key;

import java.util.List;

public interface GetKeysDataAccess {

    List<Key> getKeys();
}
