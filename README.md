# Ministore

A simple in-memory datastore for key-value storage and caching.


## Demo

![](./assets/demo.gif)


## Features

- Store and retrieve key-value entries for STRING, LIST, and NUMBER.
- Snapshotting (for persistence and data recovery).


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
