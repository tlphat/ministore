package name.tlphat.ministore.server.app.dto;

import java.util.Optional;

public record ProgramArguments(
    Optional<Integer> serverPort,
    Optional<String> snapshotPath,
    Optional<Long> snapshotWriteDurationSecond) {}
