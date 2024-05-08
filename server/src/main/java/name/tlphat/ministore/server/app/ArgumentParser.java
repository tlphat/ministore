package name.tlphat.ministore.server.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import name.tlphat.ministore.server.app.dto.ProgramArguments;
import name.tlphat.ministore.server.app.exception.ArgumentNotSupportedException;
import name.tlphat.ministore.server.app.exception.ArgumentWithNoValueException;

@Slf4j
public class ArgumentParser {

  private static final String EQUALS_SIGN = "=";

  private enum ArgumentKey {
    PORT_KEY("--port"),
    SNAPSHOT_PATH_KEY("--snapshot-path"),
    SNAPSHOT_WRITE_DURATION_KEY("--snapshot-write-duration");

    private final String key;

    ArgumentKey(String key) {
      this.key = key;
    }

    public String getKey() {
      return key;
    }
  }

  private final Set<String> supportedArguments =
      Arrays.stream(ArgumentKey.values()).map(ArgumentKey::getKey).collect(Collectors.toSet());

  /**
   * Parse the program argument into a collection of key value pairs, packaged in a {@link
   * ProgramArguments} object.
   *
   * @param args program arguments as an array of Strings
   * @return {@link ProgramArguments} object consists of arguments
   * @throws ArgumentWithNoValueException if there is no value
   * @throws ArgumentNotSupportedException if the argument key is not supported
   */
  public ProgramArguments parse(String[] args) {
    final Map<String, String> argumentMap =
        Arrays.stream(args)
            .map(ArgumentParser::splitArgumentValue)
            .map(this::validateParts)
            .collect(Collectors.toMap(parts -> parts.get(0), parts -> parts.get(1)));

    final Optional<Integer> serverPort =
        Optional.ofNullable(argumentMap.get(ArgumentKey.PORT_KEY.getKey())).map(Integer::parseInt);
    final Optional<String> snapshotPath =
        Optional.ofNullable(argumentMap.get(ArgumentKey.SNAPSHOT_PATH_KEY.getKey()));
    final Optional<Long> snapshotWriteDuration =
        Optional.ofNullable(argumentMap.get(ArgumentKey.SNAPSHOT_WRITE_DURATION_KEY.getKey()))
            .map(Long::parseLong);
    return new ProgramArguments(serverPort, snapshotPath, snapshotWriteDuration);
  }

  /**
   * Split the argument in following format "key=value".
   *
   * <p>This method only split the at the first occurrence of "=", so the result will be an array
   * with at mose 2 elements.
   *
   * <p>If there is no "=" in the input String, this method returns a List with only 1 element,
   * which is the original String.
   *
   * @param arg input argument
   * @return List of String that contains at most 2 elements: key & value
   */
  private static List<String> splitArgumentValue(String arg) {
    // We only split at the first occurrence
    return Arrays.stream(arg.split(EQUALS_SIGN, 2)).map(String::trim).toList();
  }

  private List<String> validateParts(List<String> parts) {
    if (parts.isEmpty()) {
      throw new IllegalArgumentException();
    }

    // Check if there are value assigned to this argument
    if (parts.size() < 2) {
      log.error("Argument {} doesn't have value assigned to it", parts.get(0));
      throw new ArgumentWithNoValueException();
    }

    // Check if the key is valid
    if (!supportedArguments.contains(parts.get(0))) {
      log.error("Argument {} is not supported", parts.get(0));
      throw new ArgumentNotSupportedException();
    }

    return parts;
  }
}
