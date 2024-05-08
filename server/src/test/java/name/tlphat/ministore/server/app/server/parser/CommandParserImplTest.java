package name.tlphat.ministore.server.app.server.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import name.tlphat.ministore.server.app.server.dto.CommandType;
import name.tlphat.ministore.server.app.server.dto.Tokens;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CommandParserImplTest {

  @ParameterizedTest
  @ValueSource(strings = {"get x", "get  x", "get  x  "})
  void testParseCommand(String command) {
    final Tokens expected = new Tokens(CommandType.GET, List.of("x"));

    final CommandParser parser = new CommandParserImpl();
    final Tokens actual = parser.parse(command);

    assertEquals(expected, actual);
  }
}
