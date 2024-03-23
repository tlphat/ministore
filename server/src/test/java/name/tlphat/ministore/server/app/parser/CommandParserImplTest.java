package name.tlphat.ministore.server.app.parser;

import name.tlphat.ministore.server.app.dto.CommandType;
import name.tlphat.ministore.server.app.dto.Tokens;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
