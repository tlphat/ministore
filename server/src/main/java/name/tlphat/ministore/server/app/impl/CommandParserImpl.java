package name.tlphat.ministore.server.app.impl;

import name.tlphat.ministore.server.app.CommandParser;
import name.tlphat.ministore.server.app.dto.CommandType;
import name.tlphat.ministore.server.app.dto.Tokens;

import java.util.List;

public class CommandParserImpl implements CommandParser {

    @Override
    public Tokens parse(String command) {
        final String[] words = command.split(" ");
        final CommandType commandType = "GET".equals(words[0]) ? CommandType.GET : CommandType.UNSUPPORTED;
        final List<String> arguments = java.util.Arrays.stream(words, 1, words.length).toList();
        return new Tokens(commandType, arguments);
    }
}
