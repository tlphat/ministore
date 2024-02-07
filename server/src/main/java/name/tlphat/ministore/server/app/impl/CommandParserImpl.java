package name.tlphat.ministore.server.app.impl;

import name.tlphat.ministore.server.app.CommandParser;
import name.tlphat.ministore.server.app.dto.CommandType;
import name.tlphat.ministore.server.app.dto.Tokens;

import java.util.List;
import java.util.Map;

public class CommandParserImpl implements CommandParser {

    private static final Map<String, CommandType> COMMAND_MAP = Map.of(
        "get", CommandType.GET,
        "set", CommandType.SET,
        "exit", CommandType.EXIT
    );

    @Override
    public Tokens parse(String command) {
        final String[] words = command.split(" ");
        final CommandType commandType = COMMAND_MAP.getOrDefault(words[0], CommandType.UNSUPPORTED);
        final List<String> arguments = java.util.Arrays.stream(words, 1, words.length).toList();
        return new Tokens(commandType, arguments);
    }
}
