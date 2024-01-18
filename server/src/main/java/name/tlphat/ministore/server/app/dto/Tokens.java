package name.tlphat.ministore.server.app.dto;

import java.util.List;

public record Tokens(
    CommandType commandType,
    List<String> arguments
) {
}
