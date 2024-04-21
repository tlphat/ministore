# Ministore

A simple in-memory datastore for key-value storage and caching.


## Demo

![](./assets/demo.gif)


## Features

- Concurrent storing and retrieving mechanism for STRING and LIST entries.
- Snapshotting (for persistence and data recovery).
- Ministore server is able to serve 10,000 requests per second with 0 error rate (tested on a computer with 10 cores).

For more details, refer to [Ministore Server Features](./server/doc/features.md) and [Concurrency Test](./server/doc/concurrency.md).

## Run Locally

As a prerequisite, it is required to have runtime environment for Java 17+.

Clone the project

```bash
  git clone git@github.com:tlphat/ministore.git
```

Build the server

```bash
  ./gradlew server:build
```

Start the server

```bash
  java -jar server/build/libs/server.jar
```

Build the CLI
```bash
  ./gradlew cli:build
```

Start the CLI

```bash
  java -jar cli/build/libs/cli.jar
```
