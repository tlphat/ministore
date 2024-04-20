# Ministore Server Features

## Context

This document summarizes key features support by ministore's current version.

## Operations

The following table summarizes the operations and data types available.

| Operation                                                    | Data type | Syntax                  | Example request        | Example response |
|--------------------------------------------------------------|-----------|-------------------------|------------------------|------------------|
| Insert a string value                                        | STRING    | SET \<key\> \<value\>   | SET my_key my_value    | OK               |
| Retrieve a string value                                      | STRING    | GET \<key\>             | GET my_key             | my_value         |
| Insert an element to the end of the list                     | LIST      | RPUSH \<key\> \<value\> | RPUSH my_list my_value | OK               |
| Pop the last element out of the list and return that element | LIST      | RPOP \<key\>            | RPOP my_list           | my_value         |
| Get the element at  a random index (0-based)  of the list    | LIST      | EGET \<key\> \<index\>  | EGET my_list 0         | my_value         |
| Get the list of keys                                         | ALL       | KEYS                    | KEYS                   | 1) x SINGLETON   |

## Snapshotting

We support snapshotting mechanism to recover the data in case of failure.
A snapshot file is created when user runs the server for the first time.
The data is then written to the snapshot file every 10 seconds by default. 
After recovering from a failure, server reads the data back from the file and continue the execution normally.

The path to the snapshot file and delay between back-ups (in seconds) can be configured when running the server.
For example:

```bash
java -jar server/build/libs/server.jar --port=1234 --snapshot-path="./abc.txt" --snapshot-write-duration=15
```
