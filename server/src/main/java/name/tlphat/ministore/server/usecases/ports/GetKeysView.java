package name.tlphat.ministore.server.usecases.ports;

import name.tlphat.ministore.server.usecases.dto.Key;

import java.util.List;

public interface GetKeysView {

    String prepareSuccessfulView(List<Key> keys);
}
