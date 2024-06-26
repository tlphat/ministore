package name.tlphat.ministore.server;

import name.tlphat.ministore.server.app.ApplicationRunner;
import name.tlphat.ministore.server.app.ArgumentParser;
import name.tlphat.ministore.server.app.dto.ProgramArguments;

public class TestServer extends Thread {

  private final ApplicationRunner runner;

  public TestServer(int port) {
    final String[] args = {
      String.format("--port=%d", port), "--snapshot-path=./integration-test.txt"
    };
    final ArgumentParser argumentParser = new ArgumentParser();
    final ProgramArguments programArguments = argumentParser.parse(args);
    runner = new ApplicationRunner(programArguments);
  }

  @Override
  public void run() {
    runner.run();
  }

  public boolean isReady() {
    return runner.isReady();
  }
}
