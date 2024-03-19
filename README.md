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

Go to the project directory

```bash
  cd ministore/server
```

Build the server

```bash
  ./gradlew build
```

Start the server

```bash
  java -jar build/libs/server.jar
```
