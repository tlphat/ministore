package name.tlphat.ministore.server;

import name.tlphat.ministore.server.app.ApplicationRunner;
import name.tlphat.ministore.server.app.ArgumentParser;
import name.tlphat.ministore.server.app.dto.ProgramArguments;

public class Application {

  public static void main(String[] args) {
    final ArgumentParser argumentParser = new ArgumentParser();
    final ProgramArguments programArguments = argumentParser.parse(args);
    final ApplicationRunner runner = new ApplicationRunner(programArguments);
    runner.run();
  }
}
