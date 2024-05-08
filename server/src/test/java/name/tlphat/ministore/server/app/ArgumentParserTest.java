package name.tlphat.ministore.server.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import name.tlphat.ministore.server.app.dto.ProgramArguments;
import name.tlphat.ministore.server.app.exception.ArgumentNotSupportedException;
import name.tlphat.ministore.server.app.exception.ArgumentWithNoValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArgumentParserTest {

  private ArgumentParser parser;

  @BeforeEach
  void setup() {
    parser = new ArgumentParser();
  }

  @Test
  void parseArguments() {
    final ProgramArguments expected =
        new ProgramArguments(Optional.of(1234), Optional.of("./abc.txt"), Optional.of(10L));

    final String[] args = {
      "--port=1234", "--snapshot-path=./abc.txt", "--snapshot-write-duration=10"
    };
    final ProgramArguments actual = parser.parse(args);

    assertEquals(expected, actual);
  }

  @Test
  void missingOneArgument() {
    final ProgramArguments expected =
        new ProgramArguments(Optional.of(1234), Optional.of("./abc.txt"), Optional.empty());

    final String[] args = {"--port=1234", "--snapshot-path=./abc.txt"};
    final ProgramArguments actual = parser.parse(args);

    assertEquals(expected, actual);
  }

  @Test
  void notSupportedArgument() {
    final String[] args = {"--po=11", "--snapshot-path=./abc.txt"};
    assertThrows(ArgumentNotSupportedException.class, () -> parser.parse(args));
  }

  @Test
  void argumentWithNoValue() {
    final String[] args = {"--port", "--snapshot-path=./abc.txt"};
    assertThrows(ArgumentWithNoValueException.class, () -> parser.parse(args));
  }

  @Test
  void argumentValueWithLeadingAndTrailingSpaces() {
    final ProgramArguments expected =
        new ProgramArguments(Optional.of(1234), Optional.of("./abc.txt"), Optional.empty());

    final String[] args = {"--port=  1234  ", "--snapshot-path=./abc.txt"};
    final ProgramArguments actual = parser.parse(args);

    assertEquals(expected, actual);
  }
}
