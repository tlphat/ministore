package name.tlphat.ministore.server.store;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public record Data(
    Map<String, String> stringData,
    Map<String, List<String>> stringListData
) implements Serializable {
}
