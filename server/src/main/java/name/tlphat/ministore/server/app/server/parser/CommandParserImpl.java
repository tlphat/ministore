package name.tlphat.ministore.server.app.server.parser;

import name.tlphat.ministore.server.app.server.dto.CommandType;
import name.tlphat.ministore.server.app.server.dto.Tokens;

import java.util.List;
import java.util.Map;

public class CommandParserImpl implements CommandParser {

    private static final Map<String, CommandType> COMMAND_MAP = Map.of(
        "get", CommandType.GET,
        "set", CommandType.SET,
        "del", CommandType.DEL,
        "rpush", CommandType.RPUSH,
        "rpop", CommandType.RPOP,
        "eget", CommandType.EGET,
        "keys", CommandType.KEYS,
        "exit", CommandType.EXIT
    );

    private static final String REGEX_SPACES = " +";

    @Override
    public Tokens parse(String command) {
        // On CLI abrupt termination, it sends a null command to the server
        if (command == null) {
            return new Tokens(CommandType.EXIT, List.of());
        }

        final String[] words = command.split(REGEX_SPACES);
        final CommandType commandType = COMMAND_MAP.getOrDefault(words[0], CommandType.UNSUPPORTED);
        final List<String> arguments = java.util.Arrays.stream(words, 1, words.length).toList();
        return new Tokens(commandType, arguments);
    }
}
